const PUBLISHED = "Objavljen";
const PUBLISHED_SLOT = 1;
const TEXT_SLOT = 0;
const SUBJECT_SLOT = 3;

function modifyThisComment(modifyButton){
	
	const selectedCommentId = modifyButton.dataset.commentId;
	
	const commentSubjectElement = document.getElementById("subject_" + selectedCommentId);
	const commentPublishedElement = document.getElementById("published_" + selectedCommentId);
	const commentTextElement = document.getElementById("text_" + selectedCommentId);
	
	
	const commentEditingSubjectElement = document.getElementById("comment_subject_content");
	commentEditingSubjectElement.value = commentSubjectElement.textContent;
	const commentEditingIdHolder = document.getElementById("comment_id_holder");
	commentEditingIdHolder.value = selectedCommentId;

	const commentEditingPublishedElement = document.getElementById("comment_published_content");
	commentEditingPublishedElement.checked = commentPublishedElement.dataset.isPublished === "true" ? true : false;
	
	window.quill.root.innerHTML = commentTextElement.innerHTML;
	
	const commentEditingDiv = document.getElementById("commentEditingDiv");
	commentEditingDiv.style.display = "block";
	commentEditingDiv.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
	
}

function deleteComment(deleteCommentButton){
	
	const commentEditingIdHolder = document.getElementById("comment_id_holder");
	const deleteUserForm = document.getElementById("commentDeleteForm_" + deleteCommentButton.id);
	
	var prompt = window.confirm("Jeste li sigurni da želite izbrisati komentar?");
	
	commentEditingIdHolder.value = deleteCommentButton
		
		if(prompt){
			
			deleteUserForm.submit();
		}
		else{
			console.log("cancelled comment deletion...");
			deleteUserForm.addEventListener("submit", function(event) {
 			 // Stop the form from submitting
 			 event.preventDefault();
			});
		}
	
}

function submitEditedComment(){
	var formEdit = document.getElementById("formEdit");	
	var prompt = window.confirm("Jeste li sigurni da želite izmijeniti komentar?");
	
	
		if(prompt){
			var content = document.querySelector('#commentContent');
			content.value = quill.root.innerHTML;
			formEdit.submit();
		}
		else{
			console.log("user cancelled delete submit...");
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





