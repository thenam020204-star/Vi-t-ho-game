.class public Lco/kr/eamobile/MidasGLSurfaceView;
.super Landroid/opengl/GLSurfaceView;


# instance fields
.field private mActivityRef:Lco/kr/eamobile/MidasActivity;

.field private mCletEntry:Lco/kr/eamobile/CletEntry;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .registers 10

    const/4 v2, 0x5

    const/4 v5, 0x0

    const/16 v1, 0x8

    invoke-direct {p0, p1}, Landroid/opengl/GLSurfaceView;-><init>(Landroid/content/Context;)V

    check-cast p1, Lco/kr/eamobile/MidasActivity;

    iput-object p1, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v0

    invoke-virtual {v0}, Lco/kr/eamobile/ConvertingInfo;->getPixelType()I

    move-result v7

    if-nez v7, :cond_b6

    const/4 v0, 0x6

    move v4, v5

    move v3, v2

    move v1, v2

    move v2, v0

    :goto_1c
    new-instance v0, Lco/kr/eamobile/display/ConfigChooser;

    move v6, v5

    invoke-direct/range {v0 .. v6}, Lco/kr/eamobile/display/ConfigChooser;-><init>(IIIIII)V

    invoke-virtual {p0, v0}, Lco/kr/eamobile/MidasGLSurfaceView;->setEGLConfigChooser(Landroid/opengl/GLSurfaceView$EGLConfigChooser;)V

    if-eqz v7, :cond_2f

    invoke-virtual {p0}, Lco/kr/eamobile/MidasGLSurfaceView;->getHolder()Landroid/view/SurfaceHolder;

    move-result-object v0

    const/4 v5, -0x3

    invoke-interface {v0, v5}, Landroid/view/SurfaceHolder;->setFormat(I)V

    :cond_2f
    new-instance v0, Lco/kr/eamobile/CletEntry;

    iget-object v5, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-direct {v0, v5}, Lco/kr/eamobile/CletEntry;-><init>(Lco/kr/eamobile/MidasActivity;)V

    iput-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0, v1, v2, v3, v4}, Lco/kr/eamobile/CletEntry;->setDisplayInfo(IIII)V

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    iget-object v1, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v1}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v1

    invoke-virtual {v1}, Lco/kr/eamobile/ConvertingInfo;->getWidth()I

    move-result v1

    iget-object v2, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v2}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v2

    invoke-virtual {v2}, Lco/kr/eamobile/ConvertingInfo;->getHeight()I

    move-result v2

    iget-object v3, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v3}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v3

    invoke-virtual {v3}, Lco/kr/eamobile/ConvertingInfo;->getTargetMarginX()I

    move-result v3

    iget-object v4, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v4}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v4

    invoke-virtual {v4}, Lco/kr/eamobile/ConvertingInfo;->getTargetMarginY()I

    move-result v4

    iget-object v5, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v5}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v5

    invoke-virtual {v5}, Lco/kr/eamobile/ConvertingInfo;->getPixelType()I

    move-result v5

    invoke-virtual/range {v0 .. v5}, Lco/kr/eamobile/CletEntry;->setWIPIDisplayInfo(IIIII)V

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    iget-object v1, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v1}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v1

    invoke-virtual {v1}, Lco/kr/eamobile/ConvertingInfo;->getMagnification()F

    move-result v1

    iget-object v2, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v2}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v2

    invoke-virtual {v2}, Lco/kr/eamobile/ConvertingInfo;->getMagFilterType()I

    move-result v2

    iget-object v3, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v3}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v3

    invoke-virtual {v3}, Lco/kr/eamobile/ConvertingInfo;->getViewMode()I

    move-result v3

    invoke-virtual {v0, v1, v2, v3}, Lco/kr/eamobile/CletEntry;->setTargetViewInfo(FII)V

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    iget-object v1, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v1}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v1

    invoke-virtual {v1}, Lco/kr/eamobile/ConvertingInfo;->isSubLayerUsable()I

    move-result v1

    iget-object v2, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v2}, Lco/kr/eamobile/MidasActivity;->getConvertingInfo()Lco/kr/eamobile/ConvertingInfo;

    move-result-object v2

    invoke-virtual {v2}, Lco/kr/eamobile/ConvertingInfo;->getSubLayerPixelType()I

    move-result v2

    invoke-virtual {v0, v1, v2}, Lco/kr/eamobile/CletEntry;->setSublayerInfo(II)V

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {p0, v0}, Lco/kr/eamobile/MidasGLSurfaceView;->setRenderer(Landroid/opengl/GLSurfaceView$Renderer;)V

    return-void

    :cond_b6
    move v4, v1

    move v3, v1

    move v2, v1

    goto/16 :goto_1c
.end method

.method private allowKeyEvent(ILandroid/view/KeyEvent;)Z
    .registers 4

    invoke-virtual {p2}, Landroid/view/KeyEvent;->getRepeatCount()I

    move-result v0

    if-nez v0, :cond_9

    sparse-switch p1, :sswitch_data_e

    :cond_9
    const/4 v0, 0x0

    :goto_a
    return v0

    :sswitch_b
    const/4 v0, 0x1

    goto :goto_a

    nop

    :sswitch_data_e
    .sparse-switch
        0x4 -> :sswitch_b
        0x13 -> :sswitch_b
        0x14 -> :sswitch_b
        0x15 -> :sswitch_b
        0x16 -> :sswitch_b
        0x17 -> :sswitch_b
        0x18 -> :sswitch_b
        0x19 -> :sswitch_b
    .end sparse-switch
.end method


# virtual methods
.method public doFocusGained()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0}, Lco/kr/eamobile/CletEntry;->doFocusGained()V

    return-void
.end method

.method public doFocusLost()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0}, Lco/kr/eamobile/CletEntry;->doFocusLost()V

    return-void
.end method

.method public onDestroy()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0}, Lco/kr/eamobile/CletEntry;->onDestroy()V

    return-void
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .registers 4

    invoke-direct {p0, p1, p2}, Lco/kr/eamobile/MidasGLSurfaceView;->allowKeyEvent(ILandroid/view/KeyEvent;)Z

    move-result v0

    if-eqz v0, :cond_d

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0, p1, p2}, Lco/kr/eamobile/CletEntry;->onKeyHandleEvent(ILandroid/view/KeyEvent;)V

    const/4 v0, 0x1

    :goto_c
    return v0

    :cond_d
    invoke-super {p0, p1, p2}, Landroid/opengl/GLSurfaceView;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result v0

    goto :goto_c
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .registers 4

    invoke-direct {p0, p1, p2}, Lco/kr/eamobile/MidasGLSurfaceView;->allowKeyEvent(ILandroid/view/KeyEvent;)Z

    move-result v0

    if-eqz v0, :cond_d

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0, p1, p2}, Lco/kr/eamobile/CletEntry;->onKeyHandleEvent(ILandroid/view/KeyEvent;)V

    const/4 v0, 0x1

    :goto_c
    return v0

    :cond_d
    invoke-super {p0, p1, p2}, Landroid/opengl/GLSurfaceView;->onKeyUp(ILandroid/view/KeyEvent;)Z

    move-result v0

    goto :goto_c
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .registers 3

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0, p1}, Lco/kr/eamobile/CletEntry;->onTouchHandleEvent(Landroid/view/MotionEvent;)V

    const/4 v0, 0x1

    return v0
.end method

.method public surfaceDestroyed(Landroid/view/SurfaceHolder;)V
    .registers 3

    invoke-super {p0, p1}, Landroid/opengl/GLSurfaceView;->surfaceDestroyed(Landroid/view/SurfaceHolder;)V

    iget-object v0, p0, Lco/kr/eamobile/MidasGLSurfaceView;->mCletEntry:Lco/kr/eamobile/CletEntry;

    invoke-virtual {v0}, Lco/kr/eamobile/CletEntry;->surfaceDestroyed()V

    return-void
.end method
