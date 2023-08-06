package com.onewx2m.domain.usecase

import com.onewx2m.domain.enums.ReportType
import com.onewx2m.domain.repository.UserRepository
import javax.inject.Inject

class ReportUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        reportType: ReportType,
        reportTargetId: Int,
        reportedUserId: Int,
        content: String,
    ) = userRepository.report(
        reportType = reportType,
        reportTargetId = reportTargetId,
        reportedUserId = reportedUserId,
        content = content,
    )
}
