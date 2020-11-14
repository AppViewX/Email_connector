package com.appviewx.connector.email.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Flags.Flag;

public abstract class AbstractEmailReadService {

	protected boolean subjectMatcher(String msg, String patternStr) {
		if (msg != null) {
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(msg);
			return matcher.matches();
		}
		return false;
	}

	protected List<Map<String, String>> extractMail(Message[] messages) throws MessagingException, IOException {
		List<Map<String, String>> listOfMails = new ArrayList<>();

		for (int i = 0, n = messages.length; i < n; i++) {
			Map<String, String> tempMail = new HashMap<>();
			messages[i].setFlag(Flag.SEEN, true);
			Message message = messages[i];
			tempMail.put("Subject", message.getSubject());
			tempMail.put("From", message.getFrom()[0].toString());

			StringBuilder sb = new StringBuilder();
			writePart(message, sb);
			tempMail.put("Content", sb.toString());
			listOfMails.add(tempMail);
		}
		return listOfMails;
	}

	private StringBuilder writePart(Part p, StringBuilder sb) throws MessagingException, IOException {
		// check if the content is plain text
		if (p.isMimeType("text/plain")) {
			sb.append((String) p.getContent() + "\n");
		}
		// check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				writePart(mp.getBodyPart(i), sb);
			}
		}
		// check if the content is a nested message
		else if (p.isMimeType("message/rfc822")) {
			writePart((Part) p.getContent(), sb);
		}

		return sb;
	}

	abstract List<Map<String, String>> retrieveMail(String host, String user, String password, String patternStr);

}
