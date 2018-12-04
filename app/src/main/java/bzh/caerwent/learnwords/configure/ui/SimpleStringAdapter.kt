package bzh.caerwent.learnwords.configure.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bzh.caerwent.learnwords.R
import kotlinx.android.synthetic.main.conf_words_list_row_item.view.*

class SimpleStringAdapter () : RecyclerView.Adapter<SimpleStringAdapter.VH>() {

    interface OnAdapterClickListener {
        public fun onClick(position:Int)
    }
    private var items= MutableList<String>(0, {""})
    private lateinit var mClickListener: OnAdapterClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener({ mClickListener?.onClick(position)})
    }

    override fun getItemCount(): Int = items.size

    fun setClickListener(aClickListener: OnAdapterClickListener)
    {
        mClickListener=aClickListener
    }

    fun setData(aList:MutableList<String>)
    {
        items=aList;
        notifyDataSetChanged()
    }


    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.conf_words_list_row_item, parent, false)) {

        fun bind(name: String) = with(itemView) {
            item.text = name
        }
    }
}
