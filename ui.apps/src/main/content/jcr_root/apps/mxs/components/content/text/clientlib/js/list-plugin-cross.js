(function ($) {
    "use strict";

    var _ = window._,
        Class = window.Class,
        GROUP = "mxs-richtext-cross",
        ALPHA_LIST_FEATURE = "crossList",
        ORDERED_LIST_CMD = "insertorderedCrossList",
        CUI = window.CUI;

    addCrossPluginToDefaultUISettings();

    var EAEMCrossListCmd = new Class({
        extend: CUI.rte.commands.List,

        toString: "EAEMCrossListCmd",

        execute: function(execDef) {
            this.superClass.execute.call(this, execDef);

            var list = this.getDefiningListDom(execDef.editContext, execDef.nodeList);

            if(!list){
                return;
            }

            if(execDef.command == "insertorderedCrossList")
            $(list).attr("class", "mxs-list cross-list");
            else if(execDef.command == "insertorderedcircleList")
            $(list).attr("class", "mxs-list circled-number-list");
            else if(execDef.command == "insertorderedTickList")
            $(list).attr("class", "mxs-list tick-list");
             else if(execDef.command == "insertorderedlistalpha")
            $(list).attr("class", "mxs-list alpha-list li-nested-bullet");
             else if(execDef.command == "insertorderedromanlist")
            $(list).attr("class", "roman");
           
        }
    });

    CUI.rte.commands.CommandRegistry.register("_list", EAEMCrossListCmd);

    var EAEMCrossListPlugin = new Class({
        toString: "EAEMCrossListPlugin",

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

            this.pickerUI = tbGenerator.createElement(ALPHA_LIST_FEATURE, this, false, { title: "Cross list" });
            tbGenerator.addElement(GROUP, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

            var groupFeature = GROUP + "#" + ALPHA_LIST_FEATURE;

            tbGenerator.registerIcon(groupFeature, "coral-Icon coral-Icon--close");
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

   function addCrossPluginToDefaultUISettings(){

        var toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.inline.toolbar;
        toolbar.splice(3, 1, GROUP + "#" + ALPHA_LIST_FEATURE);

        toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.fullscreen.toolbar;
        toolbar.splice(3, 1, GROUP + "#" + ALPHA_LIST_FEATURE);
    }

    CUI.rte.plugins.PluginRegistry.register(GROUP,EAEMCrossListPlugin);
})(jQuery);
