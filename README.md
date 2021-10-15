# Dog Breeds Android App
## Architecture 
Built using MVVM 
Separation of concern achieved by separating each layer into different classes as mentioned below:
* `BreedApi` consists all of the api requests used in the app
* `BreedRepository` invokes api calls in coroutines and handles exceptions 
* `MainViewModel` executes repository functions in viewModelScope and handles the response
* LiveData variables in view model are updated on response 
* Fragments observe LiveData variables from the view model and update UI when the value changes
LiveData boolean named `isLoading` used for updating visibility of progressBar and content. `isLoading` connected to UI layout using data binding and set to true before request in view model and to false at completion. Created binding adapters to handle visibility of UI element. 

`NetworkModule` is a Hilt module containing retrofit setup. Used dependency injection to inject  api class in repository. 

`RecyclerView` connected to adapters used for presenting breeds list and breed images. Nested RecyclerView in item_breed to display sub-breeds. Setup sub-breed adapted within `BreedsListAdapter` to achieve drop down `subBreed` list. Implemented Filterable for `BreedListAdapter` to achieve search functionality. 
## Libraries 
* Hilt for dependency injection 
* Timber for logging 
* Retrofit for api calls 
* Glide for image loading 
## Navigation 
* Used Navigation component  for navigation between `BreedsListFragment` and `BreedImagesFragment`
* `nav_main` contains the navigation graph
* Used Safe Args for passing breed and `subBreed` values from `BreedsListFragment` to `BreedImagesFragment` to be used for api request 
## Custom 
* Kotlin extensions for enabling recycler view top line divider, arrow rotation animation, setting toolbar title, and parsing network error response
* Custom class to achieve top line divider for `sub_breed_item` in `rv_sub_breeds` recycler view
* Horizontal scrollview for breed images to provide carousel gallery layout 
## Api Response 
`BreedApi` used for making api calls to network 
Response from https://dog.ceo/api/breeds/list/all mapped to `BreedsListResponse` data class. Used an immutable map variable with string type keys and a list of strings as values for mapping response message to data class. With the key representing the breed name and the list of strings consisting of all the sub-breeds for the given breed. 
Response from https://dog.ceo/api/breed/{breed}/images/random/10
mapped to `BreedImagesResponse` data class. Used immutable list of strings to map response message to data class to obtain image urls for different breeds. Used retrofit’s `@Path` annotation parameter to programmatically input the selected breed into the api call. Appended /random/10 at the end of url to ensure response included up to 10 random images. 
Response from https://dog.ceo/api/breed/{breed}/{subBreed}/images/random/10 mapped to `BreedImagesResponse` data class too as the response for breeds and sub-breeds was the same. Added another  `@Path` annotation parameter to programmatically input the sub-breed into the api call.  
Used a separate class for unsuccessful network response as the response differed from other calls. Mapped message to an immutable string variable, `errorMessage`. 
