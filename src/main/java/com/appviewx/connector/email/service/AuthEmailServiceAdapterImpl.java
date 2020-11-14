package com.appviewx.connector.email.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
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

@Component("AUTH-SERVICE")
public class AuthEmailServiceAdapterImpl extends AbstractMailAdapter {

	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthEmailServiceAdapterImpl.class);

	/**
	 * Send email.
	 *
	 * @param emailTemplate
	 *            the email template
	 * @throws AvxServiceException .
	 * @return boolean
	 */
	@Override
	public boolean sendEmail(EmailTemplate emailTemplate, EmailConfig emailConfig) throws Exception {

		final Object content = emailTemplate.getEmailTemplateContent();
		Message simpleMessage = null;
		InternetAddress fromAddress = null;

		final Properties props = new Properties();
		props.put(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.PROTOCOL_SMTP);
		props.put(EmailConstants.MAIL_SMTP_HOST, emailConfig.getHost());
		props.put(EmailConstants.MAIL_SMTP_PORT, emailConfig.getPort());
		props.put(EmailConstants.MAIL_SMTP_AUTH, EmailConstants.AUTH);
		props.put(EmailConstants.MAIL_SMTP_CONN_TIMEOUT, String.valueOf(emailConfig.getConnectionTimeout()));
		props.put(EmailConstants.MAIL_SMTP_TIMEOUT, String.valueOf(emailConfig.getReadIOTimeout()));
		if(emailConfig.isTlsEnabled()) {
			if("465".equals(emailConfig.getPort())){
				props.put(EmailConstants.MAIL_SMTP_SOCKETFACTORY_PORT, emailConfig.getPort());
				props.put(EmailConstants.MAIL_SSL_ENABLED, "true");
				props.put(EmailConstants.MAIL_SMTP_SOCKETFACTORY, "javax.net.ssl.SSLSocketFactory");         
				LOGGER.info("SMTP- Is SSL enabled {}", String.valueOf(emailConfig.isTlsEnabled()));
			}else {
				props.put(EmailConstants.MAIL_TLS_ENABLED, EmailConstants.AUTH);
				LOGGER.info("SMTP- Is TLS enabled {}", String.valueOf(emailConfig.isTlsEnabled()));
			}
		}

		Transport transport = null;
		try {
			setDefaultMailCapConfiguration();
			final Session mailSession = Session.getInstance(props,
				    new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
			      }
			    });
			transport = mailSession.getTransport();
			simpleMessage = new MimeMessage(mailSession);

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

			transport.connect(emailConfig.getUsername(), emailConfig.getPassword());

			LOGGER.info("SMTP server authentication successfull with username : {}", emailConfig.getUsername());

			transport.sendMessage(simpleMessage, simpleMessage.getAllRecipients());

			LOGGER.info("Mail sent successfully to {} ", emailTemplate.getEmailRecipients());
		} catch (AuthenticationFailedException e) {
			LOGGER.error("Error while sending e-mail : {}", e);
			throw e;
		} finally {
			if (null != transport) {
				try {
					transport.close();
				} catch (MessagingException e) {
					LOGGER.error("Error while closing the email transport instance", e);
				}
			}
		}
		return true;
	}
}
