package vn.edu.iuh.fit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder managerBuilder, DataSource dataSource) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        managerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN"))
                .withUser(User.withUsername("user").password(encoder.encode("user")).roles("USER"))
                .withUser(User.withUsername("thao").password(encoder.encode("thao")).roles("ADMIN", "USER"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/user.html").hasRole("USER")
                .requestMatchers("/dashboard.html").hasRole("ADMIN")
                .requestMatchers("/auth.html").authenticated()
                .anyRequest().permitAll()
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
}
