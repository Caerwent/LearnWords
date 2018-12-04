package bzh.caerwent.learnwords.play.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import bzh.caerwent.learnwords.R
import kotlinx.android.synthetic.main.play_select_words_list_row_item.view.*

class SimpleStringSelectionAdapter() : RecyclerView.Adapter<SimpleStringSelectionAdapter.VH>() {

    private var items: MutableMap<String, Boolean> = mutableMapOf()

    private var nbChecked = MutableLiveData<Int>()

    init {
        nbChecked.value = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var key = items.keys.elementAt(position)
        holder.bind(key, items.get(key) ?: false, position, ::clickOn)

    }

    override fun getItemCount(): Int = items.size

    fun clickOn(aPosition: Int, isSelected: Boolean) {
        var key = items.keys.elementAt(aPosition)
        items.put(key, isSelected)
        if (isSelected)
            nbChecked.value = nbChecked.value!! + 1
        else
            nbChecked.value = nbChecked.value!! - 1

    }

    fun setData(aList: MutableList<String>) {
        items.clear();
        aList.forEach {
            items.put(it, false)
        }
        nbChecked.value = 0
        notifyDataSetChanged()
    }

    fun getDataSelection(): MutableMap<String, Boolean> {
        return items
    }


    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.play_select_words_list_row_item, parent, false)) {

        fun bind(name: String, isSelected: Boolean, position: Int, handler: (m: Int, isSelected: Boolean) -> Unit) = with(itemView) {
            item.text = name
            item.isChecked = isSelected
            item.setOnCheckedChangeListener({ btn, isChecked -> handler(position, isChecked) })

        }
    }
}
