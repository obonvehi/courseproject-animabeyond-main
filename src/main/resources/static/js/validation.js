$(document).ready(function () {
    if (localStorage.getItem('token') == null) {
        url = "/login.html";
        $(location).attr('href', url);
    }
    if (localStorage.getItem('id') == null) {
        $.ajax({
            headers: {'Authorization': localStorage.getItem('token')},
            dataType: "json",
            url: "http://localhost:8080/clients/me",
            type: 'GET',
            success: function (data) {
                console.log(data);
                localStorage.setItem('id', data.id);
                localStorage.setItem('email', data.email);
            },
            error: function (data) {
                console.log(data);
                $(location).attr('href', "/login.html");
            }
        })
    }
})