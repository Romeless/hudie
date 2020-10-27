package com.example.hudie.adapters

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.example.hudie.R
import com.example.hudie.activities.OrderActivity
import com.example.hudie.activities.PaymentActivity
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignDetails
import com.example.hudie.models.DesignResponse
import com.example.hudie.models.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderCardAdapter(private val designList: ArrayList<OrderResponse>) : RecyclerView.Adapter<OrderCardAdapter.ViewHolder>() {

    companion object {
        fun newInstance(): OrderCardAdapter = OrderCardAdapter(ArrayList())
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var designID: Int = 0;
        var designDetails: String = "";
        var designImages: String = "";
        var designImagesPosition: String = "";

        var context: Context = itemView.context

        var qty: TextView = itemView.findViewById<TextView>(R.id.order_quantity)
        var address: TextView = itemView.findViewById<TextView>(R.id.address_order)
        var price: TextView = itemView.findViewById<TextView>(R.id.price_order)
        var pay: Button = itemView.findViewById<Button>(R.id.pay_button)
        var cancel: Button = itemView.findViewById<Button>(R.id.cancel_order_button)

        var headImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOfHead)
        var bodyImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOfbody)
        var leftHandImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOflefthand)
        var rightHandImage: ImageView = itemView.findViewById<ImageView>(R.id.imagesOfrighthand)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cardview_order, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var context = viewHolder.context

        var setting = PreferenceManager.getDefaultSharedPreferences(viewHolder.context);
        var editor = setting.edit();

        viewHolder.designID = designList[i].id
        viewHolder.designDetails = designList[i].details.toString()
        viewHolder.designImages = designList[i].images.toString()
        viewHolder.designImagesPosition = designList[i].imagesPosition.toString()
        viewHolder.qty.text = designList[i].qty.toString()
        viewHolder.address.text = designList[i].address
        viewHolder.price.text = designList[i].price.toString()


        viewHolder.pay.setOnClickListener {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("id", designList[i].id)
            viewHolder.context.startActivity(intent);
        }

        viewHolder.cancel.setOnClickListener {
            RetrofitClient.instance.updateOrder(
                setting.getString("user_id", "0").toString().toInt(),
                designList[i].userID,
                designList[i].designID,
                designList[i].qty,
                designList[i].information,
                designList[i].price,
                setting.getString("token", " ").toString()
            ).enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {

                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Log.i("HOME", "onFailure")
                    Toast.makeText(
                        context,
                        "Something went wrong fetching shop database!",
                        Toast.LENGTH_LONG
                    )
                }
            })

            var details = viewHolder.designDetails
            //var images = viewHolder.designImages
            //var imagesPosition = viewHolder.designImagesPosition

            val detailsArray = Klaxon().parse<DesignDetails>(details.toString())

            val headText = detailsArray?.kepala.toString()
            val handText = detailsArray?.tangan.toString()
            val bodyText = detailsArray?.badan.toString()
            val colorText = detailsArray?.warna.toString()

            val imageHeadId = viewHolder.context.resources.getIdentifier(
                headText + "_" + colorText,
                "drawable",
                viewHolder.context.packageName
            );
            val imageHandId = viewHolder.context.resources.getIdentifier(
                handText + "_" + colorText,
                "drawable",
                viewHolder.context.packageName
            );
            val imageBodyId = viewHolder.context.resources.getIdentifier(
                bodyText + "_" + colorText,
                "drawable",
                viewHolder.context.packageName
            );

            viewHolder.headImage.setImageResource(imageHeadId);
            viewHolder.leftHandImage.setImageResource(imageHandId);
            viewHolder.rightHandImage.setImageResource(imageHandId);
            viewHolder.bodyImage.setImageResource(imageBodyId);
        }
    }

    override fun getItemCount(): Int = designList.size


}