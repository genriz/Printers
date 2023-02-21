package com.app.printers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Location{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var office: String = ""
    var floor: String = ""
    var room: String = ""
    val fullName
        get():String{
            return "$office $room"
        }

}
