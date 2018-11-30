var username;

$( document ).ready(function() {
    username = getUsername();
    $("#Username").html(username);
    if (username == undefined) {
        //console.log("username is undefined");
        //TODO: Uncomment this in final version
        //alert("User is not logged in! You will now be redirected to login.");
        //window.location.href="index.html";
    }
    // $(".ContactContents").css("visibility","hidden");
    $("#searchbox").val("");
    
    //for testing:
    // appendText("davis r", "6");
    // appendText("the dude lewbowski", "7");
    search();
});
function dismissErr()
{
    $("#adderr").html("");
    $("#name").val("");
    $("#phone").val("");
    $("#address").val("");
    
}
function add(event) {
    

    if($("#name").val() == "") /*&& $("#phone").val() == "" && $("#address").val*/
    {
        $("#adderr").html("Name is required");
        $(event.target).attr("data-dismiss", "");
        return;
    }
    $(event.target).attr("data-dismiss", "modal");
    var contact = { name: $("#name").val(),
                    phone: $("#phone").val(),
                    address: $("#address").val(),
                    username: username };
    var r;
    $.post(
        "https://peopleorderourpatties.com/back-end/testapi/contact/create.php",
        JSON.stringify(contact),
        function(result){search();
            r = result.message;
            console.log(r);});

    if(r == "Contact was created.")
        dismissErr();

    $("#name").val("");
    $("#phone").val("");
    $("#address").val("");
    
    
    
}

function getUsername() {
    return Cookies.get("username");
}

//DISPLAY CONTACT INFO 
function display(event)
{
    event.stopPropagation();
    
    // $(".image").css("visibility","hidden");
    var id = $(event.target).parent().attr("id");
    var json = "{ \"id\":\"" + id + "\"}";
    var IDattr = "t" +id;
    // if($(".ContactContents").hasClass("slide-in"))
    // {
    //     $(".ContactContents").addClass("slide-out");
    //     $(".ContactContents").removeClass("slide-in");
    // }
    // else{
    //     $(".ContactContents").addClass("slide-in");
    //     $(".ContactContents").removeClass("slide-out");
    // }
    // checking if the name was clicked or not
    console.log("id " + id);
    console.log("tid" + IDattr);
    console.log($(".ContactContents").attr('id'));
    // if(!$(".ContactContents").hasClass("slide-in"))
    //     $(".ContactContents").addClass("slide-in");

    if(typeof($(".ContactContents").attr('id')) == "undefined")
    {
        $(".ContactContents").removeClass("slide-in-back");
        $(".ContactContents").removeClass("slide-out");
        $(".ContactContents").addClass("slide-in");
        $(".ContactContents").attr('id', IDattr);
    }
    else if($(".ContactContents").attr('id') != IDattr)
    {
        $(".ContactContents").attr('id', IDattr);
        $(".ContactContents").removeClass("slide-in");
        $(".ContactContents").addClass("slide-in-back");
        window.setTimeout(function(){ 
        $(".ContactContents").removeClass("slide-in-back");
        $(".ContactContents").addClass("slide-in");
        }, 0);
        // $(".ContactContents").addClass("slide-in");
        // $(".ContactContents").removeClass("slide-out");
        // $(".ContactContents").addClass("slide-in");
        
       
        // $("#" + IDattr).css("visibility", "hidden");
    }
    else 
    {
        $(".ContactContents").attr('id', IDattr);
        if($(".ContactContents").hasClass("slide-in"))
        {
            $(".ContactContents").removeClass("slide-in");
            $(".ContactContents").addClass("slide-out");
           
        }
        else 
        {
            $(".ContactContents").removeClass("slide-out");
            $(".ContactContents").addClass("slide-in");
           
        }
    }
    
    console.log($(".ContactContents").attr('class'));
    console.log($("#" + IDattr).attr('style'));
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "https://peopleorderourpatties.com/back-end/testapi/contact/display.php", false);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
    //display toggle
    // if($("#" + IDattr).hasClass("slide-out"))
    // {
    //     $(".ContactContents").removeClass("slide-out");
    //     $(".ContactContents").addClass("slide-in");
       
    // }

    // }    $("#" + IDattr).css("visibility","visible");
    
    // else
    //     {
    //         $(".ContactContents").removeClass("slide-out");
    //         $(".ContactContents").addClass("slide-in");
    //         // $("#" + IDattr).css("visibility", "hidden");
    //         // $(".image").css("visibility","visible");
    //     }

    try
    {
        xhr.send(json);
        //console.log(xhr.responseText);
        var jsonObject = JSON.parse( xhr.responseText );
        //console.log(jsonObject);

        var name = jsonObject.records[0].name;
        var phone = jsonObject.records[0].phone;
        var address = jsonObject.records[0].address;
        $("#contactname").html(name);
        $("#contactphone").html(phone);
        $("#contactaddress").html(address);

        
    
    }
    catch (err)
    {
        //alert(err);
    }
}

function search()
{
    var json = "{ \"username\":\"" + username + "\", \"keyword\":\"" + $("#searchbox").val() +  "\" }";
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "https://peopleorderourpatties.com/back-end/testapi/contact/search.php", false);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");

    try
    {
        xhr.send(json);
        var jsonObject = JSON.parse( xhr.responseText );
        var i;
        var contactTable = $("#contactTable").empty();
        for(i=0; i<jsonObject.records.length; i++)
        {
            
            appendText(jsonObject.records[i].name, jsonObject.records[i].id);
        }
    }
    catch (err)
    {
        //alert(err);
    }
}

function del(event)
{
    event.stopPropagation();
    var id = $(event.currentTarget).parent().parent().attr("id");
    // console.log("del "+id);
    // console.log(event.currentTarget);
    var json = "{ \"username\":\"" + username + "\", \"id\":" + id +  " }";
    //console.log(json);
    // post resquest - id and user name
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "https://peopleorderourpatties.com/back-end/testapi/contact/delete.php", false);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");

    try
    {
        xhr.send(json);
        //console.log(xhr.responseText);
        var jsonObject = JSON.parse( xhr.responseText );
    }
    catch (err)
    {
        //alert(err);
    }

    //delete will also get rid of the table but does not affect tables of undelte objects
    $(event.currentTarget).parent().parent().remove();
    if($(".ContactContents").attr('id') == ("t" + id))
    {
        $(".ContactContents").removeClass("slide-in");
        $(".ContactContents").removeClass("slide-in-back");
        $(".ContactContents").addClass("slide-out");
    }
    else
    {

    }
} 


//this function purpose is solely for testing, add onmouseover event to test
// function testinfo(event)
// {
//     event.stopPropagation();
//     var id = $(event.currentTarget).parent().parent().attr("id");
//     console.log(id);
// }

function appendText(name, id)
{
    //TODO: fix the table click to be able to open a contact
    //onClick=\"open(event);\"
    var text = "<tr id=\"" + id + "\" class=\"nameSelector\"> <td onClick=\"display(event);\">" + name + 
        "<button class=\"btn btn-back btn-outline-secondary float-right \" onClick=\"del(event);\" type=\"button\"><span class=\"fa fa-trash\"></span></button></td></tr>";
    //console.log(text);
    var contactTable = $("#contactTable");//.append("<tr ").attr(".html("<span class=\"fa fa-trash\"></span>")
    contactTable.append(text);
}
$( document ).ready(function() {
    $("#searchbox").keyup(function(event) {
        if (event.keyCode == 13) {
            $("#searchClick").click();
        }
    });
});
      