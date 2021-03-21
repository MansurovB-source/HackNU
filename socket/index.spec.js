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

var q = 'events';

var open = require('amqplib').connect('amqp://localhost');

// Publisher
open.then(function(conn) {
    return conn.createChannel();
  }).then(function(ch) {
    return ch.assertQueue('sockets').then(function(ok) {
      return ch.sendToQueue('sockets', Buffer.from(JSON.stringify({room: 'message2',
      message: 'messahljlkjg'})));
    });
  }).catch(console.warn);
// Consumer
open.then(function(conn) {
  return conn.createChannel();
}).then(function(ch) {
  return ch.assertQueue(q).then(function(ok) {
    return ch.consume(q, function(msg) {
      if (msg !== null) {
        console.log(JSON.parse(msg.content));
        ch.ack(msg);
      }
    });
  });
}).catch(console.warn);