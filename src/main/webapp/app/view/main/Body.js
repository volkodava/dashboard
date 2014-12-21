Ext.define('Dashboard.view.main.Body', {
    extend: 'Ext.panel.Panel',
    requires: [
        'Ext.layout.container.Card',
        'Dashboard.view.main.Master',
        'Dashboard.view.main.Detail'
    ],
    xtype: 'layout-card',
    layout: 'card',
    defaults: {
        border: false
    },
    items: [{
            itemId: 'masterpage',
            xtype: 'dashboardcontainer'
        }, {
            itemId: 'detailpage',
            xtype: 'detailpage'
        }]
});
