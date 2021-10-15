package com.kumail.dogbreeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumail.dogbreeds.data.repository.BreedRepository
import com.kumail.dogbreeds.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by kumailhussain on 14/10/2021.
 */
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @Mock
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repositoryImpl: BreedRepository

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var observer: Observer<Boolean>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = spy(MainViewModel(repositoryImpl))
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
}
