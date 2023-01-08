package dinhtc.app.interviewuthus.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dinhtc.app.interviewuthus.model.FoodModel


@Database(entities = [FoodModel::class], version = 1,exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

}