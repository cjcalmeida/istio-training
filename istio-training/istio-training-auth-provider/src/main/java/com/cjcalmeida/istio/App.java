package com.cjcalmeida.istio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Configuration
    @EnableAuthorizationServer
    public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

        private AuthenticationManager manager;

        @Autowired
        public AuthorizationServerConfiguration(AQualifier("authenticationManagerBean") thenticationManager manager){
            this.manager = manager;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey("chave-privada");

            JwtTokenStore store = new JwtTokenStore(converter);
            endpoints.accessTokenConverter(converter)
            .tokenStore(store)
            .authenticationManager(manager);
        }


        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.tokenKeyAccess("permitAll()");
        }
    }

    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        @Override
        protected UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                .username("professor")
                .password("1234")
                .roles("PROFESSOR")
                .build(),
                User.withDefaultPasswordEncoder()
                .username("estudante")
                .password("1234")
                .roles("ESTUDANTE")
                .build()
            );
        }

    }

}
