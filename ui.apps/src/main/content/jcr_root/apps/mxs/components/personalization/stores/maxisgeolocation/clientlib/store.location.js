
ContextHub.console.log(ContextHub.Shared.timestamp(), '[loading] contexthub.store.contexthub.maxislocation - store.location.js');
;
(function($, window) {
    'use strict';
      var defaultConfig = {
        /* html5 coordinates lookup settings */
        html5coordinatesDiscoveryAPI: {
            timeout: 30000,
            ttl: 15 * 60 * 1000,
            highAccuracy: false
        },

        /* initial location */
        initialValues: {
            latitude: 37.331375,
            longitude: -121.893992
        }
    };
    var MaxisGeolocation = function(name, config) {
        this.config = $.extend(true, {}, defaultConfig, config);
        this.init(name, this.config);

        /* initial location */
        var initialValues = this.config.initialValues;

        $.extend(this.config.html5coordinatesDiscoveryAPI, {
            location: initialValues
        });

        /* update module */
        this.updateLocationModule();
    };



    ContextHub.Utils.inheritance.inherit(MaxisGeolocation, ContextHub.Store.PersistedStore);


	MaxisGeolocation.prototype.updateLocationModule = function() {
        var self = this;
        var useCurrentCoordinates = true;

        if (useCurrentCoordinates && !this.areCoordinatesSet()) {
            useCurrentCoordinates = false;
        }

        /* update coordinates */
        if (useCurrentCoordinates) {
            $.when(this.getCoordinates())
                .then(function(response) {
                    self.storeLocation(response.coords || {}, self.config.html5coordinatesDiscoveryAPI.location);
                }, function() {
                    self.storeLocation({}, self.config.html5coordinatesDiscoveryAPI.location);
                });
        }
    };
   MaxisGeolocation.prototype.getCoordinates = function() {
        var result = $.Deferred();

        /* set rejected state if browser doesn't support geolocation */
        if (!window.navigator.geolocation) {
            return result.reject();
        }

        /* ask for current position (result.resolve or result.reject will be called) */
        var config = this.config.html5coordinatesDiscoveryAPI;
       console.log(" I am inside config"+config);
        window.navigator.geolocation.getCurrentPosition(result.resolve, result.reject, {
            maximumAge: config.ttl,
            timeout: config.timeout,
            enableHighAccuracy: config.highAccuracy
        });

        return result.promise();
    };

     MaxisGeolocation.prototype.areCoordinatesSet = function() {
         console.log(" I am in coordinate set");
       	 return $.isNumeric(this.getItem('latitude')) && $.isNumeric(this.getItem('longitude'));
    };
	MaxisGeolocation.prototype.storeLocation = function(location, defaults) {
        location = $.extend(defaults, location);

        /* persist latitude and longitude with 5 digits precision */
        this.setItem('latitude', parseInt(location.latitude * 1000000) / 1000000);
        this.setItem('longitude', parseInt(location.longitude * 1000000) / 1000000);
    };
     MaxisGeolocation.prototype.generateSignature = function(onSuccess, onFailure) {
        /* return if geocoder is not enabled */
        if (!this.useGeocoder) {
            return;
        }

        /* don't use reverse geocoder if UI is not displayed */


        /* are coordinates new? */
        var addressDetailsOf = this.getItem('addressDetailsOf') || {};
        var givenLatitude = this.getItem('latitude');
        var givenLongitude = this.getItem('longitude');

        /* no need to use reverse geocoder as coordinates didn't change since last request */
        if ((addressDetailsOf.latitude === givenLatitude) && (addressDetailsOf.longitude === givenLongitude)) {
            return;
        }

        /* is it a default location? copy address details without performing a query */
        var defaultLocation = this.config.initialValues.defaultLocation;

        if (defaultLocation && (givenLatitude === defaultLocation.latitude) && (givenLongitude === defaultLocation.longitude)) {
            this.setItem('address', defaultLocation.address);
            this.setItem('addressDetailsOf', defaultLocation.addressDetailsOf);
            return;
        }

        /* remove old values */
        delete this.config.service.params.client;
        delete this.config.service.params.signature;
        delete this.config.service.params.channel;

        /* get service url */
        var url = this.getServiceURL(true);

        /* remove http://host from the link */
        url = url.replace(/^https?:\/\/[^/]*/, '');

        /* generate signature */
        var self = this;

        $.ajax({
            url: this.config.signatureGenerator,
            method: 'get',
            dataType: 'json',
            cache: false,
            data: {
                url: url
            }
        }).done(function(data, textStatus, xhr) {
            if (xhr && xhr.responseJSON) {
                var params = self.config.service.params;

                /* set new parameters */
                params.client = xhr.responseJSON.client || '';
                params.channel = xhr.responseJSON.channel || '';
                params.signature = xhr.responseJSON.signature || '';

                /* execute success handler */
                onSuccess();
            } else {
                if (typeof onFailure === 'function') {
                    onFailure();
                }
            }
        }).fail(onFailure);
    };
     MaxisGeolocation.prototype.queryService = function(reload, useCurrentCoordinates) {
        var self = this;

        /* use current coordinates? */
        if (useCurrentCoordinates && !this.areCoordinatesSet()) {
            useCurrentCoordinates = false;
        }

        /* get address details */
        if (useCurrentCoordinates) {
            /* generate signature for reverse geocoder api */
            this.generateSignature(function() {
                /* details of currently persisted location */
                self.uber('queryService', reload);
            });
        } else {
            /* ask for current location and then resolve the address details */
            $.when(this.getCoordinates())
                .then(function(response) {
                    self.storeLocation(response.coords || {}, self.config.initialValues.defaultLocation);

                    /* request to reverse geocoder api */
                    self.generateSignature(function() {
                        self.uber('queryService', reload);
                    });
                }, function(error) {
                    self.failureHandler(error);

                    self.storeLocation({}, self.config.initialValues.defaultLocation);

                    /* generate signature for reverse geocoder api */
                    self.generateSignature(function() {
                        self.uber('queryService', reload);
                    });
                });
        }
    };

     MaxisGeolocation.prototype.successHandler = function(response) {

		 if (response && response.status === 'OK') {
            var country = null;
            var countryCode = null;
            var city = null;
            var street = null;
            var streetNumber = null;
            var region = null;
            var postalCode = null;
            var name = null;

            var results = response['results'] || [];
            /* response contains several address descriptions, traverse it in reverse way to gather all info from less to most detailed */
            $.each(results.reverse(), function(idx1, entry) {
                entry = entry || {};

                /* address */
                name = entry['formatted_address'] || name;

                $.each(entry['address_components'] || [], function(idx2, item) {
                    var types = item['types'] || [];

                    /* area name */
                    if ($.inArray('administrative_area_level_1', types) !== -1) {
                        region = item['short_name'] || region;
                    }

                    /* country */
                    if ($.inArray('country', types) !== -1) {
                        country = item['long_name'] || country;
                        countryCode = item['short_name'] || countryCode;
                    }

                    /* locality */
                    if ($.inArray('locality', types) !== -1) {
                        city = item['short_name'] || city;
                    }

                    /* route */
                    if ($.inArray('route', types) !== -1) {
                        street = item['short_name'] || street;
                    }

                    /* street number */
                    if ($.inArray('street_number', types) !== -1) {
                        streetNumber = item['short_name'] || streetNumber;
                    }

                    /* postal code */
                    if ($.inArray('postal_code', types) !== -1) {
                        postalCode = item['short_name'] || postalCode;
                    }
                });
            });

            /* persist gathered data */
            this.setItem('address', {
                country: country || '',
                countryCode: countryCode || '',
                city: city || '',
                street: street || '',
                streetNumber: streetNumber || '',
                region: region || '',
                name: name || '',
                postalCode: postalCode || ''
            });

            this.setItem('addressDetailsOf', {
                latitude: this.getItem('latitude'),
                longitude: this.getItem('longitude')
            });
        }

        /* return response */
        return response;

     };

    MaxisGeolocation.prototype.failureHandler = function(error) {
        ContextHub.console.log('Error while getting geolocation information:', error);
    };


    MaxisGeolocation.prototype.getTesting = function() {
        return this.getItem('name') || '[unknown]';
    };

     ContextHub.Utils.storeCandidates.registerStoreCandidate(MaxisGeolocation, 'contexthub.maxislocation', 10, function() {
        /* if geocoder is enabled, don't register this store implementation - store.geolocation.js wil be registered instead */
        var geoCoder = ContextHub.Utils.JSON.tree.getItem(window.ContextHubKernelConfig || {}, 'stores/geolocation/config/geocoder')
            || { enabled: false };

        return ('geolocation' in window.navigator) && !geoCoder.enabled;
    });
}(ContextHubJQ, this));