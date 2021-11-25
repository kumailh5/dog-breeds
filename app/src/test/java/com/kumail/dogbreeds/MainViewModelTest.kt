package com.kumail.dogbreeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumail.dogbreeds.data.model.BreedImagesResponse
import com.kumail.dogbreeds.data.model.BreedsListResponse
import com.kumail.dogbreeds.data.model.ErrorResponse
import com.kumail.dogbreeds.data.model.toListOfBreedItems
import com.kumail.dogbreeds.data.repository.BreedRepository
import com.kumail.dogbreeds.network.ApiResponse
import com.kumail.dogbreeds.util.TestCoroutineRule
import com.kumail.dogbreeds.util.getOrAwaitValue
import com.kumail.dogbreeds.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

/**
 * Created by kumailhussain on 14/10/2021.
 */
@Config(sdk = [30])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @Mock
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: BreedRepository

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var observer: Observer<Any>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel(repository)
        isLoadingLiveData = viewModel.isLoading
    }

    @Test
    fun `Verify livedata values changes on event`() {
        assertNotNull(viewModel.getBreedRandomImages("husky"))
        viewModel.isLoading.observeForever(observer)
        verify(observer).onChanged(false)
        viewModel.getBreedRandomImages("husky")
        verify(observer).onChanged(true)
    }

    @Test
    fun `Assert loading values are correct fetching sub breed images`() {
        var isLoading = isLoadingLiveData.value
        isLoading?.let { assertTrue(it) }
        viewModel.getSubBreedRandomImages("australian", "shepherd")
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }

    @Test
    fun `Assert loading values are correct fetching breed images`() {
        testCoroutineRule.pauseDispatcher()
        viewModel.getBreedRandomImages("husky")
        assertThat(viewModel.isLoading.getOrAwaitValue(), `is`(true))
        testCoroutineRule.resumeDispatcher()
        assertThat(viewModel.isLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun `Verify breeds list being mapped on event`() = testCoroutineRule.runBlockingTest {
        `when`(repository.getBreedsList()).thenReturn(mockBreedsListResponse)
        viewModel.getBreedsList()
        assertThat(
            mockBreedsListResponse.data.breedsList.toListOfBreedItems(),
            (equalTo(viewModel.breedsList.getOrAwaitValue()))
        )
    }

    @Test
    fun `Verify breed images being mapped on event`() = testCoroutineRule.runBlockingTest {
        `when`(repository.getBreedRandomImages("husky")).thenReturn(mockBreedImagesResponse)
        viewModel.getBreedRandomImages("husky")
        assertThat(
            mockBreedImagesResponse.data.breedImageUrls,
            `is`(viewModel.breedImageUrls.getOrAwaitValue())
        )
    }

    @Test
    fun `Verify sub-breed images being mapped on event`() = testCoroutineRule.runBlockingTest {
        `when`(repository.getSubBreedRandomImages("australian", "shepherd")).thenReturn(
            mockSubBreedsListResponse
        )
        viewModel.getSubBreedRandomImages("australian", "shepherd")
        assertThat(
            mockSubBreedsListResponse.data.breedImageUrls,
            equalTo(viewModel.breedImageUrls.getOrAwaitValue())
        )
    }

    @Test
    fun `Verify breed error response on event`() = testCoroutineRule.runBlockingTest {
        `when`(repository.getBreedRandomImages("husk")).thenReturn(mockErrorResponse as ApiResponse<BreedImagesResponse>)
        viewModel.getBreedRandomImages("husk")
        assertThat(
            mockErrorResponse.errorResponse.errorMessage,
            equalTo(viewModel.errorMessage.getOrAwaitValue())
        )
    }

    @Test
    fun `Verify sub-breed error response on event`() = testCoroutineRule.runBlockingTest {
        `when`(repository.getSubBreedRandomImages("australian", "shep")).thenReturn(
            mockErrorResponse as ApiResponse<BreedImagesResponse>
        )
        viewModel.getSubBreedRandomImages("australian", "shep")
        assertThat(
            mockErrorResponse.errorResponse.errorMessage,
            equalTo(viewModel.errorMessage.getOrAwaitValue())
        )
    }

    private val mockBreedsListResponse = ApiResponse.Success(
        BreedsListResponse(
            mapOf(
                "affenpinscher" to emptyList(),
                "australian" to listOf("shepherd"),
                "boxer" to emptyList(),
                "brabancon" to emptyList(),
                "bulldog" to listOf("boston", "english", "french"),
                "corgi" to listOf("cardigan"),
                "dane" to listOf("great"),
                "husky" to emptyList()
            ), "success"
        )
    )

    private val mockBreedImagesResponse = ApiResponse.Success(
        BreedImagesResponse(
            listOf(
                "https://images.dog.ceo/breeds/husky/n02110185_10875.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_11396.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_12656.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_1439.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_15019.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_1552.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_56.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_5871.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_7564.jpg",
                "https://images.dog.ceo/breeds/husky/n02110185_8360.jpg"
            ), "success"
        )
    )

    private val mockSubBreedsListResponse = ApiResponse.Success(
        BreedImagesResponse(
            listOf(
                "https://images.dog.ceo/breeds/australian-shepherd/leroy.jpg",
                "https://images.dog.ceo/breeds/australian-shepherd/pepper.jpg",
                "https://images.dog.ceo/breeds/australian-shepherd/pepper2.jpg",
                "https://images.dog.ceo/breeds/australian-shepherd/sadie.jpg"
            ), "success"
        )
    )

    private val mockErrorResponse =
        ApiResponse.NetworkError<ErrorResponse>(
            ErrorResponse(
                "Breed not found (master breed does not exist)",
                "error",
                404
            )
        )
}
