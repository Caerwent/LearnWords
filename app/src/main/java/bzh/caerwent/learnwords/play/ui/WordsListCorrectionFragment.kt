package bzh.caerwent.learnwords.play.ui

import android.os.Bundle
import android.view.View
import bzh.caerwent.learnwords.R
import kotlinx.android.synthetic.main.wordslist_exercice_fragment.*

class WordsListCorrectionFragment : WordslistExerciceFragment() {

    companion object {
        fun newInstance() = WordsListCorrectionFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        correction_label.visibility = View.VISIBLE

    }

    override fun doOnNext() {
        var idx = mViewModel.getCurrentIndex()?.value ?: 0;
        var nb = mViewModel.getSession().words.size
        if (idx < nb) {
            mViewModel.incrCurrentIndex()
        } else {
            mViewNavController.navigate(R.id.action_wordsListCorrectionFragment_to_welcomeFragment)
        }
    }

}
