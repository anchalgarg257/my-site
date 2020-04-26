(function () {
    var getToggleElement = function (elementId) {
            var el = $(".cq-dialog [data-id='" + elementId + "']");
            if (el.length == 0) {
                el = $(".cq-dialog ." + elementId);
            }
            return el.closest(".coral-Form-fieldwrapper").addBack(el);
        },
        toggle = function (el) {
            var showIds = $(el).attr("data-show"),
                hideIds = $(el).attr("data-hide");
            if(!_.isUndefined(showIds)) {
                showIds.split(",").forEach(function (showId) {
                    getToggleElement(showId).show();
                });
            }
            if(!_.isUndefined(hideIds)) {
                hideIds.split(",").forEach(function (hideId) {
                    getToggleElement(hideId).hide();
                });
            }
        },
        radioToggle = function ($el) {
            $el.each(function () {
                var $this = $(this);
                //same as onLoad so that relevant radio options is checked and works likewise.
                if ($this.attr("checked") != null) {
                    toggle($this);
                }
                if ($this.data("radio-toggle") == null) {
                    $this.data("radio-toggle", "enabled");
                } else {
                    return;
                }
                $this.change(function () {
                    toggle($this);
                });
            });
        };

    $(document).on("foundation-contentloaded", function (e) {
        // if there is already an inital value make sure the according target element becomes visible
        radioToggle($("coral-radio[data-toggle]", e.target));
    });
}());
