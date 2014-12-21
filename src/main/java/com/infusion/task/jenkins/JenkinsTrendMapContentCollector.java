package com.infusion.task.jenkins;

import com.infusion.client.ClientFactory;
import com.infusion.data.jenkins.JenkinsCredentials;
import com.infusion.model.jenkins.json.JenkinsProject;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

class JenkinsTrendMapContentCollector {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String TREND_MAP_RELATIVE_URL = "test/trendMap";
    private ClientFactory clientFactory;
    private JenkinsCredentials credentials;

    private JenkinsTrendMapContentCollector() {
    }

    String get(JenkinsProject project) throws IOException {
        String trendMapUrl = getTrendMapUrl(project);

        String content = clientFactory.createJenkinsClient(credentials).
            get(trendMapUrl);

        String resultContent = fixTrendMapLinks(project, content);
        return resultContent;
    }

    private String getTrendMapUrl(JenkinsProject project) {
        String projectUrl = project.getUrl();

        if (projectUrl.endsWith("/")) {
            return projectUrl + TREND_MAP_RELATIVE_URL;
        }

        return String.format("%s/%s", projectUrl, TREND_MAP_RELATIVE_URL);
    }

    private String fixTrendMapLinks(JenkinsProject project, String content) {
        String regexMatch = "href=\"";
        String replacement = "target=\"_blank\" href=\"";
        String projectUrl = project.getUrl();
        if (projectUrl.endsWith("/")) {
            replacement += projectUrl;
        } else {
            replacement += projectUrl + "/";
        }

        String resultContent = content.replaceAll(regexMatch, replacement);
        return resultContent;
    }

    static class Builder {

        private ClientFactory clientFactory;
        private JenkinsCredentials credentials;

        Builder clientFactory(ClientFactory clientFactory) {
            this.clientFactory = clientFactory;
            return this;
        }

        Builder credentials(JenkinsCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        JenkinsTrendMapContentCollector build() {
            Assert.notNull(clientFactory);
            Assert.notNull(credentials);

            JenkinsTrendMapContentCollector instance = new JenkinsTrendMapContentCollector();
            instance.clientFactory = clientFactory;
            instance.credentials = credentials;

            return instance;
        }
    }
}
