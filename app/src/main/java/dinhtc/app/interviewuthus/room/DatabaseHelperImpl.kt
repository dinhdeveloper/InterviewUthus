package dinhtc.app.interviewuthus.room

import dinhtc.app.interviewuthus.model.FoodModel

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getFoods(): List<FoodModel> = appDatabase.foodDao().getFoods()

    override suspend fun insertAll(foods: List<FoodModel>) = appDatabase.foodDao().insertAll(foods)

    override suspend fun deleteAll() = appDatabase.clearAllTables()

}