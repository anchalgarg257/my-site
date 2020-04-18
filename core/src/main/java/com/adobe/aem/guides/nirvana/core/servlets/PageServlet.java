package com.adobe.aem.guides.nirvana.core.servlets;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Page Servlet Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/pages",
})
public class PageServlet extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            String path = request.getParameter("path");

            Resource pathResource = request.getResourceResolver().getResource(path);
            if (pathResource != null) {
                Page page = pathResource.adaptTo(Page.class);
                response.getWriter().println(page.getTitle());
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
