package com.bharath.watchSpace;

import com.bharath.watchSpace.Entities.Admin;
import com.bharath.watchSpace.RepositoryPackage.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminRunner implements CommandLineRunner
{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminRepository adminRepository;

    public final String name = "admin";
    public final String password = "admin123467";

    @Override
    public void run(String... args) throws Exception
    {
        if (adminRepository.existsById(1)) {
            return;
        }

        Admin admin = new Admin();
        admin.setId(1);
        admin.setName(name);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ADMIN");
        adminRepository.save(admin);
    }
}
