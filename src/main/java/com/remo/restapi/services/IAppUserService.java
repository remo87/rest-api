package com.remo.restapi.services;

import java.util.List;

import com.remo.restapi.models.AppUser;


/**
 * IAppUserService
 */
public interface IAppUserService {

    AppUser saveUser(AppUser user);
    AppUser updateUser(AppUser user);
    List<AppUser> getUsers();
    AppUser getUser(Long id);
    void deleteUser(Long id);
    
}