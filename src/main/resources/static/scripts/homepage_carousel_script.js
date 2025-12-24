/**
 * 
 */

let carouselIntervalId = null;

document.addEventListener('DOMContentLoaded', function() {
	
	
	try{
		var gallery = document.getElementById("impressions_gallery_window");
		if(gallery){
			runCarousel();
			carouselIntervalId = setInterval(runCarousel, 5000);
		}
	}
	catch{
		/* IGNORE IF GALLERY ISN'T FOUND */
	}
	
	
	
});



function dotSelector(elem){
	var dotIndex = Array.prototype.indexOf.call(elem.parentNode.children, elem);
	var iteratorElement = document.getElementById("gallery_iterator");
	iteratorElement.value = dotIndex;
	
	stopCarousel();
	spinCarousel();	
	resumeCarousel();

}

function clearTargetWindow(){
	
	const feedbackIdElements = document.querySelectorAll("#impressions_gallery_window [data-id]");
	const feedbackIds = Array.from(feedbackIdElements).map(elements => Number(elements.dataset.id)).filter(id =>  !Number.isNaN(id));
	
	feedbackIds.forEach( id => {
		var clearWindow = document.getElementById(id);
		var dotElement = document.getElementById('dot_' + id);		
		clearWindow.classList.remove("open");
		dotElement.classList.remove("open");
	});
}

function increaseIterator(){
	var iteratorElement = document.getElementById("gallery_iterator");
	iteratorElement.value = Number(iteratorElement.value) + 1;
}

function runCarousel(){	
	
	var iteratorElement = document.getElementById("gallery_iterator");
	const mainColorArray = ["#F1CD69","#7BBE6E", "#918AD8", "#CA4B97"];
	const feedbackIdElements = document.querySelectorAll("#impressions_gallery_window [data-id]");
	const feedbackIds = Array.from(feedbackIdElements).map(elements => Number(elements.dataset.id)).filter(id =>  !Number.isNaN(id));
	var gallery = document.getElementById("impressions_gallery_window");

	var randomColorPicker = mainColorArray[Math.floor(Math.random() * mainColorArray.length)];
	
	var targetWindow = document.getElementById(feedbackIds[iteratorElement.value]);
	var dotElement = document.getElementById('dot_' + feedbackIds[iteratorElement.value]);
	
	clearTargetWindow();
	
	targetWindow.classList.add("open");
	dotElement.classList.add("open");
	gallery.style.backgroundColor = randomColorPicker;
	
	iteratorElement.value = Number(iteratorElement.value) + 1;
	if(Number(iteratorElement.value) >= feedbackIds.length){
		iteratorElement.value = 0;
	}
	
}

function spinCarousel(){
	var iteratorElement = document.getElementById("gallery_iterator");
	const mainColorArray = ["#F1CD69","#7BBE6E", "#918AD8", "#CA4B97"];
	const feedbackIdElements = document.querySelectorAll("#impressions_gallery_window [data-id]");
	const feedbackIds = Array.from(feedbackIdElements).map(elements => Number(elements.dataset.id)).filter(id =>  !Number.isNaN(id));
	var gallery = document.getElementById("impressions_gallery_window");

	var randomColorPicker = mainColorArray[Math.floor(Math.random() * mainColorArray.length)];
	
	var targetWindow = document.getElementById(feedbackIds[iteratorElement.value]);
	var dotElement = document.getElementById('dot_' + feedbackIds[iteratorElement.value]);
	
	clearTargetWindow();
	
	targetWindow.classList.add("open");
	dotElement.classList.add("open");
	gallery.style.backgroundColor = randomColorPicker;
}

function stopCarousel(){
	if(carouselIntervalId !== null){
		clearInterval(carouselIntervalId);
		carouselIntervalId = null;
	}
}

function resumeCarousel(){
	if(carouselIntervalId !== null) {
		return;
	}	
	carouselIntervalId = setInterval(runCarousel, 5000);
}

document.addEventListener('DOMContentLoaded', () => {
	
	var iteratorElement = document.getElementById("gallery_iterator");
	var previousButton = document.getElementById("gallery_previous");
	const nextButton = document.getElementById("gallery-next");
	const feedbackIdElements = document.querySelectorAll("#impressions_gallery_window [data-id]");
	const feedbackIds = Array.from(feedbackIdElements).map(elements => Number(elements.dataset.id)).filter(id =>  !Number.isNaN(id));

	previousButton.addEventListener("click", () => {
		stopCarousel();		
	
		iteratorElement.value = Number(iteratorElement.value) - 1;
		
		if(Number(iteratorElement.value) === -1){
			iteratorElement.value = feedbackIds.length - 1;
		}
		
		spinCarousel();
	
		resumeCarousel();
	/*
		setTimeout( () => {
			resumeCarousel();
		}, 5000);	*/
	});
	
	nextButton.addEventListener("click", () => {
		stopCarousel();
		
		iteratorElement.value = Number(iteratorElement.value) + 1;
		
		if(Number(iteratorElement.value) >= feedbackIds.length){
			iteratorElement.value = 0;
		}
		
		spinCarousel();
		
		resumeCarousel();
	});
	
});












