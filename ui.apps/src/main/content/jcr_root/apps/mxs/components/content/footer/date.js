use(function () {
var formattedDate = new java.text.SimpleDateFormat(this.mask).format(new Date());
return {
    formattedDate: formattedDate
    };
});