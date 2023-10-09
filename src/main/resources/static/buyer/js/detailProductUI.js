
const productQuantity = document.querySelector(".quantity-product");
const plusBtn = document.querySelector(".btn-plus");
const minusBtn = document.querySelector(".btn-minus");
const stockEl = document.querySelector("#stock");
const priceEl = document.querySelector("#price");
const modelSoldOut = document.querySelector("#model-sold-out");
const overQuantityFeedbackEl = document.querySelector(".over-quantity-feedback");
const overQuantityFeedbackMessage = $(".over-quantity-feedback-message")[0];

const variation0 = document.querySelectorAll(".variation-0")
const productItemStock = document.querySelectorAll(".product-item-stock")
const productItemPrice = document.querySelectorAll(".product-item-price")
const quantityAddedProducts = document.querySelectorAll(".quantityAddedProduct")


productQuantity.addEventListener('input', editQuantityValue);

handleVariation();

function editQuantityValue() {
	let quantityAddedProduct = 0;
	let maxQuantityProduct = parseInt(stockEl.textContent);

	if(indexVariationEl != -1) {
		quantityAddedProduct = parseInt(quantityAddedProducts[indexVariationEl] ? quantityAddedProducts[indexVariationEl].value : 0);
		maxQuantityProduct -= parseInt(quantityAddedProducts[indexVariationEl] ? quantityAddedProducts[indexVariationEl].value : 0);
	}
	
	submitBtn.disabled = maxQuantityProduct === 0;

	if((+productQuantity.value) >= maxQuantityProduct) {
		overQuantityFeedbackEl.style.display = "block";
		overQuantityFeedbackMessage.textContent = "You have reached the maximum quantity available for this item";
		if(+parseInt(stockEl.textContent) == 0) {
			overQuantityFeedbackMessage.textContent = "This variation is out of stock";
		}
	} else {
		overQuantityFeedbackEl.style.display = "none";
	}

	if (+productQuantity.value > maxQuantityProduct) {
		productQuantity.value = maxQuantityProduct;
	}
	if(+productQuantity.value <= 1) {
		productQuantity.value = 1;
	}
	
	plusBtn.disabled = +productQuantity.value >= maxQuantityProduct;
	minusBtn.disabled = +productQuantity.value <= 1;
}

function handleVariation() {
	variation0.forEach((item, index) => {
		item.addEventListener('change', () => {
			stockEl.innerHTML = productItemStock[index].value;
			priceEl.innerHTML = productItemPrice[index].value;
	
			submitBtn.disabled = productItemStock[index].value == 0;
			plusBtn.disabled = productItemStock[index].value == 0;
			productQuantity.disabled = productItemStock[index].value == 0;
	
			if(+productQuantity.value > +stockEl.textContent) {
				productQuantity.value = +stockEl.textContent;
				plusBtn.disabled = true;
			}

			indexVariationEl = getVariationValue();
			editQuantityValue();
	
			variationErrorEl.style.display = "none"; 
		})
	})

}

// Product Quantity
$('.quantity button').on('click', function (e) {
	e.preventDefault();
	var button = $(this);
	var oldValue = +productQuantity.value;
	if (button.hasClass('btn-plus')) {
		var newVal = parseInt(oldValue) + 1;
		minusBtn.removeAttribute("disabled");
		plusBtn.disabled = newVal == +productQuantity.max;
	} else {
		var newVal = parseInt(oldValue) - 1;
		plusBtn.removeAttribute("disabled");
		minusBtn.disabled = newVal == 1;
	}
	productQuantity.value = newVal;
	editQuantityValue();
});
