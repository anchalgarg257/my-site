<%@page session="false"%>
<%@ page import="org.apache.commons.lang3.ArrayUtils,
                     com.day.cq.wcm.api.WCMMode,
                     com.day.cq.wcm.foundation.ELEvaluator, com.day.cq.wcm.api.components.IncludeOptions" %><%
%><%@include file="/libs/foundation/global.jsp" %><%
%><%
    // read the redirect target from the 'page properties'
    String location = properties.get("redirectTarget", "");
    String type = properties.get("redirectType", "");
    // resolve variables in location
    location = ELEvaluator.evaluate(location, slingRequest, pageContext);
    boolean internalRedirect = properties.get("redirectInternal", false);
    // legacy default is to only redirect in publish mode:
    String[] redirectModes = properties.get("redirectModes", new String[]{"DISABLED"});
    //ArrayUtils.contains(redirectModes, WCMMode.fromRequest(request).name())
    //! WCMMode.fromRequest(request).name().equals("EDIT")
    if (! WCMMode.fromRequest(request).name().equals("EDIT")) {
        // check for recursion
        if (currentPage != null && !location.equals(currentPage.getPath()) && location.length() > 0) {
            if (internalRedirect) {
                // Remove ourselves from the context stack so we start fresh with the redirect page:
                request.setAttribute(ComponentContext.CONTEXT_ATTR_NAME, null);
                // Force the redirect page's context to proxy for us:
                IncludeOptions.getOptions(request, true).forceCurrentPage(currentPage);

                %><cq:include path="<%= location %>" resourceType="<%= resourceResolver.getResource(location).getResourceType() %>"/><%
            } else {
                // check for absolute path
                final int protocolIndex = location.indexOf(":/");
                final int queryIndex = location.indexOf('?');
                String wcmModeParam = request.getParameter("wcmmode");
                final boolean isWCMModeDisabledParameter = wcmModeParam != null && "disabled".equals(wcmModeParam);
                String redirectPath;

                if (protocolIndex > -1 && (queryIndex == -1 || queryIndex > protocolIndex)) {
                    redirectPath = location;
                } else {
                    redirectPath = request.getContextPath() + location + ".html";
                }
                if (isWCMModeDisabledParameter) {
                    if (queryIndex > 0) {
                        redirectPath += "&wcmmode=disabled";
                    } else {
                        redirectPath += "?wcmmode=disabled";
                    }
                }
                if (type.equals("301")) {
                  response.setStatus(Integer.valueOf(Integer.parseInt(type)));
                  response.setHeader("Location",redirectPath);
                 } else {
                  response.sendRedirect(redirectPath);
              }
            }
        }
        return;
    }
%>
