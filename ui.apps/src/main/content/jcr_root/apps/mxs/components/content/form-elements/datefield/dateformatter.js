"use strict";
use(function () {
var startDate="";
var endDate="";  
var defaultDate= "";
    if(this.startDate!=null & this.startDate!="")
	 startDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(this.startDate);

    if(this.endDate!=null & this.endDate!="")
     endDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(this.endDate);

    if(this.defaultDate!=null & this.defaultDate=="true")
     defaultDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date());

	return {
    		startDate: startDate,
        	endDate:endDate,
       		defaultDate:defaultDate
    };
});