Ext.define('Dashboard.view.jenkins.BuildExecutionChart', {
    extend: 'Ext.panel.Panel',
    requires: [
        'Ext.data.JsonStore',
        'Ext.chart.theme.Base',
        'Ext.chart.series.Series',
        'Ext.chart.series.Line',
        'Ext.chart.axis.Numeric'
    ],
    alias: 'widget.buildexecutionchartpanel',
    itemId: 'jenkinsbuildexecutionchartPanel',
    layout: 'fit',
    height: 300
});