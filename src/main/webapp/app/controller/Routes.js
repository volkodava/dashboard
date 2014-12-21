Ext.define('Dashboard.controller.Routes', {
    extend: 'Ext.app.Controller',
    messageBroker: Dashboard.lib.MessageBroker,
    refs: [{
            ref: 'dashboardcontainerViewport',
            selector: '#dashboardcontainerId'
        }],
    init: function () {
        this.control({
            '#detailpageHomeButton': {
                click: this.onDetailpageHomeButtonClick
            }
        });
    },
    routes: {
        'home': {
            action: 'onHome'
        },
        'details/:envName/:buildNumber': {
            action: 'onEnvDetails',
            conditions: {
                ':envName': '([a-zA-Z0-9-_\.]+)',
                ':buildNumber': '([0-9]+)'
            }
        }
    },
    listen: {
        controller: {
            '#': {
                unmatchedroute: 'onUnmatchedRoute'
            }
        }
    },
    onHome: function () {
        Ext.getDoc().dom.title = Dashboard.lib.AppLocale.Jenkins.Dashboard.title;

        var viewport = this.getDashboardcontainerViewport();
        viewport.getLayout().setActiveItem('masterpage');
    },
    onUnmatchedRoute: function (hash) {
        Ext.getDoc().dom.title = Dashboard.lib.AppLocale.Jenkins.Dashboard.title;

        this.redirectTo("/home");
        var viewport = this.getDashboardcontainerViewport();
        viewport.getLayout().setActiveItem('masterpage');
    },
    onDetailpageHomeButtonClick: function () {
        Ext.getDoc().dom.title = Dashboard.lib.AppLocale.Jenkins.Dashboard.title;

        this.redirectTo("/home");
        var viewport = this.getDashboardcontainerViewport();
        viewport.getLayout().setActiveItem('masterpage');
    },
    onEnvDetails: function (envName, buildNumber) {
        Ext.getDoc().dom.title = envName + " / #" + buildNumber;

        var viewport = this.getDashboardcontainerViewport();
        viewport.getLayout().setActiveItem('detailpage');

        this.messageBroker.fireEvent("onNavigateToDetailPage", {envName: envName, buildNumber: buildNumber});
    }
});
