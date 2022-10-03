package com.epiFiAssignment.moviesearch.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.epiFiAssignment.moviesearch.utils.Constants
import com.epiFiAssignment.moviesearch.R
import com.epiFiAssignment.moviesearch.adapters.BookmarksAdapter
import com.epiFiAssignment.moviesearch.databinding.ActivityBookmarksBinding
import com.epiFiAssignment.moviesearch.viewmodels.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity which holds the bookmarks
 */

@AndroidEntryPoint
class Bookmarks : AppCompatActivity() , View.OnClickListener {

    lateinit var bookmarksAdapter : BookmarksAdapter
    lateinit var bookmarkViewModel: BookmarkViewModel

    private lateinit var bookmarksBinding: ActivityBookmarksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookmarksBinding = DataBindingUtil.setContentView(this , R.layout.activity_bookmarks)

        init()
    }

    private fun init(){

        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
        bookmarksAdapter = BookmarksAdapter(null)

        bookmarksBinding.goBack.setOnClickListener(this)
        bookmarksBinding.bookmarksRecyclerview.apply {
            layoutManager = GridLayoutManager(context , Constants.COLUMN_COUNT)
            setHasFixedSize(true)
            adapter = bookmarksAdapter
        }

        bookmarkViewModel.getBookMarkList()
        observeViewModels()
    }

    private fun observeViewModels(){
        bookmarkViewModel.bookmarksList.observe(this) { list ->
            bookmarksAdapter = BookmarksAdapter(list)
            bookmarksBinding.bookmarksRecyclerview.adapter = bookmarksAdapter
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