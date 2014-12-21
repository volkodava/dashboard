Ext.define('Dashboard.view.jenkins.dashboard.BuildExecutionChartPart', {
    extend: 'Ext.dashboard.Part',
    requires: [
        'Dashboard.view.jenkins.BuildExecutionChart'
    ],
    reference: 'jenkinsbuildexecutionchart',
    alias: 'part.jenkinsbuildexecutionchart',
    viewTemplate: {
        layout: 'fit',
        title: Dashboard.lib.AppLocale.Jenkins.BuildExecutionChartPart.title,
        iconCls: 'fa fa-area-chart',
        tools: [{
                itemId: 'jenkinsbuildexecutionchartRefreshButton',
                type: 'refresh'
            }],
        items: [{
                xtype: 'buildexecutionchartpanel'
            }]
    }
});
