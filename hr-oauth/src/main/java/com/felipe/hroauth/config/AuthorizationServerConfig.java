package com.felipe.hroauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Value("${oauth.client.name}")
    private String oauthClientName;
    @Value("${oauth.client.secret}")
    private String oauthClientSecret;
    private static final String GRANT_TYPE = "password";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 86400;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private JwtTokenStore tokenStore;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(oauthClientName)
                .secret(passwordEncoder.encode(oauthClientSecret))
                .scopes(SCOPE_READ, SCOPE_WRITE)
                .authorizedGrantTypes(GRANT_TYPE)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter);
    }
}
