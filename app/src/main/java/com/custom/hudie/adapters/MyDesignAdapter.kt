package com.custom.hudie.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.custom.hudie.R
import com.custom.hudie.models.DesignResponse

class MyDesignAdapter(private val designList: ArrayList<DesignResponse>) : RecyclerView.Adapter<MyDesignAdapter.ViewHolder>() {

    companion object {
        fun newInstance(): MyDesignAdapter = MyDesignAdapter(ArrayList())
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var itemImage: ImageView = itemView.findViewById<ImageView>(R.id.design_image)
        var itemTitle: TextView = itemView.findViewById<TextView>(R.id.design_title)
        var itemDesigner: TextView = itemView.findViewById<TextView>(R.id.design_creator)
        var itemDetail: TextView = itemView.findViewById<TextView>(R.id.design_detail)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cardview_my_design, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = designList[i].design_name
        viewHolder.itemDesigner.text = designList[i].username
        viewHolder.itemDetail.text = designList[i].details
    }

    override fun getItemCount(): Int = designList.size

}