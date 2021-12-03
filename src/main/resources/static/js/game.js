var stompClient = null;
var uid = randString(32);

function randString(length) {
	var text = "";
	var alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	for (var i = 0; i < length; i++) {
		text += alphanum.charAt(Math.floor(Math.random() * alphanum.length));
	}

	return text;
}

function connect() {
	console.log("connect function");
    var socket = new SockJS('/zvb-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/zvb/replyjoin', function(replyOutput) {
        	console.log("replyjoin function : " + JSON.parse(replyOutput.body).gameId);
			var gameId = JSON.parse(replyOutput.body).gameId;

			stompClient.subscribe('/zvb/move/' + gameId, function (data) {
				console.log(JSON.parse(data.body));
			});
			
			stompClient.send("/zvb/connected/" + gameId, {}, {});
        });
        stompClient.send("/zvb/join", {}, JSON.stringify({'player': uid}));
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

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