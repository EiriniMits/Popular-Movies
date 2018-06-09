# Popular Movies App
![rsz_ic_launcher-web](https://user-images.githubusercontent.com/16197563/41191057-2fe1e034-6bf2-11e8-82ea-79947d204aa0.png)

Popular Movies App created as a part of [Udacity Android Developer Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801).

## Instalation 

The app uses [The Movie Database](http://themoviedb.org) API to get movie information and posters. You must provide your own API key in order to build the app. When you obtain API key, replace api_key with your API key in the ~/.gradle/gradle.properties file.
```
buildTypes.each {
        it.buildConfigField 'String', 'API_KEY', api_key
}
```
## Screenshots
![rsz_38929766-1b701ba2-4316-11e8-9689-9a26e9317cbc](https://user-images.githubusercontent.com/16197563/41191058-3da3789a-6bf2-11e8-9afa-bcffdf26402c.png) ![rsz_38929767-1b9563bc-4316-11e8-8bf0-c3f714dd1745](https://user-images.githubusercontent.com/16197563/41191059-426f856c-6bf2-11e8-907d-bb3e6d6f641a.png) ![rsz_38929768-1bb93d14-4316-11e8-8eef-66d08b5dcc54](https://user-images.githubusercontent.com/16197563/41191060-439b7ac2-6bf2-11e8-87b6-5b3897c14949.png)

## Libraries
* [Picasso](https://github.com/square/picasso)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Retrofit](https://github.com/square/retrofit)

## Icon credits
* Popcorn by [Freepik](https://www.flaticon.com/authors/freepik)
