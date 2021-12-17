package com.remo.restapi.services;

import com.remo.restapi.models.AppRole;
import com.remo.restapi.repositories.IAppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppRoleServiceImpl implements IRoleService {

    @Autowired
    private IAppRoleRepository roleRepository;

    @Override
    public List<AppRole> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<AppRole> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public AppRole updateRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public boolean roleExists(Long id) {
        return roleRepository.existsById(id);
    }
}
