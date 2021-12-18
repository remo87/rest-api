package com.remo.restapi.controllers;

import com.remo.restapi.dtos.CreateUserDto;
import com.remo.restapi.dtos.UpdateUserDto;
import com.remo.restapi.dtos.UserDetailDto;
import com.remo.restapi.dtos.UserListItemDto;
import com.remo.restapi.exceptions.NotFoundException;
import com.remo.restapi.mappers.IUserMapper;
import com.remo.restapi.models.AppUser;
import com.remo.restapi.services.IAppUserService;
import com.remo.restapi.services.IRoleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IAppUserService userService;

    @Autowired
    private IRoleService roleService;

    private IUserMapper mapper = IUserMapper.MAPPER;

    @GetMapping
    public ResponseEntity<List<UserListItemDto>> getUsers(){
        List<AppUser> users = userService.getUsers();
        List<UserListItemDto> result = new ArrayList<>();
        result = users.stream().map(user -> mapper.toListItemDto(user)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getUserById(@PathVariable Long id){
        Optional<AppUser> userOptional = userService.getUser(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        AppUser user = userOptional.get();
        UserDetailDto userDto = mapper.toUserDetailDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDetailDto> saveUser(@RequestBody CreateUserDto createUser){
        AppUser user = mapper.toAppUser(createUser);
        user = userService.saveUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users"+user.getId()).toUriString());
        UserDetailDto savedUser = mapper.toUserDetailDto(user);
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
    public ResponseEntity<UserDetailDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUser) throws NotFoundException{
        Optional<AppUser> existingUser = userService.getUser(updateUser.getId());
        if(existingUser.isEmpty())
            throw new NotFoundException("User not found");
        AppUser user = mapper.toAppUser(updateUser);
        user.setCreatedAt(existingUser.get().getCreatedAt());
        user = userService.updateUser(user);
        UserDetailDto result = mapper.toUserDetailDto(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDetailDto> addRoleToUser(@PathVariable Long id, @PathVariable Long roleId) throws NotFoundException {
        validateUser(id);
        validateRole(roleId);
        AppUser updatedUser = userService.addRoleToUser(id, roleId);
        UserDetailDto user = mapper.toUserDetailDto(updatedUser);
        return ResponseEntity.ok(user);
    }

}
