package com.appviewx.connector.email.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.appviewx.connector.email.pojos.EmailConfig;
import com.appviewx.connector.email.pojos.EmailConstants;
import com.appviewx.connector.email.pojos.EmailTemplate;

@Component("NO-AUTH-SERVICE")
public class NoAuthEmailServiceAdapterImpl extends AbstractMailAdapter {

	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NoAuthEmailServiceAdapterImpl.class);

	/**
	 * Send email.
	 *
	 * @param emailTemplate
	 *            the email template
	 * @throws AvxServiceException .
	 * @return boolean
	 */
	@Override
	protected boolean sendEmail(EmailTemplate emailTemplate, EmailConfig emailConfig) throws Exception {

		final Object content = emailTemplate.getEmailTemplateContent();
		Message simpleMessage = null;
		InternetAddress fromAddress = null;

		final Properties props = new Properties();
		props.put(EmailConstants.MAIL_SMTP_HOST, emailConfig.getHost());
		props.put(EmailConstants.MAIL_SMTP_PORT, emailConfig.getPort());
		props.put(EmailConstants.MAIL_SMTP_CONN_TIMEOUT, String.valueOf(emailConfig.getConnectionTimeout()));
		props.put(EmailConstants.MAIL_SMTP_TIMEOUT, String.valueOf(emailConfig.getReadIOTimeout()));
		if(emailConfig.isTlsEnabled()) {
			props.put(EmailConstants.MAIL_TLS_ENABLED, EmailConstants.AUTH);
			LOGGER.info("SMTP- Is TLS enabled {}",String.valueOf(emailConfig.isTlsEnabled()));
		}

		try {
			setDefaultMailCapConfiguration();
			simpleMessage = new MimeMessage(Session.getInstance(props));

			fromAddress = new InternetAddress(emailConfig.getSendAddress());
			simpleMessage.setFrom(fromAddress);
			simpleMessage.setSubject(emailTemplate.getSubject());
			simpleMessage.addRecipients(RecipientType.TO, frameRecipientsAddress(emailTemplate.getEmailRecipients()));
			simpleMessage.addRecipients(RecipientType.CC, frameRecipientsAddress(emailTemplate.getEmailCCRecipients()));
			simpleMessage.addRecipients(RecipientType.BCC,
					frameRecipientsAddress(emailTemplate.getEmailBCCRecipients()));
			simpleMessage.setSentDate(new Date());

			// setup message body
			final BodyPart messageTextPart = new MimeBodyPart();
			if (emailTemplate.isHtml()) {
				messageTextPart.setContent(String.valueOf(content), "text/html; charset=utf-8");
			} else if (content instanceof MimeMultipart) {
				messageTextPart.setContent((Multipart) content);
			} else {
				messageTextPart.setText(String.valueOf(content));
			}

			final Multipart attachmentPart = new MimeMultipart();
			attachmentPart.addBodyPart(messageTextPart, 0);

			processAttachments(attachmentPart, emailTemplate);
			simpleMessage.setContent(attachmentPart);
			Transport.send(simpleMessage);

			LOGGER.info("Mail sent successfully to {} ", emailTemplate.getEmailRecipients());
		} catch (Exception e) {
			LOGGER.error("Error while sending alert e-mail : {}", e);
			throw e;
		}
		return true;
	}
}
