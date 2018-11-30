 
$( document ).ready(function() {
    $("#regfoot").hide();
    $("#ven-reg").hide();
    $("#ft-reg").hide();
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

function login() {
    
}
