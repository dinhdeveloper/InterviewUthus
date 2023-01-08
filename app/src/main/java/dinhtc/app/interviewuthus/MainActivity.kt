package dinhtc.app.interviewuthus

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dinhtc.app.interviewuthus.adapter.FoodAdapter
import dinhtc.app.interviewuthus.databinding.ActivityMainBinding
import dinhtc.app.interviewuthus.model.FoodModel
import dinhtc.app.interviewuthus.model.Status
import dinhtc.app.interviewuthus.room.DatabaseBuilder
import dinhtc.app.interviewuthus.room.DatabaseHelperImpl
import dinhtc.app.interviewuthus.room.ViewModelFactory
import dinhtc.app.interviewuthus.viewmodel.FoodViewModel
import dinhtc.app.interviewuthus.viewmodel.RoomViewModel

class MainActivity : AppCompatActivity() {

    private val listFoodChecked = mutableListOf<FoodModel>()
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: FoodViewModel by viewModels()
    private lateinit var roomViewModel: RoomViewModel
    private var foodAdapter: FoodAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        mainViewModel.foodResponse.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    binding.rvFood.visibility = View.VISIBLE
                    initFoodAdapter(it.data)
                }
                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnSave.setOnClickListener {
            roomViewModel.insertFood(listFoodChecked)
            roomViewModel.getFoods().observe(this){
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressbar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initFoodAdapter(data: List<FoodModel>?) {
        foodAdapter = data?.let { FoodAdapter(it, context = this) }
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        foodAdapter?.setOnClickItem(object : FoodAdapter.OnItemClickListener {
            override fun onClickAddItem(foodModel: FoodModel?, position: Int) {
                listFoodChecked.add(foodModel!!)
            }

            override fun onClickRemoveItem(foodModel: FoodModel?, position: Int) {
                listFoodChecked.remove(foodModel)
            }
        })
    }
    private fun setupViewModel() {
        roomViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        )[RoomViewModel::class.java]
    }
}
