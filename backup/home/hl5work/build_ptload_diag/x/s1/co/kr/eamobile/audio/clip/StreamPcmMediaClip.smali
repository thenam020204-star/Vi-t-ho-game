.class public Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;
.super Ljava/lang/Thread;

# interfaces
.implements Lco/kr/eamobile/audio/clip/IBufferMediaClip;


# static fields
.field private static final STATE_DESTROYED:I = 0x40

.field private static final STATE_INIT:I = 0x2

.field private static final STATE_NEED_DESTROY:I = 0x20

.field private static final STATE_PAUSED:I = 0x8

.field private static final STATE_PLAYING:I = 0x4

.field private static final STATE_STOPPED:I = 0x10

.field private static final STATE_UNINIT:I = 0x1

.field private static final TAG:Ljava/lang/String; = "StreamPcmMediaClip"


# instance fields
.field private at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

.field private audioBufferWrittenCnt:I

.field private audioBufferWrittenSum:I

.field private bFirstPlay:Z

.field private bLoop:Z

.field private bitsPerSample:I

.field private bufferID:J

.field private channel:I

.field private frameCnt:I

.field private pcmBuffer:[B

.field private pcmDataLength:I

.field private playbackHeadPosition:I

.field private samplingRate:I

.field private stateCode:I

.field tempWriteAudioBufferSizePerTick:I

.field private writeAudioBufferSizePerTick:I


# direct methods
.method public constructor <init>()V
    .registers 6

    const/4 v4, 0x0

    const/4 v3, 0x1

    const/4 v2, 0x0

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bufferID:J

    iput-object v4, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmBuffer:[B

    const/4 v0, 0x3

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bitsPerSample:I

    const/16 v0, 0x1f40

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->samplingRate:I

    const/4 v0, 0x2

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->channel:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->frameCnt:I

    iput-object v4, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    iput-boolean v3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bFirstPlay:Z

    iput-boolean v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bLoop:Z

    iput v3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenCnt:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->writeAudioBufferSizePerTick:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->tempWriteAudioBufferSizePerTick:I

    return-void
.end method

.method private declared-synchronized destroyAtomic()V
    .registers 2

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_17

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->flush()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->release()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->forceFinalize()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    :cond_17
    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmBuffer:[B

    const/16 v0, 0x40

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_1e
    .catchall {:try_start_1 .. :try_end_1e} :catchall_20

    monitor-exit p0

    return-void

    :catchall_20
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method private isPlayEnd()Z
    .registers 3

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    if-lt v0, v1, :cond_8

    const/4 v0, 0x1

    :goto_7
    return v0

    :cond_8
    const/4 v0, 0x0

    goto :goto_7
.end method

.method private declared-synchronized needDestroy()V
    .registers 3

    monitor-enter p0

    :try_start_1
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_3
    .catchall {:try_start_1 .. :try_end_3} :catchall_10

    const/4 v1, 0x1

    if-ne v0, v1, :cond_8

    :goto_6
    monitor-exit p0

    return-void

    :cond_8
    const/16 v0, 0x20

    :try_start_a
    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->interrupt()V
    :try_end_f
    .catchall {:try_start_a .. :try_end_f} :catchall_10

    goto :goto_6

    :catchall_10
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method private declared-synchronized pauseClipAtomic()I
    .registers 3

    monitor-enter p0

    :try_start_1
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_3
    .catchall {:try_start_1 .. :try_end_3} :catchall_f

    const/4 v1, 0x4

    if-eq v0, v1, :cond_9

    const/4 v0, -0x1

    :goto_7
    monitor-exit p0

    return v0

    :cond_9
    const/16 v0, 0x8

    :try_start_b
    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_d
    .catchall {:try_start_b .. :try_end_d} :catchall_f

    const/4 v0, 0x0

    goto :goto_7

    :catchall_f
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method private declared-synchronized playClipAtomic(Z)I
    .registers 5

    const/4 v2, 0x0

    const/4 v1, -0x1

    monitor-enter p0

    :try_start_3
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_5
    .catchall {:try_start_3 .. :try_end_5} :catchall_24

    sparse-switch v0, :sswitch_data_28

    move v0, v1

    :goto_9
    monitor-exit p0

    return v0

    :sswitch_b
    move v0, v1

    goto :goto_9

    :sswitch_d
    :try_start_d
    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->prepareAudioTrack()I

    const/4 v0, 0x4

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    iput-boolean p1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bLoop:Z

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->start()V

    move v0, v2

    goto :goto_9

    :sswitch_1a
    iput-boolean p1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bLoop:Z

    const/4 v0, 0x4

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->interrupt()V
    :try_end_22
    .catchall {:try_start_d .. :try_end_22} :catchall_24

    move v0, v2

    goto :goto_9

    :catchall_24
    move-exception v0

    monitor-exit p0

    throw v0

    nop

    :sswitch_data_28
    .sparse-switch
        0x1 -> :sswitch_d
        0x2 -> :sswitch_b
        0x4 -> :sswitch_b
        0x8 -> :sswitch_b
        0x10 -> :sswitch_1a
        0x20 -> :sswitch_b
        0x40 -> :sswitch_b
    .end sparse-switch
.end method

.method private declared-synchronized resumeClipAtomic()I
    .registers 3

    monitor-enter p0

    :try_start_1
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_3
    .catchall {:try_start_1 .. :try_end_3} :catchall_12

    const/16 v1, 0x8

    if-eq v0, v1, :cond_a

    const/4 v0, -0x1

    :goto_8
    monitor-exit p0

    return v0

    :cond_a
    const/4 v0, 0x4

    :try_start_b
    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->interrupt()V
    :try_end_10
    .catchall {:try_start_b .. :try_end_10} :catchall_12

    const/4 v0, 0x0

    goto :goto_8

    :catchall_12
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method private declared-synchronized stopClipAtomic()I
    .registers 4

    const/4 v2, 0x0

    monitor-enter p0

    :try_start_2
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I
    :try_end_4
    .catchall {:try_start_2 .. :try_end_4} :catchall_1a

    const/4 v1, 0x4

    if-eq v0, v1, :cond_a

    const/4 v0, -0x1

    :goto_8
    monitor-exit p0

    return v0

    :cond_a
    const/16 v0, 0x10

    :try_start_c
    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    const/4 v0, 0x0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenCnt:I

    const/4 v0, 0x0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->writeAudioBufferSizePerTick:I

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->tempWriteAudioBufferSizePerTick:I
    :try_end_18
    .catchall {:try_start_c .. :try_end_18} :catchall_1a

    move v0, v2

    goto :goto_8

    :catchall_1a
    move-exception v0

    monitor-exit p0

    throw v0
.end method


# virtual methods
.method public destroy()V
    .registers 1

    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->needDestroy()V

    return-void
.end method

.method public getNativeFrameCountByArg(III)I
    .registers 8

    const/4 v3, 0x1

    const/4 v2, 0x2

    if-ne p1, v2, :cond_d

    move v0, v3

    :goto_5
    const/4 v1, 0x3

    if-ne p2, v1, :cond_f

    move v1, v3

    :goto_9
    div-int v0, p3, v0

    div-int/2addr v0, v1

    return v0

    :cond_d
    move v0, v2

    goto :goto_5

    :cond_f
    move v1, v2

    goto :goto_9
.end method

.method public pauseClip()I
    .registers 2

    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pauseClipAtomic()I

    move-result v0

    return v0
.end method

.method public playClip(Z)I
    .registers 3

    invoke-direct {p0, p1}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->playClipAtomic(Z)I

    move-result v0

    return v0
.end method

.method public prepareAudioInfo(J[BIIII)I
    .registers 12

    const/4 v3, 0x3

    const/4 v2, -0x1

    const/4 v1, 0x2

    iput-wide p1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bufferID:J

    iput-object p3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmBuffer:[B

    iput p4, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    const/16 v0, 0x8

    if-ne p5, v0, :cond_24

    iput v3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bitsPerSample:I

    :goto_f
    const/4 v0, 0x1

    if-ne p6, v0, :cond_2d

    iput v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->channel:I

    :goto_14
    iput p7, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->samplingRate:I

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->channel:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bitsPerSample:I

    iget v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    invoke-virtual {p0, v0, v1, v2}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->getNativeFrameCountByArg(III)I

    move-result v0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->frameCnt:I

    const/4 v0, 0x0

    :goto_23
    return v0

    :cond_24
    const/16 v0, 0x10

    if-ne p5, v0, :cond_2b

    iput v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bitsPerSample:I

    goto :goto_f

    :cond_2b
    move v0, v2

    goto :goto_23

    :cond_2d
    if-ne p6, v1, :cond_32

    iput v3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->channel:I

    goto :goto_14

    :cond_32
    move v0, v2

    goto :goto_23
.end method

.method public prepareAudioTrack()I
    .registers 9

    const/4 v7, -0x1

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmBuffer:[B

    if-nez v0, :cond_7

    move v0, v7

    :goto_6
    return v0

    :cond_7
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    if-gtz v0, :cond_d

    move v0, v7

    goto :goto_6

    :cond_d
    :try_start_d
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_25

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->flush()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->release()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->forceFinalize()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    move v0, v7

    goto :goto_6

    :cond_25
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->samplingRate:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->channel:I

    iget v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bitsPerSample:I

    invoke-static {v0, v1, v2}, Landroid/media/AudioTrack;->getMinBufferSize(III)I

    move-result v0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->writeAudioBufferSizePerTick:I

    new-instance v0, Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    const/4 v1, 0x3

    iget v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->samplingRate:I

    iget v3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->channel:I

    iget v4, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bitsPerSample:I

    iget v5, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->writeAudioBufferSizePerTick:I

    shl-int/lit8 v5, v5, 0x2

    const/4 v6, 0x1

    invoke-direct/range {v0 .. v6}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;-><init>(IIIIII)V

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-nez v0, :cond_4a

    move v0, v7

    goto :goto_6

    :cond_4a
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->play()V
    :try_end_4f
    .catch Ljava/lang/Exception; {:try_start_d .. :try_end_4f} :catch_54

    const/4 v0, 0x2

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    const/4 v0, 0x0

    goto :goto_6

    :catch_54
    move-exception v0

    move v0, v7

    goto :goto_6
.end method

.method public resumeClip()I
    .registers 2

    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->resumeClipAtomic()I

    move-result v0

    return v0
.end method

.method public run()V
    .registers 9

    const/16 v7, 0x10

    const/16 v6, 0x8

    const/4 v5, 0x0

    const/16 v4, 0x20

    :cond_7
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    const/4 v1, 0x1

    if-eq v0, v1, :cond_16

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-eq v0, v4, :cond_16

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    const/16 v1, 0x40

    if-ne v0, v1, :cond_1a

    :cond_16
    :goto_16
    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->destroyAtomic()V

    return-void

    :cond_1a
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    const/4 v1, 0x2

    if-ne v0, v1, :cond_23

    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->needDestroy()V

    goto :goto_16

    :cond_23
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->writeAudioBufferSizePerTick:I

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->tempWriteAudioBufferSizePerTick:I

    iput v5, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenCnt:I

    iput v5, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    :goto_2b
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    if-ge v0, v1, :cond_53

    :cond_31
    :goto_31
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-eq v0, v6, :cond_39

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-ne v0, v7, :cond_4f

    :cond_39
    const-wide/32 v0, 0x5265c00

    :try_start_3c
    invoke-static {v0, v1}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->sleep(J)V
    :try_end_3f
    .catch Ljava/lang/InterruptedException; {:try_start_3c .. :try_end_3f} :catch_40

    goto :goto_31

    :catch_40
    move-exception v0

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->isInterrupted()Z

    move-result v0

    if-eqz v0, :cond_47

    :cond_47
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-eq v0, v6, :cond_31

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-eq v0, v7, :cond_31

    :cond_4f
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-ne v0, v4, :cond_62

    :cond_53
    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stateCode:I

    if-eq v0, v4, :cond_16

    iget-boolean v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->bLoop:Z

    if-nez v0, :cond_7

    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stopClipAtomic()I

    move-result v0

    if-eqz v0, :cond_7

    goto :goto_16

    :cond_62
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    iget-object v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmBuffer:[B

    iget v2, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iget v3, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->tempWriteAudioBufferSizePerTick:I

    invoke-virtual {v0, v1, v2, v3}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->write([BII)I

    move-result v0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenCnt:I

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenCnt:I

    add-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    sub-int/2addr v0, v1

    if-lez v0, :cond_8e

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    sub-int/2addr v0, v1

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->tempWriteAudioBufferSizePerTick:I

    if-ge v0, v1, :cond_8e

    iget v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->pcmDataLength:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->audioBufferWrittenSum:I

    sub-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->tempWriteAudioBufferSizePerTick:I

    :cond_8e
    invoke-static {}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->yield()V

    goto :goto_2b
.end method

.method public stopClip()I
    .registers 2

    invoke-direct {p0}, Lco/kr/eamobile/audio/clip/StreamPcmMediaClip;->stopClipAtomic()I

    move-result v0

    return v0
.end method
