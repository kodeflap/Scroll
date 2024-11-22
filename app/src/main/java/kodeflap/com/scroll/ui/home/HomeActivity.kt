package kodeflap.com.scroll.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kodeflap.com.scroll.R
import kodeflap.com.scroll.databinding.ActivityHomeBinding
import kodeflap.com.scroll.ui.adapter.CustomAdapterMovies
import kodeflap.com.scroll.ui.userdetail.UserDetailScrollingActivity
import kodeflap.com.scroll.utils.AppConstant
import kodeflap.com.scroll.utils.NetworkUtils
import kodeflap.com.scroll.utils.RecyclerItemClickListener
import kodeflap.com.scroll.utils.State
import kodeflap.com.scroll.utils.dismissKeyboard
import kodeflap.com.scroll.utils.getColorRes
import kodeflap.com.scroll.utils.hide
import kodeflap.com.scroll.utils.show
import kodeflap.com.scroll.utils.showToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    override val kodein by kodein()
    private lateinit var dataBind: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()
    private lateinit var customAdapterMovies: CustomAdapterMovies
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setupViewModel()
        setupUI()
        initializeObserver()
        handleNetworkChanges()
        setupAPICall()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            queryHint = "Search"
            isSubmitButtonEnabled = true
            onActionViewExpanded()
        }
        search(searchView)
        return true
    }

    private fun setupUI() {
        customAdapterMovies = CustomAdapterMovies()
        dataBind.recyclerViewMovies.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = customAdapterMovies
            addOnItemTouchListener(
                RecyclerItemClickListener(
                    applicationContext,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            if (customAdapterMovies.getData().isNotEmpty()) {
                                val searchItem = customAdapterMovies.getData()[position]
                                searchItem?.let {
                                    val intent =
                                        Intent(
                                            applicationContext,
                                            UserDetailScrollingActivity::class.java
                                        )
                                    intent.putExtra(AppConstant.INTENT_USER_ID, it.id)
                                    Log.d("xggffgdfgdgd-----------", id.toString())
                                    intent.putExtra(AppConstant.INTENT_IMAGE, it.image)
                                    startActivity(intent)
                                }

                            }
                        }

                    })
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val visibleItemCount = layoutManager!!.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    viewModel.checkForLoadMoreItems(
                        visibleItemCount,
                        totalItemCount,
                        firstVisibleItemPosition
                    )

                }

            })
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewModel.getUsers()
    }

    private fun initializeObserver() {
        viewModel.userNameLiveData.observe(this, Observer {
            Log.i("Info", "Movie Name = $it")
        })
        viewModel.loadMoreListLiveData.observe(this, Observer {
            if (it) {
                customAdapterMovies.setData(null)
                Handler().postDelayed({
                    viewModel.loadMore()
                }, 2000)
            }
        })
    }

    private fun setupAPICall() {
        viewModel.userLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    // Hide the RecyclerView and show the ProgressBar while loading data
                    dataBind.recyclerViewMovies.hide()
                    dataBind.linearLayoutSearch.hide()
                    dataBind.progressBar.show()
                }
                is State.Success -> {
                    // Show the RecyclerView with data after the loading completes
                    dataBind.recyclerViewMovies.show()
                    dataBind.linearLayoutSearch.hide()
                    dataBind.progressBar.hide()
                    customAdapterMovies.setData(state.data) // Update RecyclerView with fetched data
                }
                is State.Error -> {
                    // Hide the progress bar and show the error message if there's an issue
                    dataBind.progressBar.hide()
                    showToast(state.message)
                }
            }
        })
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
                if (viewModel.userLiveData.value is State.Error || customAdapterMovies.itemCount == 0) {
                    viewModel.getUsers()
                }
                dataBind.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                dataBind.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
    }

    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                dismissKeyboard(searchView)
                searchView.clearFocus()
                viewModel.searchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchMovie(newText)
                }
                return true
            }
        })
    }


}