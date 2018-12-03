var loginFT = true;
var regFT = true;

var pass;
var letter;
var capital;
var number;
var special;
var length;

var username;

$( document ).ready(function() {
    console.log("Username: " + Cookies.get("username"));
    
    console.log(getIfLoggedIn());
    
    if (getIfLoggedIn())
    {
        $("#labUsername").text(Cookies.get("username")) ;
        hideLoginRegister();
    }
    else
    {
        $("#labUsername").hide();
    }
    $("#regfoot").hide();
    $("#ven-reg").hide();
    $("#ft-reg").hide();
    
    $("#logindrop").click(function(event){
        event.stopPropagation();
    });
    
    $("#regft").click( function() {
        console.log("register - ft");
        $(this).addClass('active').siblings().removeClass('active');
        
    });
    
    $("#regven").click( function() {
        console.log("register - ven");
        $(this).addClass('active').siblings().removeClass('active');
    });
    
    pass = document.getElementById("regpass");
    letter = document.getElementById("letter");
    capital = document.getElementById("capital");
    number = document.getElementById("number");
    special = document.getElementById("special");
    length = document.getElementById("length");
    
    $("#message").hide();
    
    // When the user clicks on the password field, show the message box
    pass.onfocus = function() {
        $("#message").show();
    }

    // When the user clicks outside of the password field, hide the message box
    pass.onblur = function() {
        $("#message").hide();
    }
    
    // When the user starts to type something inside the password field
    pass.onkeyup = function() {
        // Validate lowercase letters
        var lowerCaseLetters = /[a-z]/g;
        if(pass.value.match(lowerCaseLetters)) {  
            letter.classList.remove("list-group-item-danger");
            letter.classList.add("list-group-item-success");
        } else {
            letter.classList.remove("list-group-item-success");
            letter.classList.add("list-group-item-danger");
        } 
        
        // Validate capital letters
        var upperCaseLetters = /[A-Z]/g;
        if(pass.value.match(upperCaseLetters)) {  
            capital.classList.remove("list-group-item-danger");
            capital.classList.add("list-group-item-success");
        } else {
            capital.classList.remove("list-group-item-success");
            capital.classList.add("list-group-item-danger");
        }

        // Validate numbers
        var numbers = /[0-9]/g;
        if(pass.value.match(numbers)) {  
            number.classList.remove("list-group-item-danger");
            number.classList.add("list-group-item-success");
        } else {
            number.classList.remove("list-group-item-success");
            number.classList.add("list-group-item-danger");
        }
        
        // Validate special
        var specials = /[-[\]{}()*+?.,%|\/\;\'\"\:&_<>`~=\-\!\@\$\\^$|#\s]/g;
        if(pass.value.match(specials)) {  
            special.classList.remove("list-group-item-danger");
            special.classList.add("list-group-item-success");
        } else {
            special.classList.remove("list-group-item-success");
            special.classList.add("list-group-item-danger");
        }
        
        // Validate length
        if(pass.value.length >= 8) {
            length.classList.remove("list-group-item-danger");
            length.classList.add("list-group-item-success");
        } else {
            length.classList.remove("list-group-item-success");
            length.classList.add("list-group-item-danger");
        }
    }
});

function ftcheck(event) {
    console.log("Food truck selected for login");
    event.stopPropagation();
    loginFT = true;
}

function vencheck(event) {
    console.log("Vendor selected for login");
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
    
    Cookies.set("username", login.username);
    $("#labUsername").show();
    $("#labUsername").text(login.username);
    hideLoginRegister();
}

function register() {
    console.log("Register!");
    
    var details = { username: $("#reguser").val(),
                    email: $("#regemail").val(),
                    password: $("#regpass").val()};
                    
    var url = regFT ? "https://peopleorderourpatties.com/backend/api/users/foodtrucks/create.php" :
                        "https://peopleorderourpatties.com/backend/api/users/vendors/create.php";
    var response;
    $.post(
        url,
        JSON.stringify(details),
        function(result){
            r = result.message;
            console.log(r);});
    Cookies.set("username", details.username);
    $("#labUsername").show();
    $("#labUsername").text(details.username);
    hideLoginRegister();
}

function getIfLoggedIn() {
    return Cookies.get("username") != undefined;
}

function hideLoginRegister() {
    $("#loginGroup").hide();
    $("#regButton").hide();
}
