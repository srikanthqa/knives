package com.github.knives.script.mail;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataSource;

public class JavaxMail {
	public static void main(String[] args) {
		parseMM7SubmitRequestmultipart('multipart/related; type="text/xml"; boundary="----=_Part_1_3346521.1256243201592"')
	}

	public static void buildMM7SubmitRequestmultipart() {
		final MimeBodyPart soapPart = new MimeBodyPart();
		soapPart.setContent("soap part", "text/xml");
		soapPart.setContentID("<mm7-deliver>");

		final MimeBodyPart innerContentPart = new MimeBodyPart();
		innerContentPart.setContent("content part", "audio/midi");
		innerContentPart.setHeader("Content-ID", "<bball.mid>");
		
		final MimeMultipart innerMultipart = new MimeMultipart("related");
		innerMultipart.addBodyPart(innerContentPart);
		
		final MimeBodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(innerMultipart);

		final MimeMultipart outerMultipart = new MimeMultipart("related");
		outerMultipart.addBodyPart(soapPart);
		outerMultipart.addBodyPart(contentPart);
		
		writeToSystem(outerMultipart);
	}

	public static void buildMM7DeliverRequestMultipart() {
		final MimeBodyPart soapPart = new MimeBodyPart();
		soapPart.setContent("soap part", "text/xml");
		soapPart.setContentID("<mm7-deliver>");

		final MimeBodyPart contentPart = new MimeBodyPart();
		contentPart.setContent("content part", "audio/midi");
		contentPart.setHeader("Content-ID", "<bball.mid>");

		final MimeMultipart multipart = new MimeMultipart("related");
		writeToSystem(multipart);
	}

	public static String normalizeContentType(final String contentType) {
		return contentType.replaceAll( "\r", "" ).replaceAll( "\n", "" ).replaceAll( "\t", "" );
	}
	
	public static void parseMM7SubmitRequestmultipart(String type) {
		final DataSource dataSource = new ByteArrayDataSource(System.in, type);
		final MimeMultipart multipart = new MimeMultipart(dataSource);
		
		writeToSystem(multipart);
	}
	
	public static void writeToSystem(MimeMultipart multipart) {
		System.out.println("Content-Type: " + normalizeContentType(multipart.getContentType()));
		System.out.println();
		multipart.writeTo(System.out);
	}
}
