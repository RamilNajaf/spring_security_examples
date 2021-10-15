package rc.bootsecurity.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rc.bootsecurity.model.User;

import java.util.Arrays;
import java.util.List;


@Service
public class dbInit  implements CommandLineRunner {

    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    public dbInit(UserRepository userRepository,PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("ramil", passwordEncoder.encode("ramil123"), "USER", "");
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");
        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","ACCESS_TEST1");

        List<User> users = Arrays.asList(user,admin,manager);
        userRepository.saveAll(users);

    }
}
