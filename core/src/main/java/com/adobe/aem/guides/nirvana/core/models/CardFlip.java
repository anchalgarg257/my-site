package com.adobe.aem.guides.nirvana.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardFlip {
    @ValueMapValue
    String image;
    @ValueMapValue
    String alttext;
    @ValueMapValue
    String name;
    @ValueMapValue
    String description;
    @ValueMapValue
    String designation;
    @ValueMapValue
    String width;
    @ValueMapValue
    String height;

    public String getImage() {
        return image;
    }
    public String getAlttext() {
        return alttext;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDesignation() {
        return designation;
    }
    public String getWidth() {
        return width;
    }
    public String getHeight() {
        return height;
    }
}
