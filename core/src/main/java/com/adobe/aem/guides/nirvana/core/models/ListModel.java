package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.pojo.ListPojo;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ListModel {

    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue
    String type;

    @Inject
    private Resource fixedList;

    @Inject
    private Resource customList;

    List<ListPojo> list = new ArrayList();

    public List<ListPojo> getPageList() {

        try {
            if (type.equals("fixedlist")) {

                return getFixedList();
            } else {
                return getCustomList();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    public List<ListPojo> getFixedList() {

        List<ListPojo> list1 = new ArrayList();

        Iterator<Resource> iterator = fixedList.listChildren();
        while (iterator.hasNext()) {
            Resource childNode = iterator.next();
            ValueMap valueMap = childNode.getValueMap();
            String pagePath = valueMap.get("pagePath", String.class);
            if (pagePath != null) {

                Resource pageResource = resourceResolver.getResource(pagePath);
                if (pageResource != null && pageResource.isResourceType("cq:Page")) {
                    pagePath = pagePath + ".html";
                    String isIconAvailable = valueMap.get("isiconavailable", String.class);

                    boolean bool = Boolean.parseBoolean(isIconAvailable);
                    String className = valueMap.get("className", String.class);

                    Page page = pageResource.adaptTo(Page.class);
                    if (page != null) {
                        String title = page.getTitle() != null ? page.getTitle() : page.getName();

                        ListPojo pojo = new ListPojo(title, pagePath, bool, className);
                        list1.add(pojo);
                    }
                }
            }
        }
        return list1;
    }

    public List<ListPojo> getCustomList() {
        Iterator<Resource> iterator = customList.listChildren();
        List<ListPojo> list1 = new ArrayList();
        while (iterator.hasNext()) {
            Resource childNode = iterator.next();
            ValueMap valueMap = childNode.getValueMap();
            String pagePath = valueMap.get("pagePath", String.class);

            if (pagePath != null) {

                Resource pageResource = resourceResolver.getResource(pagePath);
                if (pageResource != null && pageResource.isResourceType("cq:Page")) {
                    pagePath = pagePath + ".html";
                }

                String pageName = valueMap.get("pageName", String.class);
                String isIconAvailable = valueMap.get("isiconavailable", String.class);

                boolean bool = Boolean.parseBoolean(isIconAvailable);

                String className = valueMap.get("className", String.class);

                ListPojo pojo = new ListPojo(pageName, pagePath, bool, className);
                list1.add(pojo);
            }
        }
        return list1;
    }

}


