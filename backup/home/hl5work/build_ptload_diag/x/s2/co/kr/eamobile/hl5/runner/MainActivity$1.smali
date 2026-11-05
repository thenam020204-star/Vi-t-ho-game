.class Lco/kr/eamobile/hl5/runner/MainActivity$1;
.super Ljava/lang/Object;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lco/kr/eamobile/hl5/runner/MainActivity;->startGameView()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lco/kr/eamobile/hl5/runner/MainActivity;


# direct methods
.method constructor <init>(Lco/kr/eamobile/hl5/runner/MainActivity;)V
    .registers 2

    .line 118
    iput-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity$1;->this$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .registers 10

    .line 121
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result p1

    .line 122
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getPointerCount()I

    move-result v0

    .line 124
    const/4 v1, 0x0

    const/4 v2, 0x1

    const/4 v3, 0x2

    if-ne p1, v3, :cond_25

    .line 126
    nop

    :goto_e
    if-ge v1, v0, :cond_24

    .line 127
    invoke-virtual {p2, v1}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result p1

    .line 128
    iget-object v4, p0, Lco/kr/eamobile/hl5/runner/MainActivity$1;->this$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    invoke-virtual {p2, v1}, Landroid/view/MotionEvent;->getX(I)F

    move-result v5

    invoke-virtual {p2, v1}, Landroid/view/MotionEvent;->getY(I)F

    move-result v6

    # invokes: Lco/kr/eamobile/hl5/runner/MainActivity;->dispatchTouch(IFFI)V
    invoke-static {v4, v3, v5, v6, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->access$000(Lco/kr/eamobile/hl5/runner/MainActivity;IFFI)V

    .line 126
    add-int/lit8 v1, v1, 0x1

    goto :goto_e

    .line 130
    :cond_24
    return v2

    .line 133
    :cond_25
    const/4 v3, 0x3

    if-ne p1, v3, :cond_40

    .line 134
    nop

    :goto_29
    if-ge v1, v0, :cond_3f

    .line 135
    invoke-virtual {p2, v1}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result p1

    .line 136
    iget-object v3, p0, Lco/kr/eamobile/hl5/runner/MainActivity$1;->this$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    invoke-virtual {p2, v1}, Landroid/view/MotionEvent;->getX(I)F

    move-result v4

    invoke-virtual {p2, v1}, Landroid/view/MotionEvent;->getY(I)F

    move-result v5

    # invokes: Lco/kr/eamobile/hl5/runner/MainActivity;->dispatchTouch(IFFI)V
    invoke-static {v3, v2, v4, v5, p1}, Lco/kr/eamobile/hl5/runner/MainActivity;->access$000(Lco/kr/eamobile/hl5/runner/MainActivity;IFFI)V

    .line 134
    add-int/lit8 v1, v1, 0x1

    goto :goto_29

    .line 138
    :cond_3f
    return v2

    .line 141
    :cond_40
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getActionIndex()I

    move-result v0

    .line 142
    invoke-virtual {p2, v0}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result v3

    .line 143
    invoke-virtual {p2, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v4

    .line 144
    invoke-virtual {p2, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result p2

    .line 146
    if-eqz p1, :cond_61

    const/4 v0, 0x5

    if-ne p1, v0, :cond_56

    goto :goto_61

    .line 149
    :cond_56
    if-eq p1, v2, :cond_5b

    const/4 v0, 0x6

    if-ne p1, v0, :cond_66

    .line 151
    :cond_5b
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity$1;->this$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    # invokes: Lco/kr/eamobile/hl5/runner/MainActivity;->dispatchTouch(IFFI)V
    invoke-static {p1, v2, v4, p2, v3}, Lco/kr/eamobile/hl5/runner/MainActivity;->access$000(Lco/kr/eamobile/hl5/runner/MainActivity;IFFI)V

    goto :goto_66

    .line 148
    :cond_61
    :goto_61
    iget-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity$1;->this$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    # invokes: Lco/kr/eamobile/hl5/runner/MainActivity;->dispatchTouch(IFFI)V
    invoke-static {p1, v1, v4, p2, v3}, Lco/kr/eamobile/hl5/runner/MainActivity;->access$000(Lco/kr/eamobile/hl5/runner/MainActivity;IFFI)V

    .line 153
    :cond_66
    :goto_66
    return v2
.end method
