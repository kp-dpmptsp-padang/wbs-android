package com.example.wbsdpmptsp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wbsdpmptsp.di.Injection
import com.example.wbsdpmptsp.repository.HistoryRepository
import com.example.wbsdpmptsp.repository.NotificationRepository
import com.example.wbsdpmptsp.repository.ReportRepository
import com.example.wbsdpmptsp.repository.UserRepository
import com.example.wbsdpmptsp.ui.auth.login.LoginViewModel
import com.example.wbsdpmptsp.ui.auth.register.RegisterViewModel
import com.example.wbsdpmptsp.ui.history.HistoryViewModel
import com.example.wbsdpmptsp.ui.notification.NotificationViewModel
import com.example.wbsdpmptsp.ui.profile.ProfileViewModel
import com.example.wbsdpmptsp.ui.report.ReportViewModel

class ViewModelFactory private constructor(
    private val userRepo: UserRepository,
    private val historyRepo: HistoryRepository,
    private val notifRepo: NotificationRepository,
    private val reportRepo: ReportRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepo) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepo) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepo) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepo) as T
            }
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(notifRepo) as T
            }
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(reportRepo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.provideHistoryRepository(context),
                    Injection.provideNotificationRepository(context),
                    Injection.provideReportRepository(context)
                )
            }.also { instance = it }
    }
}