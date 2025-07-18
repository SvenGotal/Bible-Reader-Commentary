
/*Function for updating the option in the select element for chapter on index. Fires when a chapter box is clicked on
**by the user which in turn calls fetchVersesAndComments from ajax_script. This function is embedded in each chapter box
**created in the ajax_script. */

/* User mouse tracker: */
let userMouseX;
let userMouseY;
document.addEventListener("mousemove", function(event) {
	
	const userMouseX = event.clientX;
	const userMouseY = event.clientY;
	
});

function updateChapterInChapterBoxes(chapterNumber){
	
	var chapterSelection = document.getElementById('chapterSelection');
	chapterSelection.value = chapterNumber;
	chapterSelection.text = chapterNumber;
	
	/*Make sure the onchange event is fired in the chapterSelection once the value and text are updated.*/
	chapterSelection.dispatchEvent(new Event('change'));
	
}

/*Simple password validation script for comparing whether two passwords are equal. */
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

function checkUserInputs(){
	
	const messageStart = "Potrebno je:"
	const messageSelected = "\n*Odabrati knjigu i naslov.";
	const messageSubject = "\n*Upisati naslov komentara";
	const messageComment = "\n*Upisati tekst komentara (više od 20 znakova).";
	const messageSet = new Set();
	messageSet.add(messageStart);
	
	var subject = document.getElementById('commentSubject');
	var text = document.getElementById('comment_textarea');
	var submitCommentButton = document.getElementById("submit_button");
	
	var selectedBook = document.getElementById('bookSelection');
	var selectedChapter = document.getElementById('chapterSelection');
	
	var toastMessageBox = document.getElementById("toast_message");
	
	const submitCommentButtonPosition = submitCommentButton.getBoundingClientRect();
	toastMessageBox.style.top = (submitCommentButtonPosition.top - (2* submitCommentButtonPosition.height)) + 'px'; 
	
	var hasSelected = true;
	var hasSubject = true;
	var hasComment = true;
	
	if(selectedBook.value < 0 || selectedChapter.value < 0){
		hasSelected = false;
		messageSet.add(messageSelected);
	}
	else{
		messageSet.delete(messageSelected);
	}
	if(subject.value === ''){
		hasSubject = false;
		messageSet.add(messageSubject);
	}
	else{
		messageSet.delete(messageSubject);
	}
	if(text.value.length < 20){
		hasComment = false;
		messageSet.add(messageComment);
	}
	else{
		messageSet.delete(messageComment);
	}
	
	if( !hasSelected || !hasSubject || !hasComment )
	{
		submitCommentButton.disabled = true;
		var messageBoxText = "";
		messageSet.forEach( message => {
			messageBoxText += message;
		});
		
		toastMessageBox.innerText = messageBoxText;
		toastMessageBox.style.display = "block";
	}
	else{
		toastMessageBox.style.display = "none";
		submitCommentButton.disabled = false;
	}
	
	toastMessageBox.classList.remove('toast-message-show');
	void toastMessageBox.offsetWidth;
	toastMessageBox.classList.add('toast-message-show');
	
}

function redirectToIndex(){
	
	window.location.href = "/";
	
}

function highlightChapterBox(chapterSelection){
	
	const chapter_selector = document.getElementById('chapter_selector');
	const chapterBoxes = Array.from(chapter_selector.children).filter(child => child.tagName === 'DIV');
	
	chapterBoxes.forEach( div => {
		div.className= 'chapter-selector-div';
	})
		

	
	const chapterId = chapterSelection.value;
	const chapterBoxId = "chapterBox"+ chapterId;
	
	var highlightedChapterBox = document.getElementById(chapterBoxId);
	highlightedChapterBox.className= 'highlight-chapter-box';
}

function shareThisComment(shareCommentButton){
	
	const linkCopiedMessage = "Link copied!";
	const commentId = shareCommentButton.dataset.commentId;
	const toastMessage = document.getElementById("toast_message");
	
	const link = window.location.origin +'/public/comment/' + commentId;
	navigator.clipboard.writeText(link);

}


