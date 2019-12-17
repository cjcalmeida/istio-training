package com.cjcalmeida.istio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class AppAuth {
    public static void main( String[] args ) {
        SpringApplication.run(AppAuth.class, args);
    }

    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private AuthenticationManager manager;
        private PasswordEncoder encoder;

        @Value("${auth.private-key:s3cr3t}")
        private String signingKey;

        public AuthorizationServerConfiguration(AuthenticationConfiguration configuration, PasswordEncoder passwordEncoder) throws Exception {
            this.manager = configuration.getAuthenticationManager();
            this.encoder = passwordEncoder;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.authenticationManager(manager)
                    .tokenStore(tokenStore())
                    .accessTokenConverter(accessTokenConverter());
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) {
            security.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("application")
                    .authorizedGrantTypes("client_credentials", "password", "implicit", "authorization_code", "refresh_token")
                    .redirectUris("https://app.getpostman.com/oauth2/callback")
                    .secret(encoder.encode("application-secret"))
                    .scopes("any")
                    .accessTokenValiditySeconds(3_600)
                    .and()

                    .withClient("modulo")
                    .authorizedGrantTypes("client_credentials")
                    .redirectUris("https://app.getpostman.com/oauth2/callback")
                    .secret(encoder.encode("secret"))
                    .scopes("any")
                    .accessTokenValiditySeconds(30)
                    .and()

                    .withClient("materia")
                    .authorizedGrantTypes("client_credentials")
                    .redirectUris("https://app.getpostman.com/oauth2/callback")
                    .secret(encoder.encode("secret"))
                    .scopes("any")
                    .accessTokenValiditySeconds(30)
                    .and()

                    .withClient("curso")
                    .authorizedGrantTypes("client_credentials")
                    .redirectUris("https://app.getpostman.com/oauth2/callback")
                    .secret(encoder.encode("secret"))
                    .scopes("any")
                    .accessTokenValiditySeconds(30);
        }

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(signingKey);
            return converter;
        }

        @Bean
        public TokenStore tokenStore(){
            return new JwtTokenStore(accessTokenConverter());
        }
    }

    @Configuration
    @EnableWebSecurity
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        protected UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                    User.withUsername("professor")
                            .password(passwordEncoder().encode("1234"))
                            .roles("PROFESSOR")
                            .build(),
                    User.withUsername("estudante")
                            .password(passwordEncoder().encode("1234"))
                            .roles("ESTUDANTE")
                            .build()
            );
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }
}
