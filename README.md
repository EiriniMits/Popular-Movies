# Popular Movies App

## Instalation 

The app uses [The Movie Database](http://themoviedb.org) API to get movie information and posters. You must provide your own API key in order to build the app. When you obtain API key, replace YOUR_API_KEY with your API key in the ~/.gradle/gradle.properties file.
```
buildTypes.each {
        it.buildConfigField 'String', 'API_KEY', api_key
}
```
## Screenshots
![screenshot_1524050952](https://user-images.githubusercontent.com/16197563/38929766-1b701ba2-4316-11e8-9689-9a26e9317cbc.png)

![screenshot_1524050994](https://user-images.githubusercontent.com/16197563/38929767-1b9563bc-4316-11e8-8bf0-c3f714dd1745.png)

![screenshot_1524051072](https://user-images.githubusercontent.com/16197563/38929768-1bb93d14-4316-11e8-8eef-66d08b5dcc54.png)

## Libraries
* [Picasso](https://github.com/square/picasso)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Retrofit](https://github.com/square/retrofit)

## Icon credits
* Popcorn by [Freepik](https://www.flaticon.com/authors/freepik)
