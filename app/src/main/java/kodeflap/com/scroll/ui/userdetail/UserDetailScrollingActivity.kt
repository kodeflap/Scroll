package kodeflap.com.scroll.ui.userdetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kodeflap.com.scroll.R
import kodeflap.com.scroll.databinding.ActivityDetailBinding
import kodeflap.com.scroll.ui.home.HomeActivity
import kodeflap.com.scroll.utils.AppConstant
import kodeflap.com.scroll.utils.NetworkUtils
import kodeflap.com.scroll.utils.State
import kodeflap.com.scroll.utils.getColorRes
import kodeflap.com.scroll.utils.hide
import kodeflap.com.scroll.utils.show
import kodeflap.com.scroll.utils.showToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class UserDetailScrollingActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var dataBind: ActivityDetailBinding
    private lateinit var viewModel: UserDetailViewModel
    private val factory: UserDetailViewModelFactory by instance()
    private var userId: Int? = null
    private var userImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        // setSupportActionBar(toolbar)
        setupUI()
        handleNetworkChanges()
        setupViewModel()
        setupAPICall()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupUI() {

        if (intent.hasExtra(AppConstant.INTENT_IMAGE) && intent.getStringExtra(AppConstant.INTENT_IMAGE) != null)
            userImage = intent.getStringExtra(AppConstant.INTENT_IMAGE)!!
        if (intent.hasExtra(AppConstant.INTENT_USER_ID)) {
            userId = intent.getIntExtra(AppConstant.INTENT_USER_ID, -1)
        }

//Log.d("xggffgdfgdgd-----------", userId.toString())
        Glide.with(this).load(userImage)
            .centerCrop()
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(dataBind.image)
        dataBind.toolbar.title = "User Token: ${userId.toString()}"

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(UserDetailViewModel::class.java)
    }

    private fun setupAPICall() {
        viewModel.userDetailLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    dataBind.progressBar.show()
                    dataBind.cardViewUserDetail.hide()
                }

                is State.Success -> {
                    dataBind.progressBar.hide()
                    dataBind.cardViewUserDetail.show()
                    state.data.let {
                      //  Log.d("UserDetail", "Received Data: First Name: ${it.firstName}, Last Name: ${it.lastName}, Age: ${it.age}")
                        dataBind.textFname.text = "First Name: ${it.firstName}"
                        dataBind.textLastname.text = "Last Name: ${it.lastName}"
                        dataBind.textAge.text = "Age: ${it.age}"
                        dataBind.textPhn.text = "Phone: ${it.phone}"
                        dataBind.textGender.text = "Gender: ${it.gender}"
                    }
                }

                is State.Error -> {
                    dataBind.progressBar.hide()
                    dataBind.cardViewUserDetail.hide()

                    if (state.message.contains("404")) {
                        showToast("User with ID $userId not found")
                    } else {
                        showToast(state.message)
                    }
                }
            }
        })

        userId?.let { getUsersDetail(it) }

    }


    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {
                dataBind.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                dataBind.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (viewModel.userDetailLiveData.value is State.Error) {
                    userId?.let { getUsersDetail(it) }
                }
                dataBind.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                dataBind.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(HomeActivity.ANIMATION_DURATION)
                        .setDuration(HomeActivity.ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
    }

    private fun getUsersDetail(id: Int) {
        viewModel.getUserDetail(id)
    }


}