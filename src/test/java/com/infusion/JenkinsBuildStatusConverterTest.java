package com.infusion;

import com.infusion.core.TestResourcesContainer;
import com.infusion.model.jenkins.json.JenkinsBuildStatus;
import com.infusion.verifier.JenkinsVerifier;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;

public class JenkinsBuildStatusConverterTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static JenkinsVerifier verifier = new JenkinsVerifier();
    private MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    @BeforeClass
    public static void checkFilesWithContentExists() {
//        File projectContentFile = TestResourcesContainer.PROJECT_FILE_PATH;
//        assertThat(projectContentFile.exists(), is(true));

        File buildStatusFile = TestResourcesContainer.BUILD_STATUS_FILE_PATH;
        assertThat(buildStatusFile.exists(), is(true));
//
//        File buildEnvFile = TestResourcesContainer.BUILD_ENV_FILE_PATH;
//        assertThat(buildEnvFile.exists(), is(true));
//
//        File buildTestResultFile = TestResourcesContainer.BUILD_TEST_RESULT_FILE_PATH;
//        assertThat(buildTestResultFile.exists(), is(true));
//
//        File buildTestResultPackageFile = TestResourcesContainer.BUILD_TEST_RESULT_PACKAGE_FILE_PATH;
//        assertThat(buildTestResultPackageFile.exists(), is(true));
    }

    @Test
    public void testConvertProjectContentToObject() throws IOException {
        File projectJsonFile = TestResourcesContainer.BUILD_STATUS_FILE_PATH;
        String bodyContent = new String(Files.readAllBytes(projectJsonFile.toPath()));

        MockHttpInputMessage inputMessage = new MockHttpInputMessage(bodyContent.getBytes("UTF-8"));
        inputMessage.getHeaders().setContentType(new MediaType("application", "json"));
        JenkinsBuildStatus bodyObj = (JenkinsBuildStatus) converter.read(JenkinsBuildStatus.class, inputMessage);
        verifier.checkJenkinsBuildStatus(bodyObj);
    }

    @Test
    public void testConvertProjectContentToObjectAndViceVersa() throws IOException {
        File projectJsonFile = TestResourcesContainer.BUILD_STATUS_FILE_PATH;
        String sourceContent = new String(Files.readAllBytes(projectJsonFile.toPath()));
        sourceContent = sourceContent.replaceAll("\\s+", "");

        MockHttpInputMessage inputMessage = new MockHttpInputMessage(sourceContent.getBytes("UTF-8"));
        inputMessage.getHeaders().setContentType(new MediaType("application", "json"));
        JenkinsBuildStatus body = (JenkinsBuildStatus) converter.read(JenkinsBuildStatus.class, inputMessage);

        MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
        converter.write(body, null, outputMessage);
        Charset utf8 = Charset.forName("UTF-8");
        String resultContent = outputMessage.getBodyAsString(utf8);
        resultContent = resultContent.replaceAll("\\s+", "");

        logger.debug("\n" + sourceContent);
        logger.debug("\n" + resultContent);

        assertThat(sourceContent, equalTo(resultContent));
    }
}
