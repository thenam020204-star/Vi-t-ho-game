.class public final Lco/kr/eamobile/hl5/runner/CompatibilityActivity;
.super Landroid/app/Activity;
.source "CompatibilityActivity.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .line 9
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method

.method public static routeFor(ILjava/lang/String;)Ljava/lang/String;
    .registers 3

    .line 11
    const/16 v0, 0x15

    if-lt p0, v0, :cond_17

    const-string p0, "arm64-v8a"

    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-nez p0, :cond_14

    const-string p0, "armeabi-v7a"

    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_17

    .line 12
    :cond_14
    const-string p0, "co.kr.eamobile.hl5.runner.MainActivity"

    goto :goto_19

    .line 13
    :cond_17
    const-string p0, "co.kr.eamobile.HeroesLore5.HeroesLore5"

    .line 11
    :goto_19
    return-object p0
.end method


# virtual methods
.method public onCreate(Landroid/os/Bundle;)V
    .registers 5

    .line 17
    invoke-static {p0}, Lco/kr/eamobile/hl5/diag/Diag;->s_launcher(Landroid/content/Context;)V
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 18
    new-instance p1, Landroid/content/Intent;

    invoke-direct {p1}, Landroid/content/Intent;-><init>()V

    .line 19
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    sget-object v1, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    invoke-static {v0, v1}, Lco/kr/eamobile/hl5/runner/CompatibilityActivity;->routeFor(ILjava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, p0, v0}, Landroid/content/Intent;->setClassName(Landroid/content/Context;Ljava/lang/String;)Landroid/content/Intent;

    .line 20
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/CompatibilityActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    if-eqz v0, :cond_27

    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/CompatibilityActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    const/4 v1, 0x0

    const-string v2, "debug"

    invoke-virtual {v0, v2, v1}, Landroid/content/Intent;->getBooleanExtra(Ljava/lang/String;Z)Z

    move-result v0

    invoke-virtual {p1, v2, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    .line 21
    :cond_27
    invoke-virtual {p0, p1}, Lco/kr/eamobile/hl5/runner/CompatibilityActivity;->startActivity(Landroid/content/Intent;)V

    .line 22
    invoke-virtual {p0}, Lco/kr/eamobile/hl5/runner/CompatibilityActivity;->finish()V

    .line 23
    return-void
.end method
