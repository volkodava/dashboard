Ext.define('Dashboard.lib.jenkins.ProjectModelConverterFactory', {
    singleton: true,
    buildNumberConverter: function (value, record) {
        return Dashboard.lib.DataConverter.convertObjectToIntValue(value);
    },
    selectedEnvironmentConverter: function (value, record) {
        if (value) {
            return value;
        }

        return "-";
    },
    statusConverter: function (value, record) {
        if (value) {
            return value;
        }

        return "-";
    },
    failCountConverter: function (value, record) {
        if (value === null) {
            return -1;
        }
        return Dashboard.lib.DataConverter.convertObjectToIntValue(value);
    },
    skipCountConverter: function (value, record) {
        if (value === null) {
            return -1;
        }
        return Dashboard.lib.DataConverter.convertObjectToIntValue(value);
    },
    totalCountConverter: function (value, record) {
        if (value === null) {
            return -1;
        }
        return Dashboard.lib.DataConverter.convertObjectToIntValue(value);
    },
    durationConverter: function (value, record) {
        if (value === null) {
            return -1;
        }
        return Dashboard.lib.DataConverter.convertObjectToIntValue(value);
    },
    timestampConverter: function (value, record) {
        return Dashboard.lib.DataConverter.convertTimestampToDate(value);
    },
    reportUrlConverter: function (value, record) {
        if (value) {
            return value;
        }

        return "#";
    }
});