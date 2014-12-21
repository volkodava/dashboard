Ext.define('Dashboard.view.main.Master', {
    extend: 'Ext.dashboard.Dashboard',
    requires: [
        'Dashboard.view.jenkins.dashboard.ProjectGridPart',
        'Dashboard.view.jenkins.dashboard.ProjectChartPart',
        'Dashboard.view.jenkins.dashboard.BuildExecutionChartPart',
        'Dashboard.view.jenkins.dashboard.StatusBoardPart',
        'Dashboard.view.jenkins.dashboard.ServiceStatusBoardPart'
    ],
    xtype: 'dashboardcontainer',
    reference: 'dashboardcontainer',
    stateful: false,
    parts: {
        projects: 'jenkinsprojectgrid',
        projectsChart: 'jenkinsprojectchart',
        jenkinsStatus: 'jenkinsstatusboard',
        jenkinsServicestatus: 'jenkinsservicestatusboard',
        jenkinsExecutionChart: 'jenkinsbuildexecutionchart'
    },
    defaultContent: [{
            type: 'projects',
            columnIndex: 0
        }, {
            type: 'projectsChart',
            columnIndex: 0
        }, {
            type: 'jenkinsStatus',
            columnIndex: 1
        }, {
            type: 'jenkinsServicestatus',
            columnIndex: 1
        }, {
            type: 'jenkinsExecutionChart',
            columnIndex: 1
        }]
});
