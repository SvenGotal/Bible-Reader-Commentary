/**
 * When the form loads add event listener to the create comment button in "/". Redirect the data from the 
 * select elements in index and send the data about Book and Chapter to the controller. 
 */

document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('Index_writeCommentButton').addEventListener('click', function(event) {
	event.preventDefault();
	var bookId = document.getElementById('bookSelection').value;
	var chapterId = document.getElementById('chapterSelection').value;
  
	window.location.href = '/private/submitComment?bookId=' + encodeURIComponent(bookId) + '&chapterId=' + encodeURIComponent(chapterId);
	});	
	
	
})








