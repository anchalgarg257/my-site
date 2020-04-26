/*
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

(function() {
    'use strict';

    /**
     * Sample script.
     *
     * @param {Object} val1 - sample value
     * @param {Object} val2 - sample value
     * @returns {Boolean}
     */
    var testScriptfor7Days = function(val1, val2) {
        /* let the SegmentEngine know when script should be re-run */
        this.dependOn(ContextHub.SegmentEngine.Property('maxisreferrer/externalRefDate'));


        /* variables */
        var date = ContextHub.get('maxisreferrer/externalRefDate');


		var dateOne = new Date(date);
        var dateTwo = new Date();
		dateTwo.setDate(dateOne.getDate() + 7);

        return dateOne < dateTwo;
    };

    /* register function */
    ContextHub.SegmentEngine.ScriptManager.register('Referrer-Script-7Days', testScriptfor7Days);

})();
