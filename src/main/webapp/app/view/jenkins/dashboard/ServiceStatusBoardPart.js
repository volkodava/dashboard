Ext.define('Dashboard.view.jenkins.dashboard.ServiceStatusBoardPart', {
    extend: 'Ext.dashboard.Part',
    requires: [
        'Dashboard.view.jenkins.ServiceStatusBoard'
    ],
    reference: 'jenkinsservicestatusboard',
    alias: 'part.jenkinsservicestatusboard',
    viewTemplate: {
        layout: 'fit',
        title: Dashboard.lib.AppLocale.Jenkins.ServiceStatusBoardPart.title,
        iconCls: 'fa fa-cube',
        tools: [{
                itemId: 'jenkinsservicestatusboardRefreshButton',
                type: 'refresh'
            }],
        items: [{
                xtype: 'servicestatusboardpanel'
            }]
    }
});
