package dinhtc.app.interviewuthus.room

import dinhtc.app.interviewuthus.model.FoodModel

interface DatabaseHelper {

    suspend fun getFoods(): List<FoodModel>

    suspend fun insertAll(foods: List<FoodModel>)

    suspend fun deleteAll()

}