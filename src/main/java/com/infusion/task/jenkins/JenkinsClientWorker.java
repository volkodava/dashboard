package com.infusion.task.jenkins;

import com.infusion.client.ClientFactory;
import com.infusion.data.jenkins.JenkinsCredentials;
import java.util.concurrent.Callable;
import org.springframework.util.Assert;

public class JenkinsClientWorker<T> implements Callable<T> {

    private ClientFactory clientFactory;
    private JenkinsCredentials credentials;
    private String url;
    private Class<T> objectClazz;

    @Override
    public T call() throws Exception {
        T result = clientFactory.createJenkinsClient(credentials).
            get(url, objectClazz);
        return result;
    }

    public static class Builder<T> {

        private ClientFactory clientFactory;
        private JenkinsCredentials credentials;
        private String url;
        private Class<T> objectClazz;

        public Builder clientFactory(ClientFactory clientFactory) {
            this.clientFactory = clientFactory;
            return this;
        }

        public Builder credentials(JenkinsCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder objectClazz(Class<T> objectClazz) {
            this.objectClazz = objectClazz;
            return this;
        }

        public JenkinsClientWorker build() {
            Assert.notNull(clientFactory);
            Assert.notNull(credentials);
            Assert.hasText(url);
            Assert.notNull(objectClazz);

            JenkinsClientWorker task = new JenkinsClientWorker();
            task.clientFactory = clientFactory;
            task.credentials = credentials;
            task.url = url;
            task.objectClazz = objectClazz;

            return task;
        }
    }
}
