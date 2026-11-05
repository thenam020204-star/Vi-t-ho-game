.class public Lco/kr/eamobile/audio/MidasSoundManager;
.super Ljava/lang/Object;


# static fields
.field private static final LOG_TAG:Ljava/lang/String; = "MidasSoundManager"

.field private static instance:Lco/kr/eamobile/audio/MidasSoundManager;


# instance fields
.field private final MAX_STREAM_CNT:I

.field private final RELOAD_RETRY_CNT:I

.field private final WAITING_TIME_FOR_LOADING_MS:I

.field private mAudioManager:Landroid/media/AudioManager;

.field private mContext:Landroid/content/Context;

.field private mSoundActionUsingStreamMap:Ljava/util/Vector;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Vector",
            "<",
            "Ljava/lang/Long;",
            ">;"
        }
    .end annotation
.end field

.field private mSoundPool:Landroid/media/SoundPool;

.field private mSoundPoolMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Long;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private mSoundPoolStreamMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Long;",
            "Ljava/util/Vector;",
            ">;"
        }
    .end annotation
.end field

.field private soundMaxVolume:F

.field private soundVolume:F

.field private vfsOffset:J

.field private vfsfd:Ljava/io/FileDescriptor;

.field private volumeRateWIPI:F


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V
    .registers 9

    const/4 v3, 0x4

    const/4 v2, 0x3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput v3, p0, Lco/kr/eamobile/audio/MidasSoundManager;->MAX_STREAM_CNT:I

    const/4 v0, 0x5

    iput v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->RELOAD_RETRY_CNT:I

    const/16 v0, 0xc8

    iput v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->WAITING_TIME_FOR_LOADING_MS:I

    iput-object p1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mContext:Landroid/content/Context;

    invoke-virtual {p2}, Landroid/content/res/AssetFileDescriptor;->getFileDescriptor()Ljava/io/FileDescriptor;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->vfsfd:Ljava/io/FileDescriptor;

    iput-wide p3, p0, Lco/kr/eamobile/audio/MidasSoundManager;->vfsOffset:J

    new-instance v0, Landroid/media/SoundPool;

    const/4 v1, 0x0

    invoke-direct {v0, v3, v2, v1}, Landroid/media/SoundPool;-><init>(III)V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    new-instance v0, Ljava/util/Vector;

    invoke-direct {v0}, Ljava/util/Vector;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mContext:Landroid/content/Context;

    const-string v1, "audio"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mAudioManager:Landroid/media/AudioManager;

    const/4 v0, 0x0

    iput v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->soundVolume:F

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mAudioManager:Landroid/media/AudioManager;

    invoke-virtual {v0, v2}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->soundMaxVolume:F

    iget v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->soundMaxVolume:F

    const/high16 v1, 0x42c80000    # 100.0f

    div-float/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->volumeRateWIPI:F

    return-void
.end method

.method public static clipFree(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-virtual {v0, p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->unload(J)I

    move-result v0

    return v0
.end method

.method public static clipPause(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-virtual {v0, p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->pause(J)I

    move-result v0

    return v0
.end method

.method public static clipPlay(JZ)I
    .registers 4

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-virtual {v0, p0, p1, p2}, Lco/kr/eamobile/audio/MidasSoundManager;->play(JZ)I

    move-result v0

    return v0
.end method

.method public static clipResume(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-virtual {v0, p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->resume(J)I

    move-result v0

    return v0
.end method

.method public static clipStop(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-virtual {v0, p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->stop(J)I

    move-result v0

    return v0
.end method

.method public static createSoundManager(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V
    .registers 5

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-direct {v0, p0, p1, p2, p3}, Lco/kr/eamobile/audio/MidasSoundManager;-><init>(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V

    sput-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    :cond_b
    return-void
.end method

.method private destory()V
    .registers 7

    const/4 v5, 0x0

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    if-eqz v0, :cond_c

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-virtual {v0}, Ljava/util/Vector;->clear()V

    iput-object v5, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    :cond_c
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    if-eqz v0, :cond_4b

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_1a
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_44

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Vector;

    invoke-virtual {v0}, Ljava/util/Vector;->elements()Ljava/util/Enumeration;

    move-result-object v3

    :goto_2a
    invoke-interface {v3}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v1

    if-eqz v1, :cond_40

    invoke-interface {v3}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    iget-object v4, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v4, v1}, Landroid/media/SoundPool;->stop(I)V

    goto :goto_2a

    :cond_40
    invoke-virtual {v0}, Ljava/util/Vector;->clear()V

    goto :goto_1a

    :cond_44
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->clear()V

    iput-object v5, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    :cond_4b
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    if-eqz v0, :cond_76

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_59
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_6f

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iget-object v2, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v2, v0}, Landroid/media/SoundPool;->unload(I)Z

    goto :goto_59

    :cond_6f
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->clear()V

    iput-object v5, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    :cond_76
    return-void
.end method

.method public static destroySoundManager()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-direct {v0}, Lco/kr/eamobile/audio/MidasSoundManager;->destory()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    return-void
.end method

.method public static getInstance()Lco/kr/eamobile/audio/MidasSoundManager;
    .registers 1

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    return-object v0
.end method

.method public static getVolume()I
    .registers 2

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    iget v0, v0, Lco/kr/eamobile/audio/MidasSoundManager;->soundVolume:F

    sget-object v1, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasSoundManager;->volumeRateWIPI:F

    div-float/2addr v0, v1

    float-to-int v0, v0

    return v0
.end method

.method public static putClip(JJJI)I
    .registers 15

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    move-wide v1, p0

    move-wide v3, p2

    move-wide v5, p4

    move v7, p6

    invoke-virtual/range {v0 .. v7}, Lco/kr/eamobile/audio/MidasSoundManager;->load(JJJI)I

    move-result v0

    return v0
.end method

.method public static setVolume(I)V
    .registers 4

    const/4 v2, 0x0

    int-to-float v0, p0

    sget-object v1, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasSoundManager;->volumeRateWIPI:F

    mul-float/2addr v0, v1

    cmpg-float v1, v0, v2

    if-gez v1, :cond_c

    move v0, v2

    :cond_c
    sget-object v1, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasSoundManager;->soundMaxVolume:F

    cmpl-float v1, v0, v1

    if-lez v1, :cond_18

    sget-object v0, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    iget v0, v0, Lco/kr/eamobile/audio/MidasSoundManager;->soundMaxVolume:F

    :cond_18
    sget-object v1, Lco/kr/eamobile/audio/MidasSoundManager;->instance:Lco/kr/eamobile/audio/MidasSoundManager;

    iput v0, v1, Lco/kr/eamobile/audio/MidasSoundManager;->soundVolume:F

    return-void
.end method

.method private waitTimeForLoading()V
    .registers 3

    const-wide/16 v0, 0xc8

    :try_start_2
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_5
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_5} :catch_6

    :goto_5
    return-void

    :catch_6
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_5
.end method


# virtual methods
.method public load(JJJI)I
    .registers 16

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->vfsfd:Ljava/io/FileDescriptor;

    iget-wide v2, p0, Lco/kr/eamobile/audio/MidasSoundManager;->vfsOffset:J

    add-long/2addr v2, p3

    const/4 v6, 0x1

    move-wide v4, p5

    invoke-virtual/range {v0 .. v6}, Landroid/media/SoundPool;->load(Ljava/io/FileDescriptor;JJI)I

    move-result v0

    if-gtz v0, :cond_68

    const/4 v1, 0x0

    move v7, v1

    :goto_11
    const/4 v1, 0x5

    if-ge v7, v1, :cond_3e

    const-string v0, "MidasSoundManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "## Retry to load. key ==> "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->vfsfd:Ljava/io/FileDescriptor;

    iget-wide v2, p0, Lco/kr/eamobile/audio/MidasSoundManager;->vfsOffset:J

    add-long/2addr v2, p3

    const/4 v6, 0x1

    move-wide v4, p5

    invoke-virtual/range {v0 .. v6}, Landroid/media/SoundPool;->load(Ljava/io/FileDescriptor;JJI)I

    move-result v0

    invoke-direct {p0}, Lco/kr/eamobile/audio/MidasSoundManager;->waitTimeForLoading()V

    if-lez v0, :cond_64

    :cond_3e
    if-gtz v0, :cond_68

    const-string v1, "MidasSoundManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "########### load...ERROR soundID => "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " key => "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    :goto_63
    return v0

    :cond_64
    add-int/lit8 v1, v7, 0x1

    move v7, v1

    goto :goto_11

    :cond_68
    if-lez p7, :cond_7f

    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/Vector;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_7f

    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/Vector;->add(Ljava/lang/Object;)Z

    :cond_7f
    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {v1, v2, v0}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const/4 v0, 0x0

    goto :goto_63
.end method

.method public pause(J)I
    .registers 7

    const/4 v3, 0x0

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/Vector;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_39

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Vector;

    if-eqz v0, :cond_37

    invoke-virtual {v0}, Ljava/util/Vector;->elements()Ljava/util/Enumeration;

    move-result-object v1

    :goto_1f
    invoke-interface {v1}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v0

    if-eqz v0, :cond_35

    invoke-interface {v1}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iget-object v2, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v2, v0}, Landroid/media/SoundPool;->pause(I)V

    goto :goto_1f

    :cond_35
    move v0, v3

    :goto_36
    return v0

    :cond_37
    const/4 v0, -0x1

    goto :goto_36

    :cond_39
    move v0, v3

    goto :goto_36
.end method

.method public play(JZ)I
    .registers 15

    const/4 v9, 0x0

    const/4 v8, -0x1

    const-string v10, "MidasSoundManager"

    if-eqz p3, :cond_20

    move v6, v8

    :goto_7
    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    move-object v0, v1

    check-cast v0, Ljava/lang/Integer;

    move-object v2, v0

    if-nez v2, :cond_22

    const-string v1, "MidasSoundManager"

    const-string v1, "play...Failed (Unloaded)"

    invoke-static {v10, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    move v1, v8

    :goto_1f
    return v1

    :cond_20
    move v6, v9

    goto :goto_7

    :cond_22
    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    iget v3, p0, Lco/kr/eamobile/audio/MidasSoundManager;->soundVolume:F

    iget v4, p0, Lco/kr/eamobile/audio/MidasSoundManager;->soundVolume:F

    const/4 v5, 0x1

    const/high16 v7, 0x3f800000    # 1.0f

    invoke-virtual/range {v1 .. v7}, Landroid/media/SoundPool;->play(IFFIIF)I

    move-result v2

    if-nez v2, :cond_41

    const-string v1, "MidasSoundManager"

    const-string v1, "play...Failed"

    invoke-static {v10, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    invoke-virtual {p0, p1, p2}, Lco/kr/eamobile/audio/MidasSoundManager;->unload(J)I

    move v1, v8

    goto :goto_1f

    :cond_41
    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/util/Vector;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_70

    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Vector;

    if-nez v1, :cond_60

    new-instance v1, Ljava/util/Vector;

    invoke-direct {v1}, Ljava/util/Vector;-><init>()V

    :cond_60
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/Vector;->add(Ljava/lang/Object;)Z

    iget-object v2, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v3

    invoke-virtual {v2, v3, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_70
    move v1, v9

    goto :goto_1f
.end method

.method public resume(J)I
    .registers 7

    const/4 v3, 0x0

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/Vector;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_39

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Vector;

    if-eqz v0, :cond_37

    invoke-virtual {v0}, Ljava/util/Vector;->elements()Ljava/util/Enumeration;

    move-result-object v1

    :goto_1f
    invoke-interface {v1}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v0

    if-eqz v0, :cond_35

    invoke-interface {v1}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iget-object v2, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v2, v0}, Landroid/media/SoundPool;->resume(I)V

    goto :goto_1f

    :cond_35
    move v0, v3

    :goto_36
    return v0

    :cond_37
    const/4 v0, -0x1

    goto :goto_36

    :cond_39
    move v0, v3

    goto :goto_36
.end method

.method public stop(J)I
    .registers 8

    const/4 v4, 0x0

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/Vector;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3c

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Vector;

    if-eqz v0, :cond_3a

    invoke-virtual {v0}, Ljava/util/Vector;->elements()Ljava/util/Enumeration;

    move-result-object v2

    :goto_1f
    invoke-interface {v2}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v1

    if-eqz v1, :cond_35

    invoke-interface {v2}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    iget-object v3, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v3, v1}, Landroid/media/SoundPool;->stop(I)V

    goto :goto_1f

    :cond_35
    invoke-virtual {v0}, Ljava/util/Vector;->clear()V

    move v0, v4

    :goto_39
    return v0

    :cond_3a
    const/4 v0, -0x1

    goto :goto_39

    :cond_3c
    move v0, v4

    goto :goto_39
.end method

.method public unload(J)I
    .registers 5

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    if-nez v0, :cond_10

    const/4 v0, -0x1

    :goto_f
    return v0

    :cond_10
    iget-object v1, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPool:Landroid/media/SoundPool;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    invoke-virtual {v1, v0}, Landroid/media/SoundPool;->unload(I)Z

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/Vector;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3f

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundPoolStreamMap:Ljava/util/HashMap;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Vector;

    if-eqz v0, :cond_36

    invoke-virtual {v0}, Ljava/util/Vector;->clear()V

    :cond_36
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasSoundManager;->mSoundActionUsingStreamMap:Ljava/util/Vector;

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/Vector;->remove(Ljava/lang/Object;)Z

    :cond_3f
    const/4 v0, 0x0

    goto :goto_f
.end method
