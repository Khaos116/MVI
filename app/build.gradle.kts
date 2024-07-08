import com.android.build.api.dsl.Packaging
import java.text.SimpleDateFormat

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.cc.mvi"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.cc.mvi"
    minSdk = 24
    targetSdk = 34
    versionCode = 100
    versionName = "1.0.0"
    resValue("string", "app_name", "@string/APP名称")
    resValue("string", "buildTime", SimpleDateFormat("yyyyMMdd_HHmm").format(System.currentTimeMillis()))
    resourceConfigurations.addAll(listOf("zh", "zh-rCN")) //可能三方会使用zh-rCN，所以需要保留(如果随便写一种没有的语言，则只会打默认的文字资源到APK)
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  //https://github.com/owntracks/android/blob/43db0ad8428fa30e3edb1e27c9c08143e3e81693/project/app/build.gradle.kts
  signingConfigs {
    register("release") {
      storeFile = File("${rootDir}/mvi.jks")
      storePassword = "mvi123456"
      keyAlias = "mvi"
      keyPassword = "mvi123456"
      enableV1Signing = true
      enableV2Signing = true
      enableV3Signing = true
      enableV4Signing = true
    }
  }

  buildTypes {
    debug {
      signingConfig = signingConfigs.findByName("release")
      isShrinkResources = false //是否移除无用资源
      isMinifyEnabled = false //是否开启混淆
      applicationIdSuffix = ".debug"
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
    release {
      signingConfig = signingConfigs.findByName("release")
      isShrinkResources = true //是否移除无用资源
      isMinifyEnabled = true //是否开启混淆
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "../proguard/my-proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    buildConfig = true
    viewBinding = true
    dataBinding = false
  }

  //https://github.com/whitechi73/OpenShamrock/blob/4adbc12a0bfa2220f230fcc0a7f92b7309d409eb/app/build.gradle.kts#L113
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
      excludes += "/META-INF/INDEX.LIST"
      excludes += "/META-INF/versions/9/OSGI-INF/MANIFEST.MF"
    }
  }

  sourceSets {
    getByName("main") {
      java {
        res.srcDir("/src/main/res/drawables/splash")

        res.srcDir("/src/main/res/layouts/base")
        res.srcDir("/src/main/res/layouts/comm")
        res.srcDir("/src/main/res/layouts/status")
        res.srcDir("/src/main/res/layouts/items")
        res.srcDir("/src/main/res/")
      }
    }
  }
}

dependencies {
  implementation(libs.base.core)
  implementation(libs.base.appcompat)
  implementation(libs.base.runtime)
  implementation(libs.base.coroutines)
  implementation(libs.base.recyclerview)
  implementation(libs.base.constraintlayout)
  implementation(libs.base.material)
  implementation(libs.base.json)
  implementation(libs.base.protobuf)
  implementation(libs.base.auto.size)
  implementation(libs.base.vb.ktx)
  implementation(libs.base.vb.base)
  implementation(libs.base.mmkv)
  implementation(libs.base.utilcodex)
  implementation(libs.base.xxpermissions)
  implementation(libs.base.coil)
  implementation(libs.base.glide)
  implementation(libs.base.startup)
  implementation(libs.base.immersionbar)
  implementation(libs.base.immersionbar.ktx)
  implementation(libs.base.okhttp)
  implementation(libs.base.refresh)
  implementation(libs.base.refresh.header)
  implementation(libs.base.refresh.footer)
  implementation(libs.base.xlog)
  implementation(libs.base.xlog.libcat)

  implementation(libs.third.xui)
  //implementation(libs.third.androidveil)
  //implementation(libs.third.tabLayout)
  //implementation(libs.third.pictureselector)
  //implementation(libs.third.compress)
  //implementation(libs.third.dkplayer)
  //implementation(libs.third.dkplayer.ui)
  //implementation(libs.third.dkplayer.exo)
  //implementation(libs.third.lottie)
  //implementation(libs.third.svgaplayer)
  //implementation(libs.third.jsoup)
  //implementation(libs.third.libphonenumber)
  //implementation(libs.third.datepickerview)
  //implementation(libs.third.desugar)
  //implementation(libs.third.calendar)
  //implementation(libs.third.hutool)
  //implementation(libs.third.bouncycastle)
  //implementation(libs.third.protobuf.javalite)
  //implementation(libs.third.emoji)
  //implementation(libs.third.appupdate)
  //implementation(libs.third.banner)
  //implementation(libs.third.jsbridge)
  //implementation(libs.third.flexbox)
  //implementation(libs.third.file.core)
  //implementation(libs.third.file.selector)
  //implementation(libs.third.file.compressor)
  //implementation(libs.third.uploadservice)
  //implementation(libs.third.transformationlayout)
  //implementation(libs.third.sketch)
  //implementation(libs.third.fancy)
  //implementation(libs.third.spinner)
  //implementation(libs.third.xpopup)
  //implementation(libs.third.blurview)
  //implementation(libs.third.dialogx)
  //implementation(libs.third.rteditor)
  //implementation(libs.third.skin)
  //implementation(libs.third.skin.standard)
  //implementation(libs.third.skin.reflex)
  //implementation(libs.third.ktor.core)
  //implementation(libs.third.ktor.android)
  //implementation(libs.third.ktor.okhttp)
  //implementation(libs.third.ktor.negotiation)
  //implementation(libs.third.ktor.json)
  //implementation(libs.third.ktor.logging)
  //
  //
  //debugImplementation(libs.develop.vasdolly)
  //debugImplementation(libs.develop.chucker)
  //debugImplementation(libs.develop.flipper)
  //debugImplementation(libs.develop.soloader)
  //debugImplementation(libs.develop.konsist)
  //
  //releaseImplementation (libs.develop.flipper.noop)
}

//<editor-fold defaultstate="collapsed" desc="打包处理">
//打包处理 https://github.com/Xposed-Modules-Repo/mufanc.tools.applock/blob/5507e105cb4fc30667f5b9d78c0eecf5348fd732/app/build.gradle.kts#L79
android.applicationVariants.all { //这里会走"渠道数"x2(Debug+Release)的次数
  outputs.all {
    //正式版还是测试版
    val apkBuildType = buildType.name.replaceFirstChar { it.uppercase() }
    //打包完成后执行APK复制到指定位置
    assembleProvider.get().doLast {
      //使用ApkParser库解析APK文件的清单信息
      val apkParser = net.dongliu.apk.parser.ApkFile(outputFile)
      val apkName = apkParser.apkMeta.label
      val apkVersion = apkParser.apkMeta.versionName
      val buildEndTime = SimpleDateFormat("yyyyMMdd_HHmm").format(System.currentTimeMillis())
      val apkFileName = "${apkName}_${apkBuildType}_${apkVersion}_${buildEndTime}.apk"
      val destDir = if ("Debug" == apkBuildType) {
        File(rootDir, "APK/${apkBuildType}").also {
          if (!it.exists()) it.mkdirs()
          com.android.utils.FileUtils.deleteDirectoryContents(it)
        }
      } else {
        File(rootDir, "APK/${apkBuildType}").also { if (!it.exists()) it.mkdirs() }
      }
      outputFile.copyTo(File(destDir, apkFileName), true)
    }
  }
}
//</editor-fold>