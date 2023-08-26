package com.sumber.barokah.jurnal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1 to 1
//    private static final String SQL_LOGIN
//            = "select u.username, up.user_password, u.active " +
//            "from s_users_passwords up " +
//            "inner join s_users u on u.id = up.id_user " +
//            "where u.username = ?";

    // m to m
    private static final String SQL_LOGIN
            = "select u.username, u.password, u.active" +
            "            from s_users u" +
            "            where u.username = ?";
      // 1 to 1, m to m
//    private static final String SQL_PERMISSION =
//            "select u.username, p.permission_value as authority " +
//                    "from s_users u " +
//                    "inner join s_roles r on r.id = u.id_role " +
//                    "inner join s_roles_permissions rp on rp.id_role = r.id " +
//                    "inner join s_permissions p on rp.id_permission = p.id " +
//                    "where u.username = ?";

    // m to m, m to m
    private static String SQL_PERMISSION =
            "select u.username, p.permission_value as authority " +
                    "                    from s_users u " +
                    "                    inner join s_users_roles sur on u.id = sur.id_user " +
                    "                    inner join s_roles r on sur.id_role = r.id " +
                    "                    inner join s_roles_permissions rp on rp.id_role = r.id " +
                    "                    inner join s_permissions p on rp.id_permission = p.id " +
                    "                    where u.username = ?";

    @Autowired
    DataSource dataSource;

    @Bean
    UserDetailsService users() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(this.dataSource);
        manager.setUsersByUsernameQuery(SQL_LOGIN);
        manager.setAuthoritiesByUsernameQuery(SQL_PERMISSION);
        return manager;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(users());
        return provider;

    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityConfig(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/sb/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();

    }

    @Bean
    public SecurityFilterChain htmlSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("/api/user/register").permitAll()
                .and()
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
