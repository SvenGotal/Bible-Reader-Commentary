

document.addEventListener('DOMContentLoaded', function() {
	try{
		document.getElementById('write_comment_button').addEventListener('click', function(event) {
		
		event.preventDefault();
		const titleElement = document.getElementById("book_and_chapter_title");
		var bookId = titleElement.dataset.bookId;
		var chapterId = titleElement.dataset.chapterId;
		 	 
		window.location.href = '/private/submitComment?bookId=' + encodeURIComponent(bookId) + '&chapterId=' + encodeURIComponent(chapterId);
		});	
		
	}
	catch(error){
		//console.error(error);	
		console.log("comment writing unavailable to guest users...");	
	}		
})