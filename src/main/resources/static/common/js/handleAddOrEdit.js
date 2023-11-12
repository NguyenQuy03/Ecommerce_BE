
const loadingOverlay = $('#loading-overlay')[0];

const modalAnnounce = $("#modal-announce")[0];
const modalAnnounceContent = $(".modal-announce-content")[0];
const modalAccounceTitle = $(".modal-accounce-title")[0];
const modalAnnounceCloseBtn = $(".modal-accounce-close-btn")[0];

const timeOutDefault = 1800;

function push(data, method, APIUrl, redirectUrl) {
    loadingOverlay.style.display = "block";

    fetch(APIUrl, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            response.text().then(responseText => {
                handleAccouncement(responseText, response.status);
                redirect(redirectUrl);
                resolve();
            });
        }

        return Promise.reject(response);
    })
    .catch(response => {
        response.text().then(responseText => {
            handleAccouncement(responseText, response.status)
        });
    })
    .finally(() => {
        loadingOverlay.style.display = "none";
    });
}


function handleAccouncement(responseText, responseStatus) {
    modalAccounceTitle.innerHTML = responseText;
    modalAnnounce.classList.add("show");
    modalAnnounce.style.display = "flex";
    
    if(modalAnnounceContent) {
        if(responseStatus == 200) {
            modalAnnounceContent.classList.add("text-success");
        } else {
            modalAnnounceContent.classList.add("text-danger");
            modalAnnounceCloseBtn.style.display = "block";
        }
    }
}

function redirect(redirectUrl) {
    setTimeout(function() {
        window.location.href = redirectUrl;
    }, timeOutDefault);
}

if(modalAnnounceCloseBtn) {
    modalAnnounceCloseBtn.onclick = function () {       
        modalAnnounce.style.display = "none";
    }
}