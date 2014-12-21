Ext.define('Dashboard.controller.jenkins.ProjectDetails', {
    extend: 'Ext.app.Controller',
    messageBroker: Dashboard.lib.MessageBroker,
    refs: [{
            ref: 'detailpagePanel',
            selector: '#detailpage'
        }, {
            ref: 'builddetailexecutionchartPanel',
            selector: '#jenkinsbuilddetailexecutionchartPanel'
        }],
    refreshBuilddetailexecutionchartPanel: function (obj) {
        var projectBuildsUrl = Dashboard.lib.UrlConstants.PROJECT_BUILDS_BY_ENV;
        var component = this.getBuilddetailexecutionchartPanel();

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
        }, {env: obj.envName});

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
                    title: Dashboard.lib.AppLocale.Jenkins.Build.buildNumber,
                    position: 'bottom',
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
                    xField: 'buildNumber',
                    yField: fieldName,
                    style: {
                        'stroke-width': 4
                    },
                    markerConfig: {
                        radius: 7
                    },
                    renderer: function (sprite, config, rendererData, data) {
                        if (config.data) {
                            var status = config.data['status'];
                            if (status === 'FAILURE') {
                                return Ext.apply(rendererData, {
                                    fill: "#FF0000"
                                });
                            } else if (status === 'ABORTED') {
                                return Ext.apply(rendererData, {
                                    fill: "#FFFF00"
                                });
                            }

                            return Ext.apply(rendererData, {
                                fill: "#008000"
                            });
                        }

                        return rendererData;
                    },
                    highlight: {
                        radius: 9,
                        'stroke-width': 2
                    },
                    tips: {
                        trackMouse: true,
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
                store: chartStore,
                axes: chartAxes,
                series: chartSeries
            };

            component.add(finalChart);
        }
    },
    refreshDetailPage: function (obj) {
        var detailPage = this.getDetailpagePanel();

        var selectedBuild = this.getSelectedBuild(obj);

        // When you're updating a layout dynamically, 
        // Ext will automatically re-layout for you, 
        // so the doLayout call is redundant and adding extra overhead. 
        // Secondly, it's also inefficient because the remove, then the add will trigger a layout
        // remove old items from the container
        detailPage.removeAll();

        var panel = {
            xtype: 'panel',
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [{
                    xtype: 'panel',
                    items: [{
                            xtype: this.buildDetailForm(selectedBuild)
                        }, {
                            xtype: this.buildExecutionChart()
                        }]
                }, {
                    xtype: this.buildVersionsGrid(obj)
                }]
        };

        detailPage.add(panel);

        // redraw the chart
        this.refreshBuilddetailexecutionchartPanel(obj);
    },
    getSelectedBuild: function (params) {
        var envName = params.envName;
        var buildNumber = params.buildNumber;

        var selectedBuildUrl = Dashboard.lib.UrlConstants.PROJECT_BUILD_BY_ENV_AND_NUMBER;
        var selectedBuild = "";
        Dashboard.lib.Request.GetSync(selectedBuildUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            selectedBuild = json.data;
        }, {env: envName, build: buildNumber});

        return selectedBuild;
    },
    buildExecutionChart: function () {
        return {
            xtype: 'panel',
            padding: 10,
            items: [{
                    xtype: 'builddetailexecutionchartpanel',
                    width: 800,
                    height: 400
                }]
        };
    },
    buildVersionsGrid: function (params) {
        var envName = params.envName;

        var environmentModuleVersionsUrl = Dashboard.lib.UrlConstants.ENVIRONMENT_MODULE_VERSIONS_URL;
        var environmentArtifacts = "";
        Dashboard.lib.Request.GetSync(environmentModuleVersionsUrl, function (response) {
            var jsonStr = response.responseText;
            var json = Ext.decode(jsonStr);
            environmentArtifacts = json.data;
        }, {env: envName});

        var modules = [];
        for (var artifact in environmentArtifacts) {
            if (environmentArtifacts.hasOwnProperty(artifact)) {
                var version = environmentArtifacts[artifact].version;
                var status = environmentArtifacts[artifact].status;
                modules.push({artifact: artifact, version: version, status: status});
            }
        }

        var dataStore = Ext.create('Ext.data.JsonStore', {
            fields: ["artifact", "version", "status"],
            data: modules
        });

        var grid = Ext.create('Ext.grid.Panel', {
            store: dataStore,
            width: 500,
            viewConfig: {
                enableTextSelection: true
            },
            columns: [{
                    text: Dashboard.lib.AppLocale.Jenkins.Artifact.status,
                    dataIndex: 'status',
                    align: 'center',
                    flex: 1,
                    renderer: function (value, meta, record) {
                        meta.tdAttr = 'data-qtip="' + value + '"';
                        if (value === 'UP') {
                            meta.tdStyle = 'background-color:green;color:white;';
                        } else if (value === 'DOWN') {
                            meta.tdStyle = 'background-color:red;color:white;';
                        } else {
                            meta.tdStyle = 'background-color:yellow;color:black;';
                        }
                        return value;
                    }
                }, {
                    text: Dashboard.lib.AppLocale.Jenkins.Artifact.artifact,
                    dataIndex: 'artifact',
                    renderer: function (value, meta, record) {
                        meta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    flex: 2
                }, {
                    text: Dashboard.lib.AppLocale.Jenkins.Artifact.version,
                    dataIndex: 'version',
                    renderer: function (value, meta, record) {
                        meta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    flex: 2
                }]
        });

        return {
            xtype: 'panel',
            padding: 10,
            layout: 'fit',
            items: [grid]
        };
    },
    buildDetailForm: function (selectedBuild) {
        var duration = Dashboard.lib.jenkins.ProjectModelConverterFactory.durationConverter(selectedBuild.duration);
        var durationAsString = Dashboard.lib.DataConverter.convertDurationInMsToString(duration);
        var buildNumber = Dashboard.lib.jenkins.ProjectModelConverterFactory.buildNumberConverter(selectedBuild.buildNumber);
        var selectedEnvironment = Dashboard.lib.jenkins.ProjectModelConverterFactory.selectedEnvironmentConverter(selectedBuild.selectedEnvironment);
        var status = Dashboard.lib.jenkins.ProjectModelConverterFactory.statusConverter(selectedBuild.status);
        var timestampAsDate = Dashboard.lib.jenkins.ProjectModelConverterFactory.timestampConverter(selectedBuild.timestamp);
        var failCountAsInt = Dashboard.lib.jenkins.ProjectModelConverterFactory.failCountConverter(selectedBuild.failCount);
        var skipCountAsInt = Dashboard.lib.jenkins.ProjectModelConverterFactory.skipCountConverter(selectedBuild.skipCount);
        var totalCountAsInt = Dashboard.lib.jenkins.ProjectModelConverterFactory.totalCountConverter(selectedBuild.totalCount);
        var reportUrl = Dashboard.lib.jenkins.ProjectModelConverterFactory.reportUrlConverter(selectedBuild.reportUrl);
        var statusFieldCls;

        if (status === 'FAILURE') {
            statusFieldCls = 'failure-text';
        } else if (status === 'ABORTED') {
            statusFieldCls = 'aborted-text';
        } else {
            statusFieldCls = 'other-text';
        }

        var resultForm = {
            xtype: 'panel',
            padding: 10,
            layout: 'fit',
            items: [{
                    xtype: 'form',
                    border: false,
                    style: 'background-color: #fff;',
                    items: [{
                            xtype: 'numberfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.buildNumber,
                            value: buildNumber
                        }, {
                            xtype: 'textfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.selectedEnvironment,
                            value: selectedEnvironment
                        }, {
                            xtype: 'textfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.status,
                            value: status,
                            cls: statusFieldCls
                        }, {
                            xtype: 'numberfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.failCount,
                            value: failCountAsInt
                        }, {
                            xtype: 'numberfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.skipCount,
                            value: skipCountAsInt
                        }, {
                            xtype: 'numberfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.totalCount,
                            value: totalCountAsInt
                        }, {
                            xtype: 'textfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.duration,
                            value: durationAsString
                        }, {
                            xtype: 'textfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.timestamp,
                            value: timestampAsDate
                        }, {
                            xtype: 'textfield',
                            readOnly: true,
                            fieldLabel: Dashboard.lib.AppLocale.Jenkins.Build.reportUrl,
                            value: reportUrl,
                            fieldStyle: 'color: blue; text-decoration:underline; cursor:pointer',
                            listeners: {
                                render: function (field) {
                                    var link = field.getValue();
                                    this.getEl().on('click', function (e, t, eOpts) {
                                        var url = e.target.value;
                                        if (url) {
                                            window.open(link, '_blank');
                                        }
                                    });
                                }
                            }
                        }]
                }]
        };

        return resultForm;
    }
});
