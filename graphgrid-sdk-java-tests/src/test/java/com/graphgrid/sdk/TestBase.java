package com.graphgrid.sdk;

import com.graphgrid.sdk.core.security.SecurityConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Properties;

@ActiveProfiles( "test" )
@RunWith( SpringRunner.class )
@SpringBootTest( classes = App.class, properties = "server.port:0" )
@Rollback( false )
public abstract class TestBase
{

    // only used to test getting a token for a user
//    @Value( "${spring.oauth.username}" )
//    protected String username;
//    @Value( "${spring.oauth.password}" )
//    protected String password;

    // needed to configure security context
//    @Value( "${spring.baseSecurityUrl}" )
//    private String securityEndpoint;
//    @Value( "${spring.oauth.token.url}" )
//    private String springOAuthTokenUrl;
//    @Value( "${spring.oauth.token.client.id}" )
//    private String oAuthClientId;
//    @Value( "${spring.oauth.token.client.secret}" )
//    private String oAuthClientSecret;
//    @Value( "${spring.oauth.client.id}" )
//    private String clientId;
//    @Value( "${spring.oauth.client.secret}" )
//    private String clientSecret;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    protected SecurityConfig securityConfig;

    protected String username;
    protected String password;

    @Before
    public void setUp() throws Exception
    {
        securityConfig = new SecurityConfig();
        loadSecurityCredentialsFromPom();
    }

    private void loadSecurityCredentialsFromPom()
    {
        java.io.InputStream is = this.getClass().getClassLoader().getResourceAsStream( "test-properties" );
        java.util.Properties p = new Properties();
        try
        {
            p.load( is );
        }
        catch ( final IOException e )
        {
            e.printStackTrace();
        }

        if ( !Boolean.valueOf( p.getProperty( "config.useAsOverwrite" ) ) )
        {
            return;
        }
        System.out.println( "========================================" );
        System.out.println( p.getProperty( "baseSecurityUrl" ) );

        securityConfig.setClientId( p.getProperty( "client.id" ) );
        securityConfig.setClientSecret( p.getProperty( "client.secret" ) );
        securityConfig.setBaseSecurityUrl( p.getProperty( "baseSecurityUrl" ) );
        securityConfig.setOauthTokenClientId( p.getProperty( "oauth.token.client.id" ) );
        securityConfig.setOauthTokenClientSecret( p.getProperty( "oauth.token.client.secret" ) );
        securityConfig.setOauthTokenUrl( p.getProperty( "oauth.token.url" ) );

        username = p.getProperty( "oauth.username" );
        password = p.getProperty( "oauth.password" );
    }
}
