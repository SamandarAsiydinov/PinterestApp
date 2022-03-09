package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.RetrofitGetAdapter
import uz.context.pinterestapp.adapter.RetrofitGetAdapter2
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.networking.RetrofitHttp

class Fragment6 : Fragment() {

    var photos = ArrayList<ResponseItem>()
    lateinit var recyclerView6: RecyclerView
    lateinit var swipeRefreshLayout6: SwipeRefreshLayout
    lateinit var progressBar6: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_6, container, false)

        recyclerView6 = view.findViewById(R.id.recyclerView6)
        swipeRefreshLayout6 = view.findViewById(R.id.swipe_refresh6)
        progressBar6 = view.findViewById(R.id.progress_bar6)

        apiPosterListRetrofitFragment6()

        swipeRefreshLayout6.setOnRefreshListener {
            swipeRefreshLayout6.isRefreshing = false
            photos.clear()
            apiPosterListRetrofitFragment6()
        }

        recyclerView6.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView6.layoutManager = layoutManager

        return view
    }

    fun apiPosterListRetrofitFragment6(){
        progressBar6.visibility = View.VISIBLE
        RetrofitHttp.posterService.listPhotos6().enqueue(object :
            Callback<ArrayList<ResponseItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                photos.clear()
                if (response.body() != null)
                    photos.addAll(response.body()!!)
                else
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout6.isRefreshing = false
                progressBar6.visibility = View.GONE
                refreshAdapter(photos)
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Log.d("@@@",t.message.toString())
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                progressBar6.visibility = View.GONE
            }

        })
    }


    fun refreshAdapter(photos: ArrayList<ResponseItem>){
        val homeTwoAdapter = RetrofitGetAdapter2(requireContext(),photos)
        recyclerView6.adapter = homeTwoAdapter

        //adapterdan fragmentga intent qilish
        homeTwoAdapter.itemCLick = {
            Log.d("@@@","XATOLIK")



            findNavController().navigate(R.id.detailFragment)
        }
    }
}