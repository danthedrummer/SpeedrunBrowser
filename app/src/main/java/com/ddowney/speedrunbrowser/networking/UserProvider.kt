package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.ObjectRoot
import com.ddowney.speedrunbrowser.models.User
import io.reactivex.Observable

interface UserProvider {

  /**
   * Gets the [User] for the given user [id]
   */
  fun getUser(id: String): Observable<ObjectRoot<User>>

}