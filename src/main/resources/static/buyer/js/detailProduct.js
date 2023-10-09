const data = {};
const APIProductUrl = "/api/buyer/cart";

const modalAnnounce = document.querySelector(".modal-announce");
const modalAccounceTitle = document.querySelector(".modal-accounce-title");
const modalIcon = document.querySelector(".modal-icon");

const submitForm = document.querySelector("#submit-form");
const submitBtn = document.querySelector("#submit-btn");
const variationErrorEl = document.querySelector(".variation-error");

const variationOption0 = document.querySelectorAll(".variation-option-0")
const productItemIds = document.querySelectorAll(".product-item-id")

let indexVariationEl = -1;
if(!$(".variation-content")[0]) {
    indexVariationEl = 0;
}

submitBtn.addEventListener("click", (e) => {
    e.preventDefault();
    data["productId"] = $("#id")[0].value;
    data["quantity"] = $("#quantity")[0].value;

    indexVariationEl = getVariationValue();
    if(indexVariationEl === -1) {
        variationErrorEl.style.display = "block"; 
        return;
    }

    data["id"] = productItemIds[indexVariationEl].value;

    addProduct(data);

})

function getVariationValue() {
    variationOption0.forEach((item, i) => {
        if(item.checked) {
            indexVariationEl = i;
        }
    })
    return indexVariationEl;
}

async function addProduct(data) {
    try {
        const response = await fetch(APIProductUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });
        let responseText = await response.text();

        if (response.ok) {
            displayModal(modalAnnounce, responseText, "success");
            reloadPage();
        } else if (response.status === 401 || response.status === 403 || response.status === 405) {
            window.location.href = "/login";
        } else if (response.status === 500) {
            displayModal(modalAnnounce, "Error: " + responseText, "error");
            reloadPage();
        }
    } catch (error) {
        let errorMes = await error.message;        
        displayModal(modalAnnounce, "Error: " + errorMes, "error");
        reloadPage();
    }
}

function displayModal(modal, message, status) {
    modalAccounceTitle.innerHTML = message;

    if(status == "success") {
        modalIcon.classList.add("modal-icon_success");
    } else if(status == "error") {
        modalIcon.classList.add("modal-icon_error");
    }

    modal.classList.add("show");
    modal.style.display = "flex";
}

function reloadPage() {
    setTimeout(function() {
        location.reload();
    }, 1500);
}