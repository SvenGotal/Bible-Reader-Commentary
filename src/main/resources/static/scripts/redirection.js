function redirectToIndex(){
	
	window.location.href = "/";
	
}	

function userCreationFormValidation(){
	
	var username = document.getElementById("username");
	var email = document.getElementById("email");
	var password = document.getElementById("password");
	var password_retype = document.getElementById("password_retype");
	
	var error_display = document.getElementById("error_display");
	
	let usernameIsNullFlag;
	let emailIsNullOrInvalidFlag;
	let passwordsDoNotMatchFlag;	
	
	let errorStringMessage = "";
	error_display.textContent = "";
	
	if(username.value === ""){
		errorStringMessage += "Korisničko ime nije ispunjeno.\n";
		usernameIsNullFlag = true;
	}
	if(password.value !== password_retype.value){
		errorStringMessage += "Lozinke se ne podudaraju.\n";
		emailIsNullOrInvalidFlag = true;		
	}
	if(email.value === "" || !email.checkValidity()){
		errorStringMessage += "E-mail adresa je prazna ili ne odgovara formatu.\n";
		passwordsDoNotMatchFlag = true;
	}
	
	if(usernameIsNullFlag || emailIsNullOrInvalidFlag || passwordsDoNotMatchFlag){
		event.preventDefault();
		error_display.textContent = errorStringMessage;
		error_display.style.display = "flex";
		
	}


}