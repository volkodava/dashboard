Ext.define('Dashboard.view.Viewport', {
    extend: 'Ext.container.Viewport',
    requires: [
        'Dashboard.view.main.Menu',
        'Dashboard.view.main.Body'
    ],
    layout: 'border',
//    items: [{
//            region: 'west',
//            xtype: 'dashboardmenu'
//        }, {
//            region: 'center',
//            xtype: 'dashboardcontainer'
//        }]
    items: [{
            itemId: 'dashboardcontainerId',
            region: 'center',
            xtype: 'layout-card'
        }]
});