const APICartUrl = "/api/buyer/cart"

const closeBtn = document.querySelector(".close-announce-btn");
const modalAnnounce = document.querySelector(".modal-announce");
const modalAccounceTitle = document.querySelector(".modal-accounce-title");

const buyerAgainBtns = document.querySelectorAll(".buyer-again-btn");
const orderItemIds = document.querySelectorAll(".order-item-id");

buyerAgainBtns.forEach((btn, i) => {
	btn.onclick = (e) => {
		e.preventDefault();
		buyAgain(orderItemIds[i].value)
	}
})

    // Buy-again
async function buyAgain(orderItemId) {
	try {
		const response = await fetch(APICartUrl + "/buy-again", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(orderItemId)
		});

		if (response.ok) {
			window.location.href = "/cart";
		} else if (response.status === 401 || response.status === 403 || response.status === 405) {
			window.location.href = "/login";
		} else if (response.status === 500) {
			let mesResponse = await response.text();
			console.log("Error message:", mesResponse);
		} else if(response.status === 404) {
			let responseText = await response.text();
            modalAccounceTitle.innerHTML = responseText;
			modalAccounceTitle.style.color = 'var(--danger)'
            modalAnnounce.classList.add("show");
            modalAnnounce.style.display = "flex";
		}
	} catch (error) {
		console.log("Error:", error);
	}
}

if (closeBtn) {
	closeBtn.onclick = () => {
		closeModalAnnounce();
	}
}

function closeModalAnnounce() {
	modalAnnounce.classList.remove("show");
	modalAnnounce.style.display = "none";
}