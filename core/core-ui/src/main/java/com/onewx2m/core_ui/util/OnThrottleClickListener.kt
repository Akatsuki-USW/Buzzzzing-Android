package com.onewx2m.core_ui.util

import android.view.View

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300L,  
) : View.OnClickListener {  
  
    private var clicked = false  
    override fun onClick(v: View?) {  
        if (!clicked) {  
            clicked = true  
            v?.run {  
                postDelayed(  
                    { clicked = false },  
                    interval,  
                )  
                clickListener.onClick(v)  
            }  
        }
    }  
}