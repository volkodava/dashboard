Ext.Loader.setConfig({enabled: true, disableCache: true});

Ext.onReady(function () {
    Ext.application({
        requires: [
            'Ext.tip.QuickTipManager',
            'Ext.state.CookieProvider',
            'Dashboard.lib.MessageBox',
            'Dashboard.lib.AppConstants',
            'Dashboard.lib.UrlConstants',
            'Dashboard.lib.DataConverter',
            'Dashboard.lib.MessageBroker',
            'Dashboard.lib.Request',
            'Dashboard.lib.jenkins.ProjectModelConverterFactory',
            'Dashboard.lib.jenkins.locale.Dashboard_en',
            'Dashboard.lib.jenkins.locale.Artifact_en',
            'Dashboard.lib.jenkins.locale.Build_en',
            'Dashboard.lib.jenkins.locale.ProjectInfo_en',
            'Dashboard.lib.jenkins.locale.StatusBoardPart_en',
            'Dashboard.lib.jenkins.locale.ServiceStatusBoardPart_en',
            'Dashboard.lib.jenkins.locale.ProjectGridPart_en',
            'Dashboard.lib.jenkins.locale.ProjectChartPart_en',
            'Dashboard.lib.jenkins.locale.BuildExecutionChartPart_en',
            'Dashboard.lib.jenkins.locale.Detail_en',
            'Dashboard.lib.jenkins.locale.MessageBox_en',
            'Dashboard.lib.jenkins.Locale',
            'Dashboard.lib.AppLocale'
        ],
        defaultToken: 'home',
        name: 'Dashboard',
        appFolder: "app",
        autoCreateViewport: true,
        models: ['jenkins.Project'],
        stores: ['jenkins.Projects'],
        controllers: ['Routes', 'jenkins.Projects', 'jenkins.ProjectDetails'],
        init: function () {
            Ext.tip.QuickTipManager.init();
            Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
        }
    });
});