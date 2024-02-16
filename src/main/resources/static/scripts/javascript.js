function passwordValidation(){
	var password = document.getElementById("password");
	var password_retype = document.getElementById("password_retype");
	var error_display = document.getElementById("error_display");
	
	if(password.value !== password_retype.value){
		error_display.textContent = "Lozinke se ne podudaraju";
		event.preventDefault();
	}
	else{
		error_display.textContent= '';
	}
}

function commentEdit(){
	
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	var commentEditingArea = document.getElementById("commentEditingArea");
	
	if(selectedRadio){
		
		var selectedComment = selectedRadio.closest('tr');
		var rowValues = [];
		
		selectedComment.querySelectorAll('td').forEach( function (td) {
			
			rowValues.push(td.textContent);
		});
		
		commentEditingArea.textContent = rowValues[1];
	}
	
}











