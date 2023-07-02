
const submitBtn = $(".submit-btn")

submitBtn.click(function(e) {
    e.preventDefault()
	let data = {};
	let userName = document.getElementById("userName").value;
	let password = document.getElementById("password").value;

	data["userName"] = userName
	data["password"] = password

	authenticate(data);
});

function authenticate(data) {
	$.ajax({
		url: "/api/auth/login",
		type: "POST",
		data: JSON.stringify(data),
		contentType: "application/json",
		success: function(res) {
            $.ajax({
                url: "/home",
                type: "GET",
                headers: {
                    "Authorization": "Bearer " + res
                },
                success: function(res) {
                    $("main").html(res);
                    history.pushState(null, null, "/home");
                },
                error: function(xhr) {
                    console.log(xhr.responseText);
                }
            });
        },
		error: function(e) {
			console.log(e)
		}
	})
}
