# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-08 08:19
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('museumGuide', '0003_auto_20161008_0634'),
    ]

    operations = [
        migrations.RenameField(
            model_name='room',
            old_name='buiding',
            new_name='building',
        ),
        migrations.AlterField(
            model_name='building',
            name='museum',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='buildings', to='museumGuide.Museum'),
        ),
    ]
