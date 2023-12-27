
const saveBtn = $("#saveBtn");
const descriptionEl = $("#description");
const categoryEl = $("#category")[0];
const productIdEl = $("#id")[0];

const basicForm = $("#basic-form");
const salesForm = $("#sales-form");
const data = {};

const APIProductUrl = "/api/v1/seller/product";
const timeOutDefault = 1800;
const redirectUrlDefault = "/seller/product/list/all?page=1&size=2";

saveBtn.click(function(e) {
    e.preventDefault();
    
    let isBasicFormValid = basicForm[0].checkValidity();
    if(!isBasicFormValid) return basicForm[0].classList.add("was-validated");
    
    let isSalesFormValid = salesForm[0].checkValidity();
    if(!isSalesFormValid) return salesForm[0].classList.add("was-validated");
    
    getBaseFormData();
    getSalesFormData();

    console.log(data)
    if(!productIdEl.value) {
        push(data, "POST", APIProductUrl, redirectUrlDefault);
    } else {
        push(data, "PUT", APIProductUrl, redirectUrlDefault);
    }
})

var descriptionContent;
$(document).ready(function() {
    ClassicEditor
        .create(descriptionEl[0] )
        .then( editor => {
            descriptionContent = editor;
        })
})


function getBaseFormData() {
    const formData = basicForm.serializeArray();
    const specEls = document.querySelectorAll(".spec-el")

    var specData = {};
    specEls.forEach(specEl => {
        specData[specEl.children[0].value] = specEl.children[1].value;
    })
    data["specification"] = specData;

    $.each(formData, function (i, v) {
        data[v.name] = v.value ? v.value : null;
    });

    data["category"] = {code : categoryEl.value};

    data["description"] = descriptionContent.getData();
    data["image"] = $(".preview-product-image")[0].src;
}

function getSalesFormData() {
    var variationKeys = document.querySelectorAll(".variation-key")
    var variationOptions = document.querySelectorAll(".variation-option")
    var priceEls = document.querySelectorAll(".variation-price");
    var stockEls = document.querySelectorAll(".variation-stock");
    var imageEls = document.querySelectorAll(".variation-image");
    var productItemIds =  document.querySelectorAll(".product-item-id");
    
    var productItems = [];

    if(variationKeys.length == 0) {
        var productItem = {};
        productItem["price"] = $("#product-price")[0].value
        productItem["stock"] = $("#product-stock")[0].value

        productItems.push(productItem);
    } else {
        variationKeys.forEach(key => {
            variationOptions.forEach((option, index) => {
                if(index == variationOptions.length - 1) return;
    
                var productItem = {};
                productItem["id"] = productItemIds[index] ? productItemIds[index].value : null;
                productItem["price"] = priceEls[index].value;
                productItem["stock"] = stockEls[index].value;
                productItem["image"] = imageEls[index].src;
                productItem["variationName"] = key.value.trim();
                productItem["variationValue"] = option.value.trim();

                if(productIdEl.value) {
                    productItem["productId"] = productIdEl.value;
                }
    
                productItems.push(productItem);
            })
    
        })
    }

    data["productItems"] = productItems;
}