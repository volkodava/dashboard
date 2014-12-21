Ext.define('Dashboard.store.jenkins.Projects', {
    extend: 'Ext.data.Store',
    model: 'Dashboard.model.jenkins.Project',
    autoLoad: false,
    proxy: {
        type: 'rest',
        api: {
            read: Dashboard.lib.UrlConstants.PROJECT_BUILDS_URL
        },
        reader: {
            type: 'json',
            rootProperty: 'data',
            successProperty: 'success'
        }
    }
});