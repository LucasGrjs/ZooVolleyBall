document.addEventListener("DOMContentLoaded", function(_e) {
	
	let overlay = document.getElementById("overlay");
	let jeu = document.getElementById("jeu");
	
	let ctxoverlay = overlay.getContext("2d");
	let ctxjeu = jeu.getContext("2d");
	
	ctxoverlay.fillStyle = 'blue';
	posx = 50;
	ctxoverlay.fillRect(posx, 800, 80, 70);
	
	window.onkeydown = function(e) {
		var key = e.keyCode || e.which;
		switch (key) {
			case 37:
			if (posx>0) {
				ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
				posx = posx-5;
				ctxoverlay.fillRect(posx, 800, 80, 70);
			}
			break;
		case 39:
			if (posx<920) {
				ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
				posx = posx+5;
				ctxoverlay.fillRect(posx, 800, 80, 70);
			}
			break;
		default:
			break;
    }
};

});