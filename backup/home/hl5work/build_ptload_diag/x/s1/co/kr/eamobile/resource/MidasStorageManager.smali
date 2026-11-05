.class public Lco/kr/eamobile/resource/MidasStorageManager;
.super Ljava/lang/Object;


# static fields
.field public static final TAG:Ljava/lang/String; = "MidasStorageManager"

.field public static instance:Lco/kr/eamobile/resource/MidasStorageManager;


# instance fields
.field private final ERROR:I

.field private externalStorageDirectory:Ljava/lang/String;

.field private externalStorageReadable:Z

.field private externalStorageWriteable:Z

.field private internalStorageDirectory:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/resource/MidasStorageManager;->instance:Lco/kr/eamobile/resource/MidasStorageManager;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->ERROR:I

    invoke-direct {p0, p1}, Lco/kr/eamobile/resource/MidasStorageManager;->updateExternalStorageState(Landroid/content/Context;)V

    return-void
.end method

.method public static createStorageManager(Landroid/content/Context;)V
    .registers 2

    sget-object v0, Lco/kr/eamobile/resource/MidasStorageManager;->instance:Lco/kr/eamobile/resource/MidasStorageManager;

    if-nez v0, :cond_b

    new-instance v0, Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-direct {v0, p0}, Lco/kr/eamobile/resource/MidasStorageManager;-><init>(Landroid/content/Context;)V

    sput-object v0, Lco/kr/eamobile/resource/MidasStorageManager;->instance:Lco/kr/eamobile/resource/MidasStorageManager;

    :cond_b
    return-void
.end method

.method public static destroyStorageManager()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/resource/MidasStorageManager;->instance:Lco/kr/eamobile/resource/MidasStorageManager;

    if-eqz v0, :cond_4

    :cond_4
    const/4 v0, 0x0

    sput-object v0, Lco/kr/eamobile/resource/MidasStorageManager;->instance:Lco/kr/eamobile/resource/MidasStorageManager;

    return-void
.end method

.method public static getInstance()Lco/kr/eamobile/resource/MidasStorageManager;
    .registers 1

    sget-object v0, Lco/kr/eamobile/resource/MidasStorageManager;->instance:Lco/kr/eamobile/resource/MidasStorageManager;

    return-object v0
.end method

.method private isExternalStorageAvailable()Z
    .registers 2

    iget-boolean v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageReadable:Z

    if-eqz v0, :cond_a

    iget-boolean v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageWriteable:Z

    if-eqz v0, :cond_a

    const/4 v0, 0x1

    :goto_9
    return v0

    :cond_a
    const/4 v0, 0x0

    goto :goto_9
.end method

.method private updateExternalStorageState(Landroid/content/Context;)V
    .registers 8

    const/4 v5, 0x0

    const/4 v4, 0x1

    const/4 v3, 0x0

    invoke-static {}, Landroid/os/Environment;->getExternalStorageState()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    const-string v2, "mounted"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1a

    iput-boolean v4, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageWriteable:Z

    iput-boolean v4, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageReadable:Z

    :goto_17
    if-nez p1, :cond_2c

    :cond_19
    :goto_19
    return-void

    :cond_1a
    const-string v2, "mounted_ro"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_27

    iput-boolean v4, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageReadable:Z

    iput-boolean v3, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageWriteable:Z

    goto :goto_17

    :cond_27
    iput-boolean v3, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageWriteable:Z

    iput-boolean v3, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageReadable:Z

    goto :goto_17

    :cond_2c
    invoke-virtual {p1}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v0

    if-eqz v0, :cond_19

    invoke-virtual {p1}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->internalStorageDirectory:Ljava/lang/String;

    invoke-direct {p0}, Lco/kr/eamobile/resource/MidasStorageManager;->isExternalStorageAvailable()Z

    move-result v0

    if-eqz v0, :cond_85

    if-eqz v1, :cond_85

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, "/Android/data/"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "/files"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageDirectory:Ljava/lang/String;

    new-instance v0, Ljava/io/File;

    iget-object v1, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageDirectory:Ljava/lang/String;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_19

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    move-result v0

    if-nez v0, :cond_19

    iput-object v5, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageDirectory:Ljava/lang/String;

    iput-boolean v3, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageWriteable:Z

    iput-boolean v3, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageReadable:Z

    goto :goto_19

    :cond_85
    iput-object v5, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageDirectory:Ljava/lang/String;

    goto :goto_19
.end method


# virtual methods
.method public formatSize(J)Ljava/lang/String;
    .registers 9

    const-wide/16 v4, 0x400

    const/4 v0, 0x0

    cmp-long v1, p1, v4

    if-ltz v1, :cond_35

    const-string v0, "KiB"

    div-long v1, p1, v4

    cmp-long v3, v1, v4

    if-ltz v3, :cond_12

    const-string v0, "MiB"

    div-long/2addr v1, v4

    :cond_12
    :goto_12
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-static {v1, v2}, Ljava/lang/Long;->toString(J)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v3, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->length()I

    move-result v1

    const/4 v2, 0x3

    sub-int/2addr v1, v2

    :goto_21
    if-lez v1, :cond_2b

    const/16 v2, 0x2c

    invoke-virtual {v3, v1, v2}, Ljava/lang/StringBuilder;->insert(IC)Ljava/lang/StringBuilder;

    add-int/lit8 v1, v1, -0x3

    goto :goto_21

    :cond_2b
    if-eqz v0, :cond_30

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_30
    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    :cond_35
    move-wide v1, p1

    goto :goto_12
.end method

.method public getAvailableExternalMemorySize()J
    .registers 5

    invoke-direct {p0}, Lco/kr/eamobile/resource/MidasStorageManager;->isExternalStorageAvailable()Z

    move-result v0

    if-eqz v0, :cond_22

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v0

    new-instance v1, Landroid/os/StatFs;

    invoke-virtual {v0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Landroid/os/StatFs;->getBlockSize()I

    move-result v0

    int-to-long v2, v0

    invoke-virtual {v1}, Landroid/os/StatFs;->getAvailableBlocks()I

    move-result v0

    int-to-long v0, v0

    mul-long/2addr v0, v2

    const/16 v2, 0xa

    shr-long/2addr v0, v2

    :goto_21
    return-wide v0

    :cond_22
    iget v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->ERROR:I

    int-to-long v0, v0

    goto :goto_21
.end method

.method public getAvailableInternalMemorySize()J
    .registers 5

    invoke-static {}, Landroid/os/Environment;->getDataDirectory()Ljava/io/File;

    move-result-object v0

    new-instance v1, Landroid/os/StatFs;

    invoke-virtual {v0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Landroid/os/StatFs;->getBlockSize()I

    move-result v0

    int-to-long v2, v0

    invoke-virtual {v1}, Landroid/os/StatFs;->getAvailableBlocks()I

    move-result v0

    int-to-long v0, v0

    mul-long/2addr v0, v2

    const/16 v2, 0xa

    shr-long/2addr v0, v2

    return-wide v0
.end method

.method public getExternalStorageLocation()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageDirectory:Ljava/lang/String;

    return-object v0
.end method

.method public getInternalStorageLocation()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->internalStorageDirectory:Ljava/lang/String;

    return-object v0
.end method

.method public getTotalExternalMemorySize()J
    .registers 5

    invoke-direct {p0}, Lco/kr/eamobile/resource/MidasStorageManager;->isExternalStorageAvailable()Z

    move-result v0

    if-eqz v0, :cond_22

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v0

    new-instance v1, Landroid/os/StatFs;

    invoke-virtual {v0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Landroid/os/StatFs;->getBlockSize()I

    move-result v0

    int-to-long v2, v0

    invoke-virtual {v1}, Landroid/os/StatFs;->getBlockCount()I

    move-result v0

    int-to-long v0, v0

    mul-long/2addr v0, v2

    const/16 v2, 0xa

    shr-long/2addr v0, v2

    :goto_21
    return-wide v0

    :cond_22
    iget v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->ERROR:I

    int-to-long v0, v0

    goto :goto_21
.end method

.method public getTotalInternalMemorySize()J
    .registers 5

    invoke-static {}, Landroid/os/Environment;->getDataDirectory()Ljava/io/File;

    move-result-object v0

    new-instance v1, Landroid/os/StatFs;

    invoke-virtual {v0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Landroid/os/StatFs;->getBlockSize()I

    move-result v0

    int-to-long v2, v0

    invoke-virtual {v1}, Landroid/os/StatFs;->getBlockCount()I

    move-result v0

    int-to-long v0, v0

    mul-long/2addr v0, v2

    const/16 v2, 0xa

    shr-long/2addr v0, v2

    return-wide v0
.end method

.method public isExternalStorageReadable()Z
    .registers 2

    iget-boolean v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageReadable:Z

    return v0
.end method

.method public isExternalStorageWriteable()Z
    .registers 2

    iget-boolean v0, p0, Lco/kr/eamobile/resource/MidasStorageManager;->externalStorageWriteable:Z

    return v0
.end method
