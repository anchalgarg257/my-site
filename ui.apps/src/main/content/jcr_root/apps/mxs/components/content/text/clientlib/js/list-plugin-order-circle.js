(function ($) {
    "use strict";

    var _ = window._,
        Class = window.Class,
        GROUP = "mxs-richtext-circle",
        ALPHA_LIST_FEATURE = "ordercircleList",
        ORDERED_LIST_CMD = "insertorderedcircleList",
        CUI = window.CUI;

    addOrderListPluginToDefaultUISettings();

    var EAEMOrderCircleListPlugin = new Class({
        toString: "EAEMOrderCircleListPlugin",

        extend: CUI.rte.plugins.Plugin,

        pickerUI: null,

        getFeatures: function () {
            return [ALPHA_LIST_FEATURE];
        },

        initializeUI: function(tbGenerator) {
            var plg = CUI.rte.plugins;

            if (!this.isFeatureEnabled(ALPHA_LIST_FEATURE)) {
                return;
            }

            this.pickerUI = tbGenerator.createElement(ALPHA_LIST_FEATURE, this, false, { title: "Circled Number list" });
            tbGenerator.addElement(GROUP, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

            var groupFeature = GROUP + "#" + ALPHA_LIST_FEATURE;

            tbGenerator.registerIcon(groupFeature, "coral-Icon coral-Icon--addCircle");
        },

        execute: function (id, value, envOptions) {
            if (!isValidSelection()) {
                return;
            }

            this.editorKernel.relayCmd(ORDERED_LIST_CMD);

            function isValidSelection(){
                var winSel = window.getSelection();
                return winSel && (winSel.rangeCount == 1) && (winSel.getRangeAt(0).toString().length > 0);
            }
        },

        updateState: function(selDef) {
            var hasUC = this.editorKernel.queryState(ALPHA_LIST_FEATURE, selDef);

            if (this.pickerUI != null) {
                this.pickerUI.setSelected(hasUC);
            }
        }
    });

   function addOrderListPluginToDefaultUISettings(){
        var toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.inline.toolbar;
        toolbar.splice(3, 1, GROUP + "#" + ALPHA_LIST_FEATURE);

        toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.fullscreen.toolbar;
        toolbar.splice(3, 1, GROUP + "#" + ALPHA_LIST_FEATURE);
    }

    CUI.rte.plugins.PluginRegistry.register(GROUP,EAEMOrderCircleListPlugin);
})(jQuery);
