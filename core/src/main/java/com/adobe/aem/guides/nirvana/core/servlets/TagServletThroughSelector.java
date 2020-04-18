package com.adobe.aem.guides.nirvana.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Tags Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/tags",
})
public class TagServletThroughSelector extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        String[] selectors = request.getRequestPathInfo().getSelectors();
//     String path =  selectors[0];
//     Boolean child = Boolean.valueOf(selectors[1]);
        // Path cannot be sent through selectors because it has /. So, it can be done through query parameter only.


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("selectors", selectors);

            response.getWriter().write(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
