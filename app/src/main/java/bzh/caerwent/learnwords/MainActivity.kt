package bzh.caerwent.learnwords

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val rxPermissions by lazy {
        RxPermissions(this)
    }

    override fun onResume() {
        super.onResume()
        rxPermissions
                .request(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M

                    } else {
                        AlertDialog.Builder(applicationContext)
                                .setTitle(getString(R.string.app_permission_dialog_title))
                                .setMessage(getString(R.string.app_permission_dialog_msg))
                                .setPositiveButton(getString(R.string.app_permission_dialog_ok_btn), { dialog, which -> finish() })
                                .create()
                                .show()
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_toolbar)

// Set up the ActionBar to stay in sync with the NavController
        NavigationUI.setupActionBarWithNavController(this, NavHostFragment.findNavController(activity_content))

    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(activity_content).navigateUp()


}
