package com.github.knives.greenmail;

import static org.junit.Assert.*;


import java.util.Arrays;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class GreenMailServerTest {
	final private static Logger LOG = LoggerFactory.getLogger(GreenMailServerTest.class);
	
	@Test
	public void test() throws Exception {
		final ServerSetup SMTP = new ServerSetup(8025, "localhost", "smtp");
		final ServerSetup POP3 = new ServerSetup(8110, "localhost", "pop3");
		final ServerSetup IMAP = new ServerSetup(8143, "localhost", "imap");
		final ServerSetup[] config = {SMTP, POP3, IMAP};
		
		final GreenMail server = new GreenMail(config);
		
		server.start();
		
	    GreenMailUtil.sendTextEmail("to@localhost.com", "from@localhost.com", "hello green mail", "body", SMTP);
		
		final MimeMessage[] messages = server.getReceivedMessages();
		
		LOG.info("Received [{}] messages", messages.length);
		
		Arrays.stream(messages).forEach((MimeMessage message) -> {
			try {
				LOG.info("Message: [{}]", message.getSubject());
			} catch (MessagingException e) {}
		});
	}

}
