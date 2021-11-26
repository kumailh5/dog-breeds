package com.kumail.dogbreeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumail.dogbreeds.data.model.toListOfBreedItems
import com.kumail.dogbreeds.data.repository.BreedRepository
import com.kumail.dogbreeds.network.ApiResponse
import com.kumail.dogbreeds.util.TestCoroutineRule
import com.kumail.dogbreeds.util.TestModelsGenerator
import com.kumail.dogbreeds.util.getOrAwaitValue
import com.kumail.dogbreeds.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Config(sdk = [30])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MockKViewModelUnitTest {

    private lateinit var viewModel: MainViewModel

    private val repository: BreedRepository = mockk()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Test
    fun `Verify breeds list being mapped on event`() {
        val breedsListResponseModel = testModelsGenerator.generateBreedsListResponse()
        coEvery { repository.getBreedsList() } returns (ApiResponse.Success(breedsListResponseModel))
        viewModel = MainViewModel(repository)
        viewModel.getBreedsList()
        assertThat(
            breedsListResponseModel.breedsList.toListOfBreedItems(),
            `is`(viewModel.breedsList.getOrAwaitValue())
        )
    }

    @Test
    fun `Verify breeds list error event`() {
        val errorResponseModel = testModelsGenerator.generateBreedsListErrorResponse()
        coEvery { repository.getBreedsList() } returns (ApiResponse.NetworkError(errorResponseModel))
        viewModel = MainViewModel(repository)
        viewModel.getBreedsList()
        assertThat(
            errorResponseModel.errorMessage,
            `is`(viewModel.errorMessage.getOrAwaitValue())
        )
    }
}