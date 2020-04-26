(function ($, $document) {
    var CREATE_PAGE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/createpagewizard.html",
        VIEW_PROPERTIES = "/mnt/overlay/wcm/core/content/sites/properties.html",
        VALIDATION_PARAMETER = "maxis.tag.required";

    $document.on("foundation-contentloaded", function(){
        var $tagsPicker = $("span[data-validation='maxis.tag.required'] input:text").closest(".js-cq-TagsPickerField");
        //validateRequiredField("span[data-validation='maxis.tag.required'] input:text");
       //validateRequiredField("input[data-validation='maxis.required.field']");

        if(_.isEmpty($tagsPicker)){
            return;
        }

        var $tagsTextField = $tagsPicker.find("input:text"),
            cuiPathBrowser = $tagsPicker.data("pathBrowser");

        cuiPathBrowser.$picker.on("coral-pathbrowser-picker-confirm.tagspicker", triggerChange);

        cuiPathBrowser.dropdownList.on("selected.tagspicker", triggerChange);

        $document.on("click", ".coral-TagList-tag-removeButton", triggerChange);

        function triggerChange(){
            setTimeout(function(){
                $tagsTextField.trigger("change");
            }, 250);
        }
    });

    $(document).on("click", ".cq-dialog-submit", function (e) {
        validateRequiredField("span[data-validation='maxis.tag.required'] input:text");
    });

    $(document).on("click", ".coral-TagList-tag-removeButton .coral-Icon--close", function () {
        validateRequiredField("span[data-validation='maxis.tag.required'] input:text");
    });

    $.validator.register({
        selector: "input[data-validation='maxis.pathbrowser.required']",

        validate: function ($textField) {

        var pathfieldValue = $textField.val();

            if (!pathfieldValue) {
                return "Please fill out this field";
            }
        }
    });

    $.validator.register({
        selector: "span[data-validation='maxis.tag.required'] input:text",

        validate: function ($textField) {

        var $tagsPicker = $textField.closest(".js-cq-TagsPickerField"),
            $tagList = $tagsPicker.parent().find(".coral-TagList");

            if ($tagList.find(".coral-TagList-tag").length <= 0) {
                return "Please fill out this field";
            }

        }
    });

     //validates the required textfield and disables inside multifield submit/saving when  button
     $.validator.register({
        selector: "input[data-validation='maxis.required.field']",
        validate: function(el) {
            if(window.location.pathname.indexOf(CREATE_PAGE_WIZARD_URL) === 0){
                return;
            }else{
             var isValid = [];
              var isInvalid = [];
              var field = el.closest(".coral-Multifield-list");
              var element = field.find('input[data-validation="maxis.required.field"]');
              $.each(element , function() {
                  var elem = $(this);
                  var value = elem.val();
                  if(!value){
                     isInvalid.push(elem);
                  }else{
                     isValid.push(elem);
                  }
              });

              if (isInvalid.length > 0) {
                 $.each(isInvalid, function(){
                     var elem = $(this);
                     showMessage(elem, 'This field is required.');
                 });
                  $('#shell-propertiespage-saveactivator').attr("disabled","disabled");
                  $('.cq-dialog-submit').attr("disabled",'disabled');
              }else{
                  $('.cq-dialog-submit').removeAttr("disabled");
                  $('#shell-propertiespage-saveactivator').removeAttr("disabled");
              }

              if(isValid.length > 0){
                 $.each(isValid, function(){
                     var elem = $(this);
                     clearMessage(elem);
                 });
              }
            }
        }
    });

   function showMessage(el, message){
     var fieldErrorEl,
         field,
         error,
         arrow;

        fieldErrorEl = $("<span class='coral-Form-fielderror coral-Icon coral-Icon--alert coral-Icon--sizeS' data-init='quicktip' data-quicktip-type='error' />");
        field = el.closest(".coral-Form-field");

        field.attr("aria-invalid", "true")
          .toggleClass("is-invalid", true);

        field.nextAll(".coral-Form-fieldinfo")
          .addClass("u-coral-screenReaderOnly");

//        error = field.nextAll(".coral-Form-fielderror");
//
//        if (error.length === 0) {
//          arrow = field.closest("form").hasClass("coral-Form--vertical") ? "right" : "top";
//
//          fieldErrorEl
//            .attr("data-quicktip-arrow", arrow)
//            .attr("data-quicktip-content", message)
//            .insertAfter(field);
//        } else {
//          error.data("quicktipContent", message);
//        }
   }

   function clearMessage(el){
        var field = el.closest(".coral-Form-field");

        field.removeAttr("aria-invalid").removeClass("is-invalid");

        field.nextAll(".coral-Form-fielderror").tooltip("hide").remove();
        field.nextAll(".coral-Form-fieldinfo").removeClass("u-coral-screenReaderOnly");
   }

   function validateRequiredField(elem){
    var api = $(elem).adaptTo("foundation-validation");
       if (api) {
           var isValid = api.checkValidity();
           if(!isValid){
                showMessage($(elem),"This field is required");
           }
           api.updateUI();
       }
   }
}(jQuery, jQuery(document)));
