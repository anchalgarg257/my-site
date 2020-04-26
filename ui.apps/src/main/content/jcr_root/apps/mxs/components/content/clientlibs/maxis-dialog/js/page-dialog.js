(function ($, $document) {
    var CREATE_PAGE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/createpagewizard.html",
        VIEW_PROPERTIES_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/properties.html",
        PRODUCT_PAGE = "/apps/mxs/templates/productpage",
        COUNTRY = "./sourceCountry",
        CONTENT_PAGE = "/apps/mxs/templates/blankpage",
        HOTLINK_PAGE = "/content/hotlink";

    // for create page wizard validation
    $document.on("foundation-contentloaded", function(){
        var hasParentPath = !_.isEmpty($("input[name=parentPath]").val());
        var hasContentPathData = !_.isEmpty( $(".foundation-content-path").data("foundationContentPath"));
        var hasViewProperties = !_.isEmpty($(".msm-config-endpoint").val());

        // using page properties dialog and create page wizard
        if(hasParentPath || hasContentPathData || hasViewProperties){
           displayHotlinkTopUp();
           if($("input[name=template]").val() === PRODUCT_PAGE || $("[name='./deviceBrand']").length > 0) {
                updateDeviceBrandValue();
           }
           if($("input[name=template]").val() === CONTENT_PAGE || $("[name='./networkOperator']").length > 0) setDynamicNetworkOperator();
        }

        // using view properties
        if(window.location.pathname.indexOf(VIEW_PROPERTIES_WIZARD_URL) == 0){
            displayHotlinkTopUp();
            if($("[name='./deviceBrand']").length > 0){
                updateDeviceBrandValue();
                setElementAsReadOnly("[name='./deviceBrand']");
            }
            if($("[name='./networkOperator']").length > 0) setElementAsReadOnly("[name='./networkOperator']");
        }
    });

    function updateDeviceBrandValue(){
        var parentElement = $("input[name=parentPath]").val();
        var initialPath = !_.isEmpty($("input[name=payload]").val());
        var viewPropertiesPath = !_.isEmpty($(".msm-config-endpoint").val());
            if(initialPath){
                var initialPath = $("input[name=payload]").val();
                var initialMatch = initialPath.match('/[^/]*$');
                parentElement = initialPath.replace(initialMatch,"");
            }else if(viewPropertiesPath){
                var viewPropertiesPath = $(".msm-config-endpoint").val();
                var viewMatch = viewPropertiesPath.match('/[^/]*$');
                parentElement = viewPropertiesPath.replace(viewMatch,"");
            }
        generateDeviceBrandValue(parentElement);
    }

    function generateDeviceBrandValue(parentElement){
            // generate deviceBrand value - productpage using parent page title
        $.getJSON(parentElement + ".1.json", function( data ) {
           $("[name='./deviceBrand']").val(data['jcr:content']['jcr:title']);
        });
        setElementAsReadOnly("[name='./deviceBrand']");
    }

    function setElementAsReadOnly(elem){
        //set deviceBrand field - productpage as read-only -
        $(elem).prop('readonly', true);
    }

    function setDynamicNetworkOperator(){
        setElementAsReadOnly("[name='./networkOperator']");
         var $country = $("[name='" + COUNTRY + "']"),
        cuiCountry = $country.closest(".coral-Select").data("select");
        if(cuiCountry){
            cuiCountry.on('selected.select', function(event){
                var country = $("[name='./sourceCountry']").val();
                if (country.trim().length > 0) {
                    $.ajax({
                        url: '/bin/querybuilder.json',
                        data: {
                            'path': '/content/mxs/en/admin/country',
                            'property': 'sling:resourceType',
                            'property.value': 'mxs/components/content/admin/country-data',
                            '1_property': 'countryTitle',
                            '1_property.value': country,
                        },
                        type: 'GET',
                        success: function(data) {
                            var node = data.hits[0].path;
                            $.getJSON(node + ".json").done(function(successData) {
                                $("[name='./networkOperator']").val(successData.contryNetwork);
                            });
                        }
                    });
                } else {
                    $("[name='./networkOperator']").val("");
                }
                });
                }
                }
    function displayHotlinkTopUp(){
        var parentPage = $(".foundation-content-path").data("foundationContentPath");
        var currentPageWizard = parentPage ? parentPage.includes(HOTLINK_PAGE) : false;
        var currentPageProperties = $("input[name=parentPath]").val() ? $("input[name=parentPath]").val().includes(HOTLINK_PAGE) : false;
        var viewProperties = $(".msm-config-endpoint").val() ? $(".msm-config-endpoint").val().includes(HOTLINK_PAGE) : false;
        var targetElement = $(".cq-checkbox-hotlink-topup");

        if(currentPageWizard || currentPageProperties || viewProperties){
          $(targetElement).parent().removeClass("hide");
       }else{
          $(targetElement).parent().addClass("hide");
       }
    }


}(jQuery, jQuery(document)));
