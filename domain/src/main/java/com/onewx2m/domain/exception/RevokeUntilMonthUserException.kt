package com.onewx2m.domain.exception

class RevokeUntilMonthUserException(override val message: String = "탈퇴일로부터 30일 뒤에 회원 가입을 할 수 있습니다.") : RuntimeException()
