package ddowney.com.speedrunbrowser.Models

/**
 * Wrapper model for responses from the Speedrun API. This is to accomodate for
 * a "data" key they have at the root of all of their JSON responses.
 *
 * The type of this data could be a RunModel, a GameModel, etc.
 */
data class ResponseWrapperM <out T> (val data : List<T>)

data class ResponseWrapperS <out T> (val data : T)