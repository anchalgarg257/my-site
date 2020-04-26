(function($, $document) {
    "use strict";
    var PRODUCT_LIST = "./productList",
    SELECT_PRODUCT;

    $(document).on("click", ".cq-dialog-submit", function () {
        var productSelected = $("[name='" + PRODUCT_LIST + "']").val();
        if(productSelected == null){
            var selection = $("[name='" + PRODUCT_LIST + "']").closest(".coral-Select").data('select');
            selection.setValue(SELECT_PRODUCT);
        }
    });

    $document.on("foundation-contentloaded", queryProducts);

    $.validator.register({
        selector: "input[data-validation='cq-dialog-devicePath']",
        validate: function (el) {
          var container = $(el.parents('span')[1]),
              value = el.val(),
              rootPath = container.data('root-path');

            /* Verify that path starts with root */
          if ((value && value.indexOf(rootPath) !== 0) || !value) {
           $("[name='./productList']").closest(".coral-Select").find('option').remove().end();
           $("[name='./productList']").closest(".coral-Select").find('li').remove().end();
            var selection = $("[name='" + PRODUCT_LIST + "']").closest(".coral-Select").data('select');
            selection.setValue();

            return Granite.I18n.get('The field must start under the root node of {0}.', rootPath);
          }else{
            queryProducts();
          }
        }
    });

    function queryProducts(){
        var devicePath = $("[name='./devicePath']").val();
        SELECT_PRODUCT = [];
        var productList = new CUI.Select({
            element: $("[name='" + PRODUCT_LIST + "']").closest(".coral-Select")
        });

        if (_.isEmpty(productList)) {
            return;
        }
        productList._selectList.children().not("[role='option']").remove();

        if (devicePath != "") {
            $.ajax({
                url: '/bin/querybuilder.json',
                data: {
                    'path': devicePath,
                    '1_property': 'jcr:content/sling:resourceType',
                    '1_property.value': 'mxs/components/structure/productpage',
                    'orderby':'path',
                    'p.limit':'-1',
                    'type': 'cq:Page'
                },
                type: 'GET',
                success: function(data) {
                    var $form = productList.$element.closest("form");
                   //get the second select box (country) saved value
                    $.getJSON($form.attr("action") + ".json").done(function(value) {
                       if(value.devicePath == devicePath)
                        var product = value.productList;
                        if (_.isEmpty(productList)) {
                            return;
                        }
                       $("[name='./productList']").closest(".coral-Select").find('option').remove().end();
                      $("[name='./productList']").closest(".coral-Select").find('li').remove().end();

                            _.each(data.hits, function(value) {
                                SELECT_PRODUCT.push(value.path);
                            });

                            _.each(data.hits, function(value) {
                                var test2 = $("[name='./productList']")[0];

                                $("<option>").appendTo(test2)
                                    .val(value.path).html(value.title);
                            });
                        productList = new CUI.Select({
                            element: $("[name='" + PRODUCT_LIST + "']").closest(".coral-Select")
                        });

                        if (!_.isEmpty(product)) {
                            productList.setValue(product);
                        }
                    });
                }
            });
        }
    }

})($, $(document));

