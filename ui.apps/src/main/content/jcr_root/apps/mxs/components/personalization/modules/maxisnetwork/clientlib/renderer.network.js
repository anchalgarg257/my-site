
ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.module.contexthub.maxisnetwork - renderer.network.js');

(function($) {
    'use strict';

    /**
     * Module implementation.
     *
     * @extends ContextHub.UI.BaseModuleRenderer
     * @constructor
     */
    var MaxisNetwork = function() {};

    /* inherit from ContextHub.UI.BaseModuleRenderer */
    ContextHub.Utils.inheritance.inherit(MaxisNetwork, ContextHub.UI.BaseModuleRenderer);

    /* renderer default config */
    MaxisNetwork.prototype.defaultConfig = {
        /* module icon */
        icon: 'coral-Icon--adobeSocialColor',

        /* module title */
        title: 'Maxis Network',

        /* module is clickable */
        clickable: true,

        /* where is data stored? */
        list: '/maxisnetwork',

        /* indicates that items should be editable */
        listType: 'input',

        /* what can be edited? */
        editable: {
            key: '/maxisnetwork',

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
            myStore: 'maxisnetwork'
        },

        /* module template */
        template:
            '<p>{{i18n "Network"}}</p>'
        +'<p>{{myStore.isp}}'
    };

    MaxisNetwork.prototype.getPopoverContent = function(module, popoverVariant, configOverride) {
        module.config = $.extend(true, {}, this.defaultConfig, module.config);
        return this.uber('getPopoverContent', module);
    };

    /* register module renderer */
    ContextHub.UI.ModuleRenderer('contexthub.maxisnetwork', new MaxisNetwork());

}(ContextHubJQ));