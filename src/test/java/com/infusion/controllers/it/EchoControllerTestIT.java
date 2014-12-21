package com.infusion.controllers.it;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.client.fluent.Request;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class EchoControllerTestIT {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private RestTemplate restTemplate;

    private static String endpointUrl;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new StringHttpMessageConverter());
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
    }

    @Test
    public void testEcho() throws Exception {
        final String URL = endpointUrl + "/services/echo/hello";

        logger.info("Service url: {}", URL);

        String result = Request.Get(URL).execute().returnContent().asString();
        assertThat(result, is(equalTo("{\"message\":\"hello\"}")));
    }

    @Test
    public void testEchoWithRestTemplate() throws Exception {
        final String ASSERT_KEY = "message";
        final String ASSERT_VALUE = "hello";
        final String URL = endpointUrl + "/services/echo/" + ASSERT_VALUE;

        logger.info("Service url: {}", URL);

        Map<String, String> result = restTemplate.getForObject(URL, Map.class);

        assertThat(result, is(notNullValue()));
        assertThat(result, hasKey(ASSERT_KEY));
        assertThat(result, hasValue(ASSERT_VALUE));
    }
}
