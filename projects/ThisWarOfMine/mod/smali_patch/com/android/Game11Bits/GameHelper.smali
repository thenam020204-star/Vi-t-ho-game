.class public Lcom/android/Game11Bits/GameHelper;
.super Ljava/lang/Object;
.source "GameHelper.java"

# interfaces
.implements Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;
.implements Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/Game11Bits/GameHelper$SignInFailureReason;,
        Lcom/android/Game11Bits/GameHelper$GameHelperListener;
    }
.end annotation


# static fields
.field public static final CLIENT_ALL:I = 0xd

.field public static final CLIENT_APPSTATE:I = 0x4

.field public static final CLIENT_GAMES:I = 0x1

.field public static final CLIENT_NONE:I = 0x0

.field public static final CLIENT_SNAPSHOT:I = 0x8

.field static final DEFAULT_MAX_SIGN_IN_ATTEMPTS:I = 0x1

.field static final RC_RESOLVE:I = 0x2329

.field static final RC_UNUSED:I = 0x232a

.field static final TAG:Ljava/lang/String; = "GameHelper"


# instance fields
.field private final GAMEHELPER_SHARED_PREFS:Ljava/lang/String;

.field private final KEY_SIGN_IN_CANCELLATIONS:Ljava/lang/String;

.field mActivity:Landroid/app/Activity;

.field mAppContext:Landroid/content/Context;

.field mAppStateApiOptions:Lcom/google/android/gms/common/api/Api$ApiOptions$NoOptions;

.field mConnectOnStart:Z

.field private mConnecting:Z

.field mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

.field mDebugLog:Z

.field mExpectingResolution:Z

.field mGamesApiOptions:Lcom/google/android/gms/games/Games$GamesOptions;

.field mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

.field mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

.field mHandler:Landroid/os/Handler;

.field mListener:Lcom/android/Game11Bits/GameHelper$GameHelperListener;

.field mMaxAutoSignInAttempts:I

.field mRequestedClients:I

.field private mSetupDone:Z

.field mShowErrorDialogs:Z

.field mSignInCancelled:Z

.field mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

.field mUserInitiatedSignIn:Z


# direct methods
.method public constructor <init>(Landroid/app/Activity;I)V
    .locals 3

    .line 179
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 66
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mSetupDone:Z

    .line 69
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 72
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mExpectingResolution:Z

    .line 76
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInCancelled:Z

    const/4 v1, 0x0

    .line 83
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    .line 86
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    .line 96
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    .line 99
    invoke-static {}, Lcom/google/android/gms/games/Games$GamesOptions;->builder()Lcom/google/android/gms/games/Games$GamesOptions$Builder;

    move-result-object v2

    invoke-virtual {v2}, Lcom/google/android/gms/games/Games$GamesOptions$Builder;->build()Lcom/google/android/gms/games/Games$GamesOptions;

    move-result-object v2

    iput-object v2, p0, Lcom/android/Game11Bits/GameHelper;->mGamesApiOptions:Lcom/google/android/gms/games/Games$GamesOptions;

    .line 100
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mAppStateApiOptions:Lcom/google/android/gms/common/api/Api$ApiOptions$NoOptions;

    .line 103
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    .line 114
    iput v0, p0, Lcom/android/Game11Bits/GameHelper;->mRequestedClients:I

    const/4 v2, 0x1

    .line 119
    # twommod: không tự đăng nhập Google Play Games khi mở game (gốc: v2 = true)
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    .line 127
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mUserInitiatedSignIn:Z

    .line 130
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    .line 133
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    .line 136
    iput-boolean v2, p0, Lcom/android/Game11Bits/GameHelper;->mShowErrorDialogs:Z

    .line 139
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mDebugLog:Z

    .line 162
    iput-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mListener:Lcom/android/Game11Bits/GameHelper$GameHelperListener;

    .line 168
    iput v2, p0, Lcom/android/Game11Bits/GameHelper;->mMaxAutoSignInAttempts:I

    .line 741
    const-string v0, "GAMEHELPER_SHARED_PREFS"

    iput-object v0, p0, Lcom/android/Game11Bits/GameHelper;->GAMEHELPER_SHARED_PREFS:Ljava/lang/String;

    .line 742
    const-string v0, "KEY_SIGN_IN_CANCELLATIONS"

    iput-object v0, p0, Lcom/android/Game11Bits/GameHelper;->KEY_SIGN_IN_CANCELLATIONS:Ljava/lang/String;

    .line 180
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    .line 181
    invoke-virtual {p1}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    .line 182
    iput p2, p0, Lcom/android/Game11Bits/GameHelper;->mRequestedClients:I

    .line 183
    new-instance p1, Landroid/os/Handler;

    invoke-direct {p1}, Landroid/os/Handler;-><init>()V

    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mHandler:Landroid/os/Handler;

    return-void
.end method

.method private doApiOptionsPreCheck()V
    .locals 2

    .line 211
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    if-nez v0, :cond_0

    return-void

    .line 215
    :cond_0
    const-string v0, "GameHelper: you cannot call set*ApiOptions after the client builder has been created. Call it before calling createApiClientBuilder() or setup()."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->logError(Ljava/lang/String;)V

    .line 216
    new-instance v1, Ljava/lang/IllegalStateException;

    invoke-direct {v1, v0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v1
.end method

.method static makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;)Landroid/app/Dialog;
    .locals 1

    .line 973
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, p1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object p0

    const p1, 0x104000a

    const/4 v0, 0x0

    .line 974
    invoke-virtual {p0, p1, v0}, Landroid/app/AlertDialog$Builder;->setNeutralButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object p0

    invoke-virtual {p0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object p0

    return-object p0
.end method

.method static makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;)Landroid/app/Dialog;
    .locals 1

    .line 979
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, p2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object p0

    .line 980
    invoke-virtual {p0, p1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object p0

    const p1, 0x104000a

    const/4 p2, 0x0

    invoke-virtual {p0, p1, p2}, Landroid/app/AlertDialog$Builder;->setNeutralButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object p0

    .line 981
    invoke-virtual {p0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object p0

    return-object p0
.end method

.method public static showFailureDialog(Landroid/app/Activity;II)V
    .locals 2

    .line 932
    const-string v0, "GameHelper"

    if-nez p0, :cond_0

    .line 933
    const-string p0, "*** No Activity. Can\'t show failure dialog!"

    invoke-static {v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    packed-switch p1, :pswitch_data_0

    const/16 p1, 0x232a

    const/4 v1, 0x0

    .line 954
    invoke-static {p2, p0, p1, v1}, Lcom/google/android/gms/common/GooglePlayServicesUtil;->getErrorDialog(ILandroid/app/Activity;ILandroid/content/DialogInterface$OnCancelListener;)Landroid/app/Dialog;

    move-result-object p1

    if-nez p1, :cond_1

    .line 958
    const-string p1, "No standard error dialog available. Making fallback dialog."

    invoke-static {v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 960
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v0, 0x0

    .line 962
    invoke-static {p0, v0}, Lcom/android/Game11Bits/GameHelperUtils;->getString(Landroid/content/Context;I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 965
    invoke-static {p2}, Lcom/android/Game11Bits/GameHelperUtils;->errorCodeToString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 960
    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelper;->makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;)Landroid/app/Dialog;

    move-result-object p0

    goto :goto_0

    :pswitch_0
    const/4 p1, 0x2

    .line 940
    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelperUtils;->getString(Landroid/content/Context;I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelper;->makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;)Landroid/app/Dialog;

    move-result-object p0

    goto :goto_0

    :pswitch_1
    const/4 p1, 0x3

    .line 948
    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelperUtils;->getString(Landroid/content/Context;I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelper;->makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;)Landroid/app/Dialog;

    move-result-object p0

    goto :goto_0

    :pswitch_2
    const/4 p1, 0x1

    .line 944
    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelperUtils;->getString(Landroid/content/Context;I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lcom/android/Game11Bits/GameHelper;->makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;)Landroid/app/Dialog;

    move-result-object p0

    goto :goto_0

    :cond_1
    move-object p0, p1

    .line 969
    :goto_0
    invoke-virtual {p0}, Landroid/app/Dialog;->show()V

    return-void

    :pswitch_data_0
    .packed-switch 0x2712
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method


# virtual methods
.method assertConfigured(Ljava/lang/String;)V
    .locals 2

    .line 201
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mSetupDone:Z

    if-eqz v0, :cond_0

    return-void

    .line 202
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "GameHelper error: Operation attempted without setup: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, ". The setup() method must be called before attempting any other operation."

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 205
    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->logError(Ljava/lang/String;)V

    .line 206
    new-instance v0, Ljava/lang/IllegalStateException;

    invoke-direct {v0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public beginUserInitiatedSignIn()V
    .locals 2

    .line 629
    const-string v0, "beginUserInitiatedSignIn: resetting attempt count."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 630
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->resetSignInCancellations()V

    const/4 v0, 0x0

    .line 631
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInCancelled:Z

    const/4 v0, 0x1

    .line 632
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    .line 634
    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v1}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 636
    const-string v1, "beginUserInitiatedSignIn() called when already connected. Calling listener directly to notify of success."

    invoke-virtual {p0, v1}, Lcom/android/Game11Bits/GameHelper;->logWarn(Ljava/lang/String;)V

    .line 638
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->notifyListener(Z)V

    return-void

    .line 640
    :cond_0
    iget-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    if-eqz v1, :cond_1

    .line 641
    const-string v0, "beginUserInitiatedSignIn() called when already connecting. Be patient! You can only call this method after you get an onSignInSucceeded() or onSignInFailed() callback. Suggestion: disable the sign-in button on startup and also when it\'s clicked, and re-enable when you get the callback."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->logWarn(Ljava/lang/String;)V

    return-void

    .line 651
    :cond_1
    const-string v1, "Starting USER-INITIATED sign-in flow."

    invoke-virtual {p0, v1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 656
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mUserInitiatedSignIn:Z

    .line 658
    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    if-eqz v1, :cond_2

    .line 661
    const-string v1, "beginUserInitiatedSignIn: continuing pending sign-in flow."

    invoke-virtual {p0, v1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 662
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 663
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->resolveConnectionResult()V

    return-void

    .line 666
    :cond_2
    const-string v1, "beginUserInitiatedSignIn: starting new sign-in flow."

    invoke-virtual {p0, v1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 667
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 668
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->connect()V

    return-void
.end method

.method connect()V
    .locals 1

    .line 673
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 674
    const-string v0, "Already connected."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    return-void

    .line 677
    :cond_0
    const-string v0, "Starting connection."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 v0, 0x1

    .line 678
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 681
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->connect()V

    return-void
.end method

.method public createApiClientBuilder()Lcom/google/android/gms/common/api/GoogleApiClient$Builder;
    .locals 4

    .line 245
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mSetupDone:Z

    if-nez v0, :cond_2

    .line 252
    new-instance v0, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    invoke-direct {v0, v1, p0, p0}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;-><init>(Landroid/content/Context;Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;)V

    .line 255
    const-string v1, " createApiClientBuilder "

    const-string v2, "GameHelper"

    invoke-static {v2, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 259
    iget v1, p0, Lcom/android/Game11Bits/GameHelper;->mRequestedClients:I

    and-int/lit8 v1, v1, 0x1

    if-eqz v1, :cond_0

    .line 260
    sget-object v1, Lcom/google/android/gms/games/Games;->API:Lcom/google/android/gms/common/api/Api;

    iget-object v3, p0, Lcom/android/Game11Bits/GameHelper;->mGamesApiOptions:Lcom/google/android/gms/games/Games$GamesOptions;

    invoke-virtual {v0, v1, v3}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;->addApi(Lcom/google/android/gms/common/api/Api;Lcom/google/android/gms/common/api/Api$ApiOptions$HasOptions;)Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    .line 261
    sget-object v1, Lcom/google/android/gms/games/Games;->SCOPE_GAMES:Lcom/google/android/gms/common/api/Scope;

    invoke-virtual {v0, v1}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;->addScope(Lcom/google/android/gms/common/api/Scope;)Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    .line 264
    :cond_0
    iget v1, p0, Lcom/android/Game11Bits/GameHelper;->mRequestedClients:I

    and-int/lit8 v1, v1, 0x8

    if-eqz v1, :cond_1

    .line 266
    const-string v1, " mRequestedClients & CLIENT_SNAPSHOT setup "

    invoke-static {v2, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 269
    sget-object v1, Lcom/google/android/gms/drive/Drive;->SCOPE_APPFOLDER:Lcom/google/android/gms/common/api/Scope;

    invoke-virtual {v0, v1}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;->addScope(Lcom/google/android/gms/common/api/Scope;)Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    .line 272
    :cond_1
    iput-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    return-object v0

    .line 248
    :cond_2
    const-string v0, "GameHelper: you called GameHelper.createApiClientBuilder() after calling setup. You can only get a client builder BEFORE performing setup."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->logError(Ljava/lang/String;)V

    .line 249
    new-instance v1, Ljava/lang/IllegalStateException;

    invoke-direct {v1, v0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v1
.end method

.method debugLog(Ljava/lang/String;)V
    .locals 2

    .line 1001
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mDebugLog:Z

    if-eqz v0, :cond_0

    .line 1002
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "GameHelper: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "GameHelper"

    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_0
    return-void
.end method

.method public disconnect()V
    .locals 2

    .line 873
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 874
    const-string v0, "Disconnecting client."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 875
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->disconnect()V

    return-void

    .line 877
    :cond_0
    const-string v0, "GameHelper"

    const-string v1, "disconnect() called when client was already disconnected."

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public enableDebugLog(Z)V
    .locals 0

    .line 496
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mDebugLog:Z

    if-eqz p1, :cond_0

    .line 498
    const-string p1, "Debug log enabled."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    :cond_0
    return-void
.end method

.method public enableDebugLog(ZLjava/lang/String;)V
    .locals 1
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    .line 504
    const-string p2, "GameHelper"

    const-string v0, "GameHelper.enableDebugLog(boolean,String) is deprecated. Use GameHelper.enableDebugLog(boolean)"

    invoke-static {p2, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 506
    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->enableDebugLog(Z)V

    return-void
.end method

.method public getApiClient()Lcom/google/android/gms/common/api/GoogleApiClient;
    .locals 2

    .line 314
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    if-eqz v0, :cond_0

    return-object v0

    .line 315
    :cond_0
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v1, "No GoogleApiClient. Did you call setup()?"

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method getSignInCancellations()I
    .locals 3

    .line 747
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    const-string v1, "GAMEHELPER_SHARED_PREFS"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 749
    const-string v1, "KEY_SIGN_IN_CANCELLATIONS"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public getSignInError()Lcom/android/Game11Bits/GameHelper$SignInFailureReason;
    .locals 1

    .line 345
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    return-object v0
.end method

.method giveUp(Lcom/android/Game11Bits/GameHelper$SignInFailureReason;)V
    .locals 2

    const/4 v0, 0x0

    .line 890
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    .line 891
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->disconnect()V

    .line 892
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    .line 894
    iget p1, p1, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;->mActivityResultCode:I

    const/16 v1, 0x2714

    if-ne p1, v1, :cond_0

    .line 896
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    invoke-static {p1}, Lcom/android/Game11Bits/GameHelperUtils;->printMisconfiguredDebugInfo(Landroid/content/Context;)V

    .line 899
    :cond_0
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->showFailureDialog()V

    .line 900
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 901
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->notifyListener(Z)V

    return-void
.end method

.method public hasSignInError()Z
    .locals 1

    .line 337
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    return v0

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method incrementSignInCancellations()I
    .locals 4

    .line 756
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->getSignInCancellations()I

    move-result v0

    .line 757
    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    const-string v2, "GAMEHELPER_SHARED_PREFS"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    .line 758
    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    add-int/lit8 v0, v0, 0x1

    .line 759
    const-string v2, "KEY_SIGN_IN_CANCELLATIONS"

    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    .line 760
    invoke-interface {v1}, Landroid/content/SharedPreferences$Editor;->commit()Z

    return v0
.end method

.method public isConnecting()Z
    .locals 1

    .line 329
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    return v0
.end method

.method public isSignedIn()Z
    .locals 1

    .line 324
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    return v0

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method logError(Ljava/lang/String;)V
    .locals 2

    .line 1011
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "*** GameHelper ERROR: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "GameHelper"

    invoke-static {v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method logWarn(Ljava/lang/String;)V
    .locals 2

    .line 1007
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "!!! GameHelper WARNING: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "GameHelper"

    invoke-static {v0, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public makeSimpleDialog(Ljava/lang/String;)Landroid/app/Dialog;
    .locals 1

    .line 985
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    if-nez v0, :cond_0

    .line 986
    const-string p1, "*** makeSimpleDialog failed: no current Activity!"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->logError(Ljava/lang/String;)V

    const/4 p1, 0x0

    return-object p1

    .line 989
    :cond_0
    invoke-static {v0, p1}, Lcom/android/Game11Bits/GameHelper;->makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;)Landroid/app/Dialog;

    move-result-object p1

    return-object p1
.end method

.method public makeSimpleDialog(Ljava/lang/String;Ljava/lang/String;)Landroid/app/Dialog;
    .locals 1

    .line 993
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    if-nez v0, :cond_0

    .line 994
    const-string p1, "*** makeSimpleDialog failed: no current Activity!"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->logError(Ljava/lang/String;)V

    const/4 p1, 0x0

    return-object p1

    .line 997
    :cond_0
    invoke-static {v0, p1, p2}, Lcom/android/Game11Bits/GameHelper;->makeSimpleDialog(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;)Landroid/app/Dialog;

    move-result-object p1

    return-object p1
.end method

.method notifyListener(Z)V
    .locals 2

    if-eqz p1, :cond_0

    .line 610
    const-string v0, "SUCCESS"

    goto :goto_0

    .line 611
    :cond_0
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    if-eqz v0, :cond_1

    const-string v0, "FAILURE (error)"

    goto :goto_0

    .line 612
    :cond_1
    const-string v0, "FAILURE (no error)"

    :goto_0
    const-string v1, "Notifying LISTENER of sign-in "

    invoke-virtual {v1, v0}, Ljava/lang/String;->concat(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 609
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 613
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mListener:Lcom/android/Game11Bits/GameHelper$GameHelperListener;

    if-eqz v0, :cond_3

    if-eqz p1, :cond_2

    .line 615
    invoke-interface {v0}, Lcom/android/Game11Bits/GameHelper$GameHelperListener;->onSignInSucceeded()V

    return-void

    .line 617
    :cond_2
    invoke-interface {v0}, Lcom/android/Game11Bits/GameHelper$GameHelperListener;->onSignInFailed()V

    :cond_3
    return-void
.end method

.method public onActivityResult(IILandroid/content/Intent;)V
    .locals 4

    .line 543
    new-instance p3, Ljava/lang/StringBuilder;

    const-string v0, "onActivityResult: req="

    invoke-direct {p3, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const/16 v0, 0x2329

    if-ne p1, v0, :cond_0

    .line 544
    const-string v1, "RC_RESOLVE"

    goto :goto_0

    .line 545
    :cond_0
    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    :goto_0
    invoke-virtual {p3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, ", resp="

    invoke-virtual {p3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 546
    invoke-static {p2}, Lcom/android/Game11Bits/GameHelperUtils;->activityResponseCodeToString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    .line 543
    invoke-virtual {p0, p3}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 p3, 0x0

    const/4 v1, 0x1

    const/16 v2, 0x2711

    const/4 v3, 0x0

    if-eq p1, v0, :cond_2

    if-ne p2, v2, :cond_1

    .line 549
    const-string p1, "onActivityResult: RESULT_RECONNECT_REQUIRED, but request code not meant for us. Disconnecting."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 550
    iput-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInCancelled:Z

    .line 551
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    .line 552
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mUserInitiatedSignIn:Z

    .line 553
    iput-object p3, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    .line 554
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 555
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {p1}, Lcom/google/android/gms/common/api/GoogleApiClient;->disconnect()V

    return-void

    .line 558
    :cond_1
    const-string p1, "onActivityResult: request code not meant for us. Ignoring."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    return-void

    .line 563
    :cond_2
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mExpectingResolution:Z

    .line 565
    iget-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    if-nez p1, :cond_3

    .line 566
    const-string p1, "onActivityResult: ignoring because we are not connecting."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    return-void

    :cond_3
    const/4 p1, -0x1

    if-ne p2, p1, :cond_4

    .line 574
    const-string p1, "onAR: Resolution was RESULT_OK, so connecting current client again."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 575
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->connect()V

    return-void

    :cond_4
    if-ne p2, v2, :cond_5

    .line 577
    const-string p1, "onAR: Resolution was RECONNECT_REQUIRED, so reconnecting."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 578
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->connect()V

    return-void

    :cond_5
    if-nez p2, :cond_6

    .line 581
    const-string p1, "onAR: Got a cancellation result, so disconnecting."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 582
    iput-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInCancelled:Z

    .line 583
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    .line 584
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mUserInitiatedSignIn:Z

    .line 585
    iput-object p3, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    .line 586
    iput-boolean v3, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 587
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {p1}, Lcom/google/android/gms/common/api/GoogleApiClient;->disconnect()V

    .line 590
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->getSignInCancellations()I

    move-result p1

    .line 591
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->incrementSignInCancellations()I

    move-result p2

    .line 592
    new-instance p3, Ljava/lang/StringBuilder;

    const-string v0, "onAR: # of cancellations "

    invoke-direct {p3, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " --> "

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p3, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, ", max "

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget p1, p0, Lcom/android/Game11Bits/GameHelper;->mMaxAutoSignInAttempts:I

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 595
    invoke-virtual {p0, v3}, Lcom/android/Game11Bits/GameHelper;->notifyListener(Z)V

    return-void

    .line 599
    :cond_6
    new-instance p1, Ljava/lang/StringBuilder;

    const-string p3, "onAR: responseCode="

    invoke-direct {p1, p3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 601
    invoke-static {p2}, Lcom/android/Game11Bits/GameHelperUtils;->activityResponseCodeToString(I)Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p3, ", so giving up."

    invoke-virtual {p1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 599
    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 603
    new-instance p1, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    iget-object p3, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    invoke-virtual {p3}, Lcom/google/android/gms/common/ConnectionResult;->getErrorCode()I

    move-result p3

    invoke-direct {p1, p3, p2}, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;-><init>(II)V

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->giveUp(Lcom/android/Game11Bits/GameHelper$SignInFailureReason;)V

    return-void
.end method

.method public onConnected(Landroid/os/Bundle;)V
    .locals 0

    .line 701
    const-string p1, "onConnected: connected!"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 729
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->succeedSignIn()V

    return-void
.end method

.method public onConnectionFailed(Lcom/google/android/gms/common/ConnectionResult;)V
    .locals 3

    .line 777
    const-string v0, "onConnectionFailed"

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 779
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    .line 780
    const-string v0, "Connection failure:"

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 781
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "   - code: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    .line 783
    invoke-virtual {v1}, Lcom/google/android/gms/common/ConnectionResult;->getErrorCode()I

    move-result v1

    .line 782
    invoke-static {v1}, Lcom/android/Game11Bits/GameHelperUtils;->errorCodeToString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 781
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 784
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "   - resolvable: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    invoke-virtual {v1}, Lcom/google/android/gms/common/ConnectionResult;->hasResolution()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 785
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "   - details: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    invoke-virtual {v1}, Lcom/google/android/gms/common/ConnectionResult;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 787
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->getSignInCancellations()I

    move-result v0

    .line 790
    iget-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mUserInitiatedSignIn:Z

    if-eqz v1, :cond_0

    .line 791
    const-string p1, "onConnectionFailed: WILL resolve because user initiated sign-in."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    goto :goto_0

    .line 793
    :cond_0
    iget-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInCancelled:Z

    if-eqz v1, :cond_1

    .line 794
    const-string v0, "onConnectionFailed WILL NOT resolve (user already cancelled once)."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    goto :goto_1

    .line 796
    :cond_1
    iget v1, p0, Lcom/android/Game11Bits/GameHelper;->mMaxAutoSignInAttempts:I

    if-ge v0, v1, :cond_2

    .line 797
    new-instance p1, Ljava/lang/StringBuilder;

    const-string v1, "onConnectionFailed: WILL resolve because we have below the max# of attempts, "

    invoke-direct {p1, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v0, " < "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/Game11Bits/GameHelper;->mMaxAutoSignInAttempts:I

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 821
    :goto_0
    const-string p1, "onConnectionFailed: resolving problem..."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 826
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->resolveConnectionResult()V

    return-void

    .line 805
    :cond_2
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "onConnectionFailed: Will NOT resolve; not user-initiated and max attempts reached: "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v0, " >= "

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v0, p0, Lcom/android/Game11Bits/GameHelper;->mMaxAutoSignInAttempts:I

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 814
    :goto_1
    const-string v0, "onConnectionFailed: since we won\'t resolve, failing now."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 815
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    const/4 p1, 0x0

    .line 816
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 817
    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->notifyListener(Z)V

    return-void
.end method

.method public onConnectionSuspended(I)V
    .locals 2

    .line 907
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "onConnectionSuspended, cause="

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 908
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->disconnect()V

    const/4 p1, 0x0

    .line 909
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    .line 910
    const-string p1, "Making extraordinary call to onSignInFailed callback"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 p1, 0x0

    .line 911
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 912
    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->notifyListener(Z)V

    return-void
.end method

.method public onStart(Landroid/app/Activity;)V
    .locals 3

    .line 355
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    .line 356
    invoke-virtual {p1}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    .line 358
    const-string p1, "onStart"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 359
    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->assertConfigured(Ljava/lang/String;)V

    .line 361
    iget-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    if-eqz p1, :cond_1

    .line 362
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {p1}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result p1

    if-eqz p1, :cond_0

    .line 363
    const-string p1, "GameHelper"

    const-string v0, "GameHelper: client was already connected on onStart()"

    invoke-static {p1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 366
    :cond_0
    const-string p1, "Connecting client."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 p1, 0x1

    .line 367
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 368
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {p1}, Lcom/google/android/gms/common/api/GoogleApiClient;->connect()V

    return-void

    .line 371
    :cond_1
    const-string p1, "Not attempting to connect becase mConnectOnStart=false"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 372
    const-string p1, "Instead, reporting a sign-in failure."

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 373
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mHandler:Landroid/os/Handler;

    new-instance v0, Lcom/android/Game11Bits/GameHelper$1;

    invoke-direct {v0, p0}, Lcom/android/Game11Bits/GameHelper$1;-><init>(Lcom/android/Game11Bits/GameHelper;)V

    const-wide/16 v1, 0x3e8

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method

.method public onStop()V
    .locals 1

    .line 384
    const-string v0, "onStop"

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 385
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->assertConfigured(Ljava/lang/String;)V

    .line 386
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 387
    const-string v0, "Disconnecting client due to onStop"

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 388
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->disconnect()V

    goto :goto_0

    .line 390
    :cond_0
    const-string v0, "Client already disconnected when we got onStop."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    :goto_0
    const/4 v0, 0x0

    .line 392
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    const/4 v0, 0x0

    .line 396
    iput-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    return-void
.end method

.method public reconnectClient()V
    .locals 2

    .line 688
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v0

    if-nez v0, :cond_0

    .line 689
    const-string v0, "GameHelper"

    const-string v1, "reconnectClient() called when client is not connected."

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 691
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->connect()V

    return-void

    .line 693
    :cond_0
    const-string v0, "Reconnecting client."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 694
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->reconnect()V

    return-void
.end method

.method resetSignInCancellations()V
    .locals 3

    .line 767
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mAppContext:Landroid/content/Context;

    const-string v1, "GAMEHELPER_SHARED_PREFS"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 768
    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 769
    const-string v1, "KEY_SIGN_IN_CANCELLATIONS"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    .line 770
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    return-void
.end method

.method resolveConnectionResult()V
    .locals 3

    .line 836
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mExpectingResolution:Z

    if-eqz v0, :cond_0

    .line 837
    const-string v0, "We\'re already expecting the result of a previous resolution."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    return-void

    .line 840
    :cond_0
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    if-nez v0, :cond_1

    .line 841
    const-string v0, "GameHelper"

    const-string v1, "Ignoring attempt to resolve connection result without an active Activity."

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 845
    :cond_1
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "resolveConnectionResult: trying to resolve result: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 848
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    invoke-virtual {v0}, Lcom/google/android/gms/common/ConnectionResult;->hasResolution()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 850
    const-string v0, "Result has resolution. Starting it."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 v0, 0x1

    .line 854
    :try_start_0
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mExpectingResolution:Z

    .line 855
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    const/16 v2, 0x2329

    invoke-virtual {v0, v1, v2}, Lcom/google/android/gms/common/ConnectionResult;->startResolutionForResult(Landroid/app/Activity;I)V
    :try_end_0
    .catch Landroid/content/IntentSender$SendIntentException; {:try_start_0 .. :try_end_0} :catch_0

    return-void

    .line 859
    :catch_0
    const-string v0, "SendIntentException, so connecting again."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 860
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->connect()V

    return-void

    .line 865
    :cond_2
    const-string v0, "resolveConnectionResult: result has no resolution. Giving up."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 866
    new-instance v0, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    invoke-virtual {v1}, Lcom/google/android/gms/common/ConnectionResult;->getErrorCode()I

    move-result v1

    invoke-direct {v0, v1}, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;-><init>(I)V

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->giveUp(Lcom/android/Game11Bits/GameHelper$SignInFailureReason;)V

    const/4 v0, 0x0

    .line 868
    iput-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectionResult:Lcom/google/android/gms/common/ConnectionResult;

    return-void
.end method

.method public setAppStateApiOptions(Lcom/google/android/gms/common/api/Api$ApiOptions$NoOptions;)V
    .locals 0

    .line 234
    invoke-direct {p0}, Lcom/android/Game11Bits/GameHelper;->doApiOptionsPreCheck()V

    .line 235
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mAppStateApiOptions:Lcom/google/android/gms/common/api/Api$ApiOptions$NoOptions;

    return-void
.end method

.method public setConnectOnStart(Z)V
    .locals 2

    .line 1054
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "Forcing mConnectOnStart="

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 1055
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    return-void
.end method

.method public setGamesApiOptions(Lcom/google/android/gms/games/Games$GamesOptions;)V
    .locals 0

    .line 225
    invoke-direct {p0}, Lcom/android/Game11Bits/GameHelper;->doApiOptionsPreCheck()V

    .line 226
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGamesApiOptions:Lcom/google/android/gms/games/Games$GamesOptions;

    return-void
.end method

.method public setMaxAutoSignInAttempts(I)V
    .locals 0

    .line 197
    iput p1, p0, Lcom/android/Game11Bits/GameHelper;->mMaxAutoSignInAttempts:I

    return-void
.end method

.method public setShowErrorDialogs(Z)V
    .locals 0

    .line 350
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mShowErrorDialogs:Z

    return-void
.end method

.method public setup(Lcom/android/Game11Bits/GameHelper$GameHelperListener;)V
    .locals 2

    .line 286
    iget-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mSetupDone:Z

    if-nez v0, :cond_1

    .line 291
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mListener:Lcom/android/Game11Bits/GameHelper$GameHelperListener;

    .line 292
    new-instance p1, Ljava/lang/StringBuilder;

    const-string v0, "Setup: requested clients: "

    invoke-direct {p1, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget v0, p0, Lcom/android/Game11Bits/GameHelper;->mRequestedClients:I

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 294
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    if-nez p1, :cond_0

    .line 296
    invoke-virtual {p0}, Lcom/android/Game11Bits/GameHelper;->createApiClientBuilder()Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    .line 299
    :cond_0
    iget-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    const v1, 0x1020002

    .line 301
    invoke-virtual {v0, v1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 300
    invoke-virtual {p1, v0}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;->setViewForPopups(Landroid/view/View;)Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    move-result-object p1

    .line 302
    invoke-virtual {p1}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;->useDefaultAccount()Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    move-result-object p1

    .line 303
    invoke-virtual {p1}, Lcom/google/android/gms/common/api/GoogleApiClient$Builder;->build()Lcom/google/android/gms/common/api/GoogleApiClient;

    move-result-object p1

    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    const/4 p1, 0x0

    .line 305
    iput-object p1, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClientBuilder:Lcom/google/android/gms/common/api/GoogleApiClient$Builder;

    const/4 p1, 0x1

    .line 306
    iput-boolean p1, p0, Lcom/android/Game11Bits/GameHelper;->mSetupDone:Z

    return-void

    .line 288
    :cond_1
    const-string p1, "GameHelper: you cannot call GameHelper.setup() more than once!"

    invoke-virtual {p0, p1}, Lcom/android/Game11Bits/GameHelper;->logError(Ljava/lang/String;)V

    .line 289
    new-instance v0, Ljava/lang/IllegalStateException;

    invoke-direct {v0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public showFailureDialog()V
    .locals 3

    .line 916
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    if-eqz v0, :cond_1

    .line 917
    invoke-virtual {v0}, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;->getServiceErrorCode()I

    move-result v0

    .line 918
    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    invoke-virtual {v1}, Lcom/android/Game11Bits/GameHelper$SignInFailureReason;->getActivityResultCode()I

    move-result v1

    .line 920
    iget-boolean v2, p0, Lcom/android/Game11Bits/GameHelper;->mShowErrorDialogs:Z

    if-eqz v2, :cond_0

    .line 921
    iget-object v2, p0, Lcom/android/Game11Bits/GameHelper;->mActivity:Landroid/app/Activity;

    invoke-static {v2, v1, v0}, Lcom/android/Game11Bits/GameHelper;->showFailureDialog(Landroid/app/Activity;II)V

    return-void

    .line 923
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "Not showing error dialog because mShowErrorDialogs==false. Error was: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    :cond_1
    return-void
.end method

.method public signOut()V
    .locals 1

    .line 511
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->isConnected()Z

    move-result v0

    if-nez v0, :cond_0

    .line 513
    const-string v0, "signOut: was already disconnected, ignoring."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    return-void

    .line 519
    :cond_0
    iget v0, p0, Lcom/android/Game11Bits/GameHelper;->mRequestedClients:I

    and-int/lit8 v0, v0, 0x1

    if-eqz v0, :cond_1

    .line 520
    const-string v0, "Signing out from the Google API Client."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 521
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-static {v0}, Lcom/google/android/gms/games/Games;->signOut(Lcom/google/android/gms/common/api/GoogleApiClient;)Lcom/google/android/gms/common/api/PendingResult;

    .line 525
    :cond_1
    const-string v0, "Disconnecting client."

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 v0, 0x0

    .line 526
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    .line 527
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 528
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mGoogleApiClient:Lcom/google/android/gms/common/api/GoogleApiClient;

    invoke-virtual {v0}, Lcom/google/android/gms/common/api/GoogleApiClient;->disconnect()V

    .line 530
    const-string v0, "Notifying LISTENER of sign-out"

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    .line 531
    iget-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mListener:Lcom/android/Game11Bits/GameHelper$GameHelperListener;

    if-eqz v0, :cond_2

    .line 532
    invoke-interface {v0}, Lcom/android/Game11Bits/GameHelper$GameHelperListener;->onSignOutCompleted()V

    :cond_2
    return-void
.end method

.method succeedSignIn()V
    .locals 2

    .line 733
    const-string v0, "succeedSignIn"

    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->debugLog(Ljava/lang/String;)V

    const/4 v0, 0x0

    .line 734
    iput-object v0, p0, Lcom/android/Game11Bits/GameHelper;->mSignInFailureReason:Lcom/android/Game11Bits/GameHelper$SignInFailureReason;

    const/4 v0, 0x1

    .line 735
    iput-boolean v0, p0, Lcom/android/Game11Bits/GameHelper;->mConnectOnStart:Z

    const/4 v1, 0x0

    .line 736
    iput-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mUserInitiatedSignIn:Z

    .line 737
    iput-boolean v1, p0, Lcom/android/Game11Bits/GameHelper;->mConnecting:Z

    .line 738
    invoke-virtual {p0, v0}, Lcom/android/Game11Bits/GameHelper;->notifyListener(Z)V

    return-void
.end method
