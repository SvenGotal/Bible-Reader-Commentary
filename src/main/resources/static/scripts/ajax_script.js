
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
			placeholderOption.text = 'Odaberite poglavlje';
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
			placeholderOption.text = 'Odaberite poglavlje';
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

		console.log("Started in try block...");
		var params = new URLSearchParams();
		params.append('bookId', selectedBookId);
		params.append('chapterNumber', selectedChapterNumber);

		console.log("Fetching Verses...");
		const fetchVerses = await fetch('/public/fetchVerses?' + params.toString());
		const caughtVerses = await fetchVerses.json();
		console.log("Caught Verses...");

		console.log("Fetching Comments...");
		const fetchComments = await fetch('/public/fetchPublicComments?' + params.toString());
		console.log("Fetching Comments first part complete...");
		const caughtComments = await fetchComments.json();
		console.log("Caught Comments...");

		upper_separator_text.innerText = selectedBookName + ' ' + selectedChapterNumber;
		
	
		console.log("Cleaning existing text...");
		versesDisplay.innerHTML = '';
		commentsDisplay.innerHTML = '';

		console.log("Processing caught Verses...");
		caughtVerses.forEach(verse => {
			var paragraph = document.createElement('p');
			paragraph.textContent = verse.number + ' ' + verse.text;
			versesDisplay.append(paragraph);
		});

		console.log("Processing caught Comments...");
		caughtComments.forEach(comment => {
			var paragraph = document.createElement('p');
			paragraph.innerHTML =
				comment.subject +
				'</br></br>' +
				comment.text +
				'</br></br>' +
				comment.user.username +
				' : '
				+ comment.timestamp;

			commentsDisplay.appendChild(paragraph);
		});

	}
	catch (error) {
		console.error("Error fetching verses and comments.", error);
	}

}








