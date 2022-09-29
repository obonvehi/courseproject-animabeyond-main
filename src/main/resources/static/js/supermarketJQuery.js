$(document).ready(function () {
    $("#title").prepend(localStorage.getItem("productName"));
    $("#imgSub").prepend(`<img src=${localStorage.getItem('productImg')}  alt="">`);
    $('.newSubscription').click(function () {
        console.log(`http://localhost:8080/clients/${localStorage.getItem("id")}/products/${localStorage.getItem("productID")}/?quantity=${$("#productQuantity").val()}`)

        var settings = { url: `http://localhost:8080/clients/${localStorage.getItem("id")}/products/${localStorage.getItem("productID")}/?quantity=${$("#productQuantity").val()}`, method: "POST", mode:'cors', contentType: "application/json; charset=utf-8", timeout: 0,

            success: function (data, textStatus, request) { }
            ,
            error: function (request, textStatus, errorThrown) {
                console.log('error: ' + textStatus + ' headers: ' + request.getAllResponseHeaders() + ' ErrorThrown: ' + errorThrown);
            }
        };
        $.ajax(settings).done(function (data, textStatus, request) {
            localStorage.setItem('token', request.getResponseHeader('authorization'))
            console.log('Done Response. Data: ', data, request.getResponseHeader('authorization'));
        });
        $(location).attr('href',"/supermarket.html");
        return false;
    })
    $('.logButton1').click(function () {url = "/supermarket.html";$(location).attr('href',url);
    })
})
/****PROFILE****/
$("#logout").click(function () {
    localStorage.clear()
    $(location).attr('href', "/login.html");
})
$("#userprofile").prepend("USERNAME: "+localStorage.getItem("username"));
$("#emailprofile").prepend("EMAIL: "+localStorage.getItem("email"));
$("#idprofile").prepend("ID: "+localStorage.getItem("id"));
/**END PROFILE**/
