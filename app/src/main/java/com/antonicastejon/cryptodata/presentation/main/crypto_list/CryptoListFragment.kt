package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.presentation.common.showMessage
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import com.antonicastejon.cryptodata.presentation.common.*
import com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView.PaginationScrollListener
import kotlinx.android.synthetic.main.crypto_list_fragment.*
import kotlinx.android.synthetic.main.crypto_list_fragment.view.*


val CRYPTO_LIST_FRAGMENT_TAG = CryptoListFragment::class.java.name

private val TAG = CryptoListFragment::class.java.name

fun newCryptoListFragment() = CryptoListFragment()

class CryptoListFragment : Fragment() {

    private lateinit var viewModel: CryptoListViewModel

    private val cryptoListAdapter by lazy { CryptoListRecyclerAdapter() }
    private var isLoading = false
    private var isLastPage = false

    private val stateObserver = Observer<ViewStateWithPagination<List<CryptoViewModel>>> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is DefaultPageState -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.updateData(it.data)
                }
                is LoadingPageState -> {
                    swipeRefreshLayout.isRefreshing = true
                    isLoading = true
                }
                is PaginatingState -> {
                    isLoading = true
                }
                is ErrorPageState -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.removeLoadingViewFooter()
                    showMessage((it as ErrorPageState).error)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CryptoListViewModel::class.java)
        observeViewModel()
        savedInstanceState?.let {
            viewModel.restoreCryptoList()
        } ?: viewModel.updateCryptoList()
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        initializeToolbar(view)
        initializeRecyclerView(view)
        initializeSwipeToRefreshView(view)
        return view
    }

    private fun initializeToolbar(view: View) {
        view.toolbar.title = getString(R.string.app_name)
    }

    private fun initializeRecyclerView(view: View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = cryptoListAdapter
            addOnScrollListener(OnScrollListener(linearLayoutManager))
        }
    }

    private fun initializeSwipeToRefreshView(view: View) {
        view.swipeRefreshLayout.setOnRefreshListener { viewModel.resetCryptoList() }
    }

    private fun loadNextPage() {
        cryptoListAdapter.addLoadingViewFooter()
        viewModel.updateCryptoList()
    }


    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = isLoading
        override fun loadMoreItems() = loadNextPage()
        override fun getTotalPageCount() = LIMIT_CRYPTO_LIST
        override fun isLastPage() = isLastPage
    }
}