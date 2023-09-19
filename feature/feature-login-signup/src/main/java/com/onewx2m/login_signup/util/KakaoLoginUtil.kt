package com.onewx2m.login_signup.util

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginUtil {

    fun kakaoLogin(context: Context, onSuccess: (token: String) -> Unit, onFail: (error: Throwable?) -> Unit) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    kakaoAccountLogin(context, onSuccess, onFail)
                } else if (token != null) {
                    onSuccess(token.accessToken)
                }
            }
        } else {
            kakaoAccountLogin(context, onSuccess, onFail)
        }
    }

    private fun kakaoAccountLogin(context: Context, onSuccess: (token: String) -> Unit, onFail: (error: Throwable?) -> Unit) {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = { token, error ->
            if (error != null) {
                onFail(error)
            } else if (token != null) {
                onSuccess(token.accessToken)
            }
        })
    }
}
