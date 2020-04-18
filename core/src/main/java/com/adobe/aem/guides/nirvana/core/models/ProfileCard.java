package com.adobe.aem.guides.nirvana.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProfileCard {

    @ValueMapValue
    String image;

    @ValueMapValue
    String width;

    @ValueMapValue
    String name;

    @ValueMapValue
    String designation;

    @ValueMapValue
    String university;

    @ValueMapValue
    String alttext;

    public String getImage() {
        return image;
    }

    public String getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getUniversity() {
        return university;
    }

    public String getAlttext() {
        return alttext;
    }
}
