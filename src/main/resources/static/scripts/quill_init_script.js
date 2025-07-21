/**
 * 
 */

 document.addEventListener('DOMContentLoaded', function () {
var quill = new Quill('#quillEditor', {
						    theme: 'snow',
						    placeholder: 'Write your comment here...',
						    modules: {
						      toolbar: [
						    	[{ 'header': [1, 2, 3, 4, 5, 6, false] }],
						    	[{ 'color': [] }, { 'background': [] }],
						    	[{ 'align': [] }],
						        ['bold', 'italic', 'underline'],
						        [{ list: 'ordered' }, { list: 'bullet' }],
						        ['clean']
						      ]
						    },
						    placeholder: "Komentar mora imati najmanje 20 znakova i naslov!",
						  });
						
						  // When submitting the form, copy Quill content to hidden input
						  document.querySelector("form").addEventListener("submit", function() {
						    var content = document.querySelector('#commentContent');
						    content.value = quill.root.innerHTML;
						  });	

 });