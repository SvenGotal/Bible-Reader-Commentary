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
				console.error("Response is not ok...");
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
	
	function updateComments(){
		
		console.log("Trying to update comments...");
		
		var selectedBookId = document.getElementById('bookSelection').value;
		var selectedChapterNumber = document.getElementById('chapterSelection').value;
		var commentsDisplay = document.getElementById('comments_text');
		
		var params = new URLSearchParams();
		params.append('bookId', selectedBookId);
		params.append('chapterNumber', selectedChapterNumber);
		
		console.log("Performing fetch in update comments...");
		
		fetch('/fetchPublicComments?' + params.toString())
		.then( response => response.json())
		.then( data => {
			
			console.log("Got data, displaying data to the screen...");
			commentsDisplay.innerHTML = '';
			
			if(Array.isArray(data)){
				console.log("Sent data is an array...");
			}
			else{
				console.log("Sent data is NOT an array...");
			}
			
			data.forEach(comment => {
				var paragraph = document.createElement('p');
				paragraph.innerHTML = 
					comment.subject + 
					'</br></br>' + 
					comment.text + 
					'</br></br>' + 
					comment.username.getUsername() + 
					' : ' 
					+ comment.timestamp;
					
				commentsDisplay.appendChild(paragraph);
			});
			
		}).catch( error => {
			console.error('Error fetching commentary...', error);
		});
		
	}

	
	async function fetchVersesAndComments(){
		
		var selectedBookId = document.getElementById('bookSelection').value;
		var selectedChapterNumber = document.getElementById('chapterSelection').value;
		var commentsDisplay = document.getElementById('comments_text');
		var versesDisplay = document.getElementById('bible_text');
		
		try{
			
			console.log("Started in try block...");
			var params = new URLSearchParams();
			params.append('bookId', selectedBookId);
			params.append('chapterNumber', selectedChapterNumber);
			
			console.log("Fetching Verses...");
			const fetchVerses = await fetch('/fetchVerses?' + params.toString());
			const caughtVerses = await fetchVerses.json();
			console.log("Caught Verses...");
			
			console.log("Fetching Comments...");
			const fetchComments = await fetch('/fetchPublicComments?' + params.toString());
			console.log("Fetching Comments first part complete...");
			const caughtComments = await fetchComments.json();
			console.log("Caught Comments...");
			
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
					comment.username.getUsername() + 
					' : ' 
					+ comment.timestamp;
					
				commentsDisplay.appendChild(paragraph);
			});
			
		}
		catch(error){
			console.error("Error fetching verses and comments.", error);
		}
		
	}
	
	
	
	
	
	
	
	
	