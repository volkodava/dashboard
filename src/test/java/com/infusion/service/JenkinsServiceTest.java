package com.infusion.service;

import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.repository.JenkinsRepository;
import com.infusion.ui.data.jenkins.JenkinsBuildDataUI;
import com.infusion.ui.data.jenkins.JenkinsProjectDataUI;
import com.infusion.ui.mapper.JenkinsDataMapperUI;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsServiceTest {

    @InjectMocks
    private JenkinsService service;

    @Mock
    private JenkinsRepository repository;
    @Mock
    private JenkinsDataMapperUI mapper;

    @Test
    public void testGetProjectInfoByProjectName() {
        // given
        String projectName = "someProject";
        JenkinsProjectData dataObj = new JenkinsProjectData();
        dataObj.setName(projectName);
        JenkinsProjectDataUI dataUI = new JenkinsProjectDataUI();
        dataUI.setName(projectName);
        when(repository.getJenkinsProject(projectName)).thenReturn(dataObj);
        when(mapper.transformToProjectData(dataObj)).thenReturn(dataUI);

        // when
        JenkinsProjectDataUI resultDataUI = service.getProjectInfo(projectName);

        // then
        assertThat(resultDataUI, is(notNullValue()));
        assertThat(resultDataUI.getName(), equalTo(projectName));
    }

    @Test
    public void testGetProjectBuildsByProjectName() {
        // given
        String projectName = "someProject";
        Long buildTimestamp = System.currentTimeMillis();
        JenkinsProjectData dataObj = new JenkinsProjectData();
        dataObj.setName(projectName);
        JenkinsBuildDataUI buildDataUI = new JenkinsBuildDataUI();
        buildDataUI.setTimestamp(buildTimestamp);
        List<JenkinsBuildDataUI> buildDataUIs = Collections.singletonList(buildDataUI);
        when(repository.getJenkinsProject(projectName)).thenReturn(dataObj);
        when(mapper.transformToBuildData(dataObj)).thenReturn(buildDataUIs);

        // when
        Collection<JenkinsBuildDataUI> resultBuildDataUIs = service.getProjectBuilds(projectName);

        // then
        assertThat(resultBuildDataUIs, is(notNullValue()));
        assertThat(resultBuildDataUIs, hasSize(1));

        JenkinsBuildDataUI resultBuildDataUI = resultBuildDataUIs.iterator().next();
        assertThat(resultBuildDataUI.getTimestamp(), equalTo(buildTimestamp));
    }

    @Test
    public void testGetTrendMapContentByProjectInfoWithFixedLinks() throws IOException {
        // given
        String projectName = "someProject";
        String projectUrl = "http://someUrl";
        String trendMapContent = "target=\"_blank\" href=\"" + projectUrl + "/\"";
        JenkinsProjectData dataObj = new JenkinsProjectData();
        dataObj.setName(projectName);
        dataObj.setProjectUrl(projectUrl);
        dataObj.setTrendMapContent(trendMapContent);
        when(repository.getJenkinsProject(projectName)).thenReturn(dataObj);

        // when
        String resultContent = service.getTrendMapContent(projectName);

        // then
        assertThat(resultContent, not(isEmptyOrNullString()));
        assertThat(resultContent, equalTo(trendMapContent));
    }

    @Test
    public void testGetEnvironmentStatusesByProjectName() {
        // given
        String projectName = "someProject";
        Long buildTimestamp = System.currentTimeMillis();
        JenkinsProjectData dataObj = new JenkinsProjectData();
        dataObj.setName(projectName);
        JenkinsBuildDataUI buildDataUI = new JenkinsBuildDataUI();
        buildDataUI.setTimestamp(buildTimestamp);
        List<JenkinsBuildDataUI> buildDataUIs = Collections.singletonList(buildDataUI);
        when(repository.getJenkinsProject(projectName)).thenReturn(dataObj);
        when(mapper.transformToBuildDataGroupedByEnvironment(dataObj)).thenReturn(buildDataUIs);

        // when
        Collection<JenkinsBuildDataUI> resultBuildDataUIs = service.getEnvironmentStatuses(projectName);

        // then
        assertThat(resultBuildDataUIs, is(notNullValue()));
        assertThat(resultBuildDataUIs, hasSize(1));

        JenkinsBuildDataUI resultBuildDataUI = resultBuildDataUIs.iterator().next();
        assertThat(resultBuildDataUI.getTimestamp(), equalTo(buildTimestamp));
    }
}
