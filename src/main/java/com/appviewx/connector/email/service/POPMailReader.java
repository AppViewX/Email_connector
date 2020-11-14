package com.appviewx.connector.email.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("POP-MAPPING-SERVICE")
public class POPMailReader extends AbstractEmailReadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(POPMailReader.class);

	private static final int POP_PORT = 995;

	@SuppressWarnings("serial")
	@Override
	public List<Map<String, String>> retrieveMail(String host, String user, String password, String patternStr) {
		try {
			LOGGER.info("Checking mail for WO status in POP ");
			// create properties field
			Properties properties = new Properties();
			properties.put("mail.pop3s.host", host);
			properties.put("mail.pop3s.port", POP_PORT);
			properties.put("mail.pop3s.starttls.enable", "true");
			properties.setProperty("mail.pop3s.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.store.protocol", "pop3s");
			properties.setProperty("mail.pop3s.auth", "true");
			properties.setProperty("mail.pop3s.ssl.trust", "*");
			properties.put("mail.pop3s.socketFactory", POP_PORT);
			properties.put("mail.pop3s.user", user);

			// Setup authentication, get session
			Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, POP_PORT, user, password);// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			SearchTerm term = new SearchTerm() {
				@Override
				public boolean match(Message msg) {
					try {
						if (subjectMatcher(msg.getSubject(), patternStr)) {
							return true;
						}
					} catch (MessagingException ex) {
						LOGGER.error("Error while reading mail in POP: {}", ex.getMessage());
					}
					return false;
				}
			};

			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message[] mssages = emailFolder.search(unseenFlagTerm);
			Message[] messages = emailFolder.search(term, mssages);
			return extractMail(messages);

		} catch (NoSuchProviderException e) {
			LOGGER.error("Unable to reach the mail provider :" + e.getMessage());
		} catch (MessagingException e) {
			LOGGER.error("Error while reading content in mail in POP :" + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Error while reading mail in POP :" + e.getMessage());
		}
		return null;
	}
}