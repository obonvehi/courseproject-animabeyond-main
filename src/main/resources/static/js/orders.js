let orders, i;
$(document).ready(function () {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        dataType: "json",
        url: `http://localhost:8080/clients/${localStorage.getItem('id')}/orders`,
        type: 'GET',
        success: function (data) {localStorage.setItem("orders", JSON.stringify(data));}
    })
});
function ordersList() {
    orders = JSON.parse(localStorage.getItem("orders"));
    let ordersHTML = "", state,o;
    for (let count = 0; count < orders.length; count++) {
        switch (orders[count].state) {
            case 0: state = "Open"; break;
            case 1: state = "Closed"; break;
            case 2: state = "Delivered"; break;
        }
        o = orders[count];
        ordersHTML = `${ordersHTML}
                <div class="col-md-6">
                <div class="box" >
                <a href="#" onclick="editOrder('${count}');">
                <div class="detail-box">
                    <h5> Order ID: ${o.id} </h5>
                </div>
                <div class="detail-box">
                    <h6> State: ${state} </h6>
                </div>
                <div class="detail-box">
                    <h6> Creation Date: ${o.creation_date} </h6>
                </div>
                <div class="detail-box">
                    <h6> Delivery Date: ${o.delivery_date} </h6>
                </div>
                `
        if(orders[count].state==0){
            ordersHTML = `${ordersHTML} <div class="detail-box"> <h6> Closing Date: ${o.closing_date} </h6> </div>`
        }
        ordersHTML = `${ordersHTML}
                <div class="detail-box">
                <h6> Total Price: ${o.total_price}(€) </h6>
                </div>
                </div>
                </div>
                </div>
                `
    }
    $("#gallery").html(ordersHTML)
};
function editOrder(numOrder){
    localStorage.setItem("count", numOrder);
    $(location).attr('href',"/editOrder.html");
};
