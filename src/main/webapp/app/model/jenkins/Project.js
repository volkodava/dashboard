Ext.define('Dashboard.model.jenkins.Project', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.reader.Json'
    ],
    fields: [
        {name: 'buildNumber', type: 'int', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.buildNumberConverter},
        {name: 'selectedEnvironment', type: 'string', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.selectedEnvironmentConverter},
        {name: 'status', type: 'string', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.statusConverter},
        {name: 'failCount', type: 'int', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.failCountConverter},
        {name: 'skipCount', type: 'int', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.skipCountConverter},
        {name: 'totalCount', type: 'int', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.totalCountConverter},
        {name: 'duration', type: 'int', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.durationConverter},
        {name: 'timestamp', type: 'date', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.timestampConverter},
        {name: 'reportUrl', type: 'string', convert: Dashboard.lib.jenkins.ProjectModelConverterFactory.reportUrlConverter}
    ]
});