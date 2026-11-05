.class public Lco/kr/eamobile/device/MidasVibrator;
.super Ljava/lang/Object;


# static fields
.field private static final TAG:Ljava/lang/String; = "VibrationManager"

.field private static instance:Lco/kr/eamobile/device/MidasVibrator;


# instance fields
.field private vibrator:Landroid/os/Vibrator;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "vibrator"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/Vibrator;

    iput-object v0, p0, Lco/kr/eamobile/device/MidasVibrator;->vibrator:Landroid/os/Vibrator;

    return-void
.end method

.method public static createVibrator(Landroid/content/Context;)V
    .registers 2

    sget-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/device/MidasVibrator;

    invoke-direct {v0, p0}, Lco/kr/eamobile/device/MidasVibrator;-><init>(Landroid/content/Context;)V

    sput-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    :cond_b
    return-void
.end method

.method private destory()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/device/MidasVibrator;->vibrator:Landroid/os/Vibrator;

    invoke-virtual {v0}, Landroid/os/Vibrator;->cancel()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/device/MidasVibrator;->vibrator:Landroid/os/Vibrator;

    return-void
.end method

.method public static destroyVibrator()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    invoke-direct {v0}, Lco/kr/eamobile/device/MidasVibrator;->destory()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    return-void
.end method

.method public static getInstance()Lco/kr/eamobile/device/MidasVibrator;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    return-object v0
.end method

.method public static vibration(II)I
    .registers 6

    const/4 v1, -0x1

    if-nez p0, :cond_c

    sget-object v0, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasVibrator;->vibrator:Landroid/os/Vibrator;

    invoke-virtual {v0}, Landroid/os/Vibrator;->cancel()V

    :goto_a
    const/4 v0, 0x0

    :goto_b
    return v0

    :cond_c
    const/4 v0, 0x1

    if-lt p0, v0, :cond_25

    const/16 v0, 0x64

    if-gt p0, v0, :cond_25

    if-gez p1, :cond_17

    move v0, v1

    goto :goto_b

    :cond_17
    if-nez p1, :cond_27

    const v0, 0xea60

    :goto_1c
    sget-object v1, Lco/kr/eamobile/device/MidasVibrator;->instance:Lco/kr/eamobile/device/MidasVibrator;

    iget-object v1, v1, Lco/kr/eamobile/device/MidasVibrator;->vibrator:Landroid/os/Vibrator;

    int-to-long v2, v0

    invoke-virtual {v1, v2, v3}, Landroid/os/Vibrator;->vibrate(J)V

    goto :goto_a

    :cond_25
    move v0, v1

    goto :goto_b

    :cond_27
    move v0, p1

    goto :goto_1c
.end method
