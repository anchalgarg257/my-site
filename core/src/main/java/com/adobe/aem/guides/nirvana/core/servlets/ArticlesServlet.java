package com.adobe.aem.guides.nirvana.core.servlets;

import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;

/**
 * The type Articles servlet.
 * This servlet reads the tags and years of a resource and returns JSON.
 */
@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Article Servlet Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "nirvana/components/content/article",
        "sling.servlet.selectors=" + "json",
})
public class ArticlesServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        try {
            Resource pathResource = request.getResource();
            ValueMap valueMap = pathResource.getValueMap();

            JSONObject jsonObject = new JSONObject();

            String articleTags = valueMap.get(ApplicationConstants.ARTICLE_TAGS, String.class);
            if (articleTags != null) {
                Resource articleTagsResource = request.getResourceResolver().getResource(articleTags);
                if (articleTagsResource != null) {
                    jsonObject.put(ApplicationConstants.CATEGORIES, getTags(articleTagsResource));
                }
            }

            String yearPages = valueMap.get(ApplicationConstants.YEAR_PAGES, String.class);
            if (yearPages != null) {
                Resource yearPagesResource = request.getResourceResolver().getResource(yearPages);
                if (yearPagesResource != null) {
                    jsonObject.put(ApplicationConstants.DATE, getYearPages(yearPagesResource));
                }
            }

            response.getWriter().println(jsonObject.toString());

        } catch (JSONException e) {
            response.getWriter().println("Failure");
        }
    }

    /**
     * Gets tags.
     *
     * @param articleTagsResource the article tags resource
     * @return the JSON Array of tags
     * @throws JSONException the json exception
     */
    public JSONArray getTags(Resource articleTagsResource) throws JSONException {

        JSONArray categoryJsonArray = new JSONArray();

        Tag tag = articleTagsResource.adaptTo(Tag.class);
        if (tag != null) {
            Iterator<Tag> tagIterator = tag.listChildren();
            while (tagIterator.hasNext()) {
                Tag childPage = tagIterator.next();

                JSONObject jsonObject = new JSONObject();

                String tagId = childPage.getTagID();
                String title = childPage.getTitle();
                String name = childPage.getName();

                jsonObject.put(ApplicationConstants.TAG_ID, tagId);
                jsonObject.put(ApplicationConstants.TITLE, title);
                jsonObject.put(ApplicationConstants.NAME, name);

                categoryJsonArray.put(jsonObject);
            }
        }

        return categoryJsonArray;
    }

    /**
     * Gets year pages.
     *
     * @param yearPathResource the year path resource
     * @return the year pages
     * @throws JSONException the json exception
     */
    public JSONArray getYearPages(Resource yearPathResource) throws JSONException {

        JSONArray dateJsonArray = new JSONArray();

                Page page = yearPathResource.adaptTo(Page.class);
                if (page != null) {
                    Iterator<Page> pageIterator = page.listChildren();

                    while (pageIterator.hasNext()) {
                        Page childPage = pageIterator.next();
                        JSONObject yearJSON = new JSONObject();
                        JSONArray monthsJsonArray = new JSONArray();

                        Iterator<Page> childrenPages = childPage.listChildren();
                        while (childrenPages.hasNext()) {
                            Page monthsPage = childrenPages.next();

                            String monthTitle = monthsPage.getTitle();

                            monthsJsonArray.put(monthTitle);
                        }

                        String title = childPage.getTitle();

                        yearJSON.put(ApplicationConstants.YEAR, title);
                        yearJSON.put(ApplicationConstants.MONTHS, monthsJsonArray);
                        dateJsonArray.put(yearJSON);
                    }
                }
        return dateJsonArray;
    }
}
