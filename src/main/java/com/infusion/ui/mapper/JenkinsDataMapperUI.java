package com.infusion.ui.mapper;

import com.infusion.data.jenkins.JenkinsBuildData;
import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.ui.data.jenkins.JenkinsBuildDataUI;
import com.infusion.ui.data.jenkins.JenkinsProjectDataUI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JenkinsDataMapperUI {

    public Collection<JenkinsBuildDataUI> transformToBuildData(JenkinsProjectData dataObj) {
        List<JenkinsBuildDataUI> result = new ArrayList<JenkinsBuildDataUI>();
        for (JenkinsBuildData buildData : dataObj.getBuilds()) {
            JenkinsBuildDataUI instance = new JenkinsBuildDataUI();
            instance.setBuildNumber(buildData.getBuildNumber());
            instance.setDuration(buildData.getDuration());
            instance.setFailCount(buildData.getFailCount());
            instance.setReportUrl(buildData.getReportUrl());
            instance.setSelectedEnvironment(buildData.getSelectedEnvironment());
            instance.setSkipCount(buildData.getSkipCount());
            instance.setStatus(buildData.getStatus());
            instance.setTimestamp(buildData.getTimestamp());
            instance.setTotalCount(buildData.getTotalCount());

            result.add(instance);
        }

        return result;
    }

    public JenkinsProjectDataUI transformToProjectData(JenkinsProjectData dataObj) {
        JenkinsProjectDataUI instance = new JenkinsProjectDataUI();
        instance.setBuildStability(dataObj.getBuildStability());
        instance.setBuildStabilityScore(dataObj.getBuildStabilityScore());
        instance.setTestReport(dataObj.getTestReport());
        instance.setTestReportScore(dataObj.getTestReportScore());
        instance.setName(dataObj.getName());
        instance.setProjectUrl(dataObj.getProjectUrl());
        instance.setTrendUrl(dataObj.getTrendUrl());

        return instance;
    }

    public Collection<JenkinsBuildDataUI> transformToBuildDataGroupedByEnvironment(JenkinsProjectData dataObj) {
        HashMap<String, JenkinsBuildDataUI> resultAsMap = new HashMap<String, JenkinsBuildDataUI>();
        for (JenkinsBuildData buildData : dataObj.getBuilds()) {
            String environment = buildData.getSelectedEnvironment();
            int buildNumber = buildData.getBuildNumber();

            boolean setBuildToResult = false;
            JenkinsBuildDataUI value = resultAsMap.get(environment);
            if (value != null) {
                int storedBuildNumber = value.getBuildNumber();
                if (storedBuildNumber < buildNumber) {
                    // update result only for the latest build for environment
                    setBuildToResult = true;
                }
            } else {
                // update/set new value becuase it doesn't exists
                setBuildToResult = true;
            }

            if (setBuildToResult) {
                JenkinsBuildDataUI instance = new JenkinsBuildDataUI();
                instance.setBuildNumber(buildNumber);
                instance.setSelectedEnvironment(environment);
                instance.setDuration(buildData.getDuration());
                instance.setFailCount(buildData.getFailCount());
                instance.setReportUrl(buildData.getReportUrl());
                instance.setSkipCount(buildData.getSkipCount());
                instance.setStatus(buildData.getStatus());
                instance.setTimestamp(buildData.getTimestamp());
                instance.setTotalCount(buildData.getTotalCount());

                resultAsMap.put(environment, instance);
            }
        }

        return resultAsMap.values();
    }

    public JenkinsBuildDataUI transformToBuildData(JenkinsBuildData buildData) {
        JenkinsBuildDataUI buildDataUI = new JenkinsBuildDataUI();
        buildDataUI.setBuildNumber(buildData.getBuildNumber());
        buildDataUI.setDuration(buildData.getDuration());
        buildDataUI.setFailCount(buildData.getFailCount());
        buildDataUI.setReportUrl(buildData.getReportUrl());
        buildDataUI.setSelectedEnvironment(buildData.getSelectedEnvironment());
        buildDataUI.setSkipCount(buildData.getSkipCount());
        buildDataUI.setStatus(buildData.getStatus());
        buildDataUI.setTimestamp(buildData.getTimestamp());
        buildDataUI.setTotalCount(buildData.getTotalCount());

        return buildDataUI;
    }
}
