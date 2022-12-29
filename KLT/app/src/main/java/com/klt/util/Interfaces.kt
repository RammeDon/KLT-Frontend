package com.klt.util

import java.util.Date


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
interface ITask {

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

    val id: String
    val completedAtLeastOnceToday: Boolean
    val taskName: String
    val goals: Array<IGoal>
    val requireOrderNumber: Boolean
}

/** Interface for customer */
interface ICustomer {
    val id: String
    val customerName: String
    val tasks: Array<ITask>
}