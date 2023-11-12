
// PAGINATION
const totalPage = $("#totalPage").val();
const currPage = $("#currPage").val();
const intPage = +currPage || 1;
const size = $("#size").val();
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