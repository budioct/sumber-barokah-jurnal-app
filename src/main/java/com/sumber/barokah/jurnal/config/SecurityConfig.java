package com.sumber.barokah.jurnal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityConfig(HttpSecurity http) throws Exception {

        http
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        authorize -> {
                            authorize.requestMatchers("/api/sb/auth/**").permitAll(); // allow endpoint
                            authorize.requestMatchers(HttpMethod.GET, "/api/sb/**").permitAll();
                            authorize.requestMatchers("/api/sb/**").hasAnyRole("ADMIN");
                        }
                )
                .authorizeHttpRequests().anyRequest().authenticated();

        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager data = new InMemoryUserDetailsManager();

        UserDetails budhi = User.withUsername("budhi")
                .password(passwordEncoder().encode("rahasia"))
                .roles("ADMIN")
//                .authorities("read", "write")
                .build();

        UserDetails agung = User.withUsername("agung")
                .password(passwordEncoder().encode("rahasia"))
                .roles("USER")
//                .authorities("read")
                .build();

        data.createUser(budhi);
        data.createUser(agung);

        return data;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
