.class public Lco/kr/eamobile/network/MidasNetworkManager;
.super Ljava/lang/Object;


# static fields
.field private static final AIRPLANE_MODE_OFF:I = 0x0

.field private static final AIRPLANE_MODE_ON:I = 0x1

.field private static final NETWORK_AVAILABLE_3G:I = 0x2

.field private static final NETWORK_AVAILABLE_WIFI:I = 0x1

.field private static final NETWORK_UNAVAILABLE:I = 0x0

.field private static final ROAMING_MODE_OFF:I = 0x0

.field private static final ROAMING_MODE_ON:I = 0x0

.field private static final TAG:Ljava/lang/String; = "NetworkManager"

.field private static instance:Lco/kr/eamobile/network/MidasNetworkManager;


# instance fields
.field private conContext:Landroid/content/Context;

.field private conManager:Landroid/net/ConnectivityManager;

.field private socketManagerPool:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/Integer;",
            "Lco/kr/eamobile/network/SocketManager;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lco/kr/eamobile/network/MidasNetworkManager;->conContext:Landroid/content/Context;

    const-string v0, "connectivity"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    iput-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->conManager:Landroid/net/ConnectivityManager;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->socketManagerPool:Ljava/util/HashMap;

    return-void
.end method

.method public static createNetworkManager(Landroid/content/Context;)V
    .registers 2

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/network/MidasNetworkManager;

    invoke-direct {v0, p0}, Lco/kr/eamobile/network/MidasNetworkManager;-><init>(Landroid/content/Context;)V

    sput-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    :cond_b
    return-void
.end method

.method private destroy()V
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->socketManagerPool:Ljava/util/HashMap;

    if-eqz v0, :cond_c

    iget-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->socketManagerPool:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->clear()V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->socketManagerPool:Ljava/util/HashMap;

    :cond_c
    return-void
.end method

.method public static destroyNetworkManager()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    invoke-direct {v0}, Lco/kr/eamobile/network/MidasNetworkManager;->destroy()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    return-void
.end method

.method public static getInstance()Lco/kr/eamobile/network/MidasNetworkManager;
    .registers 1

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    return-object v0
.end method

.method private is3GAvailable()Z
    .registers 5

    const/4 v3, 0x0

    const-string v2, "NetworkManager"

    iget-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->conManager:Landroid/net/ConnectivityManager;

    invoke-virtual {v0, v3}, Landroid/net/ConnectivityManager;->getNetworkInfo(I)Landroid/net/NetworkInfo;

    move-result-object v0

    if-nez v0, :cond_d

    move v0, v3

    :goto_c
    return v0

    :cond_d
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_35

    const-string v1, "NetworkManager"

    const-string v1, "### 3G is available. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1a
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v1

    if-eqz v1, :cond_3d

    const-string v1, "NetworkManager"

    const-string v1, "### 3G is connected. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_27
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_45

    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_45

    const/4 v0, 0x1

    goto :goto_c

    :cond_35
    const-string v1, "NetworkManager"

    const-string v1, "### 3G is not available. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1a

    :cond_3d
    const-string v1, "NetworkManager"

    const-string v1, "### 3G is not connected. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_27

    :cond_45
    move v0, v3

    goto :goto_c
.end method

.method public static isAirplaneModeOn()I
    .registers 3

    const/4 v2, 0x0

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    iget-object v0, v0, Lco/kr/eamobile/network/MidasNetworkManager;->conContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "airplane_mode_on"

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    if-eqz v0, :cond_13

    const/4 v0, 0x1

    :goto_12
    return v0

    :cond_13
    move v0, v2

    goto :goto_12
.end method

.method public static isNetworkAvailable()I
    .registers 3

    const/4 v2, 0x0

    const-string v0, "NetworkManager"

    const-string v1, "isNetworkAvailable()"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    iget-object v0, v0, Lco/kr/eamobile/network/MidasNetworkManager;->conManager:Landroid/net/ConnectivityManager;

    if-nez v0, :cond_10

    move v0, v2

    :goto_f
    return v0

    :cond_10
    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    invoke-direct {v0}, Lco/kr/eamobile/network/MidasNetworkManager;->isWIFIAvailable()Z

    move-result v0

    if-eqz v0, :cond_1a

    const/4 v0, 0x1

    goto :goto_f

    :cond_1a
    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    invoke-direct {v0}, Lco/kr/eamobile/network/MidasNetworkManager;->is3GAvailable()Z

    move-result v0

    if-eqz v0, :cond_24

    const/4 v0, 0x2

    goto :goto_f

    :cond_24
    move v0, v2

    goto :goto_f
.end method

.method public static isNetworkRoaming()I
    .registers 3

    const/4 v2, 0x0

    sget-object v0, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    iget-object v0, v0, Lco/kr/eamobile/network/MidasNetworkManager;->conContext:Landroid/content/Context;

    sget-object v1, Lco/kr/eamobile/network/MidasNetworkManager;->instance:Lco/kr/eamobile/network/MidasNetworkManager;

    iget-object v1, v1, Lco/kr/eamobile/network/MidasNetworkManager;->conContext:Landroid/content/Context;

    const-string v1, "phone"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->isNetworkRoaming()Z

    move-result v0

    if-eqz v0, :cond_19

    move v0, v2

    :goto_18
    return v0

    :cond_19
    move v0, v2

    goto :goto_18
.end method

.method private isWIFIAvailable()Z
    .registers 6

    const/4 v4, 0x1

    const/4 v3, 0x0

    const-string v2, "NetworkManager"

    iget-object v0, p0, Lco/kr/eamobile/network/MidasNetworkManager;->conManager:Landroid/net/ConnectivityManager;

    invoke-virtual {v0, v4}, Landroid/net/ConnectivityManager;->getNetworkInfo(I)Landroid/net/NetworkInfo;

    move-result-object v0

    if-nez v0, :cond_e

    move v0, v3

    :goto_d
    return v0

    :cond_e
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_36

    const-string v1, "NetworkManager"

    const-string v1, "### WIFI is available. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_1b
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v1

    if-eqz v1, :cond_3e

    const-string v1, "NetworkManager"

    const-string v1, "### WIFI is connected. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_28
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_46

    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_46

    move v0, v4

    goto :goto_d

    :cond_36
    const-string v1, "NetworkManager"

    const-string v1, "### WIFI is not available. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1b

    :cond_3e
    const-string v1, "NetworkManager"

    const-string v1, "### WIFI is not connected. ###"

    invoke-static {v2, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_28

    :cond_46
    move v0, v3

    goto :goto_d
.end method


# virtual methods
.method public testSocket()V
    .registers 4

    const-wide/16 v0, 0x1388

    :try_start_2
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_5
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_5} :catch_1b

    :goto_5
    new-instance v0, Lco/kr/eamobile/network/SocketManager;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Lco/kr/eamobile/network/SocketManager;-><init>(I)V

    const-string v1, "10.88.146.144"

    const/16 v2, 0x270f

    invoke-virtual {v0, v1, v2}, Lco/kr/eamobile/network/SocketManager;->connect(Ljava/lang/String;I)V

    const-wide/16 v1, 0x1388

    :try_start_14
    invoke-static {v1, v2}, Ljava/lang/Thread;->sleep(J)V
    :try_end_17
    .catch Ljava/lang/InterruptedException; {:try_start_14 .. :try_end_17} :catch_20

    :goto_17
    invoke-virtual {v0}, Lco/kr/eamobile/network/SocketManager;->close()V

    return-void

    :catch_1b
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_5

    :catch_20
    move-exception v1

    invoke-virtual {v1}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_17
.end method
