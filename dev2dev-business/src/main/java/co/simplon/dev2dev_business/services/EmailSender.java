package co.simplon.dev2dev_business.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private JavaMailSender mailSender;

    protected EmailSender(JavaMailSender mailSender) {
	this.mailSender = mailSender;
    }

    @Value("${dev2dev-business.email.from}")
    private String sender;

    public void sendEmail(String to, String subject, String text) {
	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom(sender);
	message.setTo(to);
	message.setSubject(subject);
	message.setText(text);
	mailSender.send(message);
    }

}
