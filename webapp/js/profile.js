$( document ).ready(function() {
	$("#email").hide();
	$("#telephone").hide(); 
	$("#city").hide(); 
	$("#state").hide(); 
	$("#description").hide(); 
	$("#submitbutton").hide(); 
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

	$("#descriptionField").hide();
	$("#description").show();
	$("#description").val($("#descriptionField").text());

	$("#submitbutton").show(); 

}

function submitData() {

	$("#submitbutton").hide(); 
	$("#editbutton").show(); 


}


