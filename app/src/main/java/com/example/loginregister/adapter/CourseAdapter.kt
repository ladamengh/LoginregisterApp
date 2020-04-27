package com.example.loginregister.adapter

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

        override fun onClick(p0: View?) {
            courseClickListener.onCourseClick(p0!!, adapterPosition)
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.course_layout, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.title.text = coursesList[p1].title
        p0.description.text = coursesList[p1].description

        if (p1 % 2 == 0)
            p0.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"))

        p0.setClick(object: ICourseClickListener{
            override fun onCourseClick(view: View, position: Int) {
                Toast.makeText(context, "" + coursesList[position].title, Toast.LENGTH_LONG).show()
            }
        })
    }

}