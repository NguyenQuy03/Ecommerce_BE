var checkAll = document.getElementById('checkAll');

checkAll.addEventListener('click', event => {
	if (event.target.checked) {
		$('tbody input[type=checkbox]').prop('checked', true);
		$("#btnDelete").removeClass("disabled");
	} else {
		$('tbody input[type=checkbox]').prop('checked', false);
		$("#btnDelete").addClass("disabled");
	}
});

var checkBoxes = $('tbody input[type=checkbox]');

function allCheckBoxChecked() {
	var result = true;
	for (var i = 0; i < checkBoxes.length; i++) {
		if (checkBoxes[i].checked == false) {
			result = false;
		}
	}
	return result;
}

function leastOneCheckBoxChecked() {
	var result = false;
	for (var i = 0; i < checkBoxes.length; i++) {
		if (checkBoxes[i].checked == true) {
			result = true;
		}
	}
	return result;
}

if (checkBoxes.length >= 2) {
	checkBoxes.map((index, item) => {
		item.addEventListener("click", e => {
			if (allCheckBoxChecked()) {
				$("#checkAll").prop('checked', true);
			} else if (leastOneCheckBoxChecked()) {
				$("#btnDelete").removeClass("disabled");
				$("#checkAll").prop('checked', false);
			} else {
				$("#btnDelete").addClass("disabled");
				$("#checkAll").prop('checked', false);
			}
		})
	})
} else {
	checkBoxes.map((index, item) => {
		item.addEventListener("click", e => {
			if (e.target.checked) {
				$("#checkAll").prop('checked', true);
				$("#btnDelete").removeClass("disabled");
			} else {
				$("#checkAll").prop('checked', false);
				$("#btnDelete").addClass("disabled");
			}
		})
	})
}

$("#btnDelete").click(function() {
	if (!confirm("Do you want to delete")) {
		return false;
	} else {
		//var data = {};
		var ids = [];
		$('tbody input[type=checkbox]:checked').map((index, item) => {
			ids.push(item.id);
		})
		//data["ids"] = ids
		deleteBook(ids);
	}

});

function deleteBook(data) {
	$.ajax({
		url: "/api/seller/product",
		type: "DELETE",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = "/seller/product/list/all?page=1&size=2";
		},
		error: function(e) {
			console.log("error")
		}
	})
}