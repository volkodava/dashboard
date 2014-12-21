Ext.define('Dashboard.view.jenkins.StatusBoard', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.statusboardpanel',
    itemId: 'jenkinsstatusboardPanel',
    height: 60,
    layout: {
        type: 'hbox',
        align: 'stretch'
    },
    pack: 'center'
});