package com.infusion.task.jenkins;

import com.infusion.client.ClientFactory;
import com.infusion.data.jenkins.JenkinsCredentials;
import com.infusion.model.jenkins.json.Build;
import com.infusion.model.jenkins.json.JenkinsBuildStatus;
import com.infusion.model.jenkins.json.JenkinsProject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.web.util.UriUtils;

class JenkinsBuildStatusCollector {

    private static final String BUILD_STATUS_PATH_PATTERN = "%s/job/%s/%s/api/json";
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadPoolTaskExecutor executor;
    private Integer requestTimeout;
    private TimeUnit requestTimeoutUnit;
    private ClientFactory clientFactory;
    private JenkinsCredentials credentials;
    private String baseUrl;
    private String projectName;

    private JenkinsBuildStatusCollector() {
    }

    List<JenkinsBuildStatus> get(JenkinsProject project, Integer maximumBuilds) {
        Assert.notNull(project);
        Assert.isTrue(maximumBuilds > 0);

        List<JenkinsBuildStatus> buildStatuses = new ArrayList<JenkinsBuildStatus>();
        List<Build> builds = project.getBuilds();
        if (builds == null || builds.isEmpty()) {
            return buildStatuses;
        }

        List<Future<JenkinsBuildStatus>> futures = new ArrayList<Future<JenkinsBuildStatus>>();
        int processedBuilds = 0;
        int buildsSize = builds.size();
        for (Build build : builds) {
            processedBuilds++;
            buildsSize--;
            int buildNumber = build.getNumber();
            logger.debug("Processing build #" + buildNumber + ". Left: " + buildsSize);
            String buildStatusUrlPath = getBuildStatusUrlPath(projectName, buildNumber);

            Future<JenkinsBuildStatus> future = executor.submit(new JenkinsClientWorker.Builder<JenkinsBuildStatus>().
                clientFactory(clientFactory).
                credentials(credentials).
                url(buildStatusUrlPath).
                objectClazz(JenkinsBuildStatus.class).
                build()
            );
            futures.add(future);

            if (processedBuilds == maximumBuilds) {
                // if we reach to maximum -> exit loop
                break;
            }
        }

        logger.debug("Collecting build statuses from " + futures.size() + " tasks.");
        for (Future<JenkinsBuildStatus> future : futures) {
            try {
                JenkinsBuildStatus buildStatus = future.get(requestTimeout, requestTimeoutUnit);
                buildStatuses.add(buildStatus);
            } catch (InterruptedException ex) {
                logger.error("The request's thread was interrupted while waiting", ex);
            } catch (ExecutionException ex) {
                logger.error("The request threw an exception", ex);
            } catch (TimeoutException ex) {
                logger.error("The request's wait timed out", ex);
            }
        }

        return buildStatuses;
    }

    private String getBuildStatusUrlPath(String projectName, Integer buildNumber) {
        String urlPath = String.format(BUILD_STATUS_PATH_PATTERN, baseUrl, projectName, buildNumber);

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

        private ThreadPoolTaskExecutor executor;
        private Integer requestTimeout;
        private TimeUnit requestTimeoutUnit;
        private ClientFactory clientFactory;
        private JenkinsCredentials credentials;
        private String baseUrl;
        private String projectName;

        Builder executor(ThreadPoolTaskExecutor executor) {
            this.executor = executor;
            return this;
        }

        Builder requestTimeout(Integer requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        Builder requestTimeoutUnit(TimeUnit requestTimeoutUnit) {
            this.requestTimeoutUnit = requestTimeoutUnit;
            return this;
        }

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

        JenkinsBuildStatusCollector build() {
            Assert.notNull(executor);
            Assert.isTrue(requestTimeout > 0);
            Assert.notNull(requestTimeoutUnit);
            Assert.notNull(clientFactory);
            Assert.notNull(credentials);
            Assert.hasText(baseUrl);
            Assert.hasText(projectName);

            JenkinsBuildStatusCollector instance = new JenkinsBuildStatusCollector();
            instance.executor = executor;
            instance.requestTimeout = requestTimeout;
            instance.requestTimeoutUnit = requestTimeoutUnit;
            instance.clientFactory = clientFactory;
            instance.credentials = credentials;
            instance.baseUrl = baseUrl;
            instance.projectName = projectName;

            return instance;
        }
    }
}
