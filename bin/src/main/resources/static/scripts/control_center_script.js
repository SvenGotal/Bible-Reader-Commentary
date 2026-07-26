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

function deleteAnnouncement(deleteAnnouncementButton){
	const deleteAnnouncementForm = document.getElementById("deleteAnnouncement_" + deleteAnnouncementButton.dataset.announcementId);		
	
	var prompt = window.confirm("Jeste li sigurni da želite izbrisati obavijest?");
		
		if(prompt){
			deleteAnnouncementForm.submit();
		}
		else{
			console.log("cancelled comment deletion...");
		}
}

function flipAnnouncementStatus(flipAnnouncementStatusButton){
	const changeAnnouncementActiveStatusForm = document.getElementById('changeAnnouncementActiveStatus_' + flipAnnouncementStatusButton.dataset.announcementId);
	changeAnnouncementActiveStatusForm.submit();
}

function saveDataFromQuill(){
	
	const quillTextContentHolder = document.getElementById("commentContent");
	quillTextContentHolder.value = window.quill.root.innerHTML;
	
}



