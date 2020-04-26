ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.store.contexthub.maxisonsite - store.onsitesurf.js');;
(function($, window) {
    'use strict';

    var defaultConfig = {
        service: {
            jsonp: false,
            timeout: 0,
            ttl: 0,
            path: '/etc/acs-commons/lists/mxs-revamp-generic-lists/onsite-surf-personalization/jcr:content.list.json'
        }
      };
    function MaxisOnSiteSurf(name, config) {
         this.config = $.extend(true, {}, defaultConfig, config);
        this.init(name, this.config);
        this.queryService(false);
    }
    ContextHub.Utils.inheritance.inherit(MaxisOnSiteSurf, ContextHub.Store.PersistedJSONPStore);
 	MaxisOnSiteSurf.prototype.successHandler = function(surfingObject) {
 	var path = window.location.pathname;
 	for (var i = 0; i < surfingObject.length; i++) {
            if (this.getItem(surfingObject[i].text)!=null && this.getItem(surfingObject[i].text).path == path.substring(0, path.lastIndexOf(".html"))) {
                var views = this.getItem(surfingObject[i].text).views;
                this.setItem(surfingObject[i].text, {
                    path: surfingObject[i].value || '',
                    views: views + 1
                });
            } else if (this.getItem(surfingObject[i].text)) {
             var views = this.getItem(surfingObject[i].text).views;
                this.setItem(surfingObject[i].text, {
                    path: surfingObject[i].value || '',
                    views: views
                });
            } else {
                this.setItem(surfingObject[i].text, {
                    path: surfingObject[i].value || '',
                    views: 0
                });
            }
        }
    };
    ContextHub.Utils.storeCandidates.registerStoreCandidate(MaxisOnSiteSurf, 'contexthub.maxisonsite', 0);

}(ContextHubJQ, this));