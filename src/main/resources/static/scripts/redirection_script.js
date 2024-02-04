function redirectToUserForm() {
    window.location.href = "/submitForm";
}

function redirectToCommentForm() {
	window.location.href = "/submitComment";
}

function redirectToIndex(){
	window.location.href= "/";
}


document.addEventListener("DOMContentLoaded", function() {
    var redirectToUserFormButton = document.getElementById("redirectToUserForm");
    var redirectToCommentFormButton = document.getElementById("redirectToCommentForm");
    var redirectToIndexButton = document.getElementById("redirectToIndex");
    
    if (redirectToUserFormButton) {
        redirectToUserFormButton.addEventListener("click", redirectToUserForm);
    }
    
    if (redirectToCommentFormButton){
		redirectToCommentFormButton.addEventListener("click", redirectToCommentForm);
	}
	
	if(redirectToIndexButton){
		redirectToIndexButton.addEventListener("click", redirectToIndex);
	}
});
