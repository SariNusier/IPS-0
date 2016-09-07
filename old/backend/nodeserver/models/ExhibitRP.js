var db = require('../db');

var schema = new db.Schema({
	rpid: {type: String, required: true},
	room_id: {type: String, required: true},
	name: {type: String, required: true}
});

var erp = db.model('erp', schema);
module.exports = erp;