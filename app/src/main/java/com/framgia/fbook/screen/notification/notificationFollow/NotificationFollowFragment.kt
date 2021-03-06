package com.framgia.fbook.screen.notification.notificationFollow

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.ItemNotification
import com.framgia.fbook.data.model.Notification
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentNotificationfollowBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.notification.ItemNotificationClickListener
import com.framgia.fbook.screen.notification.NotificationAdapter
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * NotificationFollow Screen.
 */
class NotificationFollowFragment : BaseFragment(), NotificationFollowContract.ViewModel, NotificationFollowListener, ItemNotificationClickListener {

  @Inject
  internal lateinit var mPresenter: NotificationFollowContract.Presenter
  @Inject
  internal lateinit var mNotificationAdapter: NotificationAdapter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mDialogManager: DialogManager
  private var mPosition = 0

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerNotificationFollowComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .notificationFollowModule(
            NotificationFollowModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentNotificationfollowBinding>(inflater,
        R.layout.fragment_notificationfollow, container,
        false)
    binding.viewModel = this
    mNotificationAdapter.setItemNotificationClickListener(this)
    return binding.root
  }


  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if(context is MainActivity){
      context.setNotificationFollowListener(this)
    }
  }

  override fun onError(error: BaseException) {
    Log.e(TAG, error.getMessageError())
  }

  override fun onUpdateNotificationSuccess() {
    mNotificationAdapter.updateItem(mPosition)
  }

  override fun getNotificationFollow(notification: Notification?) {
    mNotificationAdapter.updateData(notification?.listNotification, TYPE_FOLLOW)
  }

  override fun onItemNotificationClick(itemNotification: ItemNotification?, position: Int) {
    itemNotification?.let {
      if (itemNotification.viewed == 0) {
        mPosition = position
        mPresenter.updateNotification(itemNotification.id)
      }
      val bundle = Bundle()
      itemNotification.book?.id?.let {
          bundle.putInt(Constant.BOOK_DETAIL_EXTRA, it)
        }
        mNavigator.startActivity(BookDetailActivity::class.java, bundle)
      }
    }

  companion object {
    private val TYPE_FOLLOW = 1
    private val TAG = NotificationFollowFragment::class.java.simpleName

    fun newInstance(): NotificationFollowFragment {
      return NotificationFollowFragment()
    }
  }
}
