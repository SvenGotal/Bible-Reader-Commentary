/**
 * Script for loading the submit comment page, if user selected book and chapter on index page then
 * those values will default into the submit comment page and be pre-selected.
 */

function getParameterFromURL(paramName){
	const parameters = new URLSearchParams(window.location.search);
	return parameters.get(paramName);
}

 document.addEventListener('DOMContentLoaded', function() {
	
	const bookId = getParameterFromURL("bookId");
	const chapterId = getParameterFromURL("chapterId");
	
	if(bookId){
		const bookSelection = document.getElementById("bookSelection");
		if(bookSelection){
			bookSelection.value = bookId;
			bookSelection.dispatchEvent(new Event('change'));
		}
	}
	
	wait(200);
	
	if(chapterId){
		const chapterSelection = document.getElementById("chapterSelection");
		if(chapterSelection){
			chapterSelection.value = chapterId;
			chapterSelection.dispatchEvent(new Event('change'));
		}
	}
});

function wait(ms){
	return new Promise(resolve => setTimeout(resolve, ms));
}
/*
setTimeout(function () {
	
	const bookId = getParameterFromURL("bookId");
	const chapterId = getParameterFromURL("chapterId");
	
	
	if(chapterId && bookId){
		const chapterSelection = document.getElementById("chapterSelection");
		
		if(chapterSelection){
			chapterSelection.value = chapterId;
			chapterSelection.dispatchEvent(new Event('change', {bubbles : true}));
		}
	}	
}, 3000);

*/
