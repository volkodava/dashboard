package com.infusion.controllers.it;

import com.infusion.ui.converter.ExtJsConverter;
import com.jayway.jsonpath.JsonPath;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.fluent.Request;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

public class JenkinsControllerTestIT {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String PROJECT_NAME = "Test integration - soapui";
    private RestTemplate restTemplate;

    private static String endpointUrl;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new StringHttpMessageConverter());
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
    }

    @Test
    public void testReadProjectInfoAboutSpecificProject() throws Exception {
        final String URL = encodePath(endpointUrl + "/services/jenkins/project/" + PROJECT_NAME);

        logger.info("Service url: {}", URL);

        String content = Request.Get(URL).execute().returnContent().asString();
        Boolean requestSuccessful = JsonPath.parse(content).read("$.success", Boolean.class);
        String projectName = JsonPath.parse(content).read("$.data.name", String.class);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(projectName, not(isEmptyOrNullString()));
        assertThat(PROJECT_NAME, equalTo(projectName));
    }

    @Test
    public void testReadAllProjectBuildsOfSpecificProject() throws Exception {
        final String URL = encodePath(endpointUrl + "/services/jenkins/project/builds/" + PROJECT_NAME);

        logger.info("Service url: {}", URL);

        String content = Request.Get(URL).execute().returnContent().asString();
        Boolean requestSuccessful = JsonPath.parse(content).read("$.success", Boolean.class);
        Integer buildNumber = JsonPath.parse(content).read("$.data[0].buildNumber", Integer.class);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(buildNumber, greaterThan(0));
    }

    @Test
    public void testReadTrendMapPointsOfSpecificProject() throws Exception {
        final String URL = encodePath(endpointUrl + "/services/jenkins/project/trendMap/" + PROJECT_NAME);

        logger.info("Service url: {}", URL);

        String content = Request.Get(URL).execute().returnContent().asString();
        Boolean requestSuccessful = JsonPath.parse(content).read("$.success", Boolean.class);
        String trendMapContent = JsonPath.parse(content).read("$.data", String.class);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(trendMapContent, not(isEmptyOrNullString()));
    }

    @Test
    public void testReadLatestBuildsPerEnvironmentOfSpecificProject() throws Exception {
        final String URL = encodePath(endpointUrl + "/services/jenkins/project/environmentStatuses/" + PROJECT_NAME);

        logger.info("Service url: {}", URL);

        String content = Request.Get(URL).execute().returnContent().asString();
        Boolean requestSuccessful = JsonPath.parse(content).read("$.success", Boolean.class);
        Integer buildNumber = JsonPath.parse(content).read("$.data[0].buildNumber", Integer.class);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(buildNumber, greaterThan(0));
    }

    @Test
    public void testReadProjectInfoAboutSpecificProjectWithRestTemplate() throws Exception {
        final String URL = encodePath(endpointUrl + "/services/jenkins/project/" + PROJECT_NAME);

        logger.info("Service url: {}", URL);

        Map<String, Object> resultMap = restTemplate.getForObject(URL, Map.class);
        Boolean requestSuccessful = (Boolean) resultMap.get(ExtJsConverter.SUCCESS_FLAG_FIELD);
        LinkedHashMap<String, Object> jenkinsProject = (LinkedHashMap<String, Object>) resultMap.get(ExtJsConverter.DATA_FIELD);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(jenkinsProject, is(notNullValue()));
        assertThat(PROJECT_NAME, equalTo(jenkinsProject.get("name")));
    }

    @Test
    public void testReadBuildDetailsForSpecificProjectNameEnvironmentAndBuildNumberWithRestTemplate() throws Exception {
        final String ENVIRONMENT = "integration";
        final int BUILD_NUMBER = 96;

        final String URL = encodePath(endpointUrl + "/services/jenkins/project/build/" + PROJECT_NAME)
            + "?env=" + ENVIRONMENT + "&build=" + BUILD_NUMBER;

        logger.info("Service url: {}", URL);

        Map<String, Object> resultMap = restTemplate.getForObject(URL, Map.class);
        Boolean requestSuccessful = (Boolean) resultMap.get(ExtJsConverter.SUCCESS_FLAG_FIELD);
        LinkedHashMap<String, Object> jenkinsBuild = (LinkedHashMap<String, Object>) resultMap.get(ExtJsConverter.DATA_FIELD);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(jenkinsBuild, is(notNullValue()));
        assertThat(ENVIRONMENT, equalTo(jenkinsBuild.get("selectedEnvironment")));
        assertThat(BUILD_NUMBER, equalTo(jenkinsBuild.get("buildNumber")));
    }

    @Test
    public void testReadModuleVersionsForSpecificProjectDeployedOnSpecificEnvironmentWithRestTemplate() throws Exception {
        final String ENVIRONMENT = "qa2";
        final String VERSIONS_KEY = "Versions";

        final String URL = encodePath(endpointUrl + "/services/jenkins/project/versions/" + PROJECT_NAME)
            + "?env=" + ENVIRONMENT;

        logger.info("Service url: {}", URL);

        Map<String, Object> resultMap = restTemplate.getForObject(URL, Map.class);
        Boolean requestSuccessful = (Boolean) resultMap.get(ExtJsConverter.SUCCESS_FLAG_FIELD);
        LinkedHashMap<String, Object> jenkinsModuleVersions = (LinkedHashMap<String, Object>) resultMap.get(ExtJsConverter.DATA_FIELD);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(jenkinsModuleVersions, is(notNullValue()));
        assertThat(VERSIONS_KEY, not(isEmptyOrNullString()));
    }

    @Test
    public void testReadProjectBuildsByEnvironment() throws Exception {
        final String ENVIRONMENT = "qa2";

        final String URL = encodePath(endpointUrl + "/services/jenkins/project/buildsForEnv/" + PROJECT_NAME)
            + "?env=" + ENVIRONMENT;

        logger.info("Service url: {}", URL);

        String content = Request.Get(URL).execute().returnContent().asString();
        Boolean requestSuccessful = JsonPath.parse(content).read("$.success", Boolean.class);
        String selectedEnvironment = JsonPath.parse(content).read("$.data[0].selectedEnvironment", String.class);
        Integer buildNumber = JsonPath.parse(content).read("$.data[0].buildNumber", Integer.class);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(selectedEnvironment, equalTo(ENVIRONMENT));
        assertThat(buildNumber, greaterThan(0));
    }

    @Test
    public void testReadServiceStatusesForSpecificProjectDeployedOnSpecificEnvironment() throws Exception {
        final String URL = encodePath(endpointUrl + "/services/jenkins/project/serviceStatuses/" + PROJECT_NAME);

        logger.info("Service url: {}", URL);

        String content = Request.Get(URL).execute().returnContent().asString();
        Boolean requestSuccessful = JsonPath.parse(content).read("$.success", Boolean.class);
        String selectedEnvironment = JsonPath.parse(content).read("$.data[0].selectedEnvironment", String.class);
        Integer buildNumber = JsonPath.parse(content).read("$.data[0].buildNumber", Integer.class);
        String status = JsonPath.parse(content).read("$.data[0].status", String.class);

        assertThat(requestSuccessful, is(Boolean.TRUE));
        assertThat(selectedEnvironment, not(isEmptyOrNullString()));
        assertThat(buildNumber, greaterThan(0));
        assertThat(status, not(isEmptyOrNullString()));
    }

    private String encodePath(String urlPath) {
        try {
            String encodedUrlPath = UriUtils.encodePath(urlPath, "UTF-8");
            return encodedUrlPath;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Character Encoding is not supported for " + urlPath, ex);
        }
    }
}
