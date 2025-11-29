/**
 * 
 */

 function closeMenu(){
	
	try{
		var hamburgerToggle = document.getElementById("hamburger_toggle");
		var hamburgerMenu = document.getElementById("hamburger_menu");
		
		hamburgerToggle.checked = false;
		hamburgerMenu.style.height = "0px";
	}
	catch(e){
		
	}
	
	
 }