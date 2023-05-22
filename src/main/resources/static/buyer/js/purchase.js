
const totalPriceEls = document.querySelectorAll("#total-price-el");
const priceEls = document.querySelectorAll("#price-el");
const quantityEls = document.querySelectorAll("#quantity-el")
const closeBtn = document.querySelector(".close-btn");
const modalAnnounce = document.querySelector(".modal-announce");

const formatter = new Intl.NumberFormat('en-US', {
	style: 'currency',
	currency: 'USD',
});

$(document).ready(function() {
	totalPriceEls.forEach((item, i) => {
		item.value = formatter.format(priceEls[i].value * (quantityEls[i].value));
	})
})

if (closeBtn) {
	closeBtn.onclick = () => {
		modalAnnounce.classList.remove("show");
		modalAnnounce.style.display = "none";
	}
	
}