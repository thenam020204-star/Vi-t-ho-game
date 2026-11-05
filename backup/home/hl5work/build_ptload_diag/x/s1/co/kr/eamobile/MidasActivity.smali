.class public Lco/kr/eamobile/MidasActivity;
.super Lco/kr/eamobile/mtx/CarrierFuncActivity;


# instance fields
.field private convertingInfo:Lco/kr/eamobile/ConvertingInfo;

.field private mMidasGLView:Landroid/opengl/GLSurfaceView;

.field private mlibName:Ljava/lang/String;

.field private wakeLock:Landroid/os/PowerManager$WakeLock;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;-><init>()V

    return-void
.end method


# virtual methods
.method public getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->convertingInfo:Lco/kr/eamobile/ConvertingInfo;

    return-object v0
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .registers 16

    invoke-super {p0, p1}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->onCreate(Landroid/os/Bundle;)V

    invoke-virtual {p0}, Lco/kr/eamobile/MidasActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    const-string v1, "M_NAME"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "APPID"

    invoke-virtual {v0, v2}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    const-string v2, "WIPI_WIDTH"

    const/4 v3, 0x0

    invoke-virtual {v0, v2, v3}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v12

    const-string v2, "WIPI_HEIGHT"

    const/4 v3, 0x0

    invoke-virtual {v0, v2, v3}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v2

    const-string v3, "WIPI_MX"

    const/4 v4, 0x0

    invoke-virtual {v0, v3, v4}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v3

    const-string v4, "WIPI_MY"

    const/4 v5, 0x0

    invoke-virtual {v0, v4, v5}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v4

    const-string v5, "WIPI_PFORMAT"

    const/4 v6, 0x0

    invoke-virtual {v0, v5, v6}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v5

    const-string v6, "MAGNIFICATION"

    const/4 v7, 0x0

    invoke-virtual {v0, v6, v7}, Landroid/content/Intent;->getFloatExtra(Ljava/lang/String;F)F

    move-result v6

    const-string v7, "MAG_FILTER_TYPE"

    const/4 v8, 0x0

    invoke-virtual {v0, v7, v8}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v7

    const-string v8, "ORIENTATION"

    const/4 v9, 0x0

    invoke-virtual {v0, v8, v9}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v8

    const-string v9, "BUSE_SUBLAYER"

    const/4 v10, 0x0

    invoke-virtual {v0, v9, v10}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v9

    const-string v10, "SUB_PFORMAT"

    const/4 v13, 0x0

    invoke-virtual {v0, v10, v13}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v10

    invoke-super {p0, v11}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->setApplicationID(Ljava/lang/String;)V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->handler:Landroid/os/Handler;

    if-nez v0, :cond_67

    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/MidasActivity;->handler:Landroid/os/Handler;

    :cond_67
    iput-object v1, p0, Lco/kr/eamobile/MidasActivity;->mlibName:Ljava/lang/String;

    rem-int/lit8 v0, v12, 0x2

    if-eqz v0, :cond_d1

    if-lez v12, :cond_d1

    add-int/lit8 v0, v12, -0x1

    move v1, v0

    :goto_72
    new-instance v0, Lco/kr/eamobile/ConvertingInfo;

    invoke-direct/range {v0 .. v10}, Lco/kr/eamobile/ConvertingInfo;-><init>(IIIIIFIIII)V

    iput-object v0, p0, Lco/kr/eamobile/MidasActivity;->convertingInfo:Lco/kr/eamobile/ConvertingInfo;

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mlibName:Ljava/lang/String;

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    new-instance v0, Lco/kr/eamobile/MidasGLSurfaceView;

    invoke-direct {v0, p0}, Lco/kr/eamobile/MidasGLSurfaceView;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {p0}, Lco/kr/eamobile/MidasActivity;->getWindow()Landroid/view/Window;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {p0, v1}, Lco/kr/eamobile/MidasActivity;->requestWindowFeature(I)Z

    const/16 v1, 0x400

    const/16 v2, 0x400

    invoke-virtual {v0, v1, v2}, Landroid/view/Window;->setFlags(II)V

    invoke-virtual {p0}, Lco/kr/eamobile/MidasActivity;->getWindow()Landroid/view/Window;

    move-result-object v1

    invoke-virtual {v1}, Landroid/view/Window;->getAttributes()Landroid/view/WindowManager$LayoutParams;

    move-result-object v1

    const/high16 v2, -0x40800000    # -1.0f

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->screenBrightness:F

    invoke-virtual {v0, v1}, Landroid/view/Window;->setAttributes(Landroid/view/WindowManager$LayoutParams;)V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {p0, v0}, Lco/kr/eamobile/MidasActivity;->setContentView(Landroid/view/View;)V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/opengl/GLSurfaceView;->setFocusableInTouchMode(Z)V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/opengl/GLSurfaceView;->setFocusable(Z)V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v0}, Landroid/opengl/GLSurfaceView;->requestFocus()Z

    const-string v0, "power"

    invoke-virtual {p0, v0}, Lco/kr/eamobile/MidasActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/os/PowerManager;

    const/16 v0, 0xa

    iget-object v1, p0, Lco/kr/eamobile/MidasActivity;->mlibName:Ljava/lang/String;

    invoke-virtual {p1, v0, v1}, Landroid/os/PowerManager;->newWakeLock(ILjava/lang/String;)Landroid/os/PowerManager$WakeLock;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/MidasActivity;->wakeLock:Landroid/os/PowerManager$WakeLock;

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->wakeLock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v0}, Landroid/os/PowerManager$WakeLock;->acquire()V

    return-void

    :cond_d1
    move v1, v12

    goto :goto_72
.end method

.method protected onPause()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->wakeLock:Landroid/os/PowerManager$WakeLock;

    if-eqz v0, :cond_9

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->wakeLock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v0}, Landroid/os/PowerManager$WakeLock;->release()V

    :cond_9
    invoke-super {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->onPause()V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_15

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v0}, Landroid/opengl/GLSurfaceView;->onPause()V

    :cond_15
    return-void
.end method

.method protected onResume()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->wakeLock:Landroid/os/PowerManager$WakeLock;

    if-eqz v0, :cond_9

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->wakeLock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v0}, Landroid/os/PowerManager$WakeLock;->acquire()V

    :cond_9
    invoke-super {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->onResume()V

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    if-eqz v0, :cond_15

    iget-object v0, p0, Lco/kr/eamobile/MidasActivity;->mMidasGLView:Landroid/opengl/GLSurfaceView;

    invoke-virtual {v0}, Landroid/opengl/GLSurfaceView;->onResume()V

    :cond_15
    return-void
.end method
