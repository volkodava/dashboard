package com.infusion.client;

import com.infusion.client.jenkins.JenkinsClient;
import com.infusion.data.jenkins.JenkinsCredentials;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ClientFactory {

    public JenkinsClient createJenkinsClient(JenkinsCredentials credentials) {
        Assert.notNull(credentials);
        Assert.hasText(credentials.getUrl());
        Assert.hasText(credentials.getUsername());
        Assert.hasText(credentials.getPassword());

        return new JenkinsClient(credentials.getUrl(),
            credentials.getUsername(),
            credentials.getPassword());
    }
}
