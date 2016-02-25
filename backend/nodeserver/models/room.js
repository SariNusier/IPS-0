var db = require('../db');
var room = db.model('room', {
	name: {type: String, required: true},
	id: {type: String, required: true},
});
module.exports = room;