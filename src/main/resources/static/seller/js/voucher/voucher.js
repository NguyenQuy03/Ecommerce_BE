
const submitBtn = $("#submit-btn")[0]

const basicForm = $("#basic-form")
const rewardSettingForm = $("#reward-setting-form")

const data = {};

let APIVoucherUrl = "/api/v1/seller/voucher";
let redirectUrlDefault = "/seller/voucher/list/all?page=1&size=2";

const idEl = $("#id")[0] || null

const codeFeedback = $("#code-feedback")[0];

submitBtn.onclick = function (e) {
    e.preventDefault();

    let isBasicFormValid = basicForm[0].checkValidity();
    if(!isBasicFormValid) return basicForm[0].classList.add("was-validated");
    
    let isReewardSettingFormValid = rewardSettingForm[0].checkValidity();
    if(!isReewardSettingFormValid) return rewardSettingForm[0].classList.add("was-validated");

    getBasicFormData();
    getRewardSettingFormData();

    data["voucherScope"] = "ALL";
    data["code"] = (username + data["code"]).substring(1).toLocaleUpperCase();

    data["startDate"] = formatDateTime(new Date(startDate.value));
    data["endDate"] = formatDateTime(new Date(endDate.value));

    console.log(data);
    if(!idEl.value) {
        push(data, "POST", APIVoucherUrl, redirectUrlDefault);
    } else {
        push(data, "PUT", APIVoucherUrl, redirectUrlDefault)
    }

}

function getBasicFormData() {
    const formData = basicForm.serializeArray();

    $.each(formData, function (i, v) {
        data[v.name] = v.value ? v.value : null;
    });

}

function getRewardSettingFormData() {
    const formData = rewardSettingForm.serializeArray();
    
    $.each(formData, function (i, v) {
        data[v.name] = v.value ? v.value : null;
    });

}

function formatDateTime(date) {
    date = new Date(+date);
    date.setTime(date.getTime() - (date.getTimezoneOffset() * 60000));
    let dateAsString =  date.toISOString().substr(0, 19);
    return dateAsString;
}