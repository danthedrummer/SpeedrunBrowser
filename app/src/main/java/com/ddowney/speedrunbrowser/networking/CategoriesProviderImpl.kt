package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.injection.qualifiers.BaseUrl
import com.ddowney.speedrunbrowser.models.Leaderboard
import com.ddowney.speedrunbrowser.models.ListRoot
import com.google.gson.Gson
import io.reactivex.Observable
import javax.inject.Inject
import okhttp3.OkHttpClient

class CategoriesProviderImpl @Inject constructor(
  client: OkHttpClient,
  @BaseUrl baseUrl: String,
  gson: Gson,
) : BaseProvider(client, baseUrl, gson), CategoriesProvider {

  private val service = retrofit.create(CategoriesService::class.java)

  override fun getRecordsForCategory(id: String, top: Int): Observable<ListRoot<Leaderboard>> {
    return service.getRecordsForCategory(id, top)
  }

}