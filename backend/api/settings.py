DATABASES = {
    'default': {
    	'ENGINE':'django.contrib.gis.db.backends.mysql', #'mysql.connector.django', #'django.db.backends.mysql',
        'NAME': 'museumGuide',                     
        'USER': 'root',
        'PASSWORD': 'welcome12#',
        'HOST': 'localhost',
        'PORT': '3306',
        'CONN_MAX_AGE':30 #seconds
    }
}

INSTALLED_APPS     = ["museumGuide"]
SECRET_KEY		   = "secret" 
DEBUG 			   = True

# sudo apt-get install mysql
# sudo apt-get install libmysqlclient-dev
# pip install mysqlclient