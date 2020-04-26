(function($, $document) {
    "use strict";

    var PLANLIST = "./planList";

    $document.on("dialog-ready", function() {

        // Initializing planList drop down field
        var planList = new CUI.Select({
            element: $("[name='" + PLANLIST + "']").closest(".coral-Select")
        });
        if (_.isEmpty(planList)) {
            return;
        }
        queryToCollectPlans();
        function fillValues(data, selectedPlan) {

            $("[name='./planList']").closest(".coral-Select").find('option').remove().end();
            $("[name='./planList']").closest(".coral-Select").find('li').remove().end();
            _.each(data, function(value) {
               var planListVal = $("[name='./planList']")[0];
                $.ajax({
                    url: value.path + ".json",
                    type: 'GET',
                    async: false,
                    success: function(dataValue) {
                    if(value.path && dataValue.title)
                        $("<option>").appendTo(planListVal).val(value.path).html(dataValue.title);
                    }
                });
            });
            planList = new CUI.Select({
                element: $("[name='" + PLANLIST + "']").closest(".coral-Select")
            });
            if (!_.isEmpty(selectedPlan)) {

                planList.setValue(selectedPlan);
            }
        }
        function queryToCollectPlans() {
            planList._selectList.children().not("[role='option']").remove();
            var sourceURL = $("[name='./sourceUrl']").val();
            if (sourceURL != "") {
                $.ajax({
                    url: '/bin/querybuilder.json',
                    data: {
                        'path': sourceURL,
                        'property': 'sling:resourceType',
                        'property.value': 'mxs/components/content/admin/plan',
                        'orderby':'@jcr:path'
                    },
                    type: 'GET',
                    success: function(data) {
                        var $form = planList.$element.closest("form");
                        $.getJSON($form.attr("action") + ".json").done(function(successData) {
                            if (sourceURL == successData.sourceUrl)
                                var plan = successData.planList;
                            if (_.isEmpty(successData)) {
                                return;
                            }
                            fillValues(data.hits, plan);
                        });
                    }
                });
            }
        }
        $.validator.register({
            selector: "form [data-validation='cq-dialog-sourceURL']",
            clear: function(el) {
                queryToCollectPlans();
           }
        });

    });
})($, $(document));