
ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.module.contexthub.maxisuserinfo - renderer.userinfo.js');

(function($) {
    'use strict';

    /**
     * Module implementation.
     *
     * @extends ContextHub.UI.BaseModuleRenderer
     * @constructor
     */
    var MaxisUserInfo = function() {};

    /* inherit from ContextHub.UI.BaseModuleRenderer */
    ContextHub.Utils.inheritance.inherit(MaxisUserInfo, ContextHub.UI.BaseModuleRenderer);

    /* renderer default config */
    MaxisUserInfo.prototype.defaultConfig = {
        /* module icon */
       icon: 'coral-Icon--user',

        /* module title */
        title: 'Maxis DID UserInfo',

        /* module is clickable */
        clickable: true,

        /* where is data stored? */
        list: '/maxisuserinfo',

        /* indicates that items should be editable */
        listType: 'input',

        /* what can be edited? */
        editable: {
            key: '/maxisuserinfo',

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
            myStore: 'maxisuserinfo'
        },

        /* module template */
       template: '<p class="contexthub-module-line1">{{i18n "Persona"}}</p>' +
        '<p class="contexthub-module-line2">{{myStore.name}}</p>',

    };

    MaxisUserInfo.prototype.getPopoverContent = function(module, popoverVariant, configOverride) {
        module.config = $.extend(true, {}, this.defaultConfig, module.config);
        return this.uber('getPopoverContent', module);
    };

    /* register module renderer */
    ContextHub.UI.ModuleRenderer('contexthub.maxisuserinfo', new MaxisUserInfo());

}(ContextHubJQ));