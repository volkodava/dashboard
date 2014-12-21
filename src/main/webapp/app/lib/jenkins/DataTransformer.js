Ext.define('Dashboard.lib.jenkins.DataTransformer', {
    singleton: true,
    transformBuildsToExecutions: function (builds, maxItmesPerEnvironment) {
        if (!maxItmesPerEnvironment) {
            maxItmesPerEnvironment = Number.MAX_VALUE;
        }

        var result = {
            builds: [],
            envMap: {}
        };
        if (builds) {
            Ext.Array.forEach(builds, function (buildObj, index, allItems) {
                var convertedObj = {};
                var selectedEnvironment = Dashboard.lib.jenkins.ProjectModelConverterFactory.selectedEnvironmentConverter(buildObj.selectedEnvironment);

                var numOfItems = result.envMap[selectedEnvironment];

                if (numOfItems && numOfItems === maxItmesPerEnvironment) {
                    // skip this item if we have requested number of items for the environment
                    return true;
                }

                if (numOfItems) {
                    result.envMap[selectedEnvironment] = ++numOfItems;
                } else {
                    result.envMap[selectedEnvironment] = 1;
                }

                var duration = Dashboard.lib.jenkins.ProjectModelConverterFactory.durationConverter(buildObj.duration);
                var durationInSec = -1;
                if (duration > 0) {
                    // convert ms to sec
                    durationInSec = duration / 1000;
                }

                var buildNumber = Dashboard.lib.jenkins.ProjectModelConverterFactory.buildNumberConverter(buildObj.buildNumber);
                var status = Dashboard.lib.jenkins.ProjectModelConverterFactory.statusConverter(buildObj.status);
                var timestampAsDate = Dashboard.lib.jenkins.ProjectModelConverterFactory.timestampConverter(buildObj.timestamp);
                var failCountAsInt = Dashboard.lib.jenkins.ProjectModelConverterFactory.failCountConverter(buildObj.failCount);
                var skipCountAsInt = Dashboard.lib.jenkins.ProjectModelConverterFactory.skipCountConverter(buildObj.skipCount);
                var totalCountAsInt = Dashboard.lib.jenkins.ProjectModelConverterFactory.totalCountConverter(buildObj.totalCount);
                var reportUrl = Dashboard.lib.jenkins.ProjectModelConverterFactory.reportUrlConverter(buildObj.reportUrl);

                convertedObj["buildNumber"] = buildNumber;
                convertedObj[selectedEnvironment] = durationInSec;
                convertedObj["status"] = status;
                convertedObj["failCount"] = failCountAsInt;
                convertedObj["skipCount"] = skipCountAsInt;
                convertedObj["totalCount"] = totalCountAsInt;
                convertedObj["duration"] = Dashboard.lib.DataConverter.convertDurationInMsToString(duration);
                convertedObj["timestamp"] = timestampAsDate;
                convertedObj["reportUrl"] = reportUrl;

                result.builds.push(convertedObj);
            });

            return result;
        } else {
            return result;
        }
    }
});

