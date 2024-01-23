


function redirectToUserForm(){
	window.location.href = "/submitForm";
}

document.addEventListener("DOMContentLoaded", function()){
	var redirectToUserFormButton = document.getElementById("redirectToUserForm");
	
	if(redirectToUserFormButton){
		redirectToUserFormButton.addEventListener("click", redirectToUserForm);
	}
}