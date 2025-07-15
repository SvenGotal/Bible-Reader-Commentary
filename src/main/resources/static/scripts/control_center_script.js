/**
 * 
 */

 function deleteUser(deleteUserButton){
	
	const deleteUserForm = document.getElementById("deleteUserForm" + deleteUserButton.dataset.userId);		
	
	var prompt = window.confirm("Jeste li sigurni da želite korisnika? Biti će obrisani i svi komentari koje je korisnik napisao!");
		
		if(prompt){
			deleteUserForm.submit();
		}
		else{
			console.log("cancelled user deletion...");
		}
}