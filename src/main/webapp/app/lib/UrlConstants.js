Ext.define('Dashboard.lib.UrlConstants', {
    singleton: true,
    TREND_MAP_URL: "services/jenkins/project/trendMap/projectName",
    PROJECT_INFO_URL: "services/jenkins/project/projectName",
    ENV_STATUS_URL: "services/jenkins/project/environmentStatuses/projectName",
    PROJECT_BUILDS_URL: "services/jenkins/project/builds/projectName",
    PROJECT_BUILD_BY_ENV_AND_NUMBER: "services/jenkins/project/build/projectName",
    ENVIRONMENT_MODULE_VERSIONS_URL: "services/jenkins/project/versions/projectName",
    PROJECT_BUILDS_BY_ENV: "services/jenkins/project/buildsForEnv/projectName",
    SERVICE_STATUS_URL: "services/jenkins/project/serviceStatuses/projectName"
});