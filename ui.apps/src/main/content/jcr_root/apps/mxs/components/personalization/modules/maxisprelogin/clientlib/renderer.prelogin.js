
ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.module.contexthub.maxisprelogin - renderer.prelogin.js');

(function($) {
    'use strict';

    /**
     * Module implementation.
     *
     * @extends ContextHub.UI.BaseModuleRenderer
     * @constructor
     */
    var MaxisPreLogin = function() {};

    /* inherit from ContextHub.UI.BaseModuleRenderer */
    ContextHub.Utils.inheritance.inherit(MaxisPreLogin, ContextHub.UI.BaseModuleRenderer);

    /* renderer default config */
    MaxisPreLogin.prototype.defaultConfig = {
        /* module icon */
        icon: 'coral-Icon--history',

        /* module title */
        title: 'Maxis Prelogin UserInfo',

        /* module is clickable */
        clickable: true,

        /* where is data stored? */
        list: '/maxisprelogin',

        /* indicates that items should be editable */
        listType: 'input',

        /* what can be edited? */
        editable: {
            key: '/maxisprelogin',

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
            myStore: 'maxisprelogin'
        },

        /* module template */
        template:
            '<p>{{i18n "Maxis PreLogin UserInfo"}}</p>'
        +'<p>{{myStore.email}}: {{myStore.gender}}'

    };

    MaxisPreLogin.prototype.getPopoverContent = function(module, popoverVariant, configOverride) {
        module.config = $.extend(true, {}, this.defaultConfig, module.config);
        return this.uber('getPopoverContent', module);
    };

    /* register module renderer */
    ContextHub.UI.ModuleRenderer('contexthub.maxisprelogin', new MaxisPreLogin());

}(ContextHubJQ));