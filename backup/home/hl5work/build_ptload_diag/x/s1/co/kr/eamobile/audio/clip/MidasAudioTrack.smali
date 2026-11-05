.class public Lco/kr/eamobile/audio/clip/MidasAudioTrack;
.super Landroid/media/AudioTrack;


# direct methods
.method public constructor <init>(IIIIII)V
    .registers 7
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/IllegalArgumentException;
        }
    .end annotation

    invoke-direct/range {p0 .. p6}, Landroid/media/AudioTrack;-><init>(IIIIII)V

    return-void
.end method


# virtual methods
.method public forceFinalize()V
    .registers 1

    invoke-super {p0}, Landroid/media/AudioTrack;->finalize()V

    return-void
.end method
