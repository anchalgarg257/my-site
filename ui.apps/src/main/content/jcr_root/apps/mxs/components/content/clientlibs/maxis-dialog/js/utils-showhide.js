(function(document, $) {
    "use strict";

    var validateHandler = function(e) {
        var urlPath = $(document).find("[data-validation=maxis.pathbrowser.required]");
        var api = $(urlPath).adaptTo("foundation-validation");
        if (api) {
            api.checkValidity();
            api.updateUI();
        }
    };

    $(document).on("foundation-contentloaded", function(e) {
        $(".cq-dialog-checkbox-showhide").each( function() {
            showHide($(this));
        });

         // use for dropdown with multiple values to hide/show field
          $(".cq-dialog-dropdown-showhide-multival").each( function() {
            showHideUsingDropdown($(this));
         });

    });

    $(document).on("change", ".cq-dialog-checkbox-showhide", function(e) {
        showHide($(this));
    });

    $(document).on("selected", ".cq-dialog-dropdown-showhide-multival", function(e) {
        showHideUsingDropdown($(this));
    });

    function showHide(el){
        var target = el.data("cqDialogCheckboxShowhideTarget");
        var checked = el.prop('checked');
        var value = checked ? el.val() : '';
        $(target).not(".hide").addClass("hide");
        $(target).filter("[data-showhidetargetvalue='" + value + "']").removeClass("hide");
   }

   function showHideUsingDropdown(el){
       var widget =  el.data("select");

       if (widget) {

           // get the selector to find the target elements. its stored as data-.. attribute
           var target = el.data("cqDialogDropdownShowhideMultivalTarget");
           var fieldRequired = $(target).closest(".js-coral-pathbrowser-input");
           // get the selected value
           var value = widget.getValue();

           // make sure all unselected target elements are hidden.
           var hideFor = $(target).data('showhidetargetmultivalue').split(',');

           // for required field - pathbrowser and textfield
           var isRequired = $(target + ' .coral-Form-field').data("setFieldRequiredOnShow");
           var elementName = $(target + ' .coral-Textfield').attr("name");

           $(target).not(".hide").addClass("hide");

           // unhide the target element that contains the selected value as data-showhidetargetvalue attribute
            if (hideFor.indexOf(value) > -1) {
                $(target).removeClass("hide");
                if(isRequired){
                    $("input[name='"+elementName+"']").attr('data-validation','maxis.pathbrowser.required');
                    validateRequiredField("input[name='"+elementName+"']");
                }
            }else{
                if(isRequired){
                    $("input[name='"+elementName+"']").attr('data-validation','');
                    validateRequiredField("input[name='"+elementName+"']");
                }
            }
       }
   }

   function validateRequiredField(elem){
    var api = $(elem).adaptTo("foundation-validation");
       if (api) {
           api.checkValidity();
           api.updateUI();
       }
   }
})(document,Granite.$);
