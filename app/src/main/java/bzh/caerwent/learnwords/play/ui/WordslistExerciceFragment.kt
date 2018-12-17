package bzh.caerwent.learnwords.play.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import bzh.caerwent.learnwords.R
import bzh.caerwent.learnwords.configure.ui.ListViewModelFactory
import bzh.caerwent.learnwords.databinding.WordslistExerciceFragmentBinding
import bzh.caerwent.learnwords.play.data.model.WordsListSession
import kotlinx.android.synthetic.main.wordslist_exercice_fragment.*

open class WordslistExerciceFragment : Fragment() {

    companion object {
        val ARG_SESSION: String = "SESSION"
        fun newInstance() = WordslistExerciceFragment()
    }

    protected lateinit var mViewModel: WordslistExerciceViewModel
    protected lateinit var mViewNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var session = arguments?.getSerializable(WordslistExerciceFragment.ARG_SESSION) as WordsListSession


        mViewModel = createViewModel(session)
    }

    protected open fun createViewModel(session: WordsListSession) : WordslistExerciceViewModel
    {
        return ViewModelProviders.of(this,
                ListViewModelFactory {
                    WordslistExerciceViewModel(session)
                }).get(WordslistExerciceViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<WordslistExerciceFragmentBinding>(inflater, R.layout.wordslist_exercice_fragment, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = mViewModel
        mViewModel.getCurrentIndex().observe(this, Observer<Int> { idx -> mViewModel.play() })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        correction_label.visibility = View.GONE

        mViewNavController = Navigation.findNavController(view)
        exercice_next_btn.setOnClickListener({ doOnNext() })
        mViewModel.play()
    }

    protected open fun navigateToEndOfSession() {
        var bundle: Bundle = Bundle()
        bundle.putSerializable(WordslistExerciceFragment.ARG_SESSION, mViewModel.getSession())
        mViewNavController.navigate(R.id.action_wordslistExerciceFragment_to_wordsListCorrectionFragment, bundle)
    }
    protected open fun doOnNext() {
        var idx = mViewModel.getCurrentIndex()?.value ?: 0;
        var nb = mViewModel.getSession().words.size

        if(mViewModel.getSession().useInput)
        {
            mViewModel.getSession().words.get(idx).response = response_input.text.toString()
            response_input.setText("")
        }
        if (idx < nb-1) {
            mViewModel.incrCurrentIndex()
        } else {
            navigateToEndOfSession()
        }
    }

}
