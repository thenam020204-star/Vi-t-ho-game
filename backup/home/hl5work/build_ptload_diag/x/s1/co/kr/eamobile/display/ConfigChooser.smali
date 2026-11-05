.class public Lco/kr/eamobile/display/ConfigChooser;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/opengl/GLSurfaceView$EGLConfigChooser;


# static fields
.field private static final DEBUG:Z = false

.field private static EGL_OPENGL_ES2_BIT:I = 0x0

.field private static final TAG:Ljava/lang/String; = "ConfigChooser"

.field private static s_configAttribs2:[I


# instance fields
.field protected mAlphaSize:I

.field protected mBlueSize:I

.field protected mDepthSize:I

.field protected mGreenSize:I

.field protected mRedSize:I

.field protected mStencilSize:I

.field private mValue:[I


# direct methods
.method static constructor <clinit>()V
    .registers 3

    const/4 v0, 0x4

    sput v0, Lco/kr/eamobile/display/ConfigChooser;->EGL_OPENGL_ES2_BIT:I

    const/4 v0, 0x1

    new-array v0, v0, [I

    const/4 v1, 0x0

    const/16 v2, 0x3038

    aput v2, v0, v1

    sput-object v0, Lco/kr/eamobile/display/ConfigChooser;->s_configAttribs2:[I

    return-void
.end method

.method public constructor <init>(IIIIII)V
    .registers 8

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x1

    new-array v0, v0, [I

    iput-object v0, p0, Lco/kr/eamobile/display/ConfigChooser;->mValue:[I

    iput p1, p0, Lco/kr/eamobile/display/ConfigChooser;->mRedSize:I

    iput p2, p0, Lco/kr/eamobile/display/ConfigChooser;->mGreenSize:I

    iput p3, p0, Lco/kr/eamobile/display/ConfigChooser;->mBlueSize:I

    iput p4, p0, Lco/kr/eamobile/display/ConfigChooser;->mAlphaSize:I

    iput p5, p0, Lco/kr/eamobile/display/ConfigChooser;->mDepthSize:I

    iput p6, p0, Lco/kr/eamobile/display/ConfigChooser;->mStencilSize:I

    return-void
.end method

.method private findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I
    .registers 8

    iget-object v0, p0, Lco/kr/eamobile/display/ConfigChooser;->mValue:[I

    invoke-interface {p1, p2, p3, p4, v0}, Ljavax/microedition/khronos/egl/EGL10;->eglGetConfigAttrib(Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;I[I)Z

    move-result v0

    if-eqz v0, :cond_e

    iget-object v0, p0, Lco/kr/eamobile/display/ConfigChooser;->mValue:[I

    const/4 v1, 0x0

    aget v0, v0, v1

    :goto_d
    return v0

    :cond_e
    move v0, p5

    goto :goto_d
.end method

.method private printConfig(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;)V
    .registers 15

    const/16 v1, 0x21

    const/4 v10, 0x2

    const/4 v9, 0x1

    const/4 v8, 0x0

    new-array v0, v1, [I

    fill-array-data v0, :array_fc

    new-array v1, v1, [Ljava/lang/String;

    const-string v2, "EGL_BUFFER_SIZE"

    aput-object v2, v1, v8

    const-string v2, "EGL_ALPHA_SIZE"

    aput-object v2, v1, v9

    const-string v2, "EGL_BLUE_SIZE"

    aput-object v2, v1, v10

    const/4 v2, 0x3

    const-string v3, "EGL_GREEN_SIZE"

    aput-object v3, v1, v2

    const/4 v2, 0x4

    const-string v3, "EGL_RED_SIZE"

    aput-object v3, v1, v2

    const/4 v2, 0x5

    const-string v3, "EGL_DEPTH_SIZE"

    aput-object v3, v1, v2

    const/4 v2, 0x6

    const-string v3, "EGL_STENCIL_SIZE"

    aput-object v3, v1, v2

    const/4 v2, 0x7

    const-string v3, "EGL_CONFIG_CAVEAT"

    aput-object v3, v1, v2

    const/16 v2, 0x8

    const-string v3, "EGL_CONFIG_ID"

    aput-object v3, v1, v2

    const/16 v2, 0x9

    const-string v3, "EGL_LEVEL"

    aput-object v3, v1, v2

    const/16 v2, 0xa

    const-string v3, "EGL_MAX_PBUFFER_HEIGHT"

    aput-object v3, v1, v2

    const/16 v2, 0xb

    const-string v3, "EGL_MAX_PBUFFER_PIXELS"

    aput-object v3, v1, v2

    const/16 v2, 0xc

    const-string v3, "EGL_MAX_PBUFFER_WIDTH"

    aput-object v3, v1, v2

    const/16 v2, 0xd

    const-string v3, "EGL_NATIVE_RENDERABLE"

    aput-object v3, v1, v2

    const/16 v2, 0xe

    const-string v3, "EGL_NATIVE_VISUAL_ID"

    aput-object v3, v1, v2

    const/16 v2, 0xf

    const-string v3, "EGL_NATIVE_VISUAL_TYPE"

    aput-object v3, v1, v2

    const/16 v2, 0x10

    const-string v3, "EGL_PRESERVED_RESOURCES"

    aput-object v3, v1, v2

    const/16 v2, 0x11

    const-string v3, "EGL_SAMPLES"

    aput-object v3, v1, v2

    const/16 v2, 0x12

    const-string v3, "EGL_SAMPLE_BUFFERS"

    aput-object v3, v1, v2

    const/16 v2, 0x13

    const-string v3, "EGL_SURFACE_TYPE"

    aput-object v3, v1, v2

    const/16 v2, 0x14

    const-string v3, "EGL_TRANSPARENT_TYPE"

    aput-object v3, v1, v2

    const/16 v2, 0x15

    const-string v3, "EGL_TRANSPARENT_RED_VALUE"

    aput-object v3, v1, v2

    const/16 v2, 0x16

    const-string v3, "EGL_TRANSPARENT_GREEN_VALUE"

    aput-object v3, v1, v2

    const/16 v2, 0x17

    const-string v3, "EGL_TRANSPARENT_BLUE_VALUE"

    aput-object v3, v1, v2

    const/16 v2, 0x18

    const-string v3, "EGL_BIND_TO_TEXTURE_RGB"

    aput-object v3, v1, v2

    const/16 v2, 0x19

    const-string v3, "EGL_BIND_TO_TEXTURE_RGBA"

    aput-object v3, v1, v2

    const/16 v2, 0x1a

    const-string v3, "EGL_MIN_SWAP_INTERVAL"

    aput-object v3, v1, v2

    const/16 v2, 0x1b

    const-string v3, "EGL_MAX_SWAP_INTERVAL"

    aput-object v3, v1, v2

    const/16 v2, 0x1c

    const-string v3, "EGL_LUMINANCE_SIZE"

    aput-object v3, v1, v2

    const/16 v2, 0x1d

    const-string v3, "EGL_ALPHA_MASK_SIZE"

    aput-object v3, v1, v2

    const/16 v2, 0x1e

    const-string v3, "EGL_COLOR_BUFFER_TYPE"

    aput-object v3, v1, v2

    const/16 v2, 0x1f

    const-string v3, "EGL_RENDERABLE_TYPE"

    aput-object v3, v1, v2

    const/16 v2, 0x20

    const-string v3, "EGL_CONFORMANT"

    aput-object v3, v1, v2

    new-array v2, v9, [I

    move v3, v8

    :goto_ca
    array-length v4, v0

    if-ge v3, v4, :cond_fa

    aget v4, v0, v3

    aget-object v5, v1, v3

    invoke-interface {p1, p2, p3, v4, v2}, Ljavax/microedition/khronos/egl/EGL10;->eglGetConfigAttrib(Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;I[I)Z

    move-result v4

    if-eqz v4, :cond_f1

    const-string v4, "ConfigChooser"

    const-string v6, "  %s: %d\n"

    new-array v7, v10, [Ljava/lang/Object;

    aput-object v5, v7, v8

    aget v5, v2, v8

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :cond_ee
    add-int/lit8 v3, v3, 0x1

    goto :goto_ca

    :cond_f1
    :goto_f1
    invoke-interface {p1}, Ljavax/microedition/khronos/egl/EGL10;->eglGetError()I

    move-result v4

    const/16 v5, 0x3000

    if-eq v4, v5, :cond_ee

    goto :goto_f1

    :cond_fa
    return-void

    nop

    :array_fc
    .array-data 4
        0x3020
        0x3021
        0x3022
        0x3023
        0x3024
        0x3025
        0x3026
        0x3027
        0x3028
        0x3029
        0x302a
        0x302b
        0x302c
        0x302d
        0x302e
        0x302f
        0x3030
        0x3031
        0x3032
        0x3033
        0x3034
        0x3037
        0x3036
        0x3035
        0x3039
        0x303a
        0x303b
        0x303c
        0x303d
        0x303e
        0x303f
        0x3040
        0x3042
    .end array-data
.end method

.method private printConfigs(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;[Ljavax/microedition/khronos/egl/EGLConfig;)V
    .registers 12

    const/4 v6, 0x1

    const/4 v5, 0x0

    const-string v7, "ConfigChooser"

    array-length v0, p3

    const-string v1, "ConfigChooser"

    const-string v1, "%d configurations"

    new-array v2, v6, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v2, v5

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v7, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    move v1, v5

    :goto_19
    if-ge v1, v0, :cond_36

    const-string v2, "ConfigChooser"

    const-string v2, "Configuration %d:\n"

    new-array v3, v6, [Ljava/lang/Object;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    aput-object v4, v3, v5

    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v7, v2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    aget-object v2, p3, v1

    invoke-direct {p0, p1, p2, v2}, Lco/kr/eamobile/display/ConfigChooser;->printConfig(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;)V

    add-int/lit8 v1, v1, 0x1

    goto :goto_19

    :cond_36
    return-void
.end method


# virtual methods
.method public chooseConfig(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;)Ljavax/microedition/khronos/egl/EGLConfig;
    .registers 9

    const/4 v4, 0x0

    const/4 v0, 0x1

    new-array v5, v0, [I

    sget-object v2, Lco/kr/eamobile/display/ConfigChooser;->s_configAttribs2:[I

    const/4 v3, 0x0

    move-object v0, p1

    move-object v1, p2

    invoke-interface/range {v0 .. v5}, Ljavax/microedition/khronos/egl/EGL10;->eglChooseConfig(Ljavax/microedition/khronos/egl/EGLDisplay;[I[Ljavax/microedition/khronos/egl/EGLConfig;I[I)Z

    aget v4, v5, v4

    if-gtz v4, :cond_18

    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "No configs match configSpec"

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    :cond_18
    new-array v3, v4, [Ljavax/microedition/khronos/egl/EGLConfig;

    sget-object v2, Lco/kr/eamobile/display/ConfigChooser;->s_configAttribs2:[I

    move-object v0, p1

    move-object v1, p2

    invoke-interface/range {v0 .. v5}, Ljavax/microedition/khronos/egl/EGL10;->eglChooseConfig(Ljavax/microedition/khronos/egl/EGLDisplay;[I[Ljavax/microedition/khronos/egl/EGLConfig;I[I)Z

    invoke-virtual {p0, p1, p2, v3}, Lco/kr/eamobile/display/ConfigChooser;->chooseConfig(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;[Ljavax/microedition/khronos/egl/EGLConfig;)Ljavax/microedition/khronos/egl/EGLConfig;

    move-result-object v0

    return-object v0
.end method

.method public chooseConfig(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;[Ljavax/microedition/khronos/egl/EGLConfig;)Ljavax/microedition/khronos/egl/EGLConfig;
    .registers 15

    const/4 v5, 0x0

    array-length v6, p3

    move v7, v5

    :goto_3
    if-ge v7, v6, :cond_5b

    aget-object v3, p3, v7

    const/16 v4, 0x3025

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/display/ConfigChooser;->findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I

    move-result v8

    const/16 v4, 0x3026

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/display/ConfigChooser;->findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I

    move-result v0

    iget v1, p0, Lco/kr/eamobile/display/ConfigChooser;->mDepthSize:I

    if-lt v8, v1, :cond_21

    iget v1, p0, Lco/kr/eamobile/display/ConfigChooser;->mStencilSize:I

    if-ge v0, v1, :cond_25

    :cond_21
    add-int/lit8 v0, v7, 0x1

    move v7, v0

    goto :goto_3

    :cond_25
    const/16 v4, 0x3024

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/display/ConfigChooser;->findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I

    move-result v8

    const/16 v4, 0x3023

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/display/ConfigChooser;->findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I

    move-result v9

    const/16 v4, 0x3022

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/display/ConfigChooser;->findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I

    move-result v10

    const/16 v4, 0x3021

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Lco/kr/eamobile/display/ConfigChooser;->findConfigAttrib(Ljavax/microedition/khronos/egl/EGL10;Ljavax/microedition/khronos/egl/EGLDisplay;Ljavax/microedition/khronos/egl/EGLConfig;II)I

    move-result v0

    iget v1, p0, Lco/kr/eamobile/display/ConfigChooser;->mRedSize:I

    if-ne v8, v1, :cond_21

    iget v1, p0, Lco/kr/eamobile/display/ConfigChooser;->mGreenSize:I

    if-ne v9, v1, :cond_21

    iget v1, p0, Lco/kr/eamobile/display/ConfigChooser;->mBlueSize:I

    if-ne v10, v1, :cond_21

    iget v1, p0, Lco/kr/eamobile/display/ConfigChooser;->mAlphaSize:I

    if-ne v0, v1, :cond_21

    move-object v0, v3

    :goto_5a
    return-object v0

    :cond_5b
    const/4 v0, 0x0

    goto :goto_5a
.end method
