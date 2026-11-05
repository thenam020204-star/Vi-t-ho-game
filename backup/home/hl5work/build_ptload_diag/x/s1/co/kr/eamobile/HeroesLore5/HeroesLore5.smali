.class public Lco/kr/eamobile/HeroesLore5/HeroesLore5;
.super Lco/kr/eamobile/drm/LGTActivity;
.source "HeroesLore5.java"


# direct methods
.method public constructor <init>()V
    .registers 24

    .prologue
    .line 7
    const-string v1, "HeroesLore5"

    const-string v2, "AQ00108540"

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x0

    const/high16 v8, 0x40000000    # 2.0f

    const/4 v9, 0x0

    const/4 v10, 0x1

    const/4 v11, 0x0

    const/4 v12, 0x0

    const-string v13, "False"

    const/4 v14, 0x0

    const/4 v15, 0x0

    const/16 v16, 0x0

    const/16 v17, 0x1

    const-string v18, "${extra.ad.id}"

    const-string v19, "None"

    const/16 v20, 0x0

    const/16 v21, 0x0

    const-string v22, "None"

    move-object/from16 v0, p0

    invoke-direct/range {v0 .. v22}, Lco/kr/eamobile/drm/LGTActivity;-><init>(Ljava/lang/String;Ljava/lang/String;IIIIIFIIIILjava/lang/String;ZZZILjava/lang/String;Ljava/lang/String;IILjava/lang/String;)V

    .line 30
    return-void
.end method
