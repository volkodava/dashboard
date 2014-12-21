package com.infusion.client.jenkins;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

public class JenkinsClient {

    private String url;
    private String username;
    private String password;

    private ObjectMapper objectMapper;
    private DefaultHttpClient client;
    private BasicHttpContext localContext;

    public JenkinsClient(String url, String username, String password) {
        Assert.hasText(url);
        Assert.hasText(username);
        Assert.hasText(password);

        this.url = url;
        this.username = username;
        this.password = password;

        this.objectMapper = getDefaultMapper();
        this.client = new DefaultHttpClient();

        try {
            authenticate();
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public <T> T get(String path, Class<T> clazz) throws IOException {
        HttpGet httpGet = new HttpGet(path);
        HttpResponse response = client.execute(httpGet, localContext);
        try {
            String content = EntityUtils.toString(response.getEntity());
            T result = objectMapper.readValue(content, clazz);
            return result;
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
            httpGet.releaseConnection();
        }
    }

    public String get(String path) throws IOException {
        HttpGet httpGet = new HttpGet(path);
        HttpResponse response = client.execute(httpGet, localContext);
        try {
            String content = EntityUtils.toString(response.getEntity());
            return content;
        } finally {
            EntityUtils.consumeQuietly(response.getEntity());
            httpGet.releaseConnection();
        }
    }

    private void authenticate() throws URISyntaxException {
        // https://wiki.jenkins-ci.org/display/JENKINS/Authenticating+scripted+clients
        URI uri = new URI(url);
        CredentialsProvider provider = client.getCredentialsProvider();
        AuthScope scope = new AuthScope(uri.getHost(), uri.getPort(), "realm");
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(scope, credentials);

        localContext = new BasicHttpContext();
        localContext.setAttribute("preemptive-auth", new BasicScheme());
        client.addRequestInterceptor(new PreemptiveAuth(), 0);
    }

    private ObjectMapper getDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig.without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }
}
