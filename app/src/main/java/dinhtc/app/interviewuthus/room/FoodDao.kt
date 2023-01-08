package dinhtc.app.interviewuthus.room

import androidx.room.*
import dinhtc.app.interviewuthus.model.FoodModel


@Dao
interface FoodDao {

    @Query("SELECT * FROM table_food")
    suspend fun getFoods(): List<FoodModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<FoodModel>)

//    @Query("DELETE FROM food_table")
//    suspend fun deleteFoodAll()

}