package com.adobe.aem.guides.nirvana.core.schedulers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = AuthorScheduler.Config.class)
@Component(immediate = true, service = Runnable.class)
public class AuthorScheduler implements Runnable {

    @Activate
    private AuthorScheduler.Config config;

    private static final Logger logger = LoggerFactory.getLogger(AuthorScheduler.class);

    @ObjectClassDefinition(name = "Author Scheduler",
            description = "Simple demo for cron-job like task with properties")
    public static @interface Config {

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "0 0/1 * 1/1 * ? *";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(
                name = "Enabled",
                description = "True, if scheduler service is enabled",
                type = AttributeType.BOOLEAN)
        public boolean enabled() default false;
    }

    @Override
    public void run() {
        if (config.enabled()) {
            logger.info("Scheduler is running in Author mode");
        }
        else {
            logger.info("Scheduler is running in Publish mode");
        }
    }
}
