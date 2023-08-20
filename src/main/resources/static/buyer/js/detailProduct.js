

const productQuantity = document.querySelector(".quantity-product");
const plusBtn = document.querySelector(".btn-plus");
const minusBtn = document.querySelector(".btn-minus");
const addBtn = document.querySelector(".add-btn");
const modalSuccess = document.querySelector(".modal-success");
const stockEl = document.querySelector("#stock");
const priceEl = document.querySelector("#price");
const modelSoldOut = document.querySelector("#model-sold-out");

const variation0 = document.querySelectorAll(".variation-0")
const productItemStock = document.querySelectorAll(".product-item-stock")
const productItemPrice = document.querySelectorAll(".product-item-price")

variation0.forEach((item, index) => {
	item.addEventListener('change', () => {
		stockEl.innerHTML = productItemStock[index].value;
		priceEl.innerHTML = "$" + productItemPrice[index].value;

		addBtn.disabled = productItemStock[index].value == 0;
		plusBtn.disabled = productItemStock[index].value == 0;
		productQuantity.disabled = productItemStock[index].value == 0;
	})
})


productQuantity.addEventListener('input', editValue);

$(document).ready(function() {
	productQuantity.addEventListener('input', () => {
		if (+productQuantity.value > +productQuantity.max) {
			productQuantity.value = +productQuantity.max;
			plusBtn.setAttribute("disabled", "true");
		}
		
		if(+productQuantity.value <= 0) {
			productQuantity.value = 1;
			minusBtn.setAttribute("disabled", "true");
		}

	});

})

function editValue() {
	minusBtn.disabled = +productQuantity.value == 1;
	plusBtn.disabled = +productQuantity.value == +productQuantity.max;
}

plusBtn.addEventListener('click', (e) => {
	e.preventDefault();
	minusBtn.removeAttribute("disabled");
	plusBtn.disabled = +productQuantity.value == +productQuantity.max;
})


minusBtn.addEventListener('click', (e) => {
	e.preventDefault();
	plusBtn.removeAttribute("disabled");
	minusBtn.disabled = +productQuantity.value == 1;
})

if (modalSuccess) {
	setTimeout(() => {
		modalSuccess.classList.remove("show");
		modalSuccess.style.display = "none";
	}, 1200)

}

addBtn.addEventListener("click", (e) => {
	if (+stockEl.innerHTML == 0) {
		e.preventDefault();
		modelSoldOut.classList.add("show");
		modelSoldOut.style.display = "block"
		setTimeout(() => {
			modelSoldOut.classList.remove("show");
			modelSoldOut.style.display = "none";
		}, 1200)
	}
})



