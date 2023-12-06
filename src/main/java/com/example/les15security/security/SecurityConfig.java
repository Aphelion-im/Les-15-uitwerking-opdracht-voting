// https://www.digitalocean.com/community/tutorials/spring-configuration-annotation
// https://www.concretepage.com/spring-5/spring-enablewebsecurity-example
// https://docs.spring.io/spring-framework/reference/core/beans/java/basic-concepts.html
package com.example.les15security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Spring Configuration annotation indicates that the class has @Bean definition methods. So Spring container can process the class and generate Spring Beans to be used in the application.
@EnableWebSecurity
// The Spring Security @EnableWebSecurity annotation is annotated at class level with @Configuration annotation to enable web securities in our application defined by WebSecurityConfigurer implementations.
public class SecurityConfig {

    @Bean
    // The @Bean annotation is used to indicate that a method instantiates, configures, and initializes a new object to be managed by the Spring IoC container.. Spring Bean annotation is usually declared in Configuration classes methods.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager man = new InMemoryUserDetailsManager();

        // Users niet hardcoded voor de eindopdracht
        UserDetails u1 = User
                .withUsername("karel")
                .password(encoder.encode("appel"))
                .roles("USER")
                .build();
        man.createUser(u1);

        UserDetails u2 = User
                .withUsername("ans")
                .password(encoder.encode("peer"))
                .roles("ADMIN")
                .build();
        man.createUser(u2);

        UserDetails u3 = User
                .withUsername("pieter")
                .password(encoder.encode("pannenkoek"))
                .roles("VOTER")
                .build();
        man.createUser(u3);

        return man;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.GET, "/cars").hasAnyRole("DESK","MECHANIC","ADMIN")
                .requestMatchers(HttpMethod.GET, "/secret").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                .requestMatchers(HttpMethod.POST, "/votes").hasAnyRole("VOTER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/votes").hasRole("ADMIN")
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();

        return http.build();
    }
}
