Ext.define('Dashboard.lib.jenkins.locale.ProjectChartPart_en', {
    singleton: true,
    title: "Test Result Trend",
    chartHeaderFormat: '<div align="center">{0}. {1} Score {2}.<br />{3}</div>',
    chartBodyFormat: '<div align="center"><img lazymap="trendMap" alt="[Test result trend chart]" src="{0}" usemap="#map"></div>{1}',
    getChartHeader: function (projectInfoData) {
        if (!projectInfoData) {
            return "";
        }

        var resultHeader = Ext.String.format(Dashboard.lib.jenkins.locale.ProjectChartPart_en.chartHeaderFormat,
            projectInfoData.name, projectInfoData.buildStability, projectInfoData.buildStabilityScore, projectInfoData.testReport);

        return resultHeader;
    },
    getChartBody: function (projectInfoData, trendMapData) {
        if (!projectInfoData) {
            return "";
        }

// TODO: Change me in real environment
//        var trendUrl = "trend.png";
        var trendUrl = projectInfoData.trendUrl;
        var resultBody = Ext.String.format(Dashboard.lib.jenkins.locale.ProjectChartPart_en.chartBodyFormat, trendUrl, trendMapData);

        return resultBody;
    }
});