
ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.module.contexthub.maxisonsite - renderer.maxissitesurf.js');

(function($) {
    'use strict';

    /**
     * Module implementation.
     *
     * @extends ContextHub.UI.BaseModuleRenderer
     * @constructor
     */
    var MaxisOnSiteSurf = function() {};

    /* inherit from ContextHub.UI.BaseModuleRenderer */
    ContextHub.Utils.inheritance.inherit(MaxisOnSiteSurf, ContextHub.UI.BaseModuleRenderer);

    /* renderer default config */
    MaxisOnSiteSurf.prototype.defaultConfig = {
        /* module icon */
        icon: 'coral-Icon--history',

        /* module title */
        title: 'Maxis OnSite Surf',

        /* module is clickable */
        clickable: true,

        /* where is data stored? */
        list: '/maxisonsitesurf',

        /* indicates that items should be editable */
        listType: 'input',

        /* what can be edited? */
        editable: {
            key: '/maxisonsitesurf',

            /* list of disabled properties */
            disabled: [
                /*'/hlbsessionstore/number'*/
            ],

            /* list of hidden properties */
            hidden: [
            ]
        },

        /* store mappings */
        storeMapping: {
            myStore: 'maxisonsitesurf'
        },

        /* module template */
        template:
            '<p>{{i18n "Onsite Surf"}}</p>'
            +'<p>{{myStore}}'
    };

    MaxisOnSiteSurf.prototype.getPopoverContent = function(module, popoverVariant, configOverride) {
        module.config = $.extend(true, {}, this.defaultConfig, module.config);
        return this.uber('getPopoverContent', module);
    };

    /* register module renderer */
    ContextHub.UI.ModuleRenderer('contexthub.maxisonsite', new MaxisOnSiteSurf());

}(ContextHubJQ));