$( document ).ready(function() {
	$("#email").hide();
	$("#telephone").hide(); 
	$("#city").hide(); 
	$("#state").hide(); 
	$("#description").hide(); 
	$("#submitbutton").hide(); 
	$("#zipcode").hide(); 
    $("#cancelbutton").hide();
    $("#editButton").hide();
    
    if (Cookies.get("profile") == Cookies.get("username"))
    {
        //this profile is logged in user. Allow editing.
        $("#editButton").show();
    }
});

function editData() {

	$("#editbutton").hide(); 
    $("#cancelbutton").show();

	$("#telephoneField").hide(); 
	$("#telephone").show(); 
	$("#telephone").val($("#telephoneField").text()); 


	$("#emailField").hide();
	$("#email").show();
	$("#email").val($("#emailField").text());


	$("#cityField").hide();
	$("#city").show();
	$("#city").val($("#cityField").text());

	$("#stateField").hide();
	$("#state").show();
	$("#state").val($("#stateField").text());

	$("#zipcodeField").hide();
	$("#zipcode").show();
	$("#zipcode").val($("#zipcodeField").text());


	$("#descriptionField").hide();
	$("#description").show();
	$("#description").val($("#descriptionField").text());

	$("#submitbutton").show(); 



}

function submitData() {

	$("#submitbutton").hide(); 
	$("#editbutton").show(); 

    var url;
    
    if (Cookies.get("type") == "foodtruck")
    {
        url = "https://peopleorderourpatties.com/backend/api/vendors/edit.php";
    }
    else
    {
        url = "https://peopleorderourpatties.com/backend/api/foodtrucks/edit.php";
    }
    if (Cookies.get("profile") == Cookies.get("username"))
    {
        url = "https://peopleorderourpatties.com/backend/api/" + Cookies.get("type") + "s/edit.php";
    }
    
    var submit = {  username : Cookies.get("username"),
                        telephone : $("#telephone").val(),
                        email : $("#email").val(),
                        city : $("#city").val(),
                        state : $("#state").val(),
                        zipcode : $("#zipcode").val(), 
                        description : $("#description").val()};

    var response;
    
    $.post(
        url,
        JSON.stringify(submit),
        function(result)
        {
            r = result.message;
            console.log(r);
        });
}

function cancelData() {
    location.reload();
}
