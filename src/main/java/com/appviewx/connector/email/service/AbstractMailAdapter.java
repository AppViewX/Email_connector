package com.appviewx.connector.email.service;

import java.util.Base64;
import java.util.List;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appviewx.connector.email.ds.AppViewXDataSource;
import com.appviewx.connector.email.pojos.EmailConfig;
import com.appviewx.connector.email.pojos.EmailConstants;
import com.appviewx.connector.email.pojos.EmailTemplate;
import com.appviewx.connector.email.pojos.MailAttachment;

public abstract class AbstractMailAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMailAdapter.class);

	protected abstract boolean sendEmail(EmailTemplate emailTemplate, EmailConfig emailConfig)
			throws Exception;

	protected void processAttachments(final Multipart attachmentPart, EmailTemplate emailTemplate)
			throws MessagingException {

		if (emailTemplate.isMailAttachmentAvailable()) {
			StringBuilder attachmentType;
			StringBuilder fileName;
			MimeBodyPart individualAttachments;
			DataSource dataSource;
			byte []attachmentByteArray;

			for (MailAttachment attachment : emailTemplate.getMailAttachments()) {
				attachmentType = attachment.getAttachmentType();
				fileName = attachment.getFileName();

				if (attachment.getAttachmentBytes() != null && !attachment.getAttachmentBytes().trim().isEmpty()) {

					if (attachmentType == null || attachmentType.toString().isEmpty()) {
						attachmentType = EmailConstants.ATTACHMENT_TYPE_PDF;
					}

					attachmentByteArray = Base64.getDecoder().decode(attachment.getAttachmentBytes());
					dataSource = new AppViewXDataSource(attachmentByteArray, attachmentType.toString());
					LOGGER.debug("data source.... {}", dataSource);

					individualAttachments = new MimeBodyPart();
					individualAttachments.setDataHandler(new DataHandler(dataSource));

					if (fileName == null || fileName.toString().isEmpty()) {
						fileName = EmailConstants.AUDIT_REPORT_FILE_NAME;
					}
					individualAttachments.setFileName(fileName.toString());
					attachmentPart.addBodyPart(individualAttachments);
				}
			}
		}
	}

	protected InternetAddress[] frameRecipientsAddress(List<String> receipents) throws AddressException {
		if (receipents == null || receipents.isEmpty()) {
			return new InternetAddress[0];
		}
		InternetAddress[] inetAddress = new InternetAddress[receipents.size()];
		for (int index = 0; index < inetAddress.length; index++) {
			inetAddress[index] = new InternetAddress(receipents.get(index));
		}
		return inetAddress;
	}

	protected void setDefaultMailCapConfiguration() {
		final MailcapCommandMap mcap = new MailcapCommandMap();
		mcap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mcap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mcap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mcap.addMailcap(
				"multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed; x-java-fallback-entry=true");
		mcap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mcap);
		LOGGER.debug("Default mail cap configuration successfull");
	}
}
