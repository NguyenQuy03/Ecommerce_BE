
const totalPriceEls = document.querySelectorAll("#total-price-el");
const priceEls = document.querySelectorAll("#price-el");
const quantityEls = document.querySelectorAll("#quantity-el")


const formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});


function getTotalOrderPrice() {
	totalPriceEls.forEach((item, i) => {
		item.value = formatter.format(priceEls[i].value * (quantityEls[i].value));
	})

}

getTotalOrderPrice()