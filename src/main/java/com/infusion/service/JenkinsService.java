package com.infusion.service;

import com.infusion.data.jenkins.JenkinsBuildData;
import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.repository.JenkinsRepository;
import com.infusion.ui.data.jenkins.ArtifactUI;
import com.infusion.ui.data.jenkins.JenkinsBuildDataUI;
import com.infusion.ui.data.jenkins.JenkinsProjectDataUI;
import com.infusion.ui.data.jenkins.ServiceStatus;
import com.infusion.ui.data.jenkins.ServiceUI;
import com.infusion.ui.mapper.JenkinsDataMapperUI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class JenkinsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired(required = true)
    private JenkinsRepository repository;
    @Autowired(required = true)
    private JenkinsDataMapperUI mapper;
    @Autowired(required = true)
    private RestTemplate restTemplate;
    @Autowired(required = true)
    @Qualifier("environmentProperties")
    private Properties environmentProperties;
    @Value("${jenkins.username}")
    private String username;
    @Value("${jenkins.password}")
    private String password;

    public JenkinsProjectDataUI getProjectInfo(String projectName) {
        JenkinsProjectData project = repository.getJenkinsProject(projectName);
        JenkinsProjectDataUI dataUI = mapper.transformToProjectData(project);

        return dataUI;
    }

    public Collection<JenkinsBuildDataUI> getProjectBuilds(String projectName) {
        JenkinsProjectData project = repository.getJenkinsProject(projectName);
        Collection<JenkinsBuildDataUI> dataUI = mapper.transformToBuildData(project);

        return dataUI;
    }

    public String getTrendMapContent(String projectName) {
        JenkinsProjectData project = repository.getJenkinsProject(projectName);
        String trendMapContent = project.getTrendMapContent();

        return trendMapContent;
    }

    public Collection<JenkinsBuildDataUI> getEnvironmentStatuses(String projectName) {
        JenkinsProjectData project = repository.getJenkinsProject(projectName);
        Collection<JenkinsBuildDataUI> dataUI = mapper.transformToBuildDataGroupedByEnvironment(project);

        return dataUI;
    }

    public JenkinsBuildDataUI getProjectBuildByEnvAndNumber(String projectName, String environment, int buildNumber) {
        JenkinsProjectData project = repository.getJenkinsProject(projectName);

        JenkinsBuildData foundBuild = null;
        for (JenkinsBuildData buildData : project.getBuilds()) {
            int currBuildNumber = buildData.getBuildNumber();
            String currBuildEnv = buildData.getSelectedEnvironment();
            if (buildNumber == currBuildNumber && environment.equals(currBuildEnv)) {
                foundBuild = buildData;
                break;
            }
        }

        JenkinsBuildDataUI dataUI = null;
        if (foundBuild != null) {
            dataUI = mapper.transformToBuildData(foundBuild);
        }

        return dataUI;
    }

    public Map<String, Object> getModuleVersionsForEnvironment(String projectName, String environment) {
        String url = (String) environmentProperties.get(environment);

        if (url == null || url.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> responseMap = restTemplate.getForObject(url, Map.class);
        for (Iterator<Entry<String, Object>> it = responseMap.entrySet().iterator(); it.hasNext();) {
            Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            // TODO: [FAKED] Implement getting status of artifact
            if (value instanceof String) {
                result.put(key, new ArtifactUI((String) value, ServiceStatus.NONE));
            }
        }

        return result;
    }

    public Collection<JenkinsBuildDataUI> getProjectBuildsForEnv(String projectName, String environment) {
        JenkinsProjectData project = repository.getJenkinsProject(projectName);

        Collection<JenkinsBuildDataUI> result = new ArrayList<JenkinsBuildDataUI>();
        for (JenkinsBuildData buildData : project.getBuilds()) {
            if (environment.equals(buildData.getSelectedEnvironment())) {
                JenkinsBuildDataUI buildDataUI = mapper.transformToBuildData(buildData);
                result.add(buildDataUI);
            }
        }

        return result;
    }

    public Collection<ServiceUI> getServiceStatuses(String projectName) {
        // TODO: [FAKED] Implement real calls for this data!!!
        List<ServiceUI> servicesData = new ArrayList<ServiceUI>();
        servicesData.add(new ServiceUI(96, "integration", ServiceStatus.UP));
        servicesData.add(new ServiceUI(94, "qa2", ServiceStatus.DOWN));
        servicesData.add(new ServiceUI(89, "metl0026mwfu001.cloudapp.net", ServiceStatus.DOWN));

        return servicesData;
    }

    @PostConstruct
    public void postInit() {
        Assert.hasText(username);
        Assert.hasText(password);
    }

}
