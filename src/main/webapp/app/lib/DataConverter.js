Ext.define('Dashboard.lib.DataConverter', {
    singleton: true,
    convertTimestampToDate: function (timestamp) {
        if (!timestamp || timestamp < 0) {
            new Date(0);
        }

        return new Date(timestamp);
    },
    convertDurationInMsToString: function (durationInMs) {
        if (!durationInMs || durationInMs < 0) {
            return "-";
        }

        var durationInSec = Number(durationInMs) / 1000;
        var h = Math.floor(durationInSec / 3600);
        var m = Math.floor(durationInSec % 3600 / 60);
        var s = Math.floor(durationInSec % 3600 % 60);
        var result = ((h > 0 ? h + ":" : "") + (m > 0 ? (h > 0 && m < 10 ? "0" : "") + m + ":" : "0:") + (s < 10 ? "0" : "") + s);
        return result;
    },
    convertObjectToIntValue: function (value) {
        if (!value) {
            return 0;
        }

        return Number(value);
    }
});