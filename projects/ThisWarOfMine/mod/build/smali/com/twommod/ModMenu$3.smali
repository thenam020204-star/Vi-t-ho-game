.class Lcom/twommod/ModMenu$3;
.super Ljava/lang/Object;
.source "ModMenu.java"

# interfaces
.implements Landroid/widget/CompoundButton$OnCheckedChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/twommod/ModMenu;->sw(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;I)Landroid/widget/Switch;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$id:I

.field final synthetic val$key:Ljava/lang/String;


# direct methods
.method constructor <init>(ILjava/lang/String;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 167
    iput p1, p0, Lcom/twommod/ModMenu$3;->val$id:I

    iput-object p2, p0, Lcom/twommod/ModMenu$3;->val$key:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .registers 4

    .line 169
    iget p1, p0, Lcom/twommod/ModMenu$3;->val$id:I

    invoke-static {p1, p2}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 170
    # getter for: Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;
    invoke-static {}, Lcom/twommod/ModMenu;->access$000()Landroid/content/SharedPreferences;

    move-result-object p1

    invoke-interface {p1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object p1

    iget-object v0, p0, Lcom/twommod/ModMenu$3;->val$key:Ljava/lang/String;

    invoke-interface {p1, v0, p2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object p1

    invoke-interface {p1}, Landroid/content/SharedPreferences$Editor;->apply()V

    return-void
.end method
