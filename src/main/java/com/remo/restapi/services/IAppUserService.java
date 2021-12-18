package com.remo.restapi.services;

import java.util.List;
import java.util.Optional;

import com.remo.restapi.models.AppUser;


/**
 * IAppUserService
 */
public interface IAppUserService {

    AppUser saveUser(AppUser user);
    AppUser updateUser(AppUser user);
    List<AppUser> getUsers();
    Optional<AppUser> getUser(Long id);
    void deleteUser(Long id);
    boolean userExists(Long id);
    boolean userExists(String username);
    AppUser addRoleToUser(Long userId, Long roleId);
    AppUser addRoleToUser(String username, String roleName);
}