package dinhtc.app.interviewuthus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinhtc.app.interviewuthus.model.FoodModel
import dinhtc.app.interviewuthus.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {

    private val listFood = arrayListOf<FoodModel>()
    init {
        listFood.add(FoodModel(id = 1, name = "Acai Juice", quantity = "8 fl. oz. (240 ml)", calories = 139, expiry = "00:00:00, 01-01-2023", count = 1))
        listFood.add(FoodModel(id = 2, name = "Aloe Vera", quantity = "8 fl. oz. (240 ml)", calories = 106, expiry = "00:00:00, 01-02-2023", count = 1))
        listFood.add(FoodModel(id = 3, name = "Apple Juice", quantity = "8 fl. oz. (240 ml)", calories = 110, expiry = "00:00:00, 01-03-2023", count = 1))
        listFood.add(FoodModel(id = 4, name = "Apricot Nectar", quantity = "8 fl. oz. (240 ml)", calories = 134, expiry = "00:00:00, 01-04-2023", count = 1))
        listFood.add(FoodModel(id = 5, name = "Banana Juice", quantity = "8 fl. oz. (240 ml)", calories = 120, expiry = "00:00:00, 01-05-2023", count = 1))
        listFood.add(FoodModel(id = 6, name = "Blackberry Juice", quantity = "8 fl. oz. (240 ml)", calories = 115, expiry = "00:00:00, 01-06-2023", count = 1))
        listFood.add(FoodModel(id = 7, name = "Boysenberry Juice", quantity = "8 fl. oz. (240 ml)", calories = 130, expiry = "00:00:00, 01-07-2023", count = 1))
        listFood.add(FoodModel(id = 8, name = "Capri-Sun", quantity = "8 fl. oz. (240 ml)", calories = 82, expiry = "00:00:00, 01-08-2023", count = 1))
        listFood.add(FoodModel(id = 9, name = "Carrot Juice", quantity = "8 fl. oz. (240 ml)", calories = 96, expiry = "00:00:00, 01-09-2023", count = 1))
        listFood.add(FoodModel(id = 10, name = "Chamomile Tea", quantity = "8 fl. oz. (240 ml)", calories = 0, expiry = "00:00:00, 01-10-2023", count = 1))
    }

    private var _foodResponse = MutableLiveData<Resource<List<FoodModel>>>()
    val foodResponse: LiveData<Resource<List<FoodModel>>> = _foodResponse

    init {
        fetchAllFood()
    }

    private fun fetchAllFood() {
        viewModelScope.launch {
            _foodResponse.postValue(Resource.loading())
            delay(2000)
            _foodResponse.postValue(Resource.success(listFood))
        }
    }
}