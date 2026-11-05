.class public Lco/kr/eamobile/resource/MidasAssetManager;
.super Ljava/lang/Object;


# static fields
.field public static final CLASS_NAME:Ljava/lang/String; = "MidasAssetManager"

.field private static instance:Lco/kr/eamobile/resource/MidasAssetManager;


# instance fields
.field private assetManager:Landroid/content/res/AssetManager;

.field private assetNameFromNumericHash:Ljava/util/Hashtable;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Hashtable",
            "<",
            "Ljava/lang/Integer;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private assetUIDFromStrHash:Ljava/util/Hashtable;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Hashtable",
            "<",
            "Ljava/lang/String;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private bufferIDtoResourceIDHash:Ljava/util/Hashtable;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Hashtable",
            "<",
            "Ljava/lang/Long;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    return-void
.end method

.method private constructor <init>(Landroid/content/res/AssetManager;)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetManager:Landroid/content/res/AssetManager;

    new-instance v0, Ljava/util/Hashtable;

    invoke-direct {v0}, Ljava/util/Hashtable;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    new-instance v0, Ljava/util/Hashtable;

    invoke-direct {v0}, Ljava/util/Hashtable;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetNameFromNumericHash:Ljava/util/Hashtable;

    new-instance v0, Ljava/util/Hashtable;

    invoke-direct {v0}, Ljava/util/Hashtable;-><init>()V

    iput-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    return-void
.end method

.method public static createAssetManager(Landroid/content/res/AssetManager;)V
    .registers 2

    sget-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-direct {v0, p0}, Lco/kr/eamobile/resource/MidasAssetManager;-><init>(Landroid/content/res/AssetManager;)V

    sput-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    :cond_b
    return-void
.end method

.method private destroy()V
    .registers 3

    const/4 v1, 0x0

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    if-eqz v0, :cond_c

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    invoke-virtual {v0}, Ljava/util/Hashtable;->clear()V

    iput-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    :cond_c
    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetNameFromNumericHash:Ljava/util/Hashtable;

    if-eqz v0, :cond_17

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetNameFromNumericHash:Ljava/util/Hashtable;

    invoke-virtual {v0}, Ljava/util/Hashtable;->clear()V

    iput-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetNameFromNumericHash:Ljava/util/Hashtable;

    :cond_17
    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    if-eqz v0, :cond_22

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    invoke-virtual {v0}, Ljava/util/Hashtable;->clear()V

    iput-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    :cond_22
    return-void
.end method

.method public static destroyAssetManager()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    if-eqz v0, :cond_9

    sget-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-direct {v0}, Lco/kr/eamobile/resource/MidasAssetManager;->destroy()V

    :cond_9
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    return-void
.end method

.method public static getInstance()Lco/kr/eamobile/resource/MidasAssetManager;
    .registers 1

    sget-object v0, Lco/kr/eamobile/resource/MidasAssetManager;->instance:Lco/kr/eamobile/resource/MidasAssetManager;

    return-object v0
.end method


# virtual methods
.method public attachBufferIDtoResourceID(JI)V
    .registers 12

    const-string v7, ")"

    const-string v6, "MidasAssetManager"

    const-string v5, ", "

    const-string v4, "## Attaching Info (attachBufferID, resourceID) => ("

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    invoke-virtual {v0, v1}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    if-nez v0, :cond_48

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    iget-object v2, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    invoke-virtual {v2, v1, v0}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v2, "MidasAssetManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "## Attaching Info (attachBufferID, resourceID) => ("

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ", "

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ")"

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v6, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_47
    return-void

    :cond_48
    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    if-eq p3, v0, :cond_85

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    invoke-virtual {v0, v1}, Ljava/util/Hashtable;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    iget-object v2, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    invoke-virtual {v2, v1, v0}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v2, "MidasAssetManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "## Attaching Info (attachBufferID, resourceID) => ("

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ", "

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ")"

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v6, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_47

    :cond_85
    const-string v2, "MidasAssetManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "## Attaching Info (attachBufferID, resourceID) => ("

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ", "

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ") is already exist."

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v6, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_47
.end method

.method public detachBufferIDfromResourceID(J)V
    .registers 8

    const-string v4, "## Detaching Info (detachBufferID, resourceID) => ("

    const-string v3, "MidasAssetManager"

    invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    const-string v1, "MidasAssetManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "## detachBufferID ==> "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v3, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    iget-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->bufferIDtoResourceIDHash:Ljava/util/Hashtable;

    invoke-virtual {v1, v0}, Ljava/util/Hashtable;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    if-eqz p0, :cond_57

    const-string v1, "MidasAssetManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "## Detaching Info (detachBufferID, resourceID) => ("

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ") is removed successfully."

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_56
    return-void

    :cond_57
    const-string v1, "MidasAssetManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "## Detaching Info (detachBufferID, resourceID) => ("

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", NULL) is not exist."

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_56
.end method

.method public getAssetSize(Ljava/lang/String;)I
    .registers 7

    const/4 v0, 0x0

    const-string v1, "MidasAssetManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "###### assetName ==> "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :try_start_19
    iget-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetManager:Landroid/content/res/AssetManager;

    const/4 v2, 0x1

    invoke-virtual {v1, p1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;I)Ljava/io/InputStream;
    :try_end_1f
    .catch Ljava/io/IOException; {:try_start_19 .. :try_end_1f} :catch_30
    .catchall {:try_start_19 .. :try_end_1f} :catchall_3e

    move-result-object v0

    :try_start_20
    invoke-virtual {v0}, Ljava/io/InputStream;->available()I
    :try_end_23
    .catch Ljava/io/IOException; {:try_start_20 .. :try_end_23} :catch_30
    .catchall {:try_start_20 .. :try_end_23} :catchall_4d

    move-result v1

    if-eqz v0, :cond_29

    :try_start_26
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_29
    .catch Ljava/io/IOException; {:try_start_26 .. :try_end_29} :catch_2b

    :cond_29
    :goto_29
    move v0, v1

    :goto_2a
    return v0

    :catch_2b
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_29

    :catch_30
    move-exception v1

    const/4 v1, -0x1

    if-eqz v0, :cond_37

    :try_start_34
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_37
    .catch Ljava/io/IOException; {:try_start_34 .. :try_end_37} :catch_39

    :cond_37
    :goto_37
    move v0, v1

    goto :goto_2a

    :catch_39
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_37

    :catchall_3e
    move-exception v1

    move-object v4, v1

    move-object v1, v0

    move-object v0, v4

    :goto_42
    if-eqz v1, :cond_47

    :try_start_44
    invoke-virtual {v1}, Ljava/io/InputStream;->close()V
    :try_end_47
    .catch Ljava/io/IOException; {:try_start_44 .. :try_end_47} :catch_48

    :cond_47
    :goto_47
    throw v0

    :catch_48
    move-exception v1

    invoke-virtual {v1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_47

    :catchall_4d
    move-exception v1

    move-object v4, v1

    move-object v1, v0

    move-object v0, v4

    goto :goto_42
.end method

.method public getUniqueAssetNameFromID(I)Ljava/lang/String;
    .registers 4

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    iget-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetNameFromNumericHash:Ljava/util/Hashtable;

    invoke-virtual {v1, v0}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/String;

    return-object p0
.end method

.method public getUniqueNumberFromStrings(Ljava/lang/String;)I
    .registers 4

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    invoke-virtual {v0, p1}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    if-nez v0, :cond_2d

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    invoke-virtual {v0}, Ljava/util/Hashtable;->size()I

    move-result v0

    add-int/lit8 v0, v0, 0x1

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    iget-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    invoke-virtual {v1, p1, v0}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v1, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetNameFromNumericHash:Ljava/util/Hashtable;

    invoke-virtual {v1, v0, p1}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetUIDFromStrHash:Ljava/util/Hashtable;

    invoke-virtual {v0, p1}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    :goto_2c
    return v0

    :cond_2d
    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    goto :goto_2c
.end method

.method public loadAsset(I[BI)I
    .registers 12

    const/4 v6, -0x1

    const-string v5, "## loadAsset at close() (Exception) ##"

    const-string v4, "MidasAssetManager"

    const/4 v0, 0x0

    invoke-virtual {p0, p1}, Lco/kr/eamobile/resource/MidasAssetManager;->getUniqueAssetNameFromID(I)Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_15

    const-string v0, "MidasAssetManager"

    const-string v0, "###### assetName is null "

    invoke-static {v4, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    move v0, v6

    :goto_14
    return v0

    :cond_15
    :try_start_15
    iget-object v2, p0, Lco/kr/eamobile/resource/MidasAssetManager;->assetManager:Landroid/content/res/AssetManager;

    const/4 v3, 0x1

    invoke-virtual {v2, v1, v3}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;I)Ljava/io/InputStream;
    :try_end_1b
    .catch Ljava/io/IOException; {:try_start_15 .. :try_end_1b} :catch_31
    .catchall {:try_start_15 .. :try_end_1b} :catchall_49

    move-result-object v0

    const/4 v1, 0x0

    :try_start_1d
    invoke-virtual {v0, p2, v1, p3}, Ljava/io/InputStream;->read([BII)I
    :try_end_20
    .catch Ljava/io/IOException; {:try_start_1d .. :try_end_20} :catch_31
    .catchall {:try_start_1d .. :try_end_20} :catchall_5c

    move-result v1

    if-eqz v0, :cond_26

    :try_start_23
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_26
    .catch Ljava/io/IOException; {:try_start_23 .. :try_end_26} :catch_28

    :cond_26
    :goto_26
    move v0, v1

    goto :goto_14

    :catch_28
    move-exception v0

    const-string v0, "MidasAssetManager"

    const-string v0, "## loadAsset at close() (Exception) ##"

    invoke-static {v4, v5}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_26

    :catch_31
    move-exception v1

    :try_start_32
    const-string v1, "MidasAssetManager"

    const-string v2, "## loadAsset at open() (Exception) ##"

    invoke-static {v1, v2}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_39
    .catchall {:try_start_32 .. :try_end_39} :catchall_5c

    if-eqz v0, :cond_3e

    :try_start_3b
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_3e
    .catch Ljava/io/IOException; {:try_start_3b .. :try_end_3e} :catch_40

    :cond_3e
    :goto_3e
    move v0, v6

    goto :goto_14

    :catch_40
    move-exception v0

    const-string v0, "MidasAssetManager"

    const-string v0, "## loadAsset at close() (Exception) ##"

    invoke-static {v4, v5}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_3e

    :catchall_49
    move-exception v1

    move-object v7, v1

    move-object v1, v0

    move-object v0, v7

    :goto_4d
    if-eqz v1, :cond_52

    :try_start_4f
    invoke-virtual {v1}, Ljava/io/InputStream;->close()V
    :try_end_52
    .catch Ljava/io/IOException; {:try_start_4f .. :try_end_52} :catch_53

    :cond_52
    :goto_52
    throw v0

    :catch_53
    move-exception v1

    const-string v1, "MidasAssetManager"

    const-string v1, "## loadAsset at close() (Exception) ##"

    invoke-static {v4, v5}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_52

    :catchall_5c
    move-exception v1

    move-object v7, v1

    move-object v1, v0

    move-object v0, v7

    goto :goto_4d
.end method
