package com.remo.restapi.services;

import com.remo.restapi.models.AppRole;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<AppRole> getRoles();
    Optional<AppRole> getRoleById(Long id);
    AppRole saveRole(AppRole role);
    AppRole updateRole(AppRole role);
    void deleteRole(Long id);
    boolean roleExists(Long id);
}
