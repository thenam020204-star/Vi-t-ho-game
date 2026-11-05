.class Lco/kr/eamobile/CletEntry;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/opengl/GLSurfaceView$Renderer;


# static fields
.field private static androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

.field private static androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

.field private static androidMediaManager:Lco/kr/eamobile/audio/MidasMediaManager;

.field private static androidMusicManager:Lco/kr/eamobile/audio/MidasMusicManager;

.field private static androidNetworkManager:Lco/kr/eamobile/network/MidasNetworkManager;

.field private static androidSoundManager:Lco/kr/eamobile/audio/MidasSoundManager;

.field private static androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

.field private static androidVibrator:Lco/kr/eamobile/device/MidasVibrator;

.field private static mActivityRef:Lco/kr/eamobile/MidasActivity;


# instance fields
.field private final DATA_VFS_NAME:Ljava/lang/String;

.field private final MULTI_TOUCH_DEVICE_CHECK_FILE_NAME:Ljava/lang/String;

.field private final SOUND_VFS_NAME:Ljava/lang/String;

.field private final TAG:Ljava/lang/String;

.field private alpha:I

.field private autoMarginY:I

.field private bDecidedTouchType:Z

.field private bSupportMultitouch:Z

.field private bUseSublayer:I

.field private blue:I

.field private green:I

.field private lastActionMoveEvtX:[I

.field private lastActionMoveEvtY:[I

.field private lastActionUpEvtX:[I

.field private lastActionUpEvtY:[I

.field private mAudioManager:Landroid/media/AudioManager;

.field private mDestroyed:Z

.field private mLastTouchTime:J

.field private mStarted:Z

.field private needFinish:Z

.field private red:I

.field private screenHeight:I

.field private screenWidth:I

.field private sublayerPixelType:I

.field private targetMagFilterType:I

.field private targetMagnification:F

.field private targetMarginX:I

.field private targetMarginY:I

.field private targetViewMode:I

.field private vfsFD:Ljava/io/FileDescriptor;

.field private vfsFD_sound:Ljava/io/FileDescriptor;

.field private vfsLength:J

.field private vfsLength_sound:J

.field private vfsStartOffset:J

.field private vfsStartOffset_sound:J

.field private vfsafd:Landroid/content/res/AssetFileDescriptor;

.field private vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

.field private wipiColorType:I

.field private wipiLCDHeight:I

.field private wipiLCDWidth:I


# direct methods
.method public constructor <init>(Lco/kr/eamobile/MidasActivity;)V
    .registers 5

    const-string v1, "data.vfs.mp3"

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "ClentEntry"

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->TAG:Ljava/lang/String;

    const-string v0, "mtouchdevice.txt"

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->MULTI_TOUCH_DEVICE_CHECK_FILE_NAME:Ljava/lang/String;

    const-string v0, "data.vfs.mp3"

    iput-object v1, p0, Lco/kr/eamobile/CletEntry;->DATA_VFS_NAME:Ljava/lang/String;

    const-string v0, "data.vfs.mp3"

    iput-object v1, p0, Lco/kr/eamobile/CletEntry;->SOUND_VFS_NAME:Ljava/lang/String;

    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->mLastTouchTime:J

    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->mStarted:Z

    const/4 v0, 0x0

    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->mDestroyed:Z

    sput-object p1, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-static {p1}, Lco/kr/eamobile/device/MidasDeviceInfo;->createDeviceInfo(Landroid/content/Context;)V

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getInstance()Lco/kr/eamobile/device/MidasDeviceInfo;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

    invoke-direct {p0, p1}, Lco/kr/eamobile/CletEntry;->initEnvironmentValues(Lco/kr/eamobile/MidasActivity;)V

    invoke-virtual {p1}, Lco/kr/eamobile/MidasActivity;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    invoke-static {v0}, Lco/kr/eamobile/resource/MidasAssetManager;->createAssetManager(Landroid/content/res/AssetManager;)V

    invoke-static {}, Lco/kr/eamobile/resource/MidasAssetManager;->getInstance()Lco/kr/eamobile/resource/MidasAssetManager;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-static {p1}, Lco/kr/eamobile/resource/MidasStorageManager;->createStorageManager(Landroid/content/Context;)V

    invoke-static {}, Lco/kr/eamobile/resource/MidasStorageManager;->getInstance()Lco/kr/eamobile/resource/MidasStorageManager;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    invoke-static {v0}, Lco/kr/eamobile/audio/MidasMediaManager;->createMediaManager(Landroid/media/AudioManager;)V

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getInstance()Lco/kr/eamobile/audio/MidasMediaManager;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidMediaManager:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-static {p1}, Lco/kr/eamobile/device/MidasVibrator;->createVibrator(Landroid/content/Context;)V

    invoke-static {}, Lco/kr/eamobile/device/MidasVibrator;->getInstance()Lco/kr/eamobile/device/MidasVibrator;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidVibrator:Lco/kr/eamobile/device/MidasVibrator;

    invoke-static {p1}, Lco/kr/eamobile/network/MidasNetworkManager;->createNetworkManager(Landroid/content/Context;)V

    invoke-static {}, Lco/kr/eamobile/network/MidasNetworkManager;->getInstance()Lco/kr/eamobile/network/MidasNetworkManager;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidNetworkManager:Lco/kr/eamobile/network/MidasNetworkManager;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    iget-wide v1, p0, Lco/kr/eamobile/CletEntry;->vfsStartOffset:J

    invoke-static {p1, v0, v1, v2}, Lco/kr/eamobile/audio/MidasMusicManager;->createMusicManager(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V

    invoke-static {}, Lco/kr/eamobile/audio/MidasMusicManager;->getInstance()Lco/kr/eamobile/audio/MidasMusicManager;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidMusicManager:Lco/kr/eamobile/audio/MidasMusicManager;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    iget-wide v1, p0, Lco/kr/eamobile/CletEntry;->vfsStartOffset:J

    invoke-static {p1, v0, v1, v2}, Lco/kr/eamobile/audio/MidasSoundManager;->createSoundManager(Landroid/content/Context;Landroid/content/res/AssetFileDescriptor;J)V

    invoke-static {}, Lco/kr/eamobile/audio/MidasSoundManager;->getInstance()Lco/kr/eamobile/audio/MidasSoundManager;

    move-result-object v0

    sput-object v0, Lco/kr/eamobile/CletEntry;->androidSoundManager:Lco/kr/eamobile/audio/MidasSoundManager;

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getMin()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Lco/kr/eamobile/MidasActivity;->setMIN(Ljava/lang/String;)V

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getModel()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Lco/kr/eamobile/MidasActivity;->setModelName(Ljava/lang/String;)V

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->checkTouchType()V

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativeInitKernel()V

    return-void
.end method

.method public static attachBufferIDtoResourceID(JI)V
    .registers 4

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-virtual {v0, p0, p1, p2}, Lco/kr/eamobile/resource/MidasAssetManager;->attachBufferIDtoResourceID(JI)V

    return-void
.end method

.method private checkTouchType()V
    .registers 7

    const/4 v5, 0x0

    const/4 v4, 0x1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->bDecidedTouchType:Z

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getModel()Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_10

    iput-boolean v4, p0, Lco/kr/eamobile/CletEntry;->bDecidedTouchType:Z

    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->bSupportMultitouch:Z

    :goto_f
    return-void

    :cond_10
    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->bSupportMultitouch:Z

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0}, Lco/kr/eamobile/MidasActivity;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    :try_start_18
    const-string v2, "mtouchdevice.txt"

    invoke-virtual {v0, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v0

    check-cast v0, Landroid/content/res/AssetManager$AssetInputStream;
    :try_end_20
    .catch Ljava/io/IOException; {:try_start_18 .. :try_end_20} :catch_4d

    :try_start_20
    new-instance v2, Ljava/io/BufferedReader;

    new-instance v3, Ljava/io/InputStreamReader;

    invoke-direct {v3, v0}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    invoke-direct {v2, v3}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_2a
    .catch Ljava/io/IOException; {:try_start_20 .. :try_end_2a} :catch_51

    :cond_2a
    :try_start_2a
    invoke-virtual {v2}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_39

    invoke-virtual {v1, v3}, Ljava/lang/String;->compareTo(Ljava/lang/String;)I

    move-result v3

    if-nez v3, :cond_2a

    const/4 v1, 0x1

    iput-boolean v1, p0, Lco/kr/eamobile/CletEntry;->bSupportMultitouch:Z
    :try_end_39
    .catch Ljava/io/IOException; {:try_start_2a .. :try_end_39} :catch_55

    :cond_39
    move-object v1, v0

    move-object v0, v2

    :goto_3b
    if-eqz v1, :cond_40

    :try_start_3d
    invoke-virtual {v1}, Landroid/content/res/AssetManager$AssetInputStream;->close()V

    :cond_40
    if-eqz v0, :cond_45

    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_45
    .catch Ljava/io/IOException; {:try_start_3d .. :try_end_45} :catch_48

    :cond_45
    :goto_45
    iput-boolean v4, p0, Lco/kr/eamobile/CletEntry;->bDecidedTouchType:Z

    goto :goto_f

    :catch_48
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_45

    :catch_4d
    move-exception v0

    move-object v0, v5

    move-object v1, v5

    goto :goto_3b

    :catch_51
    move-exception v1

    move-object v1, v0

    move-object v0, v5

    goto :goto_3b

    :catch_55
    move-exception v1

    move-object v1, v0

    move-object v0, v2

    goto :goto_3b
.end method

.method public static clipFree(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMediaManager;->clipFree(J)I

    move-result v0

    return v0
.end method

.method public static clipFreeMusic(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMusicManager;->clipFree(J)I

    move-result v0

    return v0
.end method

.method public static clipFreeSound(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->clipFree(J)I

    move-result v0

    return v0
.end method

.method public static clipPause(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMediaManager;->clipPause(J)I

    move-result v0

    return v0
.end method

.method public static clipPauseMusic(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMusicManager;->clipPause(J)I

    move-result v0

    return v0
.end method

.method public static clipPauseSound(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->clipPause(J)I

    move-result v0

    return v0
.end method

.method public static clipPlay(JZ)I
    .registers 4

    invoke-static {p0, p1, p2}, Lco/kr/eamobile/audio/MidasMediaManager;->clipPlay(JZ)I

    move-result v0

    return v0
.end method

.method public static clipPlayMusic(JZ)I
    .registers 4

    invoke-static {p0, p1, p2}, Lco/kr/eamobile/audio/MidasMusicManager;->clipPlay(JZ)I

    move-result v0

    return v0
.end method

.method public static clipPlaySound(JZ)I
    .registers 4

    invoke-static {p0, p1, p2}, Lco/kr/eamobile/audio/MidasSoundManager;->clipPlay(JZ)I

    move-result v0

    return v0
.end method

.method public static clipResume(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMediaManager;->clipResume(J)I

    move-result v0

    return v0
.end method

.method public static clipResumeMusic(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMusicManager;->clipResume(J)I

    move-result v0

    return v0
.end method

.method public static clipResumeSound(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->clipResume(J)I

    move-result v0

    return v0
.end method

.method public static clipStop(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMediaManager;->clipStop(J)I

    move-result v0

    return v0
.end method

.method public static clipStopMusic(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMusicManager;->clipStop(J)I

    move-result v0

    return v0
.end method

.method public static clipStopSound(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasSoundManager;->clipStop(J)I

    move-result v0

    return v0
.end method

.method public static detachBufferIDfromResourceID(J)V
    .registers 3

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-virtual {v0, p0, p1}, Lco/kr/eamobile/resource/MidasAssetManager;->detachBufferIDfromResourceID(J)V

    return-void
.end method

.method public static exitApp()V
    .registers 1

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0}, Lco/kr/eamobile/MidasActivity;->finish()V

    return-void
.end method

.method private finalizeEnvironmentValues()V
    .registers 2

    const/4 v0, 0x0

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionUpEvtX:[I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionUpEvtY:[I

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    if-eqz v0, :cond_12

    :try_start_d
    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->close()V
    :try_end_12
    .catch Ljava/io/IOException; {:try_start_d .. :try_end_12} :catch_1c

    :cond_12
    :goto_12
    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    if-eqz v0, :cond_1b

    :try_start_16
    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->close()V
    :try_end_1b
    .catch Ljava/io/IOException; {:try_start_16 .. :try_end_1b} :catch_21

    :cond_1b
    :goto_1b
    return-void

    :catch_1c
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_12

    :catch_21
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1b
.end method

.method public static freeMemory()I
    .registers 2

    invoke-static {}, Ljava/lang/Runtime;->getRuntime()Ljava/lang/Runtime;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Runtime;->freeMemory()J

    move-result-wide v0

    long-to-int v0, v0

    return v0
.end method

.method public static getAssetSize(Ljava/lang/String;)I
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-virtual {v0, p0}, Lco/kr/eamobile/resource/MidasAssetManager;->getAssetSize(Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public static getAvailableExternalMemorySize()J
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-virtual {v0}, Lco/kr/eamobile/resource/MidasStorageManager;->getAvailableExternalMemorySize()J

    move-result-wide v0

    return-wide v0
.end method

.method public static getAvailableInternalMemorySize()J
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-virtual {v0}, Lco/kr/eamobile/resource/MidasStorageManager;->getAvailableInternalMemorySize()J

    move-result-wide v0

    return-wide v0
.end method

.method public static getClipStatus(J)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/audio/MidasMediaManager;->getClipStatus(J)I

    move-result v0

    return v0
.end method

.method public static getIMEI()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getIMEI()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getIMSI()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getIMSI()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getManufacturer()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getManufacturer()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getMin()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getMin()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getModel()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getModel()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getMusicVolume()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/audio/MidasMusicManager;->getVolume()I

    move-result v0

    return v0
.end method

.method public static getOSVersion()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getOSVersion()I

    move-result v0

    return v0
.end method

.method public static getOpenGLESVersion()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->getOpenGLESVersion()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getSoundVolume()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/audio/MidasSoundManager;->getVolume()I

    move-result v0

    return v0
.end method

.method public static getTotalExternalMemorySize()J
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-virtual {v0}, Lco/kr/eamobile/resource/MidasStorageManager;->getTotalExternalMemorySize()J

    move-result-wide v0

    return-wide v0
.end method

.method public static getTotalInternalMemorySize()J
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-virtual {v0}, Lco/kr/eamobile/resource/MidasStorageManager;->getTotalInternalMemorySize()J

    move-result-wide v0

    return-wide v0
.end method

.method public static getUniqueAssetNameFromID(I)Ljava/lang/String;
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-virtual {v0, p0}, Lco/kr/eamobile/resource/MidasAssetManager;->getUniqueAssetNameFromID(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getUniqueNumberFromStrings(Ljava/lang/String;)I
    .registers 2

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-virtual {v0, p0}, Lco/kr/eamobile/resource/MidasAssetManager;->getUniqueNumberFromStrings(Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public static getVolume()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->getVolume()I

    move-result v0

    return v0
.end method

.method private initEnvironmentValues(Lco/kr/eamobile/MidasActivity;)V
    .registers 7

    const/4 v4, 0x0

    const/16 v3, -0x270f

    const/16 v2, 0xa

    const-string v0, "data.vfs.mp3"

    const-string v0, "audio"

    invoke-virtual {p1, v0}, Lco/kr/eamobile/MidasActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    new-array v0, v2, [I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    new-array v0, v2, [I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    new-array v0, v2, [I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionUpEvtX:[I

    new-array v0, v2, [I

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionUpEvtY:[I

    move v0, v4

    :goto_22
    if-ge v0, v2, :cond_37

    iget-object v1, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    aput v3, v1, v0

    iget-object v1, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    aput v3, v1, v0

    iget-object v1, p0, Lco/kr/eamobile/CletEntry;->lastActionUpEvtX:[I

    aput v3, v1, v0

    iget-object v1, p0, Lco/kr/eamobile/CletEntry;->lastActionUpEvtY:[I

    aput v3, v1, v0

    add-int/lit8 v0, v0, 0x1

    goto :goto_22

    :cond_37
    const/4 v0, 0x0

    :try_start_38
    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {p1}, Lco/kr/eamobile/MidasActivity;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    const-string v1, "data.vfs.mp3"

    invoke-virtual {v0, v1}, Landroid/content/res/AssetManager;->openFd(Ljava/lang/String;)Landroid/content/res/AssetFileDescriptor;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->getFileDescriptor()Ljava/io/FileDescriptor;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsFD:Ljava/io/FileDescriptor;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->getStartOffset()J

    move-result-wide v0

    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->vfsStartOffset:J

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->getLength()J

    move-result-wide v0

    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->vfsLength:J
    :try_end_5e
    .catch Ljava/io/IOException; {:try_start_38 .. :try_end_5e} :catch_88

    :goto_5e
    const/4 v0, 0x0

    :try_start_5f
    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {p1}, Lco/kr/eamobile/MidasActivity;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    const-string v1, "data.vfs.mp3"

    invoke-virtual {v0, v1}, Landroid/content/res/AssetManager;->openFd(Ljava/lang/String;)Landroid/content/res/AssetFileDescriptor;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->getFileDescriptor()Ljava/io/FileDescriptor;

    move-result-object v0

    iput-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsFD_sound:Ljava/io/FileDescriptor;

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->getStartOffset()J

    move-result-wide v0

    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->vfsStartOffset_sound:J

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->vfsafd_sound:Landroid/content/res/AssetFileDescriptor;

    invoke-virtual {v0}, Landroid/content/res/AssetFileDescriptor;->getLength()J

    move-result-wide v0

    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->vfsLength_sound:J
    :try_end_85
    .catch Ljava/io/IOException; {:try_start_5f .. :try_end_85} :catch_8d

    :goto_85
    iput-boolean v4, p0, Lco/kr/eamobile/CletEntry;->needFinish:Z

    return-void

    :catch_88
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_5e

    :catch_8d
    move-exception v0

    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_85
.end method

.method public static initMTX(Ljava/lang/String;I)I
    .registers 3

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0, p0, p1}, Lco/kr/eamobile/MidasActivity;->initMTX(Ljava/lang/String;I)I

    move-result v0

    return v0
.end method

.method public static isAirplaneModeOn()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/network/MidasNetworkManager;->isAirplaneModeOn()I

    move-result v0

    return v0
.end method

.method public static isFBOSupported()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->isFBOSupported()I

    move-result v0

    return v0
.end method

.method public static isNetworkAvailable()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/network/MidasNetworkManager;->isNetworkAvailable()I

    move-result v0

    return v0
.end method

.method public static isNetworkRoaming()I
    .registers 1

    invoke-static {}, Lco/kr/eamobile/network/MidasNetworkManager;->isNetworkRoaming()I

    move-result v0

    return v0
.end method

.method public static loadAsset(I[BI)I
    .registers 4

    sget-object v0, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-virtual {v0, p0, p1, p2}, Lco/kr/eamobile/resource/MidasAssetManager;->loadAsset(I[BI)I

    move-result v0

    return v0
.end method

.method public static loadWebpage([B)I
    .registers 6

    new-instance v0, Ljava/lang/String;

    invoke-direct {v0, p0}, Ljava/lang/String;-><init>([B)V

    :try_start_5
    new-instance v1, Landroid/content/Intent;

    const-string v2, "android.intent.action.VIEW"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "http://"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0, v1}, Lco/kr/eamobile/MidasActivity;->startActivity(Landroid/content/Intent;)V
    :try_end_28
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_28} :catch_2a

    const/4 v0, 0x0

    :goto_29
    return v0

    :catch_2a
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    const/4 v0, -0x1

    goto :goto_29
.end method

.method private native nativeDestroyApp()V
.end method

.method private native nativeDestroyTexture()V
.end method

.method private native nativeFocusGained()V
.end method

.method private native nativeFocusLost()V
.end method

.method private native nativeInitAudioConfiguration(J)V
.end method

.method private native nativeInitDisplay(IIIIIIIIIIIFIIII)I
.end method

.method private native nativeInitFileStorage(Ljava/lang/String;Ljava/lang/String;)V
.end method

.method private native nativeInitKernel()V
.end method

.method private native nativeInitVFS(Ljava/io/FileDescriptor;JJ)V
.end method

.method private native nativeKeyHandleEvent(III)V
.end method

.method private native nativeLoop()V
.end method

.method private native nativePauseApp()V
.end method

.method private native nativeResumeApp()V
.end method

.method private native nativeStartApp()V
.end method

.method private native nativeTouchHandleEvent(IIII)V
.end method

.method public static phnCallPlace([B)I
    .registers 6

    new-instance v0, Ljava/lang/String;

    invoke-direct {v0, p0}, Ljava/lang/String;-><init>([B)V

    :try_start_5
    new-instance v1, Landroid/content/Intent;

    const-string v2, "android.intent.action.CALL"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "tel:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0, v1}, Lco/kr/eamobile/MidasActivity;->startActivity(Landroid/content/Intent;)V
    :try_end_28
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_28} :catch_2a

    const/4 v0, 0x0

    :goto_29
    return v0

    :catch_2a
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    const/4 v0, -0x1

    goto :goto_29
.end method

.method public static putClip(JII[BIIIII)I
    .registers 11

    invoke-static/range {p0 .. p9}, Lco/kr/eamobile/audio/MidasMediaManager;->putClip(JII[BIIIII)I

    move-result v0

    return v0
.end method

.method public static putMusicClip(JJJ)I
    .registers 7

    invoke-static/range {p0 .. p5}, Lco/kr/eamobile/audio/MidasMusicManager;->putClip(JJJ)I

    move-result v0

    return v0
.end method

.method public static putSoundClip(JJJI)I
    .registers 8

    invoke-static/range {p0 .. p6}, Lco/kr/eamobile/audio/MidasSoundManager;->putClip(JJJI)I

    move-result v0

    return v0
.end method

.method public static requestMTX(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    .registers 4

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0, p0, p1, p2}, Lco/kr/eamobile/MidasActivity;->startMTX(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public static setMusicVolume(I)V
    .registers 1

    invoke-static {p0}, Lco/kr/eamobile/audio/MidasMusicManager;->setVolume(I)V

    return-void
.end method

.method public static setSoundVolume(I)V
    .registers 1

    invoke-static {p0}, Lco/kr/eamobile/audio/MidasSoundManager;->setVolume(I)V

    return-void
.end method

.method public static setVolume(I)V
    .registers 1

    invoke-static {p0}, Lco/kr/eamobile/audio/MidasMediaManager;->setVolume(I)V

    return-void
.end method

.method public static startFullScreenAd()I
    .registers 3

    const-string v2, "TAG"

    const-string v0, "TAG"

    const-string v0, "## addbg step2 : startFullScreenAd() ==> in"

    invoke-static {v2, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0}, Lco/kr/eamobile/MidasActivity;->startFullScreenAdShow()I

    move-result v0

    const-string v1, "TAG"

    const-string v1, "## addbg step2 : startFullScreenAd() ==> out"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return v0
.end method

.method public static totalMemory()I
    .registers 2

    invoke-static {}, Ljava/lang/Runtime;->getRuntime()Ljava/lang/Runtime;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Runtime;->maxMemory()J

    move-result-wide v0

    long-to-int v0, v0

    return v0
.end method

.method public static vibration(II)I
    .registers 3

    invoke-static {p0, p1}, Lco/kr/eamobile/device/MidasVibrator;->vibration(II)I

    move-result v0

    return v0
.end method


# virtual methods
.method public adjustAutoScreen()V
    .registers 8

    const/16 v6, 0x0

    const/high16 v5, 0x3f800000    # 1.0f

    const/16 v4, 0x0

    const/16 v3, 0x0

    const/4 v2, 0x0

    iget v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    if-eqz v0, :cond_12

    iget v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    if-eqz v0, :cond_12

    :cond_11
    :goto_11
    return-void

    :cond_12
    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    iget v1, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-gt v0, v1, :cond_50

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_39

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x320

    if-lt v0, v1, :cond_39

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto :goto_11

    :cond_39
    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    if-lt v0, v3, :cond_11

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0xf0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto :goto_11

    :cond_50
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa5a

    if-lt v0, v1, :cond_6f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_6f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x212

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto :goto_11

    :cond_6f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa50

    if-lt v0, v1, :cond_8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x210

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto :goto_11

    :cond_8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa46

    if-lt v0, v1, :cond_ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa3c

    if-lt v0, v1, :cond_ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa32

    if-lt v0, v1, :cond_ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa28

    if-lt v0, v1, :cond_10e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_10e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x208

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_10e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa1e

    if-lt v0, v1, :cond_12e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_12e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x206

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_12e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa14

    if-lt v0, v1, :cond_14e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_14e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x204

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_14e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa0a

    if-lt v0, v1, :cond_16e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_16e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x202

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_16e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xa00

    if-lt v0, v1, :cond_18e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_18e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x200

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_18e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9f6

    if-lt v0, v1, :cond_1ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fe

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9ec

    if-lt v0, v1, :cond_1ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9e2

    if-lt v0, v1, :cond_1ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9d8

    if-lt v0, v1, :cond_20e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_20e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_20e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9ce

    if-lt v0, v1, :cond_22e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_22e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_22e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9c3

    if-lt v0, v1, :cond_24e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_24e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_24e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9ba

    if-lt v0, v1, :cond_26e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_26e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_26e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9b0

    if-lt v0, v1, :cond_28e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_28e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_28e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x9a6

    if-lt v0, v1, :cond_2ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ee

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x99c

    if-lt v0, v1, :cond_2ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ec

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x992

    if-lt v0, v1, :cond_2ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ea

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x988

    if-lt v0, v1, :cond_30e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_30e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_30e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x97e

    if-lt v0, v1, :cond_32e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_32e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_32e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x974

    if-lt v0, v1, :cond_34e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_34e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_34e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x96a

    if-lt v0, v1, :cond_36e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_36e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_36e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x968

    if-lt v0, v1, :cond_38e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_38e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x5

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40a00000    # 5.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_38e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x960

    if-lt v0, v1, :cond_3ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x258

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x958

    if-lt v0, v1, :cond_3ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x256

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x950

    if-lt v0, v1, :cond_3ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x254

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x948

    if-lt v0, v1, :cond_40e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_40e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x252

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_40e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x940

    if-lt v0, v1, :cond_42e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_42e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x250

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_42e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x938

    if-lt v0, v1, :cond_44e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_44e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x24e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_44e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x930

    if-lt v0, v1, :cond_46e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_46e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x24c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_46e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x928

    if-lt v0, v1, :cond_48e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_48e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x24a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_48e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x920

    if-lt v0, v1, :cond_4ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_4ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x248

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_4ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x918

    if-lt v0, v1, :cond_4ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_4ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x246

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_4ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x910

    if-lt v0, v1, :cond_4ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_4ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x244

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_4ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x908

    if-lt v0, v1, :cond_50e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_50e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x242

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_50e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x900

    if-lt v0, v1, :cond_52e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_52e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x240

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_52e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8f8

    if-lt v0, v1, :cond_54e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_54e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x23e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_54e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8f0

    if-lt v0, v1, :cond_56e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_56e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x23c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_56e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8e8

    if-lt v0, v1, :cond_58e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_58e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x23a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_58e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8e0

    if-lt v0, v1, :cond_5ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_5ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x238

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_5ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8d8

    if-lt v0, v1, :cond_5ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_5ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x236

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_5ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8d0

    if-lt v0, v1, :cond_5ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_5ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x234

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_5ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8c8

    if-lt v0, v1, :cond_60e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_60e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x232

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_60e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8c0

    if-lt v0, v1, :cond_62e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_62e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x230

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_62e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8b8

    if-lt v0, v1, :cond_64e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_64e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x22e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_64e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8b0

    if-lt v0, v1, :cond_66e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_66e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x22c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_66e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8a8

    if-lt v0, v1, :cond_68e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_68e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x22a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_68e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x8a0

    if-lt v0, v1, :cond_6ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_6ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x228

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_6ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x898

    if-lt v0, v1, :cond_6ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_6ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x226

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_6ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x890

    if-lt v0, v1, :cond_6ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_6ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x224

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_6ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x888

    if-lt v0, v1, :cond_70e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_70e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x222

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_70e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x880

    if-lt v0, v1, :cond_72e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_72e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x220

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_72e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x878

    if-lt v0, v1, :cond_74e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_74e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x21e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_74e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x870

    if-lt v0, v1, :cond_76e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_76e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x21c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_76e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x868

    if-lt v0, v1, :cond_78e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_78e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x21a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_78e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x860

    if-lt v0, v1, :cond_7ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_7ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x218

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_7ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x858

    if-lt v0, v1, :cond_7ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_7ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x216

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_7ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x850

    if-lt v0, v1, :cond_7ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_7ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x214

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_7ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x848

    if-lt v0, v1, :cond_80e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_80e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x212

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_80e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x840

    if-lt v0, v1, :cond_82e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_82e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x210

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_82e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x838

    if-lt v0, v1, :cond_84e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_84e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_84e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x830

    if-lt v0, v1, :cond_86e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_86e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_86e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x828

    if-lt v0, v1, :cond_88e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_88e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_88e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x820

    if-lt v0, v1, :cond_8ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_8ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x208

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_8ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x818

    if-lt v0, v1, :cond_8ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_8ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x206

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_8ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x810

    if-lt v0, v1, :cond_8ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_8ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x204

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_8ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x808

    if-lt v0, v1, :cond_90e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_90e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x202

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_90e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x800

    if-lt v0, v1, :cond_92e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_92e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x200

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_92e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7f8

    if-lt v0, v1, :cond_94e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_94e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fe

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_94e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7f0

    if-lt v0, v1, :cond_96e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_96e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_96e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7e8

    if-lt v0, v1, :cond_98e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_98e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_98e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7e0

    if-lt v0, v1, :cond_9ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_9ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_9ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7d8

    if-lt v0, v1, :cond_9ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_9ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_9ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7d0

    if-lt v0, v1, :cond_9ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_9ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_9ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7c8

    if-lt v0, v1, :cond_a0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_a0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_a0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7c0

    if-lt v0, v1, :cond_a2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_a2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_a2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7b8

    if-lt v0, v1, :cond_a4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_a4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ee

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_a4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7b0

    if-lt v0, v1, :cond_a6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_a6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ec

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_a6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7a8

    if-lt v0, v1, :cond_a8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_a8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ea

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_a8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x7a0

    if-lt v0, v1, :cond_aae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_aae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_aae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x798

    if-lt v0, v1, :cond_ace

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_ace

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_ace
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x790

    if-lt v0, v1, :cond_aee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_aee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_aee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x788

    if-lt v0, v1, :cond_b0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_b0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_b0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x780

    if-lt v0, v1, :cond_b2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_b2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_b2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x778

    if-lt v0, v1, :cond_b4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_b4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1de

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_b4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x770

    if-lt v0, v1, :cond_b6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_b6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1dc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_b6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x768

    if-lt v0, v1, :cond_b8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_b8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1da

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_b8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x760

    if-lt v0, v1, :cond_bae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_bae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_bae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x758

    if-lt v0, v1, :cond_bce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_bce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_bce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x750

    if-lt v0, v1, :cond_bee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_bee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_bee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x748

    if-lt v0, v1, :cond_c0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_c0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_c0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x740

    if-lt v0, v1, :cond_c2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_c2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_c2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x738

    if-lt v0, v1, :cond_c4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_c4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ce

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_c4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x730

    if-lt v0, v1, :cond_c6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_c6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1cc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_c6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x728

    if-lt v0, v1, :cond_c8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_c8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ca

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_c8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x720

    if-lt v0, v1, :cond_cae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_cae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_cae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x718

    if-lt v0, v1, :cond_cce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_cce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_cce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x710

    if-lt v0, v1, :cond_cee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_cee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_cee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x708

    if-lt v0, v1, :cond_d0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_d0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_d0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x700

    if-lt v0, v1, :cond_d2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_d2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_d2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6f8

    if-lt v0, v1, :cond_d4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_d4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1be

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_d4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6f0

    if-lt v0, v1, :cond_d6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_d6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1bc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_d6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6e8

    if-lt v0, v1, :cond_d8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_d8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ba

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_d8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6e0

    if-lt v0, v1, :cond_dae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_dae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_dae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6d8

    if-lt v0, v1, :cond_dce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_dce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_dce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6d0

    if-lt v0, v1, :cond_dee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_dee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_dee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6c8

    if-lt v0, v1, :cond_e0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_e0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_e0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6c0

    if-lt v0, v1, :cond_e2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_e2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_e2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6b8

    if-lt v0, v1, :cond_e4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_e4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ae

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_e4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6b0

    if-lt v0, v1, :cond_e6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_e6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ac

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_e6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6a8

    if-lt v0, v1, :cond_e8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_e8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1aa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_e8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x6a0

    if-lt v0, v1, :cond_eae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_eae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_eae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x698

    if-lt v0, v1, :cond_ece

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_ece

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_ece
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x690

    if-lt v0, v1, :cond_eee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_eee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_eee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x688

    if-lt v0, v1, :cond_f0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_f0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_f0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x680

    if-lt v0, v1, :cond_f2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_f2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_f2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x678

    if-lt v0, v1, :cond_f4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_f4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_f4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x670

    if-lt v0, v1, :cond_f6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_f6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_f6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x668

    if-lt v0, v1, :cond_f8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_f8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_f8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x660

    if-lt v0, v1, :cond_fae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_fae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x198

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_fae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x658

    if-lt v0, v1, :cond_fce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_fce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x196

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_fce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x650

    if-lt v0, v1, :cond_fee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_fee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x194

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_fee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x648

    if-lt v0, v1, :cond_100e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_100e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x192

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_100e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x640

    if-lt v0, v1, :cond_102e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_102e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_102e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x638

    if-lt v0, v1, :cond_104e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_104e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_104e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x630

    if-lt v0, v1, :cond_106e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_106e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_106e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x628

    if-lt v0, v1, :cond_108e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_108e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_108e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x620

    if-lt v0, v1, :cond_10ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_10ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x4

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x188

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40800000    # 4.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_10ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x61e

    if-lt v0, v1, :cond_10ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_10ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x20a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_10ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x618

    if-lt v0, v1, :cond_10ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_10ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x208

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_10ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x612

    if-lt v0, v1, :cond_110e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_110e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x206

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_110e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x60c

    if-lt v0, v1, :cond_112e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_112e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x204

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_112e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x606

    if-lt v0, v1, :cond_114e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_114e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x202

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_114e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x600

    if-lt v0, v1, :cond_116e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_116e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x200

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_116e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5fa

    if-lt v0, v1, :cond_118e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_118e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fe

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_118e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5f4

    if-lt v0, v1, :cond_11ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_11ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_11ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5ee

    if-lt v0, v1, :cond_11ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_11ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_11ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5e8

    if-lt v0, v1, :cond_11ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_11ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_11ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5e2

    if-lt v0, v1, :cond_120e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_120e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_120e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5dc

    if-lt v0, v1, :cond_122e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_122e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_122e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5d6

    if-lt v0, v1, :cond_124e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_124e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_124e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5d0

    if-lt v0, v1, :cond_126e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_126e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_126e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5ca

    if-lt v0, v1, :cond_128e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_128e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ee

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_128e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5c4

    if-lt v0, v1, :cond_12ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_12ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ec

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_12ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5be

    if-lt v0, v1, :cond_12ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_12ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ea

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_12ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5b8

    if-lt v0, v1, :cond_12ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_12ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_12ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5b2

    if-lt v0, v1, :cond_130e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_130e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_130e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5ac

    if-lt v0, v1, :cond_132e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_132e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_132e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5a6

    if-lt v0, v1, :cond_134e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_134e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_134e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x5a0

    if-lt v0, v1, :cond_136e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_136e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_136e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x59a

    if-lt v0, v1, :cond_138e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_138e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1de

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_138e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x594

    if-lt v0, v1, :cond_13ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_13ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1dc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_13ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x58e

    if-lt v0, v1, :cond_13ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_13ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1da

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_13ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x588

    if-lt v0, v1, :cond_13ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_13ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_13ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x582

    if-lt v0, v1, :cond_140e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_140e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_140e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x57c

    if-lt v0, v1, :cond_142e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_142e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_142e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x576

    if-lt v0, v1, :cond_144e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_144e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_144e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x570

    if-lt v0, v1, :cond_146e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_146e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_146e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x56a

    if-lt v0, v1, :cond_148e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_148e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ce

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_148e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x564

    if-lt v0, v1, :cond_14ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_14ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1cc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_14ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x55e

    if-lt v0, v1, :cond_14ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_14ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ca

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_14ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x558

    if-lt v0, v1, :cond_14ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_14ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_14ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x552

    if-lt v0, v1, :cond_150e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_150e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_150e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x54c

    if-lt v0, v1, :cond_152e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_152e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_152e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x546

    if-lt v0, v1, :cond_154e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_154e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_154e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x540

    if-lt v0, v1, :cond_156e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_156e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_156e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x53a

    if-lt v0, v1, :cond_158e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_158e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1be

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_158e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x534

    if-lt v0, v1, :cond_15ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_15ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1bc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_15ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x52e

    if-lt v0, v1, :cond_15ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_15ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ba

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_15ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x528

    if-lt v0, v1, :cond_15ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_15ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_15ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x522

    if-lt v0, v1, :cond_160e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_160e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_160e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x51c

    if-lt v0, v1, :cond_162e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_162e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_162e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x516

    if-lt v0, v1, :cond_164e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_164e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_164e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x510

    if-lt v0, v1, :cond_166e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_166e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_166e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x50a

    if-lt v0, v1, :cond_168e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_168e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ae

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_168e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x504

    if-lt v0, v1, :cond_16ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_16ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ac

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_16ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4fe

    if-lt v0, v1, :cond_16ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_16ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1aa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_16ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4f8

    if-lt v0, v1, :cond_16ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_16ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_16ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4f2

    if-lt v0, v1, :cond_170e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_170e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_170e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4ec

    if-lt v0, v1, :cond_172e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_172e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_172e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4e6

    if-lt v0, v1, :cond_174e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_174e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_174e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4e0

    if-lt v0, v1, :cond_176e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_176e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_176e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4da

    if-lt v0, v1, :cond_178e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_178e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_178e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4d4

    if-lt v0, v1, :cond_17ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_17ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_17ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4ce

    if-lt v0, v1, :cond_17ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_17ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_17ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4c8

    if-lt v0, v1, :cond_17ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_17ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x198

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_17ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4c2

    if-lt v0, v1, :cond_180e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_180e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x196

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_180e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4bc

    if-lt v0, v1, :cond_182e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_182e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x194

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_182e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4b6

    if-lt v0, v1, :cond_184e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_184e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x192

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_184e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4b0

    if-lt v0, v1, :cond_186e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_186e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_186e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4aa

    if-lt v0, v1, :cond_188e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_188e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_188e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x4a4

    if-lt v0, v1, :cond_18ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_18ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_18ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x49e

    if-lt v0, v1, :cond_18ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_18ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_18ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x498

    if-lt v0, v1, :cond_18ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_18ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x188

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_18ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x492

    if-lt v0, v1, :cond_190e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_190e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x186

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_190e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x48c

    if-lt v0, v1, :cond_192e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_192e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x184

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_192e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x486

    if-lt v0, v1, :cond_194e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_194e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x182

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_194e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x480

    if-lt v0, v1, :cond_196e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_196e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x180

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_196e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x47a

    if-lt v0, v1, :cond_198e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_198e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x17e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_198e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x474

    if-lt v0, v1, :cond_19ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_19ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x17c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_19ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x46e

    if-lt v0, v1, :cond_19ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_19ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x17a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_19ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x468

    if-lt v0, v1, :cond_19ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_19ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x178

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_19ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x462

    if-lt v0, v1, :cond_1a0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1a0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x176

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1a0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x45c

    if-lt v0, v1, :cond_1a2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1a2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x174

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1a2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x456

    if-lt v0, v1, :cond_1a4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1a4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x172

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1a4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x450

    if-lt v0, v1, :cond_1a6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1a6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x170

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1a6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x44a

    if-lt v0, v1, :cond_1a8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1a8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x16e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1a8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x444

    if-lt v0, v1, :cond_1aae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1aae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x16c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1aae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x43e

    if-lt v0, v1, :cond_1ace

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1ace

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x16a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1ace
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x438

    if-lt v0, v1, :cond_1aee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1aee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x168

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1aee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x432

    if-lt v0, v1, :cond_1b0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1b0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x166

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1b0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x42c

    if-lt v0, v1, :cond_1b2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1b2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x164

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1b2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x426

    if-lt v0, v1, :cond_1b4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1b4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x162

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1b4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x420

    if-lt v0, v1, :cond_1b6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1b6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x160

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1b6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x41a

    if-lt v0, v1, :cond_1b8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1b8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x15e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1b8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x414

    if-lt v0, v1, :cond_1bae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1bae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x15c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1bae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x40e

    if-lt v0, v1, :cond_1bce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1bce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x15a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1bce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x408

    if-lt v0, v1, :cond_1bee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1bee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x158

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1bee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x404

    if-lt v0, v1, :cond_1c0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1c0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x3

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x156

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40400000    # 3.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1c0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x400

    if-lt v0, v1, :cond_1c2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1c2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x200

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1c2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3fc

    if-lt v0, v1, :cond_1c4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1c4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fe

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1c4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3f8

    if-lt v0, v1, :cond_1c6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1c6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1c6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3f4

    if-lt v0, v1, :cond_1c8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1c8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1fa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1c8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3f0

    if-lt v0, v1, :cond_1cae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1cae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1cae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3ec

    if-lt v0, v1, :cond_1cce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1cce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1cce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3e8

    if-lt v0, v1, :cond_1cee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1cee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1cee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3e4

    if-lt v0, v1, :cond_1d0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1d0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1d0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3e0

    if-lt v0, v1, :cond_1d2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1d2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1f0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1d2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3dc

    if-lt v0, v1, :cond_1d4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1d4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ee

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1d4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3d8

    if-lt v0, v1, :cond_1d6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1d6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ec

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1d6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3d4

    if-lt v0, v1, :cond_1d8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1d8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ea

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1d8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3d0

    if-lt v0, v1, :cond_1dae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1dae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1dae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3cc

    if-lt v0, v1, :cond_1dce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1dce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1dce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3c8

    if-lt v0, v1, :cond_1dee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1dee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1dee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3c4

    if-lt v0, v1, :cond_1e0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1e0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1e0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3c0

    if-lt v0, v1, :cond_1e2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1e2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1e0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1e2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3bc

    if-lt v0, v1, :cond_1e4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1e4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1de

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1e4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3b8

    if-lt v0, v1, :cond_1e6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1e6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1dc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1e6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3b4

    if-lt v0, v1, :cond_1e8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1e8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1da

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1e8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3b0

    if-lt v0, v1, :cond_1eae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1eae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1eae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3ac

    if-lt v0, v1, :cond_1ece

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1ece

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1ece
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3a8

    if-lt v0, v1, :cond_1eee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1eee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1eee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3a4

    if-lt v0, v1, :cond_1f0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1f0e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1f0e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x3a0

    if-lt v0, v1, :cond_1f2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1f2e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1d0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1f2e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x39c

    if-lt v0, v1, :cond_1f4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1f4e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ce

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1f4e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x398

    if-lt v0, v1, :cond_1f6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1f6e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1cc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1f6e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x394

    if-lt v0, v1, :cond_1f8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1f8e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ca

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1f8e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x390

    if-lt v0, v1, :cond_1fae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1fae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1fae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x38c

    if-lt v0, v1, :cond_1fce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1fce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1fce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x388

    if-lt v0, v1, :cond_1fee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_1fee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_1fee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x384

    if-lt v0, v1, :cond_200e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_200e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_200e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x380

    if-lt v0, v1, :cond_202e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_202e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1c0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_202e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x37c

    if-lt v0, v1, :cond_204e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_204e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1be

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_204e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x378

    if-lt v0, v1, :cond_206e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_206e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1bc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_206e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x374

    if-lt v0, v1, :cond_208e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_208e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ba

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_208e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x370

    if-lt v0, v1, :cond_20ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_20ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_20ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x36c

    if-lt v0, v1, :cond_20ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_20ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_20ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x368

    if-lt v0, v1, :cond_20ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_20ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_20ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x364

    if-lt v0, v1, :cond_210e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_210e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_210e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x360

    if-lt v0, v1, :cond_212e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_212e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1b0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_212e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x35c

    if-lt v0, v1, :cond_214e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_214e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ae

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_214e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x358

    if-lt v0, v1, :cond_216e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_216e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1ac

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_216e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x354

    if-lt v0, v1, :cond_218e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_218e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1aa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_218e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x350

    if-lt v0, v1, :cond_21ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_21ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_21ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x34c

    if-lt v0, v1, :cond_21ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_21ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_21ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x348

    if-lt v0, v1, :cond_21ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_21ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_21ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x344

    if-lt v0, v1, :cond_220e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_220e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_220e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x340

    if-lt v0, v1, :cond_222e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_222e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x1a0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_222e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x33c

    if-lt v0, v1, :cond_224e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_224e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_224e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x338

    if-lt v0, v1, :cond_226e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_226e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_226e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x334

    if-lt v0, v1, :cond_228e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_228e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x19a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_228e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x330

    if-lt v0, v1, :cond_22ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_22ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x198

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_22ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x32c

    if-lt v0, v1, :cond_22ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_22ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x196

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_22ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x328

    if-lt v0, v1, :cond_22ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_22ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x194

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_22ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x324

    if-lt v0, v1, :cond_230e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_230e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x192

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_230e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x320

    if-lt v0, v1, :cond_232e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_232e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_232e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x31c

    if-lt v0, v1, :cond_234e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_234e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_234e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x318

    if-lt v0, v1, :cond_236e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_236e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_236e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x314

    if-lt v0, v1, :cond_238e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_238e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x18a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_238e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x310

    if-lt v0, v1, :cond_23ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_23ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x188

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_23ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x30c

    if-lt v0, v1, :cond_23ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_23ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x186

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_23ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x308

    if-lt v0, v1, :cond_23ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_23ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x184

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_23ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x304

    if-lt v0, v1, :cond_240e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_240e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x182

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_240e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x300

    if-lt v0, v1, :cond_242e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_242e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x180

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_242e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2fc

    if-lt v0, v1, :cond_244e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_244e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x17e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_244e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2f8

    if-lt v0, v1, :cond_246e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_246e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x17c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_246e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2f4

    if-lt v0, v1, :cond_248e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_248e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x17a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_248e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2f0

    if-lt v0, v1, :cond_24ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_24ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x178

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_24ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2ec

    if-lt v0, v1, :cond_24ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_24ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x176

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_24ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2e8

    if-lt v0, v1, :cond_24ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_24ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x174

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_24ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2e4

    if-lt v0, v1, :cond_250e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_250e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x172

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_250e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2e0

    if-lt v0, v1, :cond_252e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_252e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x170

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_252e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2dc

    if-lt v0, v1, :cond_254e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_254e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x16e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_254e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2d8

    if-lt v0, v1, :cond_256e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_256e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x16c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_256e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2d4

    if-lt v0, v1, :cond_258e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_258e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x16a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_258e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2d0

    if-lt v0, v1, :cond_25ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_25ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x168

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_25ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2cc

    if-lt v0, v1, :cond_25ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_25ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x166

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_25ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2c8

    if-lt v0, v1, :cond_25ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_25ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x164

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_25ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2c4

    if-lt v0, v1, :cond_260e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_260e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x162

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_260e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2c0

    if-lt v0, v1, :cond_262e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_262e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x160

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_262e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2bc

    if-lt v0, v1, :cond_264e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_264e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x15e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_264e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2b8

    if-lt v0, v1, :cond_266e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_266e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x15c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_266e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2b4

    if-lt v0, v1, :cond_268e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_268e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x15a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_268e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2b0

    if-lt v0, v1, :cond_26ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_26ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x158

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_26ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2ac

    if-lt v0, v1, :cond_26ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_26ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x156

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_26ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2a8

    if-lt v0, v1, :cond_26ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_26ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x154

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_26ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2a4

    if-lt v0, v1, :cond_270e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_270e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x152

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_270e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x2a0

    if-lt v0, v1, :cond_272e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_272e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x150

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_272e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x29c

    if-lt v0, v1, :cond_274e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_274e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x14e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_274e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x298

    if-lt v0, v1, :cond_276e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_276e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x14c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_276e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x294

    if-lt v0, v1, :cond_278e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_278e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x14a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_278e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x290

    if-lt v0, v1, :cond_27ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_27ae

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x148

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_27ae
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x28c

    if-lt v0, v1, :cond_27ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_27ce

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x146

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_27ce
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x288

    if-lt v0, v1, :cond_27ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_27ee

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x144

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_27ee
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x284

    if-lt v0, v1, :cond_280e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_280e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x142

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_280e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x280

    if-lt v0, v1, :cond_282e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_282e

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/4 v1, 0x2

    div-int/2addr v0, v1

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    const/16 v0, 0x140

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const/high16 v0, 0x40000000    # 2.0f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_282e
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e2

    if-lt v0, v1, :cond_284c

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_284c

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1e2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_284c
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e1

    if-lt v0, v1, :cond_286c

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x140

    if-lt v0, v1, :cond_286c

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1e0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_286c
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_2893

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x147

    if-lt v0, v1, :cond_2893

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x110

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_2893
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_28ba

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x146

    if-lt v0, v1, :cond_28ba

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x10f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_28ba
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_28e1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x144

    if-lt v0, v1, :cond_28e1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x10e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_28e1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_2908

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x143

    if-lt v0, v1, :cond_2908

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x10d

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_2908
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_292f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x142

    if-lt v0, v1, :cond_292f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x10c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_292f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_2956

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x141

    if-lt v0, v1, :cond_2956

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x10b

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_2956
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1e0

    if-lt v0, v1, :cond_297d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v1, 0x140

    if-lt v0, v1, :cond_297d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    const/16 v0, 0x10a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    const v0, 0x3f99999a    # 1.2f

    iput v0, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    goto/16 :goto_11

    :cond_297d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1de

    if-lt v0, v1, :cond_299b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_299b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1de

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_299b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1dc

    if-lt v0, v1, :cond_29b9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_29b9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1dc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_29b9
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1da

    if-lt v0, v1, :cond_29d7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_29d7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1da

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_29d7
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1d8

    if-lt v0, v1, :cond_29f5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_29f5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1d8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_29f5
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1d6

    if-lt v0, v1, :cond_2a13

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2a13

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1d6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2a13
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1d4

    if-lt v0, v1, :cond_2a31

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2a31

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1d4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2a31
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1d2

    if-lt v0, v1, :cond_2a4f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2a4f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1d2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2a4f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1d0

    if-lt v0, v1, :cond_2a6d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2a6d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1d0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2a6d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1ce

    if-lt v0, v1, :cond_2a8b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2a8b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1ce

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2a8b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1cc

    if-lt v0, v1, :cond_2aa9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2aa9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1cc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2aa9
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1cb

    if-lt v0, v1, :cond_2ac7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ac7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1cb

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ac7
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1ca

    if-lt v0, v1, :cond_2ae5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ae5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1ca

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ae5
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1c8

    if-lt v0, v1, :cond_2b03

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2b03

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1c8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2b03
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1c6

    if-lt v0, v1, :cond_2b21

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2b21

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1c6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2b21
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1c4

    if-lt v0, v1, :cond_2b3f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2b3f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1c4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2b3f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1c2

    if-lt v0, v1, :cond_2b5d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2b5d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1c2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2b5d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1c0

    if-lt v0, v1, :cond_2b7b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2b7b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1c0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2b7b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1be

    if-lt v0, v1, :cond_2b99

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2b99

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1be

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2b99
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1bc

    if-lt v0, v1, :cond_2bb7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2bb7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1bc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2bb7
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1ba

    if-lt v0, v1, :cond_2bd5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2bd5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1ba

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2bd5
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1b8

    if-lt v0, v1, :cond_2bf3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2bf3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1b8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2bf3
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1b6

    if-lt v0, v1, :cond_2c11

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2c11

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1b6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2c11
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1b4

    if-lt v0, v1, :cond_2c2f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2c2f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1b4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2c2f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1b2

    if-lt v0, v1, :cond_2c4d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2c4d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1b2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2c4d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1b0

    if-lt v0, v1, :cond_2c6b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2c6b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1b0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2c6b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1ae

    if-lt v0, v1, :cond_2c89

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2c89

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1ae

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2c89
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1ac

    if-lt v0, v1, :cond_2ca7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ca7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1ac

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ca7
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1aa

    if-lt v0, v1, :cond_2cc5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2cc5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1aa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2cc5
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1a8

    if-lt v0, v1, :cond_2ce3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ce3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1a8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ce3
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1a6

    if-lt v0, v1, :cond_2d01

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2d01

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1a6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2d01
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1a4

    if-lt v0, v1, :cond_2d1f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2d1f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1a4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2d1f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1a2

    if-lt v0, v1, :cond_2d3d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2d3d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1a2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2d3d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x1a0

    if-lt v0, v1, :cond_2d5b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2d5b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x1a0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2d5b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x19e

    if-lt v0, v1, :cond_2d79

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2d79

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x19e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2d79
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x19c

    if-lt v0, v1, :cond_2d97

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2d97

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x19c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2d97
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x19a

    if-lt v0, v1, :cond_2db5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2db5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x19a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2db5
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x198

    if-lt v0, v1, :cond_2dd3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2dd3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x198

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2dd3
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x196

    if-lt v0, v1, :cond_2df1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2df1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x196

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2df1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x194

    if-lt v0, v1, :cond_2e0f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2e0f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x194

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2e0f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x192

    if-lt v0, v1, :cond_2e2d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2e2d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x192

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2e2d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x190

    if-lt v0, v1, :cond_2e4b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2e4b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x190

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2e4b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x18e

    if-lt v0, v1, :cond_2e69

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2e69

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x18e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2e69
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x18c

    if-lt v0, v1, :cond_2e87

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2e87

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x18c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2e87
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x18a

    if-lt v0, v1, :cond_2ea5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ea5

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x18a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ea5
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x188

    if-lt v0, v1, :cond_2ec3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ec3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x188

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ec3
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x186

    if-lt v0, v1, :cond_2ee1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2ee1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x186

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2ee1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x184

    if-lt v0, v1, :cond_2eff

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2eff

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x184

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2eff
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x182

    if-lt v0, v1, :cond_2f1d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2f1d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x182

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2f1d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x180

    if-lt v0, v1, :cond_2f3b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2f3b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x180

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2f3b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x17e

    if-lt v0, v1, :cond_2f59

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2f59

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x17e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2f59
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x17c

    if-lt v0, v1, :cond_2f77

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2f77

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x17c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2f77
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x17a

    if-lt v0, v1, :cond_2f95

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2f95

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x17a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2f95
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x178

    if-lt v0, v1, :cond_2fb3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2fb3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x178

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2fb3
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x176

    if-lt v0, v1, :cond_2fd1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2fd1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x176

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2fd1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x174

    if-lt v0, v1, :cond_2fef

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_2fef

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x174

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_2fef
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x172

    if-lt v0, v1, :cond_300d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_300d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x172

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_300d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x170

    if-lt v0, v1, :cond_302b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_302b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x170

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_302b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x16e

    if-lt v0, v1, :cond_3049

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3049

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x16e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3049
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x16c

    if-lt v0, v1, :cond_3067

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3067

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x16c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3067
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x16a

    if-lt v0, v1, :cond_3085

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3085

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x16a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3085
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x168

    if-lt v0, v1, :cond_30a3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_30a3

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x168

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_30a3
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x166

    if-lt v0, v1, :cond_30c1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_30c1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x166

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_30c1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x164

    if-lt v0, v1, :cond_30df

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_30df

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x164

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_30df
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x162

    if-lt v0, v1, :cond_30fd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_30fd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x162

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_30fd
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x160

    if-lt v0, v1, :cond_311b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_311b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x160

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_311b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x15e

    if-lt v0, v1, :cond_3139

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3139

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x15e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3139
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x15c

    if-lt v0, v1, :cond_3157

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3157

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x15c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3157
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x15a

    if-lt v0, v1, :cond_3175

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3175

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x15a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3175
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x158

    if-lt v0, v1, :cond_3193

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3193

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x158

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3193
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x156

    if-lt v0, v1, :cond_31b1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_31b1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x156

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_31b1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x154

    if-lt v0, v1, :cond_31cf

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_31cf

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x154

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_31cf
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x152

    if-lt v0, v1, :cond_31ed

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_31ed

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x152

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_31ed
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x150

    if-lt v0, v1, :cond_320b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_320b

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x150

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_320b
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x14e

    if-lt v0, v1, :cond_3229

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3229

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x14e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3229
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x14c

    if-lt v0, v1, :cond_3247

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3247

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x14c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3247
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x14a

    if-lt v0, v1, :cond_3265

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3265

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x14a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3265
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x148

    if-lt v0, v1, :cond_3283

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3283

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x148

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3283
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x146

    if-lt v0, v1, :cond_32a1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_32a1

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x146

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_32a1
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x144

    if-lt v0, v1, :cond_32bf

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_32bf

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x144

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_32bf
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x142

    if-lt v0, v1, :cond_32dd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_32dd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x142

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_32dd
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x140

    if-lt v0, v1, :cond_32fb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_32fb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x140

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_32fb
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x13e

    if-lt v0, v1, :cond_3319

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3319

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x13e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3319
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x13c

    if-lt v0, v1, :cond_3337

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3337

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x13c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3337
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x13a

    if-lt v0, v1, :cond_3355

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3355

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x13a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3355
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x138

    if-lt v0, v1, :cond_3373

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3373

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x138

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3373
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x136

    if-lt v0, v1, :cond_3391

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3391

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x136

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3391
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x134

    if-lt v0, v1, :cond_33af

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_33af

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x134

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_33af
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x132

    if-lt v0, v1, :cond_33cd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_33cd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x132

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_33cd
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x130

    if-lt v0, v1, :cond_33eb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_33eb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x130

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_33eb
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x12e

    if-lt v0, v1, :cond_3409

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3409

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x12e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3409
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x12c

    if-lt v0, v1, :cond_3427

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3427

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x12c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3427
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x12a

    if-lt v0, v1, :cond_3445

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3445

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x12a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3445
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x128

    if-lt v0, v1, :cond_3463

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3463

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x128

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3463
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x126

    if-lt v0, v1, :cond_3481

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3481

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x126

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3481
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x124

    if-lt v0, v1, :cond_349f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_349f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x124

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_349f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x122

    if-lt v0, v1, :cond_34bd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_34bd

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x122

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_34bd
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x120

    if-lt v0, v1, :cond_34db

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_34db

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x120

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_34db
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x11e

    if-lt v0, v1, :cond_34f9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_34f9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x11e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_34f9
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x11c

    if-lt v0, v1, :cond_3517

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3517

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x11c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3517
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x11a

    if-lt v0, v1, :cond_3535

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3535

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x11a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3535
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x118

    if-lt v0, v1, :cond_3553

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3553

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x118

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3553
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x116

    if-lt v0, v1, :cond_3571

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3571

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x116

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3571
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x114

    if-lt v0, v1, :cond_358f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_358f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x114

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_358f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x112

    if-lt v0, v1, :cond_35ad

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_35ad

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x112

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_35ad
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x110

    if-lt v0, v1, :cond_35cb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_35cb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x110

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_35cb
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x10e

    if-lt v0, v1, :cond_35e9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_35e9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x10e

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_35e9
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x10c

    if-lt v0, v1, :cond_3607

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3607

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x10c

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3607
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x10a

    if-lt v0, v1, :cond_3625

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3625

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x10a

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3625
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x108

    if-lt v0, v1, :cond_3643

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3643

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x108

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3643
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x106

    if-lt v0, v1, :cond_3661

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3661

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x106

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3661
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x104

    if-lt v0, v1, :cond_367f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_367f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x104

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_367f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x102

    if-lt v0, v1, :cond_369d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_369d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x102

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_369d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0x100

    if-lt v0, v1, :cond_36bb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_36bb

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0x100

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_36bb
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xfe

    if-lt v0, v1, :cond_36d9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_36d9

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xfe

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_36d9
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xfc

    if-lt v0, v1, :cond_36f7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_36f7

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xfc

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_36f7
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xfa

    if-lt v0, v1, :cond_3715

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3715

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xfa

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3715
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xf8

    if-lt v0, v1, :cond_3733

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3733

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xf8

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3733
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xf6

    if-lt v0, v1, :cond_3751

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_3751

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xf6

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_3751
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xf4

    if-lt v0, v1, :cond_376f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_376f

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xf4

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_376f
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xf2

    if-lt v0, v1, :cond_378d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_378d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xf2

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_378d
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v1, 0xf0

    if-lt v0, v1, :cond_37ab

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v4, :cond_37ab

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    const/16 v0, 0xf0

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11

    :cond_37ab
    iget v0, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    if-lt v0, v6, :cond_11

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    if-lt v0, v3, :cond_11

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iget v0, p0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    iput v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    goto/16 :goto_11
.end method

.method public doFocusGained()V
    .registers 2

    iget-boolean v0, p0, Lco/kr/eamobile/CletEntry;->mStarted:Z

    if-nez v0, :cond_7

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativeFocusGained()V

    :cond_7
    return-void
.end method

.method public doFocusLost()V
    .registers 1

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativeFocusLost()V

    return-void
.end method

.method public onDestroy()V
    .registers 3

    const/4 v1, 0x0

    iget-boolean v0, p0, Lco/kr/eamobile/CletEntry;->mDestroyed:Z

    if-nez v0, :cond_37

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativeDestroyApp()V

    invoke-static {}, Lco/kr/eamobile/resource/MidasAssetManager;->destroyAssetManager()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidAssetManager:Lco/kr/eamobile/resource/MidasAssetManager;

    invoke-static {}, Lco/kr/eamobile/resource/MidasStorageManager;->destroyStorageManager()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-static {}, Lco/kr/eamobile/audio/MidasMediaManager;->destroyMediaManager()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidMediaManager:Lco/kr/eamobile/audio/MidasMediaManager;

    invoke-static {}, Lco/kr/eamobile/device/MidasVibrator;->destroyVibrator()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidVibrator:Lco/kr/eamobile/device/MidasVibrator;

    invoke-static {}, Lco/kr/eamobile/audio/MidasMusicManager;->destroyMusicManager()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidMusicManager:Lco/kr/eamobile/audio/MidasMusicManager;

    invoke-static {}, Lco/kr/eamobile/audio/MidasSoundManager;->destroySoundManager()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidSoundManager:Lco/kr/eamobile/audio/MidasSoundManager;

    invoke-static {}, Lco/kr/eamobile/device/MidasDeviceInfo;->destroyDeviceInfo()V

    sput-object v1, Lco/kr/eamobile/CletEntry;->androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

    iput-object v1, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    iput-object v1, v0, Lco/kr/eamobile/MidasActivity;->handler:Landroid/os/Handler;

    const/4 v0, 0x1

    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->mDestroyed:Z

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->finalizeEnvironmentValues()V

    :cond_37
    return-void
.end method

.method public onDrawFrame(Ljavax/microedition/khronos/opengles/GL10;)V
    .registers 3

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativeLoop()V

    invoke-static {}, Ljava/lang/Thread;->yield()V

    iget-boolean v0, p0, Lco/kr/eamobile/CletEntry;->needFinish:Z

    if-eqz v0, :cond_15

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativeDestroyTexture()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lco/kr/eamobile/CletEntry;->needFinish:Z

    sget-object v0, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v0}, Lco/kr/eamobile/MidasActivity;->finish()V

    :cond_15
    return-void
.end method

.method public onKeyHandleEvent(ILandroid/view/KeyEvent;)V
    .registers 8

    const/4 v4, 0x0

    const/4 v3, 0x3

    const/4 v2, 0x1

    invoke-virtual {p2}, Landroid/view/KeyEvent;->getAction()I

    move-result v0

    if-nez v0, :cond_2f

    const/16 v0, 0x18

    if-ne p1, v0, :cond_17

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    if-eqz v0, :cond_16

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    invoke-virtual {v0, v3, v2, v2}, Landroid/media/AudioManager;->adjustStreamVolume(III)V

    :cond_16
    :goto_16
    return-void

    :cond_17
    const/16 v0, 0x19

    if-ne p1, v0, :cond_26

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    if-eqz v0, :cond_16

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, -0x1

    invoke-virtual {v0, v3, v1, v2}, Landroid/media/AudioManager;->adjustStreamVolume(III)V

    goto :goto_16

    :cond_26
    const/4 v0, 0x2

    invoke-static {p1}, Lco/kr/eamobile/constant/WIPIConstant;->getWIPIKeyCode(I)I

    move-result v1

    invoke-direct {p0, v0, v1, v4}, Lco/kr/eamobile/CletEntry;->nativeKeyHandleEvent(III)V

    goto :goto_16

    :cond_2f
    if-ne v0, v2, :cond_16

    invoke-static {p1}, Lco/kr/eamobile/constant/WIPIConstant;->getWIPIKeyCode(I)I

    move-result v0

    invoke-direct {p0, v3, v0, v4}, Lco/kr/eamobile/CletEntry;->nativeKeyHandleEvent(III)V

    goto :goto_16
.end method

.method public onSurfaceChanged(Ljavax/microedition/khronos/opengles/GL10;II)V
    .registers 4

    return-void
.end method

.method public onSurfaceCreated(Ljavax/microedition/khronos/opengles/GL10;Ljavax/microedition/khronos/egl/EGLConfig;)V
    .registers 23

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lco/kr/eamobile/CletEntry;->mStarted:Z

    move v3, v0

    if-eqz v3, :cond_e5

    const/4 v3, 0x0

    move v0, v3

    move-object/from16 v1, p0

    iput-boolean v0, v1, Lco/kr/eamobile/CletEntry;->mStarted:Z

    sget-object v3, Lco/kr/eamobile/CletEntry;->androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

    const/16 v4, 0x1f02

    move-object/from16 v0, p1

    move v1, v4

    invoke-interface {v0, v1}, Ljavax/microedition/khronos/opengles/GL10;->glGetString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Lco/kr/eamobile/device/MidasDeviceInfo;->setOpenGLESMaxSupportedVersion(Ljava/lang/String;)V

    sget-object v3, Lco/kr/eamobile/CletEntry;->androidDeviceInfo:Lco/kr/eamobile/device/MidasDeviceInfo;

    const/16 v4, 0x1f03

    move-object/from16 v0, p1

    move v1, v4

    invoke-interface {v0, v1}, Ljavax/microedition/khronos/opengles/GL10;->glGetString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Lco/kr/eamobile/device/MidasDeviceInfo;->setOpenGLESExt(Ljava/lang/String;)V

    sget-object v3, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v3}, Lco/kr/eamobile/MidasActivity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object v3

    invoke-interface {v3}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v3

    invoke-virtual {v3}, Landroid/view/Display;->getWidth()I

    move-result v3

    move v0, v3

    move-object/from16 v1, p0

    iput v0, v1, Lco/kr/eamobile/CletEntry;->screenWidth:I

    sget-object v3, Lco/kr/eamobile/CletEntry;->mActivityRef:Lco/kr/eamobile/MidasActivity;

    invoke-virtual {v3}, Lco/kr/eamobile/MidasActivity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object v3

    invoke-interface {v3}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v3

    invoke-virtual {v3}, Landroid/view/Display;->getHeight()I

    move-result v3

    move v0, v3

    move-object/from16 v1, p0

    iput v0, v1, Lco/kr/eamobile/CletEntry;->screenHeight:I

    invoke-virtual/range {p0 .. p0}, Lco/kr/eamobile/CletEntry;->adjustAutoScreen()V

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->screenWidth:I

    move v4, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->screenHeight:I

    move v5, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->red:I

    move v6, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->green:I

    move v7, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->blue:I

    move v8, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->alpha:I

    move v9, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    move v10, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    move v11, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    move v12, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    move v13, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->wipiColorType:I

    move v14, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    move v15, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    move/from16 v16, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    move/from16 v17, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->bUseSublayer:I

    move/from16 v18, v0

    move-object/from16 v0, p0

    iget v0, v0, Lco/kr/eamobile/CletEntry;->sublayerPixelType:I

    move/from16 v19, v0

    move-object/from16 v3, p0

    invoke-direct/range {v3 .. v19}, Lco/kr/eamobile/CletEntry;->nativeInitDisplay(IIIIIIIIIIIFIIII)I

    move-result v3

    move v0, v3

    move-object/from16 v1, p0

    iput v0, v1, Lco/kr/eamobile/CletEntry;->autoMarginY:I

    sget-object v3, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-virtual {v3}, Lco/kr/eamobile/resource/MidasStorageManager;->getInternalStorageLocation()Ljava/lang/String;

    move-result-object v3

    sget-object v4, Lco/kr/eamobile/CletEntry;->androidStorageManager:Lco/kr/eamobile/resource/MidasStorageManager;

    invoke-virtual {v4}, Lco/kr/eamobile/resource/MidasStorageManager;->getExternalStorageLocation()Ljava/lang/String;

    move-result-object v4

    move-object/from16 v0, p0

    move-object v1, v3

    move-object v2, v4

    invoke-direct {v0, v1, v2}, Lco/kr/eamobile/CletEntry;->nativeInitFileStorage(Ljava/lang/String;Ljava/lang/String;)V

    move-object/from16 v0, p0

    iget-object v0, v0, Lco/kr/eamobile/CletEntry;->vfsFD:Ljava/io/FileDescriptor;

    move-object v4, v0

    move-object/from16 v0, p0

    iget-wide v0, v0, Lco/kr/eamobile/CletEntry;->vfsStartOffset:J

    move-wide v5, v0

    move-object/from16 v0, p0

    iget-wide v0, v0, Lco/kr/eamobile/CletEntry;->vfsLength:J

    move-wide v7, v0

    move-object/from16 v3, p0

    invoke-direct/range {v3 .. v8}, Lco/kr/eamobile/CletEntry;->nativeInitVFS(Ljava/io/FileDescriptor;JJ)V

    const-wide/32 v3, 0x316a0

    move-object/from16 v0, p0

    move-wide v1, v3

    invoke-direct {v0, v1, v2}, Lco/kr/eamobile/CletEntry;->nativeInitAudioConfiguration(J)V

    invoke-direct/range {p0 .. p0}, Lco/kr/eamobile/CletEntry;->nativeStartApp()V

    :goto_e4
    return-void

    :cond_e5
    invoke-direct/range {p0 .. p0}, Lco/kr/eamobile/CletEntry;->nativeResumeApp()V

    goto :goto_e4
.end method

.method public onTouchHandleEvent(Landroid/view/MotionEvent;)V
    .registers 11

    const/16 v8, 0x13

    const-wide/16 v6, 0x20

    const/4 v2, 0x2

    const/4 v5, 0x1

    const/4 v4, 0x0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    iget-boolean v1, p0, Lco/kr/eamobile/CletEntry;->bDecidedTouchType:Z

    if-nez v1, :cond_10

    :cond_f
    :goto_f
    return-void

    :cond_10
    iget-boolean v1, p0, Lco/kr/eamobile/CletEntry;->bSupportMultitouch:Z

    if-eqz v1, :cond_10c

    and-int/lit16 v1, v0, 0xff

    packed-switch v1, :pswitch_data_18e

    :pswitch_19
    goto :goto_f

    :pswitch_1a
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getPointerCount()I

    move-result v1

    if-gt v1, v2, :cond_f

    const v1, 0xff00

    and-int/2addr v0, v1

    shr-int/lit8 v0, v0, 0x8

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result v1

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v2

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    int-to-float v3, v3

    sub-float/2addr v2, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v2, v3

    float-to-int v2, v2

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result v0

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    int-to-float v3, v3

    sub-float/2addr v0, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->autoMarginY:I

    int-to-float v3, v3

    sub-float/2addr v0, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v0, v3

    float-to-int v0, v0

    if-gez v2, :cond_49

    move v2, v4

    :cond_49
    if-gez v0, :cond_4c

    move v0, v4

    :cond_4c
    iget v3, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    if-lt v2, v3, :cond_53

    iget v2, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    sub-int/2addr v2, v5

    :cond_53
    iget v3, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    if-lt v0, v3, :cond_5a

    iget v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    sub-int/2addr v0, v5

    :cond_5a
    invoke-direct {p0, v8, v2, v0, v1}, Lco/kr/eamobile/CletEntry;->nativeTouchHandleEvent(IIII)V

    goto :goto_f

    :pswitch_5e
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getPointerCount()I

    move-result v0

    if-le v0, v2, :cond_65

    move v0, v2

    :cond_65
    move v1, v4

    :goto_66
    if-ge v1, v0, :cond_b0

    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result v2

    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getX(I)F

    move-result v3

    iget v4, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    int-to-float v4, v4

    sub-float/2addr v3, v4

    iget v4, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v3, v4

    float-to-int v3, v3

    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getY(I)F

    move-result v4

    iget v5, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    int-to-float v5, v5

    sub-float/2addr v4, v5

    iget v5, p0, Lco/kr/eamobile/CletEntry;->autoMarginY:I

    int-to-float v5, v5

    sub-float/2addr v4, v5

    iget v5, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v4, v5

    float-to-int v4, v4

    if-ltz v3, :cond_ad

    if-ltz v4, :cond_ad

    iget v5, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    if-ge v3, v5, :cond_ad

    iget v5, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    if-ge v4, v5, :cond_ad

    iget-object v5, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    aget v5, v5, v2

    if-eq v5, v3, :cond_ad

    iget-object v5, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    aget v5, v5, v2

    if-eq v5, v4, :cond_ad

    const/16 v5, 0x15

    invoke-direct {p0, v5, v3, v4, v2}, Lco/kr/eamobile/CletEntry;->nativeTouchHandleEvent(IIII)V

    iget-object v5, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    aput v3, v5, v2

    iget-object v3, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    aput v4, v3, v2

    :cond_ad
    add-int/lit8 v1, v1, 0x1

    goto :goto_66

    :cond_b0
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-wide v2, p0, Lco/kr/eamobile/CletEntry;->mLastTouchTime:J

    sub-long v2, v0, v2

    cmp-long v2, v2, v6

    if-gez v2, :cond_c1

    const-wide/16 v2, 0x20

    :try_start_be
    invoke-static {v2, v3}, Ljava/lang/Thread;->sleep(J)V
    :try_end_c1
    .catch Ljava/lang/InterruptedException; {:try_start_be .. :try_end_c1} :catch_189

    :cond_c1
    :goto_c1
    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->mLastTouchTime:J

    goto/16 :goto_f

    :pswitch_c5
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getPointerCount()I

    move-result v1

    if-gt v1, v2, :cond_f

    const v1, 0xff00

    and-int/2addr v0, v1

    shr-int/lit8 v0, v0, 0x8

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result v1

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v2

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    int-to-float v3, v3

    sub-float/2addr v2, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v2, v3

    float-to-int v2, v2

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result v0

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    int-to-float v3, v3

    sub-float/2addr v0, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->autoMarginY:I

    int-to-float v3, v3

    sub-float/2addr v0, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v0, v3

    float-to-int v0, v0

    if-gez v2, :cond_f4

    move v2, v4

    :cond_f4
    if-gez v0, :cond_f7

    move v0, v4

    :cond_f7
    iget v3, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    if-lt v2, v3, :cond_fe

    iget v2, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    sub-int/2addr v2, v5

    :cond_fe
    iget v3, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    if-lt v0, v3, :cond_105

    iget v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    sub-int/2addr v0, v5

    :cond_105
    const/16 v3, 0x14

    invoke-direct {p0, v3, v2, v0, v1}, Lco/kr/eamobile/CletEntry;->nativeTouchHandleEvent(IIII)V

    goto/16 :goto_f

    :cond_10c
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v1

    iget v2, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    int-to-float v2, v2

    sub-float/2addr v1, v2

    iget v2, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v1, v2

    float-to-int v1, v1

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v2

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    int-to-float v3, v3

    sub-float/2addr v2, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->autoMarginY:I

    int-to-float v3, v3

    sub-float/2addr v2, v3

    iget v3, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    div-float/2addr v2, v3

    float-to-int v2, v2

    if-gez v1, :cond_12b

    move v1, v4

    :cond_12b
    if-gez v2, :cond_12e

    move v2, v4

    :cond_12e
    iget v3, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    if-lt v1, v3, :cond_135

    iget v1, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    sub-int/2addr v1, v5

    :cond_135
    iget v3, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    if-lt v2, v3, :cond_13c

    iget v2, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    sub-int/2addr v2, v5

    :cond_13c
    and-int/lit16 v0, v0, 0xff

    packed-switch v0, :pswitch_data_1a0

    goto/16 :goto_f

    :pswitch_143
    invoke-direct {p0, v8, v1, v2, v4}, Lco/kr/eamobile/CletEntry;->nativeTouchHandleEvent(IIII)V

    goto/16 :goto_f

    :pswitch_148
    if-ltz v1, :cond_16d

    if-ltz v2, :cond_16d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    if-ge v1, v0, :cond_16d

    iget v0, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    if-ge v2, v0, :cond_16d

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    aget v0, v0, v4

    if-eq v0, v1, :cond_16d

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    aget v0, v0, v4

    if-eq v0, v2, :cond_16d

    const/16 v0, 0x15

    invoke-direct {p0, v0, v1, v2, v4}, Lco/kr/eamobile/CletEntry;->nativeTouchHandleEvent(IIII)V

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtX:[I

    aput v1, v0, v4

    iget-object v0, p0, Lco/kr/eamobile/CletEntry;->lastActionMoveEvtY:[I

    aput v2, v0, v4

    :cond_16d
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    iget-wide v2, p0, Lco/kr/eamobile/CletEntry;->mLastTouchTime:J

    sub-long v2, v0, v2

    cmp-long v2, v2, v6

    if-gez v2, :cond_17e

    const-wide/16 v2, 0x20

    :try_start_17b
    invoke-static {v2, v3}, Ljava/lang/Thread;->sleep(J)V
    :try_end_17e
    .catch Ljava/lang/InterruptedException; {:try_start_17b .. :try_end_17e} :catch_18c

    :cond_17e
    :goto_17e
    iput-wide v0, p0, Lco/kr/eamobile/CletEntry;->mLastTouchTime:J

    goto/16 :goto_f

    :pswitch_182
    const/16 v0, 0x14

    invoke-direct {p0, v0, v1, v2, v4}, Lco/kr/eamobile/CletEntry;->nativeTouchHandleEvent(IIII)V

    goto/16 :goto_f

    :catch_189
    move-exception v2

    goto/16 :goto_c1

    :catch_18c
    move-exception v2

    goto :goto_17e

    :pswitch_data_18e
    .packed-switch 0x0
        :pswitch_1a
        :pswitch_c5
        :pswitch_5e
        :pswitch_c5
        :pswitch_19
        :pswitch_1a
        :pswitch_c5
    .end packed-switch

    :pswitch_data_1a0
    .packed-switch 0x0
        :pswitch_143
        :pswitch_182
        :pswitch_148
        :pswitch_182
    .end packed-switch
.end method

.method public setDisplayInfo(IIII)V
    .registers 5

    iput p1, p0, Lco/kr/eamobile/CletEntry;->red:I

    iput p2, p0, Lco/kr/eamobile/CletEntry;->green:I

    iput p3, p0, Lco/kr/eamobile/CletEntry;->blue:I

    iput p4, p0, Lco/kr/eamobile/CletEntry;->alpha:I

    return-void
.end method

.method public setSublayerInfo(II)V
    .registers 3

    iput p1, p0, Lco/kr/eamobile/CletEntry;->bUseSublayer:I

    iput p2, p0, Lco/kr/eamobile/CletEntry;->sublayerPixelType:I

    return-void
.end method

.method public setTargetViewInfo(FII)V
    .registers 4

    iput p1, p0, Lco/kr/eamobile/CletEntry;->targetMagnification:F

    iput p2, p0, Lco/kr/eamobile/CletEntry;->targetMagFilterType:I

    iput p3, p0, Lco/kr/eamobile/CletEntry;->targetViewMode:I

    return-void
.end method

.method public setWIPIDisplayInfo(IIIII)V
    .registers 6

    iput p1, p0, Lco/kr/eamobile/CletEntry;->wipiLCDWidth:I

    iput p2, p0, Lco/kr/eamobile/CletEntry;->wipiLCDHeight:I

    iput p3, p0, Lco/kr/eamobile/CletEntry;->targetMarginX:I

    iput p4, p0, Lco/kr/eamobile/CletEntry;->targetMarginY:I

    iput p5, p0, Lco/kr/eamobile/CletEntry;->wipiColorType:I

    return-void
.end method

.method public surfaceDestroyed()V
    .registers 2

    iget-boolean v0, p0, Lco/kr/eamobile/CletEntry;->mDestroyed:Z

    if-nez v0, :cond_7

    invoke-direct {p0}, Lco/kr/eamobile/CletEntry;->nativePauseApp()V

    :cond_7
    return-void
.end method
