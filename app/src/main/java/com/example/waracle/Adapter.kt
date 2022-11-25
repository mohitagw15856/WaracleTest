package com.example.waracle

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.waracle.databinding.ViewResultItemBinding


class Adapter(
    ctx: Context?,
    act: Activity,
    dataModelArrayList: ArrayList<Cakes>?,
    onNoteList: OnNoteList,
) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {
    private var onNoteList: OnNoteList
    private val inflater: LayoutInflater
    private var dataModelArrayList: ArrayList<Cakes>?
    private val activity: Activity

    init {
        inflater = LayoutInflater.from(ctx)
        this.dataModelArrayList = dataModelArrayList!!
        activity = act
        this.onNoteList = onNoteList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ViewResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onNoteList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.translate)

        Glide.with(holder.iv_flag).load(dataModelArrayList?.get(position)?.image).placeholder(R.drawable.cake_ph).into(holder.iv_flag)
        holder.name.setText(dataModelArrayList?.get(position)?.title)
    }

    override fun getItemCount(): Int = if(this.dataModelArrayList == null){
       0
   } else {
       this.dataModelArrayList!!.size
   }


    inner class MyViewHolder(itemView: ViewResultItemBinding, onNoteList: OnNoteList) :
        RecyclerView.ViewHolder(itemView.root), View.OnClickListener {

        var name: TextView
        var iv_flag: ImageView
        var onNoteList: OnNoteList? = null

        init {
            name = itemView.tvName
            iv_flag = itemView.ivFlag
            this.onNoteList = onNoteList
            itemView.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onNoteList?.OnnoteClick(dataModelArrayList?.get(adapterPosition));
        }


    }

    interface OnNoteList {
        fun OnnoteClick (userClass: Cakes?)
    }

    fun updateList(updateList: ArrayList<Cakes>?) {
        dataModelArrayList = updateList
        notifyDataSetChanged()
    }

    fun updateList() {
        notifyDataSetChanged()
    }
}