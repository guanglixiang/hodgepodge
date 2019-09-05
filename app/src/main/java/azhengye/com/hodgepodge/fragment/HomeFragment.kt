package azhengye.com.hodgepodge.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import azhengye.com.hodgepodge.R
import azhengye.com.hodgepodge.activity.ImageTransformationActivity
import azhengye.com.hodgepodge.activity.MixStringWithEditTextActivity
import azhengye.com.hodgepodge.activity.PinballAnimationActivity
import azhengye.com.hodgepodge.fragment.adapter.HomeRecyclerViewAdapter

/**
 * Created by azhengye on 16/8/19.
 */
class HomeFragment : BaseFragment(), HomeRecyclerViewAdapter.onItemClickListener {

    var recyclerView: RecyclerView? = null
    var homeRecyclerViewAdapter: HomeRecyclerViewAdapter? = null
    var data: ArrayList<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment_layout, container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        initData()

        initView()
    }

    private fun initData() {
        data = ArrayList();
        data?.add("弹球动画")
        data?.add("文字 EditText 混排")
        data?.add("图片镜像")
    }

    private fun initView() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(context!!, data!!)
        homeRecyclerViewAdapter?.listener = this

        recyclerView?.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?

        recyclerView?.adapter = homeRecyclerViewAdapter
    }

    override fun onItemClick(itemView: View, position: Int) {
        when(position){
            0 ->  {
                val intent = Intent(context, PinballAnimationActivity::class.java)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(context, MixStringWithEditTextActivity::class.java)
                startActivity(intent)
            }
            2 ->{
                val intent = Intent(context, ImageTransformationActivity::class.java)
                startActivity(intent)
            }
        }

    }
}