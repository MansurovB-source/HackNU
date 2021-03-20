var server = require('http').createServer();

var io = require('socket.io')(server);

io.on('connection', function(socket){

  socket.on('event', function(data){
      console.log(data)
      socket.broadcast.emit(data.room, data)
   });

  socket.on('disconnect', function(){
      
  });
});

server.listen(3000, function(){
    console.log('started')
});
 