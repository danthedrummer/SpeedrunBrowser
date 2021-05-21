package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.Category
import com.ddowney.speedrunbrowser.models.Game
import com.ddowney.speedrunbrowser.models.ListRoot
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.OkHttpClient

class GameProviderImpl(
  client: OkHttpClient,
  baseUrl: String,
  gson: Gson,
) : BaseProvider(client, baseUrl, gson), GameProvider {

  private val service = retrofit.create(GameService::class.java)

  override fun getGames(options: Map<String, String>): Observable<ListRoot<Game>> {
    return service.getGames(options)
  }

  override fun getCategoriesForGame(id: String): Observable<ListRoot<Category>> {
    return service.getCategoriesForGame(id)
  }

}