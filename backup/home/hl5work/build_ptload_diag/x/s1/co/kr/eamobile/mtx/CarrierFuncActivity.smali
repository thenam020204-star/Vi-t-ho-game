.class public Lco/kr/eamobile/mtx/CarrierFuncActivity;
.super Landroid/app/Activity;

# interfaces
.implements Lco/kr/eamobile/mtx/MTXFunctionable;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "CarrierFuncActivity"

.field private static cachedPID:Ljava/lang/String;


# instance fields
.field private final CHARGE_ALL_VER2:Ljava/lang/String;

.field private final CHARGE_BUYABLE_VER2:Ljava/lang/String;

.field private applicationID:Ljava/lang/String;

.field private billsocket:Lcom/lguplus/common/bill/IBillSocket;

.field private cachedContentID:Ljava/lang/String;

.field private cachedGameID:Ljava/lang/String;

.field private cachedServerIP:Ljava/lang/String;

.field private cachedServerPort:I

.field private chk_lgtstaff:Ljava/lang/String;

.field public handler:Landroid/os/Handler;

.field isSockConnect:Z

.field mIsInAppPurchaseConnect:Z

.field private minN:Ljava/lang/String;

.field private modelName:Ljava/lang/String;

.field private recieveData:[B

.field private serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;


# direct methods
.method public constructor <init>()V
    .registers 3

    const/4 v1, 0x0

    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    const-string v0, "CHARGE_BUYABLE_VER2"

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->CHARGE_BUYABLE_VER2:Ljava/lang/String;

    const-string v0, "CHARGE_ALL_VER2"

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->CHARGE_ALL_VER2:Ljava/lang/String;

    const/16 v0, 0x400

    new-array v0, v0, [B

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->recieveData:[B

    iput-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->applicationID:Ljava/lang/String;

    iput-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->minN:Ljava/lang/String;

    iput-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->modelName:Ljava/lang/String;

    iput-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedGameID:Ljava/lang/String;

    iput-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedContentID:Ljava/lang/String;

    sput-object v1, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedPID:Ljava/lang/String;

    const-string v0, "Y"

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->chk_lgtstaff:Ljava/lang/String;

    iput-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    const/4 v0, 0x0

    iput-boolean v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->isSockConnect:Z

    const-string v0, "CarrierFuncActivity"

    const-string v1, "CarrierFuncActivity()"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic access$000(Lco/kr/eamobile/mtx/CarrierFuncActivity;)Lcom/lguplus/common/bill/IBillSocket;
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    return-object v0
.end method

.method static synthetic access$002(Lco/kr/eamobile/mtx/CarrierFuncActivity;Lcom/lguplus/common/bill/IBillSocket;)Lcom/lguplus/common/bill/IBillSocket;
    .registers 2

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    return-object p1
.end method

.method static synthetic access$100(Lco/kr/eamobile/mtx/CarrierFuncActivity;)Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedGameID:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Lco/kr/eamobile/mtx/CarrierFuncActivity;)Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedServerIP:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$300(Lco/kr/eamobile/mtx/CarrierFuncActivity;)I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedServerPort:I

    return v0
.end method

.method static synthetic access$400(Lco/kr/eamobile/mtx/CarrierFuncActivity;)V
    .registers 1

    invoke-direct {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->releaseService()V

    return-void
.end method

.method static synthetic access$500(Lco/kr/eamobile/mtx/CarrierFuncActivity;)V
    .registers 1

    invoke-direct {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->gotoError()V

    return-void
.end method

.method private gotoError()V
    .registers 4

    const/4 v2, 0x0

    invoke-direct {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->releaseService()V

    const/4 v0, -0x1

    const/16 v1, -0x12

    invoke-virtual {p0, v0, v1, v2, v2}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->resultMTX(II[BLjava/lang/String;)V

    return-void
.end method

.method private native nativeResultMTX(Ljava/lang/String;II)V
.end method

.method private releaseService()V
    .registers 4

    const-string v2, "CarrierFuncActivity"

    :try_start_2
    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    if-eqz v0, :cond_18

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    invoke-virtual {p0, v0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->unbindService(Landroid/content/ServiceConnection;)V

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    const-string v0, "CarrierFuncActivity"

    const-string v1, "# Service is released successfully. #"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_18
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_18} :catch_19

    :cond_18
    :goto_18
    return-void

    :catch_19
    move-exception v0

    const-string v1, "CarrierFuncActivity"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_18
.end method


# virtual methods
.method public getChargeAllPacket()[B
    .registers 4

    const-string v2, " "

    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    const-string v1, "CHARGE_ALL_VER2"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->minN:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedGameID:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedContentID:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->modelName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->chk_lgtstaff:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->getBytes()[B

    move-result-object v0

    return-object v0
.end method

.method public getChargeBuyablePacket()[B
    .registers 4

    const-string v2, " "

    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    const-string v1, "CHARGE_BUYABLE_VER2"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->minN:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedGameID:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedContentID:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v1, " "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->chk_lgtstaff:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->getBytes()[B

    move-result-object v0

    return-object v0
.end method

.method public initMTX(Ljava/lang/String;I)I
    .registers 7

    const/4 v2, 0x0

    const-string v3, "CarrierFuncActivity"

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedServerIP:Ljava/lang/String;

    iput p2, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedServerPort:I

    const-string v0, "CarrierFuncActivity"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "MTX Server IP : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const-string v0, "CarrierFuncActivity"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "MTX Server Port : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedServerIP:Ljava/lang/String;

    if-nez v0, :cond_3d

    const/4 v0, -0x1

    :goto_3c
    return v0

    :cond_3d
    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    iput-boolean v2, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->mIsInAppPurchaseConnect:Z

    move v0, v2

    goto :goto_3c
.end method

.method public readChargeAllPacket()V
    .registers 5

    :try_start_0
    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->recieveData:[B

    invoke-interface {v0, v1}, Lcom/lguplus/common/bill/IBillSocket;->readBytes([B)I

    move-result v0

    if-eqz v0, :cond_17

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->recieveData:[B

    const/4 v1, 0x0

    aget-byte v0, v0, v1

    if-nez v0, :cond_18

    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-virtual {p0, v1, v0, v2, v3}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->resultMTX(II[BLjava/lang/String;)V

    :cond_17
    :goto_17
    return-void

    :cond_18
    const/4 v1, -0x1

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-virtual {p0, v1, v0, v2, v3}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->resultMTX(II[BLjava/lang/String;)V
    :try_end_1e
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_1e} :catch_1f

    goto :goto_17

    :catch_1f
    move-exception v0

    const-string v1, "CarrierFuncActivity"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_17
.end method

.method public readChargeBuyablePacket()V
    .registers 5

    :try_start_0
    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->recieveData:[B

    invoke-interface {v0, v1}, Lcom/lguplus/common/bill/IBillSocket;->readBytes([B)I

    move-result v0

    if-eqz v0, :cond_22

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->recieveData:[B

    const/4 v1, 0x0

    aget-byte v0, v0, v1

    const/16 v1, 0x11

    if-ne v0, v1, :cond_23

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->billsocket:Lcom/lguplus/common/bill/IBillSocket;

    invoke-virtual {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->getChargeAllPacket()[B

    move-result-object v1

    invoke-interface {v0, v1}, Lcom/lguplus/common/bill/IBillSocket;->writeBytes([B)Z

    move-result v0

    if-eqz v0, :cond_22

    invoke-virtual {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->readChargeAllPacket()V

    :cond_22
    :goto_22
    return-void

    :cond_23
    const/4 v1, -0x1

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-virtual {p0, v1, v0, v2, v3}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->resultMTX(II[BLjava/lang/String;)V
    :try_end_29
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_29} :catch_2a

    goto :goto_22

    :catch_2a
    move-exception v0

    const-string v1, "CarrierFuncActivity"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_22
.end method

.method public requestMTX(Ljava/lang/String;Ljava/lang/String;)V
    .registers 6

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    if-nez v0, :cond_26

    new-instance v0, Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    invoke-direct {v0, p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;-><init>(Lco/kr/eamobile/mtx/CarrierFuncActivity;)V

    iput-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    new-instance v0, Landroid/content/Intent;

    const-class v1, Lcom/lguplus/common/bill/IBillSocket;

    invoke-virtual {v1}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->serviceConn:Lco/kr/eamobile/mtx/CarrierFuncActivity$MtxServiceConnection;

    const/4 v2, 0x1

    invoke-virtual {p0, v0, v1, v2}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    move-result v0

    iput-boolean v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->mIsInAppPurchaseConnect:Z

    iget-boolean v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->mIsInAppPurchaseConnect:Z

    if-nez v0, :cond_26

    invoke-direct {p0}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->gotoError()V

    :cond_26
    return-void
.end method

.method public resultMTX(II[BLjava/lang/String;)V
    .registers 6

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedContentID:Ljava/lang/String;

    invoke-direct {p0, v0, p1, p2}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->nativeResultMTX(Ljava/lang/String;II)V

    return-void
.end method

.method public setApplicationID(Ljava/lang/String;)V
    .registers 2

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->applicationID:Ljava/lang/String;

    return-void
.end method

.method public setCheckLGTStaffPhone(Ljava/lang/String;)V
    .registers 2

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->chk_lgtstaff:Ljava/lang/String;

    return-void
.end method

.method public setMIN(Ljava/lang/String;)V
    .registers 2

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->minN:Ljava/lang/String;

    return-void
.end method

.method public setModelName(Ljava/lang/String;)V
    .registers 2

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->modelName:Ljava/lang/String;

    return-void
.end method

.method public startFullScreenAd()V
    .registers 1

    return-void
.end method

.method public startFullScreenAdShow()I
    .registers 2

    const/4 v0, 0x0

    return v0
.end method

.method public startMTX(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    .registers 7

    const-string v2, "CarrierFuncActivity"

    iput-object p1, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedGameID:Ljava/lang/String;

    iput-object p2, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedContentID:Ljava/lang/String;

    sput-object p3, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedPID:Ljava/lang/String;

    const-string v0, "CarrierFuncActivity"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "GameID : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const-string v0, "CarrierFuncActivity"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "ContentID : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const-string v0, "CarrierFuncActivity"

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "PID : "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedGameID:Ljava/lang/String;

    if-eqz v0, :cond_5c

    iget-object v0, p0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedContentID:Ljava/lang/String;

    if-eqz v0, :cond_5c

    sget-object v0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedPID:Ljava/lang/String;

    if-nez v0, :cond_5e

    :cond_5c
    const/4 v0, -0x1

    :goto_5d
    return v0

    :cond_5e
    sget-object v0, Lco/kr/eamobile/mtx/CarrierFuncActivity;->cachedPID:Ljava/lang/String;

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lco/kr/eamobile/mtx/CarrierFuncActivity;->requestMTX(Ljava/lang/String;Ljava/lang/String;)V

    const/4 v0, 0x0

    goto :goto_5d
.end method
