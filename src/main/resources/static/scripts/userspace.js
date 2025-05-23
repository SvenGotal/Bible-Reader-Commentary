const PUBLISHED = "Objavljen";
const PUBLISHED_SLOT = 1;
const TEXT_SLOT = 0;
const SUBJECT_SLOT = 3;

function modifyComment(){
	
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	var commentIdHolderEdit = document.getElementById("commentIdEdit");
	var commentIdHolderDelete = document.getElementById("commentIdDelete");
	
	var textArea = document.getElementById("commentEditingArea");
	var subjectArea = document.getElementById("commentSubjectEdit");
	textArea.textContent = '';
	subjectArea.value = '';
	
	if(selectedRadio){
		var selectedCommentId = selectedRadio.id;
		commentIdHolderEdit.value = selectedCommentId;
		commentIdHolderDelete.value = selectedCommentId;
		
		var commentEditingDiv = document.getElementById("commentEditingDiv");
		commentEditingDiv.style.display = "block";
		commentEditingDiv.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
		
		enableInputFields();
		
		var selectedComment = selectedRadio.closest('tr');
		var rowValues = [];
		selectedComment.querySelectorAll('td').forEach( function (td) {
			
			rowValues.push(td.textContent);
		});
		
		textArea.textContent = rowValues[TEXT_SLOT].trim();
		subjectArea.value = rowValues[SUBJECT_SLOT];
		commentMakePrivateOrPublic(rowValues[PUBLISHED_SLOT]);
	}
}

function submitEditedComment(){
	var formEdit = document.getElementById("formEdit");
	var prompt = window.confirm("Jeste li sigurni da želite izmijeniti komentar?");
	
		if(prompt){
			formEdit.submit();
		}
		else{
			console.log("user cancelled delete submit...");
		}
}

function commentDelete(){
	
	var commentIdHolder = document.getElementById("commentIdDelete");
	var submitForm = document.getElementById("formDelete");
	var selectedRadio = document.querySelector('input[name="commentSelect"]:checked');
	
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

function commentMakePrivateOrPublic(publishedBoolean){
	
	var publishedCheckbox = document.getElementById('publishedCheckbox');
	publishedCheckbox.disabled = false;
		
	if(publishedBoolean === PUBLISHED){
		publishedCheckbox.checked = true;
	}
	else{
		publishedCheckbox.checked = false;
	}
}

function enableInputFields(){
	
	var textArea = document.getElementById("commentEditingArea");
	var subjectArea = document.getElementById("commentSubjectEdit");
	
	textArea.disabled = false;
	subjectArea.disabled =false;
	
	var buttonEdit = document.getElementById("buttonEdit");
	var buttonDelete = document.getElementById("buttonDelete");
		
	buttonEdit.disabled = false;
	buttonDelete.disabled = false;
}


function showHideComment(button){
	
	var buttonRow = button.parentNode.parentNode;
	var commentRow = buttonRow.nextElementSibling;
	
	if(commentRow){
		
		if(button.textContent === '+'){
			commentRow.style.display = "table-row";
			button.text = '-';
			button.textContent = '-';
			commentRow.style.width = "100%";
		}
		else if(button.textContent === '-'){
			commentRow.style.display = "none";
			button.text = '+';
			button.textContent = '+';
		}
	}
}


