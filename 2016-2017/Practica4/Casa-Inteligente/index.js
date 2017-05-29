// express inicializa la app
var app = require('express')();

// la servimos como un servicio http
var http = require('http').Server(app);

// inicio socket.io con el servicio http
var socketio = require('socket.io')(http);

// mongoDB
var url = require("url");
var fs = require("fs");
var path = require("path");
var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongodb').Server;
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", "jpg": "image/jpeg", "png": "image/png", "js": "text/javascript", "css": "text/css", "swf": "application/x-shockwave-flash"};

http.listen(8080);
var io = socketio.listen(http);

var mongoClient = new MongoClient(new MongoServer('localhost', 27017));
// Connection URL
var url = 'mongodb://localhost:27017/myproject';
// Use connect method to connect to the Server
MongoClient.connect(url, function(err, mongoClient) {
  	console.log("Connected correctly to server");
	var db = mongoClient.db("mibd");
	
	db.createCollection("usuarios", function(err, collection){
    	io.sockets.on('connection',
		function(client) {
			client.emit('my-address', client.handshake.address);
			client.on('poner', function (data) {
				collection.insert(data, {safe:true}, function(err, result) {});
			});
			client.on('obtener', function (data) {
				collection.find(data).toArray(function(err, results){
					client.emit('obtener', results);
				});
			});
		});
    });	
});

console.log("Servicio MongoDB iniciado");

// definimos una ruta que sirva como bienvenida a la pagina index.html
app.get('/', function(req, res){
  	res.sendFile(__dirname + '/index.html');
});

// escucho el evento connection para conexiones entrantes y las muestro por consola
socketio.on('connection', function(socket){
  	console.log('a user connected');

  	// si nos llega un evento cortina lo enviamos a todos los que esten conectados y lo mostramos por consola
  	socket.on('curtain on-off', function(onoff){
  		socketio.emit('curtain on-off', onoff);
    	console.log('curtain: ' + onoff);
  	});

	// si nos llega un evento aire acondicionado lo enviamos a todos los que esten conectados y lo mostramos por consola
  	socket.on('a-c on-off', function(onoff){
  		socketio.emit('chat message', onoff);
    	console.log('air conditioner: ' + onoff);
  	});

	// si nos llega un evento luminosidad lo enviamos a todos los que esten conectados y lo mostramos por consola
  	socket.on('luminosity', function(lum){
  		socketio.emit('luminosity', lum);
    	console.log('luminosity: ' + lum);
  	});

	// si nos llega un evento humedad lo enviamos a todos los que esten conectados y lo mostramos por consola
  	socket.on('humidity', function(hum){
  		socketio.emit('humidity', hum);
    	console.log('humidity: ' + hum);
  	});
		
	socket.on('my-address', function(data) {
		var d = new Date();
		socketio.emit('poner', {host: data.address, port:data.port, time:d});
		socketio.emit('obtener', {host: data.address});
	});

	socket.on('output-evt', function(data) {
		mostrar_mensaje('Mensaje de servicio: '+ data);
	});
});

function mostrar_mensaje(msg){
		var span_msg = document.getElementById('mensaje_servicio');
		span_msg.innerHTML = msg;
}

var allClients = new Array();
io.sockets.on('connection',
	function(client) {
		allClients.push(client.handshake.address);
		io.sockets.emit('all-connections', allClients);
		client.on('output-evt', function (data) {
			client.emit('output-evt', 'Hola Cliente!');
		});
		client.on('disconnect', function() {
			var index = allClients.indexOf(client.handshake.address);
			if (index != -1) {
				allClients.splice(index, 1);
				io.sockets.emit('all-connections', allClients);
			}
			console.log('El usuario '+client.handshake.address+' se ha desconectado');
		});
	}
);

console.log("Servicio Socket.io iniciado");

// ponemos el servicio a la escucha en el puerto 3000
http.listen(3000, function(){
  	console.log('listening on *:3000');
});
