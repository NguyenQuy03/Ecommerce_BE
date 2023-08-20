
const APIProductUrl = "/api/seller/product";
const saveBtn = $("#saveBtn");
const descriptionEl = $("#description");

const basicForm = $("#basic-form");
const salesForm = $("#sales-form");
const data = {};

saveBtn.click(function(e) {
    e.preventDefault();

    
    let isBasicFormValid = basicForm[0].checkValidity();
    if(!isBasicFormValid) return basicForm[0].classList.add("was-validated");
    
    let isSalesFormValid = salesForm[0].checkValidity();
    if(!isSalesFormValid) return salesForm[0].classList.add("was-validated");
    
    getBaseFormData();
    getSalesFormData();

   if($("#id")[0].value === '') {
       addProduct(data);
   } else {
       updateProduct(data);
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

    var specData = [];
    specEls.forEach(specEl => {
        let tmp = {};
        tmp["k"] = specEl.children[0].value;
        tmp["v"] = specEl.children[1].value;
        specData.push(tmp);
    })
    data["specification"] = specData;

    $.each(formData, function (i, v) {
        data[v.name] = v.value;
    });


    data["description"] = descriptionContent.getData();
    data["image"] = $(".preview-product-image")[0].src;
}

function getSalesFormData() {
    var variationKeys = document.querySelectorAll(".variation-key")
    var variationOptions = document.querySelectorAll(".variation-option")
    var priceEls = document.querySelectorAll(".variation-price");
    var stockEls = document.querySelectorAll(".variation-stock");
    var imageEls = document.querySelectorAll(".variation-image");

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
                productItem["price"] = priceEls[index].value;
                productItem["stock"] = stockEls[index].value;
                productItem["image"] = imageEls[index].src;
                productItem["variationKey"] = key.value;
                productItem["variationValue"] = option.value;
    
                productItems.push(productItem);
            })
    
        })
    }

    data["productItemsData"] = productItems;
}


function addProduct(data) {
    $.ajax({
        url: APIProductUrl,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        processData: false,
        cache: false,
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



