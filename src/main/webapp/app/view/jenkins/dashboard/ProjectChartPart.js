Ext.define('Dashboard.view.jenkins.dashboard.ProjectChartPart', {
    extend: 'Ext.dashboard.Part',
    requires: [
        'Dashboard.view.jenkins.ProjectChart'
    ],
    reference: 'jenkinsprojectchart',
    alias: 'part.jenkinsprojectchart',
    viewTemplate: {
        layout: 'fit',
        title: Dashboard.lib.AppLocale.Jenkins.ProjectChartPart.title,
        iconCls: 'fa fa-area-chart',
        items: [{
                xtype: 'projectchartpanel'
            }]
    }
});
