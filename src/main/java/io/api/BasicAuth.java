package io.api;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Provider
public class BasicAuth implements ContainerRequestFilter {

    private static String username = null;
    private static String password = null;
    private final String credentialsPath = "/var/foodTracker/config/credentials";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic")) {
            requestContext.abortWith(Response.status(401).header("WWW-Authenticate", "Basic").build());
            return;
        }

        String[] tokens = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), StandardCharsets.UTF_8)).split(":");
        final String username = tokens[0];
        final String password = tokens[1];

        if (BasicAuth.username == null && BasicAuth.password == null) {
            String providedCredentials;
            try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                providedCredentials = sb.toString();
            } catch (IOException e) {
                System.err.println("Error while reading credentials files");
                requestContext.abortWith(Response.status(402).build());
                return;
            }

            String[] providedTokens = providedCredentials.split(":");
            BasicAuth.username = providedTokens[0];
            BasicAuth.password = providedTokens[1];
        }

        if ((!username.equals(BasicAuth.username)) || (!password.equals(BasicAuth.password))) {
            requestContext.abortWith(Response.status(401).build());
            return;
        }
    }

}