package com.framgia.fbook.screen.notification.notificationUser

import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.response.NotificationResponse
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface NotificationContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(error: BaseException)

    fun onGetNotificationSuccess(notificationResponse: NotificationResponse?)

    fun onUpdateNotificationSuccess()

    fun onReadAllNotificationSuccess()

    fun onShowProgressDialog()

    fun onDismissProgressDialog()

    fun onIsNotRefresh(): Boolean
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getNotification()

    fun updateNotification(id: Int?)

    fun readAllNotificationOfUser()
  }
}
