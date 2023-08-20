
const imageProduct = $("#imageProduct");
const rowAdderSpec = $("#row-adder-spec")
const specEl = $("#specification");

var reader = new FileReader();

let optionContentEl;
$(document).ready(function() {
	if($("#id")[0].value === '') {
        addSpecRow();
	}
    
    if(imageProduct[0]){
	    imageProduct[0].setAttribute("selected", "");
	    imageProduct[0].removeAttribute("required");
	}

})

    /*  ADD SPECIFICATIONS ROW */
rowAdderSpec.click(function (e) {
    e.preventDefault();
    addSpecRow();
});

function addSpecRow(){
    newSpecRow =
        '<div class="spec-el spec-row col-sm-10 d-flex fit-spec-content">' +
            '<input required type="text" maxlength="14" class="form-control" class="spec-key input-group-text">' +
            '<input required type="text" maxlength="24" class="form-control" class="spec-value form-control">' +
            '<button class="delete-spec-btn btn btn-danger" type="button">' +
                '<i class="bi bi-trash"></i>' +
            '</button>' +
        '</div>';

    $('#new-input-specs').append(newSpecRow);
}

specEl.on("click", ".delete-spec-btn", function () {
    $(this).parents(".spec-row").remove();
})

    /* ADD VARIATION CONTENT */
$(".enable-variation-btn").click(function(e) {
    addVariationContent();
    removeDetailContent();
    optionContentEl = $('.option-content')[0];
})

function addVariationContent() {
    const newRowAdd =
            '<div class="col-sm-5">' +
                '<span class="input-group-text mb-2">Variation 1</span>' +
                '<div>' +
                    '<div class="col-sm-12 d-flex spec-fit">' +
                        '<input type="text" maxlength="14" class="form-control variation-key" oninput="handleVariationKey(this)" placeholder="eg:colour, etc." required>' +
                    '</div></div></div>' +
            '<div class="col-sm-5">' +
                '<span class="input-group-text mb-2">Options</span>' +
                '<div class="option-content"></div></div>' +
            '<button class="btn remove-variation-btn" onclick="removeVariation(event)" style="font-size: 20px;"><i class="bi bi-x-lg"></i></button>'

    const variationListEl = '<div class="row mb-3 variation-list-content"><label class="col-sm-2 col-form-label">Variation List</label>' +
                            '<div class="tab-content pt-2 col-10">' +
                                '<div class="tab-pane fade pt-3 active show mt-0" id="all-product">' +
                                    '<div class="card"><table class="table table-bordered">' +
                                            '<thead><tr>' +
                                                '<th scope="col" class="variation-key-title" style="width: 155px;">Variation 1</th>' +
                                                '<th scope="col">Price</th>' +
                                                '<th scope="col">Stock</th></thead>' +
                                            '<tbody class="variation-list-body"></tbody></table></div></div></div></div>'

    $(".enable-variation-btn")[0].style.display = "none";
    $(".detail-container")[0].style.display = "none";
    $(".variation-content")[0].style.display = "flex";
    $(".variation-content").append(newRowAdd);
    $(".variation-list-container").append(variationListEl)
    
    addVariationListRow();
    addOptionRow();
}

function removeVariation(e) {
    e.preventDefault();
    $(".detail-container")[0].style.display = "block";
    $(".enable-variation-btn")[0].style.display = "block";

    $(".variation-content").empty();
    $(".variation-list-content").remove();
    addDetailContent();
}

function removeDetailContent() {
    $(".detail-content").remove();
}

function addDetailContent() {
    const detailContent = '<div class="detail-content">' +
                              '<div class="row mb-3">' +
                                  '<label for="price" class="col-sm-2 col-form-label">Price</label>' +
                                  '<div class="col-sm-10">' +
                                      '<input id="product-price" class="form-control" style="width: 30%; display: inline-block;" type="number"' +
                                             'min="0" max="9999"  step="any" required  id="price"/><span>$</span>' +
                                      '<div id="priceError" class="invalid-feedback">' +
                                          '<span> Please enter product\'s price! </span>' +
                                      '</div></div></div>' +
                              '<div class="row mb-3">' +
                                  '<label for="product-stock" class="col-sm-2 col-form-label">Stock</label>' +
                                  '<div class="col-sm-10">' +
                                      '<input id="product-stock" type="number"  style="width: 30%;" class="form-control"min="0" max="1000" required />' +
                                      '<div id="stockError" class="invalid-feedback">' +
                                          '<span> Please enter product\'s stock! </span>' +
                                      '</div></div></div></div>'

    $(".detail-container").append(detailContent);
}

    /* HANDLE VARIATION OPTION ROW */
function handleOptionEl(inputEl) {
    const variationOptions = document.querySelectorAll(".variation-option");
    const optionTitles = document.querySelectorAll(".option-title");

    variationOptions.forEach((option, index) => {
        if(option === inputEl) {
            if(variationOptions.length == 2 && !option.value) {
                option.parentElement.remove();
                optionTitles[index].closest('tr').remove();
                addVariationListRow();
                return;
            }
            
            if(!option.value && index != variationOptions.length - 1) {
                option.parentElement.remove();
                optionTitles[index].closest('tr').remove();
                return;
            }

            if (index === variationOptions.length - 1) {
                addOptionRow();
                if(variationOptions.length > 1) addVariationListRow();
            }

            $(".option-title")[index].textContent = option.value;
        }
    })
    
}

function addOptionRow(){
    newRowAdd =
        '<div class="col-sm-12 d-flex spec-fit mb-2">' +
            `<input type="text" id="option" class="form-control variation-option" oninput="handleOptionEl(this)" placeholder="eg:Red, etc.">` +
        '</div>';

    $('.option-content').append(newRowAdd);
}

    /* HANDLE VARIATION LIST OPTION ROW */
function addVariationListRow() {
    const newVariationListRow = '<tr>' +
                         '<td class="file-upload-variation">' +
                             '<div class="d-flex align-items-center flex-column">'+
                             `<span class="option-title"></span>` +
                             '</div>' +
                             `<img class="img-fluid preview-image-item variation-image"` +
                              `th:src="'data:image/png;base64,' ">` +
                             `<input id="variation-input-file" class="form-control" style="width: 100%;" type="file" accept="image/png, image/jpeg"` +
                                `onchange="handleImagePreview(this)" ></td>` +
                         '<td ><div class="d-flex align-center"><span class="p-2">$</span>' +
                                 `<input class="form-control variation-price" type="number" min="1" max="9999"  step="any" required/></div></td>` +
                         `<td><input type="number" class="form-control variation-stock" min="1" max="1000" required /></td>` +
            '</tr>'

    $(".variation-list-body").append(newVariationListRow);
}

    /* HANDLE VARIATION CONTENT */
function handleVariationKey(inputEl){
    if(!inputEl.value) {
        $(".variation-key-title")[0].textContent = "Variation 1";
    } else {
        $(".variation-key-title")[0].textContent = inputEl.value;
    }
}

    /*  Handle Image Preview */
function handleImagePreview(imageFile) {
    showPreviewImage(imageFile, imageFile.parentElement.getElementsByTagName('img')[0])
}

function showPreviewImage(imageFile, previewImageEl) {
	var file = imageFile.files[0];

	reader.onload = function(e) {
        if(typeof(previewImageEl) == 'string') {
            $("." + previewImageEl).attr('src', e.target.result);
        } else {
            $(previewImageEl).attr('src', e.target.result);
        }
	}

    if(file) {
	   reader.readAsDataURL(file);
    }
}
