package dinhtc.app.interviewuthus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dinhtc.app.interviewuthus.databinding.CustomItemHomeBinding
import dinhtc.app.interviewuthus.model.FoodModel
import java.text.SimpleDateFormat
import java.util.*


class FoodAdapter(
    private var listFood: List<FoodModel>, private var context: Context
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    var onItemChecked: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClickAddItem(foodModel: FoodModel?, position: Int)
        fun onClickRemoveItem(foodModel: FoodModel?, position: Int)
    }

    fun setOnClickItem(onClickListener: OnItemClickListener) {
        this.onItemChecked = onClickListener
    }


    inner class ViewHolder(val binding: CustomItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var downTimer: CountDownTimer? = null
        val currentDate = SimpleDateFormat("HH:mm:ss, MM-dd-yyyy").format(Date())
        fun refreshTime(timer: Long, position: Int) {
            val timeCurrent = convertDateToLong(currentDate)
            val timeChange = timer - timeCurrent
            if (timeChange > 0) {
                downTimer = object : CountDownTimer(timeChange, 500) {
                    override fun onTick(millisUntilFinished: Long) {
                        val seconds = millisUntilFinished / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60
                        val days = hours / 24
                        val time = ""+ hours % 24 + ":" + minutes % 60 + ":" + seconds % 60 + ", " + days + " ngày"
                        binding.tvTimer.text = time
                    }

                    override fun onFinish() {
                    }
                }.start()
            } else {
                binding.tvTimer.text = "Quá hạn sử dụng"
                binding.tvTimer.setTextColor(Color.RED)
                downTimer?.cancel()
            }
        }

        fun getDownTimer(): CountDownTimer? {
            return downTimer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CustomItemHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var currentItem = listFood[position]
        val itemBinding = holder.binding
        itemBinding.apply {
            foodModel = currentItem
            checkboxItem.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    layoutCount.visibility = View.VISIBLE
                    onItemChecked?.onClickAddItem(currentItem, position)
                } else {
                    layoutCount.visibility = View.GONE
                    onItemChecked?.onClickRemoveItem(currentItem, position)
                }
            }

            var countNumber = 1
            btnMinus.setOnClickListener {
                if (countNumber <= 1) countNumber = 1
                else {
                    countNumber -= 1
                }
                txtNumber.text = countNumber.toString()
                currentItem.count = countNumber
            }
            btnPlus.setOnClickListener {
                countNumber++
                txtNumber.text = countNumber.toString()
                currentItem.count = countNumber
            }

        }

    }

    override fun getItemCount(): Int {
        return listFood.size
    }


    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val pos = holder.adapterPosition
        val todayFmodel: FoodModel = listFood[pos]
        holder.refreshTime(convertDateToLong(todayFmodel.expiry), pos)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder.getDownTimer() != null) holder.getDownTimer()!!.cancel()
    }

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("HH:mm:ss, MM-dd-yyyy")
        return df.parse(date).time
    }
}