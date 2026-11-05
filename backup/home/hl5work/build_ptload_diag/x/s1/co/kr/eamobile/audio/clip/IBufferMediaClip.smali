.class public interface abstract Lco/kr/eamobile/audio/clip/IBufferMediaClip;
.super Ljava/lang/Object;


# static fields
.field public static final AUDIO_ENCODING_TYPE_MMF:I = 0x2

.field public static final AUDIO_ENCODING_TYPE_OGG:I = 0x1

.field public static final AUDIO_ENCODING_TYPE_UNKNOWN:I = -0x1

.field public static final AUDIO_ENCODING_TYPE_WAVE:I = 0x0

.field public static final AUDIO_TYPE_MIDI:I = 0x1

.field public static final AUDIO_TYPE_PCM:I = 0x0

.field public static final AUDIO_TYPE_UNKNOWN:I = -0x1

.field public static final HANDLING_MEDIAPLAYER:I = 0x3

.field public static final HANDLING_MIDI:I = 0x2

.field public static final HANDLING_NONE:I = -0x1

.field public static final HANDLING_PCM_STATIC:I = 0x0

.field public static final HANDLING_PCM_STREAM:I = 0x1

.field public static final HANDLING_SOUNDPOOL:I = 0x4


# virtual methods
.method public abstract destroy()V
.end method

.method public abstract pauseClip()I
.end method

.method public abstract playClip(Z)I
.end method

.method public abstract resumeClip()I
.end method

.method public abstract stopClip()I
.end method
