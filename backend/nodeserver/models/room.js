var db = require('../db');

var Rectangle = {
		lt:{
			x: Number,
			y: Number,
		},
		rt:{
			x: Number,
			y: Number,
		},
		lb:{
			x: Number,
			y: Number,
		},
		rb:{
			x: Number,
			y: Number,
		}
};

var schema = new db.Schema({
	rectangle: Rectangle,
	name: {type: String, required: true},
	width: {type: Number, required: true},
	length:{type: Number, required: true},
	floor: Number,
	est_time: {type: Number, default: 1},
	N_avg: {type: Number,default:1},
	building_id: {type: String, required: true}
});

var room = db.model('room', schema);
module.exports = room;