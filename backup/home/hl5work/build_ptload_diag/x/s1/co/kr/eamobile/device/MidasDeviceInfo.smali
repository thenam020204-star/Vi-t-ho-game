.class public Lco/kr/eamobile/device/MidasDeviceInfo;
.super Ljava/lang/Object;


# static fields
.field private static final TAG:Ljava/lang/String; = "DeviceInfo"

.field private static instance:Lco/kr/eamobile/device/MidasDeviceInfo;


# instance fields
.field private bSupportFBO:I

.field private imei:Ljava/lang/String;

.field private imsi:Ljava/lang/String;

.field private manufacturer:Ljava/lang/String;

.field private min:Ljava/lang/String;

.field private model:Ljava/lang/String;

.field private openglesExt:Ljava/lang/String;

.field private openglesMaxVersion:Ljava/lang/String;

.field private osversion:I

.field private telephonyManager:Landroid/telephony/TelephonyManager;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .registers 7

    const/4 v3, 0x0

    const-string v4, ""

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "phone"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    iput-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->telephonyManager:Landroid/telephony/TelephonyManager;

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    iput v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->osversion:I

    iput-object v3, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->openglesMaxVersion:Ljava/lang/String;

    sget-object v0, Landroid/os/Build;->MANUFACTURER:Ljava/lang/String;

    iput-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->manufacturer:Ljava/lang/String;

    sget-object v0, Landroid/os/Build;->MODEL:Ljava/lang/String;

    const-string v1, " "

    const-string v2, ""

    invoke-virtual {v0, v1, v4}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->model:Ljava/lang/String;

    iput-object v3, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->imei:Ljava/lang/String;

    iput-object v3, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->imsi:Ljava/lang/String;

    iget-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->telephonyManager:Landroid/telephony/TelephonyManager;

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getLine1Number()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_3a

    const-string v1, ""

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3c

    :cond_3a
    const-string v0, "00000000000"

    :cond_3c
    const-string v1, "\\-"

    const-string v2, ""

    invoke-virtual {v0, v1, v4}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "\\+82"

    const-string v2, "0"

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->min:Ljava/lang/String;

    return-void
.end method

.method public static createDeviceInfo(Landroid/content/Context;)V
    .registers 2

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/device/MidasDeviceInfo;

    invoke-direct {v0, p0}, Lco/kr/eamobile/device/MidasDeviceInfo;-><init>(Landroid/content/Context;)V

    sput-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    :cond_b
    return-void
.end method

.method private destory()V
    .registers 2

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->telephonyManager:Landroid/telephony/TelephonyManager;

    return-void
.end method

.method public static destroyDeviceInfo()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    invoke-direct {v0}, Lco/kr/eamobile/device/MidasDeviceInfo;->destory()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    return-void
.end method

.method public static getIMEI()Ljava/lang/String;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->imei:Ljava/lang/String;

    return-object v0
.end method

.method public static getIMSI()Ljava/lang/String;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->imsi:Ljava/lang/String;

    return-object v0
.end method

.method public static getInstance()Lco/kr/eamobile/device/MidasDeviceInfo;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    return-object v0
.end method

.method public static getManufacturer()Ljava/lang/String;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->manufacturer:Ljava/lang/String;

    return-object v0
.end method

.method public static getMin()Ljava/lang/String;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->min:Ljava/lang/String;

    return-object v0
.end method

.method public static getModel()Ljava/lang/String;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->model:Ljava/lang/String;

    return-object v0
.end method

.method public static getOSVersion()I
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->osversion:I

    return v0
.end method

.method public static getOpenGLESVersion()Ljava/lang/String;
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget-object v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->openglesMaxVersion:Ljava/lang/String;

    return-object v0
.end method

.method public static isFBOSupported()I
    .registers 1

    sget-object v0, Lco/kr/eamobile/device/MidasDeviceInfo;->instance:Lco/kr/eamobile/device/MidasDeviceInfo;

    iget v0, v0, Lco/kr/eamobile/device/MidasDeviceInfo;->bSupportFBO:I

    return v0
.end method


# virtual methods
.method public setOpenGLESExt(Ljava/lang/String;)V
    .registers 5

    const/4 v2, 0x0

    iput-object p1, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->openglesExt:Ljava/lang/String;

    iget-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->openglesExt:Ljava/lang/String;

    if-eqz v0, :cond_18

    iget-object v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->openglesExt:Ljava/lang/String;

    const-string v1, "GL_OES_framebuffer_object"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_15

    const/4 v0, 0x1

    iput v0, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->bSupportFBO:I

    :goto_14
    return-void

    :cond_15
    iput v2, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->bSupportFBO:I

    goto :goto_14

    :cond_18
    iput v2, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->bSupportFBO:I

    goto :goto_14
.end method

.method public setOpenGLESMaxSupportedVersion(Ljava/lang/String;)V
    .registers 2

    iput-object p1, p0, Lco/kr/eamobile/device/MidasDeviceInfo;->openglesMaxVersion:Ljava/lang/String;

    return-void
.end method
