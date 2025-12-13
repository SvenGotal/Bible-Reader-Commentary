/**
 * 
 */

 function closeMenu(){
	try{
		var hamburgerToggle = document.getElementById("hamburger_toggle");		
		hamburgerToggle.checked = false;
	}
	catch(e){
		/* ignore / do nothing */ 
	}		
 }
 
 function toggleUserFeedbackForm(){
	var userFormWrapper = document.getElementById("main_user_feedback_input_wrapper");

	userFormWrapper.classList.toggle('open');
	event.preventDefault();
 }
 
 function validateFeedbackInput(){
	var feedback_userinfo = document.getElementById("feedback_userinfo");
	var feedback_textarea = document.getElementById("feedback_textarea");
	var feedback_message_box = document.getElementById("feedback_message_box");
	var feedback_message_box_paragraph = document.getElementById("feedback_message_box_paragraph");
	
	
	
	if(checkStringForEmptyOrSpaces(feedback_userinfo.value) || checkStringForEmptyOrSpaces(feedback_textarea.value)){
		event.preventDefault();
		feedback_message_box_paragraph.innerHTML = "Niste upisali naslov objave ili tekst objave.";
		main_user_feedback_input_wrapper.classList.toggle('annex');
		feedback_message_box.classList.toggle('open');

	}
 }
 
 function toggleFeedbackMessageBox(){
	var feedback_message_box = document.getElementById("feedback_message_box");
	var main_user_feedback_input_wrapper = document.getElementById("main_user_feedback_input_wrapper");
	main_user_feedback_input_wrapper.classList.toggle('annex');
	feedback_message_box.classList.toggle('open');
	
 }
 
function checkStringForEmptyOrSpaces(text){	
	var str = text.trim();
    return str === "" || str === null;
}








