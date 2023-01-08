package dinhtc.app.interviewuthus.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinhtc.app.interviewuthus.model.FoodModel
import dinhtc.app.interviewuthus.model.Resource
import dinhtc.app.interviewuthus.room.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RoomViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val foodList = MutableLiveData<Resource<List<FoodModel>>>()

    fun insertFood(foods: List<FoodModel>) {
        viewModelScope.launch {
            foodList.postValue(Resource.loading())
            delay(2000)
            val usersFromDb = dbHelper.getFoods()
            if (usersFromDb.isNotEmpty()) {
                dbHelper.deleteAll()
            }
            dbHelper.insertAll(foods)
            foodList.postValue(Resource.success(foods))
        }
    }

    fun getFoods(): LiveData<Resource<List<FoodModel>>> {
        return foodList
    }
}