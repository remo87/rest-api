package com.remo.restapi.services;

import java.util.List;
import java.util.Optional;

import com.remo.restapi.models.AppUser;
import com.remo.restapi.repositories.IAppRoleRepository;
import com.remo.restapi.repositories.IAppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private IAppUserRepository userRepository;

    @Autowired
    private IAppRoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);        
    }

    @Override
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void addRoleToUser(String username, String role) {

    }

}
