var db = require('../db');
var building = db.model('building', {
	name: {type: String, required: true},
	id: {type: String, required: true},
});
module.exports = building;