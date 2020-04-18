package com.adobe.aem.guides.nirvana.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/addproperty",
})
public class AddProperty extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            String path = request.getParameter("path");
            String name = request.getParameter("name");
            String value = request.getParameter("value");

            ResourceResolver resourceResolver = request.getResourceResolver();
            Resource resource = resourceResolver.getResource(path);

            if (resource != null && resource.isResourceType("cq:Page")) {
                Resource childResource = resource.getChild("jcr:content");

                if (childResource != null) {
                    Node node = childResource.adaptTo(Node.class);

                    if (node != null) {
                        node.setProperty(name, value);

                        Session session = node.getSession();
                        session.save();
                        response.getWriter().println("Success");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("failure");
        }
    }
}
