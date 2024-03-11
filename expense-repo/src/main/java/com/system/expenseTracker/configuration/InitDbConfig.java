package com.system.expenseTracker.configuration;

import com.system.expenseTracker.model.Role;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.RoleRepo;
import com.system.expenseTracker.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
@Configuration
@RequiredArgsConstructor
public class InitDbConfig {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    @PostConstruct
    public void doEntries(){
        if (roleRepo.findAll().size()==0){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role savedAdminRole = roleRepo.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            Role savedUserRole =  roleRepo.save(userRole);


            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User();
            user.setName("admin");
            user.setUsername("admin");
            user.setPassword(encoder.encode("admin"));
            user.setRoles(Arrays.asList(savedAdminRole));
            userRepo.save(user);

            User nonAdminUser = new User();
            nonAdminUser.setName("user");
            nonAdminUser.setUsername("user");
            nonAdminUser.setPassword(encoder.encode("user"));
            nonAdminUser.setRoles(Arrays.asList(savedUserRole));
            userRepo.save(nonAdminUser);
        }
    }
}
