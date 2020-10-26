package com.example.hudie.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.activities.OrderActivity
import com.example.hudie.models.DesignResponse

class ShopCardAdapter(private val designList: ArrayList<DesignResponse>) : RecyclerView.Adapter<ShopCardAdapter.ViewHolder>() {

    companion object {
        fun newInstance(): ShopCardAdapter = ShopCardAdapter(ArrayList())
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var itemImage: ImageView = itemView.findViewById<ImageView>(R.id.design_image)
        var designID: Int = 0;
        var designDetails: String = "";
        var designImages: String = "";
        var designImagesPosition: String = "";

        var context: Context = itemView.context

        var itemTitle: TextView = itemView.findViewById<TextView>(R.id.design_title)
        var itemDesigner: TextView = itemView.findViewById<TextView>(R.id.design_creator)
        var itemDetail: TextView = itemView.findViewById<TextView>(R.id.design_detail)
        var detailButton = itemView.findViewById<Button>(R.id.details_button)
        var orderButton = itemView.findViewById<Button>(R.id.order_button)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cardview_shop_design, viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.designID = designList[i].id
        viewHolder.designDetails = designList[i].details.toString()
        viewHolder.designImages = designList[i].images.toString()
        viewHolder.designImagesPosition = designList[i].imagesPosition.toString()

        viewHolder.itemTitle.text = designList[i].design_name
        viewHolder.itemDesigner.text = designList[i].username
        viewHolder.itemDetail.text = designList[i].price.toString()

        viewHolder.detailButton.setOnClickListener { /*TODO */ }

        viewHolder.orderButton.setOnClickListener {
            val orderIntent = Intent(viewHolder.context, OrderActivity::class.java)
            orderIntent.putExtra("designID", viewHolder.designID)
            orderIntent.putExtra("details", viewHolder.designDetails)
            orderIntent.putExtra("images", viewHolder.designImages)
            orderIntent.putExtra("imagesPosition", viewHolder.designImagesPosition)
            orderIntent.putExtra("price", viewHolder.itemDetail.text.toString().toInt())

            viewHolder.context.startActivity(orderIntent)
        }
    }

    override fun getItemCount(): Int = designList.size

}