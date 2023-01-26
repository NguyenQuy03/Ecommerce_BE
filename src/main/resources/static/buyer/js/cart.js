
const priceEls = document.querySelectorAll("#price-el");
const totalPriceEls = document.querySelectorAll("#total-price-el");
const quantityEls = document.querySelectorAll("#quantity-el")
const minusBtns = document.querySelectorAll(".btn-minus")
const plusBtns = document.querySelectorAll(".btn-plus")
const deleteBtns = document.querySelectorAll(".delete-btn")
const modal = document.querySelector(".modal");
const closeBtn = document.querySelector(".close-btn");
const acceptBtn = document.querySelector(".accept-btn");
const checkAll = document.querySelector("#check-all");
let summaryPrice = document.querySelector("#summary-price");
const checkBoxes = document.querySelectorAll(".check-box-item");
const tableResponsive = document.querySelector(".table-responsive");
const cartCheckout = document.querySelector(".cart-checkout");


const formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});

$(document).ready(function() {
	totalPriceEls.forEach((item, i) => {
		item.value = formatter.format(priceEls[i].defaultValue * (quantityEls[i].defaultValue));
	})
})

function changeTotalPriceValue(index) {
	totalPriceEls[index].value = formatter.format(priceEls[index].value * (quantityEls[index].value));
	if (checkBoxes[index].checked == true) {getTotal();}
}

plusBtns.forEach((item, index) => {
	item.onclick = () => {changeTotalPriceValue(index);}
})

minusBtns.forEach((item, index) => {
	item.onclick = () => {
		if (quantityEls[index].value == 0) {
			modal.classList.add("fade");
			modal.classList.add("show");
			modal.style.display = "flex";

			closeBtn.onclick = () => {
				modal.classList.remove("show");
				modal.style.display = "none";
				quantityEls[index].value = 1;

			}

			acceptBtn.onclick = () => {
				deleteOrder(deleteBtns[index].id)
			}

		}
		changeTotalPriceValue(index);
	}
})

deleteBtns.forEach((item) => {
	item.onclick = () => {deleteOrder(item.id);}
})

checkAll.addEventListener('click', event => {
	if (event.target.checked) {
		$('tbody input[type=checkbox]').prop('checked', true);
		getTotal();
	} else {
		$('tbody input[type=checkbox]').prop('checked', false);
		summaryPrice.innerText = "$0.00"
	}
});

checkBoxes.forEach((item) => {
	item.onchange = () => {
		getTotal();
		isCheckAll() ? checkAll.checked = true : checkAll.checked = false;
	}
})

function isCheckAll() {
	var result = true;
	checkBoxes.forEach(item => {
		if (item.checked == false) {return result = false;}
	})
	return result;
}

function getTotal() {
	let total = 0;
	checkBoxes.forEach((item, index) => {
		if (item.checked == true) {
			var number = Number(totalPriceEls[index].value.replace(/[^0-9.-]+/g, ""));
			total += number;
		}
	})
	summaryPrice.innerText = formatter.format(total)
}

function deleteOrder(data) {
	$.ajax({
		url: "/api/buyer/order",
		type: "DELETE",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = "/cart";
		},
		error: function(e) {
			console.log("error")
		}
	})
}
window.onscroll = function() {myFunction()};

function myFunction() {
  if (document.documentElement.scrollTop > tableResponsive.clientHeight - cartCheckout.clientHeight - 35) {
    cartCheckout.classList.remove("cart-checkout-fixed");
  } else {
    cartCheckout.classList.add("cart-checkout-fixed");
  }
}

