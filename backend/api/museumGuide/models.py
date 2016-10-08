from django.contrib.gis.db import models
from django.db import IntegrityError

# class user(models.Model):

class Museum(models.Model):
	name = models.CharField(max_length = 500)
	description = models.CharField(max_length = 1000)
	address = models.CharField(max_length = 500)
	phone = models.CharField(max_length = 500)
	website = models.CharField(max_length = 500)

class Building(models.Model):
	name = models.CharField(max_length = 500)
	geoLocation = models.PolygonField()
	museum = models.ForeignKey(Museum, on_delete=models.CASCADE,related_name="buildings" )

class Room(models.Model):
	name = models.CharField(max_length = 500)
	geoLocation = models.PolygonField()
	floor = models.IntegerField()
	building = models.ForeignKey(Building, on_delete=models.CASCADE,related_name="rooms" )

class Exhibit(models.Model):
	name = models.CharField(max_length = 500)
	description = models.CharField(max_length = 1000)
	room = models.ForeignKey(Room, on_delete=models.CASCADE,related_name="exhibits" )


	# def save(self, *args, **kwargs):
	# 	try:
	# 		super(BadLink, self).save(*args, **kwargs)
	# 		return True
	# 	except IntegrityError as e:
	# 		print("Duplicate in database")
	# 		return False
	# 	except Exception as b:
	# 		print("Exception at save")
	# 		return False