.class public Lco/kr/eamobile/ConvertingInfo;
.super Ljava/lang/Object;


# instance fields
.field private bUseSubLayer:I

.field private oriPixelType:I

.field private oriScreenHeight:I

.field private oriScreenWidth:I

.field private oriSubLayerPixelType:I

.field private trgMagFilterType:I

.field private trgMagnification:F

.field private trgMarginX:I

.field private trgMarginY:I

.field private trgViewMode:I


# direct methods
.method public constructor <init>(IIIIIFIIII)V
    .registers 11

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->oriScreenWidth:I

    iput p2, p0, Lco/kr/eamobile/ConvertingInfo;->oriScreenHeight:I

    iput p3, p0, Lco/kr/eamobile/ConvertingInfo;->trgMarginX:I

    iput p4, p0, Lco/kr/eamobile/ConvertingInfo;->trgMarginY:I

    iput p5, p0, Lco/kr/eamobile/ConvertingInfo;->oriPixelType:I

    iput p6, p0, Lco/kr/eamobile/ConvertingInfo;->trgMagnification:F

    iput p8, p0, Lco/kr/eamobile/ConvertingInfo;->trgViewMode:I

    iput p7, p0, Lco/kr/eamobile/ConvertingInfo;->trgMagFilterType:I

    iput p9, p0, Lco/kr/eamobile/ConvertingInfo;->bUseSubLayer:I

    iput p10, p0, Lco/kr/eamobile/ConvertingInfo;->oriSubLayerPixelType:I

    return-void
.end method


# virtual methods
.method public getHeight()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->oriScreenHeight:I

    return v0
.end method

.method public getMagFilterType()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->trgMagFilterType:I

    return v0
.end method

.method public getMagnification()F
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->trgMagnification:F

    return v0
.end method

.method public getPixelType()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->oriPixelType:I

    return v0
.end method

.method public getSubLayerPixelType()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->oriSubLayerPixelType:I

    return v0
.end method

.method public getTargetMarginX()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->trgMarginX:I

    return v0
.end method

.method public getTargetMarginY()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->trgMarginY:I

    return v0
.end method

.method public getViewMode()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->trgViewMode:I

    return v0
.end method

.method public getWidth()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->oriScreenWidth:I

    return v0
.end method

.method public isSubLayerUsable()I
    .registers 2

    iget v0, p0, Lco/kr/eamobile/ConvertingInfo;->bUseSubLayer:I

    return v0
.end method

.method public setHeight(I)V
    .registers 2

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->oriScreenHeight:I

    return-void
.end method

.method public setMagFilterType(I)V
    .registers 2

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->trgMagFilterType:I

    return-void
.end method

.method public setMagnification(F)V
    .registers 2

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->trgMagnification:F

    return-void
.end method

.method public setTargetMarginX(I)V
    .registers 2

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->trgMarginX:I

    return-void
.end method

.method public setTargetMarginY(I)V
    .registers 2

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->trgMarginY:I

    return-void
.end method

.method public setWidth(I)V
    .registers 2

    iput p1, p0, Lco/kr/eamobile/ConvertingInfo;->oriScreenWidth:I

    return-void
.end method
