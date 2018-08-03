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
![webp net-resizeimage](https://user-images.githubusercontent.com/16197563/43660582-1d3fdb24-9768-11e8-8a8c-b94730d54efa.png)![webp net-resizeimage](https://user-images.githubusercontent.com/16197563/43660708-7a1a5bda-9768-11e8-87b7-06784c676fa6.png)![webp net-resizeimage](https://user-images.githubusercontent.com/16197563/43660609-31049eba-9768-11e8-867b-252f1ee3f7ee.png)
## Libraries
* [Picasso](https://github.com/square/picasso)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Retrofit](https://github.com/square/retrofit)

## Icon credits
* Popcorn by [Freepik](https://www.flaticon.com/authors/freepik)
