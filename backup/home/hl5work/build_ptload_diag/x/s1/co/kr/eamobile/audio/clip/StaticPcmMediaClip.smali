.class public Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;
.super Ljava/lang/Object;

# interfaces
.implements Lco/kr/eamobile/audio/clip/IBufferMediaClip;


# static fields
.field private static final TAG:Ljava/lang/String; = "StaticPcmMediaClip"


# instance fields
.field private at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

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


# direct methods
.method public constructor <init>()V
    .registers 5

    const/4 v3, 0x0

    const/4 v2, 0x0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bufferID:J

    iput-object v3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmBuffer:[B

    const/4 v0, 0x3

    iput v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bitsPerSample:I

    const/16 v0, 0x1f40

    iput v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->samplingRate:I

    const/4 v0, 0x2

    iput v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->channel:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    iput v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->frameCnt:I

    iput-object v3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bFirstPlay:Z

    iput-boolean v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bLoop:Z

    return-void
.end method


# virtual methods
.method public destroy()V
    .registers 4

    const/4 v2, 0x0

    const/4 v1, 0x1

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_2c

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getState()I

    move-result v0

    if-ne v0, v1, :cond_1b

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getPlayState()I

    move-result v0

    if-eq v0, v1, :cond_1b

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->stop()V

    :cond_1b
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->flush()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->release()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->forceFinalize()V

    iput-object v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    :cond_2c
    iput-object v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmBuffer:[B

    return-void
.end method

.method public getInstanceStat()I
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_b

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getState()I

    move-result v0

    :goto_a
    return v0

    :cond_b
    const/4 v0, 0x0

    goto :goto_a
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

.method public getPlayBackStat()I
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_b

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getPlayState()I

    move-result v0

    :goto_a
    return v0

    :cond_b
    const/4 v0, 0x1

    goto :goto_a
.end method

.method public pauseClip()I
    .registers 4

    const/4 v2, -0x1

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-nez v0, :cond_7

    move v0, v2

    :goto_6
    return v0

    :cond_7
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getPlayState()I

    move-result v0

    const/4 v1, 0x3

    if-eq v0, v1, :cond_12

    move v0, v2

    goto :goto_6

    :cond_12
    :try_start_12
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->pause()V

    iget-boolean v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bLoop:Z

    if-nez v0, :cond_23

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getPlaybackHeadPosition()I

    move-result v0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->playbackHeadPosition:I
    :try_end_23
    .catch Ljava/lang/IllegalStateException; {:try_start_12 .. :try_end_23} :catch_25

    :cond_23
    const/4 v0, 0x0

    goto :goto_6

    :catch_25
    move-exception v0

    const-string v0, "StaticPcmMediaClip"

    const-string v1, "(Error) at WavePcmMediaClip->pause()"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    move v0, v2

    goto :goto_6
.end method

.method public playClip(Z)I
    .registers 9

    const/4 v3, 0x1

    const/4 v5, 0x0

    const/4 v4, -0x1

    const-string v6, "StaticPcmMediaClip"

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-nez v0, :cond_10

    invoke-virtual {p0}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->prepareAudioTrack()I

    move-result v0

    if-ne v0, v4, :cond_10

    :goto_f
    return v0

    :cond_10
    :try_start_10
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getPlayState()I

    move-result v0

    if-ne v0, v3, :cond_3b

    iput-boolean p1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bLoop:Z

    iget-boolean v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bLoop:Z

    if-eqz v0, :cond_44

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    const/4 v1, 0x0

    iget v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->frameCnt:I

    sub-int/2addr v2, v3

    const/4 v3, -0x1

    invoke-virtual {v0, v1, v2, v3}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->setLoopPoints(III)I

    move-result v0

    if-eqz v0, :cond_44

    new-instance v0, Ljava/lang/IllegalStateException;

    invoke-direct {v0}, Ljava/lang/IllegalStateException;-><init>()V

    throw v0
    :try_end_31
    .catch Ljava/lang/IllegalStateException; {:try_start_10 .. :try_end_31} :catch_31

    :catch_31
    move-exception v0

    const-string v0, "StaticPcmMediaClip"

    const-string v0, "(Error) at WavePcmMediaClip->play()"

    invoke-static {v6, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    move v0, v4

    goto :goto_f

    :cond_3b
    :try_start_3b
    const-string v0, "StaticPcmMediaClip"

    const-string v1, "(ERROR) AudioTrack\'s playback state is not STOPPED!!"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    move v0, v4

    goto :goto_f

    :cond_44
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->play()V
    :try_end_49
    .catch Ljava/lang/IllegalStateException; {:try_start_3b .. :try_end_49} :catch_31

    move v0, v5

    goto :goto_f
.end method

.method public prepareAudioInfo(J[BIIII)I
    .registers 12

    const/4 v3, 0x3

    const/4 v2, -0x1

    const/4 v1, 0x2

    iput-wide p1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bufferID:J

    iput-object p3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmBuffer:[B

    iput p4, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    const/16 v0, 0x8

    if-ne p5, v0, :cond_24

    iput v3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bitsPerSample:I

    :goto_f
    const/4 v0, 0x1

    if-ne p6, v0, :cond_2d

    iput v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->channel:I

    :goto_14
    iput p7, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->samplingRate:I

    iget v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->channel:I

    iget v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bitsPerSample:I

    iget v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    invoke-virtual {p0, v0, v1, v2}, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->getNativeFrameCountByArg(III)I

    move-result v0

    iput v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->frameCnt:I

    const/4 v0, 0x0

    :goto_23
    return v0

    :cond_24
    const/16 v0, 0x10

    if-ne p5, v0, :cond_2b

    iput v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bitsPerSample:I

    goto :goto_f

    :cond_2b
    move v0, v2

    goto :goto_23

    :cond_2d
    if-ne p6, v1, :cond_32

    iput v3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->channel:I

    goto :goto_14

    :cond_32
    move v0, v2

    goto :goto_23
.end method

.method public prepareAudioTrack()I
    .registers 10

    const/4 v8, 0x0

    const/4 v7, -0x1

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmBuffer:[B

    if-nez v0, :cond_8

    move v0, v7

    :goto_7
    return v0

    :cond_8
    iget v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    if-gtz v0, :cond_e

    move v0, v7

    goto :goto_7

    :cond_e
    :try_start_e
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_26

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->flush()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->release()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->forceFinalize()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    move v0, v7

    goto :goto_7

    :cond_26
    new-instance v0, Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    const/4 v1, 0x3

    iget v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->samplingRate:I

    iget v3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->channel:I

    iget v4, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bitsPerSample:I

    iget v5, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    const/4 v6, 0x0

    invoke-direct/range {v0 .. v6}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;-><init>(IIIIII)V

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-nez v0, :cond_3d

    move v0, v7

    goto :goto_7

    :cond_3d
    move v0, v8

    :goto_3e
    iget v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    if-ge v0, v1, :cond_52

    iget-object v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    iget-object v2, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmBuffer:[B

    iget v3, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->pcmDataLength:I

    sub-int/2addr v3, v0

    invoke-virtual {v1, v2, v0, v3}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->write([BII)I
    :try_end_4c
    .catch Ljava/lang/Exception; {:try_start_e .. :try_end_4c} :catch_4f

    move-result v1

    add-int/2addr v0, v1

    goto :goto_3e

    :catch_4f
    move-exception v0

    move v0, v7

    goto :goto_7

    :cond_52
    move v0, v8

    goto :goto_7
.end method

.method public resumeClip()I
    .registers 4

    const/4 v2, -0x1

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-nez v0, :cond_7

    move v0, v2

    :goto_6
    return v0

    :cond_7
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->getPlayState()I

    move-result v0

    const/4 v1, 0x2

    if-eq v0, v1, :cond_12

    move v0, v2

    goto :goto_6

    :cond_12
    :try_start_12
    iget-boolean v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bLoop:Z

    if-nez v0, :cond_22

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->reloadStaticData()I

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    iget v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->playbackHeadPosition:I

    invoke-virtual {v0, v1}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->setPlaybackHeadPosition(I)I

    :cond_22
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->play()V
    :try_end_27
    .catch Ljava/lang/IllegalStateException; {:try_start_12 .. :try_end_27} :catch_29

    const/4 v0, 0x0

    goto :goto_6

    :catch_29
    move-exception v0

    const-string v0, "StaticPcmMediaClip"

    const-string v1, "(Error) at WavePcmMediaClip->resume()"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    move v0, v2

    goto :goto_6
.end method

.method public stopClip()I
    .registers 3

    const/4 v1, 0x0

    iput-boolean v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bLoop:Z

    iput v1, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->playbackHeadPosition:I

    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->bFirstPlay:Z

    :try_start_8
    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    if-eqz v0, :cond_23

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->stop()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->flush()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->release()V

    iget-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;

    invoke-virtual {v0}, Lco/kr/eamobile/audio/clip/MidasAudioTrack;->forceFinalize()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/StaticPcmMediaClip;->at:Lco/kr/eamobile/audio/clip/MidasAudioTrack;
    :try_end_23
    .catch Ljava/lang/IllegalStateException; {:try_start_8 .. :try_end_23} :catch_25

    :cond_23
    move v0, v1

    :goto_24
    return v0

    :catch_25
    move-exception v0

    const-string v0, "StaticPcmMediaClip"

    const-string v1, "(Error) at WavePcmMediaClip->stop()"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, -0x1

    goto :goto_24
.end method
