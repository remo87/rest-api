package com.remo.restapi;

import com.remo.restapi.models.AppRole;
import com.remo.restapi.models.AppUser;
import com.remo.restapi.services.IAppUserService;
import com.remo.restapi.services.IRoleService;
import com.remo.restapi.utils.DateUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employees API", version = "2.0", description = "Employees Information"))
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(IAppUserService userService, IRoleService roleService) {
        return args -> {
            AppRole roleUser = roleService.saveRole(new AppRole(null, "ROLE_USER"));
            AppRole roleManager = roleService.saveRole(new AppRole(null, "ROLE_MANAGER"));
            AppRole roleAdmin = roleService.saveRole(new AppRole(null, "ROLE_ADMIN"));
            AppRole roleSuperAdmin = roleService.saveRole(new AppRole(null, "ROLE_SUPER_ADMIN"));

            AppUser jc = userService.saveUser(new AppUser(null, "John", "Cena", "jh", "test123", null, "123123", DateUtils.parseDate("1987-03-12"), new ArrayList<>()));
            AppUser gc = userService.saveUser(new AppUser(null, "Galio", "Cesar", "gc", "test123", null, "123123", DateUtils.parseDate("1980-03-12"), new ArrayList<>()));
            AppUser mj = userService.saveUser(new AppUser(null, "Mary", "Jane", "mj", "test123", null, "123123", DateUtils.parseDate("1997-03-12"), new ArrayList<>()));
            AppUser ll = userService.saveUser(new AppUser(null, "Lois", "Lane", "ll", "test123", null, "123123", DateUtils.parseDate("1937-03-12"), new ArrayList<>()));

        };
    }
}
