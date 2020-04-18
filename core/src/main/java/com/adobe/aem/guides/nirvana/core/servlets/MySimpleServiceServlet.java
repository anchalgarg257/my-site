package com.adobe.aem.guides.nirvana.core.servlets;

import com.adobe.aem.guides.nirvana.core.services.MySimpleService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=Simple Service Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/simple",
})
public class MySimpleServiceServlet extends SlingSafeMethodsServlet {
    @Reference
    MySimpleService mySimpleService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("gender", mySimpleService.getGender());
            jsonObject.put("numberValue", mySimpleService.getNumberValue());
            jsonObject.put("configValue", mySimpleService.configValue());
            jsonObject.put("openInNewWindow", mySimpleService.getopenInNewWindow());
            jsonObject.put("stringValues", mySimpleService.getStringValues());
            jsonObject.put("userName", mySimpleService.getUserName());
            jsonObject.put("password", mySimpleService.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().write(jsonObject.toString());
    }
}
