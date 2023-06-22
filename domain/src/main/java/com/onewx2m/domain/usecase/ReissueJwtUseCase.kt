package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.AuthRepository
import javax.inject.Inject

class ReissueJwtUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(request: String) = authRepository.reIssueBuzzzzingJwt(request)
}
