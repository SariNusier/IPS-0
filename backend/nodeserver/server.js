
var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');

var express = require('express');
var app = express();
var bodyParser = require('body-parser');

var ObjectId = require('mongodb').ObjectId;
var dbURL = 'mongodb://localhost:27017/test';


   var found = [];


app.use(bodyParser.json());

app.get('/api/posts', function(req, res) {

});

var Building = require('./models/building');

app.get('/db/buildings/:id', function(req, res) {
	var newBuilding = new Building({name:'FirstBuilding', id:'1'});
	newBuilding.save(function (err){
		if(err){console.log('error adding building')}
		console.log('Added building');
	})
	res.send('Added building'+newBuilding);
});

app.get('/db/buildings', function(req, res) {
	MongoClient.connect(dbURL, function(err, db) {
		res.send(findRestaurants(db, function(){}, 'a'));
	});
});

app.get('/wines/:id', function(req, res) {
    res.send({id:req.params.id, name: "The Name", description: "description"});
});

app.listen(3000);
console.log('Listening on port 3000...');



function connectDB(id){

	MongoClient.connect(dbURL, function(err, db) {
	assert.equal(null,err);
	console.log("Connected!");
	return findRestaurants(db, function() {
      db.close();
  }, id);
});

}

var insertDocument = function(db, callback) {
   db.collection('restaurants').insertOne( {
      "address" : {
         "street" : "2 Avenue",
         "zipcode" : "10075",
         "building" : "1480",
         "coord" : [ -73.9557413, 40.7720266 ]
      },
      "borough" : "Manhattan",
      "cuisine" : "Italian",
      "grades" : [
         {
            "date" : new Date("2014-10-01T00:00:00Z"),
            "grade" : "A",
            "score" : 11
         },
         {
            "date" : new Date("2014-01-16T00:00:00Z"),
            "grade" : "B",
            "score" : 17
         }
      ],
      "name" : "Vella",
      "restaurant_id" : "41704620"
   }, function(err, result) {
    assert.equal(err, null);
    console.log("Inserted a document into the restaurants collection.");
    callback();
  });
};

var findRestaurants = function(db, callback, id) {

   var cursor = db.collection('restaurants').find({name: 'Subway'});

   cursor.rewind();
   cursor.each(function(err, doc) {
      assert.equal(err, null);
      if (doc != null) {
         //console.log(doc);
         found.push(doc);
         //found.push(doc);
      } else {
       //  callback();
      }
   });
   console.log(found);
   return found;
};

var updateRestaurants = function(db, callback) {
   db.collection('restaurants').updateOne(
      { "name" : "Juni" },
      {
        $set: { "cuisine": "American (New)" },
        $currentDate: { "lastModified": true }
      }, function(err, results) {
      console.log(results);
      callback();
   });
};

var removeRestaurants = function(db, callback) {
   db.collection('restaurants').deleteMany(
      { "borough": "Manhattan" },
      function(err, results) {
         console.log(results);
         callback();
      }
   );
};

var insertIntoDatabase = function() {

};
