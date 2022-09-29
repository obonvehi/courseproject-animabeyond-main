# courseproject-animabeyond
courseproject-animabeyond created by GitHub Classroom
# Course Project: 
# The Open Food Club

The Open Food Club is a kind of supermarket that sells ecological food products from the nearby producers. It has a physical shop and now it wants to develop a web application and shop in order to automate some of its processes. The Open Food Club count on us in order to implement a web application that covers its needs (well, in 10 weeks we’ll be able to cover only part of them)

The main values promoted by the Open Food Club (OFC) are:
* Health through ecological and healthy food
* Wellness and wealth in the community by consuming from local producers
* Reduce the CO2 emissions by buying local products 
* Prices that are fair for both producers and consumers

So, now let’s see how it is organized in order to translate these values into concrete actions:
* Only members of the OFC can buy in their shop. Members are asked to participate in some tasks of the club such as attending the shop, help packaging food commands, and so on. In this way members feel part of a community and costs are kept low.
* Buy directly from producers avoiding intermediaries. But this is a story for another day/project.
* Most of the products are sold in bulk to avoid packaging (again a story for another day)
* Producers would like to organize their production to avoid both cut short of production and overproduction. In the first case they wouldn’t be able to serve their clients while in the second they would need to throw away the perishable products. In both cases it would increase the cost of production. For this reason, OFC organizes its orders in well established periods (for example weekly, monthly, …) and asks their members to register for products setting the quantity they would like to buy.

OFC performs many other business processes designed according to its values but this last is the one we will need to implement. So, let’s explain it in detail:

* The periodicity of orders for each product is established by the OCT, not by users. Periodicity is expressed as follows: initial date, day of week, every x weeks or months. Most of the products have a weekly, every other week or every 3 months periodicity. Other periodicities are very rare.
* Users should be able to register for products indicating the quantity they would like to order. They should be able to modify their subscription.
* Orders are generated every Monday at 2:00h according to the product registrations. At this very moment we will say that the order is open. 
* An order represents a preorder and users are able to change it until the next Friday at 23:45h, when the order will be closed. A change can increase/decrease a quantity or delete a product. New products cannot be added (that could be a nice functionality to have, though)
* Orders can be modified several times while open. When a product is deleted it will still be listed with quantity zero.
* The state of an order can be: open, closed and delivered.
    * Open: can be modified by the user and administrator
    * Closed: can only be modified by the administrator
    * Delivered: the order can’t be modified
* When an order is generated a close and delivery date are assigned 
* Orders are delivered the Thursday after they are closed. So, commands are open on Mondays, closed the next Friday and delivered the following week on Thursday.
* A user can list his/her open, closed and delivered orders. The list shows the command id, the closing date if not closed yet, the delivery date and the total price. Including VAT
* Users can also list order details, that is, the closing date (if not closed yet), the delivery date and a  list with all products in the command with the product name, quantity, and a link to the page to modify it.

Functionality you MUST implement:
* Products and prices are read from an API. You have a file with some examples. Images are represented as Base64 binary codification fields. You can see the images using a converter. See for example: https://codebeautify.org/base64-to-image-converter 
* You’ll have details of the API in a few weeks.
* Register subscriptions: product, quantity, initial date
* Generate commands (for example at 4am) for each user and send an e-mail. Commands are modifiable, while open, and should have a closing date
* Users can modify their open commands (add?, delete, change quantities). Once modified the command is closed and can not be changed anymore.
* Planning of command freqüency for each product: init date + time interval (daily, weekly, monthly, semester, year)
* Secure the application: an user should only be able to access her own data

Functionality you should **NOT** implement:
User’s administration: you’ll assume they are already registered. You may need to create a ddbb table to store them and populate it

### Product Examples in a Json
Note that images are incomplete in the example. 

You also have a file with lots of products and complete images in "productsExample/products.json". Note that you are provided with this file just as an example. In the project you'll retrieve products from an API.

```
[
{"ID":"2928",
  "Nombre":" Bombó de castanya: 145g",
  "Precio de venta":"8.4",
  "Unidad de medida":"gr",
  "Proveedores":"CASTANYA VILADRAU, S.C.P.",
  "Impuestos cliente":"IVA 10% (Bienes)",
  "Categoría de producto":"Fuits secs | Frutos secos",
  "Imagen":"iVBORw0KGgoAAAANSUhEUgAAAJcAAAByCAYAAABeFkNIAAAACXBIWXMAAA7DAAAOwwHHb6hkAACAAElEQVR42tz9Z5BtWXqeBz7LbHNc+ryZ19e9t3x7b2BJACQoig4CyRBDIiNGGo3hcIwmGDFSKCSGQhMzCv6amD+kKI4UQyMRlAgCBOGbABqu0aaqu6rL1626Nr05fpvl5sdaJzOrGg2CIBoD6lRk5U13zj57f/uz7/t+4s6yCpeWFdhAYwNFKehIQVMH8gKWteCg9Wgh2Cgkh3Xg+oqkbgOjWWBjVXPXSo6tIHiP8B4TAkEJVCZRQhEKBUJQ1IZuZegFyHNJKBW3taffEYwaj6kDfS2YCBjWjq7x4BWdq5Kv3AetJIenLR/+4ArvvD2jt1Ry5eoaRa6o6oqTwylNbRFS0gJzG8i9pyg7jKcTqrqlzHK89bhgGJQZVeMBgQdq40AKjIGqtigpMC5ACIBg8RDpn+ ...."
 },
{"ID":"3160",
 "Nombre":" Pinyó país: Bossa 150 g",
 "Precio de venta":"13.56",
 "Unidad de medida":"gr",
 "Proveedores":"CAN GALDERIC AGRICULTURA, S.L.",
 "Impuestos cliente":"IVA 4% (Bienes)",
 "Categoría de producto":"Fuits secs | Frutos secos",
 "Imagen":"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAIBAQEBAQIBAQECAgICAgQDAgICAgUEBAMEBgUGBgYFBgYGBwkIBgcJBwYGCAsICQoKCgoKBggLDAsKDAkKCgr/2wBDAQICAgICAgUDAwUKBwYHCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgr/wAARCAUAB4ADASIAAhEBAxEB/8QAHgAAAQQDAQEBAAAAAAAAAAAABAMFBgcBAggACQr/xABREAABAwMDAwIEBAUBBgMFABMBAgMEAAURBhIhBzFBE1EIFCJhMnGBkQkVI0KhUhYkM2KxwXLR4QoXQ4KS8PElNFMYRKI1Y3MmVIMZk6Oywv/EABwBAAEFAQEBAAAAAAAAAAAAAAABAgMEBQYHCP/EADwRAAICAgICAQQBAgUDAwMCBwABAgMEERIhBTETBiJBUTIUYRUjcYGRQqGxJDPBFtHh8F..."
 }
]
```
