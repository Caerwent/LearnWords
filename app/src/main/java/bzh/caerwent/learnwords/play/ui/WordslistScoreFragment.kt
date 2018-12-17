package bzh.caerwent.learnwords.play.ui

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
import bzh.caerwent.learnwords.configure.ui.ListViewModelFactory
import bzh.caerwent.learnwords.databinding.WordslistScoreFragmentBinding
import kotlinx.android.synthetic.main.wordslist_score_fragment.*

open class WordslistScoreFragment : Fragment() {

    companion object {
        val ARG_SCORE: String = "SCORE"
        fun newInstance() = WordslistScoreFragment()
    }

    protected lateinit var mViewModel: WordslistScoreViewModel
    protected lateinit var mViewNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var score = arguments?.getInt(WordslistScoreFragment.ARG_SCORE) ?:0


        mViewModel = ViewModelProviders.of(this,
                ListViewModelFactory {
                    WordslistScoreViewModel(score)
                }).get(WordslistScoreViewModel::class.java)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<WordslistScoreFragmentBinding>(inflater, R.layout.wordslist_score_fragment, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = mViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewNavController = Navigation.findNavController(view)
        score_ok_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_wordslistScoreFragment_to_welcomeFragment))

    }



}
