$( document ).ready(function() {
	$("#email").hide();
	$("#telephone").hide(); 
	$("#city").hide(); 
	$("#state").hide(); 
	$("#description").hide(); 
	$("#submitbutton").hide(); 
	$("#zipcode").hide(); 
});

function editData() {

	$("#editbutton").hide(); 

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


	var url = loginFT ? "https://peopleorderourpatties.com/backend/vendors/edit.php";
	var submit = {  username:Cookies.get("username"),
		              	telephone: $("#telephone").val(),
						email: $("#email").val(),
						city: $("#city").val(),
						state: $("#state").val(),
						zipcode: $("#zipcode").val(), 
						description: $("#description").val()
								 };

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


