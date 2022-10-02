package com.epiFiAssignment.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.epiFiAssignment.moviesearch.databinding.ActivityBookmarksBinding

class Bookmarks : AppCompatActivity() , View.OnClickListener {

    private lateinit var bookmarksBinding: ActivityBookmarksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookmarksBinding = DataBindingUtil.setContentView(this , R.layout.activity_bookmarks)

        init()
    }

    private fun init(){

        bookmarksBinding.goBack.setOnClickListener(this)
        bookmarksBinding.bookmarksRecyclerview.apply {
            layoutManager = GridLayoutManager(context , Constants.COLUMN_COUNT)
            setHasFixedSize(true)
        }

    }

    override fun onClick(view: View?) {
       when(view?.id) {
           bookmarksBinding.goBack.id -> {
              finish()
           }
       }
    }
}