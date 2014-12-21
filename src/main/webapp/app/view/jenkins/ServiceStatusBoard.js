Ext.define('Dashboard.view.jenkins.ServiceStatusBoard', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.servicestatusboardpanel',
    itemId: 'jenkinsservicestatusboardPanel',
    height: 60,
    layout: {
        type: 'hbox',
        align: 'stretch'
    },
    pack: 'center'
});