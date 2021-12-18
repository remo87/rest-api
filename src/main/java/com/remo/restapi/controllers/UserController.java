package com.remo.restapi.controllers;

import com.remo.restapi.exceptions.NotFoundException;
import com.remo.restapi.models.AppUser;
import com.remo.restapi.services.IAppUserService;
import com.remo.restapi.services.IRoleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IAppUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers(){
        List<AppUser> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id){
        Optional<AppUser> userOptional = userService.getUser(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        return ResponseEntity.ok(userOptional.get());
    }

    @PostMapping
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        AppUser savedUser = userService.saveUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users"+savedUser.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) throws NotFoundException{
        validateUser(id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private void validateUser(Long id) throws NotFoundException{
        boolean userExists = userService.userExists(id);
        if(!userExists){
            throw new NotFoundException("User not found.");
        }
    }

    private void validateRole(Long id) throws NotFoundException{
        boolean userExists = roleService.roleExists(id);
        if(!userExists){
            throw new NotFoundException("User not found.");
        }
    }

    private void validateUser(String username) throws NotFoundException{
        boolean userExists = userService.userExists(username);
        if(!userExists){
            throw new NotFoundException("User not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser user) throws NotFoundException{
        validateUser(id);
        AppUser updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/roles/{roleId}")
    public ResponseEntity<AppUser> addRoleToUser(@PathVariable Long id, @PathVariable Long roleId) throws NotFoundException {
        validateUser(id);
        validateRole(roleId);
        AppUser updatedUser = userService.addRoleToUser(id, roleId);
        return ResponseEntity.ok(updatedUser);
    }

}
