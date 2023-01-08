package dinhtc.app.interviewuthus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_food")
data class FoodModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "quantity")
    val quantity : String,
    @ColumnInfo(name = "calories")
    var calories : Int,
    @ColumnInfo(name = "expiry")
    val expiry : String,
    @ColumnInfo(name = "count")
    var count : Int,
)
