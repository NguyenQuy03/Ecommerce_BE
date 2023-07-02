
const alertElement = document.querySelector(".alert");

if (alertElement) {
	setTimeout(() => {
		alertElement.remove();
	}, 3000);
}
