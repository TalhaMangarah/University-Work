import os, sys
import django
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'chattrends.settings')
django.setup()
from twitchio.ext import commands
from threading import Timer, Event
import datetime
from django.utils import timezone
from webapp.models import ChatTrends, Streamer, Chat

username = sys.argv[1]
token = os.environ['oauth_token']
client = os.environ['client_id']
message_array = []

#Bot class to authenticate and join the chat and report the chat events
class Bot(commands.Bot):

    def __init__(self, username):
        super().__init__(irc_token=token, client_id=client, nick='chattrends', prefix='!',
                         initial_channels=[username])

    # Events don't need decorators when subclassed
    async def event_ready(self):
        # print(f'Ready | {self.nick}')
        trends()

    async def event_message(self, message):
        global message_array
        message_array.append(message.content)
        await self.handle_commands(message)

    # Commands use a decorator...
    # @commands.command(name='test')
    # async def my_command(self, ctx):
    #     await ctx.send(f'Hello {ctx.author.name}!')

# trend method to generate trend array from user channel every 15 seconds on actual message sent
def trends():
    global message_array
    if(message_array != []):
        current_trend = max(set(message_array), key=message_array.count)
        trend = Chat(message = str(current_trend), streamer = Streamer.objects.get(user_name=username))
        trend.save()
    message_array = []
    Timer(15, trends).start()

#Run the bot
bot = Bot(username)
bot.run()