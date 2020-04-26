$(document).on("foundation-contentloaded", function(e) {
    $("form [data-validation='cq-dialog-devicePath']").updateErrorUI();
});

$.validator.register({
    selector: "form [data-validation='cq-dialog-devicePath']",
    clear: function(el) {
        var text = el.val();
        var PRODUCT_LIST = "./productList",
            CAPACITY = "./capacity";
        var productList = new CUI.Select({
            element: $("[name='" + PRODUCT_LIST + "']").closest(".coral-Select")
        });
        var capacity = new CUI.Select({
            element: $("[name='" + CAPACITY + "']").closest(".coral-Select")
        });
        if (_.isEmpty(productList)) {
            return;
        }
        productList._selectList.children().not("[role='option']").remove();
        if (text != "") {
            $.ajax({
                url: '/bin/querybuilder.json',
                data: {
                    'path': text,
                    'property': 'jcr:content/sling:resourceType',
                    'property.value': 'mxs/components/structure/productpage',
                    'type': 'cq:Page',
                    'p.limit': -1
                },
                type: 'GET',
                success: function(data) {
                    var $form = productList.$element.closest("form");
                    $.getJSON($form.attr("action") + ".json").done(function(value) {
                        if (value.devicePath == text)
                            var product = value.productList;
                        if (_.isEmpty(productList)) {
                            return;
                        }
                        $("[name='./productList']").closest(".coral-Select").find('option').remove().end();
                        $("[name='./productList']").closest(".coral-Select").find('li').remove().end();
                        $("[name='./capacity']").closest(".coral-Select").find('option').remove().end();
                        $("[name='./capacity']").closest(".coral-Select").find('li').remove().end();
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
                        if (_.isEmpty(value.capacity)) {
                            return;
                        }
                        var cap = value.capacity;
                        var contentType = $("[name='./productList']").val();
                        var capacity = [];
                        $.getJSON(contentType + ".1.json").done(function(value) {
                            var articleList = value["jcr:content"]["articleList"];
                            $("[name='./capacity']").closest(".coral-Select").find('option').remove().end();
                            $("[name='./capacity']").closest(".coral-Select").find('li').remove().end();
                            var capacityField = $("[name='./capacity']")[0];
                            $("<option>").appendTo(capacityField).html("Select Capacity");
                            if (typeof(articleList) == 'object') {
                                for (var i = 0; i < articleList.length; i++) {
                                    var article = JSON.parse(articleList[i])
                                    capacity.push(article.deviceCapacity);
                                }
                                var uniqueArray = Array.from(new Set(capacity));
                                for (var j = 0; j < uniqueArray.length; j++) {
                                    $("<option>").appendTo(capacityField).val(uniqueArray[j]).html(uniqueArray[j]);
                                }
                            } else if (typeof(articleList) == 'string') {
                                var article = JSON.parse(articleList)
                                $("<option>").appendTo(capacityField).val(article.deviceCapacity).html(article.deviceCapacity);
                            }
                            capacity = new CUI.Select({
                                element: $("[name='" + CAPACITY + "']").closest(".coral-Select")
                            });
                            if (!_.isEmpty(cap)) {
                                capacity.setValue(cap);
                            }
                        });
                    });
                }
            });
        }
    }
});
