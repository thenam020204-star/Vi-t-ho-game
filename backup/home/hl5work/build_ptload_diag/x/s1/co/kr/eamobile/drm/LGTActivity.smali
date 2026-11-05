.class public Lco/kr/eamobile/drm/LGTActivity;
.super Landroid/app/Activity;


# instance fields
.field mApplicationID:Ljava/lang/String;

.field mBUseSublayer:I

.field mLibName:Ljava/lang/String;

.field mMagFilterType:I

.field mMagnification:F

.field mSubLayerPixelType:I

.field mTargetScreenMarginX:I

.field mTargetScreenMarginY:I

.field mViewMode:I

.field mWipiLCDHeight:I

.field mWipiLCDPixelType:I

.field mWipiLCDWidth:I


# direct methods
.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;IIIIIFIIIILjava/lang/String;ZZZILjava/lang/String;Ljava/lang/String;IILjava/lang/String;)V
    .registers 24

    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    iput-object p1, p0, Lco/kr/eamobile/drm/LGTActivity;->mLibName:Ljava/lang/String;

    iput-object p2, p0, Lco/kr/eamobile/drm/LGTActivity;->mApplicationID:Ljava/lang/String;

    iput p3, p0, Lco/kr/eamobile/drm/LGTActivity;->mWipiLCDWidth:I

    iput p4, p0, Lco/kr/eamobile/drm/LGTActivity;->mWipiLCDHeight:I

    iput p5, p0, Lco/kr/eamobile/drm/LGTActivity;->mTargetScreenMarginX:I

    iput p6, p0, Lco/kr/eamobile/drm/LGTActivity;->mTargetScreenMarginY:I

    iput p7, p0, Lco/kr/eamobile/drm/LGTActivity;->mWipiLCDPixelType:I

    iput p8, p0, Lco/kr/eamobile/drm/LGTActivity;->mMagnification:F

    iput p9, p0, Lco/kr/eamobile/drm/LGTActivity;->mMagFilterType:I

    iput p10, p0, Lco/kr/eamobile/drm/LGTActivity;->mViewMode:I

    iput p11, p0, Lco/kr/eamobile/drm/LGTActivity;->mBUseSublayer:I

    iput p12, p0, Lco/kr/eamobile/drm/LGTActivity;->mSubLayerPixelType:I

    return-void
.end method

.method private runMidasGame()V
    .registers 4

    new-instance v0, Landroid/content/Intent;

    const-class v1, Lco/kr/eamobile/MidasActivity;

    invoke-direct {v0, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    const-string v1, "M_NAME"

    iget-object v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mLibName:Ljava/lang/String;

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    const-string v1, "APPID"

    iget-object v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mApplicationID:Ljava/lang/String;

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    const-string v1, "WIPI_WIDTH"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mWipiLCDWidth:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "WIPI_HEIGHT"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mWipiLCDHeight:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "WIPI_MX"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mTargetScreenMarginX:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "WIPI_MY"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mTargetScreenMarginY:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "WIPI_PFORMAT"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mWipiLCDPixelType:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "MAGNIFICATION"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mMagnification:F

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;F)Landroid/content/Intent;

    const-string v1, "MAG_FILTER_TYPE"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mMagFilterType:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "ORIENTATION"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mViewMode:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "BUSE_SUBLAYER"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mBUseSublayer:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    const-string v1, "SUB_PFORMAT"

    iget v2, p0, Lco/kr/eamobile/drm/LGTActivity;->mSubLayerPixelType:I

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    invoke-virtual {p0, v0}, Lco/kr/eamobile/drm/LGTActivity;->startActivity(Landroid/content/Intent;)V

    invoke-virtual {p0}, Lco/kr/eamobile/drm/LGTActivity;->finish()V

    return-void
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .registers 5
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    const/4 v0, 0x1

    const-string v1, "Cracked by Rostislav582. Support Multiscreen. LGT Mod by Rostislav582 & Xiaozhiche."

    invoke-static {p0, v1, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    const/16 v1, 0x400

    const/4 v2, 0x1

    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    invoke-virtual {p0}, Lco/kr/eamobile/drm/LGTActivity;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-virtual {p0, v2}, Lco/kr/eamobile/drm/LGTActivity;->requestWindowFeature(I)Z

    invoke-virtual {v0, v1, v1}, Landroid/view/Window;->setFlags(II)V

    invoke-direct {p0}, Lco/kr/eamobile/drm/LGTActivity;->runMidasGame()V

    return-void
.end method

.method protected onPause()V
    .registers 1

    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    return-void
.end method

.method protected onResume()V
    .registers 1

    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    return-void
.end method
