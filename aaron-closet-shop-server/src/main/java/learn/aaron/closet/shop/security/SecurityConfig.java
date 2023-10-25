package learn.aaron.closet.shop.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {

        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/refresh-token").authenticated()
                .antMatchers(HttpMethod.GET, "/api/products", "/api/products/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/products").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/products/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/products/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/orders", "/api/products/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/orders").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/orders/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/orders/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/cart", "/api/products/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cart").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/cart/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/cart/*").hasAuthority("ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), jwtConverter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
