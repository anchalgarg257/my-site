ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.store.contexthub.maxisnetwork - store.networkinfo.js');;
(function($, window) {
    'use strict';
    var defaultConfig = {
        service: {
            jsonp: false,
            timeout: 30000,
            ttl: 15 * 60 * 1000,
            host: 'ip-api.com',
            port: 80,
            path: '/json'
        }
      };
    function MaxisNetworkInfo(name, config) {
            this.config = $.extend(true, {}, defaultConfig, config);
        	this.init(name, this.config);
			this.queryService(false);

    }

    ContextHub.Utils.inheritance.inherit(MaxisNetworkInfo, ContextHub.Store.PersistedJSONPStore);
     MaxisNetworkInfo.prototype.successHandler = function(response) {
        this.setItem('/', response || {});
    };

    ContextHub.Utils.storeCandidates.registerStoreCandidate(MaxisNetworkInfo, 'contexthub.maxisnetwork', 0);
}(ContextHubJQ, this));