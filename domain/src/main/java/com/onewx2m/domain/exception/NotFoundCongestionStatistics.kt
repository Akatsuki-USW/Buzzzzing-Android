package com.onewx2m.domain.exception

class NotFoundCongestionStatistics(override val message: String = "문제가 있어 혼잡도 통계를 불러올 수 없어요.") :
    RuntimeException()
