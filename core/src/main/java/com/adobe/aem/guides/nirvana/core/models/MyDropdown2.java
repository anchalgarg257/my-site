package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.pojo.NavigationPojo;
import com.adobe.aem.guides.nirvana.core.services.PageService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyDropdown2 {

    @OSGiService
    PageService pageService;

    @ValueMapValue
    String listFrom;

    @ValueMapValue
    String pathfield;

    @Inject
    Resource imagepath;

    @SlingObject
    ResourceResolver resourceResolver;



    public List getChildPages() {
        return pageService.getChildrenPages(resourceResolver, pathfield);
    }

    public List getMultifield() {

        List<NavigationPojo> list = new ArrayList<>();

        Iterator<Resource> iterator = imagepath.listChildren();
        while (iterator.hasNext()) {
            Resource childResource = iterator.next();
            ValueMap valueMap = childResource.getValueMap();
            String imagePath = valueMap.get("imagepath", String.class);

            Resource imagePathResource = resourceResolver.getResource(imagePath);
            Page page = imagePathResource.adaptTo(Page.class);
            String name = page.getName();
            String path = page.getPath();

            NavigationPojo navigationPojo = new NavigationPojo(name, path);
            list.add(navigationPojo);

        }
        return list;
    }

    public List getType() {

        List list = null;

        if (listFrom.equals("fixedList")) {
            list = getMultifield();
        } else if (listFrom.equals("childPages")) {
            list = getChildPages();
        }
        return list;
    }
}
