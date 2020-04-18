$(document).ready(function(){
    // Get value on button click and show alert
    $("#myBtn").click(function(){
        var str = $("#myInput").val();
        alert(str);

var abc = $('input#foo').val();
var number = $('input#zoo').val();

        $.ajax({
        type: "GET",
        url: "/bin/searchpages",
        data: { 
           
            Path: str,
            RootPath: abc,
            NumberofPages: number
        },

        success: function(result) {
            alert(result);
        },
        error: function(result) {
            alert('error');
        }
    });


    });
});