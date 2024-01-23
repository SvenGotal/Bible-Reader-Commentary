function redirectToUserForm() {
    window.location.href = "/submitForm";
}

function redirectToCommentForm() {
	window.location.href = "/submitComment";
}

document.addEventListener("DOMContentLoaded", function() {
    var redirectToUserFormButton = document.getElementById("redirectToUserForm");
    var redirectToCommentFormButton = document.getElementById("redirectToCommentForm");
    
    if (redirectToUserFormButton) {
        redirectToUserFormButton.addEventListener("click", redirectToUserForm);
    }
    
    if (redirectToCommentFormButton){
		redirectToCommentFormButton.addEventListener("click", redirectToCommentForm);
	}
});
