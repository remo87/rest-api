package com.remo.restapi.controllers;

import com.remo.restapi.exceptions.NotFoundException;
import com.remo.restapi.models.AppRole;
import com.remo.restapi.services.IAppUserService;
import com.remo.restapi.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<AppRole>> getRoles(){
        List<AppRole> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppRole> geRoleById(@PathVariable Long id){
        Optional<AppRole> roleOptional = roleService.getRoleById(id);
        if(roleOptional.isEmpty())
            throw new NotFoundException("User not found");
        return ResponseEntity.ok(roleOptional.get());
    }

    @PostMapping
    public ResponseEntity<AppRole> saveRole(@RequestBody AppRole role){
        AppRole savedRole = roleService.saveRole(role);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles"+savedRole.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRole(@PathVariable Long id) throws NotFoundException{
        validateRole(id);
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    private void validateRole(Long id) throws NotFoundException{
        boolean roleExists = roleService.roleExists(id);
        if(!roleExists){
            throw new NotFoundException("Role not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppRole> updateRole(@PathVariable Long id, @RequestBody AppRole role) throws NotFoundException{
        validateRole(id);
        AppRole updatedRole = roleService.updateRole(role);
        return ResponseEntity.ok(updatedRole);
    }
}
