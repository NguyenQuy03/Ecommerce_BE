
// PAGINATION
var totalPage = $("#totalPage").val();
var currPage = $("#currPage").val();
var intPage = +currPage;
var size = $("#size").val();
$(function () {
	window.pagObj = $('#pagination').twbsPagination({
		totalPages: totalPage,
		startPage: intPage,
		visiblePages: totalPage >= 10 ? 10 : totalPage,
		onPageClick: function (event, page) {
			if (currPage != page) {
				$("#currPage").val(page);
				$("#size").val(size);
				$("#formSubmit").submit();
			}
		}
	})
});