package com.github.knives.javax.mail;

import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class JavaxMail {
	public static void main(String[] args) throws Exception {
		//parseMM7SubmitRequestmultipart('multipart/related; type="text/xml"; boundary="----=_Part_1_3346521.1256243201592"');
		buildMM7SubmitRequestmultipart();
		
		betterParseMM7SubmitRequestmultipart();
	}

	public static void buildMM7SubmitRequestmultipart() throws Exception {
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
		contentPart.setHeader("Content-Type", normalizeContentType(innerMultipart.getContentType()));
		contentPart.setContentID("<generic_content_id>");

		final MimeMultipart outerMultipart = new MimeMultipart("related");
		outerMultipart.addBodyPart(soapPart);
		outerMultipart.addBodyPart(contentPart);
		
		writeToSystem(outerMultipart);
	}

	public static void buildMM7DeliverRequestMultipart() throws Exception {
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
	
	public static void parseMM7SubmitRequestmultipart(String type) throws Exception {
		final DataSource dataSource = new ByteArrayDataSource(System.in, type);
		final MimeMultipart multipart = new MimeMultipart(dataSource);
		
		writeToSystem(multipart);
	}
	
	/**
	 * Parse MineBodyPart, because it is just MineMultipart wrapped by MineBodyPart.
	 *
	 * The difference is that MineBodyPart have Content-Type header, while
	 * MineMultipart does not
	 */
	public static void betterParseMM7SubmitRequestmultipart() throws Exception {
		final MimeBodyPart mineBodyPart = new MimeBodyPart(System.in);
		
		// Multipart.writeTo
		//mineBodyPart.getContent().writeTo(System.out);
		
		// MineBodyPart.writeTo
		//mineBodyPart.writeTo(System.out);
		
		final MimeMultipart multipart = (MimeMultipart)mineBodyPart.getContent();
		//System.out.println(mineBodyPart.getContent());
		//System.out.println(multipart.getCount());
		
		//final BodyPart bodyPart = multipart.getBodyPart(0);
		//System.out.println(new String(bodyPart.getInputStream().getBytes()));
		
		//System.out.println(multipart.getBodyPart(1));
		
		final MimeBodyPart attachmentPart = (MimeBodyPart)multipart.getBodyPart(1);
		
		final MimeMultipart attachmentMultiPart = (MimeMultipart)attachmentPart.getContent();
		
		//System.out.println(attachmentMultiPart.getCount());
		//attachmentMultiPart.writeTo(System.out);
		
		//System.out.println(attachmentMultiPart.getBodyPart(0));
		
		System.out.println(attachmentMultiPart.getBodyPart(0).getContent());
	}
	
	public static void writeToSystem(MimeMultipart multipart) throws Exception {
		System.out.println("Content-Type: " + normalizeContentType(multipart.getContentType()));
		System.out.println();
		multipart.writeTo(System.out);
	}
}
