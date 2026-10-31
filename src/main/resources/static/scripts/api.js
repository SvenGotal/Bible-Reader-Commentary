
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



