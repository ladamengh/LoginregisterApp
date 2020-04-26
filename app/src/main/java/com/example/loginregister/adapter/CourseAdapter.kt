package com.example.loginregister.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregister.R
import com.example.loginregister.interfaceC.ICourseClickListener
import com.example.loginregister.model.Course

class CourseAdapter(internal var context: Context, internal var coursesList:List<Course>): RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var root_view: CardView
        internal var title: TextView
        internal var description: TextView

        internal lateinit var courseClickListener:ICourseClickListener


        fun setClick(courseClickListener: ICourseClickListener) {
            this.courseClickListener = courseClickListener
        }

        init {
            root_view = itemView.findViewById(R.id.root_view) as CardView

            title = itemView.findViewById(R.id.txt_title) as TextView
            description = itemView.findViewById(R.id.txt_description) as TextView
        }

        override fun onClick(v: View?) {
            courseClickListener.onCourseClick(v!!, adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}