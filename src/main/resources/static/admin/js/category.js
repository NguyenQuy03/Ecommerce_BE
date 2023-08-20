const APICategoryUrl = "/api/manager/category"
const saveBtn = $("#saveBtn");
const formData = $("#form-submit");
const data = {};

saveBtn.click(function(e) {
    e.preventDefault();

    let isBasicFormValid = formData[0].checkValidity();
    if(!isBasicFormValid) return formData[0].classList.add("was-validated");

    getFormData();

    if($("#id")[0].value === '') {
        addCategory(data);
    } else {
        updateProduct(data);
    }
})

function getFormData() {
    data["code"] = $("#inputCode")[0].value
    data["thumbnail"] = $(".preview-category-thumbnail")[0].src
}

function addCategory(data) {
    $.ajax({
        url: APICategoryUrl,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        processData: false,
        cache: false,
        success: function() {
            window.location.href = "/manager/category?page=1&size=2&message=addSucceed";
        },
        error: function(e) {
            console.log("error" + e)
        }
    });
}

function showPreviewThumbnail(thumbnailFile) {
    var reader = new FileReader();
	var file = thumbnailFile.files[0];

	reader.onload = function(e) {
        $(".preview-category-thumbnail").attr('src', e.target.result);
	}

    if(file) {
	   reader.readAsDataURL(file);
    }
}