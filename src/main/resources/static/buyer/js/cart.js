
const APICartUrl = "/api/buyer/cart"
const APIOrderUrl = "/api/buyer/order"

const modalAccounceTitle = document.querySelector(".modal-accounce-title");

const timeReload = 1500;

deleteBtns.forEach(deleteBtn => {
    deleteBtn.addEventListener("click", (e) => {
        e.preventDefault();
        displayModalPermission();
        closeBtn.onclick = () => {
            closeModalPermission();
		}
        acceptBtn.onclick = () => {
            closeModalPermission();
            deleteProductItem(deleteBtn.id);
        }
    })
})

    // DELETE ProductItem
async function deleteProductItem(CartItemId) {
    try {
        const response = await fetch(APICartUrl, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(CartItemId)
        });

        if (response.ok) {
            let responseText = await response.text();
            modalAccounceTitle.innerHTML = responseText;
            modalAnnounce.classList.add("show");
            modalAnnounce.style.display = "flex";
            
            setTimeout(function() {
                location.reload();
            }, timeReload);
        } else if (response.status === 401 || response.status === 403 || response.status === 405) {
            window.location.href = "/login";
        } else if (response.status === 500) {
            let mesResponse = await response.text();
            console.log("Error message:", mesResponse);
        }
    } catch (error) {
        console.log("Error:", error);
    }
}
    // PURCHASE
purchaseBtn.addEventListener("click", (e) => {
    e.preventDefault();
	let data = {};
	let orderItems = [];
    let cartItemIds = [];
    
	checkBoxes.forEach((item, i) => {
        if (item.checked == true) {
            let orderItem = {};
            let productItem = {};

            productItem["id"] = item.id;
			orderItem["quantity"] = quantityEls[i].value;
            orderItem["curPrice"] = priceEls[i].value;
            orderItem["productItem"] = productItem;

            orderItems.push(orderItem);
            cartItemIds.push(deleteBtns[i].id);
		}
    })

	data["orderItems"] = orderItems;
    
    data["cartItemIds"] = cartItemIds;
    data["cartId"] = cartId.value;

	postOrder(data);
})

async function postOrder(data) {
    try {
        const response = await fetch(APIOrderUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            let responseText = await response.text();
            modalAccounceTitle.innerHTML = responseText;
            modalAnnounce.classList.add("show");
            modalAnnounce.style.display = "flex";
            
            setTimeout(function() {
                window.location.href = "/home";
            }, timeReload);
        } else if (response.status === 401 || response.status === 403 || response.status === 405) {
            window.location.href = "/login";
        } else if (response.status === 500) {
            let mesResponse = await response.text();
            console.log("Error message:", mesResponse);
        }
    } catch (error) {
        console.log("Error:", error);
    }
}