package com.example.hudie.activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hudie.R
import com.example.hudie.activities.ui.dashboard.DashboardFragment
import com.example.hudie.adapters.ShopCardAdapter
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.DesignResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    companion object {
        fun newInstance(): HomeFragment = HomeFragment();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false);

        val mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        RetrofitClient.instance.getDesigns().enqueue(object: Callback<ArrayList<DesignResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DesignResponse>>,
                response: Response<ArrayList<DesignResponse>>
            ) {
                val designList = response.body()
                val mRecyclerAdapter = designList?.let { ShopCardAdapter(it) }

                mRecyclerView.adapter = mRecyclerAdapter
                mRecyclerAdapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<DesignResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return view
    }
}