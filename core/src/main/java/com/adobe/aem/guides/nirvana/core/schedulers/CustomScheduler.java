package com.adobe.aem.guides.nirvana.core.schedulers;

import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
import com.adobe.aem.guides.nirvana.core.services.EmailService;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Designate(ocd = CustomScheduler.Config.class)
@Component(immediate = true, service = Runnable.class)
public class CustomScheduler implements Runnable {
    @Reference
    EmailService emailService;

    @Activate
    private CustomScheduler.Config config;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @ObjectClassDefinition(name = "Custom Scheduler",
            description = "Simple demo for cron-job like task with properties")
    public static @interface Config {

        @AttributeDefinition(name = "Cron-job expression")
        String schedulerExpression() default "0 0/1 * 1/1 * ? *";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean schedulerConcurrent() default false;

        @AttributeDefinition(name = "A parameter",
                description = "Can be configured in /system/console/configMgr")
        String myParameter() default "";

        @AttributeDefinition(name = "Email To Recipients",
                description = "Who will receive the e-mail")
        String recipientEmail();

        @AttributeDefinition(name = "Email cc To Recipients",
                description = "Who will receive the e-mail in CC")
        String ccRecipientEmail();

        @AttributeDefinition(name = "Email Subject",
                description = "What will be the Email Subject")
        String emailSubject();

        @AttributeDefinition(name = "Email From",
                description = "Who will send the Email")
        String emailFrom();

        @AttributeDefinition(name = "Email Message",
                description = "What is written in Email")
        String emailMessage();

        @AttributeDefinition(name = "Scheduler turn off",
                description = "By clicking, scheduler will be turned off", type = AttributeType.BOOLEAN)
        boolean schedulerOff();

        @AttributeDefinition(name = "Path",
                description = "Path where property should be saved")
        String path();

        @AttributeDefinition(name = "Property",
                description = "Name of the property which needs to be saved")
        String property();
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomScheduler.class);

    @Override
    public void run() {
        logger.info("CustomScheduler is running");
        if (!config.schedulerOff()) {


            try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, ApplicationConstants.SYSTEM_USER_NIRVANA_SYSTEM_USER_SERVICE))) {

                Resource resource = resourceResolver.getResource(config.path());


                if (resource == null) {
                    logger.error("Resource is null at path {}", config.path());
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();

                    ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
                    if (modifiableValueMap != null) {
                        modifiableValueMap.put(config.property(), formatter.format(date));
                        resource.getResourceResolver().commit();
                        logger.debug("{} has been modified at {}", config.property(), config.path());
                        emailService.sendEmail(config.emailFrom(), config.recipientEmail(), config.ccRecipientEmail(), config.emailSubject(), config.emailMessage());
                        logger.debug("Mail has been sent at {}", config.recipientEmail());
                    }
                }
            } catch (Exception e) {
                logger.error("Exception in Custom scheduler", e);
            }
        }
    }
}

