package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.services.AddHtmlExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Link {
    @OSGiService
    AddHtmlExtension addHtmlExtension;

    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue
    String linkPath;

    @ValueMapValue
    String linkText;

    @ValueMapValue
    String openInNewTab;

    public String getLinkPath() {
      Resource resource =  resourceResolver.getResource(linkPath);

      if (resource != null && resource.isResourceType("cq:Page")) {
          linkPath = linkPath + ".html";
      }
        return linkPath;
    }
    public String getLinkText() {
      return linkText;
    }

//    public String getTarget() {
//        if (openInNewTab.equals("true")) {
//            return "_blank";
//        }
//        else {
//            return "_self";
//        }
//    }

    public String getTarget() {
        String target = "_self";
        if (openInNewTab.equals("true")) {
            target = "_blank";
        }
        return  target;
    }


}