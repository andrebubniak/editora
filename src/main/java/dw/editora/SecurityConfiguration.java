package dw.editora;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter 
{

    private final String clientId;
    private final String logoutURL;
    private final String redirectURI;
    private final String scope;

    public SecurityConfiguration(
            @Value("${spring.security.oauth2.client.registration.cognito.clientId}") String clientId,
            @Value("${app.cognito.logoutURL}") String logoutURL,
            @Value("${app.cognito.redirectURI}") String redirectURI,
            @Value("${spring.security.oauth2.client.registration.cognito.scope}") String scope) 
    {
        this.clientId = clientId;
        this.logoutURL = logoutURL;
        this.redirectURI = redirectURI;
        this.scope = scope;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf();
        http.csrf()
                .and()
                .authorizeRequests(authz -> authz.mvcMatchers("/")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login()
                .and()
                .logout()
                .logoutSuccessHandler(new CognitoLogoutSuccessHandler(logoutURL, redirectURI, clientId, scope))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");
                
    }
}
