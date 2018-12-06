$( document ).ready(function() {
    $("#name").hide();
	$("#email").hide();
	$("#telephone").hide(); 
	$("#city").hide(); 
	$("#state").hide(); 
	$("#description").hide(); 
	$("#submitbutton").hide(); 
	$("#zipcode").hide(); 
    $("#cancelbutton").hide();
    $("#editbutton").hide();
    $("#changeIMG").hide();
    console.log(Cookies.get("profile") === Cookies.get("username"));
    
    if (Cookies.get("profile") === Cookies.get("username"))
    {
        //this profile is logged in user. Allow editing.
        $("#editbutton").show();
        $("#changeIMG").show();
        //console.log(Cookies.get("profile") === Cookies.get("username"));
    }
    
    var profileToLoad = Cookies.get("profile");
    console.log(profileToLoad);
    loadData(profileToLoad);
});

function loadData(profileToLoad) {
    //connect to showprofile.php -> send username in json
    //post 
    var url;

    if (Cookies.get("type") == "foodtruck")
    {
        url = "https://peopleorderourpatties.com/backend/api2/vendors/showProfile.php";
    }
    else
    {
        url = "https://peopleorderourpatties.com/backend/api2/foodtrucks/showProfile.php";
    }
    if (Cookies.get("profile") == Cookies.get("username"))
    {
        url = "https://peopleorderourpatties.com/backend/api/" + Cookies.get("type") + "s/showProfile.php";
    }

    var data = { username : Cookies.get("profile")};

    console.log(url);
    console.log(data);
    var response;
    
    $.post(
        url,
        JSON.stringify(data),
        function(result)
        {
            console.log(result);
            console.log(result.records[0].imgURL);
            
            //$(".hero-image").css("background-image", "linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url(" + result.records[0].imgURL + ") !important;");
            $(".hero-image").css("background-image", "url(" + result.records[0].imgURL + ")");
            $("#nameField").text(result.records[0].first + " " + result.records[0].last);
            $("#telephoneField").text(result.records[0].phone);
            $("#emailField").text(result.records[0].email);
            $("#cityField").text(result.records[0].city);
            $("#stateField").text(result.records[0].state);
            $("#zipcodeField").text(result.records[0].zip);
            $("#descriptionField").text(result.records[0].description);
        });

    


    
    //from response, fill in label datas.

}

function editData() {

	$("#editbutton").hide(); 
    $("#cancelbutton").show();
    
    $("#nameField").hide(); 
	$("#name").show(); 
	$("#name").val($("#nameField").text()); 

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
    
    if (Cookies.get("profile") == Cookies.get("username"))
    {
        url = "https://peopleorderourpatties.com/backend/api2/" + Cookies.get("type") + "s/editProfile.php";
    }
    
    console.log(url);
    
    var submit = {  username : Cookies.get("username"),
                        usernameBefore : Cookies.get("username"),
                        first : $("#name").val(),
                        last : " ",
                        phone : $("#telephone").val(),
                        email : $("#email").val(),
                        city : $("#city").val(),
                        state : $("#state").val(),
                        zip : $("#zipcode").val(), 
                        description : $("#description").val() };
/*
                        $ft->usernameBefore = $data->usernameBefore;
$ft->username = $data->username;
$ft->truck_name = $data->truck_name;
$ft->address = $data->address;
$ft->city = $data->city;
$ft->state = $data->state;
$ft->zip = $data->zip;
$ft->first = $data->first;
$ft->last = $data->last;
$ft->email = $data->email;
$ft->phone = $data->phone;
$ft->description = $data->description;*/
                        
    var response;
    
    $.post(
        url,
        JSON.stringify(submit),
        function(result)
        {
            console.log(result);
            //location.reload();
        });
}

function cancelData() {
    location.reload();
}
