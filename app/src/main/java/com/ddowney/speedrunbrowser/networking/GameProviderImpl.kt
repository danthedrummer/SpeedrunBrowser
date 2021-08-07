package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.injection.qualifiers.BaseUrl
import com.ddowney.speedrunbrowser.injection.qualifiers.IoDispatcher
import com.ddowney.speedrunbrowser.models.Category
import com.ddowney.speedrunbrowser.models.Game
import com.ddowney.speedrunbrowser.models.ListRoot
import com.google.gson.Gson
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import okhttp3.OkHttpClient

class GameProviderImpl @Inject constructor(
  client: OkHttpClient,
  @BaseUrl baseUrl: String,
  gson: Gson,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseProvider(client, baseUrl, gson), GameProvider {

  private val service = retrofit.create(GameService::class.java)

  override fun getGames(options: Map<String, String>): Observable<ListRoot<Game>> {
    return service.getGames(options)
  }

  override suspend fun coGetGames(options: Map<String, String>): ListRoot<Game> = withContext(ioDispatcher) {
    service.coGetGames(options)
  }

  override fun getCategoriesForGame(id: String): Observable<ListRoot<Category>> {
    return service.getCategoriesForGame(id)
  }

}