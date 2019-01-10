package bzh.caerwent.learnwords.play.ui

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
import androidx.recyclerview.widget.LinearLayoutManager
import bzh.caerwent.learnwords.R
import bzh.caerwent.learnwords.common.data.WordsListProvider
import bzh.caerwent.learnwords.databinding.WordsListSelectionFragmentBinding
import bzh.caerwent.learnwords.play.data.model.WordsListSession
import bzh.caerwent.learnwords.play.data.model.WordsListSessionItem
import kotlinx.android.synthetic.main.words_list_selection_fragment.*
import java.util.*

class WordsListSelectionFragment : Fragment() {

    companion object {
        fun newInstance() = WordsListSelectionFragment()
    }

    protected var mViewNavController: NavController? = null
    private lateinit var mViewModel: WordsListSelectionViewModel

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
                mViewModel.mMaxItems = result.toInt()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(WordsListSelectionViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<WordsListSelectionFragmentBinding>(inflater, R.layout.words_list_selection_fragment, container, false)
        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        binding.viewModel = mViewModel




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewNavController = Navigation.findNavController(view)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mViewModel.getAdapter()

        WordsListProvider.INSTANCE.getGroups().observe(viewLifecycleOwner, Observer<MutableList<String>> { list: MutableList<String> ->
            mViewModel.getAdapter().setData(list)
        })
        selection_launch_btn.setOnClickListener { onValidation() }
        selection_input_choice.setOnCheckedChangeListener({ button, checked -> mViewModel.mUseInput = checked })
        selection_max_input.addTextChangedListener(mAddItemTextWatcher)
    }

    protected fun onValidation() {
        var selectionMap = mViewModel.getAdapter().getDataSelection()
        var session = WordsListSession()
        session.useInput = mViewModel.mUseInput
        session.maxItems = mViewModel.mMaxItems
        for ((k, v) in selectionMap) {
            if (v) {

                var itemsListForGroup = WordsListProvider.INSTANCE.getListForGroupData(k)
                for (item in itemsListForGroup) {
                    var newSessionItem = WordsListSessionItem(k, item)
                    session.words.add(newSessionItem)
                }
            }
        }
        if (session.words.size > 0) {
            session.words.shuffle(Random(System.currentTimeMillis()))
            var bundle: Bundle = Bundle()
            bundle.putSerializable(WordslistExerciceFragment.ARG_SESSION, session)
            mViewNavController?.navigate(R.id.action_wordsListSelectionFragment_to_wordslistExerciceFragment, bundle)
        }
    }


}
