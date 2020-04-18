
$(document).on("dialog-ready", function () {
    $("button[coral-multifield-add]").click(function() {debugger;
        var field = $(this).parent();
        var size = field.attr("data-maxlinksallowed");
        if (true) {
            var ui = $(window).adaptTo("foundation-ui");
            var totalLinkCount = $(this).prevAll().length;
            if (totalLinkCount >= size) {
                ui.alert("Warning", "<b>Maximum " + size + " links are allowed!</b>", "notice");
                return false;
            }
        }
    });
});
