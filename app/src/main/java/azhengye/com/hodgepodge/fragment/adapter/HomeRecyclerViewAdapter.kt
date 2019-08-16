package azhengye.com.hodgepodge.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import azhengye.com.hodgepodge.R

/**
 * Created by azhengye on 2019-08-16.
 */
open class HomeRecyclerViewAdapter(private val context: Context, private val data: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: onItemClickListener? = null

    interface onItemClickListener {
        fun onItemClick(itemView: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.home_rv_item, parent, false));
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            holder.textView.setText(data.get(position))
            holder.itemView.setOnClickListener(View.OnClickListener {
                listener?.onItemClick(holder.itemView, position)
            })
        }
    }

    inner class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.content)
    }

}