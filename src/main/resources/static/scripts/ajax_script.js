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