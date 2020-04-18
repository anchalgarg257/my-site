package com.adobe.aem.guides.nirvana.core.services;

import org.apache.sling.api.resource.Resource;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIMethods {

    public String getPage(Resource resource) {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            if (resource != null) {
                String name = resource.getName();
                String path = resource.getPath();
                jsonObject.put("name", name);
                jsonObject.put("path", path);
                jsonArray.put(jsonObject);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}
