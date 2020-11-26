package com.example.hudie.activities.ui.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.adapters.ShopCardAdapter
import com.example.hudie.api.RetrofitClient
import com.example.hudie.decorations.GridSpacingItemDecoration
import com.example.hudie.models.DesignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShopFragment : Fragment(){


    companion object {
        fun newInstance(): ShopFragment = ShopFragment();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("HOME", "onCreateView")

        val view = inflater.inflate(R.layout.fragment_shop, container, false);

        val mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.addItemDecoration(GridSpacingItemDecoration(3, 10, true))
        mRecyclerView.layoutManager = GridLayoutManager(this.context, 3)

        RetrofitClient.instance.getSharableDesigns().enqueue(object: Callback<ArrayList<DesignResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DesignResponse>>,
                response: Response<ArrayList<DesignResponse>>
            ) {
                Log.i("HOME", "onRespond")

                val designList = response.body()
                val mRecyclerAdapter = designList?.let { ShopCardAdapter(it)  }

                mRecyclerView.adapter = mRecyclerAdapter
                mRecyclerAdapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<DesignResponse>>, t: Throwable) {
                Log.i("HOME", "onFailure")
                Toast.makeText(context, "Something went wrong fetching shop database!", Toast.LENGTH_LONG)
            }

        })

        return view
    }
}