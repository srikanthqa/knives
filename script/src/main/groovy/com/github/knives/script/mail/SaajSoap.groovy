package com.github.knives.script.mail

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Text;

class SaajSoap {
	public static void main(String[] args) {
		//buildMM7Soap();
		parseMM7Soap();
	}
	
	public static void parseMM7Soap() {
		final MessageFactory messageFactory = MessageFactory.newInstance();
		final SOAPMessage soapMessage = messageFactory.createMessage(new MimeHeaders(), System.in);
		
		final SOAPHeader soapHeader = soapMessage.getSOAPHeader();
		soapHeader.getChildElements().each { element ->
			switch (element) {
				case Text:
					println(element.getValue());
					break;
				case SOAPElement:
					println(element.getNodeName())
				default:
					println (element.getClass());
				
			}
		}
		
		soapMessage.writeTo(System.out);
	}
	
	public static void buildMM7Soap() {
		final MessageFactory messageFactory = MessageFactory.newInstance();
		final SOAPMessage soapMessage = messageFactory.createMessage();
		final SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
		
		final SOAPHeader soapHeader = soapMessage.getSOAPHeader();
		soapHeader.addNamespaceDeclaration("mm7", "http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-3");
		
		final SOAPElement transactionID = soapHeader.addChildElement("TransactionID", "mm7");
		transactionID.setValue("transaction-id-testt");
		
		final SOAPBody soapBody = soapMessage.getSOAPBody();
		final SOAPElement submitReq = soapBody.addChildElement("SubmitReq","", "http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-3");
		submitReq.addChildElement("MM7Version").setValue("5.3.0");
		
		final SOAPElement senderIdentification = submitReq.addChildElement("SenderIdentification");
		senderIdentification.addChildElement("VASPID").setValue("YOUR_VASP");
		
		/*
		 <SubmitReq xmlns="http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-3">
		 <MM7Version>5.3.0</MM7Version>
		 <SenderIdentification>
		   <VASPID>YOUR_VASP</VASPID>
		   <VASID>MMS_SUBMIT</VASID>
		 </SenderIdentification>
		 <Recipients>
		   <To>
			 <Number>8883800000</Number>
		   </To>
		 </Recipients>
		 <ServiceCode>10851</ServiceCode>
		 <MessageClass>Personal</MessageClass>
		 <ExpiryDate>2009-12-31T23:59:59</ExpiryDate>
		 <DeliveryReport>false</DeliveryReport>
		 <Priority>Normal</Priority>
		 <Subject>alltel MM7 Test</Subject>
		 <ApplicID>44</ApplicID>
		 <Content allowAdaptations="false" href="cid:generic_content_id"/>
	   </SubmitReq>
		 */
		
		// Using AttachmentPart will result into MINE envelopment, but not nested.
		/*
		final AttachmentPart attachmentPart = soapMessage.createAttachmentPart();
		attachmentPart.setContent("hello world", "text/txt");
		soapMessage.addAttachmentPart(attachmentPart);
		*/
		
		soapMessage.writeTo(System.out);
	}
}

