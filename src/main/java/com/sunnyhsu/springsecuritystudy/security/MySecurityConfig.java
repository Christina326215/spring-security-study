package com.sunnyhsu.springsecuritystudy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();  // 指定要使用哪一種演算法，實作密碼的加密
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails userTest1 = User
//                .withUsername("test1")
//                .password("{noop}111")
//                .roles("ADMIN","USER")
//                .build();
//
//        UserDetails userTest2 = User
//                .withUsername("test2")
//                .password("{bcrypt}$2a$12$Vai3MGH.kVJV6rL.GAJLgufv7.xz4IQ2GLhRuotcNhGAJMtNjGU4G")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(userTest1, userTest2);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // 設定 Session 的創建機制
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )

//                .csrf(csrf -> csrf.disable())
                // 設定 CSRF 保護
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(createCsrfHandler())
                        .ignoringRequestMatchers("/register","/userLogin")
                )

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())

                .authorizeHttpRequests(request -> request
                                .requestMatchers("/register","/userLogin").permitAll() // 不需身分驗證(登入)，所有人皆可看到
                                .requestMatchers("/hello").authenticated()
                                .requestMatchers("/welcome").hasRole("ADMIN")
//              .requestMatchers("/welcome").hasAnyRole("ADMIN", "USER")
//              .requestMatchers(HttpMethod.GET, "/hello").authenticated()

                                // Movie 功能
//              .requestMatchers("/getMovies").hasAnyRole("NORMAL_MEMBER", "MOVIE_MANAGER", "ADMIN")
//              .requestMatchers("/watchFreeMovie").hasAnyRole("NORMAL_MEMBER", "ADMIN")
//              .requestMatchers("/watchVipMovie").hasAnyRole("VIP_MEMBER", "ADMIN")
//              .requestMatchers("/uploadMovie").hasAnyRole("MOVIE_MANAGER", "ADMIN")
//              .requestMatchers("/deleteMovie").hasAnyRole("MOVIE_MANAGER", "ADMIN")

                                // 三種控制授權的方法
//              .requestMatchers("/api1").hasRole("ADMIN")
//              .requestMatchers("/api2").hasAuthority("ROLE_ADMIN")
//              .requestMatchers("/api3").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasIpAddress('192.168.0.1/24')"))

//              .requestMatchers("/getMovie", "/deleteMovie").hasRole("ADMIN")
                                .anyRequest().denyAll()
//                .anyRequest().authenticated() //除了 welcome 以外的 api ，都需要經過身分驗證(登入)
                        // permitAll() 允許所有人訪問
                        // denyAll() 拒絕所有人訪問
                        // authenticated() 需要登入才能訪問
                        // hasRole()、hasAnyRole() 需要登入、並且有該權限，才能訪問
                )

//                .addFilterBefore(new MyFilter2(), BasicAuthenticationFilter.class)

                .cors(cors -> cors
                        .configurationSource(createCorsConfig())
                )

                .build();
    }

    private CsrfTokenRequestAttributeHandler createCsrfHandler(){
        CsrfTokenRequestAttributeHandler csrfHandler = new CsrfTokenRequestAttributeHandler();
        csrfHandler.setCsrfRequestAttributeName(null);

        return csrfHandler;
    }

    private CorsConfigurationSource createCorsConfig(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));
//        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
