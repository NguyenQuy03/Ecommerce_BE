const deleteBtns = document.querySelectorAll(".delete-btn")


//HANDLE DELETE ORDER
deleteBtns.forEach((item) => {
	item.onclick = () => { deleteOrder(item.id); }
})

function deleteOrder(data) {
	$.ajax({
		url: "/api/buyer/order",
		type: "DELETE",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = "/cart";
		},
		error: function() {
			console.log("error")
		}
	})
}


//HANDLE PURCHASE
purchaseBtn.addEventListener("click", () => {
	var data = {};
	var orders = [];
	checkBoxes.forEach(item => {
		if (item.checked == true) {
			orders.push(item.id);
			data["ids"] = orders;
		}
	})
	data["id"] = cartId.value;
	
	postCart(data);
})

function postCart(data) {
	$.ajax({
		url: "/api/buyer/cart",
		type: "POST",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = "/home";
		},
		error: function() {
			console.log("error")
		}
	})
}