
const priceEls = document.querySelectorAll("#price-el");
const totalPriceEls = document.querySelectorAll("#total-price-el");
const quantityEls = document.querySelectorAll("#quantity-el")
const minusBtns = document.querySelectorAll(".btn-minus")
const plusBtns = document.querySelectorAll(".btn-plus")
const modalPermission = document.querySelector(".modal-check-permission");
const modalAnnounce = document.querySelector(".modal-announce");
const closeBtn = document.querySelector(".close-btn");
const closeAnnounceBtn = document.querySelector(".close-announce-btn");
const acceptBtn = document.querySelector(".accept-btn");
const checkAll = document.querySelector("#check-all");
const checkBoxes = document.querySelectorAll(".check-box-item");
const tableResponsive = document.querySelector(".table-responsive");
const cartCheckout = document.querySelector(".cart-checkout");
const purchaseBtn = document.querySelector(".purchase-btn");

const cartId = document.querySelector("#cartId");

let summaryPrice = document.querySelector("#summary-price");

const formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});

$(document).ready(function() {
	getTotalOrderPrice();

	quantityEls.forEach((item, index) => {
		item.addEventListener('input', (e) => {
			if (+item.value == +item.max) {
				item.value = +item.max;
				plusBtns[index].setAttribute("disabled", "true");
				totalPriceEls[index].value = formatter.format(priceEls[index].value * (item.value));
				e.preventDefault();
			} else {
				plusBtns[index].removeAttribute("disabled")
			}

			if (+item.value == 0) {
				modalPermission.classList.add("fade");
				modalPermission.classList.add("show");
				modalPermission.style.display = "flex";
				item.setAttribute("disabled", "true");

				closeBtn.onclick = () => {
					modalPermission.classList.remove("show");
					modalPermission.style.display = "none";
					quantityEls[index].value = 1;
					item.removeAttribute("disabled");

					totalPriceEls[index].value = formatter.format(priceEls[index].value);
					getTotal()
				}
			}

		});
	})
})

if (closeAnnounceBtn) {
	closeAnnounceBtn.onclick = () => {
		modalAnnounce.classList.remove("show");
		modalAnnounce.style.display = "none";
	}				
}

function getTotalOrderPrice() {
	totalPriceEls.forEach((item, i) => {
		item.value = formatter.format(priceEls[i].value * (quantityEls[i].value));
	})

}

function changeTotalPriceValue(index) {
	totalPriceEls[index].value = formatter.format(priceEls[index].value * (quantityEls[index].value));
	if (checkBoxes[index].checked == true) { getTotal(); }
}

//HANDLE BTN ACTIONS
plusBtns.forEach((item, index) => {
	item.onclick = () => {
		if (quantityEls[index].value > quantityEls[index].max - 1) {
			item.setAttribute("disabled", "true");
		}
		changeTotalPriceValue(index);
	}
})

minusBtns.forEach((item, index) => {
	item.onclick = () => {
		if (quantityEls[index].value == 0) {
			modalPermission.classList.add("fade");
			modalPermission.classList.add("show");
			modalPermission.style.display = "flex";

			closeBtn.onclick = () => {
				modalPermission.classList.remove("show");
				modalPermission.style.display = "none";
				quantityEls[index].value = 1;

			}

			acceptBtn.onclick = () => {
				deleteOrder(deleteBtns[index].id)
			}

		} else {
			changeTotalPriceValue(index);
		}
		if (plusBtns[index].attributes.disabled = true) {
			plusBtns[index].removeAttribute("disabled")
		}
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
