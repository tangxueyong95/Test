package com;

import java.text.ParseException;
import java.util.*;

import javax.sip.*;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

import com.ServerListener;
import gov.nist.javax.sip.header.From;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author XWF
 *
 */
public class JainSipServer{
    SipStack sipStack = null;
    HeaderFactory headerFactory = null;
    AddressFactory addressFactory = null;
    MessageFactory messageFactory = null;
    SipProvider sipProvider = null;
    private Dialog dialog;

    static String ip = "192.168.10.220";
    static int port = 5070;
    static String uname="tang";

    public static void main(String[] args) {
        JainSipServer test = new JainSipServer();
        test.init();
        String message1 = "<?xml version=\"1.0\"?><Notify><CmdType>Alarm</CmdType><SN>17430</SN><DeviceID>34020000001110000001</DeviceID><StartAlarmPriority>1</StartAlarmPriority><EndAlarmPriority>4</EndAlarmPriority><AlarmMethod>0</AlarmMethod><StartTime>2010-11-11T00:00:00</StartTime><EndTime>2010-12-11T00:00:00</EndTime></Notify>";
        String message = "<?xml version=\"1.0\"?><Query><CmdType>Catalog</CmdType><SN>17430</SN><DeviceID>>64010000001110000001</DeviceID><StartTime>2010-11-11T00:00:00</StartTime><EndTime>2010-12-11T00:00:00</EndTime></Query>";
//        test.sendSubscribe("Tom","192.168.10.21:5060",message);
    }
    public void init() {
        Properties prop = new Properties();
        prop.setProperty("javax.sip.STACK_NAME", "teststackname");
//		prop.setProperty("javax.sip.IP_ADDRESS", "127.0.0.1");
//		prop.setProperty("javax.sip.OUTBOUND_PROXY", "127.0.0.1:8888/UDP");
        // You need 16 for logging traces. 32 for debug + traces.
        // Your code will limp at 32 but it is best for debugging.
//        prop.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "32");
//        prop.setProperty("gov.nist.javax.sip.DEBUG_LOG", "siptestdebug.txt");
//        prop.setProperty("gov.nist.javax.sip.SERVER_LOG", "siptestlog.txt");

        SipFactory sipFactory = SipFactory.getInstance();
        sipFactory.setPathName("gov.nist");
        try {
            sipStack = sipFactory.createSipStack(prop);
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
        }

        try {
            headerFactory = sipFactory.createHeaderFactory();
            addressFactory = sipFactory.createAddressFactory();
            messageFactory = sipFactory.createMessageFactory();
            ListeningPoint listeningPoint = sipStack.createListeningPoint(ip, port, "udp");
            sipProvider = sipStack.createSipProvider(listeningPoint);
            ServerListener listener = new ServerListener(addressFactory,headerFactory,messageFactory,sipProvider,this);
            sipProvider.addSipListener(listener);
            System.out.println("服务启动完成。。。");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
        } catch (ObjectInUseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (TransportNotSupportedException e) {
            e.printStackTrace();
        }

    }


    
    public void sendMessage(String toUserName,String toIpPort,String message){
        try {
            //requestURI
            SipURI requestSipURI = addressFactory.createSipURI(toUserName,toIpPort);
            requestSipURI.setTransportParam("udp");
            //from
            SipURI fromSipURI = addressFactory.createSipURI(uname, ip+":"+port);
            Address fromAddress = addressFactory.createAddress(fromSipURI);
            fromAddress.setDisplayName(uname);
            FromHeader fromHeader = headerFactory.createFromHeader(fromAddress,"mytag");
            //to
            SipURI toSipURI = addressFactory.createSipURI(toUserName, toIpPort);
            Address toAddress = addressFactory.createAddress(toSipURI);
            toAddress.setDisplayName(toUserName);
            ToHeader toHeader = headerFactory.createToHeader(toAddress,null);
            //via
            ViaHeader viaHeader = headerFactory.createViaHeader(ip, port, "udp", "branchingbranching");
            List<ViaHeader> viaHeaderList = new ArrayList<>();
            viaHeaderList.add(viaHeader);
            //callid
            CallIdHeader callIdHeader = sipProvider.getNewCallId();
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.MESSAGE);
            MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
            //
            Request request = messageFactory.createRequest(requestSipURI, Request.MESSAGE, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
            //contact
            SipURI contactURI = addressFactory.createSipURI(uname, ip+":"+port);
            contactURI.setPort(port);
            Address  contactAddress = addressFactory.createAddress(contactURI);
            contactAddress.setDisplayName(uname);
            ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
            request.addHeader(contactHeader);
            //expires
            ExpiresHeader expiresHeader = headerFactory.createExpiresHeader(3600);
            request.addHeader(expiresHeader);
            //message 消息
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application","MANSCDP+XML");
            request.setContent(message,contentTypeHeader);

            sipProvider.sendRequest(request);
//            ClientTransaction ctrans = sipProvider.getNewClientTransaction(request);
//            ctrans.sendRequest();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
    public void sendNotify(String toUserName,String toIpPort,String message,String callId,String tag){
        try {
            //requestURI 请求地址
            SipURI requestSipURI = addressFactory.createSipURI(toUserName,toIpPort);
//			requestSipURI.setTransportParam("udp");
            //via 请求头
            ViaHeader viaHeader = headerFactory.createViaHeader(ip, port, "udp", "branchingbranching");
            List<ViaHeader> viaHeaderList = new ArrayList<>();
            viaHeaderList.add(viaHeader);
            //from 请求发起人
            SipURI fromSipURI = addressFactory.createSipURI(uname, ip+":"+port);
            Address fromAddress = addressFactory.createAddress(fromSipURI);
            fromAddress.setDisplayName(uname);
            FromHeader fromHeader = headerFactory.createFromHeader(fromAddress,"mytag");
            //to 接收方
            SipURI toSipURI = addressFactory.createSipURI(toUserName, toIpPort);
            Address toAddress = addressFactory.createAddress(toSipURI);
            toAddress.setDisplayName(toUserName);
            ToHeader toHeader = headerFactory.createToHeader(toAddress,tag);
            //callid 唯一标识
            CallIdHeader callIdHeader = headerFactory.createCallIdHeader(callId);
            //Cseq
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.NOTIFY);
            //Max-Forwards
            MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
            //
            Request request = messageFactory.createRequest(requestSipURI, Request.NOTIFY, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
            SubscriptionStateHeader subscriptionStateHeader = headerFactory.createSubscriptionStateHeader("active");
            subscriptionStateHeader.setExpires(90);
            request.addHeader(subscriptionStateHeader);

            //contact
            SipURI contactURI = addressFactory.createSipURI(uname, ip+":"+port);
            contactURI.setPort(port);
            Address  contactAddress = addressFactory.createAddress(contactURI);
            contactAddress.setDisplayName(uname);
            ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
            request.addHeader(contactHeader);

            EventHeader eventHeader = headerFactory.createEventHeader("presence");
            request.addHeader(eventHeader);
//			//message 消息
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application","MANSCDP+XML");
            request.setContent(message,contentTypeHeader);
            System.out.println(request);

            sipProvider.sendRequest(request);
//            ClientTransaction trans = sipProvider.getNewClientTransaction(request);
//			dialog = trans.getDialog();
//			trans.sendRequest();
//			//
//			request = dialog.createRequest(Request.MESSAGE);
//			request.setHeader(contactHeader);
//			request.setContent(message, contentTypeHeader);
//			ClientTransaction ctrans = sipProvider.getNewClientTransaction(request);
//			ctrans.sendRequest();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    public void sendSubscribe(String toUserName,String toIpPort,String message){
        try {
            //requestURI 请求地址
            SipURI requestSipURI = addressFactory.createSipURI(toUserName,toIpPort);
//			requestSipURI.setTransportParam("udp");
            //via 请求头
            ViaHeader viaHeader = headerFactory.createViaHeader(ip, port, "udp", "branchingbranching");
            List<ViaHeader> viaHeaderList = new ArrayList<>();
            viaHeaderList.add(viaHeader);
            //from 请求发起人
            SipURI fromSipURI = addressFactory.createSipURI(uname, ip+":"+port);
            Address fromAddress = addressFactory.createAddress(fromSipURI);
            fromAddress.setDisplayName(uname);
            FromHeader fromHeader = headerFactory.createFromHeader(fromAddress,"mytag");
            //to 接收方
            SipURI toSipURI = addressFactory.createSipURI(toUserName, toIpPort);
            Address toAddress = addressFactory.createAddress(toSipURI);
            toAddress.setDisplayName(toUserName);
            ToHeader toHeader = headerFactory.createToHeader(toAddress,null);
            //callid 唯一标识
            CallIdHeader callIdHeader = headerFactory.createCallIdHeader("17250");
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.SUBSCRIBE);
            //Max-Forwards
            MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
            //
            Request request = messageFactory.createRequest(requestSipURI, Request.SUBSCRIBE, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
            //contact 接收请求后回复地址
            SipURI contactURI = addressFactory.createSipURI(uname, ip+":"+port);
            contactURI.setPort(port);
            Address  contactAddress = addressFactory.createAddress(contactURI);
            contactAddress.setDisplayName(uname);
            ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
            request.addHeader(contactHeader);
            //expires 有效时间
            ExpiresHeader expiresHeader = headerFactory.createExpiresHeader(3600);
            request.addHeader(expiresHeader);

            EventHeader eventHeader = headerFactory.createEventHeader("presence");
            request.addHeader(eventHeader);
//			//message 消息
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application","MANSCDP+XML");
            request.setContent(message,contentTypeHeader);
            System.out.println(request);
//			sipProvider.sendRequest(request);

            ClientTransaction ctrans = sipProvider.getNewClientTransaction(request);
            ctrans.sendRequest();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    public void sendRegist(){
        try {
            //requestURI
            SipURI requestSipURI = addressFactory.createSipURI("41010500002000000001","192.168.10.21:5069");
            requestSipURI.setTransportParam("udp");
            //from
            SipURI fromSipURI = addressFactory.createSipURI("Tom", "192.168.10.21:5070");
            Address fromAddress = addressFactory.createAddress(fromSipURI);
            fromAddress.setDisplayName("a");
            FromHeader fromHeader = headerFactory.createFromHeader(fromAddress,"mytag2");
            //to
            SipURI toSipURI = addressFactory.createSipURI("Tom", "192.168.10.21:5069");
            Address toAddress = addressFactory.createAddress(toSipURI);
            toAddress.setDisplayName("b");
            ToHeader toHeader = headerFactory.createToHeader(toAddress,null);
            //via
            ViaHeader viaHeader = headerFactory.createViaHeader(ip, port, "udp", "branchingbranching");
            List<ViaHeader> viaHeaderList = new ArrayList<>();
            viaHeaderList.add(viaHeader);
            //callid,cseq,maxforwards
            CallIdHeader callIdHeader = sipProvider.getNewCallId();
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.REGISTER);
            MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
            //
            Request request = messageFactory.createRequest(requestSipURI, Request.REGISTER, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
            //contact
            SipURI contactURI = addressFactory.createSipURI("Tom", "192.168.10.21:5070");
            contactURI.setPort(port);
            Address  contactAddress = addressFactory.createAddress(contactURI);
            contactAddress.setDisplayName(uname);
            ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
            request.addHeader(contactHeader);
            //expires
            ExpiresHeader expiresHeader = headerFactory.createExpiresHeader(3600);
            request.addHeader(expiresHeader);

//			ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("text","plain");
//			request.setContent(message,contentTypeHeader);

            System.out.println(request);
            sipProvider.sendRequest(request);
//			//
//			ClientTransaction trans = sipProvider.getNewClientTransaction(request);
//			dialog = trans.getDialog();
//			trans.sendRequest();
//			//
//			request = dialog.createRequest(Request.MESSAGE);
//			request.setHeader(contactHeader);
//			request.setContent(message, contentTypeHeader);
//			ClientTransaction ctrans = sipProvider.getNewClientTransaction(request);
//			ctrans.sendRequest();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}