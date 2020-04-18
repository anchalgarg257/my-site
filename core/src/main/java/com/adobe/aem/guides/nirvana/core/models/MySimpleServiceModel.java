package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.services.MySimpleService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = Resource.class)
public class MySimpleServiceModel {

    @OSGiService
    MySimpleService mySimpleService;

    public String getConfigValue() {
        return mySimpleService.configValue();
    }

    public String getGender() {
        return mySimpleService.getGender();
    }

    public int getNumberValue() {
        return mySimpleService.getNumberValue();
    }

    public String getUserName() {
        return mySimpleService.getUserName();
    }

    public String getPassword() {
        return mySimpleService.getPassword();
    }

    public boolean getOpenInNewWindow() {
        return mySimpleService.getopenInNewWindow();
    }

    public String getStringValues() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : mySimpleService.getStringValues()) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }
}
