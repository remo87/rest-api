package com.remo.restapi;

import com.remo.restapi.models.AppRole;
import com.remo.restapi.models.AppUser;
import com.remo.restapi.services.IAppUserService;
import com.remo.restapi.services.IRoleService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employees API", version = "2.0", description = "Employees Information"))
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(IAppUserService userService, IRoleService roleService) {
        return args -> {
            roleService.saveRole(new AppRole(null, "ROLE_USER"));
            roleService.saveRole(new AppRole(null, "ROLE_MANAGER"));
            roleService.saveRole(new AppRole(null, "ROLE_ADMIN"));
            roleService.saveRole(new AppRole(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(null, "John", "Cena", "jh", "test123", null, "123123", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Galio", "Cesar", "gc", "test123", null, "123123", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Mary", "Jane", "mj", "test123", null, "123123", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Lois", "Lane", "ll", "test123", null, "123123", new ArrayList<>()));

            userService.addRoleToUser("jh", "ROLE_USER");
            userService.addRoleToUser("gc", "ROLE_MANAGER");
            userService.addRoleToUser("mj", "ROLE_ADMIN");
            userService.addRoleToUser("ll", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("ll", "ROLE_USER");
            userService.addRoleToUser("ll", "ROLE_ADMIN");
        };
    }
}
