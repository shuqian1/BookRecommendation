package com.thoughworks.bookrecommendation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.databinding.BookItemBinding
import com.thoughworks.bookrecommendation.model.data.Book

class BookAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val bookList = mutableListOf<Book>()
    private lateinit var clickListener: OnItemClickListener

    inner class BookViewHolder(private val dataBinding: BookItemBinding): RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(book: Book) {
            dataBinding.book = book
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bookViewHolder = BookViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.book_item,
                parent,
                false
            )
        )
        bookViewHolder.itemView.setOnClickListener { v -> clickListener.onItemClick(v, v.tag) }
        return bookViewHolder
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookViewHolder).bind(bookList[position])
        holder.itemView.tag = bookList[position]
    }

    fun updateData(newData: List<Book>?) {
        newData?.let {
            bookList.clear()
            bookList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.clickListener = onItemClickListener
    }
}