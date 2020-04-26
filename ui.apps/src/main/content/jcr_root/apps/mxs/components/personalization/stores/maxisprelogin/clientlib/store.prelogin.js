ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.store.contexthub.maxisprelogin - store.prelogin.js');

(function($, window) {
    'use strict';
    var MaxisPreLoginStore = function(name, config) {
        this.init(name, this.config);
        if (ContextHub.Utils.Cookie.exists("PRELOGIN_INFO")) {
            this.setItem('/', JSON.parse(ContextHub.Utils.Cookie.getItem("PRELOGIN_INFO")));
        } else {
            this.clean();
        }
    };

    ContextHub.Utils.inheritance.inherit(MaxisPreLoginStore, ContextHub.Store.PersistedStore);

    ContextHub.Utils.storeCandidates.registerStoreCandidate(MaxisPreLoginStore, 'contexthub.maxisprelogin', 0);
}(ContextHubJQ, this));