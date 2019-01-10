package bzh.caerwent.learnwords.play.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import bzh.caerwent.learnwords.R
import bzh.caerwent.learnwords.configure.ui.ListViewModelFactory
import bzh.caerwent.learnwords.play.data.model.WordsListSession
import kotlinx.android.synthetic.main.wordslist_exercice_fragment.*

class WordsListCorrectionFragment : WordslistExerciceFragment() {

    companion object {
        fun newInstance() = WordsListCorrectionFragment()
    }

    override fun createViewModel(session: WordsListSession): WordslistExerciceViewModel {
        return ViewModelProviders.of(this,
                ListViewModelFactory {
                    WordslistCorrectionViewModel(session)
                }).get(WordslistCorrectionViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        correction_label.visibility = View.VISIBLE
        exercice_correct_choice.visibility = View.VISIBLE
        response_editor.isEnabled = false
        mViewModel.getCurrentIndex().observe(this, Observer<Int> { idx -> onNextIdx(idx) })

    }

    protected fun onNextIdx(idx: Int) {
        if (mViewModel.getSession().useInput) {
            response_input.setText(mViewModel.getSession().words.get(idx).response)
            exercice_correct_choice.isChecked = mViewModel.getSession().words.get(idx).response.compareTo(mViewModel.getSession().words.get(idx).item, true)==0

        }
        else
        {
            exercice_correct_choice.isChecked = false
        }
    }

    override fun navigateToEndOfSession() {
        var bundle: Bundle = Bundle()

        var nb = mViewModel.getSession().words.size
        if (mViewModel.getSession().maxItems > 0 && mViewModel.getSession().maxItems < nb) {
            nb = mViewModel.getSession().maxItems
        }
        var score = ((mViewModel as WordslistCorrectionViewModel).getScore() * 20) / nb
        bundle.putSerializable(WordslistScoreFragment.ARG_SCORE, score)
        mViewNavController.navigate(R.id.action_wordsListCorrectionFragment_to_wordslistScoreFragment, bundle)
    }

    override fun doOnNext() {
        if (exercice_correct_choice.isChecked) {
            (mViewModel as WordslistCorrectionViewModel).incrScore()
        }
        super.doOnNext()
    }

}
