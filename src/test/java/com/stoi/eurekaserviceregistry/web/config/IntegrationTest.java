package com.stoi.eurekaserviceregistry.web.config;

import com.stoi.eurekaserviceregistry.EurekaserviceregistryApplication;
import com.stoi.eurekaserviceregistry.web.config.AsyncSyncConfiguration;
import com.stoi.eurekaserviceregistry.web.config.EmbeddedSQL;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { EurekaserviceregistryApplication.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
