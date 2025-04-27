
/*Function for updating the option in the select element for chapter on index. Fires when a chapter box is clicked on
**by the user which in turn calls fetchVersesAndComments from ajax_script. This function is embedded in each chapter box
**created in the ajax_script. */
function updateChapterInChapterBoxes(chapterNumber){
	
	var chapterSelection = document.getElementById('chapterSelection');
	chapterSelection.value = chapterNumber;
	chapterSelection.text = chapterNumber;
	
	/*Make sure the onchange event is fired in the chapterSelection once the value and text are updated.*/
	chapterSelection.dispatchEvent(new Event('change'));
	
	console.log("shoot!...");
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






