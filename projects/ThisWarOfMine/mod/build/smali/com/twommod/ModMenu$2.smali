.class Lcom/twommod/ModMenu$2;
.super Ljava/lang/Object;
.source "ModMenu.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/twommod/ModMenu;->build(Landroid/app/Activity;)Landroid/view/View;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field dx:F

.field dy:F

.field moved:Z

.field sx:F

.field sy:F

.field final synthetic val$a:Landroid/app/Activity;

.field final synthetic val$panel:Landroid/widget/LinearLayout;


# direct methods
.method constructor <init>(Landroid/widget/LinearLayout;Landroid/app/Activity;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 114
    iput-object p1, p0, Lcom/twommod/ModMenu$2;->val$panel:Landroid/widget/LinearLayout;

    iput-object p2, p0, Lcom/twommod/ModMenu$2;->val$a:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .registers 9

    .line 117
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout$LayoutParams;

    .line 118
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v1

    const/4 v2, 0x0

    const/4 v3, 0x1

    if-eqz v1, :cond_a0

    if-eq v1, v3, :cond_4f

    const/4 v4, 0x2

    if-eq v1, v4, :cond_14

    return v2

    .line 123
    :cond_14
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawX()F

    move-result v1

    iget v2, p0, Lcom/twommod/ModMenu$2;->sx:F

    sub-float/2addr v1, v2

    invoke-static {v1}, Ljava/lang/Math;->abs(F)F

    move-result v1

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result v2

    iget v4, p0, Lcom/twommod/ModMenu$2;->sy:F

    sub-float/2addr v2, v4

    invoke-static {v2}, Ljava/lang/Math;->abs(F)F

    move-result v2

    add-float/2addr v1, v2

    const/high16 v2, 0x41400000    # 12.0f

    cmpl-float v1, v1, v2

    if-lez v1, :cond_33

    iput-boolean v3, p0, Lcom/twommod/ModMenu$2;->moved:Z

    .line 124
    :cond_33
    iget-boolean v1, p0, Lcom/twommod/ModMenu$2;->moved:Z

    if-eqz v1, :cond_4e

    .line 125
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawX()F

    move-result v1

    iget v2, p0, Lcom/twommod/ModMenu$2;->dx:F

    sub-float/2addr v1, v2

    float-to-int v1, v1

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result p2

    iget v1, p0, Lcom/twommod/ModMenu$2;->dy:F

    sub-float/2addr p2, v1

    float-to-int p2, p2

    iput p2, v0, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 126
    invoke-virtual {p1, v0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    :cond_4e
    return v3

    .line 130
    :cond_4f
    iget-boolean p2, p0, Lcom/twommod/ModMenu$2;->moved:Z

    if-nez p2, :cond_9f

    .line 131
    iget-object p2, p0, Lcom/twommod/ModMenu$2;->val$panel:Landroid/widget/LinearLayout;

    invoke-virtual {p2}, Landroid/widget/LinearLayout;->getVisibility()I

    move-result p2

    if-eqz p2, :cond_5d

    const/4 p2, 0x1

    goto :goto_5e

    :cond_5d
    const/4 p2, 0x0

    :goto_5e
    if-eqz p2, :cond_65

    .line 132
    iget-object v1, p0, Lcom/twommod/ModMenu$2;->val$panel:Landroid/widget/LinearLayout;

    # invokes: Lcom/twommod/ModMenu;->refresh(Landroid/widget/LinearLayout;)V
    invoke-static {v1}, Lcom/twommod/ModMenu;->access$100(Landroid/widget/LinearLayout;)V

    .line 133
    :cond_65
    iget-object v1, p0, Lcom/twommod/ModMenu$2;->val$panel:Landroid/widget/LinearLayout;

    invoke-virtual {v1}, Landroid/widget/LinearLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/widget/FrameLayout$LayoutParams;

    .line 134
    iget v4, v0, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    invoke-virtual {p1}, Landroid/view/View;->getWidth()I

    move-result p1

    add-int/2addr v4, p1

    iget-object p1, p0, Lcom/twommod/ModMenu$2;->val$a:Landroid/app/Activity;

    const/high16 v5, 0x41000000    # 8.0f

    # invokes: Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I
    invoke-static {p1, v5}, Lcom/twommod/ModMenu;->access$200(Landroid/app/Activity;F)I

    move-result p1

    add-int/2addr v4, p1

    iput v4, v1, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    iget p1, v0, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    iget-object v0, p0, Lcom/twommod/ModMenu$2;->val$a:Landroid/app/Activity;

    const/high16 v4, 0x41a00000    # 20.0f

    # invokes: Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I
    invoke-static {v0, v4}, Lcom/twommod/ModMenu;->access$200(Landroid/app/Activity;F)I

    move-result v0

    sub-int/2addr p1, v0

    invoke-static {v2, p1}, Ljava/lang/Math;->max(II)I

    move-result p1

    iput p1, v1, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 135
    iget-object p1, p0, Lcom/twommod/ModMenu$2;->val$panel:Landroid/widget/LinearLayout;

    invoke-virtual {p1, v1}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 136
    iget-object p1, p0, Lcom/twommod/ModMenu$2;->val$panel:Landroid/widget/LinearLayout;

    if-eqz p2, :cond_9a

    goto :goto_9c

    :cond_9a
    const/16 v2, 0x8

    :goto_9c
    invoke-virtual {p1, v2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_9f
    return v3

    .line 120
    :cond_a0
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawX()F

    move-result p1

    iget v1, v0, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    int-to-float v1, v1

    sub-float/2addr p1, v1

    iput p1, p0, Lcom/twommod/ModMenu$2;->dx:F

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result p1

    iget v0, v0, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    int-to-float v0, v0

    sub-float/2addr p1, v0

    iput p1, p0, Lcom/twommod/ModMenu$2;->dy:F

    .line 121
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawX()F

    move-result p1

    iput p1, p0, Lcom/twommod/ModMenu$2;->sx:F

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result p1

    iput p1, p0, Lcom/twommod/ModMenu$2;->sy:F

    iput-boolean v2, p0, Lcom/twommod/ModMenu$2;->moved:Z

    return v3
.end method
