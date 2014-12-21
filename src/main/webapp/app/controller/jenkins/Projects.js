Ext.define('Dashboard.controller.jenkins.Projects', {
    extend: 'Ext.app.Controller',
    stores: ['Projects@Dashboard.store.jenkins'],
    models: ['Project@Dashboard.model.jenkins'],
    views: [
        'ProjectGridPart@Dashboard.view.jenkins.dashboard',
        'ProjectChartPart@Dashboard.view.jenkins.dashboard',
        'StatusBoardPart@Dashboard.view.jenkins.dashboard',
        'ServiceStatusBoardPart@Dashboard.view.jenkins.dashboard',
        'BuildExecutionChartPart@Dashboard.view.jenkins.dashboard'
    ],
    requires: [
        'Dashboard.lib.jenkins.DataTransformer'
    ],
    messageBroker: Dashboard.lib.MessageBroker,
    refs: [{
            ref: 'dashboardcontainerViewport',
            selector: '#dashboardcontainerId'
        }],
    init: function () {
        this.control({'#jenkinsprojectgridRefreshButton': {
                click: this.onProjectgridRefreshButtonClick
            },
            '#jenkinsstatusboardRefreshButton': {
                click: this.onStatusboardRefreshButtonClick
            },
            '#jenkinsservicestatusboardRefreshButton': {
                click: this.onServicestatusboardRefreshButtonClick
            },
            '#jenkinsbuildexecutionchartRefreshButton': {
                click: this.onBuildexecutionchartRefreshButtonClick
            },
            '#jenkinsprojectGrid': {
                afterrender: this.onProjectGridPartAfterrender
            },
            '#jenkinsprojectchartPanel': {
                afterrender: this.onProjectchartPanelAfterrender
            },
            '#jenkinsstatusboardPanel': {
                afterrender: this.onStatusboardPanelAfterrender
            },
            '#jenkinsservicestatusboardPanel': {
                afterrender: this.onServicestatusboardPanelAfterrender
            },
            '#jenkinsbuildexecutionchartPanel': {
                afterrender: this.onBuildexecutionchartPanelAfterrender
            }
        });
    },
    onProjectgridRefreshButtonClick: function (component) {
        this.messageBroker.fireEvent("onProjectgridRefreshButtonClick", this);
    },
    onStatusboardRefreshButtonClick: function (component) {
        this.messageBroker.fireEvent("onStatusboardRefreshButtonClick", this);
    },
    onServicestatusboardRefreshButtonClick: function (component) {
        this.messageBroker.fireEvent("onServicestatusboardRefreshButtonClick", this);
    },
    onBuildexecutionchartRefreshButtonClick: function (component) {
        this.messageBroker.fireEvent("onBuildexecutionchartRefreshButtonClick", this);
    },
    onProjectGridPartAfterrender: function (component) {
        var store = this.getProjectsStore();
        this.refreshProjectgrid(store);
    },
    refreshProjectgrid: function (store) {
        store.load(function (records, operation, success) {
            console.log('Grid refreshed');
        });
    },
    onProjectchartPanelAfterrender: function (component) {
        var trendMapUrl = Dashboard.lib.UrlConstants.TREND_MAP_URL;
        var projectInfoUrl = Dashboard.lib.UrlConstants.PROJECT_INFO_URL;

        var trendMapData = "";
        Dashboard.lib.Request.GetSync(trendMapUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            trendMapData = json.data;
        });
        var projectInfoData = "";
        Dashboard.lib.Request.GetSync(projectInfoUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            projectInfoData = json.data;
        });

        var chartHeader = Dashboard.lib.AppLocale.Jenkins.ProjectChartPart.getChartHeader(projectInfoData);
        var chartBody = Dashboard.lib.AppLocale.Jenkins.ProjectChartPart.getChartBody(projectInfoData, trendMapData);
        var resultContent = chartHeader + chartBody;

        component.update(resultContent);
    },
    onStatusboardPanelAfterrender: function (component) {
        this.refreshStatusboardPanel(component);
    },
    onServicestatusboardPanelAfterrender: function (component) {
        this.refreshServicestatusboardPanel(component);
    },
    refreshStatusboardPanel: function (component) {
        var envStatusesUrl = Dashboard.lib.UrlConstants.ENV_STATUS_URL;

        if (!component) {
            return;
        }

        // remove old items from the container
        component.removeAll();

        var envStatuses = "";
        Dashboard.lib.Request.GetSync(envStatusesUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            envStatuses = json.data;
        });

        if (envStatuses) {
            Ext.Array.forEach(envStatuses, function (envStatus, index, allItems) {
                var buildNumber = Dashboard.lib.jenkins.ProjectModelConverterFactory.buildNumberConverter(envStatus.buildNumber);
                var selectedEnvironment = Dashboard.lib.jenkins.ProjectModelConverterFactory.selectedEnvironmentConverter(envStatus.selectedEnvironment);
                var status = Dashboard.lib.jenkins.ProjectModelConverterFactory.statusConverter(envStatus.status);

                var text = selectedEnvironment + "&nbsp;/&nbsp;#" + buildNumber;
                var tooltip = text + "&nbsp;[" + status + "]";
                var style;
                var buttonCls;
                var statusFieldCls;

                if (status === 'FAILURE') {
                    style = {
                        background: 'red',
                        color: 'white'
                    };
                    buttonCls = 'failure-button-text';
                    statusFieldCls = 'failure-text';
                } else if (status === 'ABORTED') {
                    style = {
                        background: 'yellow',
                        color: 'black'
                    };
                    buttonCls = 'aborted-button-text';
                    statusFieldCls = 'aborted-text';
                } else {
                    style = {background: 'green',
                        color: 'white'
                    };
                    buttonCls = 'other-button-text';
                    statusFieldCls = 'other-text';
                }

                component.add({
                    xtype: 'button',
                    iconCls: 'fa fa-building',
                    text: text,
                    tooltip: tooltip,
                    scale: 'medium', iconAlign: 'top',
                    flex: 1,
                    cls: buttonCls,
                    style: style,
                    handler: function (button) {
                        window.open('#details/' + selectedEnvironment + '/' + buildNumber, '_blank');
                    }
                });
            });
        }
    },
    refreshServicestatusboardPanel: function (component) {
        var srvcStatusesUrl = Dashboard.lib.UrlConstants.SERVICE_STATUS_URL;

        if (!component) {
            return;
        }

        // remove old items from the container
        component.removeAll();

        var srvcStatuses = "";
        Dashboard.lib.Request.GetSync(srvcStatusesUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            srvcStatuses = json.data;
        });

        if (srvcStatuses) {
            Ext.Array.forEach(srvcStatuses, function (envStatus, index, allItems) {
                var buildNumber = Dashboard.lib.jenkins.ProjectModelConverterFactory.buildNumberConverter(envStatus.buildNumber);
                var selectedEnvironment = Dashboard.lib.jenkins.ProjectModelConverterFactory.selectedEnvironmentConverter(envStatus.selectedEnvironment);
                var status = Dashboard.lib.jenkins.ProjectModelConverterFactory.statusConverter(envStatus.status);

                var text = selectedEnvironment + "&nbsp;/&nbsp;#" + buildNumber;
                var tooltip = text + "&nbsp;[" + status + "]";
                var style;
                var buttonCls;
                var statusFieldCls;

                if (status === 'DOWN') {
                    style = {
                        background: 'red',
                        color: 'white'
                    };
                    buttonCls = 'failure-button-text';
                    statusFieldCls = 'failure-text';
                } else {
                    style = {background: 'green',
                        color: 'white'
                    };
                    buttonCls = 'other-button-text';
                    statusFieldCls = 'other-text';
                }

                component.add({
                    xtype: 'button',
                    iconCls: 'fa fa-building',
                    text: text,
                    tooltip: tooltip,
                    scale: 'medium', iconAlign: 'top',
                    flex: 1,
                    cls: buttonCls,
                    style: style,
                    handler: function (button) {
                        window.open('#details/' + selectedEnvironment + '/' + buildNumber, '_blank');
                    }
                });
            });
        }
    },
    onBuildexecutionchartPanelAfterrender: function (component) {
        this.refreshBuildexecutionchartPanel(component);
    },
    refreshBuildexecutionchartPanel: function (component) {
        var projectBuildsUrl = Dashboard.lib.UrlConstants.PROJECT_BUILDS_URL;

        var labelOnChart = new Ext.Template(
            '<div style="background: rgba(25, 25, 25, .7);color: white;">',
            '<span>{environmentName}</span>: {environment}<br />',
            '<span>{buildNumberName}</span>: {buildNumber}<br />',
            '<span>{statusName}</span>: {status}<br />',
            '<span>{failCountName}</span>: {failCount}<br />',
            '<span>{skipCountName}</span>: {skipCount}<br />',
            '<span>{totalCountName}</span>: {totalCount}<br />',
            '<span>{durationName}</span>: {duration}&nbsp;/&nbsp;{durationInSec} sec.<br />',
            '<span>{timestampName}</span>: {timestamp}<br />',
            '<span>{reportUrlName}</span>: {reportUrl}<br />',
            '</div>',
            {
                compiled: true
            }
        );

        if (!component) {
            return;
        }

        // remove old items from the container
        component.removeAll();

        var buildsData = "";
        Dashboard.lib.Request.GetSync(projectBuildsUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            buildsData = json.data;
        });
        var resultExecutions =
            Dashboard.lib.jenkins.DataTransformer.transformBuildsToExecutions(
                buildsData, Dashboard.lib.AppConstants.EXECUTION_CHART_MAX_ITMES_PER_ENVIRONMENT);
        var fieldsArr = Object.getOwnPropertyNames(resultExecutions.envMap);

        if (resultExecutions.builds && fieldsArr) {
            var chartAxes = [{
                    type: 'Numeric',
                    fields: fieldsArr,
                    title: Dashboard.lib.AppLocale.Jenkins.Build.duration + ', sec',
                    position: 'left',
                    grid: {
                        odd: {
                            opacity: 1,
                            fill: '#ddd',
                            stroke: '#bbb',
                            lineWidth: 1
                        }
                    },
                    minimum: 0
                }, {
                    type: 'Category',
                    fields: 'buildNumber',
                    title: Dashboard.lib.AppLocale.Jenkins.Build.buildNumber, position: 'bottom',
                    grid: true,
                    label: {
                        rotate: {
                            degrees: -45
                        }
                    }
                }];

            var chartSeries = [];
            Ext.Array.forEach(fieldsArr, function (fieldName, index, allItems) {
                chartSeries.push({
                    type: 'line',
                    axis: 'left',
                    title: fieldName,
                    xField: 'buildNumber', yField: fieldName,
                    style: {
                        'stroke-width': 4
                    },
                    markerConfig: {
                        radius: 5
                    },
                    highlight: {
                        fill: '#000',
                        radius: 5,
                        'stroke-width': 2,
                        stroke: '#fff'},
                    tips: {trackMouse: true,
                        style: 'background: #FFF',
                        height: 20,
                        renderer: function (storeItem, item) {
                            var environment = item.series.title;
                            var buildNumber = storeItem.get('buildNumber');
                            var status = storeItem.get('status');
                            var failCount = storeItem.get('failCount');
                            var skipCount = storeItem.get('skipCount');
                            var totalCount = storeItem.get('totalCount');
                            var duration = storeItem.get('duration');
                            var timestamp = storeItem.get('timestamp');
                            var reportUrl = storeItem.get('reportUrl');
                            var durationInSec = storeItem.get(item.series.yField);

                            var data = {
                                environmentName: Dashboard.lib.AppLocale.Jenkins.Build.selectedEnvironment,
                                environment: environment,
                                buildNumberName: Dashboard.lib.AppLocale.Jenkins.Build.buildNumber,
                                buildNumber: buildNumber,
                                statusName: Dashboard.lib.AppLocale.Jenkins.Build.status,
                                status: status,
                                failCountName: Dashboard.lib.AppLocale.Jenkins.Build.failCount,
                                failCount: failCount,
                                skipCountName: Dashboard.lib.AppLocale.Jenkins.Build.skipCount,
                                skipCount: skipCount,
                                totalCountName: Dashboard.lib.AppLocale.Jenkins.Build.totalCount,
                                totalCount: totalCount,
                                durationName: Dashboard.lib.AppLocale.Jenkins.Build.duration,
                                duration: duration,
                                durationInSec: durationInSec,
                                timestampName: Dashboard.lib.AppLocale.Jenkins.Build.timestamp,
                                timestamp: Dashboard.lib.DataConverter.convertTimestampToDate(timestamp),
                                reportUrlName: Dashboard.lib.AppLocale.Jenkins.Build.reportUrl,
                                reportUrl: reportUrl
                            };

                            var resultContent = {
                                innerHTML: ""
                            };
                            labelOnChart.overwrite(resultContent, data);

                            this.setTitle(resultContent.innerHTML);
                        }
                    }
                });
            });

            var chartStore = Ext.create('Ext.data.JsonStore', {
                fields: fieldsArr,
                data: resultExecutions.builds
            });

            var finalChart = {
                xtype: 'chart',
                width: '100%',
                height: '100%',
                animate: true,
                shadow: false,
                style: 'background: #fff;',
                legend: {
                    position: 'bottom',
                    boxStrokeWidth: 0,
                    labelFont: '16px Helvetica'
                },
                store: chartStore,
                axes: chartAxes,
                series: chartSeries
            };

            component.add(finalChart);
        }
    }
});
