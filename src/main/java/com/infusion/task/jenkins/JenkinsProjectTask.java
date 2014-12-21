package com.infusion.task.jenkins;

import com.infusion.client.ClientFactory;
import com.infusion.data.jenkins.JenkinsCredentials;
import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.mapper.JenkinsDataMapper;
import com.infusion.model.JenkinsAggregate;
import com.infusion.model.jenkins.json.JenkinsBuildStatus;
import com.infusion.model.jenkins.json.JenkinsProject;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

public class JenkinsProjectTask implements Callable<JenkinsProjectData> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadPoolTaskExecutor executor;
    private Integer requestTimeout;
    private TimeUnit requestTimeoutUnit;
    private ClientFactory clientFactory;
    private JenkinsCredentials credentials;
    private String baseUrl;
    private String projectName;
    private JenkinsDataMapper mapper;
    private Integer maximumBuilds;

    private JenkinsProjectTask() {
    }

    @Override
    public JenkinsProjectData call() throws Exception {
        JenkinsAggregate aggregate = new JenkinsAggregate();

        JenkinsProject project = new JenkinsProjectCollector.Builder().
            clientFactory(clientFactory).
            credentials(credentials).
            baseUrl(baseUrl).
            projectName(projectName).build().get();
        String trendMapContent = new JenkinsTrendMapContentCollector.Builder().
            clientFactory(clientFactory).
            credentials(credentials).
            build().get(project);
        List<JenkinsBuildStatus> buildStatuses = new JenkinsBuildStatusCollector.Builder().
            executor(executor).
            requestTimeout(requestTimeout).
            requestTimeoutUnit(requestTimeoutUnit).
            clientFactory(clientFactory).
            credentials(credentials).
            baseUrl(baseUrl).
            projectName(projectName).build().get(project, maximumBuilds);
        aggregate.setProject(project);
        aggregate.setBuildStatuses(buildStatuses);
        aggregate.setTrendMapContent(trendMapContent);

        JenkinsProjectData dataObj = mapper.transform(aggregate);
        return dataObj;
    }

    public static class Builder {

        private ThreadPoolTaskExecutor executor;
        private Integer requestTimeout;
        private TimeUnit requestTimeoutUnit;
        private ClientFactory clientFactory;
        private JenkinsCredentials credentials;
        private String baseUrl;
        private String projectName;
        private JenkinsDataMapper mapper;
        private Integer maximumBuilds;

        public Builder executor(ThreadPoolTaskExecutor executor) {
            this.executor = executor;
            return this;
        }

        public Builder requestTimeout(Integer requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        public Builder requestTimeoutUnit(TimeUnit requestTimeoutUnit) {
            this.requestTimeoutUnit = requestTimeoutUnit;
            return this;
        }

        public Builder clientFactory(ClientFactory clientFactory) {
            this.clientFactory = clientFactory;
            return this;
        }

        public Builder credentials(JenkinsCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder mapper(JenkinsDataMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public Builder maximumBuilds(Integer maximumBuilds) {
            this.maximumBuilds = maximumBuilds;
            return this;
        }

        public JenkinsProjectTask build() {
            Assert.notNull(executor);
            Assert.isTrue(requestTimeout > 0);
            Assert.notNull(requestTimeoutUnit);
            Assert.notNull(clientFactory);
            Assert.notNull(credentials);
            Assert.hasText(baseUrl);
            Assert.hasText(projectName);
            Assert.notNull(mapper);
            Assert.isTrue(maximumBuilds > 0);

            JenkinsProjectTask task = new JenkinsProjectTask();
            task.executor = executor;
            task.requestTimeout = requestTimeout;
            task.requestTimeoutUnit = requestTimeoutUnit;
            task.clientFactory = clientFactory;
            task.credentials = credentials;
            task.baseUrl = baseUrl;
            task.projectName = projectName;
            task.mapper = mapper;
            task.maximumBuilds = maximumBuilds;

            return task;
        }
    }
}
