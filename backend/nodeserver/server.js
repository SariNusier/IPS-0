var express     =   require("express");
var app         =   express();
var bodyParser  =   require("body-parser");
var router      =   express.Router();
var Building    =   require('./models/building');
var Room        =   require('./models/room');
var RP          =   require('./models/RP');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({"extended" : false}));

router.get("/",function(req,res){
    res.json({"error" : false,"message" : "Hello World"});
});
 app.use('/', router);
//route() will allow you to use same path for different HTTP operation.
//So if you have same URL but with different HTTP OP such as POST,GET etc
//Then use route() to remove redundant code.

router.route("/buildings")
    .get(function(req,res){
        var response = {};
        Building.find({},function(err,data){
        // Mongo command to fetch all data from collection.
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
                response = data;
            }
            res.json(response);
        });
    }).post(function(req,res){
        var db = new Building();
        var response = {};
        console.log("Post");
        console.log(req);
        // fetch email and password from REST request.
        // Add strict validation when you use this in Production.
        db.rectangle.lt = req.body.rectangle.lt;
        db.rectangle.rt = req.body.rectangle.rt;
        db.rectangle.lb = req.body.rectangle.lb;
        db.rectangle.rb = req.body.rectangle.rb;
        db.name = req.body.name;
        db.width = req.body.width;
        db.height = req.body.height; 
        // Hash the password using SHA1 algorithm.
        //db.id =  req.body.id;
        db.save(function(err){
        // save() will run insert() command of MongoDB.
        // it will add new data in collection.
            if(err) {
                response = {"error" : true,"message" : "Failed!"};
                console.log(err);
            } else {
                response = {"error" : false,"message" : "Data added"};
            }
            res.json(response);
        });
    });

    router.route("/buildings/:id")
    .get(function(req,res){
        var response = {};
        Building.findById(req.params.id,function(err,data){
        // This will run Mongo Query to fetch data based on ID.
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
                response = data;
            }
            res.json(response);
        });
    })
    .put(function(req,res){
        var response = {};
        // first find out record exists or not
        // if it does then update the record
        Building.findById(req.params.id,function(err,data){
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
            // we got data from Mongo.
            // change it accordingly.
                if(req.body.rectangle !== undefined){
                    data.rectangle.lt = req.body.rectangle.lt;
                    data.rectangle.rt = req.body.rectangle.rt;
                    data.rectangle.lb = req.body.rectangle.lb;
                    data.rectangle.rb = req.body.rectangle.rb;
                }
                if(req.body.name !== undefined){
                    data.name = req.body.name;
                }
                if(req.body.width !== undefined){
                    data.width = req.body.width;
                }
                if(req.body.height !== undefined){
                    data.height = req.body.height;
                }
                
                // save the data
                data.save(function(err){
                    if(err) {
                        response = {"error" : true,"message" : "Error updating data"};
                        console.log(err);
                    } else {
                        response = {"error" : false,"message" : "Data is updated for "+req.params.id};
                    }
                    res.json(response);
                })
            }
        });
    })
    .delete(function(req,res){
        var response = {};
        // find the data
        Building.findById(req.params.id,function(err,data){
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
                // data exists, remove it.
                Room.remove({'building_id' : req.params.id},function(err){
                    if(err) {
                        response = {"error" : true,"message" : "Error deleting data"};
                    } else {
                        response = {"error" : true,"message" : "Data associated with "+req.params.id+"is deleted"};
                    }
                });
                Building.remove({_id : req.params.id},function(err){
                    if(err) {
                        response = {"error" : true,"message" : "Error deleting data"};
                    } else {
                        response = {"error" : true,"message" : "Data associated with "+req.params.id+"is deleted"};
                    }
                    res.json(response);
                });
            }
        });
    })

    router.route("/rooms/:id")
    .get(function(req,res){
        var response = {};
        Room.find({'building_id': req.params.id}, function(err,data){
            if(err){
                response = {"error" : true,"message" : "Error!"};
            } else {
                response = data;
            }
            res.json(response);
        });
    })

    router.route("/rooms/:id")
    .post(function(req,res){
        var response = {};
        var db = new Room();
        db.rectangle.lt = req.body.rectangle.lt;
        db.rectangle.rt = req.body.rectangle.rt;
        db.rectangle.lb = req.body.rectangle.lb;
        db.rectangle.rb = req.body.rectangle.rb;
        db.name = req.body.name;
        db.width = req.body.width;
        db.height = req.body.height;
        db.building_id = req.params.id; 
        // Hash the password using SHA1 algorithm.
        //db.id =  req.body.id;
        db.save(function(err){
        // save() will run insert() command of MongoDB.
        // it will add new data in collection.
            if(err) {
                response = {"error" : true,"message" : "Failed!"};
                console.log(err);
            } else {
                response = {"error" : false,"message" : "Data added"};
            }
            res.json(response);
        });

    })



app.use('/',router);
app.listen(3000);
console.log("Listening to PORT 3000");