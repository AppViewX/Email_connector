package com.appviewx.connector.email.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("IMAP-MAPPING-SERVICE")
public class IMAPMailReader extends AbstractEmailReadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IMAPMailReader.class);

	private static final int IMAP_PORT = 993;

	@SuppressWarnings("serial")
	@Override
	public List<Map<String, String>> retrieveMail(String host, String user, String password, String patternStr) {
		try {
			LOGGER.info("Checking mail for WO status in IMAP ");
			Properties props = new Properties();
			props.put("mail.imaps.host", host);
			props.put("mail.imaps.user", user);
			props.put("mail.imaps.socketFactory", IMAP_PORT);
			props.put("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.imaps.port", IMAP_PORT);
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
			try {
				Store store = session.getStore("imaps");
				store.connect(host, user, password);
				try {
					Folder fldr = store.getFolder("Inbox");
					fldr.open(Folder.READ_WRITE);
					// retrieve the messages from the folder in an array and print it

					Flags seen = new Flags(Flags.Flag.SEEN);
					FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
					Message[] mssag = fldr.search(unseenFlagTerm);

					SearchTerm term = new SearchTerm() {
						@Override
						public boolean match(Message msg) {
							try {
								if (subjectMatcher(msg.getSubject(), patternStr)) {
									return true;
								}
							} catch (MessagingException ex) {
								LOGGER.error("Error while reading mail in IMAP :" + ex.getMessage());
							}
							return false;
						}
					};

					Message[] messages = fldr.search(term, mssag);
					List<Map<String, String>> extractMail = extractMail(messages);
					return extractMail;
				} catch (Exception e) {
					LOGGER.error("Error while connecting to inbox of mail in IMAP: {}", e.getMessage());
				}finally {
					store.close();
				}
			} catch (Exception exc) {
				LOGGER.error("Error while reading mail in IMAP: {}", exc.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error("Error while reading mail in IMAP :" + e.getMessage());
		}
		return null;
	}

}