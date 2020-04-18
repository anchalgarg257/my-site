package com.adobe.aem.guides.nirvana.core.services;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

@Component(service = AddHtmlExtension.class, immediate = true)
public class AddHtmlExtension {

    public String getHtmlExtension(String path, ResourceResolver resourceResolver) {
        try {
            if (path != null && path != "") {
                Resource resource = resourceResolver.getResource(path);
                if (resource != null && resource.isResourceType("cq:Page")) {
                    return path + ".html";
                }
            }
        } catch (Exception e) {
        }
        return path;
    }
}
