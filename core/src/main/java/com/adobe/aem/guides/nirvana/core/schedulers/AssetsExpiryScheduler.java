package com.adobe.aem.guides.nirvana.core.schedulers;

import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.text.SimpleDateFormat;
import java.util.*;


@Designate(ocd = AssetsExpiryScheduler.Config.class)
@Component(service = Runnable.class)
public class AssetsExpiryScheduler implements Runnable {

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    QueryBuilder queryBuilder;

    private static final Logger logger = LoggerFactory.getLogger(AssetsExpiryScheduler.class);

    @Activate
    private AssetsExpiryScheduler.Config config;

    @ObjectClassDefinition(name = "Assets Expiry Scheduler",
            description = "Simple demo for cron-job like task with properties")
    public static @interface Config {

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "0 0/1 * 1/1 * ? *";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "Path",
                description = "Path to search expiry date")
        String path();
    }

    @Override
    public void run() {
        logger.info("Assets Expiry Scheduler is running");

        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, ApplicationConstants.SYSTEM_USER_NIRVANA_SYSTEM_USER_SERVICE))) {

            Map<String, String> predicate = new HashMap<String, String>();

            predicate.put("path", config.path());
            predicate.put("type", "dam:Asset");
            predicate.put("property", "@jcr:content/metadata/prism:expirationDate");
            predicate.put("property.operation", "exists");
            predicate.put("p.limit", "-1");

            Query query = queryBuilder.createQuery(PredicateGroup.create(predicate), resourceResolver.adaptTo(Session.class));

            Iterator<Resource> iterator = query.getResult().getResources();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                Asset asset = resource.adaptTo(Asset.class);
                if (asset != null) {

                    String assetExpiryDate = asset.getMetadataValueFromJcr("prism:expirationDate");

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    Date date = new Date();
                    String currentDate = formatter.format(date);
                    Date d1 = formatter.parse(currentDate);
                    Date d2 = formatter.parse(assetExpiryDate);

                    if (currentDate.compareTo(assetExpiryDate) < 0) {

                        long diff = d2.getTime() - d1.getTime();
                    long diffHours = diff / (60 * 60 * 1000) % 24;

                    if (diffHours < 12 || diffHours == 12) {
                        logger.info("Asset {} expiry in next 12 hours is {}", asset.getPath(), diffHours);
                    }
                }
            }
            }

        } catch (Exception e) {
            logger.error("Exception in Assets Expiry scheduler", e);
        }
    }
}
