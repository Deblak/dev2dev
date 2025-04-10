package co.simplon.dev2dev_business.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender {
	private JavaMailSender mailSender;

	protected EmailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Value("${dev2dev-business.email.from}")
	private String sender;

	public void sendEmail(String to, String subject, String htmlText) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(sender);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlText, true);

		mailSender.send(message);
	}
}
