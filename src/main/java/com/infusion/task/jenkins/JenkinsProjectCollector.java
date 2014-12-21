package com.infusion.task.jenkins;

import com.infusion.client.ClientFactory;
import com.infusion.data.jenkins.JenkinsCredentials;
import com.infusion.model.jenkins.json.JenkinsProject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.util.UriUtils;

class JenkinsProjectCollector {

    private static final String PROJECT_PATH_PATTERN = "%s/job/%s/api/json";
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ClientFactory clientFactory;
    private JenkinsCredentials credentials;
    private String baseUrl;
    private String projectName;

    private JenkinsProjectCollector() {
    }

    JenkinsProject get() throws IOException {
        String projectUrlPath = getProjectUrlPath(projectName);
        JenkinsProject project = clientFactory.createJenkinsClient(credentials).
            get(projectUrlPath, JenkinsProject.class);

        return project;
    }

    private String getProjectUrlPath(String projectName) {
        String urlPath = String.format(PROJECT_PATH_PATTERN, baseUrl, projectName);

        // dirty hack to prevent double encoding of URL
        if (urlPath.contains("%")) {
            return urlPath;
        }

        try {
            String encodedUrlPath = UriUtils.encodePath(urlPath, "UTF-8");
            return encodedUrlPath;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Character Encoding is not supported for " + urlPath);
        }
    }

    static class Builder {

        private ClientFactory clientFactory;
        private JenkinsCredentials credentials;
        private String baseUrl;
        private String projectName;

        Builder clientFactory(ClientFactory clientFactory) {
            this.clientFactory = clientFactory;
            return this;
        }

        Builder credentials(JenkinsCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        JenkinsProjectCollector build() {
            Assert.notNull(clientFactory);
            Assert.notNull(credentials);
            Assert.hasText(baseUrl);
            Assert.hasText(projectName);

            JenkinsProjectCollector instance = new JenkinsProjectCollector();
            instance.clientFactory = clientFactory;
            instance.credentials = credentials;
            instance.baseUrl = baseUrl;
            instance.projectName = projectName;

            return instance;
        }
    }
}
