# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
import datetime

from django.db import models
from django.utils import timezone

class ChatTrends(models.Model):
    streamer = models.ForeignKey('Streamer', on_delete=models.CASCADE)
    trend = models.ForeignKey('Chat', on_delete=models.CASCADE)

    class Meta:
        managed = False
        db_table = 'Chat Trends'


class Streamer(models.Model):
    user_name = models.CharField(max_length=255)
    # Field name made lowercase.

    def streamer(self):
        return self.user_name

    class Meta:
        managed = False
        db_table = 'Streamer'

class Chat(models.Model):
    message = models.CharField(max_length=255)
    created_at = models.DateTimeField(default=timezone.now, editable=False)
    streamer = models.ForeignKey('Streamer',on_delete=models.CASCADE)

    def __str__(self):
            return self.message
            
    def trend_at(self):
        now = timezone.now()
        return now - datetime.timedelta(days=1) <= self.trend_at <= now

    class Meta:
        managed = False
        db_table = 'Chat'