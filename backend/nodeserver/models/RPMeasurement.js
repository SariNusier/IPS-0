var db = require('../db');

var schema = new db.Schema({
	RPID:  {type: String, required: true},
	value: {type: Number, required: true},
	room_id: {type: String, required: true}
});

var RPMeasurement = db.model('RPMeasurement', schema);
module.exports = RPMeasurement;