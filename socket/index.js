var server = require('http').createServer();
var q = 'events';
var w = 'sockets';
var open = require('amqplib').connect('amqp://localhost');
var io = require('socket.io')(server, {
        cors: {
            origin: "*"
        }
});

io.on('connection', function(socket){

  socket.on('event', function(data){
      console.log(data)
      socket.broadcast.emit(data.room, data)
      open.then(function(conn) {
        return conn.createChannel();
      }).then(function(ch) {
        return ch.assertQueue(q).then(function(ok) {
          return ch.sendToQueue(q, Buffer.from(JSON.stringify(data)));
        });
      }).catch(console.warn);
    });

    open.then(function(conn) {
        return conn.createChannel();
      }).then(function(ch) {
        return ch.assertQueue(w).then(function(ok) {
          return ch.consume(w, function(msg) {
            if (msg !== null) {
                try{
                ch.ack(msg);
                const data = JSON.parse(msg.content);
              console.log(data);
              socket.emit(data.room, data)
                } catch(err) {
                    console.log(err)
                }
            }
          });
        });
      }).catch(console.warn);

  socket.on('disconnect', function(){
      
  });
});

server.listen(3000, function(){
    console.log('started')
});
 