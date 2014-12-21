package com.infusion.repository;

import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.verifier.JenkinsVerifier;
import static com.jayway.awaitility.Awaitility.await;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-jenkins-repository-spring-config.xml"})
public class JenkinsRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static JenkinsVerifier verifier = new JenkinsVerifier();
    @Autowired(required = true)
    private JenkinsRepository repository;

    @Test
    public void testGetJenkinsProject() {
        final String projectName = "Test integration - soapui";

        JenkinsProjectData project = repository.getJenkinsProject(projectName);
        verifier.checkJenkinsProjectDto(project);
    }

    @Test
    public void testCachedEntryEvictionPolicy() {
        final String projectName = "Test integration - soapui";

        // refresh underlying cache
        JenkinsProjectData project = repository.getJenkinsProject(projectName);
        assertThat(project, is(notNullValue()));

        Callable<Boolean> projectExpired = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                boolean expired = false;
                JenkinsProjectData project = repository.getJenkinsProjectIfPresent(projectName);
                if (project == null) {
                    expired = true;
                }

                return expired;
            }
        };

        await().atMost(10, TimeUnit.SECONDS).until(projectExpired, is(true));
    }

}
