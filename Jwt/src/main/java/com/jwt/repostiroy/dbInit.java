package com.jwt.repostiroy;

import com.jwt.entity.User;
import com.jwt.utils.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class dbInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public dbInit(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("ramil", passwordEncoder.encode("ramil123"), Role.ROLE_USER);
        User admin = new User("admin", passwordEncoder.encode("admin123"), Role.ROLE_ADMIN);
        User manager = new User("manager", passwordEncoder.encode("manager123"), Role.ROLE_MANAGER);

        List<User> users = Arrays.asList(user, admin, manager);
        userRepository.saveAll(users);

    }
}
