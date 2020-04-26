(function($, $document) {
    "use strict";
    var MINI_STORES = "./ministores",
    SELECT_STORES;

    $document.on("dialog-ready", populateData);
   $document.on("change","input:text[data-validation='maxis.update.dropdown']", populateData);

    function populateData(){
        var miniStoresList = new CUI.Select({element: $("[name='" + MINI_STORES + "']").closest(".coral-Select")});
        queryToCollectMiniStores(miniStoresList);
    }

    function queryToCollectMiniStores(miniStoresList) {
        miniStoresList._selectList.children().not("[role='option']").remove();
        var storePath = $("[name='./storePath']").val();
        var dataValue = setData( storePath);
        if (storePath != "") {
            $.ajax({
                url: '/bin/querybuilder.json',
                data: dataValue,
                type: 'GET',
                success: function(data) {
                    var $form = miniStoresList.$element.closest("form");
                    $.getJSON($form.attr("action") + ".json").done(function(value) {
                        if (value.storePath == storePath)
                            var ministores = value.ministores;
                        if (_.isEmpty(value)) {
                            return;
                        }
                        fillValues(data.hits, ministores);
                    });
                }
            });
        }
    }

    function fillValues(data, selectedStores) {
        $("[name='./ministores']").closest(".coral-Select").find('option').remove().end();
        $("[name='./ministores']").closest(".coral-Select").find('li').remove().end();
        var ministore = $("[name='./ministores']")[0];
        SELECT_STORES = [];
        //stores all the stores based on storeSelection
        _.each(data, function(value) {
            if(value){
                SELECT_STORES.push(value["path"]);
            }
        });

        //get the second select box (mini store list) saved value
        _.each(data, function(value) {
            var path = value["path"];
            $.ajax({
                url: value["path"] + ".json",
                type: 'GET',
                async: false,
                success: function(value) {
                    $("<option>").appendTo(ministore)
                        .val(path).html(value["state"] + ', ' + value["town"] + ', ' + value["storeName"]);
                }
            });
        });
        var miniStoresList = new CUI.Select({element: $("[name='" + MINI_STORES + "']").closest(".coral-Select")});

        if (!_.isEmpty(selectedStores)) {
            miniStoresList.setValue(selectedStores);
        }
    }

    function setData(storePath){
                return {'path': storePath,
                 '1_property': 'sling:resourceType',
                 '1_property.value': 'mxs/components/content/admin/location-tile',
                 '2_property': 'isMiniStore',
                 '2_property.value': 'true',
                 'p.limit': '-1'}
     }

})($, $(document));

