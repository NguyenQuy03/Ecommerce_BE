
$(document).ready(function() {
	$("#imageFile").change(function() {
		showPreviewImage(this);
	})
})

function showPreviewImage(imageFile) {
	var file = imageFile.files[0];
	var reader = new FileReader();

	reader.onload = function(e) {
		$("#previewImage").attr('src', e.target.result);
	}

	reader.readAsDataURL(file);
}
