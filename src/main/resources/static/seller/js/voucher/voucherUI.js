
const typeEl = $("#type")[0];
const typeUnitEl = $("#type-unit")[0];
const valueEl = $("#value")[0];
const productRowEl = $("#product-row")[0];

const minOrderTotalPriceEl = $("#minOrderTotalPrice")[0];

const voucherCodeEl = $("#code")[0];
const voucherCodeInformEl = $("#voucher-code-inform")[0];

const usageQuantity = $("#usageQuantity")[0];
const usageQuantityFeedBack = $("#usageQuantity-feedback")[0];
const maxUsagePerUser = $("#maxUsagePerUser")[0];
const maxUsagePerUserFeedBack = $("#maxUsagePerUser-feedback")[0];

const startDate = $("#start-date")[0]
const endDate = $("#end-date")[0]

const productScopeEls = $("#productScope .product-scope-item");

const typeFeedback = $("#type-feedback")[0];
const minOrderTotalPriceFeedback = $("#minOrderTotalPrice-feedback")[0];

const typeWarning = $("#type-warning")[0];

const avgPercenWarning = 69;
const maxFixAmountValue = 100000;

const maxUsagePerUserQuantity = 5;
const maxUsageQuantity = 200000

const valueAsString = $("#string-value")[0].value;

let username = voucherCodeInformEl.innerText.split(":")[1];
const optionItems = $(".option-item");

$(document).ready(function() {
	let unit = valueAsString.charAt(valueAsString.length - 1)
    if(unit == "%") {
        $.each( optionItems, function( index, item ) {
            if(item.value == "PERCENTAGE") {
                item.selected = "selected";
                typeUnitEl.innerText = "%"
            }
        });
    } else if(!unit) {
        optionItems[0].selected = "selected"
    }

    if(idEl.value) {
        voucherCodeEl.value = voucherCodeEl.value.substring(username.length);
        innerTextVoucherInform()
    }
})

voucherCodeEl.oninput = () => {
    innerTextVoucherInform()
}

typeEl.oninput = function() {
    let typeElValue = this.value;

    if(typeElValue === 'PERCENTAGE') {
        typeUnitEl.innerText = '%';
        valueEl.value = "";
        valueEl.min = 1;
        valueEl.max = 99;
    } else if(typeElValue === 'FIX_AMOUNT') {
        valueEl.value = "";
        typeUnitEl.innerText = '$';
        valueEl.max = maxFixAmountValue;
    }
}

maxUsagePerUser.oninput = function() {
    if(+this.value > maxUsagePerUserQuantity) {
        maxUsagePerUserFeedBack.innerText = "Please note that the max distribution per buyer cannot be more than " + maxUsagePerUserQuantity;
        maxUsagePerUserFeedBack.style.display = "block"
        return;
    } else {
        maxUsagePerUserFeedBack.style.display = "none"
    }
    
    checkMaxUsagePerUserValue(this.value, usageQuantity.value);
}

usageQuantity.oninput = function() {
    this.value = this.value.slice(0, 6);
    handleFeedBack(+this.value <= 0 || +this.value > maxUsageQuantity, usageQuantityFeedBack, "Please enter a value between 1 and " + maxUsageQuantity)

    checkMaxUsagePerUserValue(maxUsagePerUser.value, this.value);
}

function checkMaxUsagePerUserValue(maxUsagePerUser, maxQuantity) {
    handleFeedBack(maxQuantity && +maxUsagePerUser >= +maxQuantity, maxUsagePerUserFeedBack, "Max distribution per buyer cannot exceed usage quantity")
}

function innerTextVoucherInform() {
    voucherCodeInformEl.innerText = "Your complete voucher code is: " + username + voucherCodeEl.value.toUpperCase();
}

function checkFixAmountValue(value) {
    handleFeedBack(value > minOrderTotalPriceEl.value && typeEl.value != "PERCENTAGE" && minOrderTotalPriceEl.value,
        minOrderTotalPriceFeedback, "Voucher discount amount cannot exceed min. spend required"
    )
}

minOrderTotalPriceEl.oninput = function() {
    if(+this.value >= maxFixAmountValue) {
        return this.value = maxFixAmountValue;
    };
    checkFixAmountValue(+valueEl.value);
}

function checkPercentageValue(value) {
    handleFeedBack(value < 1 || value > 99, typeFeedback, "Please enter a value between 1 and 99")

    if(value > avgPercenWarning) {
        typeWarning.style.display = "block"
    } else {
        typeWarning.style.display = "none"
    }
}

function handleFeedBack(conditional, element, message) {
    if(conditional) {
        element.innerText = message;
        element.style.display = "block"
    } else {
        element.style.display = "none"
    }
}

valueEl.oninput = function() {
    let typeElValue = typeEl.value;
    let value = this.value;

    if(typeElValue === "PERCENTAGE") {
        if(+value.length >= 3) {
            valueEl.value = value.slice(0, 2);
            return;
        };
        checkPercentageValue(+value);
    } else if(typeElValue === "FIX_AMOUNT") {
        if(+value >= maxFixAmountValue) {
            valueEl.value = maxFixAmountValue;
        };

        checkFixAmountValue(+value);
    }
}

function setMaxDate() {
    startDate.max = endDate.value;
}

function setMinDate() {
    endDate.min = startDate.value;
}
