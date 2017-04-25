package umm3601.OAUTH;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.matching.PathMatcher;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.oauth.client.Google2Client;
import spark.TemplateEngine;

/**
 * Created by frazi177 on 4/25/17.
 */
public class DDGConfigFactory implements ConfigFactory {

    private final String salt;

    public DDGConfigFactory(final String salt) {
        this.salt = salt;
    }

    @Override
    public Config build() {
//
        final Google2Client google2Client = new Google2Client("124405005703-d2kbqml252lon2osu99sd2keplofo5us", "0Lk736R6UyLUh15jPKhRXNEx");

        // REST authent with JWT for a token passed in the url as the token parameter
        ParameterClient parameterClient = new ParameterClient("token", new JwtAuthenticator(new SecretSignatureConfiguration(salt)));
        parameterClient.setSupportGetRequest(true);
        parameterClient.setSupportPostRequest(false);

        final Clients clients = new Clients("http://localhost:2538/callback", google2Client, parameterClient, new AnonymousClient());

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        //TODO:
//        config.addAuthorizer("custom", new CustomAuthorizer());
        config.addMatcher("excludedPath", new PathMatcher().excludeRegex("^/facebook/notprotected$"));
//        config.setHttpActionAdapter(new DemoHttpActionAdapter(templateEngine));
        return config;
    }

}
