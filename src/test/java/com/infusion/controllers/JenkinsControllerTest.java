package com.infusion.controllers;

import com.infusion.service.JenkinsService;
import com.infusion.ui.data.jenkins.JenkinsBuildDataUI;
import com.infusion.ui.data.jenkins.JenkinsProjectDataUI;
import com.infusion.ui.data.jenkins.ServiceStatus;
import com.infusion.ui.data.jenkins.ServiceUI;
import java.io.IOException;
import java.util.Collections;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-jenkins-controller-spring-config.xml"})
public class JenkinsControllerTest {

    private static final String PROJECT_NAME = "SomeCoolProject";
    private static final String PROJECT_INFO_URI = "/jenkins/project/" + PROJECT_NAME;
    private static final String PROJECT_BUILDS_URI = "/jenkins/project/builds/" + PROJECT_NAME;
    private static final String PROJECT_TREND_MAP_URI = "/jenkins/project/trendMap/" + PROJECT_NAME;
    private static final String ENVIRONMENT_STATUSES_URI = "/jenkins/project/environmentStatuses/" + PROJECT_NAME;
    private static final String PROJECT_BUILD_URI = "/jenkins/project/build/" + PROJECT_NAME;
    private static final String ENVIRONMENT_MODULE_VERSIONS_URI = "/jenkins/project/versions/" + PROJECT_NAME;
    private static final String PROJECT_BUILDS_FOR_ENV_URI = "/jenkins/project/buildsForEnv/" + PROJECT_NAME;
    private static final String SERVICE_STATUSES_FOR_ENV_URI = "/jenkins/project/serviceStatuses/" + PROJECT_NAME;

    private MockMvc mockMvc;

    @Autowired(required = true)
    private JenkinsService service;

    @Autowired(required = true)
    private WebApplicationContext webApplicationContext;

    private JenkinsBuildDataUI buildDataUI;
    private JenkinsProjectDataUI projectDataUI;

    @Before
    public void setUp() throws IOException {
        reset(service);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        buildDataUI = new JenkinsBuildDataUI();

        projectDataUI = new JenkinsProjectDataUI();
        projectDataUI.setName(PROJECT_NAME);
        when(service.getProjectInfo(PROJECT_NAME)).thenReturn(projectDataUI);

        when(service.getProjectBuilds(PROJECT_NAME)).thenReturn(Collections.singletonList(buildDataUI));
        when(service.getTrendMapContent(PROJECT_NAME)).thenReturn("content");
        when(service.getEnvironmentStatuses(PROJECT_NAME)).thenReturn(Collections.singletonList(buildDataUI));
    }

    @Test
    public void testReadProjectInfoAboutSpecificProject() throws Exception {
        mockMvc.perform(get(PROJECT_INFO_URI)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data.name", is(PROJECT_NAME)));
    }

    @Test
    public void testReadAllProjectBuildsOfSpecificProject() throws Exception {
        final int BUILD_NUMBER = 100;
        buildDataUI.setBuildNumber(BUILD_NUMBER);

        mockMvc.perform(get(PROJECT_BUILDS_URI)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data[0].buildNumber", equalTo(BUILD_NUMBER)));
    }

    @Test
    public void testReadTrendMapPointsOfSpecificProject() throws Exception {
        mockMvc.perform(get(PROJECT_TREND_MAP_URI)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.data", is("content")));
    }

    @Test
    public void testReadLatestBuildsPerEnvironmentOfSpecificProject() throws Exception {
        final int BUILD_NUMBER = 100;
        buildDataUI.setBuildNumber(BUILD_NUMBER);

        mockMvc.perform(get(ENVIRONMENT_STATUSES_URI)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data[0].buildNumber", equalTo(BUILD_NUMBER)));
    }

    @Test
    public void testReadBuildDetailsForSpecificProjectNameEnvironmentAndBuildNumber() throws Exception {
        final String ENVIRONMENT = "integration";
        final int BUILD_NUMBER = 96;

        buildDataUI.setSelectedEnvironment(ENVIRONMENT);
        buildDataUI.setBuildNumber(BUILD_NUMBER);
        when(service.getProjectBuildByEnvAndNumber(PROJECT_NAME, ENVIRONMENT, BUILD_NUMBER)).thenReturn(buildDataUI);

        mockMvc.perform(get(PROJECT_BUILD_URI)
            .param("env", ENVIRONMENT)
            .param("build", "" + BUILD_NUMBER)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data.selectedEnvironment", equalTo(ENVIRONMENT)))
            .andExpect(jsonPath("$.data.buildNumber", equalTo(BUILD_NUMBER)));
    }

    @Test
    public void testReadModuleVersionsForSpecificProjectDeployedOnSpecificEnvironment() throws Exception {
        final String ENVIRONMENT = "qa2";
        final String VERSIONS_KEY = "Versions";
        final String VERSIONS_VALUE = "1.2";

        buildDataUI.setSelectedEnvironment(ENVIRONMENT);
        when(service.getModuleVersionsForEnvironment(PROJECT_NAME, ENVIRONMENT)).thenReturn(Collections.<String, Object>singletonMap(VERSIONS_KEY, VERSIONS_VALUE));

        mockMvc.perform(get(ENVIRONMENT_MODULE_VERSIONS_URI)
            .param("env", ENVIRONMENT)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data.Versions", equalTo(VERSIONS_VALUE)));
    }

    @Test
    public void testReadProjectBuildsByEnvironment() throws Exception {
        final String ENVIRONMENT = "qa2";

        buildDataUI.setSelectedEnvironment(ENVIRONMENT);
        when(service.getProjectBuildsForEnv(PROJECT_NAME, ENVIRONMENT)).thenReturn(Collections.<JenkinsBuildDataUI>singletonList(buildDataUI));

        mockMvc.perform(get(PROJECT_BUILDS_FOR_ENV_URI)
            .param("env", ENVIRONMENT)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data[0].selectedEnvironment", equalTo(ENVIRONMENT)));
    }

    @Test
    public void testReadServiceStatusesForSpecificProjectDeployedOnSpecificEnvironment() throws Exception {
        final String ENVIRONMENT = "qa2";
        final int BUILD_NUMBER = 96;
        final ServiceStatus SERVICE_STATUS = ServiceStatus.UP;

        ServiceUI serviceUI = new ServiceUI(BUILD_NUMBER, ENVIRONMENT, SERVICE_STATUS);
        when(service.getServiceStatuses(PROJECT_NAME)).thenReturn(Collections.<ServiceUI>singletonList(serviceUI));

        mockMvc.perform(get(SERVICE_STATUSES_FOR_ENV_URI)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.success", is(Boolean.TRUE)))
            .andExpect(jsonPath("$.data[0].selectedEnvironment", equalTo(ENVIRONMENT)))
            .andExpect(jsonPath("$.data[0].buildNumber", equalTo(BUILD_NUMBER)))
            .andExpect(jsonPath("$.data[0].status", equalTo(SERVICE_STATUS.name())));
    }
}
