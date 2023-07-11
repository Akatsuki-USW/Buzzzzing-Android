package com.onewx2m.core_ui.util

object BuzzzzingUser {
    var email: String = ""
        private set

    var nickname: String = ""
        private set

    var profileImageUrl: String = ""
        private set

    fun setInfo(email: String, nickname: String, profileImageUrl: String) {
        this.email = email
        this.nickname = nickname
        this.profileImageUrl = profileImageUrl
    }
}
