package com.charapadev.blendsshop.modules.admin;

import com.charapadev.blendsshop.modules.users.User;
import com.charapadev.blendsshop.modules.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Value("${admin.access_code}")
    private String defaultAccessCode;

    @Value("${admin.secret}")
    private String defaultSecret;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    public void saveDefault() {
        boolean userExists = userRepository.findByAccessCode(defaultAccessCode)
            .isPresent();

        if (!userExists) {
            String encodedSecret = encoder.encode(defaultSecret);

            User defaultAdmin = User.builder()
                .accessCode(defaultAccessCode).password(encodedSecret)
                .build();

            userRepository.save(defaultAdmin);
        }
    }

}
