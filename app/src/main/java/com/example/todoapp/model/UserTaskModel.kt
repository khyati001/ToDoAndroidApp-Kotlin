package com.example.todoapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTask")
class UserTaskModel : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "taskName")
    var taskName: String = ""

    @ColumnInfo(name = "taskDate")
    var taskDate: String = ""

    @ColumnInfo(name = "taskCreator")
    var taskCreator: String = ""

    @ColumnInfo(name = "createdOn")
    var createdOn: String = ""

    @ColumnInfo(name = "updatedOn")
    var updatedOn: String = ""

    constructor(source: Parcel) : this()

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserTaskModel> =
            object : Parcelable.Creator<UserTaskModel> {
                override fun createFromParcel(source: Parcel): UserTaskModel = UserTaskModel(source)
                override fun newArray(size: Int): Array<UserTaskModel?> = arrayOfNulls(size)
            }
    }
}
