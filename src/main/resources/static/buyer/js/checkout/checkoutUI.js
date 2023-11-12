
const priceEls = document.querySelectorAll(".price-el");
const totalPriceEls = document.querySelectorAll(".total-price-el");
const quantityEls = document.querySelectorAll(".quantity-el")

const orderContentEls = document.querySelectorAll(".order-content")

const shopVoucherTotalValueEls = document.querySelectorAll(".shop-voucher-total-value");
const orderTotalValueEls = document.querySelectorAll(".order-total-value");

const platformVoucher = $("#platform-voucher")[0];
const subTotalPayment = $("#sub-total-payment")[0];
const totalPayment = $("#total-payment")[0];

$(document).ready(function() {
	getTotalOrderItemPrice();
    getTotalOrderPrice();	

    getSubTotalPayment();
})

function getTotalOrderItemPrice() {
    totalPriceEls.forEach((item, index) => {
		item.value = "$" + (priceEls[index].value * quantityEls[index].value).toFixed(2)
	})
}

function getTotalOrderPrice() {
    orderTotalValueEls.forEach((orderTotal, index) => {
        let totalOrderItemEls = $(orderContentEls[index]).find('.total-price-el');
        let tmp;
        $.each(totalOrderItemEls, (index, orderItemTotal) => {
            tmp = ""
            tmp += orderItemTotal.value.slice(1);
        })
        orderTotal.value = "$" + tmp; 
    })
}

function getSubTotalPayment() {
    let sum = 0;
    orderTotalValueEls.forEach((orderTotal, index) => {
        sum += (+orderTotal.value.slice(1));
    })
    subTotalPayment.value = "$" + sum;
}
