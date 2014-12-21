Ext.define('Dashboard.lib.MessageBox', {
    singleton: true,
    ShowErrorBox: function (message) {
        Ext.MessageBox.show({
            title: Dashboard.lib.jenkins.Locale.MessageBox.errorTitle,
            message: message,
            buttons: Ext.Msg.OK,
            icon: Ext.Msg.ERROR,
            overflowY: 'auto'
        });
    }
});