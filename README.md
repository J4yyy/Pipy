<div align="center">
<pre>
██████╗ ██╗██████╗ ██╗   ██╗
██╔══██╗██║██╔══██╗╚██╗ ██╔╝
██████╔╝██║██████╔╝ ╚████╔╝ 
██╔═══╝ ██║██╔═══╝   ╚██╔╝  
██║     ██║██║        ██║   
╚═╝     ╚═╝╚═╝        ╚═╝   
Java tool that sends Twitch Chat Messages to Webhooks
</pre>
</div>

## About the Project
This Java project is designed to facilitate the integration of Twitch chat messages with an external application using webhooks. The primary goal is to capture messages from a specified Twitch channel chat and forward them to a designated webhook URL. This enables seamless communication and integration between the Twitch chat environment and an external application or service.

### Built With
* [![Java][java-shield]][java-url]
* [![Twitch][twitch-shield]][twitch-url]

## Installation
### Jar File
1. Download Jar File from [Latest Release][gh-latest-release]
2. Place the '.env' File in the Folder where u Execute the command
```shell
java -jar Pipy-{version}.jar
```
Place .env File in the same directory as the Jar File

### Docker Container
```shell
docker run -d --name pipy -v /local/config/path/.env:/app/.env ghcr.io/j4yyy/pipy
```

### Get Twitch Token
1. Register a new Application [here][twitch-developers]
   1. Enter on the OAuth Redirect URLs Field ````http://localhost````
2. Open this URL and change the 'YOUR_CLIENT_ID' you get from your created application
   1. ```https://id.twitch.tv/oauth2/authorize?response_type=token&client_id=<YOUR_CLIENT_ID>&redirect_uri=http://localhost&scope=chat%3Aread+chat%3Aedit```
   2. Get the token from the 'access_token' property


## Configuration
You need a '.env' File with the following structure
```properties
BOT_TOKEN=YourTwitchToken
BOT_NAME=YourBotName
BOT_CHANNEL=TheChannel
DC_HOOK=YourDiscordHookURL
```


## Roadmap
- [x] Read Twitch Chat
- [ ] Webhooks
  - [x] Discord
  - [ ] Slack
- [ ] Custom Hook Body

## Contact

Jayy - [@Twitter](https://twitter.com/J4yy_B) - [Send Mail](mailto:codingjayy@gmail.com?subject=[GitHub]%20Pipy%20Project)


<!-- MARKDOWN LINKS & IMAGES-->
<!-- BADGES -->
[java-shield]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[twitch-shield]: https://img.shields.io/badge/Twitch-9146FF?style=for-the-badge&logo=twitch&logoColor=white
<!-- URLS -->
[java-url]: https://openjdk.org/
[twitch-url]: https://twitch.tv
[twitch-developers]: https://dev.twitch.tv/console/app
[gh-latest-release]: https://github.com/J4yyy/pipy/releases/latest