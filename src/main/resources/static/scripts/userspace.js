const PUBLISHED = "Objavljen";
const PUBLISHED_SLOT = 1;
const TEXT_SLOT = 0;
const SUBJECT_SLOT = 3;

const commentEditorDiv = document.getElementById("comment_edit_div");

function modifyComment(button){
	
	const commentId = button.dataset.commentId;
	const commentText = button.dataset.commentText;
	const commentSubject = button.dataset.commentSubject;
	const commentIsPublished = button.dataset.commentPublished;
	
	const commentIdHolder = document.getElementById("comment_id_holder");
	const commentTextHolder = document.getElementById("commentContent");
	const commentSubjectHolder = document.getElementById("comment_subject_content");
	const commentPublishedHolder = document.getElementById("comment_published_content");
	
	commentIdHolder.value = commentId;
	window.quill.root.innerHTML = commentText;
	commentSubjectHolder.value = commentSubject;
	commentPublishedHolder.checked = commentIsPublished === "true" ? true : false;
	
	commentEditorDiv.style.display = "flex";
	//commentEditorDiv.style.width = "500px";
	commentEditorDiv.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
	});
	
	
	
}

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

function toggleAccordionViaHeader(element){

	const accordionElement = document.getElementById(element.dataset.targetId);
	const toggle = accordionElement.classList.toggle("is-open");
	
	if(toggle){
		element.scrollIntoView({
			behavior: "smooth",
			block: "start"
		});
	}
	else{
		commentEditorDiv.style.display = "none";
	}
}

function closeAllOpenAccordionElements(){
	const allOpenAccordionElements = document.querySelectorAll('.user-comments-comment-row.is-open');
	
	allOpenAccordionElements.forEach(accordion => {
		accordion.classList.remove('is-open');
	});
	commentEditorDiv.style.display = "none";
}






