package com.txwstudio.drinkitclient.ui.activity.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.txwstudio.drinkitclient.R
import com.txwstudio.drinkitclient.Util
import com.txwstudio.drinkitclient.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val FIREBASE_TOKEN_LABEL = "firebaseTokenLabel"
    }

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainActivityViewModel

        setupToolBar()
        subscribeUi()

        mainActivityViewModel.getToken()

//        val db = Firebase.firestore
//        val docRef = db.collection("bottleStatus").document("7HQ26j7APOAghhMip5xH")
//        docRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null && snapshot.exists()) {
//                Log.d(TAG, "Current data: ${snapshot.data}")
//            } else {
//                Log.d(TAG, "Current data: null")
//            }
//        }


    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.test()
    }

    override fun onPause() {
        super.onPause()
        mainActivityViewModel.remove()
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.toolbarMainActivity)
        supportActionBar?.title = getString(R.string.main_activity)
    }

    private fun subscribeUi() {
        mainActivityViewModel.isBottleExists.observe(this) {
            val googleRed = ContextCompat.getColor(this, R.color.googleRed);
            val googleYellow = ContextCompat.getColor(this, R.color.googleYellow);
            val googleGreen = ContextCompat.getColor(this, R.color.googleGreen);

            if (it) {
                binding.imageView.imageTintList = ColorStateList.valueOf(googleYellow)
                binding.textView2.text = getString(R.string.bottleStatus_exists)
            } else {
                binding.imageView.imageTintList = ColorStateList.valueOf(googleGreen)
                binding.textView2.text = getString(R.string.bottleStatus_notExists)
            }
        }

        binding.fabMainActivityCopyUid.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText(
                FIREBASE_TOKEN_LABEL,
                mainActivityViewModel.firebaseToken.value
            )
            clipboard.setPrimaryClip(clip)

            Util().snackBarShort(coordinatorLayout_main, R.string.snackBar_copy_success)
        }
    }
}