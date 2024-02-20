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
		var formEdit = document.getElementById("formEdit");
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
		formEdit.submit();
	}
	
}

function commentDelete(){
	
	/* get id holder (hidden <inpu> tag)  */
	var commentIdHolder = document.getElementById("commentIdDelete");
	
	/* get submit form ++seems unecessary approach++ */
	var submitForm = document.getElementById("formDelete");
	
	/* get currently selected radio button (id holds the comment Id) */
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	
	/* if radio button is selected */
	if(selectedRadio){
		
		commentIdHolder.value = selectedRadio.id;
		
		var prompt = window.confirm("Jeste li sigurni da želite obrisati komentar?");
	
		if(prompt){
			submitForm.submit();
		}
		else{
			console.log("user cancelled delete submit...");
		}
	}
	
	
}











