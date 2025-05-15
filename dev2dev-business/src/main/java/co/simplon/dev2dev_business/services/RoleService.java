package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.entities.Role;
import co.simplon.dev2dev_business.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    protected RoleService(RoleRepository roleRepository) {
	this.roleRepository = roleRepository;
    }


    @Deprecated(since = "for test purpose", forRemoval = true)
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
