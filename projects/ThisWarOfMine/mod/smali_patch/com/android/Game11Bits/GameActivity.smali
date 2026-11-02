.class public Lcom/android/Game11Bits/GameActivity;
.super Lcom/android/Game11Bits/PlatformSpecificBaseActivity;
.source "GameActivity.java"


# static fields
.field protected static mActivity:Lcom/android/Game11Bits/GameActivity; = null

.field public static mIsEast2WestBuild:Z = false

.field protected static mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;


# instance fields
.field public ic:Landroid/os/Bundle;

.field protected mHasFocus:Z

.field public mIsPureBuild:Z

.field public mIsTapTapBuild:Z

.field protected mView:Lcom/android/Game11Bits/GLMainView;

.field protected mWaitingToResume:Z


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 19
    invoke-direct {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;-><init>()V

    const/4 v0, 0x0

    .line 21
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mIsPureBuild:Z

    .line 23
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mIsTapTapBuild:Z

    const/4 v0, 0x0

    .line 27
    iput-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    return-void
.end method

.method public static ForceQuit()V
    .locals 1

    const/4 v0, 0x0

    .line 109
    invoke-static {v0}, Lcom/android/Game11Bits/BaseActivity;->OnChildActivityFinished(Z)V

    .line 110
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mActivity:Lcom/android/Game11Bits/GameActivity;

    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->finish()V

    return-void
.end method

.method public static Quit()V
    .locals 1

    .line 115
    sget-boolean v0, Lcom/android/Game11Bits/GameActivity;->mIsEast2WestBuild:Z

    if-eqz v0, :cond_0

    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 117
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->Quit()V

    .line 120
    :cond_0
    invoke-static {}, Lcom/android/Game11Bits/GameActivity;->ForceQuit()V

    return-void
.end method


# virtual methods
.method protected createPurchaseDelegate()Lcom/android/Game11Bits/PurchaseDelegate;
    .locals 2

    .line 138
    const-string v0, "GameActivity"

    const-string v1, "+ getPurchaseDelegate is GooglePlay"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 140
    new-instance v0, Lcom/android/Game11Bits/GooglePlayPurchaseDelegate;

    invoke-direct {v0, p0}, Lcom/android/Game11Bits/GooglePlayPurchaseDelegate;-><init>(Lcom/android/Game11Bits/GameActivity;)V

    return-object v0
.end method

.method public dispatchKeyEvent(Landroid/view/KeyEvent;)Z
    .locals 1

    .line 229
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0, p1}, Lcom/android/Game11Bits/GLMainView;->onKeyEvent(Landroid/view/KeyEvent;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x1

    return p1

    .line 232
    :cond_0
    invoke-super {p0, p1}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->dispatchKeyEvent(Landroid/view/KeyEvent;)Z

    move-result p1

    return p1
.end method

.method protected hideStatusBar()V
    .locals 2

    .line 145
    const-string v0, "GameActivity"

    const-string v1, "+ hideStatusBar"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 149
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    const/16 v1, 0x1707

    invoke-virtual {v0, v1}, Lcom/android/Game11Bits/GLMainView;->setSystemUiVisibility(I)V

    return-void
.end method

.method protected initInAppDelegate()V
    .locals 1

    .line 125
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mIsPureBuild:Z

    if-eqz v0, :cond_0

    goto :goto_0

    .line 128
    :cond_0
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-nez v0, :cond_1

    .line 130
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameActivity;->createPurchaseDelegate()Lcom/android/Game11Bits/PurchaseDelegate;

    move-result-object v0

    sput-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    .line 132
    invoke-static {v0}, Lcom/android/Game11Bits/GLHelper;->setInAppDelegate(Lcom/android/Game11Bits/PurchaseDelegate;)V

    :cond_1
    :goto_0
    return-void
.end method

.method public isFinalBuild()Z
    .locals 1

    .line 168
    invoke-static {}, Lcom/android/Game11Bits/GameLib;->isFinalBuild()Z

    move-result v0

    return v0
.end method

.method public isNetworkAvailable()Z
    .locals 2

    .line 282
    const-string v0, "connectivity"

    .line 283
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    .line 284
    invoke-virtual {v0}, Landroid/net/ConnectivityManager;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 286
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    if-nez v0, :cond_1

    .line 288
    new-instance v1, Lcom/android/Game11Bits/GameActivity$1;

    invoke-direct {v1, p0}, Lcom/android/Game11Bits/GameActivity$1;-><init>(Lcom/android/Game11Bits/GameActivity;)V

    invoke-virtual {p0, v1}, Lcom/android/Game11Bits/GameActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    :cond_1
    return v0
.end method

.method public isScoreCenterAvailable()Z
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public onActivityResult(IILandroid/content/Intent;)V
    .locals 1

    .line 309
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 311
    invoke-interface {v0, p1, p2, p3}, Lcom/android/Game11Bits/PurchaseDelegate;->handleActivityResult(IILandroid/content/Intent;)V

    .line 314
    :cond_0
    invoke-super {p0, p1, p2, p3}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onActivityResult(IILandroid/content/Intent;)V

    return-void
.end method

.method public onBackPressed()V
    .locals 2

    .line 237
    const-string v0, "GameActivity"

    const-string v1, "+ onBackPressed"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 238
    sget-boolean v0, Lcom/android/Game11Bits/GameActivity;->mIsEast2WestBuild:Z

    if-eqz v0, :cond_0

    .line 239
    invoke-static {}, Lcom/android/Game11Bits/GameActivity;->Quit()V

    return-void

    .line 240
    :cond_0
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0}, Lcom/android/Game11Bits/GLMainView;->onBackPressed()Z

    move-result v0

    if-nez v0, :cond_1

    const/4 v0, 0x1

    .line 242
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameActivity;->moveTaskToBack(Z)Z

    .line 243
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onBackPressed()V

    :cond_1
    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 17

    move-object/from16 v0, p0

    .line 34
    const-string v1, "+ onCreate"

    const-string v2, "GameActivity"

    invoke-static {v2, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 35
    invoke-super/range {p0 .. p1}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onCreate(Landroid/os/Bundle;)V

    move-object/from16 v1, p1

    .line 36
    iput-object v1, v0, Lcom/android/Game11Bits/GameActivity;->ic:Landroid/os/Bundle;

    .line 38
    const-string v1, "+ load AndroidGame"

    invoke-static {v2, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 39
    const-string v1, "AndroidGame"

    invoke-static {v1}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    .line 41
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v3, "+ "

    invoke-direct {v1, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object v4

    invoke-interface {v4}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v4

    invoke-virtual {v4}, Landroid/view/Display;->getPixelFormat()I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x1

    new-array v6, v5, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object v4, v6, v7

    const-string v4, "Pixel format: %d"

    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 42
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-static {v1}, Lcom/android/Game11Bits/GLHelper;->SetResources(Landroid/content/res/Resources;)V

    .line 46
    new-instance v1, Landroid/util/DisplayMetrics;

    invoke-direct {v1}, Landroid/util/DisplayMetrics;-><init>()V

    .line 47
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object v4

    invoke-interface {v4}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v4

    invoke-virtual {v4, v1}, Landroid/view/Display;->getRealMetrics(Landroid/util/DisplayMetrics;)V

    .line 48
    iget v4, v1, Landroid/util/DisplayMetrics;->widthPixels:I

    .line 49
    iget v6, v1, Landroid/util/DisplayMetrics;->heightPixels:I

    .line 50
    iget v8, v1, Landroid/util/DisplayMetrics;->densityDpi:I

    .line 53
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v9

    const-string v10, "org.chromium.arc.device_management"

    invoke-virtual {v9, v10}, Landroid/content/pm/PackageManager;->hasSystemFeature(Ljava/lang/String;)Z

    move-result v9

    if-nez v9, :cond_0

    if-le v6, v4, :cond_0

    move/from16 v16, v6

    move v6, v4

    move/from16 v4, v16

    :cond_0
    int-to-double v11, v4

    .line 60
    iget v9, v1, Landroid/util/DisplayMetrics;->xdpi:F

    float-to-double v13, v9

    div-double/2addr v11, v13

    int-to-double v13, v6

    .line 61
    iget v9, v1, Landroid/util/DisplayMetrics;->ydpi:F

    move v15, v8

    const/16 p1, 0x0

    float-to-double v7, v9

    div-double/2addr v13, v7

    const-wide/high16 v7, 0x4000000000000000L    # 2.0

    .line 62
    invoke-static {v11, v12, v7, v8}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v11

    .line 63
    invoke-static {v13, v14, v7, v8}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v7

    add-double/2addr v11, v7

    .line 64
    invoke-static {v11, v12}, Ljava/lang/Math;->sqrt(D)D

    move-result-wide v7

    double-to-float v7, v7

    const/high16 v8, 0x41200000    # 10.0f

    mul-float v7, v7, v8

    .line 65
    invoke-static {v7}, Ljava/lang/Math;->round(F)I

    move-result v7

    int-to-float v7, v7

    div-float/2addr v7, v8

    .line 67
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    iget v11, v1, Landroid/util/DisplayMetrics;->xdpi:F

    invoke-static {v11}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v11

    iget v1, v1, Landroid/util/DisplayMetrics;->ydpi:F

    invoke-static {v1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v1

    invoke-static {v15}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    const/4 v13, 0x5

    new-array v13, v13, [Ljava/lang/Object;

    aput-object v3, v13, p1

    aput-object v9, v13, v5

    const/4 v3, 0x2

    aput-object v11, v13, v3

    const/4 v3, 0x3

    aput-object v1, v13, v3

    const/4 v1, 0x4

    aput-object v12, v13, v1

    const-string v1, "DisplayMetrics: width %d height %d xdpi %.2f ydpi %.2f densityDpi %d"

    invoke-static {v1, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v8, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 68
    invoke-static {v7}, Lcom/android/Game11Bits/GameLib;->setDisplaySizeInInches(F)V

    int-to-float v1, v4

    int-to-float v2, v6

    div-float/2addr v1, v2

    .line 71
    invoke-static {v1}, Lcom/android/Game11Bits/GameLib;->setAspectRatio(F)V

    .line 75
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getIntent()Landroid/content/Intent;

    move-result-object v1

    const-string v2, "SourcePath"

    invoke-virtual {v1, v2}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v1

    invoke-virtual {v1, v2}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    goto :goto_0

    .line 76
    :cond_1
    const-string v1, ""

    .line 78
    :goto_0
    new-instance v2, Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getApplication()Landroid/app/Application;

    move-result-object v3

    invoke-direct {v2, v3, v1}, Lcom/android/Game11Bits/GLMainView;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    iput-object v2, v0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    .line 79
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->hideStatusBar()V

    .line 80
    iget-object v1, v0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0, v1}, Lcom/android/Game11Bits/GameActivity;->setContentView(Landroid/view/View;)V

    # twommod: gắn menu mod
    invoke-static/range {p0 .. p0}, Lcom/twommod/ModMenu;->attach(Landroid/app/Activity;)V

    .line 82
    iput-boolean v5, v0, Lcom/android/Game11Bits/GameActivity;->mHasFocus:Z

    const/4 v1, 0x0

    .line 83
    iput-boolean v1, v0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    const/4 v1, 0x6

    .line 86
    invoke-virtual {v0, v1}, Lcom/android/Game11Bits/GameActivity;->setRequestedOrientation(I)V

    .line 88
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    invoke-virtual {v1, v10}, Landroid/content/pm/PackageManager;->hasSystemFeature(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 90
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0xe3

    invoke-virtual {v1, v2}, Landroid/view/Window;->addFlags(I)V

    .line 91
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0x50

    invoke-virtual {v1, v2}, Landroid/view/Window;->setGravity(I)V

    goto :goto_1

    .line 94
    :cond_2
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0x80

    invoke-virtual {v1, v2}, Landroid/view/Window;->addFlags(I)V

    .line 96
    :goto_1
    invoke-static {v0}, Lcom/android/Game11Bits/GLHelper;->setGameActivity(Lcom/android/Game11Bits/GameActivity;)V

    .line 97
    iget-object v1, v0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-static {v1}, Lcom/android/Game11Bits/GLHelper;->setMainView(Lcom/android/Game11Bits/GLMainView;)V

    .line 100
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameActivity;->initInAppDelegate()V

    .line 102
    invoke-static {}, Lcom/android/Game11Bits/GameLib;->initNativeInterface()V

    .line 104
    sput-object v0, Lcom/android/Game11Bits/GameActivity;->mActivity:Lcom/android/Game11Bits/GameActivity;

    return-void
.end method

.method protected onDestroy()V
    .locals 2

    .line 265
    const-string v0, "GameActivity"

    const-string v1, "+ onDestroy"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 266
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onDestroy()V

    .line 267
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 268
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->onDestroy()V

    :cond_0
    return-void
.end method

.method public onExitGame()Z
    .locals 1

    .line 301
    invoke-static {}, Lcom/android/Game11Bits/GameActivity;->Quit()V

    const/4 v0, 0x0

    .line 302
    invoke-static {v0}, Ljava/lang/System;->exit(I)V

    const/4 v0, 0x1

    return v0
.end method

.method public onGenericMotionEvent(Landroid/view/MotionEvent;)Z
    .locals 2

    .line 216
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getSource()I

    move-result v0

    and-int/lit8 v0, v0, 0x10

    if-eqz v0, :cond_0

    .line 218
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    .line 220
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0, p1}, Lcom/android/Game11Bits/GLMainView;->onJoystickEvent(Landroid/view/MotionEvent;)V

    const/4 p1, 0x1

    return p1

    .line 224
    :cond_0
    invoke-super {p0, p1}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onGenericMotionEvent(Landroid/view/MotionEvent;)Z

    move-result p1

    return p1
.end method

.method protected onPause()V
    .locals 2

    .line 173
    const-string v0, "GameActivity"

    const-string v1, "+ onPause"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 174
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onPause()V

    .line 175
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0}, Lcom/android/Game11Bits/GLMainView;->onPause()V

    .line 176
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 177
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->onPause()V

    :cond_0
    return-void
.end method

.method protected onRestart()V
    .locals 2

    .line 273
    const-string v0, "GameActivity"

    const-string v1, "+ onRestart"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 274
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onRestart()V

    .line 275
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 276
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->onRestart()V

    :cond_0
    return-void
.end method

.method protected onResume()V
    .locals 2

    .line 182
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "+ onResume: mHasFocus="

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-boolean v1, p0, Lcom/android/Game11Bits/GameActivity;->mHasFocus:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", mWaitingToResume="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "GameActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 184
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onResume()V

    .line 186
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mHasFocus:Z

    if-eqz v0, :cond_1

    .line 188
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0}, Lcom/android/Game11Bits/GLMainView;->onResume()V

    .line 189
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameActivity;->hideStatusBar()V

    .line 190
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 191
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->onResume()V

    :cond_0
    const/4 v0, 0x0

    .line 192
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    return-void

    :cond_1
    const/4 v0, 0x1

    .line 196
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    return-void
.end method

.method protected onStart()V
    .locals 2

    .line 249
    const-string v0, "GameActivity"

    const-string v1, "+ onStart"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 250
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onStart()V

    .line 251
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 252
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->onStart()V

    :cond_0
    return-void
.end method

.method protected onStop()V
    .locals 2

    .line 257
    const-string v0, "GameActivity"

    const-string v1, "+ onStop"

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 258
    invoke-super {p0}, Lcom/android/Game11Bits/PlatformSpecificBaseActivity;->onStop()V

    .line 259
    sget-object v0, Lcom/android/Game11Bits/GameActivity;->mPurchaseDelegate:Lcom/android/Game11Bits/PurchaseDelegate;

    if-eqz v0, :cond_0

    .line 260
    invoke-interface {v0}, Lcom/android/Game11Bits/PurchaseDelegate;->onStop()V

    :cond_0
    return-void
.end method

.method public onWindowFocusChanged(Z)V
    .locals 2

    .line 202
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "+ onWindowFocusChanged: hasFocus="

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", mWaitingToResume="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "GameActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 203
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    if-eqz v0, :cond_0

    if-eqz p1, :cond_0

    .line 205
    iget-object v0, p0, Lcom/android/Game11Bits/GameActivity;->mView:Lcom/android/Game11Bits/GLMainView;

    invoke-virtual {v0}, Lcom/android/Game11Bits/GLMainView;->onResume()V

    const/4 v0, 0x0

    .line 206
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameActivity;->mWaitingToResume:Z

    .line 208
    :cond_0
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameActivity;->mHasFocus:Z

    if-eqz p1, :cond_1

    .line 211
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameActivity;->hideStatusBar()V

    :cond_1
    return-void
.end method

.method public reportScore(JLjava/lang/String;)V
    .locals 0

    return-void
.end method

.method public showAchievements()V
    .locals 0

    return-void
.end method

.method public showLeaderboards()V
    .locals 0

    return-void
.end method

.method public updateAchievement(Ljava/lang/String;F)V
    .locals 0

    return-void
.end method
