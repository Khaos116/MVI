package com.cc.mvi.ui.splash

import android.os.Bundle
import com.cc.mvi.databinding.ActivitySplashBinding
import com.cc.mvi.ui.base.BaseActivity

/**
 * Author:Khaos116
 * Date:2024/6/4
 * Time:14:12
 */
class SplashActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
  }
}