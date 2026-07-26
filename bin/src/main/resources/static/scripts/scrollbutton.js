/**
 * 
 */		
window.onscroll = function() {scrollFunction()};
	
function scrollFunction(){
	var topbtn = document.getElementById('topBtn');
	
	if(document.body.scrollTop > 50 || document.documentElement.scrollTop > 50){
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