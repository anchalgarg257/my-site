use([],function () {
   var path = granite.resource.path;
   var resourceResolver = resource.getResourceResolver();
   var comp_resource = resourceResolver.getResource(path + '/realview-container/col1/columncontrol/col0/columncontrol/col1/cta_button');
   var product_resource = resourceResolver.getResource(path + '/realview-container/col1/text_description');
   var properties = comp_resource.properties;
   var product_props = product_resource.properties;
   var description= properties["ctaDescription"];
   var isNewWindow = properties["isNewWindow"] ? '_blank' : '';
   var isOutline= properties["ctaButtonFill"] == 'outline' ? '-' : '';
   var buttonStyle = properties["ctaButtonFill"] == 'outline' ? properties["ctaButtonStyleOutline"] : properties["ctaButtonStyleSolid"];
   var wideText = properties["ctaButtonSize"] == 'large' && description ? 'wtext' : '';
   var hasDescription = description && properties["ctaButtonSize"] == 'large' ? '<span>'+ description +'</span>' : '';
   var product_desc_text = product_props["text"] ? product_props["text"] : '';

   var btn_store = '<a href="' + properties["ctaURL"] + '" target="' + isNewWindow + '" class="btn ' + buttonStyle + isOutline + properties["ctaButtonFill"] + ' ' +
               properties["ctaButtonSize"]+ ' ' + wideText + ' ' + properties["fullwidth"] + '">' + properties["ctaText"] + ' ' + hasDescription + '</a>';
   return {
       currentPath: path,
       currentResource: comp_resource,
       buttonInStore : btn_store,
       productDescription : product_desc_text
   };
});