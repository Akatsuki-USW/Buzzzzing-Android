package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke() = notificationRepository.getNotificationList()
}
