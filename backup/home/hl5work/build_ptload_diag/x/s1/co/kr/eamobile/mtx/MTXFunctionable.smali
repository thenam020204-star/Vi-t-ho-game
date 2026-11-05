.class public interface abstract Lco/kr/eamobile/mtx/MTXFunctionable;
.super Ljava/lang/Object;


# static fields
.field public static final KT_RES_ERR_CODE_ALREADY_BOUGHT:I = -0x1f

.field public static final KT_RES_ERR_CODE_CANCEL_BUY:I = -0x20

.field public static final KT_RES_ERR_CODE_NONE_PID:I = -0x1e

.field public static final KT_RES_ERR_CODE_OVER_CHARGE_DAY:I = -0x22

.field public static final KT_RES_ERR_CODE_OVER_CHARGE_MONTH:I = -0x23

.field public static final KT_RES_ERR_CODE_OVER_CHARGE_TOTAL:I = -0x21

.field public static final MTX_ERROR_ALREADY_BOUGHT:I = -0x9

.field public static final MTX_ERROR_CODE_AUTH:I = -0xd

.field public static final MTX_ERROR_CODE_CANCEL:I = -0xa

.field public static final MTX_ERROR_CODE_DATA:I = -0x11

.field public static final MTX_ERROR_CODE_ITEMINFO:I = -0xf

.field public static final MTX_ERROR_CODE_ITEMPURCHASE:I = -0x10

.field public static final MTX_ERROR_CODE_ITEMQUERY:I = -0xe

.field public static final MTX_ERROR_CODE_NETWORKERROR:I = -0xb

.field public static final MTX_ERROR_CODE_PID_IS_NULL:I = -0xc

.field public static final MTX_ERROR_CODE_UNKNOWN:I = -0x12

.field public static final MTX_LOGWRITE:B = 0x3t

.field public static final MTX_REQUEST_BUYABLE:B = 0x2t

.field public static final MTX_REQUEST_PID:B = 0x1t

.field public static final MTX_RESULT_FAIL:I = -0x1

.field public static final MTX_RESULT_SUCCESS:I = 0x0

.field public static final RESULT_CODE_ALREADY_BOUGHT:I = 0x1

.field public static final RESULT_CODE_CASH_LIMIT_GAME_PER_DAY:I = -0x17

.field public static final RESULT_CODE_CASH_LIMIT_GAME_PER_MONTH:I = -0x18

.field public static final RESULT_CODE_CASH_LIMIT_TOTAL_PER_MONTH:I = -0x19

.field public static final RESULT_CODE_CHARGE_BUYABLE_SUCCESS:I = 0x11

.field public static final RESULT_CODE_CHARGE_FAIL_LGT_HOLE:I = -0x16

.field public static final RESULT_CODE_CHARGE_SUCCESS:I = 0x0

.field public static final RESULT_CODE_LGT_FIRMWARE_UPGRADE:I = -0x6

.field public static final RESULT_CODE_LGT_STAFF_PHONE:I = -0x5

.field public static final SKT_ERROR_CODE_ONJOINDIALOG_CANCEL:I = -0x29


# virtual methods
.method public abstract initMTX(Ljava/lang/String;I)I
.end method

.method public abstract requestMTX(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method public abstract resultMTX(II[BLjava/lang/String;)V
.end method

.method public abstract setApplicationID(Ljava/lang/String;)V
.end method

.method public abstract setMIN(Ljava/lang/String;)V
.end method

.method public abstract setModelName(Ljava/lang/String;)V
.end method

.method public abstract startFullScreenAd()V
.end method

.method public abstract startFullScreenAdShow()I
.end method

.method public abstract startMTX(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
.end method
