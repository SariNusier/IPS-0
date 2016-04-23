//THE SERVER WAS BUILT WITH THE HELP OF THE FOLLOWING:
// https://codeforgeek.com/2015/08/restful-api-node-mongodb/
// https://nodejs.org/api/
// https://docs.mongodb.org/manual/
// http://mongoosejs.com/docs/guide.html
// http://expressjs.com/en/guide/routing.html

var express     =   require("express");
var app         =   express();
var bodyParser  =   require("body-parser");
var router      =   express.Router();
var Building    =   require('./models/building');
var Room        =   require('./models/room');
var RP          =   require('./models/RP');
var ERP         =   require('./models/ExhibitRP');
var RPMeasurement = require('./models/RPMeasurement');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({"extended" : false}));

router.get("/",function(req,res){
    res.json({"error" : false,"message" : "Hello World"});


});

app.use('/', router);

router.route("/buildings")
    .get(function(req,res){
        var response = {};
        Building.find({},function(err,data){
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
        db.rectangle.lt = req.body.rectangle.lt;
        db.rectangle.rt = req.body.rectangle.rt;
        db.rectangle.lb = req.body.rectangle.lb;
        db.rectangle.rb = req.body.rectangle.rb;
        db.name = req.body.name;
        db.width = req.body.width;
        db.length = req.body.length; 
        db.save(function(err){
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
        Building.findById(req.params.id,function(err,data){
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
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
                if(req.body.length !== undefined){
                    data.length = req.body.length;
                }
                
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
        Building.findById(req.params.id,function(err,data){
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
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
        db.length = req.body.length;
        db.est_time = req.body.est_time;
        db.N_avg = 0;
        db.building_id = req.params.id; 
        db.save(function(err){
            if(err) {
                response = {"error" : true,"message" : "Failed!"};
                console.log(err);
            } else {
                response = {"error" : false,"message" : "Data added"};
            }
            res.json(response);
        });

    })

    router.route("/measurements/")
    .get(function(req,res){
        var response = {};
        RPMeasurement.find({}, function(err,data){
            if(err){
                response = {"error" : true,"message" : "Error!"};
            } else {
                response = data;
            }
            res.json(response);
        });
    })
    .delete(function(req,res){
        var response = {};
        RPMeasurement.remove({},function(err){
                    if(err) {
                        response = {"error" : true,"message" : "Error deleting data"};
                    } else {
                        response = {"error" : true,"message" : "Data associated with "+req.params.id+"is deleted"};
                    }
                    res.json(response);
        });
    })

    router.route("/measurements/:id")
    .get(function(req,res){
        var response = {};
        RPMeasurement.find({'room_id': req.params.id}, function(err,data){
            if(err){
                response = {"error" : true,"message" : "Error!"};
            } else {
                response = data;
            }
            res.json(response);
        });
    })
    .post(function(req,res){
        var response = {};
        var db = new RPMeasurement();
        db.rpv_pair = req.body.rpv_pair;
        db.room_id = req.params.id;
        db.save(function(err){
            if(err) {
                response = {"error" : true,"message" : "Failed!"};
                console.log(err);
            } else {
                response = {"error" : false,"message" : "Data added"};
            }
            res.json(response);
        });

    })

    router.route("/learn/:id")
    .get(function(req,res){
        var response = {};
        var net = require('net');
        var client = net.connect(5000, 'localhost');
        var request = {};
        var count = 0
        request.command = 'learn';
        request.building_id = req.params.id;
        request.learning_set = [];
        var rooms;
        Room.find({'building_id': req.params.id}, function(err,data){
            rooms = data.map(function(data) {return data._id;});
            RPMeasurement.find({room_id: {$in: rooms}}, function(err,data){
                request.learning_set = data;
                console.log(JSON.stringify(request));
                client.write(JSON.stringify(request));
                client.end();
                count ++;   
            });
        });
    
        client.on('data', (data) => {
            console.log(data.toString());
            res.send(data.toString());
            count++;
        });
        })
    .post(function(req,res){
        var response = {};
        var net = require('net');
        var client = net.connect(5000, 'localhost');
        var request = {};
        var count = 0
        request.command = 'learn';
        request.building_id = req.params.id;
        request.learning_set = [];
        var rooms;
        Room.find({'building_id': req.params.id}, function(err,data){
            rooms = data.map(function(data) {return data._id;});
            console.log(rooms);

            RPMeasurement.find({room_id: {$in: rooms}}, function(err,data){
                request.learning_set = data;
                console.log(JSON.stringify(request));
                client.write(JSON.stringify(request));
                count ++;
                if(count == 2)
                    client.end();
            });
        });

        client.on('data', (data) => {
            console.log(data.toString());
            res.send(data.toString());
            count++;
            if(count == 2)
                client.end();
        });
        });

    router.route("/locate/:id")
    .get(function(req,res){
        var response = "NOTHING";
        var net = require('net');
        var client = net.connect(5000, 'localhost');
        var request = {};
        request.command = 'classify';
        request.building_id = req.params.id;
        request.learning_set = [];  
        RPMeasurement.find({}, function(err,data){
            response = data;
            request.learning_set = response;
        });
        client.write(JSON.stringify(request));
        client.on('data', (data) => {
            console.log(data.toString());
            response = data;
        });
        client.end;
        res.send(response);
    })
    .post(function(req,res){
        var response = {};
        var net = require('net');
        var client = net.connect(5000, 'localhost');
        var request = {};
        request.command = 'classify';
        request.building_id = req.params.id;
        request.learning_set = [];
        request.learning_set.push(req.body);
        client.write(JSON.stringify(request));
        client.on('data', (data) => {
            console.log(data.toString());
            client.end();
            response = data;
            res.send(response);
        });
        client.end();
        });

    router.route("/locationdata/:id")
    .post(function(req,res){
        var response = {};        
        console.log(req.body);
        var duration = req.body.duration;
        Room.findById(req.params.id,function(err,data){
            if(err) {
                response = {"error" : true,"message" : "Error fetching data"};
            } else {
                var est_time;
                var N_avg;
                if(!isFinite(data.est_time)){
                    data.est_time = 1;
                    est_time = 1;
                } else {
                    est_time = data.est_time;
                }
                if(!isFinite(data.N_avg)){
                    data.N_avg = 1;
                    N_avg = 1;
                } else {
                    N_avg = data.N_avg;
                }
                data.est_time = (est_time*N_avg+duration)/N_avg+1;
                data.N_avg++;
                data.save(function(err){
                    if(err) {
                        response = {"error" : true,"message" : "Error updating data"};
                        console.log(err);
                    } else {
                        response = {"error" : false,"message" : "Data is updated for "+req.params.id};
                        console.log("Updated:"+data.name+" "+data.est_time);
                    }
                    res.json(response);
                })
            }
        });
    })


    router.route("/route/:id")
    .post(function(req,res){
        var response = {};
        var net = require('net');
        var client = net.connect(5000, 'localhost');
        var request = {};
        var selectedRooms = [];
        request.command = 'route';
        request.building_id = req.params.id;
        request.deadline = req.body.deadline;
        request.request_set = [];
        selectedRooms = req.body.selected_rooms;
        var roomIDs = selectedRooms.map(function(selectedRooms) {return selectedRooms.id;});
        Room.find({_id: {$in: roomIDs}}, function(err,data){
            var dataObj = [];
            
            for(var i = 0; i< data.length;i++) {
                var temp = data[i].toObject();
                for(var j = 0; j<selectedRooms.length;j++){
                    if(selectedRooms[j].id == temp._id){
                        temp.excitement = selectedRooms[j].excitement;
                        dataObj.push(temp);    
                    }
                        
                }
               
            }
            request.request_set = dataObj;
            console.log(request.request_set); 

            client.write(JSON.stringify(request));
            client.end();
        });
        client.on('data', (data) => {
            console.log(data.toString());
            response = data;
            res.send(response);
        });
        });
    

router.route("/exhibits/:id")
    .get(function(req,res){
        var response = {};
        ERP.find({'room_id': req.params.id}, function(err,data){
            if(err){
                response = {"error" : true,"message" : "Error!"};
            } else {
                response = data;
            }
            res.json(response);
        });
    })
    .post(function(req,res){
        var db = new ERP();
        var response = {};
        db.rpid = req.body.rpid; 
        db.name = req.body.name;
        db.room_id = req.params.id; 
        db.save(function(err){
            if(err) {
                response = {"error" : true,"message" : "Failed!"};
                console.log(err);
            } else {
                response = {"error" : false,"message" : "Data added"};
            }
            res.json(response);
        });
    });

app.use('/',router);
app.listen(3000);
console.log("Listening to PORT 3000");