
const APIProductUrl = "/api/seller/product";
const saveBtn = $("#saveBtn");
const descriptionEl = $("#description");
const categoryEl = $("#category")[0];
const productIdEl = $("#id")[0];

const loadingOverlay = $('#loading-overlay')[0];
const modalAnnounce = $("#modal-announce")[0];
const modalAnnounceContent = $(".modal-announce-content")[0];
const modalAccounceTitle = $(".modal-accounce-title")[0];


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

    console.log(data)
    if(!productIdEl.value) {
        pushProduct(data, "POST");
    } else {
        pushProduct(data, "PUT");
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

async function pushProduct(data, method) {
    try {
        loadingOverlay.style.display = "block";
        const response = await fetch(APIProductUrl, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            let responseText = await response.text();
            modalAccounceTitle.innerHTML = responseText;
            modalAnnounceContent.classList.add("text-success");
            modalAnnounce.classList.add("show");
            modalAnnounce.style.display = "flex";
            
            setTimeout(function() {
                window.location.href = "/seller/product/list/all?page=1&size=2";
            }, 1800);
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