Ext.define('Dashboard.lib.MessageBroker', {
    singleton: true,
    mixins: {
        observable: 'Ext.util.Observable'
    },
    constructor: function (config) {
        // The Observable constructor copies all of the properties of `config` on
        // to `this` using Ext.apply. Further, the `listeners` property is
        // processed to add listeners.
        //
        this.mixins.observable.constructor.call(this, config);
    },
    name: 'jenkins.Projects',
    listeners: {
        onProjectgridRefreshButtonClick: function (controller) {
            this.refreshProjectsDashboard(controller);
        },
        onStatusboardRefreshButtonClick: function (controller) {
            this.refreshProjectsDashboard(controller);
        },
        onServicestatusboardRefreshButtonClick: function (controller) {
            this.refreshProjectsDashboard(controller);
        },
        onBuildexecutionchartRefreshButtonClick: function (controller) {
            this.refreshProjectsDashboard(controller);
        },
        onNavigateToDetailPage: function (obj) {
            var controller = Dashboard.app.getController('jenkins.ProjectDetails');
            controller.refreshDetailPage(obj);
        }
    },
    refreshProjectsDashboard: function (controller) {
        if (!controller) {
            console.log("Controller not found!");
            return;
        }

        var viewport = controller.getDashboardcontainerViewport();

        // refresh Projectgrid
        var store = controller.getProjectsStore();
        controller.refreshProjectgrid(store);

        // refresh Statusboard
        var jenkinsstatusboardPanel = viewport.down("#jenkinsstatusboardPanel");
        controller.refreshStatusboardPanel(jenkinsstatusboardPanel);

        // refresh Servicestatusboard
        var jenkinsservicestatusboardPanel = viewport.down("#jenkinsservicestatusboardPanel");
        controller.refreshServicestatusboardPanel(jenkinsservicestatusboardPanel);

        // refresh Buildexecutionchart
        var jenkinsbuildexecutionchartPanel = viewport.down("#jenkinsbuildexecutionchartPanel");
        controller.refreshBuildexecutionchartPanel(jenkinsbuildexecutionchartPanel);
    }
});