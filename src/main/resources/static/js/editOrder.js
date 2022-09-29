let order;
$("#orderInfo").ready(function () {
    orders = JSON.parse(localStorage.getItem("orders"));
    let count = localStorage.getItem("count")
    order = orders[count];
    console.log(order);
    let tab = `&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`;
    let orderHTML = `
                            <div class="heading_container">
                                <h2 id="title">OrderID: ${order.id}</h2>
                                <h4 id="title">Delivery Date: ${order.delivery_date}</h4>
                                `
    if (order.state == 0) {
        orderHTML = `${orderHTML}<h4 id="title">Closing Date: ${order.closing_date}</h4><br>`;
        for (i = 0; i < order.productList.length; i++) {
            orderHTML = `${orderHTML} <h4 id="title">${tab}Product: ${order.productList[i].product_name}</h4>
                                   <h6 id="title">${tab}${tab}${tab}Current quantity: ${order.productList[i].amount}</h6>
                                    <input id='${i}' type="text" placeholder="New quantity" />`
        }
        orderHTML = `${orderHTML}<br>
            <div className="d-flex ">
                <button onclick="updateOrder()"> SEND</button>
            </div>`
    }
    else { orderHTML = `${orderHTML}<br>`
        for (let i = 0; i < order.productList.length; i++) {
            orderHTML = `${orderHTML} <h4 id="title">${tab}Product: ${order.productList[i].product_name}</h4>
                                   <h6 id="title">${tab}${tab}${tab}Quantity: ${order.productList[i].amount}</h6>`
        }
    }
    orderHTML = `${orderHTML}   <div class="d-flex ">
                                    <button onclick="back()"> BACK </button>
                                </div>
                        </div>
                    `
    $("#orderInfo").html(`${orderHTML}`)

});
function editOrder(numOrder){
    localStorage.setItem("count", numOrder);
    $(location).attr('href',"/editOrder.html");
};

function updateOrder() {
    for (let x=0;x<i;x++) {
        let quantityProductID = `#${x}`;
        localStorage.setItem(quantityProductID, $(quantityProductID).val());
        console.log(localStorage.getItem(quantityProductID));
        console.log(`http://localhost:8080/clients/orders/${order.id}/products/${order.productList[x].product_id}/?quantity=${$(quantityProductID).val()}`);

        var settings = { url: `http://localhost:8080/clients/orders/${order.id}/products/${order.productList[x].product_id}/?quantity=${$(quantityProductID).val()}`, method: "POST", mode:'cors', contentType: "application/json; charset=utf-8", timeout: 0,

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
    }
    $(location).attr('href',"/orders.html");
}
function back() {
    $(location).attr('href',"/orders.html");
}