# Speedrun Browser

An Android application that lets the user browse the [Speedrun.com](https://www.speedrun.com/) leaderboards and view world record speedruns for the various categories available.
Users can also request a random speedrun and they will be presented with a random game, category and leaderboard position run.
The application makes use of the [Speedrun.com api](https://github.com/speedruncomorg/api) to access the data

### Contributing

Please follow the guidelines outlined in [Contributing](https://github.com/danthedrummer/SpeedrunBrowser/tree/master/.github/CONTRIBUTING.md) if you wish to help with development

### Note

This app uses the Youtube Android Player and requires an API key to function. If you are cloning the repo you must provide your own key.
Place your key in a field called `YOUTUBE_API_KEY` in `gradle.properties` and the project should pick it up
```
YOUTUBE_API_KEY="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
```