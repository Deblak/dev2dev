package co.simplon.dev2dev_business.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

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

    public boolean isEmailValidate() {
	return emailValidate;
    }

    public void setEmailValidate(boolean emailValidate) {
	this.emailValidate = emailValidate;
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
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("password=[PROTECTED]")
                .add("emailValidate=" + emailValidate)
                .add("role=" + role)
                .add("notificationTypeSet=" + notificationTypeSet)
                .toString();
    }
}
