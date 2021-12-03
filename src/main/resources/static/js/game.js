var stompClient = null;
var sessionId = randString(32);

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
    var socket = new SockJS('/zvb-websocket', [], {
	    sessionId: () => {
	       return sessionId
	    }
 	});
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/zvb/game/replyjoin/' + sessionId, function(replyOutput) {
        	console.log("replyjoin function");

			if (JSON.parse(replyOutput.body).error)
			{
				console.log("ERROR : " + JSON.parse(replyOutput.body).errorMessage);
			}
			else
			{
				var gameId = JSON.parse(replyOutput.body).gameId;
				
				stompClient.subscribe('/zvb/game/init/' + gameId, function (data) {
					console.log(JSON.parse(data.body));
				});
	
				stompClient.subscribe('/zvb/game/move/' + gameId, function (data) {
					console.log(JSON.parse(data.body));
				});
				
				stompClient.send("/zvb/game/connected/" + gameId, {}, {});
			}
        });
        stompClient.send("/zvb/game/join", {}, JSON.stringify({'gameId': 10}));
    });
}

function disconnect() {
	console.log("Disconnected " + stompClient);
	
    if(stompClient != null) stompClient.disconnect();
}

document.addEventListener("DOMContentLoaded", function(_e) {
	connect();
	
	let overlay = document.getElementById("overlay");
	let jeu = document.getElementById("jeu");
	
	let ctxoverlay = overlay.getContext("2d");
	let ctxjeu = jeu.getContext("2d");
	
	let posb = 50;
	
	let fillRect = function()
	{
		ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);
		ctxoverlay.fillStyle = 'blue';
		ctxoverlay.fillRect(posb, 800, 80, 70);
	};
	
	fillRect();
	
	window.onkeydown = function(e) {
		var key = e.keyCode || e.which;
		if (key == 37 && posb > 0)
		{
			posb -= 10;
			fillRect();
		}
		if (key == 39 && posb < 420)
		{
			posb += 10;
			fillRect();
		}
	};
});

window.onunload = function() {
	disconnect();
}