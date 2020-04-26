ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.store.contexthub.maxisreferrer - store.referrer.js');

(function($, window) {
    'use strict';
    var MaxisStoreReferrer = function(name, config) {
        this.init(name, this.config);
         this.setItem('currentPage', document.location);
        var referrer = document.referrer;
        if (referrer == '' || referrer == window.location.href) {
            this.setItem('referrer', "");
        } else {
            this.setItem('referrer', document.referrer);
            var url = referrer;
			var hostname = (new URL(url)).hostname;
            if(hostname!=document.location.hostname)
            {
                var d = new Date();
				var month = d.getMonth()+1;
				var day = d.getDate();
				var date = d.getFullYear() + '/' +((''+month).length<2 ? '0' : '') + month + '/' +((''+day).length<2 ? '0' : '') + day;
			this.setItem('externalRefDate', date);
 			this.setItem('externalRef', referrer);
            }
        }
    };

    ContextHub.Utils.inheritance.inherit(MaxisStoreReferrer, ContextHub.Store.PersistedStore);
    MaxisStoreReferrer.prototype.getReferrer = function() {
        return this.getItem('referrer') || '[unknown]';
    };
    ContextHub.Utils.storeCandidates.registerStoreCandidate(MaxisStoreReferrer, 'contexthub.maxisreferrer', 0);
}(ContextHubJQ, this));
