var loginFT = true;


$( document ).ready(function() {
    $("#regfoot").hide();
    $("#ven-reg").hide();
    $("#ft-reg").hide();
    
    $("#logindrop").click(function(event){
        event.stopPropagation();
    });
});

function regft() {
    $("#typesel").hide();
    $("#ft-reg").show();
}

function regven() {
    $("#typesel").hide();
    $("#ven-reg").show();
}

function back() {
    $("#ft-reg").hide();
    $("#ven-reg").hide();
    $("#typesel").show();
}

function toggleBtn(event) {
//     $("#ven").prop("checked", true);
}

function ftcheck(event) {
    console.log("Food truck selected for login");
//     $("#ven").prop("checked", false);
//     $("#ft").prop("checked", true);
    event.stopPropagation();
    loginFT = true;
}

function vencheck(event) {
    console.log("Vendor selected for login");
//     $("#ven").prop("checked", true);
//     $("#ft").prop("checked", false);
    event.stopPropagation();
    loginFT = false;
}

function login() {
    var login = { username: $("#loginUser").val(),
                    password: $("#loginPass").val()};

    var url = loginFT ? "https://peopleorderourpatties.com/backend/api/users/foodtrucks/authenticate.php" :
                        "https://peopleorderourpatties.com/backend/api/users/vendors/authenticate.php";
    var response;
    $.post(
        url,
        JSON.stringify(login),
        function(result){
            r = result.message;
            console.log(r);});
}

function register() {
    console.log("Register!");
    
    var details = { username: $("#loginUser").val(),
                    email: $("#loginUser").val(),
                    password: $("#loginPass").val()};
    var response;
    $.post(
        "https://peopleorderourpatties.com/backend/api/users/foodtrucks/create.php",
        JSON.stringify(login),
        function(result){
            r = result.message;
            console.log(r);});
}

function hideLoginRegister() {
    $("#loginGroup").hide();
    $("#regButton").hide();
}
