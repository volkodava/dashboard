Ext.define('Dashboard.view.jenkins.dashboard.ProjectGridPart', {
    extend: 'Ext.dashboard.Part',
    requires: [
        'Dashboard.view.jenkins.ProjectGrid'
    ],
    reference: 'jenkinsprojectgrid',
    alias: 'part.jenkinsprojectgrid',
    viewTemplate: {
        layout: 'fit',
        title: Dashboard.lib.AppLocale.Jenkins.ProjectGridPart.title,
        iconCls: 'fa fa-table',
        tools: [{
                itemId: 'jenkinsprojectgridRefreshButton',
                type: 'refresh'
            }],
        items: [{
                xtype: 'projectgrid'
            }]
    }
});
