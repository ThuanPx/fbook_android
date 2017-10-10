package com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.databinding.FragmentReturningandreturnedfragmentBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.approverequest.approvedetail.ApproveDetailActivity
import com.framgia.fbook.screen.approverequest.approvedetail.GetListUserListener
import com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest.ItemRequestClickListener
import com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest.ListRequestAdapter
import javax.inject.Inject

/**
 * ReturningAndReturnedFragment Screen.
 */
class ReturningAndReturnedFragmentFragment : BaseFragment(), ReturningAndReturnedFragmentContract.ViewModel,
    GetListUserListener.GetListUserReturnAndReturning, ItemRequestClickListener {

  @Inject
  internal lateinit var mPresenter: ReturningAndReturnedFragmentContract.Presenter
  @Inject
  internal lateinit var mListRequestAdapter: ListRequestAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerReturningAndReturnedFragmentComponent.builder()
        .approveDetailComponent((activity as ApproveDetailActivity).getApproveRequestComponent())
        .returningAndReturnedFragmentModule(ReturningAndReturnedFragmentModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentReturningandreturnedfragmentBinding>(inflater,
        R.layout.fragment_returningandreturnedfragment,
        container, false)
    binding.viewModel = this
    mListRequestAdapter.setItemRequestClickListener(this)
    return binding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is ApproveDetailActivity) {
      context.setGetListUserReturnAndReturning(this)
    }
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onGetListUserReturned(listUser: List<User>?) {
    mListRequestAdapter.updateData(listUser)
  }

  override fun onGetListUserReturning(listUser: List<User>?) {
    mListRequestAdapter.updateData(listUser)
  }

  override fun onItemRequestClick(pivot: User.Pivot?, isApprove: Boolean) {
   //TODO edit later
  }

  companion object {
    private val TAG = ReturningAndReturnedFragmentFragment::class.java.simpleName

    fun newInstance(): ReturningAndReturnedFragmentFragment {
      return ReturningAndReturnedFragmentFragment()
    }
  }
}
