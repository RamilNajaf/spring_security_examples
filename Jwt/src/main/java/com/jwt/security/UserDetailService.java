package com.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailService implements UserDetailsService {

    private Map<String,String> userRepo=  new HashMap();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepo.put("ramil", passwordEncoder.encode("123"));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepo.containsKey(username)){
            return new User(username,userRepo.get(username),new ArrayList<>());
        }
        throw new UsernameNotFoundException("User is not available");
    }
}
