
var mongoose = require('mongoose');

var dbURL = 'mongodb://localhost:27017/test';


mongoose.connect(dbURL, function(){
	console.log('db connected');
});
module.exports = mongoose;
