package com.infusion.verifier;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.infusion.data.jenkins.JenkinsProjectData;
import com.infusion.model.jenkins.json.Action;
import com.infusion.model.jenkins.json.BuildStatusAction;
import com.infusion.model.jenkins.json.HealthReport;
import com.infusion.model.jenkins.json.JenkinsBuildStatus;
import com.infusion.model.jenkins.json.JenkinsProject;
import com.infusion.model.jenkins.json.Parameter;
import com.infusion.model.jenkins.json.ParameterDefinition;
import com.infusion.model.jenkins.json.Property;
import java.util.Collection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class JenkinsVerifier {

    public void checkJenkinsProject(JenkinsProject project) {
        assertThat(project, is(notNullValue()));

        assertThat(project.getActions(), is(notNullValue()));
        assertThat(project.getActions(), not(empty()));

        Action action = project.getActions().iterator().next();
        assertThat(action.getParameterDefinitions(), is(notNullValue()));
        assertThat(action.getParameterDefinitions(), not(empty()));

        ParameterDefinition definition = action.getParameterDefinitions().iterator().next();
        assertThat(definition.getDefaultParameterValue(), is(notNullValue()));
        assertThat(definition.getDefaultParameterValue().getName(), is(nullValue()));
        assertThat(definition.getDefaultParameterValue().getValue(), not(isEmptyOrNullString()));
        assertThat(definition.getDescription(), not(isEmptyOrNullString()));
        assertThat(definition.getName(), not(isEmptyOrNullString()));
        assertThat(definition.getType(), not(isEmptyOrNullString()));
        assertThat(definition.getChoices(), is(notNullValue()));
        assertThat(definition.getChoices(), not(empty()));

        assertThat(project.getDisplayName(), not(isEmptyOrNullString()));
        assertThat(project.getName(), not(isEmptyOrNullString()));
        assertThat(project.getUrl(), not(isEmptyOrNullString()));
        assertThat(project.isBuildable(), is(true));
        assertThat(project.getBuilds(), is(notNullValue()));
        assertThat(project.getBuilds(), not(empty()));
        assertThat(project.getColor(), not(isEmptyOrNullString()));
        assertThat(project.getFirstBuild(), is(notNullValue()));
        assertThat(project.getFirstBuild().getNumber(), is(1));
        assertThat(project.getFirstBuild().getUrl(), not(isEmptyOrNullString()));
        assertThat(project.getHealthReport(), is(notNullValue()));
        assertThat(project.getHealthReport(), not(empty()));

        HealthReport healthReport = project.getHealthReport().iterator().next();
        assertThat(healthReport.getDescription(), not(isEmptyOrNullString()));
        assertThat(healthReport.getIconClassName(), not(isEmptyOrNullString()));
        assertThat(healthReport.getIconUrl(), not(isEmptyOrNullString()));
        assertThat(healthReport.getScore(), greaterThanOrEqualTo(0));

        assertThat(project.isInQueue(), is(false));
        assertThat(project.isKeepDependencies(), is(false));
        assertThat(project.getLastBuild(), is(notNullValue()));
        assertThat(project.getLastBuild().getNumber(), greaterThan(1));
        assertThat(project.getLastBuild().getUrl(), not(isEmptyOrNullString()));
        assertThat(project.getLastCompletedBuild(), is(notNullValue()));
        assertThat(project.getLastCompletedBuild().getNumber(), greaterThan(1));
        assertThat(project.getLastCompletedBuild().getUrl(), not(isEmptyOrNullString()));
        assertThat(project.getLastFailedBuild(), is(notNullValue()));
        assertThat(project.getLastFailedBuild().getNumber(), greaterThan(1));
        assertThat(project.getLastFailedBuild().getUrl(), not(isEmptyOrNullString()));
        assertThat(project.getLastStableBuild(), is(nullValue()));
        assertThat(project.getLastSuccessfulBuild(), is(nullValue()));
        assertThat(project.getLastUnstableBuild(), is(nullValue()));
        assertThat(project.getLastUnsuccessfulBuild(), is(notNullValue()));
        assertThat(project.getLastUnsuccessfulBuild().getNumber(), greaterThan(1));
        assertThat(project.getLastUnsuccessfulBuild().getUrl(), not(isEmptyOrNullString()));
        assertThat(project.getNextBuildNumber(), greaterThan(1));
        assertThat(project.getProperty(), is(notNullValue()));
        assertThat(project.getProperty(), not(empty()));

        Property property = project.getProperty().iterator().next();
        assertThat(property.getParameterDefinitions(), is(notNullValue()));
        assertThat(property.getParameterDefinitions(), not(empty()));

        definition = property.getParameterDefinitions().iterator().next();
        assertThat(definition.getDefaultParameterValue(), is(notNullValue()));
        assertThat(definition.getDefaultParameterValue().getName(), not(isEmptyOrNullString()));
        assertThat(definition.getDefaultParameterValue().getValue(), not(isEmptyOrNullString()));
        assertThat(definition.getDescription(), not(isEmptyOrNullString()));
        assertThat(definition.getName(), not(isEmptyOrNullString()));
        assertThat(definition.getType(), not(isEmptyOrNullString()));
        assertThat(definition.getChoices(), is(notNullValue()));
        assertThat(definition.getChoices(), not(empty()));

        assertThat(project.getQueueItem(), is(nullValue()));
        assertThat(project.isConcurrentBuild(), is(false));
        assertThat(project.getDownstreamProjects(), is(notNullValue()));
        assertThat(project.getDownstreamProjects(), is(empty()));
        assertThat(project.getScm(), is(notNullValue()));
        assertThat(project.getUpstreamProjects(), is(notNullValue()));
        assertThat(project.getUpstreamProjects(), is(empty()));
    }

    public void checkJenkinsBuildStatus(JenkinsBuildStatus buildStatus) {
        assertThat(buildStatus, is(notNullValue()));

        assertThat(buildStatus.getActions(), is(notNullValue()));
        assertThat(buildStatus.getActions(), not(empty()));

        Collection<BuildStatusAction> actionsWithParameters = Collections2.filter(buildStatus.getActions(), new Predicate<BuildStatusAction>() {

            @Override
            public boolean apply(BuildStatusAction action) {
                if (action.getParameters() != null && !action.getParameters().isEmpty()) {
                    return true;
                }
                return false;
            }
        });

        BuildStatusAction actionWithParameters = actionsWithParameters.iterator().next();
        assertThat(actionWithParameters.getParameters(), is(notNullValue()));
        assertThat(actionWithParameters.getParameters(), not(empty()));

        Parameter parameter = actionWithParameters.getParameters().iterator().next();
        assertThat(parameter.getName(), not(isEmptyOrNullString()));
        assertThat(parameter.getValue(), not(isEmptyOrNullString()));

        Collection<BuildStatusAction> actionsWithReportUrl = Collections2.filter(buildStatus.getActions(), new Predicate<BuildStatusAction>() {

            @Override
            public boolean apply(BuildStatusAction action) {
                if (action.getUrlName() != null && !action.getUrlName().isEmpty()) {
                    return true;
                }
                return false;
            }
        });

        assertThat(actionsWithReportUrl, not(empty()));

        BuildStatusAction actionWithReportUrl = actionsWithReportUrl.iterator().next();
        assertThat(actionWithReportUrl.getUrlName(), not(isEmptyOrNullString()));
        assertThat(actionWithReportUrl.getTotalCount(), greaterThan(0));

        assertThat(buildStatus.getArtifacts(), is(notNullValue()));
        assertThat(buildStatus.getArtifacts(), not(empty()));

        assertThat(buildStatus.getDuration(), greaterThan(0L));
        assertThat(buildStatus.getEstimatedDuration(), greaterThan(0));

        assertThat(buildStatus.getFullDisplayName(), not(isEmptyOrNullString()));
        assertThat(buildStatus.getId(), not(isEmptyOrNullString()));
        assertThat(buildStatus.getNumber(), greaterThan(0));
        assertThat(buildStatus.getResult(), not(isEmptyOrNullString()));
        assertThat(buildStatus.getTimestamp(), greaterThan(0L));
        assertThat(buildStatus.getUrl(), not(isEmptyOrNullString()));
        assertThat(buildStatus.getChangeSet(), is(notNullValue()));
        assertThat(buildStatus.getCulprits(), is(notNullValue()));
        assertThat(buildStatus.getCulprits(), not(empty()));
    }

    public void checkJenkinsProjectDto(JenkinsProjectData project) {
        assertThat(project.getName(), not(isEmptyOrNullString()));
        assertThat(project.getBuildStability(), not(isEmptyOrNullString()));
        assertThat(project.getBuildStabilityScore(), greaterThanOrEqualTo(0));
        assertThat(project.getTestReport(), not(isEmptyOrNullString()));
        assertThat(project.getTestReportScore(), greaterThanOrEqualTo(0));
        assertThat(project.getProjectUrl(), not(isEmptyOrNullString()));
        assertThat(project.getTrendUrl(), not(isEmptyOrNullString()));
        assertThat(project.getBuilds(), not(empty()));
    }
}
