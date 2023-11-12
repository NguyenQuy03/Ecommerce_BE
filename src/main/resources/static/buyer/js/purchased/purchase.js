const APICartUrl = "/api/buyer/cart"
const redirectUrlDefault = "/cart"

const closeBtn = document.querySelector(".close-announce-btn");
const modalAnnounce = document.querySelector(".modal-announce");
const modalAccounceTitle = document.querySelector(".modal-accounce-title");

const buyerAgainBtns = document.querySelectorAll(".buyer-again-btn");
const orderItemIds = document.querySelectorAll(".order-item-id");

buyerAgainBtns.forEach((btn, i) => {
	btn.onclick = (e) => {
		e.preventDefault();

		push(orderItemIds[i].value, "POST", APICartUrl, redirectUrlDefault);
	}
})