package com.klt.util

import java.util.*


/** Interface for tasks entry's */
interface ITaskEntry {

    /** Interface for Deviations that can occur */
    interface IDeviation {
        val start: Date
        val end: Date
        val reason: String
    }

    val id: String
    val taskId: String
    val start: Date
    val end: Date
    val userId: String
    val deviations: Array<IDeviation>
}

/** Interface for task's */
interface ITask : IKLTItem {

    /** Enum for data types */
    enum class GoalDataTypes(text: String) {
        Number("Number"),
        Text("Text"),
        Boolean("Boolean")
    }

    /** Interface for goals in a task */
    interface IGoal {
        val name: String
        var value: Any?
        val unit: String
        val type: GoalDataTypes
    }

    val taskName: String
    val goals: MutableList<IGoal>
    var pinned: Boolean
    val requireOrderNumber: Boolean

    override var name: String
        get() = taskName
        set(newName) {
            this.name = newName
        }

    override val hasIcon: Boolean
        get() = true
}

/** Interface for customer */
interface ICustomer : IKLTItem {
    var pinned: Boolean
}

interface IUser : IKLTItem {
    var firstName: String
    var lastName: String
    var email: String
}

/** Interface for KLT Item */
sealed interface IKLTItem {
    var name: String
    val id: String
    val hasIcon: Boolean
}
