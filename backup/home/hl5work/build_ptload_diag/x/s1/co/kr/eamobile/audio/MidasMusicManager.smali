.class public Lco/kr/eamobile/audio/MidasMusicManager;
.super Ljava/lang/Object;


# static fields
.field private static final LOG_TAG:Ljava/lang/String; = "MidasMusicManager"

.field private static instance:Lco/kr/eamobile/audio/MidasMusicManager;


# instance fields
.field private mAudioManager:Landroid/media/AudioManager;

.field private mediaPlayer:Landroid/media/MediaPlayer;

.field private musicMaxVolume:F

.field private musicVolume:F

.field private mustResume:Z

.field private vfsOffset:J

.field private vfsfd:Ljava/io/FileDescriptor;

.field private volumeRateWIPI:F


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V
    .registers 7

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    invoke-virtual {p2}, Landroid/content/res/AssetFileDescriptor;->getFileDescriptor()Ljava/io/FileDescriptor;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->vfsfd:Ljava/io/FileDescriptor;

    iput-wide p3, p0, Lco/kr/eamobile/audio/MidasMusicManager;->vfsOffset:J

    new-instance v0, Landroid/media/MediaPlayer;

    invoke-direct {v0}, Landroid/media/MediaPlayer;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    const/4 v0, 0x0

    iput-boolean v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mustResume:Z

    const-string v0, "audio"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mAudioManager:Landroid/media/AudioManager;

    const/4 v0, 0x0

    iput v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->musicMaxVolume:F

    iget v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->musicMaxVolume:F

    const/high16 v1, 0x42c80000    # 100.0f

    div-float/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->volumeRateWIPI:F

    return-void
.end method

.method public static clipFree(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/MidasMusicManager;->unload()I

    move-result v0

    return v0
.end method

.method public static clipPause(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/MidasMusicManager;->pause()I

    move-result v0

    return v0
.end method

.method public static clipPlay(JZ)I
    .registers 4

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-virtual {v0, p2}, Lco/kr/eamobile/audio/MidasMusicManager;->play(Z)I

    move-result v0

    return v0
.end method

.method public static clipResume(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/MidasMusicManager;->resume()I

    move-result v0

    return v0
.end method

.method public static clipStop(J)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/MidasMusicManager;->stop()I

    move-result v0

    return v0
.end method

.method public static createMusicManager(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V
    .registers 5

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-direct {v0, p0, p1, p2, p3}, Lco/kr/eamobile/audio/MidasMusicManager;-><init>(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V

    sput-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    :cond_b
    return-void
.end method

.method private destory()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->release()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    return-void
.end method

.method public static destroyMusicManager()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-direct {v0}, Lco/kr/eamobile/audio/MidasMusicManager;->destory()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    return-void
.end method

.method public static getInstance()Lco/kr/eamobile/audio/MidasMusicManager;
    .registers 1

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    return-object v0
.end method

.method public static getVolume()I
    .registers 2

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v0, v0, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    sget-object v1, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasMusicManager;->volumeRateWIPI:F

    div-float/2addr v0, v1

    float-to-int v0, v0

    return v0
.end method

.method public static putClip(JJJ)I
    .registers 7

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-virtual {v0, p2, p3, p4, p5}, Lco/kr/eamobile/audio/MidasMusicManager;->loadFromAfd(JJ)I

    move-result v0

    return v0
.end method

.method public static setVolume(I)V
    .registers 4

    const/4 v2, 0x0

    int-to-float v0, p0

    sget-object v1, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasMusicManager;->volumeRateWIPI:F

    mul-float/2addr v0, v1

    cmpg-float v1, v0, v2

    if-gez v1, :cond_c

    move v0, v2

    :cond_c
    sget-object v1, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasMusicManager;->musicMaxVolume:F

    cmpl-float v1, v0, v1

    if-lez v1, :cond_18

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v0, v0, Lco/kr/eamobile/audio/MidasMusicManager;->musicMaxVolume:F

    :cond_18
    sget-object v1, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iput v0, v1, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    sget-object v0, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget-object v0, v0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    sget-object v1, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v1, v1, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    sget-object v2, Lco/kr/eamobile/audio/MidasMusicManager;->instance:Lco/kr/eamobile/audio/MidasMusicManager;

    iget v2, v2, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    invoke-virtual {v0, v1, v2}, Landroid/media/MediaPlayer;->setVolume(FF)V

    return-void
.end method


# virtual methods
.method public loadFromAfd(JJ)I
    .registers 12

    const/4 v6, -0x1

    :try_start_1
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->reset()V

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    iget-object v1, p0, Lco/kr/eamobile/audio/MidasMusicManager;->vfsfd:Ljava/io/FileDescriptor;

    iget-wide v2, p0, Lco/kr/eamobile/audio/MidasMusicManager;->vfsOffset:J

    add-long/2addr v2, p1

    move-wide v4, p3

    invoke-virtual/range {v0 .. v5}, Landroid/media/MediaPlayer;->setDataSource(Ljava/io/FileDescriptor;JJ)V

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/MediaPlayer;->setAudioStreamType(I)V
    :try_end_17
    .catch Ljava/lang/IllegalArgumentException; {:try_start_1 .. :try_end_17} :catch_1e
    .catch Ljava/lang/IllegalStateException; {:try_start_1 .. :try_end_17} :catch_24
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_17} :catch_2a

    :try_start_17
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->prepare()V
    :try_end_1c
    .catch Ljava/lang/IllegalStateException; {:try_start_17 .. :try_end_1c} :catch_30
    .catch Ljava/io/IOException; {:try_start_17 .. :try_end_1c} :catch_36

    const/4 v0, 0x0

    :goto_1d
    return v0

    :catch_1e
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalArgumentException;->printStackTrace()V

    move v0, v6

    goto :goto_1d

    :catch_24
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    move v0, v6

    goto :goto_1d

    :catch_2a
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    move v0, v6

    goto :goto_1d

    :catch_30
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    move v0, v6

    goto :goto_1d

    :catch_36
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    move v0, v6

    goto :goto_1d
.end method

.method public pause()I
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_10

    :try_start_8
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->pause()V
    :try_end_d
    .catch Ljava/lang/IllegalStateException; {:try_start_8 .. :try_end_d} :catch_12

    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mustResume:Z

    :cond_10
    const/4 v0, 0x0

    :goto_11
    return v0

    :catch_12
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    const/4 v0, -0x1

    goto :goto_11
.end method

.method public play(Z)I
    .registers 5

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p1}, Landroid/media/MediaPlayer;->setLooping(Z)V

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    iget v1, p0, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    iget v2, p0, Lco/kr/eamobile/audio/MidasMusicManager;->musicVolume:F

    invoke-virtual {v0, v1, v2}, Landroid/media/MediaPlayer;->setVolume(FF)V

    :try_start_e
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->start()V
    :try_end_13
    .catch Ljava/lang/IllegalStateException; {:try_start_e .. :try_end_13} :catch_15

    const/4 v0, 0x0

    :goto_14
    return v0

    :catch_15
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    const/4 v0, -0x1

    goto :goto_14
.end method

.method public resume()I
    .registers 3

    const/4 v1, 0x0

    iget-boolean v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mustResume:Z

    if-eqz v0, :cond_c

    :try_start_5
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->start()V
    :try_end_a
    .catch Ljava/lang/IllegalStateException; {:try_start_5 .. :try_end_a} :catch_e

    iput-boolean v1, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mustResume:Z

    :cond_c
    move v0, v1

    :goto_d
    return v0

    :catch_e
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    const/4 v0, -0x1

    goto :goto_d
.end method

.method public stop()I
    .registers 3

    const/4 v1, -0x1

    :try_start_1
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->stop()V

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->prepare()V
    :try_end_b
    .catch Ljava/lang/IllegalStateException; {:try_start_1 .. :try_end_b} :catch_d
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_b} :catch_13

    const/4 v0, 0x0

    :goto_c
    return v0

    :catch_d
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    move v0, v1

    goto :goto_c

    :catch_13
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    move v0, v1

    goto :goto_c
.end method

.method public unload()I
    .registers 2

    :try_start_0
    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->stop()V

    iget-object v0, p0, Lco/kr/eamobile/audio/MidasMusicManager;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->reset()V
    :try_end_a
    .catch Ljava/lang/IllegalStateException; {:try_start_0 .. :try_end_a} :catch_c

    const/4 v0, 0x0

    :goto_b
    return v0

    :catch_c
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->printStackTrace()V

    const/4 v0, -0x1

    goto :goto_b
.end method
