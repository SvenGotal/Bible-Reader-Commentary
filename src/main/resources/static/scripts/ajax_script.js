
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
	
	/*Check for previous child elements - remove residual chapter boxes */
	if(chapter_selector != null){
		console.log("chapter selector is found...");
		while(chapter_selector.hasChildNodes()){
			chapter_selector.removeChild(chapter_selector.firstChild);
		}
		
		/*Have the container div for select boxes be grid in it's display, none 
		(invisible) is defined in index.css for that element*/
		chapter_selector.style.display='grid';
	}
	else{
		console.log("chapter selector not found...");
	}

	console.log("Performing fetch...")
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
				
				console.log("appending chapter: " + chapter.number);
				
				/*Create child elements for chapter boxes and append them in chapter_selector element*/
				var chapterBoxElementDiv = document.createElement('div');
				chapterBoxElementDiv.classList.add('chapter-selector-div');
				chapterBoxElementDiv.innerHTML=chapter.number;
				
				
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
		var quotation_text = document.getElementById('main_welcome_text');
		var quotation = document.getElementById('main_welcome_quotation');
		
		quotation_text.innerText = '';
		quotation.innerText = '';
		
		versesDisplay.innerHTML = '';
		commentsDisplay.innerHTML = '';

		
		caughtVerses.forEach(verse => {
			var paragraph = document.createElement('p');
			paragraph.textContent = verse.number + ' ' + verse.text;
			versesDisplay.append(paragraph);
		});

		
		caughtComments.forEach(comment => {
			
			var table = document.createElement('table');
			var tbody = document.createElement('tbody');
			var h3 = document.createElement('h3');
			var rowSubject = document.createElement('tr');
			var rowText = document.createElement('tr');
			var rowAuthor = document.createElement('tr');
			
			var tdSubject = document.createElement('td');
			var tdText = document.createElement('td');
			var tdAuthor = document.createElement('td');
			
			h3.innerText = comment.subject;
			h3.style.cssText = 'padding-bottom: 2px;';
			
			tdSubject.appendChild(h3);
			tdSubject.style.cssText = 'padding-left: 15px;';	
			tdText.innerText = comment.text;
			tdText.style.cssText = 'padding-left: 15px;';
			tdAuthor.innerText = comment.author + ' : ' + comment.timestamp;
			tdAuthor.style.cssText = 'padding-left: 15px;';
			
			rowSubject.appendChild(tdSubject);
			rowSubject.style.cssText = 'height: 35px;'
			rowText.appendChild(tdText);
			rowAuthor.appendChild(tdAuthor);
			
			rowSubject.classList.add('comments-rowSubject');
			rowText.classList.add('comments-rowText');
			rowAuthor.classList.add('comments-rowAuthor');
			
			tbody.appendChild(rowSubject);
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








