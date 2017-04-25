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

    private final String salt, key, secret;

    public DDGConfigFactory(final String salt, final String key, final String secret) {
        this.salt = salt;
        this.key = key;
        this.secret = secret;
    }

    @Override
    public Config build() {
//
        final Google2Client google2Client = new Google2Client(key, secret);
        google2Client.setScope(Google2Client.Google2Scope.EMAIL);

        // REST authent with JWT for a token passed in the url as the token parameter
        ParameterClient parameterClient = new ParameterClient("token", new JwtAuthenticator(new SecretSignatureConfiguration(salt)));
        parameterClient.setSupportGetRequest(true);
        parameterClient.setSupportPostRequest(false);

        final Clients clients = new Clients("http://localhost:2538/callback", google2Client, parameterClient);

        final Config config = new Config(clients);
        config.setHttpActionAdapter(new DDGHttpActionAdapter());
        return config;
    }

}
