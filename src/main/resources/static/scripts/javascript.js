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
	
	
	//var commentEditingArea = document.getElementById("hidden_div_comment_edit");
	//commentEditingArea.style.visibility = "visible";
	
	if(selectedRadio){
		/* Get Commentary id from radio button, each rb is given an id by Commentary id by thymeleaf */
		var selectedCommentId = selectedRadio.id;
		
		var selectedComment = selectedRadio.closest('tr');
		var rowValues = [];
		
		
		var label = document.getElementById("commentId");
		
		selectedComment.querySelectorAll('td').forEach( function (td) {
			
			rowValues.push(td.textContent);
		});
		
		
		commentEditingArea.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
		
		
		commentEditingArea.textContent = rowValues[1];
		label.value = selectedCommentId;
	}
	
}











