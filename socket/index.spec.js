const io = require("socket.io-client");

var socket = io('http://localhost:3000'); // Server endpoint
socket.on('connect', connectUser);
socket.on('message2', function (data) {
 console.log(data);
});

function connectUser () { // Called whenever a user signs in
 var userId = 123
 if (!userId) return;
 socket.emit('userConnected', userId);
}
function disconnectUser () { // Called whenever a user signs out


 socket.emit('event', {room: 'message2',
 message: 'messag'});
}
disconnectUser()