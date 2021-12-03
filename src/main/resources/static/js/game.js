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