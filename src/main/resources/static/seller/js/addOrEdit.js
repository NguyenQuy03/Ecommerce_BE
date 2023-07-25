
const saveBtn = $("#saveBtn");
const APIProductUrl = "/api/seller/product";
const form = $("#formSubmit");
const data = {};

$(document).ready(function() {
	$("#imageBase64").change(function() {
		showPreviewImage(this);
	})
	if($("#id")[0].value === '') {
        addSpecRow();
	} else {
	    $("#imageBase64")[0].setAttribute("selected", "");
	    $("#imageBase64")[0].removeAttribute("required");
	}

    ClassicEditor
            .create( document.querySelector( '#details') )
            .catch( error => {
                console.error( error );
            } );
})

function showPreviewImage(imageFile) {
	var file = imageFile.files[0];
	var reader = new FileReader();

	reader.onload = function(e) {
		$(".previewImage").attr('src', e.target.result);
		data["imageBase64"] = e.target.result;
	}

    if(file) {
	   reader.readAsDataURL(file);
    }
}

$("#row-adder").click(function (e) {
    e.preventDefault();
    addSpecRow();
});

function addSpecRow(){
    newRowAdd =
        '<div class="spec-el spec-row col-sm-10 d-flex spec-fit">' +
            '<input required type="text" aria-label="Key" class="spec-key input-group-text">' +
            '<input required type="text" aria-label="Value" class="spec-value form-control">' +
            '<button class="delete-spec-btn btn btn-danger" type="button">' +
                '<i class="bi bi-trash"></i>' +
            '</button>' +
        '</div>';

    $('#new-input').append(newRowAdd);
}

$("body").on("click", ".delete-spec-btn", function () {
    $(this).parents(".spec-row").remove();
})


saveBtn.click(function(e) {
    e.preventDefault();
    const formData = form.serializeArray();
    const specEls = document.querySelectorAll(".spec-el")

    const specData = [];
    specEls.forEach(specEl => {
        let tmp = {};
        tmp["k"] = specEl.children[0].value;
        tmp["v"] = specEl.children[1].value;
        specData.push(tmp);
    })
    data["specifications"] = specData;

    $.each(formData, function (i, v) {
        data[v.name] = v.value;
    });

    let isValidForm = form[0].checkValidity();

    if(isValidForm && $("#id")[0].value === '') {
        addProduct(data);
    } else if (isValidForm) {
        updateProduct(data);
    } else {
        form[0].classList.add("was-validated");
    }

})


function addProduct(data) {
    $.ajax({
        url: APIProductUrl,
        type: "POST",
        contentType: "application/json",
        processData: false,
        cache: false,
        data: JSON.stringify(data),
        success: function() {
            window.location.href = "/seller/product/list/all?page=1&size=2&message=addSucceed";
        },
        error: function(e) {
            console.log("error" + e)
        }
    });
}

function updateProduct(data) {
    $.ajax({
        url: APIProductUrl,
        type: "PUT",
        contentType: "application/json",
        processData: false,
        cache: false,
        data: JSON.stringify(data),
        success: function() {
            window.location.href = "/seller/product/list/all?page=1&size=2&message=addSucceed";
        },
        error: function(e) {
            console.log("error" + e)
        }
    });
}



