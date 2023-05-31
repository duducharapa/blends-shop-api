package com.charapadev.blendsshop.modules.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultAdminLoader {

    @Bean
    CommandLineRunner run(AdminService adminService) {
        return args -> adminService.saveDefault();
    }

}
