
var productQuantity = document.querySelector(".amount-product");
var plusBtn = document.querySelector(".btn-plus");
var minusBtn = document.querySelector(".btn-minus");
var addBtn = document.querySelector(".add-btn");

productQuantity.addEventListener('input', test);

function test(){
	if (+productQuantity.value == 1) {
		minusBtn.setAttribute("disabled", "true");
	} else {
		minusBtn.removeAttribute("disabled");
	}
	
	if (+productQuantity.value == +productQuantity.ariaValueMax) {
		plusBtn.setAttribute("disabled", "true");
	} else {
		plusBtn.removeAttribute("disabled");
	}
	
	console.log("ok")
}

plusBtn.addEventListener('click', () => {
	minusBtn.removeAttribute("disabled");
	if (+productQuantity.value == +productQuantity.ariaValueMax) {
		plusBtn.setAttribute("disabled", "true");
	} else {
		plusBtn.removeAttribute("disabled");
	}
})


minusBtn.addEventListener('click', () => {
	plusBtn.removeAttribute("disabled");
	if (+productQuantity.value == 1) {
		minusBtn.setAttribute("disabled", "true");
	} else {
		minusBtn.removeAttribute("disabled");
	}
})

