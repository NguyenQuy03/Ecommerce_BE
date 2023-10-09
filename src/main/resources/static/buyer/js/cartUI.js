
const priceEls = document.querySelectorAll(".price-el");
const totalPriceEls = document.querySelectorAll(".total-price-el");
const quantityEls = document.querySelectorAll(".quantity-el")
const minusBtns = document.querySelectorAll(".btn-minus")
const plusBtns = document.querySelectorAll(".btn-plus")
const modalPermission = document.querySelector(".modal-check-permission");
const modalAnnounce = document.querySelector(".modal-announce");
const closeBtn = document.querySelector(".btn-close");
const acceptBtn = document.querySelector(".accept-btn");
const checkAll = document.querySelector("#check-all");
const checkBoxes = document.querySelectorAll(".check-box-item");
const tableResponsive = document.querySelector(".table-responsive");
const cartCheckout = document.querySelector(".cart-checkout");
const purchaseBtn = document.querySelector(".btn-purchase");

const deleteBtns = document.querySelectorAll(".btn-delete");

const cartId = document.querySelector("#cartId");

let summaryPrice = document.querySelector("#summary-price");

const formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});

$(document).ready(function() {
	getTotalCartItemPrice();

	quantityEls.forEach((item, index) => {
		item.addEventListener('input', () => {
			editQuantityValue(item, index);
		});
	})
})

function editQuantityValue(item, index) {
	if(item.value > parseInt(item.max)) {
		item.value = parseInt(item.max);
	} else if (parseInt(item.value) <= 0) {
		displayModalPermission();

		closeBtn.onclick = () => {
			closeModalPermission();
			initPrice(index)
		}
	}

	plusBtns[index].disabled = parseInt(item.value) >= parseInt(item.max);
	changeTotalCartItemPrice(index);
}

function closeModalPermission() {
	modalPermission.classList.remove("show");
	modalPermission.style.display = "none";
}

function initPrice(index) {
	quantityEls[index].value = 1;
	changeTotalCartItemPrice(index)

}

function displayModalPermission() {
	modalPermission.classList.add("fade");
	modalPermission.classList.add("show");
	modalPermission.style.display = "flex";
}

function getTotalCartItemPrice() {
	totalPriceEls.forEach((item, i) => {
		changeTotalCartItemPrice(i);
	})
}

function changeTotalCartItemPrice(index) {
	totalPriceEls[index].value = formatter.format(priceEls[index].value * (quantityEls[index].value));
	if (checkBoxes[index].checked == true) { getTotal(); }
}

//HANDLE BTN ACTIONS
plusBtns.forEach((item, index) => {
	item.onclick = () => {
		var quantityEl = quantityEls[index];
		quantityEl.value = parseInt(quantityEl.value) + 1;

		item.disabled = quantityEl.value >= parseInt(quantityEl.max)
		changeTotalCartItemPrice(index);
	}
})

minusBtns.forEach((item, index) => {
	item.onclick = () => {
		var quantityEl = quantityEls[index];
		quantityEl.value = parseInt(quantityEl.value) - 1;

		if (quantityEls[index].value == 0) {	
			displayModalPermission();

			closeBtn.onclick = () => {
				closeModalPermission();
				initPrice(index)
			}

			acceptBtn.onclick = () => {
				deleteProductItem(deleteBtns[index].id)
			}

		} else {
			changeTotalCartItemPrice(index);
		}

		plusBtns[index].disabled = quantityEl.value === 1;
	}
})

//HANDLE GET SUM PRICE
checkAll.addEventListener('click', event => {
	if (event.target.checked) {
		$('tbody input[type=checkbox]').prop('checked', true);
		getTotal();
		if (checkBoxes.length > 0) {
			purchaseBtn.removeAttribute("disabled");
		}
	} else {
		$('tbody input[type=checkbox]').prop('checked', false);
		summaryPrice.innerText = "$0.00"
		purchaseBtn.setAttribute("disabled", "true");
	}
});

checkBoxes.forEach((item) => {
	item.onchange = () => {
		getTotal();

		isCheckAll() ? checkAll.checked = true : checkAll.checked = false;
		if(leastChecked()) {
        	purchaseBtn.removeAttribute("disabled", "false");
		} else{
        	purchaseBtn.setAttribute("disabled", "true");
		}
	}
})

function isCheckAll() {
    let res = true;
	checkBoxes.forEach(item => {
		if (!item.checked) { return res = false }
	})
	return res;
}

function leastChecked() {
    var res = false;
	checkBoxes.forEach(item => {
		if (item.checked) { return res = true }
	})
	return res;
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

