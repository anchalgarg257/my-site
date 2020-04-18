package com.adobe.aem.guides.nirvana.core.servlets;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/path",
})

public class NameAndPathofPage extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            String url = request.getParameter("url");
            String type = request.getParameter("type");

            ResourceResolver resourceResolver = request.getResourceResolver();
            Resource resource = resourceResolver.getResource(url);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            if (resource != null && type.equals("page")) {

                Page page = resource.adaptTo(Page.class);

                if (page != null) {
                    String name = page.getName();
                    String path = page.getPath();
                    jsonObject.put("name", name);
                    jsonObject.put("path", path);
                    jsonArray.put(jsonObject);
                    response.getWriter().println(jsonArray.toString());
                }
            } else if (resource != null && type.equals("node")) {

                Node node = resource.adaptTo(Node.class);

                if (node != null) {
                    Node childNode = node.getNode("jcr:content");
                    Property property = childNode.getProperty("jcr:title");

                    String title = property.getString();
                    String path = node.getPath();
                    jsonObject.put("title", title);
                    jsonObject.put("path", path);
                    jsonArray.put(jsonObject);
                    response.getWriter().println(jsonArray.toString());
                }
            } else {
                if (resource != null) {
                    Resource childResource = resource.getChild("jcr:content");

                    if (childResource != null) {
                        ValueMap valueMap = childResource.getValueMap();
                        String title = valueMap.get("jcr:title", String.class);
                        String path = resource.getPath();
                        jsonObject.put("title", title);
                        jsonObject.put("path", path);
                        jsonArray.put(jsonObject);
                    }
                }
                response.getWriter().println(jsonArray.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Failure");
        }
    }
}
