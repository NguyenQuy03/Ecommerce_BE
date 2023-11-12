
const restoreBtn = $(".restore-btn");

const defaultPageSize = 2;

$(".btnDelete.force").click(function() {
	var ids = [];
	$('tbody input[type=checkbox]:checked').map((index, item) => {
		ids.push(item.id);
	})
	forceDeleteProduct(ids);

});

$(".btnDelete").click(function() {
	var ids = [];
	$('tbody input[type=checkbox]:checked').map((index, item) => {
		ids.push(item.id);
	})
	deleteProduct(ids);

});

restoreBtn.click(function() {
	restoreProduct(this.id);
});

function deleteProduct(data) {
	$.ajax({
		url: "/api/seller/product",
		type: "DELETE",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = `/seller/product/list/all?page=1&size=${defaultPageSize}`;
		},
		error: function(e) {
			console.log("error")
		}
	})
}

function forceDeleteProduct(data) {
	$.ajax({
		url: "/api/seller/product/forceDelete",
		type: "DELETE",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = `/seller/product/trashbin?page=1&size=${defaultPageSize}`;
		},
		error: function(e) {
			console.log("error")
		}
	})
}

function restoreProduct(data) {
	$.ajax({
		url: "/api/seller/product/restore",
		type: "POST",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function() {
			window.location.href = `/seller/product/list/all?page=1&size=2${defaultPageSize}`;
		},
		error: function(e) {
			console.log("error" + e)
		}
	})
}

var alertElement = $(".alert");
if (alertElement) {
	setTimeout(() => {
		alertElement.remove();
	}, 3000);
}
