.class public final synthetic Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lco/kr/eamobile/hl5/runner/MainActivity;

.field public final synthetic f$1:Landroid/widget/TextView;


# direct methods
.method public synthetic constructor <init>(Lco/kr/eamobile/hl5/runner/MainActivity;Landroid/widget/TextView;)V
    .registers 3

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;->f$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    iput-object p2, p0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;->f$1:Landroid/widget/TextView;

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 3

    .line 0
    iget-object v0, p0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;->f$0:Lco/kr/eamobile/hl5/runner/MainActivity;

    iget-object v1, p0, Lco/kr/eamobile/hl5/runner/MainActivity$$ExternalSyntheticLambda3;->f$1:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Lco/kr/eamobile/hl5/runner/MainActivity;->lambda$onCreate$2$co-kr-eamobile-hl5-runner-MainActivity(Landroid/widget/TextView;)V

    return-void
.end method
