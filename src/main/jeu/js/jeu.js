document.addEventListener("DOMContentLoaded", function(_e) {
	
	let overlay = document.getElementById("overlay");
	let jeu = document.getElementById("jeu");
	
	let ctxoverlay = overlay.getContext("2d");
	let ctxjeu = jeu.getContext("2d");
	
	ctxoverlay.fillStyle = 'blue';
	posb = 50;
	ctxoverlay.fillRect(posb, 800, 80, 70);
	
	ctxoverlay.fillStyle = 'red';
	posr = 870;
	ctxoverlay.fillRect(posr, 800, 80, 70);
	
	window.onkeydown = function(e) {
		var key = e.keyCode || e.which;
		if (key == 37 && posb > 0) {
			ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
			posb = posb-10;
			ctxoverlay.fillStyle = 'blue';
			ctxoverlay.fillRect(posb, 800, 80, 70);
			ctxoverlay.fillStyle = 'red';
			ctxoverlay.fillRect(posr, 800, 80, 70);
		}
		if (key == 39 && posb < 420) {
			ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
			posb = posb+10;
			ctxoverlay.fillStyle = 'blue';
			ctxoverlay.fillRect(posb, 800, 80, 70);
			ctxoverlay.fillStyle = 'red';
			ctxoverlay.fillRect(posr, 800, 80, 70);
		}
		if (key == 81 && posr > 500) {
			ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
			posr = posr-10;
			ctxoverlay.fillStyle = 'red';
			ctxoverlay.fillRect(posr, 800, 80, 70);
			ctxoverlay.fillStyle = 'blue';
			ctxoverlay.fillRect(posb, 800, 80, 70);
		}
		if (key == 68 && posr < 920) {
			ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
			posr = posr+10;
			ctxoverlay.fillStyle = 'red';
			ctxoverlay.fillRect(posr, 800, 80, 70);
			ctxoverlay.fillStyle = 'blue';
			ctxoverlay.fillRect(posb, 800, 80, 70);
		}
	};

});