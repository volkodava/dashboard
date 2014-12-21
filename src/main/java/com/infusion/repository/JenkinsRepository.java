package com.infusion.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.infusion.client.ClientFactory;
import com.infusion.data.jenkins.JenkinsCredentials;
import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.mapper.JenkinsDataMapper;
import com.infusion.task.jenkins.JenkinsProjectTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JenkinsRepository {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired(required = true)
    private ThreadPoolTaskExecutor executor;
    @Autowired(required = true)
    private JenkinsDataMapper mapper;
    @Autowired(required = true)
    private ClientFactory clientFactory;
    private Cache<String, JenkinsProjectData> cache;
    @Value("${jenkins.per-request.timeoutInSec}")
    private Integer requestTimeout;
    private TimeUnit requestTimeoutUnit;
    @Value("${jenkins.cache.durationInSec}")
    private Integer duration;
    private TimeUnit durationUnit;
    @Value("${jenkins.cache.maximumSize}")
    private Integer maximumSize;
    @Value("${jenkins.cache.maximumBuilds}")
    private Integer maximumBuilds;
    @Value("${jenkins.url}")
    private String baseUrl;
    @Value("${jenkins.username}")
    private String username;
    @Value("${jenkins.password}")
    private String password;

    /**
     * Returns the value associated with {@code projectName} in this cache,
     * obtaining that value from {@code service} if necessary.
     *
     * @param projectName
     * @return
     */
    public JenkinsProjectData getJenkinsProject(String projectName) {
        Assert.hasText(projectName);

        JenkinsProjectData dataObj;
        try {
            dataObj = cache.get(projectName, new JenkinsProjectTask.Builder().
                executor(executor).
                requestTimeout(requestTimeout).
                requestTimeoutUnit(requestTimeoutUnit).
                clientFactory(clientFactory).
                credentials(new JenkinsCredentials(baseUrl, username, password)).
                baseUrl(baseUrl).
                projectName(projectName).
                mapper(mapper).
                maximumBuilds(maximumBuilds).
                build()
            );
        } catch (ExecutionException ex) {
            logger.error("Error was thrown while loading the value for project " + projectName, ex);
            throw new RuntimeException("Can't load the value.");
        }

        return dataObj;
    }

    /**
     * Returns the value associated with {@code key} in this cache, or
     * {@code null} if there is no cached value for {@code key}.
     *
     * @param projectName
     * @return
     */
    public JenkinsProjectData getJenkinsProjectIfPresent(String projectName) {
        Assert.hasText(projectName);

        return cache.getIfPresent(projectName);
    }

    private void buildCaches() {
        cache = CacheBuilder.newBuilder().expireAfterWrite(duration, durationUnit)
            .maximumSize(maximumSize)
            .build();
    }

    @PostConstruct
    public void postInit() {
        Assert.isTrue(requestTimeout > 0);
        Assert.notNull(duration);
        Assert.isTrue(duration > 0);
        Assert.isTrue(maximumSize > 0);
        Assert.isTrue(maximumBuilds > 0);
        Assert.hasText(baseUrl);
        Assert.hasText(username);
        Assert.hasText(password);

        durationUnit = TimeUnit.SECONDS;
        requestTimeoutUnit = TimeUnit.SECONDS;
        buildCaches();
    }
}
