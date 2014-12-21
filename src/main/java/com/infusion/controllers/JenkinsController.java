package com.infusion.controllers;

import com.infusion.service.JenkinsService;
import com.infusion.ui.converter.ExtJsConverter;
import com.infusion.ui.data.jenkins.JenkinsBuildDataUI;
import com.infusion.ui.data.jenkins.JenkinsProjectDataUI;
import com.infusion.ui.data.jenkins.ServiceUI;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

@Controller
@RequestMapping(value = "/jenkins")
public class JenkinsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private JenkinsService service;
    @Autowired(required = true)
    private ExtJsConverter converter;

    @RequestMapping(value = "/project/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getProjectInfo(@PathVariable("projectName") String projectName) {
        logger.debug("Getting project's information {}", projectName);
        try {
            JenkinsProjectDataUI dataUI = service.getProjectInfo(projectName);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get information for the project " + projectName, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/builds/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getProjectBuilds(@PathVariable("projectName") String projectName) {
        logger.debug("Getting project's builds {}", projectName);
        try {
            Collection<JenkinsBuildDataUI> dataUI = service.getProjectBuilds(projectName);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get builds for the project " + projectName, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/trendMap/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getProjectTrendMap(@PathVariable("projectName") String projectName) {
        logger.debug("Getting project's trend map {}", projectName);
        String content;
        try {
            content = service.getTrendMapContent(projectName);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(content);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get trend map for the project " + projectName, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/environmentStatuses/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getEnvironmentStatus(@PathVariable("projectName") String projectName) {
        logger.debug("Getting environment status {}", projectName);
        try {
            Collection<JenkinsBuildDataUI> dataUI = service.getEnvironmentStatuses(projectName);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get environment status for the project " + projectName, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/versions/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getModuleVersions(@PathVariable("projectName") String projectName,
        @RequestParam(value = "env") String environment) {
        logger.debug("Getting environment modules {} environment {}", projectName, environment);
        try {
            Map<String, Object> versions = service.getModuleVersionsForEnvironment(projectName, environment);
            Map<String, Object> bodyMessage = converter.transformSuccess(versions);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get versions for the project " + projectName + " in " + environment + " environment", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/build/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getProjectBuildByEnvAndNumber(@PathVariable("projectName") String projectName,
        @RequestParam(value = "env") String environment, @RequestParam(value = "build") int buildNumber) {
        logger.debug("Getting project's build {} BY environment {} buildNumber {}", projectName, environment, buildNumber);
        try {
            JenkinsBuildDataUI dataUI = service.getProjectBuildByEnvAndNumber(projectName, environment, buildNumber);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get build for the project " + projectName + " in " + environment + " environment by number " + buildNumber, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/buildsForEnv/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getProjectBuildsForEnv(@PathVariable("projectName") String projectName,
        @RequestParam(value = "env") String environment) {
        logger.debug("Getting project builds {} BY environment {}", projectName, environment);
        try {
            Collection<JenkinsBuildDataUI> dataUI = service.getProjectBuildsForEnv(projectName, environment);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get builds for the project " + projectName + " in " + environment + " environment", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }

    @RequestMapping(value = "/project/serviceStatuses/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getServiceStatusesForEnv(@PathVariable("projectName") String projectName) {
        logger.debug("Getting service statuses {}", projectName);
        try {
            Collection<ServiceUI> dataUI = service.getServiceStatuses(projectName);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RestClientException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get service statuses for the project " + projectName, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }
}
