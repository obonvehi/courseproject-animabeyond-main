let categoriesList;
let breedType ="All";
let productsHTML = "";
var productsData = "";
async function start(){
    categoriesList = await(await fetch("http://localhost:8080/api/v1/categories")).json()
    productsData = await(await fetch("http://localhost:8080/api/v1/products")).json()
}

async function BreedList(){
    await start()
    await products();
    document.getElementById("breed").innerHTML = ``
    document.getElementById("breed").innerHTML = `
        <th>Products</th>  <select onchange="breedType=this.value;products();">
            <option>Choose a Category</option>
            ${Object.keys(categoriesList).map(function(breed){
        return `<option>${categoriesList[breed]}</option>`
    }).join('')}
        </select>`
}

async function subscribedProducts() {
    await start()
    productsHTML = "";
    for (let count = 0; count < productsData.length; count++) {
        let dat = await (await fetch(`http://localhost:8080/clients/${localStorage.getItem('id')}/products/${productsData[count].id}`)).json()
        let subs = (!dat) ? "new" : "subscribed";

        if (dat) {
            await adaptions(productsData[count],subs)
        }
        document.getElementById("gallery").innerHTML = productsHTML
    }
}
async function products() {
    productsHTML = "";
    for (let count=0; count < productsData.length;count++ ) {
        if (productsData[count].category == (breedType) || breedType == ("All") || breedType == ("Choose a Category")) {
            let dat = await (await fetch(`http://localhost:8080/clients/${localStorage.getItem('id')}/products/${productsData[count].id}`)).json()
            let subs = (!dat) ? "new" : "subscribed";
                await adaptions(productsData[count],subs)
        }
    }
        document.getElementById("gallery").innerHTML = productsHTML
}
async function adaptions(product,subs){
    let name = product.name.replace(/['"]+/g, '');
    let img = (product.image=="")?"images/s1.png":"data:image/png;base64,"+product.image;
    await productsListHTMLGenerator(product.id,name,img,product.category, product.provider, product.price, subs);
}
async function productsListHTMLGenerator(id,name,img,category, provider, price, subs){
    productsHTML = `${productsHTML} 
                    <div class="col-6">
                        <div class="box" >
                         <a href="javascript:void(0);" onclick="newSubscription('${id}','${name}','${img}');">
                                <div class="img-box" >
                                    <img src= ${img} alt="The Open Food Club" />
                                </div>
                                    <div class="detail-box">
                                    <h6> ${name} </h6>
                                    <h6> ${category} </h6>
                                    <h6> Price: <span> € ${price} </span> </h6>
                                </div>
                                <div class=${subs}> <span> ${subs} </span> </div>
                             </a>
                        </div>
                    </div>
        `;
}

function newSubscription(id, name, img) {
        localStorage.setItem('productID',id);
        localStorage.setItem('productName',name);
        localStorage.setItem('productImg',img);
        $(location).attr('href',"/subscription.html");
}
