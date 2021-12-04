package com.example.atomictracker

import java.net.URL

data class HabitDTO(
    var name: String,
    var frequency: String,
    var notification: Boolean,
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int,
    var id: String,
    var url: String,
) {

    constructor() : this(
        "undefined",
        "undefined",
        false,
        0,
        0,
        0,
        0,
        0,
        "",
        "undefined"
    )


}