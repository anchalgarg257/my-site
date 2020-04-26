$(document).on("foundation-contentloaded", function(e) {
   // console.log(" This is hotlink site");
    if($('#ContentFrame').contents().find('body').hasClass("hotlink-site"))
        $(".coral-RichText-editable").addClass("hotlink-site")
});
