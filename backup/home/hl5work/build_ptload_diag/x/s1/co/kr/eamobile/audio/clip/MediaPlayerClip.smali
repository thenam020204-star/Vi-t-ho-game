.class public Lco/kr/eamobile/audio/clip/MediaPlayerClip;
.super Ljava/lang/Object;

# interfaces
.implements Lco/kr/eamobile/audio/clip/IBufferMediaClip;


# instance fields
.field private final PLAYER_ERROR:I

.field private final PLAYER_PAUSED:I

.field private final PLAYER_PLAYING:I

.field private final PLAYER_PREPARED:I

.field private final PLAYER_STOPPED:I

.field private final PLAYER_UNINITIALIZED:I

.field private bLoop:Z

.field private mediaPlayer:Landroid/media/MediaPlayer;

.field private srcName:Ljava/lang/String;

.field private state:I


# direct methods
.method public constructor <init>(Ljava/lang/String;I)V
    .registers 5

    const/4 v1, 0x0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->mediaPlayer:Landroid/media/MediaPlayer;

    iput v1, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->PLAYER_UNINITIALIZED:I

    const/4 v0, 0x1

    iput v0, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->PLAYER_PREPARED:I

    const/4 v0, 0x2

    iput v0, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->PLAYER_STOPPED:I

    const/4 v0, 0x3

    iput v0, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->PLAYER_PAUSED:I

    const/4 v0, 0x4

    iput v0, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->PLAYER_PLAYING:I

    const/4 v0, 0x5

    iput v0, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->PLAYER_ERROR:I

    iput v1, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->state:I

    iput-object p1, p0, Lco/kr/eamobile/audio/clip/MediaPlayerClip;->srcName:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public destroy()V
    .registers 1

    return-void
.end method

.method public pauseClip()I
    .registers 2

    const/4 v0, 0x0

    return v0
.end method

.method public playClip(Z)I
    .registers 3

    const/4 v0, 0x0

    return v0
.end method

.method public resumeClip()I
    .registers 2

    const/4 v0, 0x0

    return v0
.end method

.method public stopClip()I
    .registers 2

    const/4 v0, 0x0

    return v0
.end method
