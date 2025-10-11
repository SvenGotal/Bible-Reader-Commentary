
/* Update chapters in submitComment form.  */
function updateChapters() {
	var selectedBookId = document.getElementById('bookSelection').value;

	// Perform an Ajax request to fetch chapters for the selected book
	fetch('/submitComment/fetchChapters?bookId=' + selectedBookId)
		.then(response => response.json())
		.then(data => {
			// Populate the 'chapterSelection' dropdown based on the response
			var chapterSelection = document.getElementById('chapterSelection');
			chapterSelection.innerHTML = ''; // Clear existing options

			// Add a placeholder option
			var placeholderOption = document.createElement('option');
			placeholderOption.value = '';
			placeholderOption.text = 'Glava';
			placeholderOption.disabled = true;
			placeholderOption.selected = true;
			chapterSelection.appendChild(placeholderOption);

			// Add options for each chapter
			data.forEach(chapter => {
				var option = document.createElement('option');
				option.value = chapter.number;
				option.text = chapter.number;
				chapterSelection.appendChild(option);
			});

			// Enable the 'chapterSelection' dropdown
			chapterSelection.disabled = false;
		})
		.catch(error => {
			console.error('Error fetching chapters:', error);
		});
}

/* Update chapters when Book is selected in index (publicly readable) */
function updateIndexChapters() {
	var selectedBookId = document.getElementById('bookSelection').value;
	var chapter_selector = document.getElementById('chapter_selector');
	var filterCheckbox = document.getElementById('filter_only_commented');
	
	/*Check for previous child elements - remove residual chapter boxes */
	if(chapter_selector != null){

		while(chapter_selector.hasChildNodes()){
			chapter_selector.removeChild(chapter_selector.firstChild);
		}
		
		/*Have the container div for select boxes be grid in it's display, none 
		(invisible) is defined in index.css for that element*/
		chapter_selector.style.display='grid';
	}

	// Perform an Ajax request to fetch chapters for the selected book
	fetch('public/fetchChapters?bookId=' + selectedBookId)
		.then(response => response.json())
		.then(data => {
			// Populate the 'chapterSelection' dropdown based on the response
			var chapterSelection = document.getElementById('chapterSelection');
			chapterSelection.innerHTML = ''; // Clear existing options

			// Add a placeholder option
			var placeholderOption = document.createElement('option');
			placeholderOption.value = '';
			placeholderOption.text = 'Glava';
			placeholderOption.disabled = true;
			placeholderOption.selected = true;
			chapterSelection.appendChild(placeholderOption);
			


			// Add options for each chapter
			data.forEach(chapter => {
				/*Create child elements and append them to drop-down select element*/ 
				var option = document.createElement('option');
				option.value = chapter.number;
				option.text = chapter.number;
				chapterSelection.appendChild(option);
				
				/*Create child elements for chapter boxes and append them in chapter_selector element*/
				var chapterBoxElementDiv = document.createElement('div');
				chapterBoxElementDiv.classList.add('chapter-selector-div');
				chapterBoxElementDiv.innerHTML=chapter.number;
				chapterBoxElementDiv.id = "chapterBox"+chapter.number;
				chapterBoxElementDiv.setAttribute('onclick','updateChapterInChapterBoxes(' + chapter.number + ')');
				
				
				chapter_selector.appendChild(chapterBoxElementDiv);
				
			});

			// Enable the 'chapterSelection' dropdown
			chapterSelection.disabled = false;
		})
		.catch(error => {
			console.error('Error fetching chapters:', error);
		});
}

/* Fetch Verses and Comments for selected Chapter in index (publicly readable) */
async function fetchVersesAndComments() {

	var selectedBookId = document.getElementById('bookSelection').value;
	var selectedBook = document.getElementById('bookSelection');
	var selectedChapterNumber = document.getElementById('chapterSelection').value;
	var commentsDisplay = document.getElementById('comments_text');
	var versesDisplay = document.getElementById('bible_text');
	
	var upper_separator_text = document.getElementById('upper_separator_text');
	var selectedBookOption = selectedBook.options[selectedBook.selectedIndex];
	var selectedBookName = selectedBookOption.text;
	upper_separator_text.innerText = '';
	

	try {

		
		var params = new URLSearchParams();
		params.append('bookId', selectedBookId);
		params.append('chapterNumber', selectedChapterNumber);

		
		const fetchVerses = await fetch('/public/fetchVerses?' + params.toString());
		const caughtVerses = await fetchVerses.json();
		

	
		const fetchComments = await fetch('/public/fetchPublicComments?' + params.toString());
		
		const caughtComments = await fetchComments.json();
		

		upper_separator_text.innerText = selectedBookName + ' ' + selectedChapterNumber;
		
		try{
			var quotation_text = document.getElementById('main_welcome_text');
			var quotation = document.getElementById('main_welcome_quotation');
		
			quotation_text.remove();
			quotation.remove();
		}
		catch{}
		
		versesDisplay.innerHTML = '';
		commentsDisplay.innerHTML = '';

		
		caughtVerses.forEach(verse => {
			var paragraph = document.createElement('p');
			paragraph.textContent = verse.number + ' ' + verse.text;
			versesDisplay.append(paragraph);
		});

		
		caughtComments.forEach(comment => {
			
			var table = document.createElement('table');
			table.classList.add("comments-expandable-table");
			var tbody = document.createElement('tbody');
			var h3 = document.createElement('h4');			
			var rowHeader = document.createElement('tr');
			rowHeader.classList.add("comments-expandable-table-row");
			rowHeader.classList.add("max-height");
			var rowText = document.createElement('tr');
			rowText.classList.add("word-break");
			var rowAuthor = document.createElement('tr');
			

			var tdAuthor = document.createElement('td');
			tdAuthor.colSpan = "1";
			var tdTitle = document.createElement('td');
			tdTitle.classList.add("comment-expandable-table-subject-td");
			tdTitle.colSpan = "2";
			
			var tdExpandButton = document.createElement('td');
			var expandButton = document.createElement('button');
			tdExpandButton.colSpan = "1";
			expandButton.classList.add("comments-expandable-table-comment-button");
			tdExpandButton.classList.add("width-2-percent");
			tdExpandButton.classList.add("comment-expandable-table-subject-td");
			tdExpandButton.classList.add("align-center");
			
			expandButton.innerHTML = "+";
			expandButton.addEventListener('click', function(event) {showHideIndexComment(this)});
			tdExpandButton.appendChild(expandButton);
			var tdText = document.createElement('td');
			
			
			var richText = document.createElement('div');
			tdText.appendChild(richText);
			tdText.colSpan = "4";
			
			h3.innerText = comment.subject;
			h3.style.cssText = 'padding-bottom: 2px;';
			

			tdTitle.appendChild(h3);
			tdTitle.style.cssText = 'padding-left: 15px;';	
			richText.innerHTML = comment.text;
			tdText.style.cssText = 'padding-left: 15px;';
			tdAuthor.innerText = comment.author + " | " + comment.timestamp;
			tdAuthor.style.cssText = 'padding-left: 15px;';
			
			if(comment.published == false){
				tdTitle.classList.remove("comment-expandable-table-subject-td");
				tdTitle.classList.add("comment-expandable-table-subject-private-td");
				tdAuthor.innerText = comment.author + "| PRIVATE | " + comment.timestamp;
			}
			
			
			rowHeader.appendChild(tdTitle);
			//rowHeader.appendChild(tdAuthor);
			//rowHeader.appendChild(tdTimestamp);
			rowHeader.appendChild(tdExpandButton);
			
			rowText.appendChild(tdText);
			
			
			rowHeader.classList.add('comments-expandable-table-row');
			rowText.classList.add('comments-expandable-table-row');
			rowText.style.cssText = 'display: none;';
			
			rowAuthor.classList.add('comments-expandable-table-row');
			rowAuthor.appendChild(tdAuthor);
			
			tbody.appendChild(rowHeader);
			tbody.appendChild(rowText);
			tbody.appendChild(rowAuthor);
			
			table.appendChild(tbody);
			
			
			commentsDisplay.appendChild(table);
		
		});

	}
	catch (error) {
		console.error("Error fetching verses and comments.", error);
	}

}

function fetchFiltered(inputText){
	
	if(inputText.value.length >= 3){
		
	}
	
}

async function updateCommentedBooks(){
	
	/* Get the checkbox element */
	const showOnlyCommentedBooksCheckBox = document.getElementById("filter_only_commented");
	
	/* If checkbox is checked - then fetch only books that contain commentary */
	if(showOnlyCommentedBooksCheckBox.checked){
		
		/* Clean the existing options from book element */
		const bookSelectionElement = document.getElementById("bookSelection");
		bookSelectionElement.innerHTML = "";
		
		/* Create a new placeholder option and append it to the element */
		var placeholderOption = document.createElement('option');
		placeholderOption.value = '';
		placeholderOption.text = 'Odaberite knjigu';
		bookSelectionElement.appendChild(placeholderOption);
		
		/* Proceed with the fetch */
		try{
			/* Fetch books from URL in APIController */
			const fetchCommentedBooks = await fetch('/public/fetchCommentedBooks');
			const caughtBooks = await fetchCommentedBooks.json();
			console.log("fetching books...");
			/* Foreach book, create an option element then append it to book selection */
			caughtBooks.forEach(book => {				
				var bookOption = document.createElement('option');
				bookOption.text = book.name;
				bookOption.value = book.id;
				bookSelectionElement.appendChild(bookOption);
				
			});
		}
		catch(e){
			console.log("error loading books from database...");
			console.log(e.message);
		}
	}
	
}






