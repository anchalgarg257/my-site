package com.adobe.aem.guides.nirvana.core.services;

import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;

@Component(service = GenericListService.class, immediate = true)
public class GenericListService {

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    private static final Logger logger = LoggerFactory.getLogger(GenericListService.class);

    public JSONObject getGenericListJSON(final String pathfield) {

        JSONObject jsonObject = new JSONObject();
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, ApplicationConstants.SYSTEM_USER_ACS_SYSTEM_USER_SERVICE))) {

            Resource resource = resourceResolver.getResource(pathfield + "/jcr:content/list");
            if (resource == null) {
                logger.error("Resource is null at path {}", resource);
            } else {
                Iterator<Resource> iterator = resource.listChildren();

                while (iterator.hasNext()) {

                    Resource iteratorResource = iterator.next();

                    String title = iteratorResource.getValueMap().get(ApplicationConstants.STRING_JCR_TITLE, String.class);
                    String value = iteratorResource.getValueMap().get(ApplicationConstants.STRING_VALUE, String.class);

                    if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(value)) {

                        jsonObject.put(title.trim(), value.trim());
                    }
                }
            }
        } catch (LoginException | JSONException e) {
            logger.error("Exception occured in getGenericListJSON method {} ", e);

        }
        return jsonObject;
    }
}
