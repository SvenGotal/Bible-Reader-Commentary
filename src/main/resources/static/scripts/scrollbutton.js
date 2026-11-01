/**
 * 
 */		
window.onscroll = function() {scrollFunction()};
	
function scrollFunction(){
	var topbtn = document.getElementById('go_to_top_button');
	var topbtn2 = document.getElementById('go_to_top_button_upper');
	
	if(document.body.scrollTop > 80 || document.documentElement.scrollTop > 80){
		topbtn.style.display = "block";
		topbtn2.style.display = "block";
	}
	else{
		topbtn.style.display = "none";
		topbtn2.style.display = "none";
	}
}
			
function goToTop() {
  
  document.body.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
}