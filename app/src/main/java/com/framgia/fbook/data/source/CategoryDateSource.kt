package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import io.reactivex.Single

/**
 * Created by levutantuan on 9/11/17.
 */
interface CategoryDateSource {

  interface CategoryRemoteDataSource {
    fun getCategory(): Single<BaseResponse<List<Category>>>
  }
}
