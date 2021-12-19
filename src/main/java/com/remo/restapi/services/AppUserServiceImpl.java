package com.remo.restapi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.remo.restapi.models.AppRole;
import com.remo.restapi.models.AppUser;
import com.remo.restapi.repositories.IAppRoleRepository;
import com.remo.restapi.repositories.IAppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements IAppUserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IAppUserRepository userRepository;

    @Autowired
    private IAppRoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(AppUser user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser addRoleToUser(Long userId, Long roleId) {
        AppUser user = userRepository.getById(userId);
        AppRole appRole = roleRepository.getById(roleId);
        user.getRoles().add(appRole);
        return userRepository.save(user);
    }

    @Override
    public AppUser addRoleToUser(String username, String roleName) {
        AppUser user = userRepository.findByUsername(username);
        AppRole appRole = roleRepository.findByName(roleName);
        user.getRoles().add(appRole);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database.");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
