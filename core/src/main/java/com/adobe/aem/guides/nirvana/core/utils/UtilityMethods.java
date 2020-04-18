package com.adobe.aem.guides.nirvana.core.utils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

@Component(service = UtilityMethods.class, immediate = true)

public class UtilityMethods {

    public String getInternalLink(String path, ResourceResolver resourceResolver) {

        Resource internalresource = resourceResolver.getResource(path);
        if (internalresource == null) {
            return path;
        } else {
            return path + ".html";
        }
    }

}
