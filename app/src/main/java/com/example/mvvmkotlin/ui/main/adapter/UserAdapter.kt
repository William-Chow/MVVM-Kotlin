package com.example.mvvmkotlin.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.model.User

internal class UserAdapter(private var userList: List<User>, var context: Context) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_item, parent, false)
        return UserHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = userList[position]
        holder.name.text = user.id.toString() + ". " + user.name
        holder.email.text = context.resources.getString(R.string.email) + " " + user.email
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    internal inner class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvName)
        var email: TextView = view.findViewById(R.id.tvEmail)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(userList[adapterPosition])
            }
        }
    }

    fun sorting(userList: List<User>){
        this.userList = userList
        notifyDataSetChanged()
    }
}