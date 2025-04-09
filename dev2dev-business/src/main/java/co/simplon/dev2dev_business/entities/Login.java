package co.simplon.dev2dev_business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_accounts")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "flag")
    private boolean flag;

    public Long getId() {
	return id;
    }

    public String getUsername() {
	return username;
    }

    public String getPassword() {
	return password;
    }

    public boolean isFlag() {
	return flag;
    }

    public void setFlag(boolean flag) {
	this.flag = flag;
    }

    @Override
    public String toString() {
	return "Login [username=" + username + ", password=[PORTECTED], flag=" + flag + "]";
    }

}
