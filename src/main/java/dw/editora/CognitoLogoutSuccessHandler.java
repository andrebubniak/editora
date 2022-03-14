package dw.editora;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class CognitoLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler 
{

    private final String logoutURL;
    private final String clientId;
    private final String redirectURI;
    private final String scope;

    public CognitoLogoutSuccessHandler(String logoutURL, String redirectURI, String clientId, String scope) {
        this.logoutURL = logoutURL;
        this.redirectURI = redirectURI;
        this.clientId = clientId;
        this.scope = scope;
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) 
    {

        UriComponents baseUrl = UriComponentsBuilder
                .fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .build();

                
        String uri = UriComponentsBuilder
                .fromUri(URI.create(logoutURL))
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("logout_uri", baseUrl)
                .queryParam("redirect_uri", redirectURI)
                .queryParam("scope", scope)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
        
        return uri;

    }
}
