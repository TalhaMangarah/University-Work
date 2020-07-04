from django.shortcuts import render, redirect
from django.http import HttpResponse, JsonResponse
from django.conf.urls import url
from django.template.loader import render_to_string
from django.core import serializers
from .models import Streamer, Chat
import random
import json
import requests
import os, sys, subprocess

client_id = os.environ['client_id']
client_secret = os.environ['client_secret']
# refresh_token = ''
# access_token = ''

def index(request):
    varsForRender = {
        "title": "Home"
    }
    return render(request, 'index.html', varsForRender)

def about(request):
    varsForRender = {
        "title": 'About',
    }
    return render(request, 'about.html', varsForRender)

def trends(request):
    # get user from from
    channel = request.GET['channelinput']
    # check if user exits in db then get trends and return them to render otherwise send error
    if Streamer.objects.filter(user_name = channel).exists():
        channel_data = Streamer.objects.filter(user_name = channel)
        serialised_data = serializers.serialize('python', channel_data)
        channel_id = 0
        for user in serialised_data:
            channel_id = user['pk']
        trend_array = Chat.objects.filter(streamer_id = channel_id).values('message')[:25]
        trend_only_array = []
        for trend_only in trend_array:
            trend_only_array.append(trend_only['message'])
        varsForRender = {
            "trend":trend_only_array,
            "title":channel
        }

    else:
        return render(request, 'index.html', {"error": "Error, user not found. Make sure they have logged in."})
    return render(request, 'trends.html', varsForRender)

def submitchannel(request):
    # set some values
    abs_url = request.build_absolute_uri('/')
    url = request.build_absolute_uri()
    # get auth code via GET
    auth_code = request.GET.get('code','')
    auth = requests.post(f'https://id.twitch.tv/oauth2/token?client_id={client_id}&client_secret={client_secret}&code={auth_code}&grant_type=authorization_code&redirect_uri={abs_url}')
    access_token_json = auth.json()
    access_token = access_token_json['access_token']
    headers_for_calls = {
    'Authorization': f'Bearer {access_token}', 
    'Client-ID': client_id
    }
    response = requests.get('https://api.twitch.tv/helix/users', headers=headers_for_calls)
    user_data = response.json().get('data', '')
    channel_name = user_data[0].get('login')
    refresh_token = access_token_json['refresh_token']
    # checkTokens(access_token, refresh_token)
    if (Streamer.objects.filter(user_name = channel_name).exists()):
        varsForRender = {
            "alreadyin":"User already being tracked",
        }
        return render(request, 'index.html', varsForRender)
    else:
        channel = Streamer(user_name = channel_name)
        channel.save()
        subprocess.Popen(["python3","-u","./bot.py",channel_name,"&"])
        varsForRender = {
            "added": "Success, user added"
        }
        return render(request, 'index.html', varsForRender)

# def checkTokens(acc_token, refresh_token):
#     refresh = requests.post(f'https://id.twitch.tv/oauth2/token?client_id={client_id}&client_secret={client_secret}&grant_type=refresh_token&refresh_token={refresh_token}')
#     print(refresh_token)
#     return