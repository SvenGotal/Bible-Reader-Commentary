
/*Function for updating the option in the select element for chapter on index. Fires when a chapter box is clicked on
**by the user which in turn calls fetchVersesAndComments from ajax_script. This function is embedded in each chapter box
**created in the ajax_script. */
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
	
	var subject = document.getElementById('commentSubject');
	var text = document.getElementById('comment_textarea');
	var submitButton = document.getElementById("submit_button");
	
	var selectedBook = document.getElementById('bookSelection');
	var selectedChapter = document.getElementById('chapterSelection');
	
	var toastMessageBox = document.getElementById("toast_message");
	
	if(selectedBook.value !== -1 || selectedChapter.value !== -1){
		
		toastMessageBox.innerText = "Potrebno je izabrati knjigu i poglavlje.";
		toastMessageBox.style.display = "block";
		
		if(subject.value === '' || text.value === ''){
			
			submitButton.disabled = true;
			toastMessageBox.innerText = "Potrebno je upisati naslov i tekst.";
			toastMessageBox.style.display = "block";
			
		}
		else{
			if(text.value.length < 20){
				toastMessageBox.innerText = "Tekst mora sadržavati minimalno 20 znakova.";
				toastMessageBox.style.display = "block";
				
				submitButton.disabled = true;
			}
			else{
				submitButton.disabled = false;
				toastMessageBox.style.display = "none";
			}
		}
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


