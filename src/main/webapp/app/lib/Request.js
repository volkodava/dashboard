Ext.define('Dashboard.lib.Request', {
    singleton: true,
    GetSync: function (url, successFn, params) {
        var me = this;
        Ext.Ajax.request({
            url: url,
            method: 'GET',
            async: false,
            params: params,
            timeout: 5000,
            success: successFn,
            failure: me.DefaultRequestFailureHandler
        });
    },
    DefaultRequestFailureHandler: function (response) {
        var statusCode = response.status;
        if (response.responseText) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            var success = json.success;
            if (!success) {
                var message = json.message + ": " + statusCode;
                Dashboard.lib.MessageBox.ShowErrorBox(message);
            }
        } else {
            var message = Dashboard.lib.jenkins.Locale.MessageBox.statusErrorMessageWithCode + ": " + statusCode;
            Dashboard.lib.MessageBox.ShowErrorBox(message);
        }
    }
});