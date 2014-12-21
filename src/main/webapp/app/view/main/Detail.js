Ext.define('Dashboard.view.main.Detail', {
    extend: 'Ext.panel.Panel',
    requires: [
        'Ext.data.JsonStore',
        'Ext.chart.theme.Base',
        'Ext.chart.series.Series',
        'Ext.chart.series.Line',
        'Ext.chart.axis.Numeric',
        'Dashboard.view.jenkins.BuildDetailExecutionChart'
    ],
    alias: 'widget.detailpage',
    border: false,
    header: {
        itemPosition: 0,
        items: [{
                xtype: 'button',
                itemId: 'detailpageHomeButton',
                iconCls: 'fa fa-home',
                text: Dashboard.lib.AppLocale.Jenkins.Detail.dashboardButtonText
            }]
    },
    autoScroll: true,
    padding: 7
});