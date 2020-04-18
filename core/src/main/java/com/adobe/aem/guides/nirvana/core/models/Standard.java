package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.services.AddHtmlExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.text.SimpleDateFormat;
import java.util.Date;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Standard {

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    AddHtmlExtension addHtmlExtension;

    @ValueMapValue
    String name;

    @ValueMapValue
    String height;

    @ValueMapValue
    String description;

    @ValueMapValue
    String websiteLink;

    @ValueMapValue
    String pathBrowser;

    @ValueMapValue
    String openInNewTab;

    @ValueMapValue
    String gender;

    @ValueMapValue
    String country;

    @ValueMapValue
    String colour;

    @ValueMapValue
    String date;

    @ValueMapValue
    String tag;

    public String getName() {
        String finalName = addString(name, "manya");
        return finalName;
        //add "2232" in begining of name
    }

    public String getHeight() {
        int x = Integer.parseInt(height);
        int finalHeight = addNumbers(x, 20);
        String finalHeightString = Integer.toString(finalHeight);
        return finalHeightString;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsiteLink() {
       return addHtmlExtension.getHtmlExtension(websiteLink, resourceResolver);
    }

    public String getPathBrowser() {
       return addHtmlExtension.getHtmlExtension(pathBrowser, resourceResolver);
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
       String finalCountry;
        if (country.equals("india")) {
           finalCountry =  addcountryCode(country, "91");
        } else {
          finalCountry =  addcountryCode(country, "86");
        }
        return finalCountry;
    }

    public String getColour() {
        return colour;
    }

    public String getDate() {
        try {
            SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date date1 = mdyFormat.parse(date);
            String strDate = mdyFormat.format(date1);
            return strDate;
        } catch (Exception e) {

        }
        //change format using java
        return date;
    }

    public String getTag() {
        return tag;
    }

    public String getHtmlExtension(String path) {
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

    public int addNumbers(int number, int numberAdd) {
        return number + numberAdd;
    }

    public String addString(String a, String b) {
        return a + b;
    }

    public String addcountryCode(String country, String code) {
        return country + code;
    }

}
