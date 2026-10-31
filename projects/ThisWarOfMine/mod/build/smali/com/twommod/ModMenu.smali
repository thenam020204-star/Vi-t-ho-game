.class public final Lcom/twommod/ModMenu;
.super Ljava/lang/Object;
.source "ModMenu.java"


# static fields
.field static final OPT_DAYSPEED:I = 0x5

.field static final OPT_GOD:I = 0x0

.field static final OPT_INFBAG:I = 0x1

.field static final OPT_INSTANT:I = 0x4

.field static final OPT_LOOTMUL:I = 0x3

.field static final OPT_STACK99:I = 0x2

.field static final SPEED_TXT:[Ljava/lang/String;

.field private static loaded:Z

.field private static prefs:Landroid/content/SharedPreferences;


# direct methods
.method static constructor <clinit>()V
    .registers 9

    const-string v0, "x0.25"

    const-string v1, "x0.33"

    const-string v2, "x0.5"

    const-string v3, "x0.75"

    const-string v4, "x1 (g\u1ed1c)"

    const-string v5, "x1.5"

    const-string v6, "x2"

    const-string v7, "x3"

    const-string v8, "x4"

    .line 25
    filled-new-array/range {v0 .. v8}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/twommod/ModMenu;->SPEED_TXT:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .line 22
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()Landroid/content/SharedPreferences;
    .registers 1

    .line 22
    sget-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$100(Landroid/widget/LinearLayout;)V
    .registers 1

    .line 22
    invoke-static {p0}, Lcom/twommod/ModMenu;->refresh(Landroid/widget/LinearLayout;)V

    return-void
.end method

.method static synthetic access$200(Landroid/app/Activity;F)I
    .registers 2

    .line 22
    invoke-static {p0, p1}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result p0

    return p0
.end method

.method public static attach(Landroid/app/Activity;)V
    .registers 5

    const-string v0, "twommod"

    .line 34
    :try_start_2
    sget-boolean v1, Lcom/twommod/ModMenu;->loaded:Z

    const/4 v2, 0x1

    if-nez v1, :cond_c

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    sput-boolean v2, Lcom/twommod/ModMenu;->loaded:Z
    :try_end_c
    .catchall {:try_start_2 .. :try_end_c} :catchall_65

    :cond_c
    const/4 v1, 0x0

    .line 39
    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    sput-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v3, "god"

    .line 40
    invoke-interface {v0, v3, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    invoke-static {v1, v0}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 41
    sget-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v3, "bag"

    invoke-interface {v0, v3, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    invoke-static {v2, v0}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 42
    sget-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v3, "stack"

    invoke-interface {v0, v3, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    const/4 v3, 0x2

    invoke-static {v3, v0}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 43
    sget-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v3, "mul"

    invoke-interface {v0, v3, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    const/4 v2, 0x3

    invoke-static {v2, v0}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 44
    sget-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v2, "instant"

    invoke-interface {v0, v2, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    const/4 v1, 0x4

    invoke-static {v1, v0}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 45
    sget-object v0, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v2, "dayspeed"

    invoke-interface {v0, v2, v1}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x5

    invoke-static {v1, v0}, Lcom/twommod/ModMenu;->nSet(II)V

    .line 46
    invoke-static {p0}, Lcom/twommod/ModMenu;->build(Landroid/app/Activity;)Landroid/view/View;

    move-result-object v0

    new-instance v1, Landroid/view/ViewGroup$LayoutParams;

    const/4 v2, -0x1

    invoke-direct {v1, v2, v2}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->addContentView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    return-void

    :catchall_65
    move-exception p0

    const-string v1, "kh\u00f4ng n\u1ea1p \u0111\u01b0\u1ee3c libtwommod.so"

    .line 36
    invoke-static {v0, v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return-void
.end method

.method private static bg(IF)Landroid/graphics/drawable/GradientDrawable;
    .registers 3

    .line 54
    new-instance v0, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v0}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 55
    invoke-virtual {v0, p0}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    const/4 p0, 0x2

    const p1, -0x7585a6

    invoke-virtual {v0, p0, p1}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    return-object v0
.end method

.method private static build(Landroid/app/Activity;)Landroid/view/View;
    .registers 17

    move-object/from16 v0, p0

    .line 60
    new-instance v1, Landroid/widget/FrameLayout;

    invoke-direct {v1, v0}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;)V

    .line 62
    new-instance v2, Landroid/widget/LinearLayout;

    invoke-direct {v2, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    const/4 v3, 0x1

    .line 63
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setOrientation(I)V

    const/high16 v4, 0x41000000    # 8.0f

    .line 64
    invoke-static {v0, v4}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v5

    int-to-float v5, v5

    const v6, -0x19dfe3e8

    invoke-static {v6, v5}, Lcom/twommod/ModMenu;->bg(IF)Landroid/graphics/drawable/GradientDrawable;

    move-result-object v5

    invoke-virtual {v2, v5}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    const/high16 v5, 0x41400000    # 12.0f

    .line 65
    invoke-static {v0, v5}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v5

    .line 66
    invoke-virtual {v2, v5, v5, v5, v5}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 67
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setClickable(Z)V

    .line 69
    new-instance v5, Landroid/widget/ScrollView;

    invoke-direct {v5, v0}, Landroid/widget/ScrollView;-><init>(Landroid/content/Context;)V

    .line 70
    invoke-virtual {v5, v2}, Landroid/widget/ScrollView;->addView(Landroid/view/View;)V

    const/16 v6, 0x8

    .line 71
    invoke-virtual {v5, v6}, Landroid/widget/ScrollView;->setVisibility(I)V

    .line 73
    new-instance v6, Landroid/widget/TextView;

    invoke-direct {v6, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    const-string v7, "MOD \u2014 ch\u01a1i \u0111\u01a1n"

    .line 74
    invoke-virtual {v6, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const v7, -0x172650

    .line 75
    invoke-virtual {v6, v7}, Landroid/widget/TextView;->setTextColor(I)V

    const/high16 v8, 0x41800000    # 16.0f

    .line 76
    invoke-virtual {v6, v8}, Landroid/widget/TextView;->setTextSize(F)V

    .line 77
    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    const-string v6, "B\u1ea5t t\u1eed khi l\u1ee5c l\u1ecdi (m\u00e1u kh\u00f4ng v\u1ec1 0)"

    const-string v8, "god"

    const/4 v9, 0x0

    .line 79
    invoke-static {v0, v6, v8, v9}, Lcom/twommod/ModMenu;->sw(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;I)Landroid/widget/Switch;

    move-result-object v6

    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    const-string v6, "Ba l\u00f4 kh\u00f4ng gi\u1edbi h\u1ea1n"

    const-string v8, "bag"

    .line 80
    invoke-static {v0, v6, v8, v3}, Lcom/twommod/ModMenu;->sw(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;I)Landroid/widget/Switch;

    move-result-object v6

    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    const-string v6, "stack"

    const/4 v8, 0x2

    const-string v10, "M\u1ed7i \u00f4 ch\u1ee9a 99"

    .line 81
    invoke-static {v0, v10, v6, v8}, Lcom/twommod/ModMenu;->sw(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;I)Landroid/widget/Switch;

    move-result-object v6

    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    const-string v6, "C\u00f4ng vi\u1ec7c xong t\u1ee9c th\u00ec"

    const-string v8, "instant"

    const/4 v10, 0x4

    .line 82
    invoke-static {v0, v6, v8, v10}, Lcom/twommod/ModMenu;->sw(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;I)Landroid/widget/Switch;

    move-result-object v6

    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 84
    new-instance v6, Landroid/widget/TextView;

    invoke-direct {v6, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    const/4 v8, -0x1

    .line 85
    invoke-virtual {v6, v8}, Landroid/widget/TextView;->setTextColor(I)V

    const/high16 v11, 0x41600000    # 14.0f

    .line 86
    invoke-virtual {v6, v11}, Landroid/widget/TextView;->setTextSize(F)V

    .line 87
    new-instance v12, Landroid/widget/SeekBar;

    invoke-direct {v12, v0}, Landroid/widget/SeekBar;-><init>(Landroid/content/Context;)V

    const/4 v13, 0x5

    .line 88
    invoke-virtual {v12, v13}, Landroid/widget/SeekBar;->setMax(I)V

    .line 89
    sget-object v14, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v15, "mul"

    invoke-interface {v14, v15, v3}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v14

    add-int/lit8 v15, v14, -0x1

    .line 90
    invoke-virtual {v12, v15}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 91
    new-instance v15, Ljava/lang/StringBuilder;

    const-string v4, "H\u1ec7 s\u1ed1 nh\u1eb7t \u0111\u1ed3: x"

    invoke-direct {v15, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v15, v14}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v6, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const/4 v4, 0x3

    .line 92
    invoke-static {v4}, Lcom/twommod/ModMenu;->nAvail(I)I

    move-result v4

    if-eqz v4, :cond_bf

    const/4 v4, 0x1

    goto :goto_c0

    :cond_bf
    const/4 v4, 0x0

    .line 93
    :goto_c0
    invoke-virtual {v12, v4}, Landroid/widget/SeekBar;->setEnabled(Z)V

    if-nez v4, :cond_ca

    const-string v4, "H\u1ec7 s\u1ed1 nh\u1eb7t \u0111\u1ed3: kh\u00f4ng kh\u1ea3 d\u1ee5ng"

    .line 94
    invoke-virtual {v6, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 95
    :cond_ca
    new-instance v4, Lcom/twommod/ModMenu$1;

    invoke-direct {v4, v6}, Lcom/twommod/ModMenu$1;-><init>(Landroid/widget/TextView;)V

    invoke-virtual {v12, v4}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    .line 105
    invoke-virtual {v2, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 106
    new-instance v4, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v6, 0x43820000    # 260.0f

    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v14

    const/4 v15, -0x2

    invoke-direct {v4, v14, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v2, v12, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 109
    new-instance v4, Landroid/widget/TextView;

    invoke-direct {v4, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 110
    invoke-virtual {v4, v8}, Landroid/widget/TextView;->setTextColor(I)V

    .line 111
    invoke-virtual {v4, v11}, Landroid/widget/TextView;->setTextSize(F)V

    .line 112
    new-instance v8, Landroid/widget/SeekBar;

    invoke-direct {v8, v0}, Landroid/widget/SeekBar;-><init>(Landroid/content/Context;)V

    .line 113
    sget-object v11, Lcom/twommod/ModMenu;->SPEED_TXT:[Ljava/lang/String;

    array-length v12, v11

    sub-int/2addr v12, v3

    invoke-virtual {v8, v12}, Landroid/widget/SeekBar;->setMax(I)V

    .line 114
    array-length v12, v11

    sub-int/2addr v12, v3

    sget-object v14, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const-string v3, "dayspeed"

    invoke-interface {v14, v3, v10}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v3

    invoke-static {v12, v3}, Ljava/lang/Math;->min(II)I

    move-result v3

    invoke-static {v9, v3}, Ljava/lang/Math;->max(II)I

    move-result v3

    .line 115
    invoke-virtual {v8, v3}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 116
    new-instance v10, Ljava/lang/StringBuilder;

    const-string v12, "T\u1ed1c \u0111\u1ed9 ng\u00e0y/\u0111\u00eam: "

    invoke-direct {v10, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    aget-object v3, v11, v3

    invoke-virtual {v10, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v4, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 117
    invoke-static {v13}, Lcom/twommod/ModMenu;->nAvail(I)I

    move-result v3

    if-eqz v3, :cond_12b

    const/4 v3, 0x1

    goto :goto_12c

    :cond_12b
    const/4 v3, 0x0

    .line 118
    :goto_12c
    invoke-virtual {v8, v3}, Landroid/widget/SeekBar;->setEnabled(Z)V

    if-nez v3, :cond_136

    const-string v3, "T\u1ed1c \u0111\u1ed9 ng\u00e0y/\u0111\u00eam: kh\u00f4ng kh\u1ea3 d\u1ee5ng"

    .line 119
    invoke-virtual {v4, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 120
    :cond_136
    new-instance v3, Lcom/twommod/ModMenu$2;

    invoke-direct {v3, v4}, Lcom/twommod/ModMenu$2;-><init>(Landroid/widget/TextView;)V

    invoke-virtual {v8, v3}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    .line 129
    invoke-virtual {v2, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 130
    new-instance v3, Landroid/widget/LinearLayout$LayoutParams;

    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v4

    invoke-direct {v3, v4, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v2, v8, v3}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 132
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    const-string v4, "MOD"

    .line 133
    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 134
    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setTextColor(I)V

    const/high16 v4, 0x41500000    # 13.0f

    .line 135
    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setTextSize(F)V

    const/16 v4, 0x11

    .line 136
    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setGravity(I)V

    const/high16 v4, 0x41a00000    # 20.0f

    .line 137
    invoke-static {v0, v4}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v4

    int-to-float v4, v4

    const v6, -0x33dfe3e8    # -4.197181E7f

    invoke-static {v6, v4}, Lcom/twommod/ModMenu;->bg(IF)Landroid/graphics/drawable/GradientDrawable;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 138
    new-instance v4, Landroid/widget/FrameLayout$LayoutParams;

    const/high16 v6, 0x42400000    # 48.0f

    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v6

    const/high16 v7, 0x42200000    # 40.0f

    invoke-static {v0, v7}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v8

    invoke-direct {v4, v6, v8}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    const/high16 v6, 0x41000000    # 8.0f

    .line 139
    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v8

    iput v8, v4, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    const/high16 v6, 0x42700000    # 60.0f

    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v6

    iput v6, v4, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 140
    invoke-virtual {v1, v3, v4}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 142
    new-instance v4, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v4, v15, v15}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    const/high16 v6, 0x42800000    # 64.0f

    .line 143
    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v6

    iput v6, v4, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    invoke-static {v0, v7}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v6

    iput v6, v4, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    const/high16 v6, 0x41000000    # 8.0f

    invoke-static {v0, v6}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result v6

    iput v6, v4, Landroid/widget/FrameLayout$LayoutParams;->bottomMargin:I

    .line 144
    invoke-virtual {v1, v5, v4}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 147
    new-instance v4, Lcom/twommod/ModMenu$3;

    invoke-direct {v4, v5, v2, v0}, Lcom/twommod/ModMenu$3;-><init>(Landroid/widget/ScrollView;Landroid/widget/LinearLayout;Landroid/app/Activity;)V

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    return-object v1
.end method

.method private static dp(Landroid/app/Activity;F)I
    .registers 3

    .line 50
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p0

    const/4 v0, 0x1

    invoke-static {v0, p1, p0}, Landroid/util/TypedValue;->applyDimension(IFLandroid/util/DisplayMetrics;)F

    move-result p0

    float-to-int p0, p0

    return p0
.end method

.method static native nAvail(I)I
.end method

.method static native nSet(II)V
.end method

.method private static refresh(Landroid/widget/LinearLayout;)V
    .registers 5

    const/4 v0, 0x0

    const/4 v1, 0x0

    .line 181
    :goto_2
    invoke-virtual {p0}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v2

    if-ge v1, v2, :cond_3c

    .line 182
    invoke-virtual {p0, v1}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v2

    .line 183
    instance-of v3, v2, Landroid/widget/Switch;

    if-eqz v3, :cond_39

    invoke-virtual {v2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v3

    instance-of v3, v3, Ljava/lang/Integer;

    if-eqz v3, :cond_39

    .line 184
    invoke-virtual {v2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3

    invoke-static {v3}, Lcom/twommod/ModMenu;->nAvail(I)I

    move-result v3

    if-eqz v3, :cond_2a

    const/4 v3, 0x1

    goto :goto_2b

    :cond_2a
    const/4 v3, 0x0

    .line 185
    :goto_2b
    invoke-virtual {v2, v3}, Landroid/view/View;->setEnabled(Z)V

    if-eqz v3, :cond_33

    const/high16 v3, 0x3f800000    # 1.0f

    goto :goto_36

    :cond_33
    const v3, 0x3ecccccd    # 0.4f

    .line 186
    :goto_36
    invoke-virtual {v2, v3}, Landroid/view/View;->setAlpha(F)V

    :cond_39
    add-int/lit8 v1, v1, 0x1

    goto :goto_2

    :cond_3c
    return-void
.end method

.method private static sw(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;I)Landroid/widget/Switch;
    .registers 6

    .line 192
    new-instance v0, Landroid/widget/Switch;

    invoke-direct {v0, p0}, Landroid/widget/Switch;-><init>(Landroid/content/Context;)V

    .line 193
    invoke-virtual {v0, p1}, Landroid/widget/Switch;->setText(Ljava/lang/CharSequence;)V

    const/4 p1, -0x1

    .line 194
    invoke-virtual {v0, p1}, Landroid/widget/Switch;->setTextColor(I)V

    const/high16 p1, 0x41600000    # 14.0f

    .line 195
    invoke-virtual {v0, p1}, Landroid/widget/Switch;->setTextSize(F)V

    .line 196
    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/widget/Switch;->setTag(Ljava/lang/Object;)V

    .line 197
    sget-object p1, Lcom/twommod/ModMenu;->prefs:Landroid/content/SharedPreferences;

    const/4 v1, 0x0

    invoke-interface {p1, p2, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result p1

    invoke-virtual {v0, p1}, Landroid/widget/Switch;->setChecked(Z)V

    const/high16 p1, 0x40c00000    # 6.0f

    .line 198
    invoke-static {p0, p1}, Lcom/twommod/ModMenu;->dp(Landroid/app/Activity;F)I

    move-result p0

    .line 199
    invoke-virtual {v0, v1, p0, v1, p0}, Landroid/widget/Switch;->setPadding(IIII)V

    .line 200
    new-instance p0, Lcom/twommod/ModMenu$4;

    invoke-direct {p0, p3, p2}, Lcom/twommod/ModMenu$4;-><init>(ILjava/lang/String;)V

    invoke-virtual {v0, p0}, Landroid/widget/Switch;->setOnCheckedChangeListener(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V

    return-object v0
.end method
