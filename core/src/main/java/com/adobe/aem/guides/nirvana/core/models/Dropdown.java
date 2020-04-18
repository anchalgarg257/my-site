package com.adobe.aem.guides.nirvana.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Dropdown {

    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue
    String listFrom;

    @ValueMapValue
    String pathfield;

    @ValueMapValue
    String[] imagepath;

    public List<Page> getPageList() {

        List<Page> list = new ArrayList<>();

        if (listFrom.equals("childPages")) {
            Resource resource = resourceResolver.getResource(pathfield);
            Page page = resource.adaptTo(Page.class);
            Iterator<Page> pageIterator = page.listChildren();
            while (pageIterator.hasNext()) {
                Page childPage = pageIterator.next();
                list.add(childPage);
            }
        } else {
            if (imagepath != null) {
                for (String str : imagepath) {

                    Resource imagepathResource = resourceResolver.getResource(str);
                    Page imagePathPage = imagepathResource.adaptTo(Page.class);
                    list.add(imagePathPage);
                }
            }
        }
        return list;
    }
}


//    Iterator<Resource> iterator = imagepath.listChildren();
//            while (iterator.hasNext()) {
//                    Resource childResource = iterator.next();
//                    ValueMap valueMap = childResource.getValueMap();
//                    String imagePath = valueMap.get("imagepath", String.class);
//
//        Resource imagePathResource = resourceResolver.getResource(imagePath);
//        Page page = imagePathResource.adaptTo(Page.class);
//        list.add(page);
//        }