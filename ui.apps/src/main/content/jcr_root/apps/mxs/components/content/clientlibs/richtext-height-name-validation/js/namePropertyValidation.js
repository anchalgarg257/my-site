(function ($) {
    'use strict';
    $(document).on("coral-component:attached", ".cmp-form--elements-name", function(e) {
        getValue($("form [data-validation='cq-dialog-name']"));
         getValue($("form [data-validation='cq-dialog-serviceName']"));
         getValue($("form [data-validation='cq-dialog-catalogTypeName']"));
         function getValue(tt)
         {
         var value= tt.val();
               if(value!="" && value!=undefined)
               {
            tt.val(value.replace(/\[0]/g,'[*]'));
        }
        }
    });
   	$(document).on("click", ".cq-dialog-submit", function (e) {
       setValue($("form [data-validation='cq-dialog-name']"));
        setValue($("form [data-validation='cq-dialog-serviceName']"));
         setValue($("form [data-validation='cq-dialog-catalogTypeName']"));

        function setValue(tt)
        {
        var value= tt.val();
        if(value!="" && value!=undefined)
        {
            tt.val(value.replace(/\[\*\]/g,'[0]'));
        }
        }
    });
})(jQuery);
