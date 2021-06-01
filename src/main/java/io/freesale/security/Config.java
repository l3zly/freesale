package io.freesale.security;

import io.freesale.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

@EnableWebFluxSecurity
public class Config {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
      ReactiveAuthenticationManager authenticationManager,
      ServerAuthenticationConverter serverAuthenticationConverter) {
    var authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
    authenticationWebFilter.setServerAuthenticationConverter(serverAuthenticationConverter);
    return http
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .logout()
        .disable()
        .authorizeExchange()
        .pathMatchers(HttpMethod.POST, "/users")
        .permitAll()
        .anyExchange()
        .authenticated()
        .and()
        .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }

  @Bean
  public ServerAuthenticationConverter serverAuthenticationConverter() {
    return new TokenAuthenticationConverter();
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager(TokenUtil tokenUtil,
      ReactiveUserDetailsService userDetailsService) {
    return new TokenAuthenticationManager(tokenUtil, userDetailsService);
  }

  @Bean
  public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
    return new MongoUserDetailsService(userRepository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
