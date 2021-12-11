var stompClient = null;
var sessionId = randString(32);
var ctxoverlay = null;
var overlay = null;
var gameId = null;
let contentSearch;
let contentGame;

var gameIntervalObject;

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
    stompClient.connect({name: sessionId}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.send("/zvb/findcasual/join", {}, JSON.stringify({'gameId': 0}));

        stompClient.subscribe('/user/findcasual/replyjoin', function(replyOutput) {
            console.log("==================== Found match");
            let replyActionMessage = JSON.parse(replyOutput.body);
            console.log(replyActionMessage);
            contentSearch.hidden = true;
            contentGame.hidden = false;
            stompClient.send("/zvb/game/join", {}, JSON.stringify({'gameId': replyActionMessage.gameId}));
            console.log("==================== ");

        });

        stompClient.subscribe('/user/game/replyjoin', function(replyOutput) {
            console.log("replyjoin function");

            if (JSON.parse(replyOutput.body).error)
            {
                console.log("ERROR : " + JSON.parse(replyOutput.body).errorMessage);
            }
            else
            {
                gameId = JSON.parse(replyOutput.body).gameId;

                console.log("replyjoin function gameId : " + gameId);

                stompClient.subscribe('/zvb/game/init/' + gameId, function (data) {
                    console.log(JSON.parse(data.body));
                });

                stompClient.subscribe('/user/game/move', function (data) {
                    // recup le json envoyé par le serveur et update l'affichage
                    let replyActionMessage = JSON.parse(data.body);
                    console.log(replyActionMessage);

                    //fillRect(replyActionMessage.xJ1,replyActionMessage.yJ1,replyActionMessage.xJ2,replyActionMessage.yJ2);
                    updatePlayers(replyActionMessage);
                });

                stompClient.send("/zvb/game/connected/" + gameId, {}, {});
            }
        });
    });
}

function disconnect() {
    console.log("Disconnected " + stompClient);

    if(stompClient != null) stompClient.disconnect();
}

function updatePlayers(replyActionMessage){
    let xJ1=replyActionMessage.xJ1;
    let yJ1=replyActionMessage.yJ1;
    let xJ2=replyActionMessage.xJ2;
    let yJ2=replyActionMessage.yJ2;
    if(replyActionMessage.velocityYJ1 !=0){
        stompClient.send("/zvb/game/move", {}, JSON.stringify({'action': 'enSaut', 'gameId':gameId}));
    }
    fillRect(xJ1,yJ1,xJ2,yJ2);
}

function fillRect(xJ1,yJ1,xJ2,yJ2){
    ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);// effacer tout la balle avec
    ctxoverlay.fillStyle = 'blue';
    ctxoverlay.fillRect(xJ1, yJ1, 80, 70);

    ctxoverlay.fillStyle = 'red';
    ctxoverlay.fillRect(xJ2, yJ2, 80, 70);
}



document.addEventListener("DOMContentLoaded", function(_e) {

    overlay = document.getElementById("overlay");
    let jeu = document.getElementById("jeu");

    ctxoverlay = overlay.getContext("2d");
    let ctxjeu = jeu.getContext("2d");

    contentSearch = document.getElementById("contentSearch");
    contentGame = document.getElementById("contentGame");
    contentGame.hidden = true;

    connect();

    let posb = 50;

    fillRect(250,800,550,800);

    window.addEventListener("keydown", function(e) {
        // space and arrow keys
        if([32, 37, 38, 39, 40].indexOf(e.keyCode) > -1) {
            e.preventDefault();
        }
    }, false);

    window.onkeydown = function(e) {
        var key = e.keyCode || e.which;
        if (key == 37 && posb > 0)
        {
            // requete demande de mouvement vers la gauche au serveur
            stompClient.send("/zvb/game/move", {}, JSON.stringify({'action': 'gauche', 'gameId':gameId}));
        }
        if (key == 39 && posb < 420)
        {
            // requete demande de mouvement vers la droite au serveur
            stompClient.send("/zvb/game/move", {}, JSON.stringify({'action': 'droite', 'gameId':gameId}));
        }
        if(key == 38){ // key up
            // requete demande de saut au serveur
            console.log("========== SAUT ==========")
            stompClient.send("/zvb/game/move", {}, JSON.stringify({'action': 'saut', 'gameId':gameId}));
        }
    };

    //gameIntervalObject = setInterval(gameIteration, 20);
});

window.onunload = function() {
    disconnect();
}
