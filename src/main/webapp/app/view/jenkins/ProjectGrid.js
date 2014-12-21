Ext.define('Dashboard.view.jenkins.ProjectGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.projectgrid',
    itemId: 'jenkinsprojectGrid',
    store: 'Projects',
    plugins: 'gridfilters',
    emptyText: Dashboard.lib.AppLocale.Jenkins.ProjectGridPart.emptyText,
    loadMask: true,
    stateful: true,
    stateId: 'stateful-jenkinsprojectgrid-grid',
    height: 300,
    viewConfig: {
        enableTextSelection: true
    },
    columns: [{
            header: Dashboard.lib.AppLocale.Jenkins.Build.buildNumber,
            dataIndex: 'buildNumber',
            xtype: 'numbercolumn',
            format: '0',
            align: 'right',
            filter: 'number',
            renderer: function (value, meta, record) {
                var url = record.data.reportUrl;
                meta.tdAttr = 'data-qtip="' + url + '"';
                return Ext.String.format('<a href="{0}" target="_blank">{1}</a>', url, value);
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Jenkins.Build.selectedEnvironment,
            dataIndex: 'selectedEnvironment',
            filter: 'string',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Jenkins.Build.status,
            dataIndex: 'status',
            align: 'center',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                if (value === 'FAILURE') {
                    meta.tdStyle = 'background-color:red;color:white;';
                } else if (value === 'ABORTED') {
                    meta.tdStyle = 'background-color:yellow;color:black;';
                } else {
                    meta.tdStyle = 'background-color:green;color:white;';
                }
                return value;
            },
            filter: {
                type: 'list',
                options: ['ABORTED', 'FAILURE', 'NOT_BUILT', 'SUCCESS', 'UNSTABLE']
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Jenkins.Build.failCount,
            dataIndex: 'failCount',
            xtype: 'numbercolumn',
            format: '0', align: 'right',
            filter: 'number',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Jenkins.Build.skipCount,
            dataIndex: 'skipCount',
            xtype: 'numbercolumn',
            format: '0',
            align: 'right',
            filter: 'number',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Jenkins.Build.totalCount,
            dataIndex: 'totalCount',
            xtype: 'numbercolumn',
            format: '0',
            align: 'right',
            filter: 'number',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            // TODO: fix the filter. Works with filter value in ms
            header: Dashboard.lib.AppLocale.Jenkins.Build.duration,
            dataIndex: 'duration',
            renderer: function (value, meta, record) {
                var result = Dashboard.lib.DataConverter.convertDurationInMsToString(value);
                meta.tdAttr = 'data-qtip="' + result + '"';
                return result;
            },
            filter: true,
            align: 'right',
            flex: 1
        }, {
            // TODO: fix the filter. Doen't save selected filter
            header: Dashboard.lib.AppLocale.Jenkins.Build.timestamp,
            dataIndex: 'timestamp',
            align: 'center',
            xtype: 'datecolumn',
            filter: true,
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }]
});