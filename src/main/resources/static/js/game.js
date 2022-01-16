var stompClient = null;
var sessionId = randString(32);
var ctxoverlay = null;
var overlay = null;
var gameId = null;
let contentSearch;
let contentGame;

var gameIntervalObject;



var skin1 = new Image();
var skin2 = new Image();

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

                stompClient.subscribe('/user/game/init', function (data) {
                    let replyActionMessage = JSON.parse(data.body);
                    console.log("RAM init = "+replyActionMessage);
                    fillRect(replyActionMessage.xJ1,replyActionMessage.yJ1,replyActionMessage.xJ2,replyActionMessage.yJ2,replyActionMessage.xBall,replyActionMessage.yBall,replyActionMessage.skinJ1,replyActionMessage.skinJ2);
                });

                stompClient.subscribe('/user/game/move', function (data) {
                    // recup le json envoyé par le serveur et update l'affichage
                    let replyActionMessage = JSON.parse(data.body);
                    console.log("RAM MOVE = "+replyActionMessage);

                    fillRect(replyActionMessage.xJ1,replyActionMessage.yJ1,replyActionMessage.xJ2,replyActionMessage.yJ2,replyActionMessage.xBall,replyActionMessage.yBall,replyActionMessage.skinJ1,replyActionMessage.skinJ2);
                    //updatePlayers(replyActionMessage);
                });

                stompClient.subscribe('/user/game/jump', function (data) {
                    let replyActionMessage = JSON.parse(data.body);
                    console.log(replyActionMessage);
                    updatePlayers(replyActionMessage);
                });

                stompClient.subscribe('/user/game/moveBall', function (data) {
                    let replyActionMessage = JSON.parse(data.body);
                    console.log("RAM MOVEBALL = "+replyActionMessage);
                    updateBall(replyActionMessage);
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
    let xBall = replyActionMessage.xBall;
    let yBall = replyActionMessage.yBall;
    let skinJ1=replyActionMessage.skinJ1;
    let skinJ2=replyActionMessage.skinJ2;


    if(replyActionMessage.idJoueurInAction === sessionId){
        if(replyActionMessage.velocityYJ1 !=0){ // Ne faire ça que pour le joueur qui a sauté (sinon on le fait en double)
            setTimeout(function () {
                stompClient.send("/zvb/game/jump", {}, JSON.stringify({'action': 'enSaut', 'gameId':gameId}));
            },20);
        }
        if(replyActionMessage.velocityYJ2 !=0){ // Ne faire ça que pour le joueur qui a sauté (sinon on le fait en double)
            setTimeout(function () {
                stompClient.send("/zvb/game/jump", {}, JSON.stringify({'action': 'enSaut', 'gameId':gameId}));
            },20);
        }
    }

    fillRect(xJ1,yJ1,xJ2,yJ2,xBall,yBall,skinJ1,skinJ2);
}

function updateBall(replyActionMessage){
    let xJ1=replyActionMessage.xJ1;
    let yJ1=replyActionMessage.yJ1;
    let xJ2=replyActionMessage.xJ2;
    let yJ2=replyActionMessage.yJ2;
    let xBall = replyActionMessage.xBall;
    let yBall = replyActionMessage.yBall;
    let skinJ1=replyActionMessage.skinJ1;
    let skinJ2=replyActionMessage.skinJ2;


    if(replyActionMessage.idJoueurInAction === sessionId){
        setTimeout(function () {
            stompClient.send("/zvb/game/moveBall", {}, JSON.stringify({'gameId':gameId}));
        },20);
    }

    fillRect(xJ1,yJ1,xJ2,yJ2,xBall,yBall,skinJ1,skinJ2);
}

function fillRect(xJ1,yJ1,xJ2,yJ2,xBall,yBall,skinJ1,skinJ2){

    ctxoverlay.clearRect(0, 0, overlay.width, overlay.height);// effacer tout la balle avec
    ctxoverlay.fillStyle = 'blue';
    //ctxoverlay.fillRect(xJ1, yJ1, 80, 70);
    ctxoverlay.beginPath();
    ctxoverlay.ellipse(xJ1, yJ1, 40, 70, 0, 0, Math.PI,true); // y est 2* plus petit que x car le canva est étiré
    ctxoverlay.fill();

    ctxoverlay.fillStyle = 'red';
    //ctxoverlay.fillRect(xJ2, yJ2, 80, 70);
    ctxoverlay.beginPath();
    ctxoverlay.ellipse(xJ2, yJ2, 40, 70, 0, 0, Math.PI,true); // y est 2* plus petit que x car le canva est étiré
    ctxoverlay.fill();

    ctxoverlay.fillStyle = 'black';
    ctxoverlay.beginPath();
    ctxoverlay.ellipse(xBall, yBall, 25, 48, 0, 0, 2*Math.PI); // y est 2* plus petit que x car le canva est étiré
    ctxoverlay.fill();

    /*skin1.src = skinJ1;
    skin2.src = skinJ2;

    console.log("XXXXXXXXXXXXXXXXXXXXXX J1 "+skinJ1);
    console.log("XXXXXXXXXXXXXXXXXXXXXX J2 "+skinJ2);

    ctxoverlay.drawImage(skin1, xJ1, yJ1, 80, 70);
    ctxoverlay.drawImage(skin2, xJ2, yJ2, 80, 70);*/
    // todo draw selected skin of players
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

    fillRect(250,800,550,800,"../images/Lion.png","../images/Lion.png");


    var keysMap = {};
    window.addEventListener("keydown", function(e) {
        // space and arrow keys
        if([32, 37, 38, 39, 40].indexOf(e.keyCode) > -1) {
            e.preventDefault();
        }
        keysMap[e.keyCode] = true;
    }, false);
    window.addEventListener("keyup", function(e) {
        keysMap[e.keyCode] = false;
    }, false);

    setInterval(function(){
        if (keysMap[37] && !keysMap[39]) // fleche gauche sans fleche droite
        {
            // requete demande de mouvement vers la gauche au serveur
            console.log("= GAUCHE =");
            stompClient.send("/zvb/game/move", {}, JSON.stringify({'action': 'gauche', 'gameId':gameId}));
        }
        if (keysMap[39] && !keysMap[37]) // fleche droite sans fleche gauche
        {
            // requete demande de mouvement vers la droite au serveur
            console.log("= DROITE =");
            stompClient.send("/zvb/game/move", {}, JSON.stringify({'action': 'droite', 'gameId':gameId}));
        }
        if(keysMap[38]){ // fleche du dessus
            // requete demande de saut au serveur
            console.log("==================== SAUT ====================");
            stompClient.send("/zvb/game/jump", {}, JSON.stringify({'action': 'saut', 'gameId':gameId}));
        }
    },20);

    //gameIntervalObject = setInterval(gameIteration, 20);
});

window.onunload = function() {
    disconnect();
}
