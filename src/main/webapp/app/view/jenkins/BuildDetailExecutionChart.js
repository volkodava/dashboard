Ext.define('Dashboard.view.jenkins.BuildDetailExecutionChart', {
    extend: 'Ext.panel.Panel',
    requires: [
        'Ext.data.JsonStore',
        'Ext.chart.theme.Base',
        'Ext.chart.series.Series',
        'Ext.chart.series.Line',
        'Ext.chart.axis.Numeric'
    ],
    alias: 'widget.builddetailexecutionchartpanel',
    itemId: 'jenkinsbuilddetailexecutionchartPanel',
    layout: 'fit',
    height: 300
});