Ext.define('Dashboard.view.main.Menu', {
    extend: 'Ext.panel.Panel',
    xtype: 'dashboardmenu',
    reference: 'dashboardmenu',
    title: 'Menu',
    animCollapse: true,
    width: 200,
    minWidth: 150,
    maxWidth: 400,
    split: true,
    collapsible: true,
    layout: {
        type: 'accordion',
        animate: true
    },
    header: {
        itemPosition: 1,
        items: [{
                xtype: 'splitbutton',
                text: 'Add View',
//                handler: 'onAddView',
                menu: [{
                        text: 'Jenkins Projects',
                        handler: 'onAddJenkinsProjects'
                    }, {
                        text: 'Users',
                        handler: 'onAddUsers'
                    }, {
                        text: 'Jenkins Build Env',
                        handler: 'onAddJenkinsBuildEnv'
                    }, {
                        text: 'Jenkins Build Result',
                        handler: 'onAddJenkinsBuildResult'
                    }, {
                        text: 'Jenkins Build Package',
                        handler: 'onAddJenkinsBuildPackage'
                    }]
            }]
    },
    items: [{
            title: 'Navigation',
            html: '<div class="portlet-content">Some menu items will appear shortly... </div>',
            border: false,
            autoScroll: true,
            iconCls: 'fa fa-bars'
        }, {
            title: 'Settings',
            html: '<div class="portlet-content">Some menu items will appear shortly... </div>',
            border: false,
            autoScroll: true,
            iconCls: 'fa fa-wrench'
        }]
});
