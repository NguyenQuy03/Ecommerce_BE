const APICategoryUrl = "/api/manager/category"
const saveBtn = $("#saveBtn");
const formData = $("#form-submit");
const data = {};

const loadingOverlay = $("#loading-overlay")[0];

saveBtn.click(function(e) {
    e.preventDefault();

    let isBasicFormValid = formData[0].checkValidity();
    if(!isBasicFormValid) return formData[0].classList.add("was-validated");

    getFormData();

    if($("#id")[0].value === '') {
        addCategory(data, "POST");
    }
})

function getFormData() {
    data["code"] = $("#inputCode")[0].value
    data["thumbnail"] = $(".preview-category-thumbnail")[0].src
}

async function addCategory(data, method) {
    try {
        loadingOverlay.style.display = "block";
        const response = await fetch(APICategoryUrl, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            window.location.href = "/manager/category?page=1&size=2";
        } else if (response.status === 401 || response.status === 403 || response.status === 405) {
            console.log(response);
        } else if (response.status === 500) {
            let mesResponse = await response.text();
            console.log("Error message:", mesResponse);
        }
    } catch (error) {
        console.log("Error:", error);
    } finally {
        loadingOverlay.style.display = "none";
    }
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