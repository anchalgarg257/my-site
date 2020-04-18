package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.pojo.MyDropdownPojo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyDropdown {

    @Inject
    private Resource imagepath;

    @ValueMapValue
    String text;

    @ValueMapValue
    String imagefield;

    @ValueMapValue
    String pathfield;

    public List<MyDropdownPojo> getMultifieldValues() {

      Iterator<Resource> childIterator =  imagepath.listChildren();

        List<MyDropdownPojo> list = new ArrayList();

        while (childIterator.hasNext()) {
            Resource childrenResource = childIterator.next();
           ValueMap valueMap = childrenResource.getValueMap();
          String link = valueMap.get("link", String.class);
         String text = valueMap.get("text", String.class);

         MyDropdownPojo myDropdownPojo = new MyDropdownPojo(link, text);
         list.add(myDropdownPojo);
        }
        return list;
    }

    public String getText() {
        return text;
    }

    public String getImagefield() {
        return imagefield;
    }

    public String getPathfield() {
        return pathfield;
    }
}
