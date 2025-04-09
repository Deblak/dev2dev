package co.simplon.dev2dev_business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
	return id;
    }

    protected Role(Long id, String name, String roleDefault) {
	this.id = id;
	this.name = name;
	this.roleDefault = roleDefault;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
	// this.id = id; genareted by db
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getRoleDefault() {
	return roleDefault;
    }

    public void setRoleDefault(String roleDefault) {
	this.roleDefault = roleDefault;
    }

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "role_default", nullable = false)
    private String roleDefault;

}
