/**
 * 
 */

function getParameterFromURL(paramName){
	const parameters = new URLSearchParams(window.location.search);
	return parameters.get(paramName);
}

 document.addEventListener('DOMContentLoaded', function() {
	
	const bookId = getParameterFromURL("bookId");
	
	if(bookId){
		const bookSelection = document.getElementById("bookSelection");
		if(bookSelection){
			bookSelection.value = bookId;
			bookSelection.dispatchEvent(new Event('change'));
		}
	}
});

setTimeout(function () {
	
	const bookId = getParameterFromURL("bookId");
	const chapterId = getParameterFromURL("chapterId");
	
	
	if(chapterId && bookId){
		const chapterSelection = document.getElementById("chapterSelection");
		console.log("chapter Id found...: " + chapterId);
		
		if(chapterSelection){
			console.log("chapter select element found...");
			chapterSelection.value = chapterId;
			chapterSelection.dispatchEvent(new Event('change', {bubbles : true}));
		}
	}	
}, 200);



/*



$(document).ajaxStop(function () {
	
	const chapterId = getParameterFromURL("chapterId");
	
	
	if(chapterId){
		const chapterSelection = document.getElementById("chapterSelection");
		console.log("chapter Id found...: " + chapterId);
		
		if(chapterSelection){
			console.log("chapter select element found...");
			chapterSelection.value = chapterId;
			chapterSelection.dispatchEvent(new Event('change', {bubbles : true}));
		}
	}	
});

/*
fetch(url)
  .then(response => response.text())
  .then(html => {
    // Update DOM with HTML
    document.body.innerHTML = html;
    // Now set chapterSelection
    const chapterId = getParameterFromURL("chapterId");
    const chapterSelection = document.getElementById("chapterSelection");
    if (chapterId && chapterSelection) {
      chapterSelection.value = chapterId;
      chapterSelection.dispatchEvent(new Event('change', { bubbles: true }));
    }
  });

*/








