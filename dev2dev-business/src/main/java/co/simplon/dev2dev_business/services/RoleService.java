package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.repositories.RoleRepository;

public class RoleService {
    private final RoleRepository roleRepository;

    protected RoleService(RoleRepository roleRepository) {
	this.roleRepository = roleRepository;
    }

}
