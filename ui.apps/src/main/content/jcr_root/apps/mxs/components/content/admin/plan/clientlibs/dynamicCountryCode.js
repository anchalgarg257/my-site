(function($, $document) {
    "use strict";
    var COUNTRY = "./sourceCountry";

    $document.on("dialog-ready", function() {
        $("[name='./countryCode']").prop("readonly", true);
     var $country = $("[name='" + COUNTRY + "']"),

        cuiCountry = $country.closest(".coral-Select").data("select");
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
                        $("[name='./countryCode']").val(successData.countryCode);
                    });
                }
            });
            }
            else
            {
             $("[name='./countryCode']").val("");
            }
        });
    });

})($, $(document));