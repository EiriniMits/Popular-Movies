# Popular Movies App

## API Key

The app uses [The Movie Database](http://themoviedb.org) API to get movie information and posters. You must provide your own API key in order to build the app. When you obtain API key, replace YOUR_API_KEY with your API key in the~/.gradle/gradle.properties file.
```
buildTypes.each {
        it.buildConfigField 'String', 'API_KEY', api_key
}
```
## Screenshots


## Libraries
* [Picasso](https://github.com/square/picasso)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Retrofit](https://github.com/square/retrofit)

## Icon credits
* Popcorn by [Freepik](https://www.flaticon.com/authors/freepik)
