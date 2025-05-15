package co.simplon.dev2dev_business.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Immutable;

import java.util.Objects;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "t_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "role_default", nullable = false)
    private boolean roleDefault;

    protected Role(Long id, String name, boolean roleDefault) {
	this.id = id;
	this.name = name;
	this.roleDefault = roleDefault;
    }

    public Role() {
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
	// this.id = id; genareted by db
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isRoleDefault() {
	return roleDefault;
    }

    public void setRoleDefault(boolean roleDefault) {
	this.roleDefault = roleDefault;
    }

    @Override
    public String toString() {
	return String.format("Role{name='%s'}", name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Role role)) return false;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
