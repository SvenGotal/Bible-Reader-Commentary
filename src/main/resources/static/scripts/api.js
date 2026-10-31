
function fetchSelectedBookChapters(e){
	
	const chapterHolder = document.getElementById("book_chapter_holder");
	const bookId = e.dataset.bookId;
	const title = document.getElementById("book_and_chapter_title");
	
	//TODO: this is only a stub for filtering contents based on commented chapter or not commented
	const selectOnlyCommentedChaptersCheckbox = false;

	try{
		
		fetch('public/fetchChapters?bookId='+bookId+'&filterCommented='+selectOnlyCommentedChaptersCheckbox)
		.then(response => response.json())
		.then(data => {
			
			chapterHolder.innerHTML = "";
			
			title.innerHTML = e.innerHTML;
			title.dataset.bookId = bookId;
			
			data.forEach(chapter => {
				const chapterbox = document.createElement("div");
				chapterbox.innerHTML = chapter.number;
				chapterbox.classList.add("chapter-holder-box");
				chapterbox.dataset.chapterId = chapter.id;
				//chapterbox.dataset.bookId = bookId;
				chapterbox.dataset.bookName = e.innerHTML;
				chapterbox.addEventListener("click", () => {fetchSelectedChapterVerses(chapterbox)});
				chapterHolder.append(chapterbox);
			});
			
			
		});
		
	}
	catch{
		console.log("fetch failed");
	}		
}

function fetchSelectedChapterVerses(e){
	
	const chapterId = e.dataset.chapterId;
	const title = document.getElementById("book_and_chapter_title");

	fetch("/public/fetchChapterVerses?chapterId="+chapterId)
	.then( response => response.json())
	.then( data => {
		

		title.innerHTML = e.dataset.bookName + "    " + e.innerHTML;
		title.dataset.chapterId = chapterId;
		
		const readingArea = document.getElementById("bible_text");
		readingArea.innerHTML = "";
		
		data.forEach(verse => {
			
			const verseWrapper = document.createElement("div");						
			const verseParagraph = document.createElement("p");
			verseParagraph.innerHTML = verse.number + ". " + verse.text;			
			verseWrapper.append(verseParagraph);			
			readingArea.append(verseWrapper);
		});
		
	});
		
}

let databaseQueryTimeout;

function searchVersesThatMatch(element){
	
	const searchString = element.value;
	if(searchString.length < 3){
		//console.log("return");
		return;
	}
	
	clearTimeout(databaseQueryTimeout);
	
	const readingArea = document.getElementById("bible_text");
		
	databaseQueryTimeout = setTimeout( () => {
		
		//console.log("sucess");
		fetch("/public/fetchSearchVerses?match=" +searchString)
		.then( response => response.json())
		.then( data => {
			
			readingArea.innerHTML = "";
			
			if(data.length === 0){
				const stringNotFoundTitleWrapper = document.createElement("div");
				const stringNotFoundTitle = document.createElement("h1");
				stringNotFoundTitle.innerHTML = 'Nije pronađena "' + searchString + '"';
				stringNotFoundTitleWrapper.append(stringNotFoundTitle);
				readingArea.append(stringNotFoundTitleWrapper);
			}
			
			data.forEach(DTO => {
				
				const verseText = DTO.verseText;
				
				const startIndex = verseText.toLowerCase().indexOf(searchString.toLowerCase());
				if(startIndex !== -1){
					const endIndex = startIndex + searchString.length;

					const textBefore = verseText.slice(0 , startIndex);
					const foundMatch = verseText.slice(startIndex, endIndex);
					const textAfter = verseText.slice(endIndex);

					const markingSpan = document.createElement("span");
					markingSpan.classList.add("highlight");
					markingSpan.textContent = foundMatch;

					const verseWrapper = document.createElement("div");
					const verseTitle = document.createElement("h3");						
					const verseParagraph = document.createElement("p");

					verseTitle.innerHTML = DTO.bookName + " " + DTO.chapterNumber + ":" + DTO.verseNumber;
					verseParagraph.replaceChildren(textBefore, markingSpan, textAfter);// innerHTML = textBefore + markingSpan.textContent + textAfter;	

					verseWrapper.append(verseTitle);			
					verseWrapper.append(verseParagraph);			
					readingArea.append(verseWrapper);
				}
				
			});
			
		});
		
	}, 1500);
	
}

