package com.adobe.aem.guides.nirvana.core.servlets;

        import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
        import com.adobe.aem.guides.nirvana.core.services.TagService;
        import com.day.cq.tagging.Tag;
        import org.apache.commons.lang.StringUtils;
        import org.apache.sling.api.SlingHttpServletRequest;
        import org.apache.sling.api.SlingHttpServletResponse;
        import org.apache.sling.api.resource.Resource;
        import org.apache.sling.api.servlets.HttpConstants;
        import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.osgi.framework.Constants;
        import org.osgi.service.component.annotations.Component;
        import org.osgi.service.component.annotations.Reference;

        import javax.servlet.Servlet;
        import java.io.IOException;
        import java.util.Iterator;

/**
 * The type Tag servlet.
 * This servlet reads the name ond path of all the tags and its children tags.
 */
@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Tag Servlet Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/tag",
})
public class TagServlet extends SlingSafeMethodsServlet {
    @Reference
    private TagService tagService;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        try {

            String path = request.getParameter(ApplicationConstants.PATH);
            String child = request.getParameter(ApplicationConstants.CHILD);

            if (path == null) {

               path = tagService.getPath();
            }

            if (child == null) {
                child = Boolean.toString(tagService.getChildren());

            }

            JSONArray jsonArray = new JSONArray();

            Resource resource = request.getResourceResolver().getResource(path);
            if (resource != null) {
                Tag tag = resource.adaptTo(Tag.class);
                JSONObject jsonObject = new JSONObject();

                if (tag != null) {

                    String tagTitle = tag.getTitle();
                    String tagPath = tag.getPath();

                    jsonObject.put(ApplicationConstants.TAG_TITLE, tagTitle);
                    jsonObject.put(ApplicationConstants.TAG_PATH, tagPath);

                    if (StringUtils.isNotBlank(child) && child.equals(ApplicationConstants.TRUE)) {

                        JSONArray childJsonArray = new JSONArray();

                        Iterator<Tag> iterator = tag.listChildren();

                        while (iterator.hasNext()) {
                            JSONObject childJson = new JSONObject();

                            Tag childrenTag = iterator.next();
                            String childrenTagTitle = childrenTag.getTitle();
                            String childrenTagPath = childrenTag.getPath();

                            childJson.put(ApplicationConstants.CHILDREN_TAG_TITLE, childrenTagTitle);
                            childJson.put(ApplicationConstants.CHILDREN_TAG_PATH, childrenTagPath);

                            childJsonArray.put(childJson);
                            jsonObject.put(ApplicationConstants.CHILD_JSON_ARRAY, childJsonArray);

                        }
                        jsonArray.put(jsonObject);
                    } else if (child == null || child.equals(ApplicationConstants.FALSE)) {
                        jsonArray.put(jsonObject);
                    }

                }
                response.getWriter().write(jsonArray.toString());
            }

        } catch (JSONException e) {
            response.getWriter().write(ApplicationConstants.FAILURE);
        }
    }
}