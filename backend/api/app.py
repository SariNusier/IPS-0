from flask import Flask, render_template, request, redirect, url_for, abort, session, flash, send_from_directory
from flask import make_response
from functools import wraps
import os, sys
from django.db import connection
import utils
import csv
import time
from django.core.validators import URLValidator
from django.core.exceptions import ValidationError
from pyquery import PyQuery as pq
sys.path.append(os.getcwd())
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "settings")
import threading
import django
from api import getFromApi
from pytor import pytor


django.setup()

from steamwatcher.models import *

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


def getFromSteam(link):
    html = pytor.getSource(link)
    dom = pq(html)
    price = dom(".game_purchase_price").text()
    title = dom(".apphub_AppName").text()
    if not price:
        return (0,title)
    return (price,title)

@app.route('/price', methods=['POST'])
def price():
    if request.method == 'POST':
        link = request.form['link'].strip()
        newLink = websiteFix(link)
        if newLink:
            price,title = getFromSteam(link)
            return render_template("price.html", price = price, title = title)
        else:
            return render_template("notvalid.html", link = link)
    return redirect(url_for('notFound'))


@app.route('/', methods=['GET','POST'])
def home():
    return render_template("home.html")

@app.route('/404', methods=['GET','POST'])
def notFound():
    return render_template("error.html")

@app.errorhandler(404)
def page_not_found(e):
    resp = make_response(render_template('error.html'), 404)
    return resp

if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=34343, threaded=True)