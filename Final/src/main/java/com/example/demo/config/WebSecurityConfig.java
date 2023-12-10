package com.example.demo.config;

import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Quản lý xác thực người dùng
    @Bean
    public AuthenticationManager authenticationManager(
            AccountService accountService,
            PasswordEncoder passwordEncoder) {

        // Một triển khai của AuthenticationProvider được cung cấp bởi Spring Security
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountService); // UserDetailService được AccountService implements
        authenticationProvider.setPasswordEncoder(passwordEncoder); // passwordEncoder

        // Là một triển khai của AuthenticationManager trong Spring Security và được sử dụng để quản lý danh sách các AuthenticationProvider.
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/Admin/**").hasRole("ADMIN")
                                .requestMatchers("/logout", "/register", "/", "/index",
                                        "/fonts/**", "/lib/**", "/js/**", "/css/**", "/images/**",
                                        "/admin/**", "/img.contact/**")
                                .permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
                                .anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
                )
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF protection
                .cors(AbstractHttpConfigurer::disable) // Ngăn chặn request từ một domain khác
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login")
                                .permitAll()
                                .successHandler(loginSuccessHandler)
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                )
                .httpBasic(withDefaults());
        return http.build();
    }

}
