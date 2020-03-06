package com;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.sun.xml.internal.ws.util.StringUtils;
import gov.nist.javax.sip.header.From;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import javax.sip.*;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.*;

class ServerListener implements SipListener {
    private AddressFactory addressFactory;

    private HeaderFactory headerFactory;

    private MessageFactory messageFactory;

    private SipProvider sipProvider;

    private JainSipServer jainSipServer;

    public ServerListener(AddressFactory addressFactory, HeaderFactory headerFactory, MessageFactory messageFactory,
                          SipProvider sipProvider,JainSipServer jainSipServer) {
        super();
        this.addressFactory = addressFactory;
        this.headerFactory = headerFactory;
        this.messageFactory = messageFactory;
        this.sipProvider = sipProvider;
        this.jainSipServer=jainSipServer;
    }

    /**
     * 保存正在注册的用户，注册第一步的
     */
    private static Set<String> registingId = new HashSet<>();
    /**
     * 保存当前注册的用户，注册成功的
     */
    private static Hashtable<String, URI> registedContactURI = new Hashtable<>();

    //Listener实现
    @Override
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        if(null == request) {
            System.out.println("收到的requestEvent.getRequest() is null.");
            return ;
        }

        System.out.println(">>>>>收到的request内容是\n"+request);

        switch(request.getMethod().toUpperCase()){
            case Request.INVITE:
                System.out.println("收到INVITE的请求");
                break;
            case Request.REGISTER:
                System.out.println("收到REGISTER的请求");
                doRegister(request,requestEvent);
                break;
            case Request.MESSAGE:
                System.out.println("收到MESSAGE的请求");
                doMssage(request,requestEvent);
                break;
            case Request.SUBSCRIBE:
                System.out.println("收到SUBSCRIBE的请求");
                dosubscribe(request,requestEvent);
                break;
            case Request.ACK:
                System.out.println("收到ACK的请求");
                break;
            case Request.BYE:
                System.out.println("收到BYE的请求");
                break;
            case Request.CANCEL:
                System.out.println("收到CANCEL的请求");
                break;
            case Request.NOTIFY:
                System.out.println("收到NOTIFY的请求");
                doNtify(requestEvent, request);
                break;
            default:
                System.out.println("不处理的requestMethod："+request.getMethod().toUpperCase());
        }
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        if(null == response) {
            System.out.println("response is null.");
            return ;
        }
        System.out.println("通知端 收到的Response is :"+response);
//        ClientTransaction clientTransaction = responseEvent.getClientTransaction();
//        Request request = clientTransaction.getRequest();
//        System.out.println("收到的Response is :"+request);

//        if(response.getStatusCode() == Response.TRYING) {
//            System.out.println("收到的response is 100 TRYING");
//            return ;
//        }
//        switch(request.getMethod().toUpperCase()) {
//            case Request.INVITE:
//                System.out.println("收到INVITE的响应");
//                break;
//            case Request.BYE:
//                System.out.println("收到BYE的响应");
//                break;
//            case Request.CANCEL:
//                System.out.println("收到CANCEL的响应");
//                break;
//            default:
//                System.out.println("不处理的response的请求类型："+request.getMethod().toUpperCase());
//        }
        WWWAuthenticateHeader wwwHeader = (WWWAuthenticateHeader) response.getHeader(WWWAuthenticateHeader.NAME);
        if(null != wwwHeader) {
            String realm = wwwHeader.getRealm();
            String nonce = wwwHeader.getNonce();
            String A1 = MD5Utils.md5("Tom:" + realm + ":12345678","");
            String A2 = MD5Utils.md5("REGISTER:sip:servername@172.31.104.60:5060","");
            String resStr = MD5Utils.md5(A1 + ":" + nonce + ":" + A2,"");

            try {
                //requestURI
                SipURI requestSipURI = addressFactory.createSipURI("gov.nist","172.31.104.60:5069");
                requestSipURI.setTransportParam("udp");
                //from
                SipURI fromSipURI = addressFactory.createSipURI("Tom", "172.31.104.60:5070");
                Address fromAddress = addressFactory.createAddress(fromSipURI);
                fromAddress.setDisplayName("a");
                FromHeader fromHeader = headerFactory.createFromHeader(fromAddress,"mytag2");
                //to
                SipURI toSipURI = addressFactory.createSipURI("Tom", "172.31.104.60:5069");
                Address toAddress = addressFactory.createAddress(toSipURI);
                toAddress.setDisplayName("b");
                ToHeader toHeader = headerFactory.createToHeader(toAddress,null);
                //via
                ViaHeader viaHeader = headerFactory.createViaHeader("172.31.104.60", 5070, "udp", "branchingbranching");
                List<ViaHeader> viaHeaderList = new ArrayList<>();
                viaHeaderList.add(viaHeader);
                //callid,cseq,maxforwards
                CallIdHeader callIdHeader = sipProvider.getNewCallId();
                CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(2L, Request.REGISTER);
                MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
                //
                Request request = messageFactory.createRequest(requestSipURI, Request.REGISTER, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
                //contant
                SipURI contantURI = addressFactory.createSipURI("Tom", "172.31.104.600:5070");
                contantURI.setPort(5070);
                Address  contantAddress = addressFactory.createAddress(contantURI);
                contantAddress.setDisplayName("abc");
                ContactHeader contactHeader = headerFactory.createContactHeader(contantAddress);
                request.addHeader(contactHeader);
                //expires
                ExpiresHeader expiresHeader = headerFactory.createExpiresHeader(3600);
                request.addHeader(expiresHeader);

                ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("text","plain");
                request.setContent("",contentTypeHeader);

                AuthorizationHeader aHeader = headerFactory.createAuthorizationHeader("Degist");
                aHeader.setUsername("Tom");
                aHeader.setRealm(realm);
                aHeader.setNonce(nonce);
                aHeader.setURI(fromSipURI);
                aHeader.setResponse(resStr);
                aHeader.setAlgorithm("MD5");
                request.addHeader(aHeader);

                System.out.println(request);
                sipProvider.sendRequest(request);
            } catch (ParseException | InvalidArgumentException | SipException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void processIOException(IOExceptionEvent exceptionEvent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        // TODO Auto-generated method stub
    }

    private void doRegister(Request request, RequestEvent requestEvent) {
        if(null == request && null == requestEvent) {
            System.out.println("无法处理REGISTER请求，request="+request+",event="+requestEvent);
            return ;
        }
        ServerTransaction serverTransactionId = requestEvent.getServerTransaction();
        try {
            Response response = null;

            ToHeader toHead = (ToHeader) request.getHeader(ToHeader.NAME);
            Address toAddress = toHead.getAddress();
            URI toURI = toAddress.getURI();
            SipURI sipURI_to = (SipURI) toURI;
            String toUserId = sipURI_to.getUser();
            System.out.println("注册的toUserId是"+toUserId);

            ContactHeader contactHeader = (ContactHeader) request.getHeader(ContactHeader.NAME);
            Address contactAddr = contactHeader.getAddress();
            URI contactURI = contactAddr.getURI();

            System.out.println("注册 from: " + toURI + " request str: " + contactURI);
            if(null == toUserId || "".equals(toUserId)) {
                System.out.println("无法识别的userId，不处理。");
                return ;
            }
            int expires = request.getExpires().getExpires();
            // 如果expires不等于0,则为注册，否则为注销。
            if (expires != 0 && contactHeader.getExpires() != 0) {//注册
                if(registedContactURI.containsKey(toUserId)) {//已经注册了
                    System.out.println("已经注册过了 user=" + toURI);
                }else {//不是注册成功状态
                    if(registingId.contains(toUserId)) {//是第二次注册
                        System.out.println("第二次注册 register user=" + toURI);
                        // 验证AuthorizationHeader摘要认证信息
                        AuthorizationHeader authorizationHeader = (AuthorizationHeader) request.getHeader(AuthorizationHeader.NAME);
                        boolean authorizationResult = false;
                        if(null != authorizationHeader) {//验证
                            String username = authorizationHeader.getUsername();
                            String realm = authorizationHeader.getRealm();
                            String nonce = authorizationHeader.getNonce();
                            URI uri = authorizationHeader.getURI();
                            String res = authorizationHeader.getResponse();
                            String algorithm = authorizationHeader.getAlgorithm();
                            System.out.println("Authorization信息：username="+username+",realm="+realm+",nonce="+nonce+",uri="+uri+",response="+res+",algorithm="+algorithm);
                            if(null==username || null==realm || null==nonce || null==uri || null==res || null==algorithm) {
                                System.out.println("Authorization信息不全，无法认证。");
                            }else {
                                // 比较Authorization信息正确性
                                String A1 = MD5Utils.md5(username + ":" + realm + ":12345678","");
                                String A2 = MD5Utils.md5("REGISTER:sip:tang@172.31.104.600:5060","");
                                String resStr = MD5Utils.md5(A1 + ":" + nonce + ":" + A2,"");
                                if(resStr.equals(res)) {
                                    //注册成功，标记true
                                    authorizationResult = true;
                                }
                            }
                        }
                        registingId.remove(toUserId);//不管第二次是否成功都移除，失败要从头再来
                        // 验证成功加入成功注册列表，失败不加入
                        if(authorizationResult) {//注册成功
                            System.out.println("注册成功？");
                            registedContactURI.put(toUserId, contactURI);
                            //返回成功
                            response = messageFactory.createResponse(Response.OK, request);
                            DateHeader dateHeader = headerFactory.createDateHeader(Calendar.getInstance());
                            response.addHeader(dateHeader);
                            System.out.println("返回注册结果 response是\n" + response.toString());

                            if (serverTransactionId == null) {
                                serverTransactionId = sipProvider.getNewServerTransaction(request);
                                serverTransactionId.sendResponse(response);
                                // serverTransactionId.terminate();
//								System.out.println("register serverTransaction: " + serverTransactionId);
                            } else {
                                System.out.println("processRequest serverTransactionId is null.");
                            }
                        }else {//注册失败
                            System.out.println("注册失败？");
                            //返回失败
                            response = messageFactory.createResponse(Response.FORBIDDEN, request);
                            System.out.println("返回注册结果 response是\n" + response.toString());

                            if (serverTransactionId == null) {
                                serverTransactionId = sipProvider.getNewServerTransaction(request);
                                serverTransactionId.sendResponse(response);
                            } else {
                                System.out.println("注册请求 serverTransactionId is null.");
                            }
                        }
                    }else {//是第一次注册
                        System.out.println("首次注册 user=" + toURI);
                        // 加入registing列表
                        registingId.add(toUserId);
                        //发送响应
                        response = messageFactory.createResponse(Response.UNAUTHORIZED, request);
                        String realm = "zectec";
                        CallIdHeader callIdHeader = (CallIdHeader) request.getHeader(CallIdHeader.NAME);
                        String callId = callIdHeader.getCallId();
                        String nonce = MD5Utils.md5(callId + toUserId,"");
                        WWWAuthenticateHeader wwwAuthenticateHeader = headerFactory.createWWWAuthenticateHeader("Digest realm=\""+realm+"\",nonce=\""+nonce+"\"");
                        response.setHeader(wwwAuthenticateHeader);
                        System.out.println("返回注册结果 response是\n" + response.toString());

                        if (serverTransactionId == null) {
                            serverTransactionId = sipProvider.getNewServerTransaction(request);
                            serverTransactionId.sendResponse(response);
                            // serverTransactionId.terminate();
//							System.out.println("register serverTransaction: " + serverTransactionId);
                        } else {
                            System.out.println("注册请求 serverTransactionId is null.");
                        }

                    }
                }
            } else {//注销
                System.out.println("注销 user=" + toURI);
                //发送ok响应
                response = messageFactory.createResponse(Response.OK, request);
                System.out.println("返回注销结果 response  : " + response.toString());
                if (serverTransactionId == null) {
                    serverTransactionId = sipProvider.getNewServerTransaction(request);
                    serverTransactionId.sendResponse(response);
                } else {
                    System.out.println("注销请求 serverTransactionId is null.");
                }
                //移除
                registingId.remove(toUserId);
                registedContactURI.remove(toUserId);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    private void dosubscribe(Request request,RequestEvent requestEvent) {
        String name = "";
        String ipport = "";
        if (request.getHeader(ContactHeader.NAME) != null) {//获取平台id  平台地址
            ContactHeader contactHeader = (ContactHeader) request.getHeader(ContactHeader.NAME);
            Address contactAddr = contactHeader.getAddress();
            URI contactURI = contactAddr.getURI();
            name = contactURI.toString().split("@")[0].split(":")[1];
            ipport = contactURI.toString().split("@")[1];
        } else {
            ViaHeader via = (ViaHeader) request.getHeader(ViaHeader.NAME);
            ipport = via.getHost() + ":" + via.getPort();
            From fromHeader = (From) request.getHeader(FromHeader.NAME);
            name = fromHeader.getUserAtHostPort().split("@")[0];
        }
        //解析返回的xml
        Document document = null;
        byte[] bytes = request.getRawContent();
        try {
            document = DocumentHelper.parseText(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        Element element = rootElement.element("Query");
        String name1 = rootElement.getName();
        System.out.println(name1);
        if (element==null){
            System.out.println("这是个Query命令");
        }
        String cmdType = rootElement.element("CmdType").getStringValue().trim();
        String sn = rootElement.element("SN").getStringValue().trim();
        String deviceID = rootElement.element("DeviceID").getStringValue().trim();

        String message = "<?xml version=\"1.0\"?><Response><CmdType>"+cmdType+"</CmdType><SN>"+sn+"</SN><DeviceID>"+deviceID+"</DeviceID><Result>OK</Result></Response>";

        //返回成功
        ServerTransaction serverTransactionId = requestEvent.getServerTransaction();
        Response response = null;
        try {
            response = messageFactory.createResponse(Response.OK, request);
            ExpiresHeader expiresHeader = request.getExpires();
            response.addHeader(expiresHeader);
            EventHeader eventHeader = headerFactory.createEventHeader("presence");
            response.addHeader(eventHeader);
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application","MANSCDP+XML");
            response.setContent(message,contentTypeHeader);
            System.out.println(response);

            if (serverTransactionId == null) {
                serverTransactionId = sipProvider.getNewServerTransaction(request);
                serverTransactionId.sendResponse(response);
            } else {
                sipProvider.sendResponse(response);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TransactionAlreadyExistsException e) {
            e.printStackTrace();
        } catch (TransactionUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
        CallIdHeader callIdHeader = (CallIdHeader) request.getHeader(CallIdHeader.NAME);
        String callId = callIdHeader.getCallId();
        FromHeader fromHeader = (FromHeader)request.getHeader(FromHeader.NAME);
        String tag = fromHeader.getTag();
        String message1 = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n" +
                "\n" +
                "<Notify>\n" +
                "<CmdType>Catalog</CmdType>\n" +
                "<SN>2</SN>\n" +
                "<DeviceID>4129</DeviceID>\n" +
                "<SumNum>1</SumNum>\n" +
                "<DeviceList Num=\"1\">\n" +
                "<Item>\n" +
                "<DeviceID>41010100001320029003</DeviceID>\n" +
                "<Event>OFF</Event>\n" +
                "</Item>\n" +
                "</DeviceList>\n" +
                "</Notify>";
        jainSipServer.sendNotify(name,ipport,message1,callId,tag);
    }

    private void doMssage(Request request,RequestEvent requestEvent) {
        String name = "";
        String ipport = "";
        if (request.getHeader(ContactHeader.NAME) != null) {//获取平台id  平台地址
            ContactHeader contactHeader = (ContactHeader) request.getHeader(ContactHeader.NAME);
            Address contactAddr = contactHeader.getAddress();
            URI contactURI = contactAddr.getURI();
            name = contactURI.toString().split("@")[0].split(":")[1];
            ipport = contactURI.toString().split("@")[1];
        } else {
            ViaHeader via = (ViaHeader) request.getHeader(ViaHeader.NAME);
            ipport = via.getHost() + ":" + via.getPort();
            From fromHeader = (From) request.getHeader(FromHeader.NAME);
            name = fromHeader.getUserAtHostPort().split("@")[0];
        }
        //解析返回的xml
        Document document = null;
        byte[] bytes = request.getRawContent();
        try {
            document = DocumentHelper.parseText(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();

        String message = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n" +
                "\n" +
                "<Response>\n" +
                "<CmdType>Catalog</CmdType>\n" +
                "<SN>52876502</SN>\n" +
                "<DeviceID>41010500002000000001</DeviceID>\n" +
                "<SumNum>1391</SumNum>\n" +
                "<DeviceList Num=\"1\">\n" +
                "<Item>\n" +
                "<DeviceID>30</DeviceID>\n" +
                "<Name>30省管电厂室外监控</Name>\n" +
                "</Item>\n" +
                "</DeviceList>\n" +
                "</Response>";
        //返回成功
        ServerTransaction serverTransactionId = requestEvent.getServerTransaction();
        Response response = null;
        try {
            response = messageFactory.createResponse(Response.OK, request);

            if (serverTransactionId == null) {
                serverTransactionId = sipProvider.getNewServerTransaction(request);
                serverTransactionId.sendResponse(response);
            } else {
                sipProvider.sendResponse(response);
            }
            jainSipServer.sendMessage(name,ipport,message);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TransactionAlreadyExistsException e) {
            e.printStackTrace();
        } catch (TransactionUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    private void doNtify(RequestEvent requestEvent, Request request) {
        //解析返回的xml
        Document document = null;
        byte[] bytes = request.getRawContent();
        try {
            document = DocumentHelper.parseText(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        String cmdType = rootElement.element("CmdType").getStringValue().trim();
        String sn = rootElement.element("SN").getStringValue().trim();
        String deviceID = rootElement.element("DeviceID").getStringValue().trim();

        String message = "<?xml version=\"1.0\"?><Response><CmdType>"+cmdType+"</CmdType><SN>"+sn+"</SN><DeviceID>"+deviceID+"</DeviceID><Result>OK</Result></Response>";
        //返回成功

        ServerTransaction serverTransactionId = requestEvent.getServerTransaction();
        Response response = null;
        try {
            response = messageFactory.createResponse(Response.OK, request);
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application","MANSCDP+XML");
            response.setContent(message,contentTypeHeader);
            System.out.println(response);
            if (serverTransactionId == null) {
                serverTransactionId = sipProvider.getNewServerTransaction(request);
                serverTransactionId.sendResponse(response);
            } else {
                sipProvider.sendResponse(response);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TransactionAlreadyExistsException e) {
            e.printStackTrace();
        } catch (TransactionUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}