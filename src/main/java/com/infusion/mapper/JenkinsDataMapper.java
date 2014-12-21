package com.infusion.mapper;

import com.infusion.core.Constant;
import com.infusion.data.jenkins.JenkinsBuildData;
import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.model.JenkinsAggregate;
import com.infusion.model.jenkins.json.BuildStatusAction;
import com.infusion.model.jenkins.json.HealthReport;
import com.infusion.model.jenkins.json.JenkinsBuildStatus;
import com.infusion.model.jenkins.json.JenkinsProject;
import com.infusion.model.jenkins.json.Parameter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JenkinsDataMapper {

    private static final String ENVIRONMENT_PARAMETER_NAME = "selectedEnvironment";
    private static final String HEALTH_REPORT_BUILD_STABILITY_PREFIX = "Build stability";
    private static final String HEALTH_REPORT_TEST_RESULT_PREFIX = "Test Result";
    private static final String TREND_RELATIVE_URL = "test/trend";

    public JenkinsProjectData transform(JenkinsAggregate aggregate) {
        JenkinsProjectData dataObj = new JenkinsProjectData();
        JenkinsProject project = aggregate.getProject();
        if (project == null) {
            return dataObj;
        }

        List<JenkinsBuildStatus> buildStatuses = aggregate.getBuildStatuses();
        if (buildStatuses == null || buildStatuses.isEmpty()) {
            return dataObj;
        }

        dataObj.setName(project.getName());
        dataObj.setProjectUrl(project.getUrl());
        dataObj.setTrendUrl(getTrendUrl(project));
        dataObj.setTrendMapContent(aggregate.getTrendMapContent());
        setHealthReportToDto(project, dataObj);

        for (JenkinsBuildStatus buildStatus : aggregate.getBuildStatuses()) {
            JenkinsBuildData buildDto = new JenkinsBuildData();
            buildDto.setBuildNumber(buildStatus.getNumber());
            buildDto.setTimestamp(buildStatus.getTimestamp());
            buildDto.setStatus(buildStatus.getResult());
            buildDto.setDuration(buildStatus.getDuration());
            setActionsToDto(buildStatus, buildDto);
            dataObj.addJenkinsBuildDto(buildDto);
        }

        return dataObj;
    }

    private void setActionsToDto(JenkinsBuildStatus buildStatus, JenkinsBuildData buildDto) {
        // set default values
        buildDto.setReportUrl(Constant.NO_REPORT_URL_VALUE);
        buildDto.setFailCount(Constant.NO_FAIL_COUNT_VALUE);
        buildDto.setSkipCount(Constant.NO_SKIP_COUNT_VALUE);
        buildDto.setTotalCount(Constant.NO_TOTAL_COUNT_VALUE);
        buildDto.setSelectedEnvironment(Constant.NO_ENVIRONMENT_VALUE);

        List<BuildStatusAction> actions = buildStatus.getActions();
        if (actions == null || actions.isEmpty()) {
            return;
        }

        for (BuildStatusAction action : actions) {
            if (action.getUrlName() != null && !action.getUrlName().isEmpty()) {
                buildDto.setReportUrl(getReportUrl(buildStatus.getUrl(), action));
                buildDto.setFailCount(action.getFailCount());
                buildDto.setSkipCount(action.getSkipCount());
                buildDto.setTotalCount(action.getTotalCount());
            } else {
                setParametersToDto(action, buildDto);
            }
        }
    }

    private void setParametersToDto(BuildStatusAction action, JenkinsBuildData buildDto) {
        List<Parameter> parameters = action.getParameters();
        if (parameters == null || parameters.isEmpty()) {
            return;
        }

        for (Parameter p : parameters) {
            if (ENVIRONMENT_PARAMETER_NAME.equalsIgnoreCase(p.getName())) {
                buildDto.setSelectedEnvironment(getSelectedEnvironment(p));
                // nothing more to look for
                break;
            }
        }
    }

    private void setHealthReportToDto(JenkinsProject project, JenkinsProjectData dataObj) {
        List<HealthReport> healthReports = project.getHealthReport();
        if (healthReports == null || healthReports.isEmpty()) {
            return;
        }

        for (HealthReport hr : healthReports) {
            if (hr.getDescription() != null && !hr.getDescription().isEmpty()) {
                if (hr.getDescription().startsWith(HEALTH_REPORT_BUILD_STABILITY_PREFIX)) {
                    dataObj.setBuildStability(hr.getDescription());
                    dataObj.setBuildStabilityScore(hr.getScore());
                } else if (hr.getDescription().startsWith(HEALTH_REPORT_TEST_RESULT_PREFIX)) {
                    dataObj.setTestReport(hr.getDescription());
                    dataObj.setTestReportScore(hr.getScore());
                }
            }
        }
    }

    private String getTrendUrl(JenkinsProject project) {
        String projectUrl = project.getUrl();

        if (projectUrl.endsWith("/")) {
            return projectUrl + TREND_RELATIVE_URL;
        }

        return String.format("%s/%s", projectUrl, TREND_RELATIVE_URL);
    }

    private String getReportUrl(String buildUrl, BuildStatusAction statusAction) {
        String reportRelativeUrl = statusAction.getUrlName();

        if (buildUrl.endsWith("/")) {
            return buildUrl + reportRelativeUrl;
        }

        return String.format("%s/%s", buildUrl, reportRelativeUrl);
    }

    private String getSelectedEnvironment(Parameter parameter) {
        if (parameter == null) {
            return Constant.NO_ENVIRONMENT_VALUE;
        }

        String environment = parameter.getValue();
        if (environment == null || environment.isEmpty()) {
            return Constant.NO_ENVIRONMENT_VALUE;
        }

        return environment;
    }
}
