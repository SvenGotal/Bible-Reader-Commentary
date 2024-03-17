/**
 * 
 */		
window.onscroll = function() {scrollFunction()};
	
function scrollFunction(){
	var topbtn = document.getElementById('topBtn');
	
	if(document.body.scrollTop > 20 || document.documentElement.scrollTop > 20){
		topbtn.style.display = "block";
	}
	else{
		topbtn.style.display = "none";
	}
}
			
function goToTop() {
  
  document.body.scrollIntoView({
			behavior: 'smooth',
			block: 'start'
		});
}