ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.store.contexthub.maxisuserinfo - store.userinfo.js');;
(function($, window) {
    'use strict';
    var defaultConfig = {
        service: {
            jsonp: false,
            timeout: 0,
            ttl: 0,
            path: '/bin/api/didauth?action=getUserInfo'
        }
    };
    var calculateYear = function(year) {
        if (year >= 40 && year <= 99)
            return 19 + year;
        else
            return 20 + year;
    }

    var calculateAge = function(birthday) {
        var YEAR_IN_MS = 24 * 3600 * 365.25 * 1000;
        var date = new Date(birthday).getTime();
        return ~~((Date.now() - date) / YEAR_IN_MS);
    };

    var calculateGender = function(idValue) {
        if (idValue % 2 == 0)
            return "Female";
        else
            return "Male";
    }

    function MaxisUserInfo(name, config) {
        this.config = $.extend(true, {}, defaultConfig, config);
        this.init(name, this.config);
        this.queryService(false);
    }

    ContextHub.Utils.inheritance.inherit(MaxisUserInfo, ContextHub.Store.PersistedJSONPStore);

    MaxisUserInfo.prototype.successHandler = function(userInfoObject) {
        if ($.isEmptyObject(userInfoObject)) {
            this.clean();
        } else {

            this.setItem('name', userInfoObject.name);
            var nricIDValue = userInfoObject.idType && userInfoObject.idType == 'NRIC' ? userInfoObject.idValue : undefined;
            if (nricIDValue != undefined) {
                this.setItem('gender', calculateGender(nricIDValue));
                this.setItem('age', calculateAge(calculateYear(nricIDValue.substring(0, 2))))
            } else {
                this.setItem('gender', undefined);
                this.setItem('age', undefined)
            }
            if (userInfoObject.entitlements != undefined && userInfoObject.entitlements.accounts != undefined) {
                this.setItem('accounts', userInfoObject.entitlements.accounts);
            }
        }
    };

    ContextHub.Utils.storeCandidates.registerStoreCandidate(MaxisUserInfo, 'contexthub.maxisuserinfo', 0);

}(ContextHubJQ, this));