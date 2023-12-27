
const APICartUrl = "/api/v1/buyer/cart"
const APIOrderUrl = "/api/v1/buyer/order"
const redirectUrlDefault = "/home"

const shopVoucherEls = $(".shop-voucher");
const platformVoucherEl = $("#platform-voucher")[0];

const timeReload = 1500;

    // DELETE
deleteBtns.forEach(deleteBtn => {
    deleteBtn.addEventListener("click", (e) => {
        e.preventDefault();
        displayModalPermission();
        closeBtn.onclick = () => {
            closeModalPermission();
		}
        acceptBtn.onclick = () => {
            closeModalPermission();

            push(deleteBtn.id, "DELETE", APICartUrl, "/cart");
        }
    })
})

    
    // CHECK OUT
checkOutBtn.addEventListener("click", (e) => {
    e.preventDefault();

    let cart = {};
	let orders = [];

    orderContents.forEach((orderContent, index) => {
        let order = {};
        let orderItems = [];
                
        let tmpCheckBoxes = $(orderContent).find('.check-box-item');
        
        $.each(tmpCheckBoxes, (i, checkBox) => {
            if(checkBox.checked) {
                let orderItem = {};
                let productItem = {};
                
                productItem["id"] = checkBox.id;
                orderItem["quantity"] = quantityEls[i].value;
                orderItem["productItem"] = productItem;
                
                orderItems.push(orderItem);
            }
        })
        
        
        if(orderItems.length > 0) {
            order["shopVoucher"] = shopVoucherEls[index].value;
            order["orderItems"] = orderItems;

            orders.push(order);
        }
    })


    cart["platformVoucher"] = platformVoucherEl.value;
    cart["orders"] = orders;

    // push(orders, "POST", APIOrderUrl, redirectUrlDefault);
    console.log(cart);
})