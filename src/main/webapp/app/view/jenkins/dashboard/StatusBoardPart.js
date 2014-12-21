Ext.define('Dashboard.view.jenkins.dashboard.StatusBoardPart', {
    extend: 'Ext.dashboard.Part',
    requires: [
        'Dashboard.view.jenkins.StatusBoard'
    ],
    reference: 'jenkinsstatusboard',
    alias: 'part.jenkinsstatusboard',
    viewTemplate: {
        layout: 'fit',
        title: Dashboard.lib.AppLocale.Jenkins.StatusBoardPart.title,
        iconCls: 'fa fa-cube',
        tools: [{
                itemId: 'jenkinsstatusboardRefreshButton',
                type: 'refresh'
            }],
        items: [{
                xtype: 'statusboardpanel'
            }]
    }
});
