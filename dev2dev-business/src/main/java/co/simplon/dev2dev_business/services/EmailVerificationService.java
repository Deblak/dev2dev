package co.simplon.dev2dev_business.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
    private EmailSender emailService;

    protected EmailVerificationService(EmailSender emailService) {
	this.emailService = emailService;
    }

    public String generateVerificationCode() {
	return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendVerificationCode(String email) {
	String code = generateVerificationCode();
	emailService.sendEmail(email, "Verification Code",
		"Please use the following code to complete your login: " + code);
    }

    public boolean verifyCode(String userEmail, String userEnteredCode, String sentCode) {
	return sentCode.equals(userEnteredCode);
    }

}
