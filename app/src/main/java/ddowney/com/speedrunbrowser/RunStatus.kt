package ddowney.com.speedrunbrowser


/**
 * Represents the different statuses a run can have
 */
enum class RunStatus {

    /**
     * The run has not yet been examined and will not have an examiner
     */
    new,

    /**
     * The run has been accepted by an examiner and contains a verify-date field
     */
    verified,

    /**
     * The run has been rejected by an examiner and contains a reason
     */
    rejected
}