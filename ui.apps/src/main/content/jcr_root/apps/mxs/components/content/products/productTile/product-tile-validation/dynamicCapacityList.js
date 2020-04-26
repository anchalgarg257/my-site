(function($, $document) {
    "use strict";
    var PRODUCT_LIST = "./productList",
        CAPACITY = "./capacity";
    $document.on("dialog-ready", function() {
        var productList1 = new CUI.Select({
            element: $("[name='" + PRODUCT_LIST + "']").closest(".coral-Select")
        });
        var capacity = new CUI.Select({
            element: $("[name='" + CAPACITY + "']").closest(".coral-Select")
        });
        productList1._selectList.on('selected.select', function(event) {
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
                console.log(uniqueArray);
            });
        });
    });
})($, $(document));