package bzh.caerwent.learnwords.configure.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import bzh.caerwent.learnwords.R
import bzh.caerwent.learnwords.databinding.ConfRecordWordFragmentBinding
import kotlinx.android.synthetic.main.conf_record_word_fragment.*

class ConfRecordWordFragment : Fragment() {

    companion object {
        val ARG_ID: String = "ID"
        val ARG_GROUP_ID: String = "GROUP_ID"
        fun newInstance() = ConfRecordWordFragment()
    }

    private lateinit var mViewModel: ConfRecordWordViewModel
    protected lateinit var mViewNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, ListViewModelFactory {
            ConfRecordWordViewModel(arguments?.getString(ARG_ID)
                    ?: "", arguments?.getString(ARG_GROUP_ID) ?: "")
        }).get(ConfRecordWordViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<ConfRecordWordFragmentBinding>(inflater, R.layout.conf_record_word_fragment, container, false)

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        binding.viewModel = mViewModel



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mViewNavController = Navigation.findNavController(view)
        btn_ok.setOnClickListener({ mViewNavController.popBackStack() })
    }


}
