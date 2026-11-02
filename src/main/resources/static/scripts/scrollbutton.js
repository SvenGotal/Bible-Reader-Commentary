/**
 * 
 */		
window.onscroll = function() {scrollFunction()};
	
function scrollFunction(){
	
	try{
		var topbtn = document.getElementById('go_to_top_button');		
	}
	catch{
		//do nothing
		console.log("top btn is null");
	}
		var topbtn2 = document.getElementById('go_to_top_button_upper');
	
	if(document.body.scrollTop > 80 || document.documentElement.scrollTop > 80){
		if(topbtn !== null) topbtn.style.display = "block";
		topbtn2.style.display = "block";
	}
	else{
		if(topbtn !== null) topbtn.style.display = "none";
		topbtn2.style.display = "none";
	}
}
			
function goToTop() {
  
  document.body.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
}