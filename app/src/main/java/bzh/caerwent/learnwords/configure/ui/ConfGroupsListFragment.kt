package bzh.caerwent.learnwords.configure.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bzh.caerwent.learnwords.R
import bzh.caerwent.learnwords.databinding.ConfGroupsListFragmentBinding
import bzh.caerwent.learnwords.utils.ui.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.conf_groups_list_fragment.*


class ConfGroupsListFragment : Fragment() {

    companion object {
        fun newInstance() = ConfGroupsListFragment()
    }


    protected var mViewNavController: NavController? = null
    private lateinit var mViewModel: ConfGroupsListViewModel
    private var mAdapter = SimpleStringAdapter()

    private var mAddItemTextWatcher = object : TextWatcher {

        override fun beforeTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {

        }

        override fun onTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {

        }

        override fun afterTextChanged(var1: Editable) {
            val result = var1.toString().trim()
            if (!var1.toString().equals(result)) {
                var1.clear()
                var1.append(result)
            } else {
                canAddItem(var1.toString())
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(ConfGroupsListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding = DataBindingUtil.inflate<ConfGroupsListFragmentBinding>(inflater, R.layout.conf_groups_list_fragment, container, false)
        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        binding.viewModel = mViewModel

        mAdapter.setClickListener(object : SimpleStringAdapter.OnAdapterClickListener {
            public override fun onClick(position: Int) {
                onItemClick(position)
            }
        })



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewNavController = Navigation.findNavController(view)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        name_input.addTextChangedListener(mAddItemTextWatcher)
        add_button.isEnabled = false
        add_button.setOnClickListener({ addItem() })
        mViewModel.getWordsList().observe(viewLifecycleOwner, Observer<MutableList<String>> { list: MutableList<String> ->
            mAdapter.setData(list)
        })
    }

    fun canAddItem(aItemName: String) {
        add_button.isEnabled = mViewModel.canAddItem(aItemName)
    }

    fun removeItem(aPosition: Int) {
        mViewModel.removeItem(mViewModel.getWordsList().value?.get(aPosition)!!)
    }

    fun addItem() {
        mViewModel.addItem(name_input.editableText.toString())
    }

    fun onItemClick(aPosition: Int) {
        var bundle: Bundle = Bundle()
        bundle.putSerializable(ConfRecordWordFragment.ARG_ID, mViewModel.getWordsList().value?.get(aPosition))
        mViewNavController?.navigate(R.id.action_confGroupsListFragment_to_confWordsListFragment, bundle)
    }
}