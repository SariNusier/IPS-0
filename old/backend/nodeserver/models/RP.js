var db = require('../db');

var schema = new db.Schema({
	rpid: {type: String, required: true},
	building_id: {type: String, required: true},
	coordinate: {
		x: Number,
		y: Number
	}
});

var rp = db.model('rp', schema);
module.exports = rp;