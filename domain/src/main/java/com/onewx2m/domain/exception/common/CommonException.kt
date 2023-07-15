package com.onewx2m.domain.exception.common // ktlint-disable filename

sealed class CommonException(val snackBarMessage: String) : RuntimeException() {
    class BannedUserException(override val message: String = "정지된 유저에게는 권한이 없습니다.") : CommonException(message)
    class BlackListUserException(override val message: String = "블랙리스트 유저에게는 권한이 없습니다.") :
        CommonException(message)
    class NeedLoginException(override val message: String = "자동 로그인 기간이 끝났어요. 다시 로그인 해주세요.") : CommonException(message)
    class NetworkException(override val message: String = "서버 또는 네트워크 연결 상태가 불안정해요.") : CommonException(message)
    class ServerException(override val message: String = "복쟉복쟉 서버에서 동작을 수행할 수 없습니다.") : CommonException(message)
    class UnknownException(override val message: String = "알 수 없는 에러가 발생했어요.", val throwable: Throwable? = null) : CommonException(message)
}
