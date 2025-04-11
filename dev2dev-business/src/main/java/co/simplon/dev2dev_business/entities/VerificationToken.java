package co.simplon.dev2dev_business.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_verification_tokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_username", nullable = false)
    private Account account;

    @Column(name = "code_pin", nullable = false)
    private String codePin;

    @Column(name = "verification_exp", nullable = false)
    private LocalDateTime verificationTime;

    @SuppressWarnings("unused")
    public void setId(Long id) {
	// this.id = id; genareted by db
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

    public String getCodePin() {
	return codePin;
    }

    public void setCodePin(String code) {
	this.codePin = code;
    }

    public LocalDateTime getVerificationTime() {
	return verificationTime;
    }

    public void setVerificationTime(LocalDateTime verificationTime) {
	this.verificationTime = verificationTime;
    }

    public Long getId() {
	return id;
    }

    @Override
    public String toString() {
	return "VerificationToken [id=" + id + ", account=" + account + ", codePin=" + codePin + ", verificationTime="
		+ verificationTime + "]";
    }

    public VerificationToken() {
    }

}
