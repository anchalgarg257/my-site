/* note: ORDERED_LIST_CMD must be registered in the js below
 * /apps/clientlibs/granite/richtext/core/js/commands/List.js
 */
(function ($) {
    "use strict";

    var _ = window._,
        Class = window.Class,
        GROUP = "mxs-richtext-roman",
        ROMAN_NUMERAL_LIST_FEATURE = "romanList",
        ORDERED_LIST_CMD = "insertorderedromanlist",
        CUI = window.CUI;

    addRomanPluginToDefaultUISettings();

    var EAEMRomanListPlugin = new Class({
        toString: "EAEMRomanNumeralListPlugin",

        extend: CUI.rte.plugins.Plugin,

        pickerUI: null,

        getFeatures: function () {
            return [ROMAN_NUMERAL_LIST_FEATURE];
        },

        initializeUI: function(tbGenerator) {
            var plg = CUI.rte.plugins;

            if (!this.isFeatureEnabled(ROMAN_NUMERAL_LIST_FEATURE)) {
                return;
            }

            this.pickerUI = tbGenerator.createElement(ROMAN_NUMERAL_LIST_FEATURE, this, false, { title: "Roman Numeral list" });
            tbGenerator.addElement(GROUP, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

            var groupFeature = GROUP + "#" + ROMAN_NUMERAL_LIST_FEATURE;

            tbGenerator.registerIcon(groupFeature, "coral-Icon coral-Icon--textRomanLowercase");
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
            var hasUC = this.editorKernel.queryState(ROMAN_NUMERAL_LIST_FEATURE, selDef);

            if (this.pickerUI != null) {
                this.pickerUI.setSelected(hasUC);
            }
        }
    });

   function addRomanPluginToDefaultUISettings(){
        var toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.inline.toolbar;
        toolbar.splice(3, 1, GROUP + "#" + ROMAN_NUMERAL_LIST_FEATURE);

        toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.fullscreen.toolbar;
        toolbar.splice(3, 1, GROUP + "#" + ROMAN_NUMERAL_LIST_FEATURE);
    }

    CUI.rte.plugins.PluginRegistry.register(GROUP,EAEMRomanListPlugin);
})(jQuery);
