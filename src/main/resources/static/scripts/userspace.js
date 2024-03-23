/**
 *  finish this js for version 2.
 */

 /**Used in radio button selection */
function modifyComment(){
	
	/* fetch selected radio button from the group */
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	
	/* fetch hidden inputs required by java backend. Each carries comment Id required for form submission */
	var commentIdHolderEdit = document.getElementById("commentIdEdit");
	var commentIdHolderDelete = document.getElementById("commentIdDelete");
	
	
	/* fetch textarea and subject input field set it's value to empty string */
	var textArea = document.getElementById("commentEditingArea");
	var subjectArea = document.getElementById("commentSubjectEdit");
	textArea.textContent = '';
	subjectArea.value = '';
	
	if(selectedRadio){
		
		/* get ID from radio button and set hidden inputs to it's value */
		var selectedCommentId = selectedRadio.id;
		
		commentIdHolderEdit.value = selectedCommentId;
		commentIdHolderDelete.value = selectedCommentId;
		
		
		/* enable smooth scrolling and focus user window to editing area */
		var commentEditingDiv = document.getElementById("commentEditingDiv");
		commentEditingDiv.style.display = "block";
		commentEditingDiv.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
		
		enableInputFields();
		
		/* get values from selected commentary */
		var selectedComment = selectedRadio.closest('tr');
		var rowValues = [];
		selectedComment.querySelectorAll('td').forEach( function (td) {
			
			rowValues.push(td.textContent);
		});
		
		/* fill textarea and subject area with selected commentary text */
		textArea.textContent = rowValues[1];
		subjectArea.value = rowValues[6];
		
		commentMakePrivateOrPublic(rowValues[4]);
	}
}

/**Use only in comment editing submission */
function submitEditedComment(){
	
	/* get submission form that calls controller method 'public String editMyComment()' */
	var formEdit = document.getElementById("formEdit");

	var prompt = window.confirm("Jeste li sigurni da želite izmijeniti komentar?");
	
		/* if user clicked yes/ok */
		if(prompt){
			/* send POST to call the controller */
			formEdit.submit();
		}
		/* if user canceled */
		else{
			console.log("user cancelled delete submit...");
		}
	
}

/**Use only in comment deletion */
function commentDelete(){
	
	/* get id holder (hidden <input> tag)  */
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

function commentMakePrivateOrPublic(publishedBoolean){
	
	var publishedCheckbox = document.getElementById('publishedCheckbox');
	publishedCheckbox.disabled = false;
		
	if(publishedBoolean === 'Da'){
		publishedCheckbox.checked = true;
	}
	else{
		publishedCheckbox.checked = false;
	}
}

function enableInputFields(){
	
	/* fetch textarea and subject and enable them */
	var textArea = document.getElementById("commentEditingArea");
	var subjectArea = document.getElementById("commentSubjectEdit");
	
	textArea.disabled = false;
	subjectArea.disabled =false;
	
	/* fetch buttons and enable them */
	var buttonEdit = document.getElementById("buttonEdit");
	var buttonDelete = document.getElementById("buttonDelete");
		
	buttonEdit.disabled = false;
	buttonDelete.disabled = false;
}





