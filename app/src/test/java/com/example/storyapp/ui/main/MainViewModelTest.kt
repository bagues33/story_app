package com.example.storyapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.database.StoryApp
import com.example.storyapp.repository.StoryAppRepository
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.MainDispatcherRule
import com.example.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyAppRepository: StoryAppRepository

    private val dummyStoryResponse = DataDummy.generateDummyStory()

    @Test
    fun `when getStories Should Not Null and Return Success`() = runTest {
        val data: PagingData<StoryApp> = StoryPagingSource.snapshot(dummyStoryResponse.listStory)
        val expectedStories = MutableLiveData<PagingData<StoryApp>>()
        expectedStories.value = data
        Mockito.`when`(storyAppRepository.getStories()).thenReturn(expectedStories)

        val listStoryViewModel = MainViewModel(storyAppRepository)
        val actualStory: PagingData<StoryApp> = listStoryViewModel.stories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStoryResponse.listStory, differ.snapshot())
        Assert.assertEquals(dummyStoryResponse.listStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStoryResponse.listStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Quote Empty Should Return No Data`() = runTest {
        val data: PagingData<StoryApp> = StoryPagingSource.snapshot(emptyList())
        val expectedStories = MutableLiveData<PagingData<StoryApp>>()
        expectedStories.value = data
        Mockito.`when`(storyAppRepository.getStories()).thenReturn(expectedStories)

        val listStoryViewModel = MainViewModel(storyAppRepository)
        val actualStory: PagingData<StoryApp> = listStoryViewModel.stories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStory)

        Assert.assertEquals(0, differ.snapshot().size)
    }

}

class StoryPagingSource : PagingSource<Int, LiveData<List<StoryApp>>>() {
    companion object {
        fun snapshot(items: List<StoryApp>): PagingData<StoryApp> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryApp>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryApp>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}