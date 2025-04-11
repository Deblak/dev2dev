package co.simplon.dev2dev_business.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
@Table(name = "t_accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email_validate", nullable = false)
	private boolean emailValidate;

	@Column(name = "uuid_token", nullable = false)
	private String uuidToken;

	@Column(name = "expiration_token", nullable = false)
	private LocalDateTime expirationToken;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_role", nullable = false)
	private Role role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_account_notification_type",
    joinColumns = @JoinColumn(name = "account_id"),
    inverseJoinColumns = @JoinColumn(name = "notification_type_id"))
    private Set<NotificationType> notificationTypeSet = new HashSet<>();

    public Account() {
    }

    public Long getId() {
	return id;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEmailValidate() {
		return emailValidate;
	}

	public void setEmailValidate(boolean emailValidate) {
		this.emailValidate = emailValidate;
	}

	public String getUuidToken() {
		return uuidToken;
	}

	public void setUuidToken(String uuidToken) {
		this.uuidToken = uuidToken;
	}

	public LocalDateTime getExpirationToken() {
		return expirationToken;
	}

	public void setExpirationToken(LocalDateTime expirationToken) {
		this.expirationToken = expirationToken;
	}

    public Set<NotificationType> getNotificationTypeSet() {
        return Collections.unmodifiableSet(notificationTypeSet);
    }

    public void addNotificationType(NotificationType notificationType) {
        this.notificationTypeSet.add(notificationType);
    }

    public void removeNotificationType(NotificationType notificationType) {
        this.notificationTypeSet.remove(notificationType);
    }

    @Override
    public String toString() {
        return String.format("username=%s", "password=[PORTECTED]", id, username);
    }

}
