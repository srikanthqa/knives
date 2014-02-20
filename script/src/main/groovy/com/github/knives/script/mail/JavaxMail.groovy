package com.github.knives.script.mail;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class JavaxMail {
	public static void main(String[] args) throws Throwable {
        MimeMultipart multipart = new MimeMultipart("related");

        MimeBodyPart soapPart = new MimeBodyPart();

        soapPart.setContent("soap part", "text/xml");
        soapPart.setContentID("<mm7-deliver>");
        
        multipart.addBodyPart(soapPart);

        MimeBodyPart contentPart = new MimeBodyPart();
        contentPart.setContent("content part", "audio/midi");
        contentPart.setHeader("Content-ID", "<bball.mid>");

        multipart.addBodyPart(contentPart);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mess = new MimeMessage(session);
        mess.setContent(multipart);
        mess.saveChanges();

        mess.writeTo(System.out);
	}
}
