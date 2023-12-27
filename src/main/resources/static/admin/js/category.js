
const APICategoryUrl = "/api/v1/manager/category"
const redirectUrlDefault = "/manager/category/list/all?page=1&size=2";

const saveBtn = $("#saveBtn");
const formData = $("#form-submit");
const data = {};

saveBtn.click(function(e) {
    e.preventDefault();

    let isBasicFormValid = formData[0].checkValidity();
    if(!isBasicFormValid) return formData[0].classList.add("was-validated");

    getFormData();

    if($("#id")[0].value === '') {
        push(data, "POST", APICategoryUrl, redirectUrlDefault);
    } else {
        data["id"] = $("#id")[0].value;
        push(data, "PUT", APICategoryUrl, redirectUrlDefault);
    }
})

function getFormData() {
    data["code"] = $("#inputCode")[0].value
    data["thumbnail"] = $(".preview-category-thumbnail")[0].src
}

function showPreviewThumbnail(thumbnailFile, element) {
    var reader = new FileReader();
	var file = thumbnailFile.files[0];

	reader.onload = function(e) {
        $("." + element).attr('src', e.target.result);
	}

    if(file) {
	   reader.readAsDataURL(file);
    }
}