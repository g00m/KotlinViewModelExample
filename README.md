# Kotlin ViewModel Example
This is a small example on how to use *ViewModels* for your Kotlin RecyclerView.

## Gradle
Add the *databinding:compiler* to your build.gradle.
	android { 
	    dataBinding.enabled = true 
	}
	
	dependencies {
	    kapt 'com.android.databinding:compiler:2.3.3'
	}

## ViewModel
	class ViewModel(val initText : String) : BaseObservable() {
	    @Bindable var textToDisplay : String? = ""
	        set(value) {
	            field = value
	            notifyPropertyChanged(BR.textToDisplay)
	        }
	
	    var count = 0
	
	    init { textToDisplay = initText }
	
	    fun printValue () {
	        textToDisplay = initText + " ${++count}"
	    }
	}

## ViewHolder
	class BaseViewHolder (view : ViewDataBinding) : RecyclerView.ViewHolder(view.root) {}

## BaseAdapter
	class BaseAdapter(val strings : Array<String>) : RecyclerView.Adapter<BaseViewHolder>() {
	
	    override fun getItemCount(): Int {
	        return strings.size
	    }
	
	    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {}
	
	    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
	        val layoutInflater : LayoutInflater = LayoutInflater.from(parent?.context)
	
	        val binding : ItemViewModelBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_view_model, parent, false)
	        binding.viewModel = ViewModel(strings.get(viewType))
	
	        return BaseViewHolder(binding)
	
	    }
	
	}

## XML 
	<data>
	    <variable
	        name="viewModel"
	        type="net.g00m.kotlinviewmodelexample.MainActivity.ViewModel"/>
	</data>
	
	<LinearLayout
	    android:layout_width="match_parent"
	    android:layout_height="50dp"
	    android:layout_margin="10dp">
	
	    <TextView
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:text="@{viewModel.textToDisplay}"
	        android:onClick="@{() -> viewModel.printValue()}"/>
	</LinearLayout>