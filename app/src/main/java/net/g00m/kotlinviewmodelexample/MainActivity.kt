package net.g00m.kotlinviewmodelexample

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import net.g00m.kotlinviewmodelexample.databinding.ItemViewModelBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    val random : String
        get() {
            return UUID.randomUUID().toString()
        }

    var recyclerView : RecyclerView? = null
        get() {
            return findViewById<RecyclerView>(R.id.recycler_view)
        }

    val strings = arrayOf(random , random, random, random)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView?.adapter = BaseAdapter(strings)

    }

    /**
     * Constains everything that should be displayed. In this case the 'textToDisplay'.
     */
    class ViewModel(val initText : String) : BaseObservable() {

        /**
         * @Bindable annotation marks the
         */
        @Bindable var textToDisplay : String? = ""
            set(value) {
                field = value
                notifyPropertyChanged(BR.textToDisplay)
            }

        var count = 0

        init { textToDisplay = initText }

        fun printValue () {
            textToDisplay = initText + " ${++count}"
            Log.w("ViewModel", textToDisplay)
        }
    }

    /**
     * ViewHolder for the RecyclerView with a little trick to super(view : View)
     */
    class BaseViewHolder (view : ViewDataBinding) : RecyclerView.ViewHolder(view.root) {}

    class BaseAdapter(val strings : Array<String>) : RecyclerView.Adapter<BaseViewHolder>() {

        override fun getItemCount(): Int {
            return strings.size
        }

        override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {}

        /**
         * Bind the ViewModel to the ViewHolder
         */
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
            val layoutInflater : LayoutInflater = LayoutInflater.from(parent?.context)

            /**
             * The ItemViewModelBinding will be generated from 'com.android.databinding' for you.
             * It is named after your layout file. In this case after 'R.layout.item_view_model'.
             */
            val binding : ItemViewModelBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_view_model, parent, false)
            binding.viewModel = ViewModel(strings.get(viewType))

            return BaseViewHolder(binding)

        }

    }

}
