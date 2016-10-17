from flask import Flask, render_template, request, redirect, url_for, abort, session, flash, send_from_directory,jsonify
from flask import make_response
from functools import wraps
import os, sys
from django.db import connection
import time
import copy
import json
from django.core import serializers
from django.core.validators import URLValidator
from django.core.exceptions import ValidationError
sys.path.append(os.getcwd())
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "settings")
import threading
import django
from django.contrib.gis.geos import Polygon
django.setup()

from museumGuide.models import *

app = Flask(__name__, static_url_path='')
app.config['SECRET_KEY'] = 'F34TF$($e34D';


def async(f):
    def wrapper(*args, **kwargs):
        thr = threading.Thread(target = f, args = args, kwargs = kwargs)
        thr.start()
    return wrapper

def urlValidator(link):
    val = URLValidator()
    valid = True
    try:
        val(link)
    except ValidationError as e:
        valid = False
    return valid

def websiteFix(website):
    website = website.strip().replace("www.","")
    if "http://" not in website and "https://" not in website:
        website = "http://" + website
    #validate website
    if urlValidator(website):
        return website
    else:
        return False

# {  
#    "name":"MUSEUM_NAME",
#    "description":"MUSEUM_DESC",
#    "address":"MUSEUM_ADDRESS",
#    "phone":"MUSEUM_PHONE",
#    "website":"MUSEUM_WEBSITE",
#    "buildings":[  
#       {  
#          "name":"BUILDING_0",
#          "geoLocation":[[2,3],[4,5],[2,3],[4,5]],
#          "rooms":[  
#             {  
#                "name":"ROOM_1",
#                "geoLocation":[[2,3],[4,5],[2,3],[4,5]],
#                "floor":0,
#                "exhibits":[  

#                ]
#             },
#             {  
#                "name":"ROOM_2",
#                "geoLocation":[[2,3],[4,5],[2,3],[4,5]],
#                "floor":-1,
#                "exhibits":[  

#                ]
#             }
#          ]
#       },
#       {  
#          "name":"BUILDING_1",
#          "geoLocation":[[2,3],[4,5],[2,3],[4,5]],
#          "rooms":[  
#             {  
#                "name":"ROOM_1",
#                "geoLocation":[[2,3],[4,5],[2,3],[4,5]],
#                "floor":0,
#                "exhibits":[  {"name":"test1","description":"blabla"}

#                ]
#             },
#             {  
#                "name":"ROOM_2",
#                "geoLocation":"??geoLoc??",
#                "floor":-1,
#                "exhibits":[ {"name":"test2","description":"blabla"} 

#                ]
#             }
#          ]
#       }
#    ]
# }

def getGeoLocation(item):
    geoSet = []
    for coordSet in item["geoLocation"]:
        geoSet.append((coordSet[0],coordSet[1]))
    geoSet = tuple(geoSet)
    return geoSet

@app.route('/museums', methods=['POST','GET'])
def museums():
    if request.method == 'POST':
        data = request.get_json()
        website = data["website"]
        # website = websiteFix(website)
        if website:
            museum = Museum(name = data["name"], description = data["description"], address = data["address"], phone = data["phone"], website = website)
            museum.save()
            for building in data["buildings"]:
                geoLocation = Polygon(getGeoLocation(building))
                b = Building(name = building["name"],geoLocation = geoLocation, museum_id = museum.id)
                b.save()
                for room in building["rooms"]:
                    geoLocation = Polygon(getGeoLocation(room))
                    r = Room(name = room["name"], geoLocation = geoLocation, floor = room["floor"], building_id = b.id)
                    r.save()
                    for exhibit in room["exhibits"]:
                        ex = Exhibit(name = exhibit["name"], description = exhibit["description"], room_id = r.id)
                        ex.save()
            return jsonify({"added":True})
        else:
            return jsonify({"added":False})
    else:
        if request.method == "GET":
            museums = serializers.serialize("python", Museum.objects.all())
            aux = 0
            for museum in museums:
                for key,val in museum.items():
                    if key == "pk":
                        key = "id"
                        aux = val
                    if key == "fields":
                        for k, v in museum[key].items():
                            museum[k] = v
                # RunTime Error over .items(), fixed using var aux
                museum["id"] = aux
                del museum["pk"]
                del museum["fields"]
            return jsonify(museums)
    return jsonify({"added":False})


def strPolyToObject(item):
	# [[2,3],[4,5],[2,3],[4,5]]
	if item:
		item = item.split(";")[1][10:-2]
		item = item.split(",")
		polyList = [poly.strip().split(" ") for poly in item]
		polyObject = {}
		polyObject["points"] = []
		for poly in polyList:
			polyObject["points"].append({"x":float(poly[0]),"y":float(poly[1])})
		return polyObject
	return ""



@app.route('/museum/<id>', methods=['GET'])
def museum(id):
	if request.method == "GET":
		try:
			m = Museum.objects.get(id = id)
		except:
			return "Id not found"
		museum = serializers.serialize("python",Museum.objects.filter(id = id))
		# museum = serializers.serialize("python",Museum.objects.filter(id = id)[0].buildings.all())
		museum = museum[0]
		for key,val in museum.items():
			if key == "pk":
				key = "id"
				aux = val
			if key == "fields":
				for k, v in museum[key].items():
					museum[k] = v
		# RunTime Error over .items(), fixed using var aux
		museum["id"] = aux
		del museum["pk"]
		del museum["fields"]
		buildings = m.buildings.all()
		museum["buildings"] = serializers.serialize("python",buildings)
		museum["buildings"] = fixModel(museum,"buildings")
		for building in museum["buildings"]:
		# 	# rooms
			building["rooms"] = serializers.serialize("python",Room.objects.filter(building_id = building["id"]))
			building["rooms"] = fixModel(building,"rooms")



		# TODO  - rooms for buildings and exhibits
		# print(m.buildings.all())


	return jsonify(museum)

def fixModel(item,point):
	new = item[point]
	new = copy.deepcopy(new)
	for val in new:
		fields = {}
		fields = val["fields"]
		val["id"] = val["pk"]
		del val["fields"]
		del val["pk"]
		for key,value in fields.items():
			if key == "geoLocation":
				val[key] = strPolyToObject(value)
			else:
				val[key] = value
	# print(new)
	return new



@app.route('/', methods=['GET','POST'])
def home():
    return "Not found"

@app.route('/404', methods=['GET','POST'])
def notFound():
    return redirect(url_for("page_not_found"))

@app.errorhandler(404)
def page_not_found(e):
	return "Not found"

if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=34343, threaded=True)