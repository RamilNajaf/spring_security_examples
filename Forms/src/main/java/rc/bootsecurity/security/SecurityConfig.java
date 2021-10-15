package rc.bootsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import rc.bootsecurity.repository.UserRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private FailureHandler failureHandler;
    private final UserPrincipialDetailService userPrincipialDetailService;

    public SecurityConfig(UserPrincipialDetailService userPrincipialDetailService) {
        this.userPrincipialDetailService = userPrincipialDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(daoAuthenticationProvider());


//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("123"))
//                .authorities("ACCESS_TEST1", "ACCESS_TEST","ROLE_ADMIN")
//                .and()
//                .withUser("manager").password(passwordEncoder().encode("123")).authorities("ACCES_TEST1","ROLE_MANAGER")
//                .and()
//                .withUser("hesen").password(passwordEncoder().encode("123"))
//                .roles("USER");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1")
                .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")
                .antMatchers("/api/public/users").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/signin")
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .defaultSuccessUrl("/index?success")
                .failureHandler(failureHandler)
                .failureUrl("/login?fail")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and()
                .rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe");


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipialDetailService);
        return daoAuthenticationProvider;

    }


}
