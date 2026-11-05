.class public Lco/kr/eamobile/hl5/runner/MainActivity;
.super Landroid/app/Activity;
.source "MainActivity.java"

# interfaces
.implements Landroid/opengl/GLSurfaceView$Renderer;


# static fields
.field private static final TAG:Ljava/lang/String; = "HL5_Runner"

.field private static nativeLibraryLoaded:Z


# instance fields
.field private activityResumed:Z

.field private bgmPlayer:Landroid/media/MediaPlayer;

.field private bgmWasPlayingOnPause:Z

.field private copyStatusBtn:Landroid/widget/TextView;

.field private debugEnabled:Z

.field private exitRequested:Z

.field private externalPath:Ljava/lang/String;

.field private filesPrepared:Z

.field private frameCount:I

.field private glSurfaceView:Landroid/opengl/GLSurfaceView;

.field private internalPath:Ljava/lang/String;

.field private volatile latestStatus:Ljava/lang/String;

.field private musicVolume:F

.field private overlayTextView:Landroid/widget/TextView;

.field private soundPool:Landroid/media/SoundPool;

.field private final soundPoolMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/Long;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private final soundStreamMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/Long;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private soundVolume:F

.field private surfaceNeedsInit:Z

.field private vfsAudioStream:Ljava/io/FileInputStream;

.field private vfsFile:Ljava/io/File;


# direct methods
.method public static synthetic $r8$lambda$S3qeTyhRSDViYji_7eKC70z4ngY(Lco/kr/eamobile/hl5/runner/MainActivity;)V
    .registers 1

    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->hideSystemUI()V

    return-void
.end method

.method static constructor <clinit>()V
    .registers 4

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_clinit()V

    .line 15
    const-string v0, "HL5_Runner"

    const/4 v1, 0x0

    sput-boolean v1, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeLibraryLoaded:Z

    .line 19
    :try_start_5
    const-string v1, "hl5_arm64_bridge"

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_loadLibBefore()V
    invoke-static {v1}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_loadLibAfter()V

    .line 20
    const/4 v1, 0x1

    sput-boolean v1, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeLibraryLoaded:Z

    .line 21
    const-string v1, "Successfully loaded native library libhl5_arm64_bridge.so"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_12
    .catch Ljava/lang/UnsatisfiedLinkError; {:try_start_5 .. :try_end_12} :catch_13

    .line 24
    goto :goto_2e

    .line 22
    :catch_13
    move-exception v1

    invoke-static {v1}, Lco/kr/eamobile/hl5/diag/Diag;->s_throwable(Ljava/lang/Throwable;)V

    .line 23
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Failed to load native library libhl5_arm64_bridge.so: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v1}, Ljava/lang/UnsatisfiedLinkError;->getMessage()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 25
    :goto_2e
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_clinitEnd()V
    return-void
.end method

.method public constructor <init>()V
    .registers 3

    .line 13
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 30
    const-string v0, "Preparing original target assets..."

    iput-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->latestStatus:Ljava/lang/String;

    .line 31
    const-string v0, ""

    iput-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->internalPath:Ljava/lang/String;

    .line 32
    iput-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->externalPath:Ljava/lang/String;

    .line 33
    const/4 v0, 0x0

    iput v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    .line 34
    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    .line 35
    const/4 v1, 0x1

    iput-boolean v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->surfaceNeedsInit:Z

    .line 36
    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->exitRequested:Z

    .line 37
    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->activityResumed:Z

    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->filesPrepared:Z

    .line 45
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPoolMap:Ljava/util/HashMap;

    .line 46
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundStreamMap:Ljava/util/HashMap;

    .line 47
    const v1, 0x3f4ccccd    # 0.8f

    iput v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    .line 48
    iput v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundVolume:F

    .line 49
    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmWasPlayingOnPause:Z

    return-void
.end method

.method static synthetic access$000(Lco/kr/eamobile/hl5/runner/MainActivity;IFFI)V
    .registers 5

    .line 13
    invoke-direct {p0, p1, p2, p3, p4}, Lco/kr/eamobile/hl5/runner/MainActivity;->dispatchTouch(IFFI)V

    return-void
.end method

.method private applyFullscreenInsets(Landroid/view/View;)V
    .registers 3

    .line 393
    const/4 v0, 0x0

    invoke-virtual {p1, v0, v0, v0, v0}, Landroid/view/View;->setPadding(IIII)V

    .line 394
    return-void
.end method

.method private configureFullscreenWindow()V
    .registers 6

    .line 376
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->getWindow()Landroid/view/Window;

    move-result-object v0

    .line 377
    if-nez v0, :cond_7

    return-void

    .line 378
    :cond_7
    const/16 v1, 0x400

    invoke-virtual {v0, v1}, Landroid/view/Window;->addFlags(I)V

    .line 379
    const/high16 v1, -0x1000000

    invoke-virtual {v0, v1}, Landroid/view/Window;->setStatusBarColor(I)V

    .line 380
    invoke-virtual {v0, v1}, Landroid/view/Window;->setNavigationBarColor(I)V

    .line 381
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1c

    const/16 v3, 0x1e

    if-lt v1, v2, :cond_2c

    .line 382
    invoke-virtual {v0}, Landroid/view/Window;->getAttributes()Landroid/view/WindowManager$LayoutParams;

    move-result-object v1

    .line 383
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    if-lt v2, v3, :cond_26

    .line 384
    const/4 v2, 0x3

    goto :goto_27

    .line 385
    :cond_26
    const/4 v2, 0x1

    :goto_27
    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->layoutInDisplayCutoutMode:I

    .line 386
    invoke-virtual {v0, v1}, Landroid/view/Window;->setAttributes(Landroid/view/WindowManager$LayoutParams;)V

    .line 388
    :cond_2c
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1d

    const/4 v4, 0x0

    if-lt v1, v2, :cond_36

    invoke-virtual {v0, v4}, Landroid/view/Window;->setNavigationBarContrastEnforced(Z)V

    .line 389
    :cond_36
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    if-lt v1, v3, :cond_3d

    invoke-virtual {v0, v4}, Landroid/view/Window;->setDecorFitsSystemWindows(Z)V

    .line 390
    :cond_3d
    return-void
.end method

.method private dispatchTouch(IFFI)V
    .registers 12

    .line 202
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_13

    .line 203
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda1;

    move-object v2, p0

    move v3, p1

    move v4, p2

    move v5, p3

    move v6, p4

    invoke-direct/range {v1 .. v6}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda1;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;IFFI)V

    invoke-virtual {v0, v1}, Landroid/opengl/GLSurfaceView;->queueEvent(Ljava/lang/Runnable;)V

    .line 205
    :cond_13
    return-void
.end method

.method private hideSystemUI()V
    .registers 4

    .line 397
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->getWindow()Landroid/view/Window;

    move-result-object v0

    .line 398
    if-nez v0, :cond_7

    return-void

    .line 399
    :cond_7
    invoke-virtual {v0}, Landroid/view/Window;->peekDecorView()Landroid/view/View;

    move-result-object v0

    .line 400
    if-eqz v0, :cond_33

    invoke-virtual {v0}, Landroid/view/View;->isAttachedToWindow()Z

    move-result v1

    if-nez v1, :cond_14

    goto :goto_33

    .line 402
    :cond_14
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1e

    if-lt v1, v2, :cond_2d

    .line 403
    invoke-virtual {v0}, Landroid/view/View;->getWindowInsetsController()Landroid/view/WindowInsetsController;

    move-result-object v0

    .line 404
    if-nez v0, :cond_21

    return-void

    .line 405
    :cond_21
    const/4 v1, 0x2

    invoke-interface {v0, v1}, Landroid/view/WindowInsetsController;->setSystemBarsBehavior(I)V

    .line 406
    invoke-static {}, Landroid/view/WindowInsets$Type;->systemBars()I

    move-result v1

    invoke-interface {v0, v1}, Landroid/view/WindowInsetsController;->hide(I)V

    .line 407
    goto :goto_32

    .line 408
    :cond_2d
    const/16 v1, 0x1706

    invoke-virtual {v0, v1}, Landroid/view/View;->setSystemUiVisibility(I)V

    .line 416
    :goto_32
    return-void

    .line 400
    :cond_33
    :goto_33
    return-void
.end method

.method private native nativeOnDestroy()V
.end method

.method private native nativeOnDrawFrame(Z)Ljava/lang/String;
.end method

.method private native nativeOnPause()V
.end method

.method private native nativeOnResume()V
.end method

.method private native nativeOnSurfaceChanged(II)V
.end method

.method private native nativeOnSurfaceCreated(Ljava/lang/String;Ljava/lang/String;IIZ)V
.end method

.method private native nativeOnTouchEvent(IFFI)V
.end method

.method private native nativeRunDiagnosticTests(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
.end method

.method private startGameView()V
    .registers 12

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_startGameView()V

    .line 101
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-nez v0, :cond_14e

    iget-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->filesPrepared:Z

    if-eqz v0, :cond_14e

    iget-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->activityResumed:Z

    if-eqz v0, :cond_14e

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isFinishing()Z

    move-result v0

    if-nez v0, :cond_14e

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isDestroyed()Z

    move-result v0

    if-eqz v0, :cond_1a

    goto/16 :goto_14e

    .line 102
    :cond_1a
    new-instance v0, Landroid/media/AudioAttributes$Builder;

    invoke-direct {v0}, Landroid/media/AudioAttributes$Builder;-><init>()V

    .line 103
    const/16 v1, 0xe

    invoke-virtual {v0, v1}, Landroid/media/AudioAttributes$Builder;->setUsage(I)Landroid/media/AudioAttributes$Builder;

    move-result-object v0

    .line 104
    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/media/AudioAttributes$Builder;->setContentType(I)Landroid/media/AudioAttributes$Builder;

    move-result-object v0

    .line 105
    invoke-virtual {v0}, Landroid/media/AudioAttributes$Builder;->build()Landroid/media/AudioAttributes;

    move-result-object v0

    .line 106
    new-instance v1, Landroid/media/SoundPool$Builder;

    invoke-direct {v1}, Landroid/media/SoundPool$Builder;-><init>()V

    .line 107
    const/16 v2, 0x8

    invoke-virtual {v1, v2}, Landroid/media/SoundPool$Builder;->setMaxStreams(I)Landroid/media/SoundPool$Builder;

    move-result-object v1

    .line 108
    invoke-virtual {v1, v0}, Landroid/media/SoundPool$Builder;->setAudioAttributes(Landroid/media/AudioAttributes;)Landroid/media/SoundPool$Builder;

    move-result-object v0

    .line 109
    invoke-virtual {v0}, Landroid/media/SoundPool$Builder;->build()Landroid/media/SoundPool;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    .line 110
    new-instance v0, Landroid/media/MediaPlayer;

    invoke-direct {v0}, Landroid/media/MediaPlayer;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    .line 111
    new-instance v0, Landroid/widget/FrameLayout;

    invoke-direct {v0, p0}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;)V

    .line 112
    const/high16 v1, -0x1000000

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->setBackgroundColor(I)V

    .line 113
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->setFitsSystemWindows(Z)V

    .line 114
    new-instance v3, Landroid/opengl/GLSurfaceView;

    invoke-direct {v3, p0}, Landroid/opengl/GLSurfaceView;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    .line 115
    iget-object v4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    const/16 v9, 0x10

    const/4 v10, 0x0

    const/16 v5, 0x8

    const/16 v6, 0x8

    const/16 v7, 0x8

    const/16 v8, 0x8

    invoke-virtual/range {v4 .. v10}, Landroid/opengl/GLSurfaceView;->setEGLConfigChooser(IIIIII)V

    .line 116
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    const/4 v4, 0x1

    invoke-virtual {v3, v4}, Landroid/opengl/GLSurfaceView;->setPreserveEGLContextOnPause(Z)V

    .line 117
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v3, p0}, Landroid/opengl/GLSurfaceView;->setRenderer(Landroid/opengl/GLSurfaceView$Renderer;)V

    .line 118
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    new-instance v5, Lco/kr/eamobile/hl5/runner/MainActivity$1;

    invoke-direct {v5, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$1;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {v3, v5}, Landroid/opengl/GLSurfaceView;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 156
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, p0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    .line 157
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    const v5, -0xff0100

    invoke-virtual {v3, v5}, Landroid/widget/TextView;->setTextColor(I)V

    .line 158
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    const/16 v5, 0xb4

    invoke-static {v5, v1, v1, v1}, Landroid/graphics/Color;->argb(IIII)I

    move-result v5

    invoke-virtual {v3, v5}, Landroid/widget/TextView;->setBackgroundColor(I)V

    .line 159
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    const/high16 v5, 0x41300000    # 11.0f

    invoke-virtual {v3, v5}, Landroid/widget/TextView;->setTextSize(F)V

    .line 160
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    const/16 v5, 0x10

    const/16 v6, 0xc

    invoke-virtual {v3, v5, v6, v5, v6}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 161
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    iget-object v6, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->latestStatus:Ljava/lang/String;

    invoke-virtual {v3, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 162
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setTextIsSelectable(Z)V

    .line 163
    new-instance v3, Landroid/widget/FrameLayout$LayoutParams;

    const/16 v4, 0x53

    const/4 v6, -0x2

    invoke-direct {v3, v6, v6, v4}, Landroid/widget/FrameLayout$LayoutParams;-><init>(III)V

    .line 167
    const/16 v4, 0x14

    invoke-virtual {v3, v4, v4, v4, v4}, Landroid/widget/FrameLayout$LayoutParams;->setMargins(IIII)V

    .line 168
    iget-object v7, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    invoke-virtual {v7, v3}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 169
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, p0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    .line 170
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    const/4 v7, -0x1

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setTextColor(I)V

    .line 171
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    const/16 v7, 0xdc

    invoke-static {v7, v4, v4, v4}, Landroid/graphics/Color;->argb(IIII)I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setBackgroundColor(I)V

    .line 172
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    const/high16 v4, 0x41600000    # 14.0f

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setTextSize(F)V

    .line 173
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    const/16 v4, 0x1c

    invoke-virtual {v3, v4, v5, v4, v5}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 174
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    const-string v4, "Salin status"

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 175
    new-instance v3, Landroid/widget/FrameLayout$LayoutParams;

    const/16 v4, 0x33

    invoke-direct {v3, v6, v6, v4}, Landroid/widget/FrameLayout$LayoutParams;-><init>(III)V

    .line 178
    const/16 v4, 0x18

    invoke-virtual {v3, v4, v4, v4, v4}, Landroid/widget/FrameLayout$LayoutParams;->setMargins(IIII)V

    .line 179
    iget-object v4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    invoke-virtual {v4, v3}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 180
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    new-instance v4, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda5;

    invoke-direct {v4, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda5;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 187
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v0, v3}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;)V

    .line 188
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    iget-boolean v4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    if-eqz v4, :cond_121

    const/4 v4, 0x0

    goto :goto_123

    :cond_121
    const/16 v4, 0x8

    :goto_123
    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setVisibility(I)V

    .line 189
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    iget-boolean v4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    if-eqz v4, :cond_12d

    const/4 v2, 0x0

    :cond_12d
    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setVisibility(I)V

    .line 190
    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;)V

    .line 191
    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->copyStatusBtn:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;)V

    .line 192
    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda6;

    invoke-direct {v1, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda6;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->setOnApplyWindowInsetsListener(Landroid/view/View$OnApplyWindowInsetsListener;)V

    .line 197
    invoke-virtual {p0, v0}, Lco/kr/eamobile/hl5/runner/MainActivity;->setContentView(Landroid/view/View;)V

    .line 198
    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda2;

    invoke-direct {v1, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda2;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->post(Ljava/lang/Runnable;)Z

    .line 199
    return-void

    .line 101
    :cond_14e
    :goto_14e
    return-void
.end method


# virtual methods
.method synthetic lambda$dispatchTouch$5$co-kr-eamobile-hl5-runner-MainActivity(IFFI)V
    .registers 5

    .line 203
    invoke-direct {p0, p1, p2, p3, p4}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnTouchEvent(IFFI)V

    return-void
.end method

.method synthetic lambda$onCreate$0$co-kr-eamobile-hl5-runner-MainActivity()V
    .registers 2

    .line 89
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isFinishing()Z

    move-result v0

    if-nez v0, :cond_18

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isDestroyed()Z

    move-result v0

    if-eqz v0, :cond_d

    goto :goto_18

    .line 90
    :cond_d
    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->filesPrepared:Z

    .line 91
    iget-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->activityResumed:Z

    if-eqz v0, :cond_17

    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->startGameView()V

    .line 92
    :cond_17
    return-void

    .line 89
    :cond_18
    :goto_18
    return-void
.end method

.method synthetic lambda$onCreate$1$co-kr-eamobile-hl5-runner-MainActivity(Landroid/widget/TextView;Ljava/lang/Exception;)V
    .registers 5

    .line 95
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isFinishing()Z

    move-result v0

    if-nez v0, :cond_26

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isDestroyed()Z

    move-result v0

    if-nez v0, :cond_26

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Unable to prepare game files. "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p2}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_26
    return-void
.end method

.method synthetic lambda$onCreate$2$co-kr-eamobile-hl5-runner-MainActivity(Landroid/widget/TextView;)V
    .registers 5

    .line 81
    :try_start_0
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->getFilesDir()Ljava/io/File;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Lco/kr/eamobile/hl5/runner/MainActivity;->getExternalFilesDir(Ljava/lang/String;)Ljava/io/File;

    move-result-object v1

    .line 82
    if-eqz v0, :cond_39

    .line 83
    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    if-eqz v1, :cond_13

    invoke-virtual {v1}, Ljava/io/File;->mkdirs()Z

    .line 84
    :cond_13
    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    iput-object v2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->internalPath:Ljava/lang/String;

    .line 85
    if-eqz v1, :cond_20

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v1

    goto :goto_22

    :cond_20
    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->internalPath:Ljava/lang/String;

    :goto_22
    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->externalPath:Ljava/lang/String;

    .line 86
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_instBefore()V
    invoke-static {p0, v0}, Lco/kr/eamobile/hl5/runner/TargetAssets;->install(Landroid/content/Context;Ljava/io/File;)V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_instAfter()V

    .line 87
    new-instance v1, Ljava/io/File;

    const-string v2, "data.vfs.mp3"

    invoke-direct {v1, v0, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    .line 88
    new-instance v0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda9;

    invoke-direct {v0, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda9;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {p0, v0}, Lco/kr/eamobile/hl5/runner/MainActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 96
    goto :goto_51

    .line 82
    :cond_39
    new-instance v0, Ljava/io/IOException;

    const-string v1, "Internal storage unavailable"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_41
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_41} :catch_41

    .line 93
    :catch_41
    move-exception v0

    invoke-static {v0}, Lco/kr/eamobile/hl5/diag/Diag;->s_throwable(Ljava/lang/Throwable;)V

    .line 94
    const-string v1, "HL5_Runner"

    const-string v2, "Game preparation failed"

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 95
    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda10;

    invoke-direct {v1, p0, p1, v0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda10;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;Landroid/widget/TextView;Ljava/lang/Exception;)V

    invoke-virtual {p0, v1}, Lco/kr/eamobile/hl5/runner/MainActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 97
    :goto_51
    return-void
.end method

.method synthetic lambda$onGameExitRequested$7$co-kr-eamobile-hl5-runner-MainActivity()V
    .registers 2

    .line 457
    iget-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->exitRequested:Z

    if-eqz v0, :cond_5

    return-void

    .line 458
    :cond_5
    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->exitRequested:Z

    .line 459
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->finishAndRemoveTask()V

    .line 460
    return-void
.end method

.method synthetic lambda$onNativeStatus$6$co-kr-eamobile-hl5-runner-MainActivity(Ljava/lang/String;)V
    .registers 4

    .line 448
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    if-eqz v0, :cond_1f

    .line 449
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    iget-boolean v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    if-nez v1, :cond_16

    const-string v1, "FAIL "

    invoke-virtual {p1, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_13

    goto :goto_16

    .line 450
    :cond_13
    const/16 v1, 0x8

    goto :goto_17

    :cond_16
    :goto_16
    const/4 v1, 0x0

    .line 449
    :goto_17
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    .line 451
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->overlayTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 453
    :cond_1f
    return-void
.end method

.method synthetic lambda$onPause$8$co-kr-eamobile-hl5-runner-MainActivity()V
    .registers 1

    .line 479
    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnPause()V

    return-void
.end method

.method synthetic lambda$onResume$9$co-kr-eamobile-hl5-runner-MainActivity()V
    .registers 1

    .line 491
    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnResume()V

    return-void
.end method

.method synthetic lambda$startGameView$3$co-kr-eamobile-hl5-runner-MainActivity(Landroid/view/View;)V
    .registers 4

    .line 181
    nop

    .line 182
    const-string p1, "clipboard"

    invoke-virtual {p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/content/ClipboardManager;

    .line 183
    if-eqz p1, :cond_16

    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->latestStatus:Ljava/lang/String;

    .line 184
    const-string v1, "HL5 startup status"

    invoke-static {v1, v0}, Landroid/content/ClipData;->newPlainText(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Landroid/content/ClipData;

    move-result-object v0

    .line 183
    invoke-virtual {p1, v0}, Landroid/content/ClipboardManager;->setPrimaryClip(Landroid/content/ClipData;)V

    .line 185
    :cond_16
    const-string p1, "Status disalin"

    const/4 v0, 0x0

    invoke-static {p0, p1, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    .line 186
    return-void
.end method

.method synthetic lambda$startGameView$4$co-kr-eamobile-hl5-runner-MainActivity(Landroid/view/View;Landroid/view/WindowInsets;)Landroid/view/WindowInsets;
    .registers 3

    .line 194
    invoke-direct {p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->applyFullscreenInsets(Landroid/view/View;)V

    .line 195
    return-object p2
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .registers 5

    invoke-static {p0}, Lco/kr/eamobile/hl5/diag/Diag;->s_mainCreate(Landroid/content/Context;)V

    .line 61
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 62
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->getIntent()Landroid/content/Intent;

    move-result-object p1

    const/4 v0, 0x0

    if-eqz p1, :cond_17

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->getIntent()Landroid/content/Intent;

    move-result-object p1

    const-string v1, "debug"

    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->getBooleanExtra(Ljava/lang/String;Z)Z

    move-result p1

    if-eqz p1, :cond_17

    const/4 v0, 0x1

    :cond_17
    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    .line 63
    sget-boolean p1, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeLibraryLoaded:Z

    if-nez p1, :cond_2b

    .line 64
    new-instance p1, Landroid/widget/TextView;

    invoke-direct {p1, p0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 65
    const-string v0, "Unable to load the game runtime on this device."

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 66
    invoke-virtual {p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->setContentView(Landroid/view/View;)V

    .line 67
    return-void

    .line 69
    :cond_2b
    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->configureFullscreenWindow()V

    .line 70
    const-string p1, "HL5_Runner"

    const-string v0, "MainActivity.onCreate() started"

    invoke-static {p1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 72
    new-instance p1, Landroid/widget/FrameLayout;

    invoke-direct {p1, p0}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;)V

    .line 73
    const/high16 v0, -0x1000000

    invoke-virtual {p1, v0}, Landroid/widget/FrameLayout;->setBackgroundColor(I)V

    .line 74
    new-instance v0, Landroid/widget/TextView;

    invoke-direct {v0, p0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 75
    const-string v1, "Memuat game\u2026"

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const/4 v1, -0x1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 76
    const/16 v2, 0x11

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setGravity(I)V

    .line 77
    new-instance v2, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v2, v1, v1}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    invoke-virtual {p1, v0, v2}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 78
    invoke-virtual {p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->setContentView(Landroid/view/View;)V

    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda2;

    invoke-direct {v1, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda2;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {p1, v1}, Landroid/widget/FrameLayout;->post(Ljava/lang/Runnable;)Z

    .line 79
    new-instance p1, Ljava/lang/Thread;

    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;

    invoke-direct {v1, p0, v0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;Landroid/widget/TextView;)V

    const-string v0, "HL5-assets"

    invoke-direct {p1, v1, v0}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    .line 97
    invoke-virtual {p1}, Ljava/lang/Thread;->start()V

    .line 98
    return-void
.end method

.method protected onDestroy()V
    .registers 3

    .line 504
    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_destroy()V

    .line 506
    :try_start_3
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    const/4 v1, 0x0

    if-eqz v0, :cond_f

    .line 507
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->release()V

    .line 508
    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    .line 510
    :cond_f
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz v0, :cond_1a

    .line 511
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {v0}, Landroid/media/SoundPool;->release()V

    .line 512
    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    .line 514
    :cond_1a
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsAudioStream:Ljava/io/FileInputStream;

    if-eqz v0, :cond_27

    .line 515
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsAudioStream:Ljava/io/FileInputStream;

    invoke-virtual {v0}, Ljava/io/FileInputStream;->close()V

    .line 516
    iput-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsAudioStream:Ljava/io/FileInputStream;
    :try_end_25
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_25} :catch_26

    goto :goto_27

    .line 518
    :catch_26
    move-exception v0

    :cond_27
    :goto_27
    nop

    .line 519
    sget-boolean v0, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeLibraryLoaded:Z

    if-eqz v0, :cond_39

    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_39

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_39

    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnDestroy()V

    .line 520
    :cond_39
    return-void
.end method

.method public onDrawFrame(Ljavax/microedition/khronos/opengles/GL10;)V
    .registers 4

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_frame()V

    .line 438
    iget p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    const/4 v0, 0x1

    add-int/2addr p1, v0

    iput p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    .line 439
    iget-boolean p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    const/4 v1, 0x5

    if-eqz p1, :cond_16

    iget p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    if-le p1, v1, :cond_15

    iget p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    rem-int/lit8 p1, p1, 0x3c

    if-nez p1, :cond_16

    :cond_15
    goto :goto_17

    :cond_16
    const/4 v0, 0x0

    :goto_17
    invoke-direct {p0, v0}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnDrawFrame(Z)Ljava/lang/String;

    move-result-object p1

    .line 440
    if-eqz p1, :cond_2a

    iget v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    if-le v0, v1, :cond_27

    iget v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->frameCount:I

    rem-int/lit8 v0, v0, 0x3c

    if-nez v0, :cond_2a

    :cond_27
    invoke-virtual {p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->onNativeStatus(Ljava/lang/String;)V

    .line 441
    :cond_2a
    return-void
.end method

.method public declared-synchronized onFreeMusicClip(J)I
    .registers 5

    monitor-enter p0

    .line 276
    :try_start_1
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_a

    .line 277
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->reset()V
    :try_end_a
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_a} :catch_d
    .catchall {:try_start_1 .. :try_end_a} :catchall_b

    .line 281
    :cond_a
    goto :goto_2a

    .line 275
    :catchall_b
    move-exception p1

    goto :goto_2d

    .line 279
    :catch_d
    move-exception p1

    .line 280
    :try_start_e
    const-string p2, "HL5_Runner"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onFreeMusicClip exception: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2a
    .catchall {:try_start_e .. :try_end_2a} :catchall_b

    .line 282
    :goto_2a
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 275
    :goto_2d
    :try_start_2d
    monitor-exit p0
    :try_end_2e
    .catchall {:try_start_2d .. :try_end_2e} :catchall_b

    throw p1
.end method

.method public declared-synchronized onFreeSoundClip(J)I
    .registers 5

    monitor-enter p0

    .line 348
    :try_start_1
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz v0, :cond_1c

    .line 349
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Integer;

    .line 350
    if-eqz p1, :cond_1c

    .line 351
    iget-object p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    invoke-virtual {p2, p1}, Landroid/media/SoundPool;->unload(I)Z
    :try_end_1c
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1c} :catch_1f
    .catchall {:try_start_1 .. :try_end_1c} :catchall_1d

    .line 356
    :cond_1c
    goto :goto_3c

    .line 347
    :catchall_1d
    move-exception p1

    goto :goto_3f

    .line 354
    :catch_1f
    move-exception p1

    .line 355
    :try_start_20
    const-string p2, "HL5_Runner"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onFreeSoundClip exception: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_3c
    .catchall {:try_start_20 .. :try_end_3c} :catchall_1d

    .line 357
    :goto_3c
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 347
    :goto_3f
    :try_start_3f
    monitor-exit p0
    :try_end_40
    .catchall {:try_start_3f .. :try_end_40} :catchall_1d

    throw p1
.end method

.method public onGameExitRequested()V
    .registers 2

    .line 456
    new-instance v0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda0;

    invoke-direct {v0, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda0;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {p0, v0}, Lco/kr/eamobile/hl5/runner/MainActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 461
    return-void
.end method

.method public onNativeStatus(Ljava/lang/String;)V
    .registers 3

    .line 445
    iput-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->latestStatus:Ljava/lang/String;

    .line 446
    const-string v0, "HL5_Runner"

    invoke-static {v0, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 447
    new-instance v0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda7;

    invoke-direct {v0, p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda7;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;Ljava/lang/String;)V

    invoke-virtual {p0, v0}, Lco/kr/eamobile/hl5/runner/MainActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 454
    return-void
.end method

.method protected onPause()V
    .registers 3

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_pause()V

    .line 465
    const/4 v0, 0x0

    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->activityResumed:Z

    .line 466
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 468
    :try_start_6
    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz v1, :cond_1b

    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v1

    if-eqz v1, :cond_1b

    .line 469
    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmWasPlayingOnPause:Z

    .line 470
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->pause()V

    goto :goto_1d

    .line 472
    :cond_1b
    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmWasPlayingOnPause:Z

    .line 474
    :goto_1d
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz v0, :cond_28

    .line 475
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {v0}, Landroid/media/SoundPool;->autoPause()V
    :try_end_26
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_26} :catch_27

    goto :goto_28

    .line 477
    :catch_27
    move-exception v0

    :cond_28
    :goto_28
    nop

    .line 478
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_3c

    .line 479
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda8;

    invoke-direct {v1, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda8;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/opengl/GLSurfaceView;->queueEvent(Ljava/lang/Runnable;)V

    .line 480
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v0}, Landroid/opengl/GLSurfaceView;->onPause()V

    .line 482
    :cond_3c
    return-void
.end method

.method public declared-synchronized onPauseMusicClip(J)I
    .registers 5

    monitor-enter p0

    .line 254
    :try_start_1
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_12

    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-eqz p1, :cond_12

    .line 255
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->pause()V
    :try_end_12
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_12} :catch_15
    .catchall {:try_start_1 .. :try_end_12} :catchall_13

    .line 259
    :cond_12
    goto :goto_32

    .line 253
    :catchall_13
    move-exception p1

    goto :goto_35

    .line 257
    :catch_15
    move-exception p1

    .line 258
    :try_start_16
    const-string p2, "HL5_Runner"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onPauseMusicClip exception: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_32
    .catchall {:try_start_16 .. :try_end_32} :catchall_13

    .line 260
    :goto_32
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 253
    :goto_35
    :try_start_35
    monitor-exit p0
    :try_end_36
    .catchall {:try_start_35 .. :try_end_36} :catchall_13

    throw p1
.end method

.method public declared-synchronized onPlayMusicClip(JZ)I
    .registers 5

    monitor-enter p0

    .line 230
    :try_start_1
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_18

    .line 231
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1, p3}, Landroid/media/MediaPlayer;->setLooping(Z)V

    .line 232
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    iget p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    iget p3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    invoke-virtual {p1, p2, p3}, Landroid/media/MediaPlayer;->setVolume(FF)V

    .line 233
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->start()V
    :try_end_18
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_18} :catch_1b
    .catchall {:try_start_1 .. :try_end_18} :catchall_19

    .line 237
    :cond_18
    goto :goto_38

    .line 229
    :catchall_19
    move-exception p1

    goto :goto_3b

    .line 235
    :catch_1b
    move-exception p1

    .line 236
    :try_start_1c
    const-string p2, "HL5_Runner"

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onPlayMusicClip exception: "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_38
    .catchall {:try_start_1c .. :try_end_38} :catchall_19

    .line 238
    :goto_38
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 229
    :goto_3b
    :try_start_3b
    monitor-exit p0
    :try_end_3c
    .catchall {:try_start_3b .. :try_end_3c} :catchall_19

    throw p1
.end method

.method public declared-synchronized onPlaySoundClip(JZ)I
    .registers 13

    monitor-enter p0

    .line 319
    const/4 v1, 0x0

    :try_start_2
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz v0, :cond_38

    .line 320
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    .line 321
    if-eqz v0, :cond_38

    .line 322
    iget-object v2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v3

    iget v4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundVolume:F

    iget v5, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundVolume:F

    if-eqz p3, :cond_23

    const/4 p3, -0x1

    const/4 v7, -0x1

    goto :goto_24

    :cond_23
    const/4 v7, 0x0

    :goto_24
    const/high16 v8, 0x3f800000    # 1.0f

    const/4 v6, 0x1

    invoke-virtual/range {v2 .. v8}, Landroid/media/SoundPool;->play(IFFIIF)I

    move-result p3

    .line 323
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p1

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    invoke-virtual {v0, p1, p2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_38
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_38} :catch_3c
    .catchall {:try_start_2 .. :try_end_38} :catchall_39

    .line 328
    :cond_38
    goto :goto_5a

    .line 318
    :catchall_39
    move-exception v0

    move-object p1, v0

    goto :goto_5c

    .line 326
    :catch_3c
    move-exception v0

    move-object p1, v0

    .line 327
    :try_start_3e
    const-string p2, "HL5_Runner"

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onPlaySoundClip exception: "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_5a
    .catchall {:try_start_3e .. :try_end_5a} :catchall_39

    .line 329
    :goto_5a
    monitor-exit p0

    return v1

    .line 318
    :goto_5c
    :try_start_5c
    monitor-exit p0
    :try_end_5d
    .catchall {:try_start_5c .. :try_end_5d} :catchall_39

    throw p1
.end method

.method public declared-synchronized onPutMusicClip(JJJ)I
    .registers 13

    monitor-enter p0

    .line 210
    :try_start_1
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-nez p1, :cond_d

    .line 211
    new-instance p1, Landroid/media/MediaPlayer;

    invoke-direct {p1}, Landroid/media/MediaPlayer;-><init>()V

    iput-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    goto :goto_12

    .line 213
    :cond_d
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->reset()V

    .line 215
    :goto_12
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    if-eqz p1, :cond_4e

    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    invoke-virtual {p1}, Ljava/io/File;->exists()Z

    move-result p1

    if-eqz p1, :cond_4e

    .line 216
    new-instance p1, Ljava/io/FileInputStream;

    iget-object p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    invoke-direct {p1, p2}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_25
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_25} :catch_52
    .catchall {:try_start_1 .. :try_end_25} :catchall_4f

    .line 217
    :try_start_25
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Ljava/io/FileInputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object v1

    move-wide v2, p3

    move-wide v4, p5

    invoke-virtual/range {v0 .. v5}, Landroid/media/MediaPlayer;->setDataSource(Ljava/io/FileDescriptor;JJ)V

    .line 218
    iget-object p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    iget p3, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    iget p4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    invoke-virtual {p2, p3, p4}, Landroid/media/MediaPlayer;->setVolume(FF)V

    .line 219
    iget-object p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p2}, Landroid/media/MediaPlayer;->prepare()V
    :try_end_3e
    .catchall {:try_start_25 .. :try_end_3e} :catchall_42

    .line 220
    :try_start_3e
    invoke-virtual {p1}, Ljava/io/FileInputStream;->close()V
    :try_end_41
    .catch Ljava/lang/Exception; {:try_start_3e .. :try_end_41} :catch_52
    .catchall {:try_start_3e .. :try_end_41} :catchall_4f

    goto :goto_4e

    .line 216
    :catchall_42
    move-exception v0

    move-object p2, v0

    :try_start_44
    invoke-virtual {p1}, Ljava/io/FileInputStream;->close()V
    :try_end_47
    .catchall {:try_start_44 .. :try_end_47} :catchall_48

    goto :goto_4d

    :catchall_48
    move-exception v0

    move-object p1, v0

    :try_start_4a
    invoke-virtual {p2, p1}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    :goto_4d
    throw p2
    :try_end_4e
    .catch Ljava/lang/Exception; {:try_start_4a .. :try_end_4e} :catch_52
    .catchall {:try_start_4a .. :try_end_4e} :catchall_4f

    .line 224
    :cond_4e
    :goto_4e
    goto :goto_70

    .line 209
    :catchall_4f
    move-exception v0

    move-object p1, v0

    goto :goto_73

    .line 222
    :catch_52
    move-exception v0

    move-object p1, v0

    .line 223
    :try_start_54
    const-string p2, "HL5_Runner"

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string p4, "onPutMusicClip exception: "

    invoke-virtual {p3, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_70
    .catchall {:try_start_54 .. :try_end_70} :catchall_4f

    .line 225
    :goto_70
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 209
    :goto_73
    :try_start_73
    monitor-exit p0
    :try_end_74
    .catchall {:try_start_73 .. :try_end_74} :catchall_4f

    throw p1
.end method

.method public declared-synchronized onPutSoundClip(JJJI)I
    .registers 16

    monitor-enter p0

    .line 301
    :try_start_1
    iget-object p7, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz p7, :cond_59

    iget-object p7, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    if-eqz p7, :cond_59

    iget-object p7, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    invoke-virtual {p7}, Ljava/io/File;->exists()Z

    move-result p7

    if-eqz p7, :cond_59

    .line 302
    iget-object p7, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-virtual {p7, v0}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p7

    check-cast p7, Ljava/lang/Integer;

    .line 303
    if-eqz p7, :cond_28

    .line 304
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {p7}, Ljava/lang/Integer;->intValue()I

    move-result p7

    invoke-virtual {v0, p7}, Landroid/media/SoundPool;->unload(I)Z

    .line 306
    :cond_28
    new-instance p7, Ljava/io/FileInputStream;

    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->vfsFile:Ljava/io/File;

    invoke-direct {p7, v0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_2f
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_2f} :catch_5d
    .catchall {:try_start_1 .. :try_end_2f} :catchall_5a

    .line 307
    :try_start_2f
    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {p7}, Ljava/io/FileInputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object v2

    const/4 v7, 0x1

    move-wide v3, p3

    move-wide v5, p5

    invoke-virtual/range {v1 .. v7}, Landroid/media/SoundPool;->load(Ljava/io/FileDescriptor;JJI)I

    move-result p3

    .line 308
    iget-object p4, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p1

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    invoke-virtual {p4, p1, p2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_49
    .catchall {:try_start_2f .. :try_end_49} :catchall_4d

    .line 309
    :try_start_49
    invoke-virtual {p7}, Ljava/io/FileInputStream;->close()V
    :try_end_4c
    .catch Ljava/lang/Exception; {:try_start_49 .. :try_end_4c} :catch_5d
    .catchall {:try_start_49 .. :try_end_4c} :catchall_5a

    goto :goto_59

    .line 306
    :catchall_4d
    move-exception v0

    move-object p1, v0

    :try_start_4f
    invoke-virtual {p7}, Ljava/io/FileInputStream;->close()V
    :try_end_52
    .catchall {:try_start_4f .. :try_end_52} :catchall_53

    goto :goto_58

    :catchall_53
    move-exception v0

    move-object p2, v0

    :try_start_55
    invoke-virtual {p1, p2}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    :goto_58
    throw p1
    :try_end_59
    .catch Ljava/lang/Exception; {:try_start_55 .. :try_end_59} :catch_5d
    .catchall {:try_start_55 .. :try_end_59} :catchall_5a

    .line 313
    :cond_59
    :goto_59
    goto :goto_7b

    .line 300
    :catchall_5a
    move-exception v0

    move-object p1, v0

    goto :goto_7e

    .line 311
    :catch_5d
    move-exception v0

    move-object p1, v0

    .line 312
    :try_start_5f
    const-string p2, "HL5_Runner"

    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string p4, "onPutSoundClip exception: "

    invoke-virtual {p3, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_7b
    .catchall {:try_start_5f .. :try_end_7b} :catchall_5a

    .line 314
    :goto_7b
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 300
    :goto_7e
    :try_start_7e
    monitor-exit p0
    :try_end_7f
    .catchall {:try_start_7e .. :try_end_7f} :catchall_5a

    throw p1
.end method

.method protected onResume()V
    .registers 3

    .line 486
    invoke-super {p0}, Landroid/app/Activity;->onResume()V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_resume()V

    .line 487
    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->activityResumed:Z

    .line 488
    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->hideSystemUI()V

    .line 489
    iget-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->filesPrepared:Z

    if-eqz v0, :cond_14

    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-nez v0, :cond_14

    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->startGameView()V

    .line 490
    :cond_14
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_1d

    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v0}, Landroid/opengl/GLSurfaceView;->onResume()V

    .line 491
    :cond_1d
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_2b

    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->glSurfaceView:Landroid/opengl/GLSurfaceView;

    new-instance v1, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda4;

    invoke-direct {v1, p0}, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda4;-><init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/opengl/GLSurfaceView;->queueEvent(Ljava/lang/Runnable;)V

    .line 493
    :cond_2b
    :try_start_2b
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_38

    iget-boolean v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmWasPlayingOnPause:Z

    if-eqz v0, :cond_38

    .line 494
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->start()V

    .line 496
    :cond_38
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz v0, :cond_43

    .line 497
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {v0}, Landroid/media/SoundPool;->autoResume()V
    :try_end_41
    .catch Ljava/lang/Exception; {:try_start_2b .. :try_end_41} :catch_42

    goto :goto_43

    .line 499
    :catch_42
    move-exception v0

    :cond_43
    :goto_43
    nop

    .line 500
    return-void
.end method

.method public declared-synchronized onResumeMusicClip(J)I
    .registers 5

    monitor-enter p0

    .line 265
    :try_start_1
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_12

    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-nez p1, :cond_12

    .line 266
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->start()V
    :try_end_12
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_12} :catch_15
    .catchall {:try_start_1 .. :try_end_12} :catchall_13

    .line 270
    :cond_12
    goto :goto_32

    .line 264
    :catchall_13
    move-exception p1

    goto :goto_35

    .line 268
    :catch_15
    move-exception p1

    .line 269
    :try_start_16
    const-string p2, "HL5_Runner"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onResumeMusicClip exception: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_32
    .catchall {:try_start_16 .. :try_end_32} :catchall_13

    .line 271
    :goto_32
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 264
    :goto_35
    :try_start_35
    monitor-exit p0
    :try_end_36
    .catchall {:try_start_35 .. :try_end_36} :catchall_13

    throw p1
.end method

.method public declared-synchronized onSetMusicVolume(I)V
    .registers 4

    monitor-enter p0

    .line 286
    if-gtz p1, :cond_7

    .line 287
    const/4 p1, 0x0

    :try_start_4
    iput p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    goto :goto_1a

    .line 290
    :cond_7
    int-to-float p1, p1

    const/high16 v0, 0x42c80000    # 100.0f

    div-float/2addr p1, v0

    const v0, 0x3f19999a    # 0.6f

    invoke-static {v0, p1}, Ljava/lang/Math;->max(FF)F

    move-result p1

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-static {v0, p1}, Ljava/lang/Math;->min(FF)F

    move-result p1

    iput p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F
    :try_end_1a
    .catchall {:try_start_4 .. :try_end_1a} :catchall_2c

    .line 293
    :goto_1a
    :try_start_1a
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_29

    .line 294
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    iget v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    iget v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->musicVolume:F

    invoke-virtual {p1, v0, v1}, Landroid/media/MediaPlayer;->setVolume(FF)V
    :try_end_27
    .catch Ljava/lang/Exception; {:try_start_1a .. :try_end_27} :catch_28
    .catchall {:try_start_1a .. :try_end_27} :catchall_2c

    goto :goto_29

    .line 296
    :catch_28
    move-exception p1

    :cond_29
    :goto_29
    nop

    .line 297
    monitor-exit p0

    return-void

    .line 285
    :catchall_2c
    move-exception p1

    :try_start_2d
    monitor-exit p0
    :try_end_2e
    .catchall {:try_start_2d .. :try_end_2e} :catchall_2c

    throw p1
.end method

.method public declared-synchronized onSetSoundVolume(I)V
    .registers 3

    monitor-enter p0

    .line 361
    if-gtz p1, :cond_7

    .line 362
    const/4 p1, 0x0

    :try_start_4
    iput p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundVolume:F

    goto :goto_1a

    .line 364
    :cond_7
    int-to-float p1, p1

    const/high16 v0, 0x42c80000    # 100.0f

    div-float/2addr p1, v0

    const v0, 0x3f19999a    # 0.6f

    invoke-static {v0, p1}, Ljava/lang/Math;->max(FF)F

    move-result p1

    const/high16 v0, 0x3f800000    # 1.0f

    invoke-static {v0, p1}, Ljava/lang/Math;->min(FF)F

    move-result p1

    iput p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundVolume:F
    :try_end_1a
    .catchall {:try_start_4 .. :try_end_1a} :catchall_1c

    .line 366
    :goto_1a
    monitor-exit p0

    return-void

    .line 360
    :catchall_1c
    move-exception p1

    :try_start_1d
    monitor-exit p0
    :try_end_1e
    .catchall {:try_start_1d .. :try_end_1e} :catchall_1c

    throw p1
.end method

.method public declared-synchronized onStopMusicClip(J)I
    .registers 5

    monitor-enter p0

    .line 243
    :try_start_1
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_12

    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-eqz p1, :cond_12

    .line 244
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->bgmPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->stop()V
    :try_end_12
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_12} :catch_15
    .catchall {:try_start_1 .. :try_end_12} :catchall_13

    .line 248
    :cond_12
    goto :goto_32

    .line 242
    :catchall_13
    move-exception p1

    goto :goto_35

    .line 246
    :catch_15
    move-exception p1

    .line 247
    :try_start_16
    const-string p2, "HL5_Runner"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onStopMusicClip exception: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_32
    .catchall {:try_start_16 .. :try_end_32} :catchall_13

    .line 249
    :goto_32
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 242
    :goto_35
    :try_start_35
    monitor-exit p0
    :try_end_36
    .catchall {:try_start_35 .. :try_end_36} :catchall_13

    throw p1
.end method

.method public declared-synchronized onStopSoundClip(J)I
    .registers 5

    monitor-enter p0

    .line 334
    :try_start_1
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    if-eqz v0, :cond_1c

    .line 335
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Integer;

    .line 336
    if-eqz p1, :cond_1c

    .line 337
    iget-object p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->soundPool:Landroid/media/SoundPool;

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    invoke-virtual {p2, p1}, Landroid/media/SoundPool;->stop(I)V
    :try_end_1c
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1c} :catch_1f
    .catchall {:try_start_1 .. :try_end_1c} :catchall_1d

    .line 342
    :cond_1c
    goto :goto_3c

    .line 333
    :catchall_1d
    move-exception p1

    goto :goto_3f

    .line 340
    :catch_1f
    move-exception p1

    .line 341
    :try_start_20
    const-string p2, "HL5_Runner"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onStopSoundClip exception: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p2, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_3c
    .catchall {:try_start_20 .. :try_end_3c} :catchall_1d

    .line 343
    :goto_3c
    monitor-exit p0

    const/4 p1, 0x0

    return p1

    .line 333
    :goto_3f
    :try_start_3f
    monitor-exit p0
    :try_end_40
    .catchall {:try_start_3f .. :try_end_40} :catchall_1d

    throw p1
.end method

.method public onSurfaceChanged(Ljavax/microedition/khronos/opengles/GL10;II)V
    .registers 10

    .line 427
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "GLThread: onSurfaceChanged: "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "x"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "HL5_Runner"

    invoke-static {v0, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 428
    iget-boolean p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->surfaceNeedsInit:Z

    if-eqz p1, :cond_36

    .line 429
    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->internalPath:Ljava/lang/String;

    iget-object v2, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->externalPath:Ljava/lang/String;

    iget-boolean v5, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->debugEnabled:Z

    move-object v0, p0

    move v3, p2

    move v4, p3

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_nSurfBefore()V
    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnSurfaceCreated(Ljava/lang/String;Ljava/lang/String;IIZ)V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_nSurfAfter()V

    .line 430
    const/4 p1, 0x0

    iput-boolean p1, v0, Lco/kr/eamobile/hl5/runner/MainActivity;->surfaceNeedsInit:Z

    goto :goto_39

    .line 428
    :cond_36
    move-object v0, p0

    move v3, p2

    move v4, p3

    .line 432
    :goto_39
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_nChangedBefore()V
    invoke-direct {p0, v3, v4}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnSurfaceChanged(II)V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_nChangedAfter()V

    .line 433
    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->nativeOnResume()V
    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_nResumeAfter()V

    .line 434
    return-void
.end method

.method public onSurfaceCreated(Ljavax/microedition/khronos/opengles/GL10;Ljavax/microedition/khronos/egl/EGLConfig;)V
    .registers 3

    invoke-static {}, Lco/kr/eamobile/hl5/diag/Diag;->s_surfCreatedJava()V

    .line 420
    const-string p1, "HL5_Runner"

    const-string p2, "GLThread: onSurfaceCreated entered"

    invoke-static {p1, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 421
    const-string p1, "Surface created; starting target ARM32 engine..."

    invoke-virtual {p0, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->onNativeStatus(Ljava/lang/String;)V

    .line 422
    const/4 p1, 0x1

    iput-boolean p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity;->surfaceNeedsInit:Z

    .line 423
    return-void
.end method

.method public onWindowFocusChanged(Z)V
    .registers 2

    .line 369
    invoke-super {p0, p1}, Landroid/app/Activity;->onWindowFocusChanged(Z)V

    .line 370
    if-eqz p1, :cond_8

    .line 371
    invoke-direct {p0}, Lco/kr/eamobile/hl5/runner/MainActivity;->hideSystemUI()V

    .line 373
    :cond_8
    return-void
.end method
