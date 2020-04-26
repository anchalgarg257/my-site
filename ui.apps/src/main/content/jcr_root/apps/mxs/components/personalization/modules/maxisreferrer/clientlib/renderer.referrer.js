
ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.module.contexthub.maxisreferrer - renderer.referrer.js');

(function($) {
    'use strict';

    /**
     * Module implementation.
     *
     * @extends ContextHub.UI.BaseModuleRenderer
     * @constructor
     */
    var MaxisModule = function() {};

    /* inherit from ContextHub.UI.BaseModuleRenderer */
    ContextHub.Utils.inheritance.inherit(MaxisModule, ContextHub.UI.BaseModuleRenderer);

    /* renderer default config */
    MaxisModule.prototype.defaultConfig = {
        /* module icon */
        icon: 'coral-Icon--history',

        /* module title */
        title: 'Referrer URL',

        /* module is clickable */
        clickable: true,

        /* where is data stored? */
        list: '/maxisreferrer',

        /* indicates that items should be editable */
        listType: 'input',

        /* what can be edited? */
        editable: {
            key: '/maxisreferrer',

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
            myStore: 'maxisreferrer'
        },

        /* module template */
        template:
            '<p>{{i18n "Referrer URL"}}</p>'
            +'<p>{{myStore.referrer}}'
    };

    MaxisModule.prototype.getPopoverContent = function(module, popoverVariant, configOverride) {
        module.config = $.extend(true, {}, this.defaultConfig, module.config);
        return this.uber('getPopoverContent', module);
    };

    /* register module renderer */
    ContextHub.UI.ModuleRenderer('contexthub.maxisreferrer', new MaxisModule());

}(ContextHubJQ));