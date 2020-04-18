package com.adobe.aem.guides.nirvana.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Notes {

    @ValueMapValue
    String danger;
    @ValueMapValue
    String colour;
    @ValueMapValue
    String sometext;

    public String getDanger() {
        return danger;
    }
public String getColour() {
        return colour;
    }
    public String getSometext() {
        return sometext;
    }
}
