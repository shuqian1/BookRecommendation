package com.thoughworks.bookrecommendation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import com.thoughworks.bookrecommendation.db.DBCatalog
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.repository.CatalogRepository
import com.thoughworks.bookrecommendation.viewmodel.CatalogViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


class CatalogViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var rule1 = TrampolineSchedulerRule()

    @Test
    internal fun `get db category when internet error`() {
        val catalogRepository = Mockito.mock(CatalogRepository::class.java)
        val context = Mockito.mock(Context::class.java)
        val catalogViewModel = CatalogViewModel(context, catalogRepository)
        val catalogList = MediatorLiveData<List<DBCatalog>>()
        catalogList.postValue(listOf(DBCatalog("文学", 1)))
        Mockito.`when`(catalogRepository.getAllCatalog()).thenReturn(catalogList)
        catalogViewModel.getCatalogList()
        catalogViewModel.catalogLiveData.observeForever {
            assertEquals(it.size, 1)
        }
    }

    @Test
    internal fun `get web category when internet success`() {
        val context = Mockito.mock(Context::class.java)
        val catalogRepository = CatalogRepository(context)
        val catalogViewModel = CatalogViewModel(context, catalogRepository)
        val catalogList = MediatorLiveData<List<DBCatalog>>()
        catalogList.postValue(listOf(DBCatalog("文学", 1)))
        catalogViewModel.getCatalogList()
        assertEquals(catalogViewModel.catalogLiveData.value?.size, 17)
    }
}