/*var btnSave = $("#saveBtn");
var APIsellerProduct = "/api/seller/product";

btnSave.click(function(e) {
	e.preventDefault();
	var id = $('#id').val();
	var data = {};
	var formData = $('#formSubmit').serializeArray();
	$.each(formData, function(i, v) {
		data["" + v.name + ""] = v.value;
	});

	function checkData() {
		var result = true;
		for (var i = 0; i < formData.length - 1; i++) {
			if (!formData[i].value) {
				result = false;
			}
		}
		return result;
	}

	if (checkData()) {
		if (!id) {
			addBook(data);
		} else {
			updateBook(data);
		}
	} else {
		alert("Vui lòng điền đầy đủ các trường");
	}
})

function addBook(data) {
	$.ajax({
		url: APIsellerProduct,
		type: "POST",
		contentType: 'application/json',
		data: JSON.stringify(data),
		dataType: "json",
		success: function() {
			console.log("success");
		},
		error: function() {
			console.log("failue");
		}
	})
}

function updateBook(data) {
	$.ajax({
		url: APIsellerProduct,
		type: 'PUT',
		contentType: 'application/json',
		data: JSON.stringify(data),
		dataType: 'json',
		success: function() {
			window.location.href =
				"${PublisherBookURL}?type=list&page=1&maxPageItem=2&alertType=success&alertMessage=update_success";
		},
		error: function() {
			window.location.href =
				"${PublisherBookURL}?type=list&page=1&maxPageItem=2&alertType=danger&alertMessage=update_error";
		}
	})
}*/

var btn = $("#saveBtn");
btn.click(function (e) {

	var APIsellerProduct = "/api/seller/product";
	var formData = new FormData();

	var fileUpload = $("#formFile")[0].files[0];
	
	var reader = new FileReader();
	var fileByteArray = [];
	reader.readAsArrayBuffer(fileUpload);
	reader.onloadend = function (evt) {
		if (evt.target.readyState == FileReader.DONE) {
			var arrayBuffer = evt.target.result,
			array = new Uint8Array(arrayBuffer);
			for (var i = 0; i < array.length; i++) {
				fileByteArray.push(array[i]);
			}
		}
	}

	formData.append("file", fileByteArray);

	$.ajax({
		url: APIsellerProduct,
		type: "POST",
		data: formData,
		processData: false,
		contentType: false,
		success: function (data) {
			console.log(data);
		}, error: function (errData) {
			console.log("error:");
			console.log(errData);
		}
	});
})