package com.sypark.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.Content
import com.sypark.data.db.entity.Data
import com.sypark.data.service.OpenTicketService
import com.sypark.data.util.Util
import javax.inject.Inject

class NewPagingSource @Inject constructor(
    private val service: OpenTicketService,
    private val genre: String,
    private val sortType: Util.NewButtonType? = Util.NewButtonType.CREATED_DATE
) : PagingSource<Int, Content>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val nextPage = params.key ?: 1
            val item = Gson().fromJson<Data>(
                service.requestPerformancesNew(
                    genre = genre,
                    sortType = sortType!!.name,
                    sortOrder = "DESC",
                    page = nextPage,
                    pageSize = params.loadSize,
                ).data, object : TypeToken<Data>() {}.type
            ).contents

            LoadResult.Page(
                data = item,
                prevKey = null,
                nextKey = if (item.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }


}