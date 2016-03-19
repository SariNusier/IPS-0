var db = require('../db');


var schema = new db.Schema({
	rpv_pair: [{ RPID: String, value: Number }],
	room_id: {type: String, required: true}
});

var RPMeasurement = db.model('RPMeasurement', schema);
module.exports = RPMeasurement;