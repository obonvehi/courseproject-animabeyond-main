$(document).ready(function () {
    $('.message').click(function () {
        $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
    $('.logButton').click(function () {         //login

        var settings = { url: "http://localhost:8080/login", method: "POST", mode:'cors', contentType: "application/json; charset=utf-8", timeout: 0, data: JSON.stringify( {
                "username": $("#inputUsername").val(),
                "password": $("#inputPassword").val()
            }),
            success: function (data, textStatus, request) {
                console.log('success: ',  request.getAllResponseHeaders());
                localStorage.setItem('token', data)
                localStorage.setItem('username',$("#inputUsername").val());
                localStorage.setItem('password',$("#inputPassword").val());
                url = "/supermarket.html";
                $(location).attr('href',url);

            }
            ,
            error: function (request, textStatus, errorThrown) {
                console.log('error: ' +textStatus + ' headers: ' + request.getAllResponseHeaders() + ' ErrorThrown: ' + errorThrown);
            }
        };
        $.ajax(settings).done(function (data, textStatus, request) {
            localStorage.setItem('token', request.getResponseHeader('authorization'))
            console.log('Done Response. Data: ', data, request.getResponseHeader('authorization'));
        });
         return false;
    });

    $('.regButton').click(function () {         //register

        var settings = { url: "http://localhost:8080/api/v1/register", method: "POST", mode:'cors', contentType: "application/json; charset=utf-8", timeout: 0, data: JSON.stringify( {
                "username": $("#regUsername").val(),
                "password": $("#regPassword").val(),
                "email": $("#regEmail").val()
            }),
            success: function (data, textStatus, request) {
                console.log('success: ',  request.getAllResponseHeaders());
                $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
            },
            error: function (request, textStatus, errorThrown) {
                console.log('error: ' + textStatus + ' headers: ' + request.getAllResponseHeaders() + ' ErrorThrown: ' + errorThrown);
            }
        };
        $.ajax(settings).done(function (data, textStatus, request) {
            localStorage.setItem('token', request.getResponseHeader('authorization'))
            console.log('Done Response. Data: ', data, request.getResponseHeader('authorization'));
        });
        return false;
    });
});