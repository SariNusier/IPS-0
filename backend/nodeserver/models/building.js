var db = require('../db');
var buildings = db.model('building', {
	name: {type: String, required: true},
	id: {type: String, required: true},
});
module.exports = buildings;