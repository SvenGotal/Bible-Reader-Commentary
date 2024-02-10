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
    
    function updateIndexChapters() {
        var selectedBookId = document.getElementById('bookSelection').value;

		console.log("Performing fetch...")
        // Perform an Ajax request to fetch chapters for the selected book
        fetch('/fetchChapters?bookId=' + selectedBookId)
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
    
    function updateIndexVerses(){
		
		/* Find tag element values by Id 
		Book: Postanak
		Chapter: 1
		*/ 
		
		console.log("Searching for tag elements and their values...")
		
		var selectedBookId = document.getElementById('bookSelection').value;
		var selectedChapterNumber = document.getElementById('chapterSelection').value;
		
		console.log("Found tag elements and their values...")
		
		/* Define parameters to send to controller - ApiController */
		var params = new URLSearchParams();
		params.append('bookId', selectedBookId);
		params.append('chapterNumber', selectedChapterNumber);
		
		console.log("fetching...");
		
		fetch('/fetchVerses?' + params.toString())
		.then(response => {
			if(!response.ok){
				throw new Error("Network response not sending json...");
			}
			console.log("Printing parameters: " + params.toString());
			console.log("Response is ok...");
			return response.json();
		})
		.then(data => {
			
			console.log("inside fetch sequence...");
			
			var verses = document.getElementById('bible_text');
			verses.innerHTML = '';
			
			console.log("Filling in data...");
			
			data.forEach(verse => {
				var paragraph = document.createElement('p');
				paragraph.textContent = verse.number + ' ' + verse.text;
				verses.append(paragraph);
			});
			
		}).catch(error => {
			console.error('Error fetching verses:', error);
			});
		
	}