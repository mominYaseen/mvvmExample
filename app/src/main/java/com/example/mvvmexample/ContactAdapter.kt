package com.example.mvvmexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ContactAdapter(
    private val listOfContacts :List<Contact>
): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    class ContactViewHolder(view :View):ViewHolder(view){
        val image : ImageView = view.findViewById(R.id.rv_img)
        val textHeading :TextView = view.findViewById(R.id.rv_heading)
        val textSubHeading :TextView = view.findViewById(R.id.rv_sub_heading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfContacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.image.setImageURI(listOfContacts[position].image)
        holder.textHeading.text = listOfContacts[position].headingText
        holder.textSubHeading.text = listOfContacts[position].subText

    }
}