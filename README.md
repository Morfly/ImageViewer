# Image Viewer Android App
## Description
Android app that uses the Flickr image search API and shows the results in a 3-column scrollable view.
The app allows users enter queries, such as "kittens".
The app supports endless scrolling, automatically requesting and displaying more images when the user scrolls to the bottom of the
view.
No third-party library used in the app.

## Architecture
### Clean Architecture
#### Primary packages:
- **UI layer** - contains presentational logic of the app. MVP is placed here
- **Domain layer** - Core layer of the app. Contains busiless rules of the app
- **Data layer** - contains mechanisms to retrieve the data from different data sources

#### Additional packages:
- **Image loader** - mini-library which allows image loading from `url` into `ImageView`
- **Lib** - contains common utilities which can be user in each layer

#### Common rules:
- Components from UI layer can't access Data layer components directly. DI is exception in this case. There was no need to do complicated DI in such small app
- The app uses *Dependency Injection* principle. All class dependencies can be injected through constructors or setters (in rare cases)
- The app mostly used *Dependency Inversion* principle. This allows to be independent of implementations and change them with minimum impact. Also, it helps to easily mock dependencies during testing
- `Repository` interfaces are placed in Domain layer and awailable to use from any other layer. Repository implementations aee placed in Data layer.

### Model-View-Presenter
MVP is used as a lightweight implementation of presentation pattern which is based `Contract` interfaces. This is sufficient for such small app.
In case of larger app there can be used MVVM or MVI approaches based on databinding and observable components.

## What technologies/approaches could be used in production

- Retrofit/Volley/Ktor - for networking
- Gson/Jackson - for Json parsing
- Picasso/Glide - for image loading
- RxJava2/Coroutines - for concurrency
- Kotlin-Test/Mockk/Mockito/PowerMock - for Unit testing
- DataBinding/LiveData/Lifecycle - for MVVM implementation
- Dagger2/Koin - for DI
- MVVM/MVI - as presentation patterns
- Realm/Room/Requery - for persistence (in case further features would require it)
- Jacoco - for test coverage
- detekt/klint/SonarLint

## What can be improved in future

### In terms of implementation
- Reusable bitmaps.
- Load low-quality images to every slot first. After that reload them in higher quality to the same ImageViews. Will allow to perform first image loading faster.
-  Orientation change handling. Also, affects bitmap caching
- Error handling and corner case handling
- Disk bitmap cache using DiskLruCache
- Imclude mock framework to test classes which involve Android SDK components (such as Bitmap)
- Use UseCases. Presenter can refer to UseCases instead of direct references to Repositories (As per Clean Architecture) 
- Handle DI scopes

### In terms of features
- Image detail screen
- Menu screen
- Login screen with Flickr auth

