const trigger = document.getElementById("book_scroll_menu_div");
const target = document.getElementById("book_chapter_holder");

let highlightAnimation;

trigger.addEventListener("click", () => {

    highlightAnimation?.cancel();

    highlightAnimation = target.animate(
        [
			{ backgroundColor: getComputedStyle(target).backgroundColor },
			{ backgroundColor: "#DFE3E6", offset: 0.25 },
			{ borderRadius: "25px"},
			{ backgroundColor: getComputedStyle(target).backgroundColor }
			
            /*{ boxShadow: "inset 0 0 0 3px transparent" },
            { boxShadow: "inset 0 0 0 3px #ffbf00", offset: 0.25 },
            { boxShadow: "inset 0 0 0 3px transparent" }*/
        ],
        {
            duration: 900,
            easing: "ease-out"
        }
    );
});