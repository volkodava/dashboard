dashboard
===============

================
= Requirements =
================

- Java SE Development Kit 7+ (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- Apache Maven 3+ (http://maven.apache.org/download.cgi)


===============
= Server side =
===============

Lets assume that we are going to represent some data on UI received from https://api.github.com/search/repositories?q=spring-projects+language:java&sort=stars&order=desc REST service

1. Define the final format of JSON message for our UI from the link https://api.github.com/search/repositories?q=spring-projects+language:java&sort=stars&order=desc
{  
  "items":[  
    {  
      "id":6441969,
      "name":"spring-restbucks",
      "full_name":"olivergierke/spring-restbucks",
      "pushed_at":"2014-08-14T15:45:18Z",
      "watchers_count":188,
      "language":"Java",
      "score":11.230686
    },
    {  
      "id":4938085,
      "name":"spring-hibernate-springdata-springmvc-maven-project-framework",
      "full_name":"ykameshrao/spring-hibernate-springdata-springmvc-maven-project-framework",
      "pushed_at":"2012-10-06T04:40:47Z",
      "watchers_count":149,
      "language":"Java",
      "score":3.8319557
    },
    {  
      "id":14984085,
      "name":"ddd-leaven-v2",
      "full_name":"BottegaIT/ddd-leaven-v2",
      "pushed_at":"2014-03-14T20:57:51Z",
      "watchers_count":103,
      "language":"Java",
      "score":4.530273
    },
    {  
      "id":4653065,
      "name":"spring-integration-extensions",
      "full_name":"spring-projects/spring-integration-extensions",
      "pushed_at":"2014-11-26T21:07:53Z",
      "watchers_count":92,
      "language":"Java",
      "score":9.39626
    }
  ]
}

2. Add to src/main/resources/application.properties and to src/test/resources/test-jenkins-repository-application.properties files Github repository url to query for projects and fixed cache duration in seconds with maximum number of entries the cache may contains
github.repositories.cache.durationInSec=600
github.repositories.cache.maximumSize=5

# Repositories URL for Github
github.repositories.url=https://api.github.com/search/repositories

3. Create Plain Old Java Object to map JSON message and put it into com.infusion.model
package com.infusion.model;

import java.util.Objects;

public class GithubProject {

    private Integer id;
    private String name;
    private String fullName;
    // time in milliseconds since January 1, 1970, 00:00:00 GMT
    private Long pushedAt;
    private Integer watchersCount;
    private String language;
    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(Long pushedAt) {
        this.pushedAt = pushedAt;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GithubProject other = (GithubProject) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GithubProject{" + "id=" + id + ", name=" + name + ", fullName=" + fullName + ", pushedAt=" + pushedAt + ", watchersCount=" + watchersCount + ", language=" + language + ", score=" + score + '}';
    }
}

4. Create mapper to transform JSON representation into the GithubProject Plain Old Java Object
package com.infusion.mapper;

import com.infusion.model.GithubProject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GithubProjectMapper {

    private static final String ITEMS_FIELD_ELEMENT = "items";
    private static final String ID_FIELD_ELEMENT = "id";
    private static final String NAME_FIELD_ELEMENT = "name";
    private static final String FULL_NAME_FIELD_ELEMENT = "full_name";
    private static final String PUSHED_AT_FIELD_ELEMENT = "pushed_at";
    private static final String WATCHERS_COUNT_FIELD_ELEMENT = "watchers_count";
    private static final String LANGUAGE_FIELD_ELEMENT = "language";
    private static final String SCORE_FIELD_ELEMENT = "score";
    private static final String DATE_TIME_STRING_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public Collection<GithubProject> transformToGithubProject(Map<String, Object> resultMap) throws ParseException {
        if (resultMap == null || resultMap.get(ITEMS_FIELD_ELEMENT) == null) {
            return Collections.EMPTY_LIST;
        }

        List<GithubProject> result = new ArrayList<GithubProject>();
        List<LinkedHashMap<String, Object>> items = (List<LinkedHashMap<String, Object>>) resultMap.get(ITEMS_FIELD_ELEMENT);
        for (LinkedHashMap<String, Object> item : items) {
            Integer id = (Integer) item.get(ID_FIELD_ELEMENT);
            String name = (String) item.get(NAME_FIELD_ELEMENT);
            String fullName = (String) item.get(FULL_NAME_FIELD_ELEMENT);
            String pushedAt = (String) item.get(PUSHED_AT_FIELD_ELEMENT);
            Integer watchersCount = (Integer) item.get(WATCHERS_COUNT_FIELD_ELEMENT);
            String language = (String) item.get(LANGUAGE_FIELD_ELEMENT);
            Double score = (Double) item.get(SCORE_FIELD_ELEMENT);

            GithubProject project = new GithubProject();
            project.setFullName(fullName);
            project.setId(id);
            project.setLanguage(language);
            project.setName(name);
            project.setPushedAt(convertDateTimeStringToTime(pushedAt));
            project.setScore(score);
            project.setWatchersCount(watchersCount);
            result.add(project);
        }

        return result;
    }

    private Long convertDateTimeStringToTime(String dateTimeAsStr) throws ParseException {
        return new SimpleDateFormat(DATE_TIME_STRING_FORMAT).parse(dateTimeAsStr).getTime();
    }
}

5. Create service which query for a github projects by project name and put it into com.infusion.service
package com.infusion.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.infusion.mapper.GithubProjectMapper;
import com.infusion.model.GithubProject;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String SEARCH_QUERY_PATTERN = "?q=%s+language:java&sort=stars&order=desc";
    @Autowired(required = true)
    private GithubProjectMapper mapper;
    @Autowired(required = true)
    private RestTemplate restTemplate;
    @Value("${github.repositories.url}")
    private String repositoryUrl;
    @Value("${github.repositories.cache.durationInSec}")
    private Integer repositoryDuration;
    private TimeUnit repositoryDurationUnit;
    @Value("${github.repositories.cache.maximumSize}")
    private Integer repositoryMaximumSize;
    private Cache<String, Collection<GithubProject>> repositoryCache;

    public Collection<GithubProject> getGithubProjects(final String projectName) {
        try {
            return repositoryCache.get(projectName, new Callable<Collection<GithubProject>>() {

                @Override
                public Collection<GithubProject> call() throws Exception {
                    String searchUrl = getSearchUrl(projectName);
                    Map<String, Object> resultMap = restTemplate.getForObject(searchUrl, Map.class);
                    Collection<GithubProject> githubProjects = mapper.transformToGithubProject(resultMap);
                    return githubProjects;
                }
            });
        } catch (ExecutionException ex) {
            logger.error("Error while parsing results found by project " + projectName, ex);
            throw new RuntimeException("Parsing error of the result matched by project " + projectName);
        }
    }

    private String getSearchUrl(String projectName) {
        String queryString = String.format(SEARCH_QUERY_PATTERN, projectName);
        return repositoryUrl + queryString;
    }

    private void buildCaches() {
        repositoryCache = CacheBuilder.newBuilder().expireAfterWrite(repositoryDuration, repositoryDurationUnit)
            .maximumSize(repositoryMaximumSize)
            .build();
    }

    @PostConstruct
    public void postInit() {
        Assert.hasText(repositoryUrl);
        Assert.notNull(repositoryDuration);
        Assert.isTrue(repositoryDuration > 0);
        Assert.isTrue(repositoryMaximumSize > 0);

        repositoryDurationUnit = TimeUnit.SECONDS;
        buildCaches();
    }
}

6. Create controller which prepare and produce some data as JSON through the REST service
package com.infusion.controllers;

import com.infusion.model.GithubProject;
import com.infusion.service.GithubService;
import com.infusion.ui.converter.ExtJsConverter;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/github")
public class GithubController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private GithubService service;
    @Autowired(required = true)
    private ExtJsConverter converter;

    @RequestMapping(value = "/get/{projectName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getGithubProjects(@PathVariable("projectName") String projectName) {
        logger.debug("Getting github projects {}", projectName);
        try {
            Collection<GithubProject> dataUI = service.getGithubProjects(projectName);
            Map<String, ? extends Object> bodyMessage = converter.transformSuccess(dataUI);
            return ResponseEntity.status(HttpStatus.OK).body(bodyMessage);
        } catch (RuntimeException ex) {
            Map<String, Object> bodyMessage = converter.transformError("Couldn't get github projects " + projectName, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(bodyMessage);
        }
    }
}

7. The final REST URL for GithubController is: http://localhost:8080/dashboard/services/github/get/spring-projects
Web context URL: http://localhost:8080/dashboard
REST services location: services (taken from src/webapp/WEB-INF/web.xml dispatcher servlet mapping)
GitHub REST service root: github (taken from annotation @RequestMapping(value = "/github") above GithubController class)
GitHub REST service get projects endpoint: /get/spring-projects (taken from annotation @RequestMapping(value = "/get/{projectName}"... above getGithubProjects method of GithubController class, where "spring-projects" path parameter stored into {projectName} variable)


=======================
= Unit Test (on Mock) =
=======================

Cover controller with unit test based on mock and save it into src/test/java folder under the package com.infusion.controllers

1. Create test spring configuration file /src/test/resources/test-github-controller-spring-config.xml and paste lines below to it
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:task="http://www.springframework.org/schema/task"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
		http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
 
 	<!-- Main Spring context configuration -->
    <import resource="classpath:/spring-config.xml" />
    
    <!-- Mock of real service -->
    <bean id="githubService" name="githubService" class="org.mockito.Mockito" factory-method="mock">
        <constructor-arg value="com.infusion.service.GithubService"/>
    </bean>
</beans>

2. Create unit test to cover GithubController spring controller
package com.infusion.controllers;

import com.infusion.model.GithubProject;
import com.infusion.service.GithubService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-github-controller-spring-config.xml"})
public class GithubControllerTest {

    private static final String PROJECT_NAME = "SomeCoolProject";
    private static final String GITHUB_PROJECTS_GET_URI = "/github/get/" + PROJECT_NAME;

    private MockMvc mockMvc;

    @Autowired(required = true)
    private GithubService service;

    @Autowired(required = true)
    private WebApplicationContext webApplicationContext;

    private List<GithubProject> projects = new ArrayList<GithubProject>();

    @Before
    public void setUp() throws IOException {
        reset(service);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        when(service.getGithubProjects(PROJECT_NAME)).thenReturn(projects);
    }

    @Test
    public void testReadProjectsMatchedBySpecificProjectName() throws Exception {
        final Integer PROJECT_ID = 10;

        GithubProject githubProject = new GithubProject();
        githubProject.setId(PROJECT_ID);
        projects.add(githubProject);

        mockMvc.perform(get(GITHUB_PROJECTS_GET_URI).
            accept(MediaType.APPLICATION_JSON)).
            andExpect(status().isOk()).
            andExpect(content().contentType("application/json")).
            andExpect(jsonPath("$.success", is(Boolean.TRUE))).
            andExpect(jsonPath("$.data[0].id", is(PROJECT_ID)));
    }
}

3. Run the test either from IDE or from command line with maven


===========
= UI side =
===========

TODO: It would be better to remove mediator from UI codebase named src/main/webapp/app/lib/MessageBroker.js, because now it seems more like an overhead

Lets assume that we are going to show the data from GitHub filtered by project name - 'spring-projects' on a dashboard grid

1. Add to src/main/webapp/app/lib/UrlConstants.js link to GitHub controller with predefined project name filter as 'spring-projects'
...
    GITHUB_PROJECTS_GET_URL: "services/github/get/spring-projects"
...

2. Define JSON model for our GithubProject and save it into src/main/webapp/app/model/github/GithubProject.js
Ext.define('Dashboard.model.github.GithubProject', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.reader.Json'
    ],
    fields: [
        {name: 'id', type: 'int'},
        {name: 'name', type: 'string'},
        {name: 'fullName', type: 'string'},
        {name: 'pushedAt', type: 'date', convert: this.dateTimeStringConverter},
        {name: 'watchersCount', type: 'int'},
        {name: 'language', type: 'string'},
        {name: 'score', type: 'float'}
    ],
    dateTimeStringConverter: function (value, record) {
        return new Date(value);
    }
});

3. Localize JSON model fields and save result into src/main/webapp/app/lib/github/locale/GithubProject_en.js
Ext.define('Dashboard.lib.github.locale.GithubProject_en', {
    singleton: true,
    id: "#",
    name: "Name",
    fullName: "Full Name",
    watchersCount: "Watchers",
    language: "Language",
    score: "Score",
    pushedAt: "Pushed At"
});

4. Define store service that helps you read the data from the REST service and map result to the model. Save the store service to src/main/webapp/app/store/github/GithubProjects.js
Ext.define('Dashboard.store.github.GithubProjects', {
    extend: 'Ext.data.Store',
    model: 'Dashboard.model.github.GithubProject',
    autoLoad: true,
    proxy: {
        type: 'rest',
        api: {
            read: Dashboard.lib.UrlConstants.GITHUB_PROJECTS_GET_URL
        },
        reader: {
            type: 'json',
            rootProperty: 'data',
            successProperty: 'success'
        }
    }
});

5. Define the grid to show the results from REST service and save it into src/main/webapp/app/view/github/GithubProjectGrid.js
Ext.define('Dashboard.view.github.GithubProjectGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.githubprojectgrid',
    store: 'github.GithubProjects',
    plugins: 'gridfilters',
    emptyText: Dashboard.lib.AppLocale.Github.GithubProjectGrid.emptyText,
    loadMask: true,
    stateful: true,
    stateId: 'stateful-githubprojectgrid-grid',
    height: 300,
    viewConfig: {
        enableTextSelection: true
    },
    columns: [{
            header: Dashboard.lib.AppLocale.Github.GithubProject.id,
            dataIndex: 'id',
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
            header: Dashboard.lib.AppLocale.Github.GithubProject.name,
            dataIndex: 'name',
            filter: 'string',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Github.GithubProject.fullName,
            dataIndex: 'fullName',
            filter: 'string',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Github.GithubProject.watchersCount,
            dataIndex: 'watchersCount',
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
            header: Dashboard.lib.AppLocale.Github.GithubProject.language,
            dataIndex: 'language',
            filter: 'string',
            renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            },
            flex: 1
        }, {
            header: Dashboard.lib.AppLocale.Github.GithubProject.score,
            dataIndex: 'score',
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
            header: Dashboard.lib.AppLocale.Github.GithubProject.pushedAt,
            dataIndex: 'pushedAt',
            align: 'center',
            xtype: 'datecolumn',
            filter: true,
            renderer: function (value, meta, record) {
                var dateValue = new Date(value);
                meta.tdAttr = 'data-qtip="' + dateValue + '"';
                return dateValue;
            },
            flex: 1
        }]
});

6. Localize GithubProjectGrid fields and save it into src/main/webapp/app/lib/github/locale/GithubProjectGrid_en.js
Ext.define('Dashboard.lib.github.locale.GithubProjectGrid_en', {
    singleton: true,
    title: "GitHub Projects",
    emptyText: "No Matching Records"
});

7. Define github locale helper class and save it into src/main/webapp/app/lib/github/Locale.js
Ext.define('Dashboard.lib.github.Locale', {
    singleton: true,
    GithubProjectGrid: Dashboard.lib.github.locale.GithubProjectGrid_en,
    GithubProject: Dashboard.lib.github.locale.GithubProject_en
});

8. Add reference to github locale helper class into src/main/webapp/app/lib/AppLocale.js
Ext.define('Dashboard.lib.AppLocale', {
    singleton: true,
    Jenkins: Dashboard.lib.jenkins.Locale,
->    Github: Dashboard.lib.github.Locale
});

9. Specify locales as dependencies for the application in src/main/webapp/app/app.js at requires container
...
            'Dashboard.lib.jenkins.Locale',
->            'Dashboard.lib.github.locale.GithubProjectGrid_en',
->            'Dashboard.lib.github.locale.GithubProject_en',
->            'Dashboard.lib.github.Locale',
            'Dashboard.lib.AppLocale'
...

10. Define the grid dashboard part and save it into src/main/webapp/app/view/github/dashboard/GithubProjectGridPart.js
* requires - specify the list of external dependencies in this block
* xtype: 'githubprojectgrid' referenced to GithubProjectGrid alias: 'widget.githubprojectgrid'
Ext.define('Dashboard.view.github.dashboard.GithubProjectGridPart', {
    extend: 'Ext.dashboard.Part',
    requires: [
        'Dashboard.view.github.GithubProjectGrid'
    ],
    alias: 'part.githubprojectgridPart',
    viewTemplate: {
        layout: 'fit',
        title: Dashboard.lib.AppLocale.Github.GithubProjectGrid.title,
        iconCls: 'fa fa-table',
        tools: [{
                itemId: 'githubprojectgridRefreshButton',
                type: 'refresh'
            }],
        items: [{
                xtype: 'githubprojectgrid'
            }]
    }
});

11. Define controller to handle refresh button click on the grid and save it into src/main/webapp/app/controller/github/GithubProjects.js
Ext.define('Dashboard.controller.github.GithubProjects', {
    extend: 'Ext.app.Controller',
    stores: ['GithubProjects@Dashboard.store.github'],
    models: ['GithubProject@Dashboard.model.github'],
    views: [
        'GithubProjectGrid@Dashboard.view.github',
        'GithubProjectGridPart@Dashboard.view.github.dashboard'
    ],
    init: function () {
        this.control({'#githubprojectgridRefreshButton': {
                click: this.onGithubprojectgridRefreshButtonClick
            }
        });
    },
    onGithubprojectgridRefreshButtonClick: function (component) {
        var store = this.getGithubProjectsStore();
        store.load(function (records, operation, success) {
            console.log('GitHub Grid refreshed');
        });
    }
});

12. Specify new component dependencies in src/main/webapp/app/app.js
...
        autoCreateViewport: true,
->        models: ['jenkins.Project', 'github.GithubProject'],
->        stores: ['jenkins.Projects', 'github.GithubProjects'],
->        controllers: ['Routes', 'jenkins.Projects', 'jenkins.ProjectDetails', 'github.GithubProjects'],
        init: function () {
...

13. Add new github component to the dashboard defined in file src/main/webapp/app/view/main/Master.js
    requires: [
...
        'Dashboard.view.jenkins.dashboard.ServiceStatusBoardPart',
->        'Dashboard.view.github.dashboard.GithubProjectGridPart'
    ],
...
    parts: {
...
        jenkinsExecutionChart: 'jenkinsbuildexecutionchart',
->        githubProjects: 'githubprojectgridPart'
    },
    defaultContent: [{
...
        }, {
->            type: 'githubProjects',
->            columnIndex: 1
        }]
});


============================
= First Application Launch =
============================

1. Open command prompt

2. Execute command
mvn clean tomcat7:run-war

3. Open browser and go to http://localhost:8080/dashboard/

4. Hopefully UI should appear without errors


=========
= Notes =
=========

Documentation for ExtJS UI framework:
http://docs.sencha.com/extjs/5.0/apidocs/#!
 
Examples for ExtJS UI framework: 
http://dev.sencha.com/extjs/5.0.0/examples/index.html
