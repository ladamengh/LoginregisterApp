package com.example.loginregister

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginregister.API.INodeJS
import com.example.loginregister.API.RetroFitClient
import com.example.loginregister.adapter.CourseAdapter
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity: AppCompatActivity() {

    internal var disposables = CompositeDisposable()
    internal lateinit var layoutManager: LinearLayoutManager
    internal lateinit var adapter: CourseAdapter
    internal var suggestList: MutableList<String> = ArrayList()

    // init API
    val retrofit = RetroFitClient.getInstance()
    var IMyService = retrofit.create(INodeJS::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // View
        recycler_search.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_search.layoutManager = layoutManager
        recycler_search.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        search_bar.setCardViewElevation(10)
        //addSuggestList()
        search_bar.addTextChangeListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val suggest = ArrayList<String>()
                for  (search_term in suggestList)
                if (search_term.toLowerCase().contentEquals(search_bar.text.toLowerCase()))
                        suggest.add(search_term)
                search_bar.lastSuggestions = suggest
            }

        })

        search_bar.setOnSearchActionListener(object: MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {

            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if (!enabled)
                    getAllCourses()
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())
            }

        })
        getAllCourses()
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

    private fun startSearch(query: String) {
        disposables.addAll(IMyService.searchCourse(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ courses ->
                adapter = CourseAdapter(baseContext, courses)
                recycler_search.adapter = adapter
            }, {
                Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show()
            }))
    }

    private fun getAllCourses() {
        disposables.addAll(IMyService.coursesList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ courses ->
                adapter = CourseAdapter(baseContext, courses)
                recycler_search.adapter = adapter
            }, {
                Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show()
            }))
    }

  /*  private fun addSuggestList() {
        // here you can cache suggest list
        // suggestList.add("")

        search_bar.lastSuggestions = (suggestList)
    }*/
}