package com;

import gov.nist.javax.sip.header.From;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
 
public class JainSipClient {

	private SipStack sipStack;

	private SipFactory sipFactory;

	private AddressFactory addressFactory;

	private HeaderFactory headerFactory;

	private MessageFactory messageFactory;

	private SipProvider sipProvider;

	private Dialog dialog;

	static String ip = "192.168.10.21";
	static int port = 5069;
	static String uname="tang";

	public static void main(String[] args) {
		JainSipClient client = new JainSipClient();
		client.init();
		//Subscribe
		String message = "<?xml version=\"1.0\"?><Query><CmdType>Catalog</CmdType><SN>17430</SN><DeviceID>>64010000001110000001</DeviceID><StartTime>2010-11-11T00:00:00</StartTime><EndTime>2010-12-11T00:00:00</EndTime></Query>";
		//Notify
		String message1 = "<?xml version=\"1.0\"?><Notify><CmdType>Alarm</CmdType><SN>17430</SN><DeviceID>34020000001110000001</DeviceID><StartAlarmPriority>1</StartAlarmPriority><EndAlarmPriority>4</EndAlarmPriority><AlarmMethod>0</AlarmMethod><StartTime>2010-11-11T00:00:00</StartTime><EndTime>2010-12-11T00:00:00</EndTime></Notify>";
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        client.sendSubscribe("Tom","192.168.10.21:5060",message);
//		String message1 = "<?xml version=\"1.0\"?><Notify><CmdType>Alarm</CmdType><SN>17430</SN><DeviceID>34020000001110000001</DeviceID><StartAlarmPriority>1</StartAlarmPriority><EndAlarmPriority>4</EndAlarmPriority><AlarmMethod>0</AlarmMethod><StartTime>2010-11-11T00:00:00</StartTime><EndTime>2010-12-11T00:00:00</EndTime></Notify>";
//		client.sendNotify("Tom","172.31.104.60:5070",message1);
//		client.sendMessage("Tom","172.31.104.60:5060",message);
	}

	public void init(){
		try {
			Properties prop = new Properties();
			prop.setProperty("javax.sip.STACK_NAME", "teststackname");
//			prop.setProperty("javax.sip.IP_ADDRESS", ip);
//			prop.setProperty("javax.sip.OUTBOUND_PROXY", "127.0.0.1:8889/UDP");
//			prop.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "32");
//			prop.setProperty("gov.nist.javax.sip.DEBUG_LOG", "sipclientdebug.txt");
//			prop.setProperty("gov.nist.javax.sip.SERVER_LOG", "sipclientlog.txt");

			sipFactory = SipFactory.getInstance();
			sipFactory.setPathName("gov.nist");
			sipStack = sipFactory.createSipStack(prop);
			headerFactory = sipFactory.createHeaderFactory();
			addressFactory = sipFactory.createAddressFactory();
			messageFactory = sipFactory.createMessageFactory();
			ListeningPoint listeningpoint_udp =sipStack.createListeningPoint(ip,port, "udp");
			sipProvider = sipStack.createSipProvider(listeningpoint_udp);
			ClientListener listener = new ClientListener(addressFactory,headerFactory,messageFactory,sipProvider,this);
			sipProvider.addSipListener(listener);

			System.out.println("客户端初始化完成。。。");
		} catch (PeerUnavailableException | TransportNotSupportedException | ObjectInUseException
				| InvalidArgumentException | TooManyListenersException e) {
			e.printStackTrace();
		}
	}
	/***
	 *
	 * @param toUserName 接收方帐号
	 * @param toIpPort 接收方ip和端口
	 * @param message 消息
	 */
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
	public void sendNotify(String toUserName,String toIpPort,String message){
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
			CallIdHeader callIdHeader = sipProvider.getNewCallId();
			//Cseq
			CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.NOTIFY);
			//Max-Forwards
			MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
			//
			Request request = messageFactory.createRequest(requestSipURI, Request.NOTIFY, callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
			SubscriptionStateHeader subscriptionStateHeader = headerFactory.createSubscriptionStateHeader("active;expires=90;retry-after=0");
			request.addHeader(subscriptionStateHeader);

			EventHeader eventHeader = headerFactory.createEventHeader("presence");
			request.addHeader(eventHeader);
//			//message 消息
			ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application","MANSCDP+XML");
			request.setContent(message,contentTypeHeader);
			System.out.println(request);

			sipProvider.sendRequest(request);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (SipException e) {
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
