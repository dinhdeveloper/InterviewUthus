package dinhtc.app.interviewuthus.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dinhtc.app.interviewuthus.viewmodel.RoomViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}