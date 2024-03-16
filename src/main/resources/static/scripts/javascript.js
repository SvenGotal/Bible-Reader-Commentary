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
	
	/* get currently selected radio-button */
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	
	/* if radio-button is selected */
	if(selectedRadio){
		/* Get Commentary id from radio button, each rb is given an id by Commentary id by thymeleaf */
		var selectedCommentId = selectedRadio.id;
		/* get editing button */
		var buttonEdit = document.getElementById("button_edit");
		/* get deleting button */
		var buttonDelete = document.getElementById("buttonDelete");
		/* get text-area element */
		var textArea = document.getElementById("commentEditingArea");
		/* get values from table row where radio-button is selected */
		var selectedComment = selectedRadio.closest('tr');
		/* prepare an array to store row values */
		var rowValues = [];
		
		/* erase current content from text-area for editing */
		textArea.textContent = '';
		
		/* get hidden input that will store Commentary id */
		var commentIdHolder = document.getElementById("commentId");
		
		/* fill rowValues array with table row values */
		selectedComment.querySelectorAll('td').forEach( function (td) {
			
			rowValues.push(td.textContent);
		});
		
		/* enable smooth scrolling and focus user window to editing area */
		commentEditingArea.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
		
		/* fill text-area with row value from 2nd <td> in table row (Commentary.text) */
		commentEditingArea.textContent = rowValues[1];
		/* set hidden label value to Commentary.id (send to controller) */
		commentIdHolder.value = selectedCommentId;
		
		/* enable editing buttons (disabled by default) */
		buttonEdit.disabled = false;
		buttonDelete.disabled = false;
	}
	
}

/**Use only in comment editing submission */
function submitEditedComment(){
	
	/* get submission form that calls controller method 'public String editMyComment()' */
	var formEdit = document.getElementById("formEdit");
	/* send POST to call the controller */
	formEdit.submit();
	
}

/** */
function commentDelete(){
	
	/* get id holder (hidden <inpu> tag)  */
	var commentIdHolder = document.getElementById("commentIdDelete");
	
	/* get submit form that calls controller method deleteMyComment() */
	var submitForm = document.getElementById("formDelete");
	
	/* get currently selected radio button (id holds the comment Id) */
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	
	/* if radio button is selected */
	if(selectedRadio){
		
		/* set value of hidden <input> to Commentary id which will be sent to the controller */
		commentIdHolder.value = selectedRadio.id;
		/* prompt pop-up to confirm the decision to delete Commentary from persistence */
		var prompt = window.confirm("Jeste li sigurni da želite obrisati komentar?");
	
		/* if user clicked yes/ok */
		if(prompt){
			submitForm.submit();
		}
		/* if user canceled */
		else{
			console.log("user cancelled delete submit...");
		}
	}
	
	
}

/* not working adjust later */
function insertComment(){
	
	var subject = document.getElementById('commentSubject');
	var text = document.getElementById('comment_textarea');
	var submitButton = document.getElementById("submit_button");
	
	var selectedBook = document.getElementById('bookSelection');
	var selectedChapter = document.getElementById('chapterSelection');
	
	if(selectedBook.selectedIndex !== 0 || selectedChapter.selectedIndex !== 0){
		
		if(subject.value === '' || text.value === ''){
			
			submitButton.disabled = true;
			
		}
		else{
			if(text.value.length < 20){
				var warn = window.prompt("Tekst mora sadržavati minimalno 20 znakova.");
				submitButton.disabled = true;
			}
			else{
				submitButton.disabled = false;
			}
		}
	}
	
	
	
	
}










