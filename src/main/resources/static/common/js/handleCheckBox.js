const checkAll = document.getElementById('checkAll');
const checkBoxes = $('tbody input[type=checkbox]');

checkAll.addEventListener('click', event => {
	if (event.target.checked && checkBoxes.length > 0) {
		$('tbody input[type=checkbox]').prop('checked', true);
		$(".btnDelete").removeClass("disabled");
	} else {
		$('tbody input[type=checkbox]').prop('checked', false);
		$(".btnDelete").addClass("disabled");
	}
});

function allCheckBoxChecked() {
	for (let checkbox of checkBoxes) {
		if (checkbox.checked == false) {
			return false;
		}
	}
	return true;
}

function leastOneCheckBoxChecked() {
	for (let checkbox of checkBoxes) {
		if (checkbox.checked == true) {
			return true;
		}
	}
	return false;
}

if (checkBoxes.length >= 2) {
	checkBoxes.map((index, item) => {
		item.addEventListener("click", e => {
			if (allCheckBoxChecked()) {
				$("#checkAll").prop('checked', true);
			} else if (leastOneCheckBoxChecked()) {
				$(".btnDelete").removeClass("disabled");
				$("#checkAll").prop('checked', false);
			} else {
				$(".btnDelete").addClass("disabled");
				$("#checkAll").prop('checked', false);
			}
		})
	})
} else {
	checkBoxes.map((index, item) => {
		item.addEventListener("click", e => {
			if (e.target.checked) {
				$("#checkAll").prop('checked', true);
				$(".btnDelete").removeClass("disabled");
			} else {
				$("#checkAll").prop('checked', false);
				$(".btnDelete").addClass("disabled");
			}
		})
	})
}