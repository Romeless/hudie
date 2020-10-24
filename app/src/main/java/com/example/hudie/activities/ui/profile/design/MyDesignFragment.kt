package com.example.hudie.activities.ui.profile.design

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.adapters.ShopCardAdapter
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyDesignFragment : Fragment() {


    companion object {
        fun newInstance(): MyDesignFragment = MyDesignFragment();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MyDesign", "onCreateView")

        var setting = this.activity?.getSharedPreferences("Hudie", 0);
        var userId = setting?.getString("id", "0")?.toInt()

        val view = inflater.inflate(R.layout.fragment_my_design, container, false);

        val mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        mRecyclerView.layoutManager = layoutManager

        RetrofitClient.instance.userDesign(userId!!).enqueue(object: Callback<ArrayList<DesignResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DesignResponse>>,
                response: Response<ArrayList<DesignResponse>>
            ) {
                Log.i("MyDesign", "onRespond")

                val designList = response.body()
                val mRecyclerAdapter = designList?.let { ShopCardAdapter(it) }

                mRecyclerView.adapter = mRecyclerAdapter
                mRecyclerAdapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<DesignResponse>>, t: Throwable) {
                Log.i("MyDesign", "onFailure")
                TODO("Not yet implemented")
            }

        })

        return view
    }
}