
const priceEls = document.querySelectorAll(".price-el");
const totalPriceEls = document.querySelectorAll(".total-price-el");
const quantityEls = document.querySelectorAll(".quantity-el")

const checkOutBtn = document.querySelector(".btn-check-out");

let summaryPrice = document.querySelector("#summary-price");

const checkAll = document.querySelector("#check-all");
let checkLists = document.querySelectorAll(".check-box-list");
let orderContents = document.querySelectorAll(".order-content");
let checkBoxes = document.querySelectorAll(".check-box-item");

const deleteBtns = document.querySelectorAll(".btn-delete");

const modalPermission = document.querySelector(".modal-check-permission");
const closeBtn = document.querySelector(".btn-close");
const acceptBtn = document.querySelector(".accept-btn");

const minusBtns = document.querySelectorAll(".btn-minus")
const plusBtns = document.querySelectorAll(".btn-plus")

const inActiveCartItemEl = $("#inActiveCartItem")[0]; 

const formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});

$(document).ready(function() {
	getTotalOrderItemPrice();

	if(inActiveCartItemEl) {
		let inactiveItem = inActiveCartItemEl.value.slice(1, inActiveCartItemEl.value.length - 1).split(", ");

		inactiveItem.forEach(item => {
			checkBoxes.forEach(checkBox => {
				if(item == checkBox.id) {
					checkBox.remove()
				}
			})
		})
		checkBoxes = document.querySelectorAll(".check-box-item");
	}

	quantityEls.forEach((item, index) => {
		item.addEventListener('input', () => {
			editQuantityValue(item, index);
		});
	})
})


checkLists.forEach((item, index) => {
	item.onchange = () => {
		$(orderContents[index]).find('input[type=checkbox]').prop('checked', item.checked);

		checkAll.checked = isCheckAll(checkBoxes);

		checkOutBtn.disabled = !leastChecked();

		getTotal();
	}
})

//HANDLE BTN ACTIONS
plusBtns.forEach((item, index) => {
	item.onclick = () => {
		var quantityEl = quantityEls[index];
		quantityEl.value = parseInt(quantityEl.value) + 1;

		item.disabled = quantityEl.value >= parseInt(quantityEl.max)
		changeTotalOrderItemPrice(index);
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
			changeTotalOrderItemPrice(index);
		}

		plusBtns[index].disabled = quantityEl.value === 1;
	}
})

//HANDLE GET SUM PRICE
checkAll.addEventListener('click', event => {
	var isChecked = event.target.checked;

	if (checkBoxes.length > 0) {
		checkOutBtn.disabled = !isChecked
	}

	$('#order-container input[type=checkbox]').prop('checked', isChecked);

	if (isChecked) {
		getTotal();
	} else {
		summaryPrice.innerText = "$0.00"
	}
});

checkBoxes.forEach((item) => {
	item.onchange = () => {
		checkAll.checked = isCheckAll(checkBoxes);

		const parentElement = $(item).closest('.order-content');
		const checkBoxList = parentElement.find('.check-box-list')[0];
		const stepCheckBoxes = parentElement.find('.check-box-item');
		
		checkOutBtn.disabled = !leastChecked();

		getTotal();

		checkBoxList.checked = isCheckAll(stepCheckBoxes);
	}
})

function isCheckAll(checkBoxEls) {
    let res = true;
	$.each(checkBoxEls, (i, item) => {
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
	changeTotalOrderItemPrice(index);
}

function closeModalPermission() {
	modalPermission.classList.remove("show");
	modalPermission.style.display = "none";
}

function initPrice(index) {
	quantityEls[index].value = 1;
	changeTotalOrderItemPrice(index)

}

function displayModalPermission() {
	modalPermission.classList.add("fade");
	modalPermission.classList.add("show");
	modalPermission.style.display = "flex";
}

function getTotalOrderItemPrice() {
	totalPriceEls.forEach((item, i) => {
		changeTotalOrderItemPrice(i);
	})
}

function changeTotalOrderItemPrice(index) {
	totalPriceEls[index].value = formatter.format(priceEls[index].value * (quantityEls[index].value));
	if (checkBoxes[index].checked == true) { getTotal(); }
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

