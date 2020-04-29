package com.adobe.aem.guides.nirvana.core.services;

import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JSONObject getGenericListJSON(final String pathfield) {

        JSONObject jsonObject = new JSONObject();
        try {

            Resource resource = resourceResolverFactory.getServiceResourceResolver(Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, ApplicationConstants.SYSTEM_USER_NIRVANA_SYSTEM_USER_SERVICE)).getResource(pathfield + "/jcr:content/list");
            if (resource == null) {
                logger.error("Resource is null at path {}", resource);
            }
            if (resource != null) {
                Iterator<Resource> iterator = resource.listChildren();

                while (iterator.hasNext()) {

                    Resource iteratorResource = iterator.next();

                    String title = iteratorResource.getValueMap().get(ApplicationConstants.STRING_JCR_TITLE.trim(), String.class);
                    String value = iteratorResource.getValueMap().get(ApplicationConstants.STRING_VALUE.trim(), String.class);

                    jsonObject.put(title, value);
                }
            }


        } catch (LoginException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
