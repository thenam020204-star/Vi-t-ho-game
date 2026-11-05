.class public Lco/kr/eamobile/audio/MidasMediaManager;
.super Ljava/lang/Object;


# static fields
.field private static final TAG:Ljava/lang/String; = "MidasMediaManager"

.field private static instance:Lco/kr/eamobile/audio/MidasMediaManager;


# instance fields
.field private audioManager:Landroid/media/AudioManager;

.field private nMaxVolume:I

.field private staticPcmMediaPoolMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Long;",
            "Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;",
            ">;"
        }
    .end annotation
.end field

.field private streamPcmMediaPoolMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Long;",
            "Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;",
            ">;"
        }
    .end annotation
.end field

.field private volumeRateWIPI:F


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    return-void
.end method

.method private constructor <init>(Landroid/media/AudioManager;)V
    .registers 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lco/kr/eamobile/audio/MidasMediaManager;->audioManager:Landroid/media/AudioManager;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->audioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v0

    iput v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->nMaxVolume:I

    iget v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->nMaxVolume:I

    int-to-float v0, v0

    const/high16 v1, 0x42c80000    # 100.0f

    div-float/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->volumeRateWIPI:F

    return-void
.end method

.method public static clipFree(J)I
    .registers 5

    const/4 v2, 0x0

    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    if-eqz p0, :cond_18

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->destroy()V

    move v0, v2

    :goto_17
    return v0

    :cond_18
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    if-eqz p0, :cond_34

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->destroy()V

    :try_start_29
    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->join()V
    :try_end_2c
    .catch Ljava/lang/InterruptedException; {:try_start_29 .. :try_end_2c} :catch_2e
    .catchall {:try_start_29 .. :try_end_2c} :catchall_31

    move v0, v2

    goto :goto_17

    :catch_2e
    move-exception v0

    move v0, v2

    goto :goto_17

    :catchall_31
    move-exception v0

    move v0, v2

    goto :goto_17

    :cond_34
    const/4 v0, -0x1

    goto :goto_17
.end method

.method public static clipPause(J)I
    .registers 4

    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    if-eqz p0, :cond_17

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pauseClip()I

    move-result v0

    :goto_16
    return v0

    :cond_17
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    if-eqz p0, :cond_2a

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pauseClip()I

    move-result v0

    goto :goto_16

    :cond_2a
    const/4 v0, -0x1

    goto :goto_16
.end method

.method public static clipPlay(JZ)I
    .registers 5

    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    if-eqz p0, :cond_17

    invoke-virtual {p0, p2}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->playClip(Z)I

    move-result v0

    :goto_16
    return v0

    :cond_17
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    if-eqz p0, :cond_2a

    invoke-virtual {p0, p2}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->playClip(Z)I

    move-result v0

    goto :goto_16

    :cond_2a
    const/4 v0, -0x1

    goto :goto_16
.end method

.method public static clipResume(J)I
    .registers 4

    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    if-eqz p0, :cond_17

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->resumeClip()I

    move-result v0

    :goto_16
    return v0

    :cond_17
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    if-eqz p0, :cond_2a

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->resumeClip()I

    move-result v0

    goto :goto_16

    :cond_2a
    const/4 v0, -0x1

    goto :goto_16
.end method

.method public static clipStop(J)I
    .registers 4

    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    if-eqz p0, :cond_17

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->stopClip()I

    move-result v0

    :goto_16
    return v0

    :cond_17
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    if-eqz p0, :cond_2a

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stopClip()I

    move-result v0

    goto :goto_16

    :cond_2a
    const/4 v0, -0x1

    goto :goto_16
.end method

.method public static createMediaManager(Landroid/media/AudioManager;)V
    .registers 2

    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-direct {v0, p0}, Lco/kr/eamobile/audio/MidasMediaManager;-><init>(Landroid/media/AudioManager;)V

    sput-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    :cond_b
    return-void
.end method

.method private destory()V
    .registers 4

    const/4 v2, 0x0

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    if-eqz v0, :cond_26

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_f
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1f

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->destroy()V

    goto :goto_f

    :cond_1f
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->clear()V

    iput-object v2, p0, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    :cond_26
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    if-eqz v0, :cond_50

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_34
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_49

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->destroy()V

    :try_start_43
    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->join()V
    :try_end_46
    .catch Ljava/lang/InterruptedException; {:try_start_43 .. :try_end_46} :catch_47

    goto :goto_34

    :catch_47
    move-exception v0

    goto :goto_34

    :cond_49
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->clear()V

    iput-object v2, p0, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    :cond_50
    iput-object v2, p0, Lco/kr/eamobile/audio/MidasMediaManager;->audioManager:Landroid/media/AudioManager;

    return-void
.end method

.method public static destroyMediaManager()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-direct {v0}, Lco/kr/eamobile/audio/MidasMediaManager;->destory()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    return-void
.end method

.method public static getClipStatus(J)I
    .registers 4

    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    if-eqz p0, :cond_1f

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->getInstanceStat()I

    move-result v0

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->getPlayBackStat()I

    move-result v1

    invoke-static {v0, v1}, Lco/kr/eamobile/constant/WIPIConstant;->getWIPIMediaStatus(II)I

    move-result v0

    :goto_1e
    return v0

    :cond_1f
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v1

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {v1, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    if-eqz p0, :cond_2d

    :cond_2d
    const/4 v0, -0x1

    goto :goto_1e
.end method

.method public static getInstance()Lco/kr/eamobile/audio/MidasMediaManager;
    .registers 1

    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    return-object v0
.end method

.method private getMaxVolume()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->nMaxVolume:I

    return v0
.end method

.method public static getVolume()I
    .registers 2

    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    if-nez v0, :cond_6

    const/4 v0, -0x1

    :goto_5
    return v0

    :cond_6
    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    iget-object v0, v0, Lco/kr/eamobile/audio/MidasMediaManager;->audioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v0

    int-to-float v0, v0

    sget-object v1, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-direct {v1}, Lco/kr/eamobile/audio/MidasMediaManager;->getVoumeRateWIPI()F

    move-result v1

    div-float/2addr v0, v1

    float-to-int v0, v0

    goto :goto_5
.end method

.method private getVoumeRateWIPI()F
    .registers 2

    iget v0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->volumeRateWIPI:F

    return v0
.end method

.method public static putClip(JII[BIIIII)I
    .registers 20

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object p2

    if-nez p2, :cond_8

    const/4 p0, -0x1

    :goto_7
    return p0

    :cond_8
    const-wide/16 v2, 0x0

    cmp-long p2, p0, v2

    if-gtz p2, :cond_10

    const/4 p0, -0x1

    goto :goto_7

    :cond_10
    if-nez p4, :cond_14

    const/4 p0, -0x1

    goto :goto_7

    :cond_14
    if-gtz p5, :cond_18

    const/4 p0, -0x1

    goto :goto_7

    :cond_18
    invoke-static {p0, p1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object p2

    packed-switch p3, :pswitch_data_9a

    const/4 p0, -0x1

    goto :goto_7

    :pswitch_21
    if-nez p9, :cond_47

    new-instance v2, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;

    invoke-direct {v2}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;-><init>()V

    move-wide v3, p0

    move-object v5, p4

    move v6, p5

    move/from16 v7, p6

    move/from16 v8, p7

    move/from16 v9, p8

    invoke-virtual/range {v2 .. v9}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->prepareAudioInfo(J[BIIII)I

    move-result p0

    if-gez p0, :cond_3c

    invoke-virtual {v2}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->destroy()V

    const/4 p0, -0x1

    goto :goto_7

    :cond_3c
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object p0

    iget-object p0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->staticPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {p0, p2, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_45
    :goto_45
    const/4 p0, 0x0

    goto :goto_7

    :cond_47
    const/4 p3, 0x1

    move/from16 v0, p9

    move v1, p3

    if-ne v0, v1, :cond_79

    new-instance v2, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;

    invoke-direct {v2}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;-><init>()V

    move-wide v3, p0

    move-object v5, p4

    move v6, p5

    move/from16 v7, p6

    move/from16 v8, p7

    move/from16 v9, p8

    invoke-virtual/range {v2 .. v9}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->prepareAudioInfo(J[BIIII)I

    move-result p0

    if-gez p0, :cond_6f

    invoke-virtual {v2}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->destroy()V

    :try_start_64
    invoke-virtual {v2}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->join()V
    :try_end_67
    .catch Ljava/lang/InterruptedException; {:try_start_64 .. :try_end_67} :catch_69
    .catchall {:try_start_64 .. :try_end_67} :catchall_6c

    const/4 p0, -0x1

    goto :goto_7

    :catch_69
    move-exception p0

    const/4 p0, -0x1

    goto :goto_7

    :catchall_6c
    move-exception p0

    const/4 p0, -0x1

    goto :goto_7

    :cond_6f
    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object p0

    iget-object p0, p0, Lco/kr/eamobile/audio/MidasMediaManager;->streamPcmMediaPoolMap:Ljava/util/HashMap;

    invoke-virtual {p0, p2, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_45

    :cond_79
    const/4 p0, 0x2

    move/from16 v0, p9

    move v1, p0

    if-ne v0, v1, :cond_81

    const/4 p0, -0x1

    goto :goto_7

    :cond_81
    const/4 p0, -0x1

    goto :goto_7

    :pswitch_83
    if-nez p9, :cond_87

    const/4 p0, -0x1

    goto :goto_7

    :cond_87
    const/4 p0, 0x1

    move/from16 v0, p9

    move v1, p0

    if-ne v0, v1, :cond_90

    const/4 p0, -0x1

    goto/16 :goto_7

    :cond_90
    const/4 p0, 0x2

    move/from16 v0, p9

    move v1, p0

    if-eq v0, v1, :cond_45

    const/4 p0, -0x1

    goto/16 :goto_7

    nop

    :pswitch_data_9a
    .packed-switch 0x0
        :pswitch_21
        :pswitch_83
    .end packed-switch
.end method

.method public static putClipFromFile(JLjava/lang/String;I)I
    .registers 5

    const/4 v0, -0x1

    return v0
.end method

.method public static setVolume(I)V
    .registers 5

    const/4 v3, 0x0

    int-to-float v0, p0

    sget-object v1, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-direct {v1}, Lco/kr/eamobile/audio/MidasMediaManager;->getVoumeRateWIPI()F

    move-result v1

    mul-float/2addr v0, v1

    float-to-int v0, v0

    if-gez v0, :cond_d

    move v0, v3

    :cond_d
    sget-object v1, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-direct {v1}, Lco/kr/eamobile/audio/MidasMediaManager;->getMaxVolume()I

    move-result v1

    if-le v0, v1, :cond_1b

    sget-object v0, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-direct {v0}, Lco/kr/eamobile/audio/MidasMediaManager;->getMaxVolume()I

    move-result v0

    :cond_1b
    sget-object v1, Lco/kr/eamobile/audio/MidasMediaManager;->instance:Lco/kr/eamobile/audio/MidasMediaManager;

    iget-object v1, v1, Lco/kr/eamobile/audio/MidasMediaManager;->audioManager:Landroid/media/AudioManager;

    const/4 v2, 0x3

    invoke-virtual {v1, v2, v0, v3}, Landroid/media/AudioManager;->setStreamVolume(III)V

    return-void
.end method
