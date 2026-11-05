.class public Lco/kr/eamobile/constant/WIPIConstant;
.super Ljava/lang/Object;


# static fields
.field private static final MC_KEY_CLEAR:I = -0x10

.field private static final MC_KEY_DOWN:I = -0x2

.field private static final MC_KEY_INVALID:I = 0x0

.field private static final MC_KEY_LEFT:I = -0x3

.field private static final MC_KEY_RIGHT:I = -0x4

.field private static final MC_KEY_SELECT:I = -0x5

.field private static final MC_KEY_UP:I = -0x1

.field public static final MEDIA_NOT_EXIST:I = -0x1

.field public static final MEDIA_PAUSED:I = 0x1

.field public static final MEDIA_PLAYING:I = 0x2

.field public static final MEDIA_STOPPED:I = 0x0

.field public static final MV_KEY_PRESS_EVENT:I = 0x2

.field public static final MV_KEY_RELEASE_EVENT:I = 0x3

.field public static final MV_POINTER_MOVE_EVENT:I = 0x15

.field public static final MV_POINTER_PRESS_EVENT:I = 0x13

.field public static final MV_POINTER_RELEASE_EVENT:I = 0x14

.field public static final M_E_ERROR:I = -0x1

.field public static final M_E_SUCCESS:I = 0x0

.field public static final WIPI_MAX_VOLUME:F = 100.0f


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static final getWIPIKeyCode(I)I
    .registers 2

    sparse-switch p0, :sswitch_data_12

    const/4 v0, 0x0

    :goto_4
    return v0

    :sswitch_5
    const/4 v0, -0x1

    goto :goto_4

    :sswitch_7
    const/4 v0, -0x2

    goto :goto_4

    :sswitch_9
    const/4 v0, -0x3

    goto :goto_4

    :sswitch_b
    const/4 v0, -0x4

    goto :goto_4

    :sswitch_d
    const/4 v0, -0x5

    goto :goto_4

    :sswitch_f
    const/16 v0, -0x10

    goto :goto_4

    :sswitch_data_12
    .sparse-switch
        0x4 -> :sswitch_f
        0x13 -> :sswitch_5
        0x14 -> :sswitch_7
        0x15 -> :sswitch_9
        0x16 -> :sswitch_b
        0x17 -> :sswitch_d
    .end sparse-switch
.end method

.method public static final getWIPIMediaStatus(II)I
    .registers 5

    const/4 v2, 0x2

    const/4 v1, -0x1

    const/4 v0, 0x1

    if-ne p0, v0, :cond_12

    if-ne p1, v0, :cond_9

    const/4 v0, 0x0

    :cond_8
    :goto_8
    return v0

    :cond_9
    if-eq p1, v2, :cond_8

    const/4 v0, 0x3

    if-ne p1, v0, :cond_10

    move v0, v2

    goto :goto_8

    :cond_10
    move v0, v1

    goto :goto_8

    :cond_12
    move v0, v1

    goto :goto_8
.end method
