package com.remo.restapi.controllers;

import com.remo.restapi.dtos.CreateRoleDto;
import com.remo.restapi.dtos.RoleDto;
import com.remo.restapi.dtos.UpdateRoleDto;
import com.remo.restapi.exceptions.NotFoundException;
import com.remo.restapi.mappers.IRoleMapper;
import com.remo.restapi.models.AppRole;
import com.remo.restapi.services.IAppUserService;
import com.remo.restapi.services.IRoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.management.relation.Role;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "restapi")
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    private IRoleMapper mapper = IRoleMapper.MAPPER;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoles(){
        List<AppRole> roles = roleService.getRoles();
        List<RoleDto> mappedRoles = roles.stream().map(role -> mapper.toRoleDto(role)).collect(Collectors.toList());
        return ResponseEntity.ok(mappedRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> geRoleById(@PathVariable Long id){
        Optional<AppRole> roleOptional = roleService.getRoleById(id);
        if(roleOptional.isEmpty())
            throw new NotFoundException("User not found");
        AppRole role = roleOptional.get();
        RoleDto mappedRole = mapper.toRoleDto(role);
        return ResponseEntity.ok(mappedRole);
    }

    @PostMapping
    public ResponseEntity<RoleDto> saveRole(@RequestBody CreateRoleDto createRole){
        AppRole role = mapper.toAppRole(createRole);
        role = roleService.saveRole(role);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles"+role.getId()).toUriString());
        RoleDto mappedRole = mapper.toRoleDto(role);
        return ResponseEntity.created(uri).body(mappedRole);
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
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody UpdateRoleDto roleToUpdate) throws NotFoundException{
        validateRole(id);
        AppRole role = mapper.toAppRole(roleToUpdate);
        role = roleService.updateRole(role);
        RoleDto mappedRole = mapper.toRoleDto(role);
        return ResponseEntity.ok(mappedRole);
    }
}
