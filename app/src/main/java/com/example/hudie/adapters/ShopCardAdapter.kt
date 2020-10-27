package com.example.hudie.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.example.hudie.R
import com.example.hudie.activities.DetailsActivity
import com.example.hudie.activities.OrderActivity
import com.example.hudie.models.DesignDetails
import com.example.hudie.models.DesignResponse

class ShopCardAdapter(private val designList: ArrayList<DesignResponse>) : RecyclerView.Adapter<ShopCardAdapter.ViewHolder>() {

    companion object {
        fun newInstance(): ShopCardAdapter = ShopCardAdapter(ArrayList())
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var designID: Int = 0;
        var designDetails: String = "";
        var designImages: String = "";
        var designImagesPosition: String = "";

        var context: Context = itemView.context

        var itemTitle: TextView = itemView.findViewById<TextView>(R.id.design_title)
        var itemDesigner: TextView = itemView.findViewById<TextView>(R.id.design_creator)
        var itemDetail: TextView = itemView.findViewById<TextView>(R.id.design_detail)
        var detailButton: Button = itemView.findViewById<Button>(R.id.details_button)
        var orderButton: Button = itemView.findViewById<Button>(R.id.order_button)

        var headImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOfHead)
        var bodyImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOfbody)
        var leftHandImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOflefthand)
        var rightHandImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOfrighthand)

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

        viewHolder.detailButton.setOnClickListener {
            val detailIntent = Intent(viewHolder.context, DetailsActivity::class.java)
            detailIntent.putExtra("designID", viewHolder.designID)
            detailIntent.putExtra("details", viewHolder.designDetails)
            detailIntent.putExtra("images", viewHolder.designImages)
            detailIntent.putExtra("imagesPosition", viewHolder.designImagesPosition)
            detailIntent.putExtra("price", viewHolder.itemDetail.text.toString().toInt())
            detailIntent.putExtra("designName", viewHolder.itemTitle.text.toString())

            viewHolder.context.startActivity(detailIntent)
        }

        viewHolder.orderButton.setOnClickListener {
            val orderIntent = Intent(viewHolder.context, OrderActivity::class.java)
            orderIntent.putExtra("designID", viewHolder.designID)
            orderIntent.putExtra("details", viewHolder.designDetails)
            orderIntent.putExtra("images", viewHolder.designImages)
            orderIntent.putExtra("imagesPosition", viewHolder.designImagesPosition)
            orderIntent.putExtra("price", viewHolder.itemDetail.text.toString().toInt())
            orderIntent.putExtra("designName", viewHolder.itemTitle.text.toString())

            viewHolder.context.startActivity(orderIntent)
        }

        var details = viewHolder.designDetails
        //var images = viewHolder.designImages
        //var imagesPosition = viewHolder.designImagesPosition

        val detailsArray = Klaxon().parse<DesignDetails>(details.toString())

        val headText = detailsArray?.kepala.toString()
        val handText = detailsArray?.tangan.toString()
        val bodyText = detailsArray?.badan.toString()
        val colorText = detailsArray?.warna.toString()

        val imageHeadId = viewHolder.context.resources.getIdentifier(headText + "_" + colorText, "drawable", viewHolder.context.packageName);
        val imageHandId = viewHolder.context.resources.getIdentifier(handText + "_" + colorText, "drawable", viewHolder.context.packageName);
        val imageBodyId = viewHolder.context.resources.getIdentifier(bodyText + "_" + colorText, "drawable", viewHolder.context.packageName);

        viewHolder.headImage.setImageResource(imageHeadId);
        viewHolder.leftHandImage.setImageResource(imageHandId);
        viewHolder.rightHandImage.setImageResource(imageHandId);
        viewHolder.bodyImage.setImageResource(imageBodyId);
    }

    override fun getItemCount(): Int = designList.size

}