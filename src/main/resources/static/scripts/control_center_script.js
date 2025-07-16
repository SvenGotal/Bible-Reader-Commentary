/**
 * 
 */

 function deleteUser(deleteUserButton){
	
	const deleteUserForm = document.getElementById("deleteUserForm" + deleteUserButton.dataset.userId);		
	
	var prompt = window.confirm("Jeste li sigurni da želite izbrisati korisnika? Biti će obrisani i svi komentari koje je korisnik napisao!");
		
		if(prompt){
			deleteUserForm.submit();
		}
		else{
			console.log("cancelled user deletion...");
		}
}

function deleteComment(deleteCommentButton){
	const deleteUserForm = document.getElementById("deleteCommentForm" + deleteCommentButton.dataset.userId);		
	
	var prompt = window.confirm("Jeste li sigurni da želite izbrisati komentar?");
		
		if(prompt){
			deleteUserForm.submit();
		}
		else{
			console.log("cancelled comment deletion...");
		}
	
}