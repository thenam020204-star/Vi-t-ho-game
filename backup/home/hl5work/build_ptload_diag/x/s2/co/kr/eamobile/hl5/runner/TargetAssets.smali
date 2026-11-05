.class final Lco/kr/eamobile/hl5/runner/TargetAssets;
.super Ljava/lang/Object;
.source "TargetAssets.java"


# direct methods
.method constructor <init>()V
    .registers 1

    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static cacheMatches(Ljava/io/File;Lorg/json/JSONObject;Ljava/lang/String;)Z
    .registers 9

    .line 25
    if-eqz p1, :cond_34

    invoke-virtual {p0}, Ljava/io/File;->isFile()Z

    move-result v0

    if-eqz v0, :cond_34

    const-string v0, "sha256"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_34

    .line 26
    invoke-virtual {p0}, Ljava/io/File;->length()J

    move-result-wide v0

    const-string p2, "length"

    const-wide/16 v2, -0x1

    invoke-virtual {p1, p2, v2, v3}, Lorg/json/JSONObject;->optLong(Ljava/lang/String;J)J

    move-result-wide v4

    cmp-long p2, v0, v4

    if-nez p2, :cond_34

    invoke-virtual {p0}, Ljava/io/File;->lastModified()J

    move-result-wide v0

    const-string p0, "modified"

    invoke-virtual {p1, p0, v2, v3}, Lorg/json/JSONObject;->optLong(Ljava/lang/String;J)J

    move-result-wide p0

    cmp-long p2, v0, p0

    if-nez p2, :cond_34

    const/4 p0, 0x1

    goto :goto_35

    :cond_34
    const/4 p0, 0x0

    .line 25
    :goto_35
    return p0
.end method

.method private static digest(Ljava/io/InputStream;)Ljava/lang/String;
    .registers 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 20
    const-string v0, "SHA-256"

    invoke-static {v0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    const/high16 v1, 0x10000

    new-array v1, v1, [B

    .line 21
    :goto_a
    invoke-virtual {p0, v1}, Ljava/io/InputStream;->read([B)I

    move-result v2

    const/4 v3, -0x1

    if-eq v2, v3, :cond_16

    const/4 v3, 0x0

    invoke-virtual {v0, v1, v3, v2}, Ljava/security/MessageDigest;->update([BII)V

    goto :goto_a

    .line 22
    :cond_16
    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object p0

    invoke-static {p0}, Lco/kr/eamobile/hl5/runner/TargetAssets;->hex([B)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static hex([B)Ljava/lang/String;
    .registers 6

    .line 15
    const-string v0, "0123456789abcdef"

    invoke-virtual {v0}, Ljava/lang/String;->toCharArray()[C

    move-result-object v0

    array-length v1, p0

    mul-int/lit8 v1, v1, 0x2

    new-array v1, v1, [C

    .line 16
    const/4 v2, 0x0

    :goto_c
    array-length v3, p0

    if-ge v2, v3, :cond_28

    mul-int/lit8 v3, v2, 0x2

    aget-byte v4, p0, v2

    and-int/lit16 v4, v4, 0xff

    ushr-int/lit8 v4, v4, 0x4

    aget-char v4, v0, v4

    aput-char v4, v1, v3

    add-int/lit8 v3, v3, 0x1

    aget-byte v4, p0, v2

    and-int/lit8 v4, v4, 0xf

    aget-char v4, v0, v4

    aput-char v4, v1, v3

    add-int/lit8 v2, v2, 0x1

    goto :goto_c

    .line 17
    :cond_28
    new-instance p0, Ljava/lang/String;

    invoke-direct {p0, v1}, Ljava/lang/String;-><init>([C)V

    return-object p0
.end method

.method static declared-synchronized install(Landroid/content/Context;Ljava/io/File;)V
    .registers 18
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    move-object/from16 v1, p1

    const-class v2, Lco/kr/eamobile/hl5/runner/TargetAssets;

    monitor-enter v2

    .line 29
    if-eqz v1, :cond_1b4

    .line 31
    :try_start_7
    invoke-virtual/range {p0 .. p0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    const-string v3, "target-assets.json"

    invoke-virtual {v0, v3}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v3
    :try_end_11
    .catchall {:try_start_7 .. :try_end_11} :catchall_1b2

    :try_start_11
    invoke-static {v3}, Lco/kr/eamobile/hl5/runner/TargetAssets;->readJson(Ljava/io/InputStream;)Lorg/json/JSONObject;

    move-result-object v4
    :try_end_15
    .catchall {:try_start_11 .. :try_end_15} :catchall_1a5

    if-eqz v3, :cond_1a

    :try_start_17
    invoke-virtual {v3}, Ljava/io/InputStream;->close()V

    .line 32
    :cond_1a
    new-instance v3, Landroid/util/AtomicFile;

    new-instance v0, Ljava/io/File;

    const-string v5, "runner-assets-verified.json"

    invoke-direct {v0, v1, v5}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-direct {v3, v0}, Landroid/util/AtomicFile;-><init>(Ljava/io/File;)V
    :try_end_26
    .catchall {:try_start_17 .. :try_end_26} :catchall_1b2

    .line 34
    :try_start_26
    invoke-virtual {v3}, Landroid/util/AtomicFile;->openRead()Ljava/io/FileInputStream;

    move-result-object v5
    :try_end_2a
    .catch Ljava/lang/Exception; {:try_start_26 .. :try_end_2a} :catch_41
    .catchall {:try_start_26 .. :try_end_2a} :catchall_1b2

    :try_start_2a
    invoke-static {v5}, Lco/kr/eamobile/hl5/runner/TargetAssets;->readJson(Ljava/io/InputStream;)Lorg/json/JSONObject;

    move-result-object v0
    :try_end_2e
    .catchall {:try_start_2a .. :try_end_2e} :catchall_34

    if-eqz v5, :cond_47

    :try_start_30
    invoke-virtual {v5}, Ljava/io/InputStream;->close()V
    :try_end_33
    .catch Ljava/lang/Exception; {:try_start_30 .. :try_end_33} :catch_41
    .catchall {:try_start_30 .. :try_end_33} :catchall_1b2

    goto :goto_47

    :catchall_34
    move-exception v0

    move-object v6, v0

    if-eqz v5, :cond_40

    :try_start_38
    invoke-virtual {v5}, Ljava/io/InputStream;->close()V
    :try_end_3b
    .catchall {:try_start_38 .. :try_end_3b} :catchall_3c

    goto :goto_40

    :catchall_3c
    move-exception v0

    :try_start_3d
    invoke-virtual {v6, v0}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    :cond_40
    :goto_40
    throw v6
    :try_end_41
    .catch Ljava/lang/Exception; {:try_start_3d .. :try_end_41} :catch_41
    .catchall {:try_start_3d .. :try_end_41} :catchall_1b2

    .line 35
    :catch_41
    move-exception v0

    :try_start_42
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    :cond_47
    :goto_47
    move-object v5, v0

    .line 36
    nop

    .line 37
    const-string v0, "libHeroesLore5.so"

    const-string v6, "data.vfs.mp3"

    filled-new-array {v0, v6}, [Ljava/lang/String;

    move-result-object v6

    const/4 v0, 0x0

    const/4 v8, 0x0

    :goto_53
    const/4 v10, 0x2

    if-ge v8, v10, :cond_181

    aget-object v10, v6, v8

    .line 38
    invoke-virtual {v4, v10}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    .line 39
    new-instance v12, Landroid/util/AtomicFile;

    new-instance v13, Ljava/io/File;

    invoke-direct {v13, v1, v10}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-direct {v12, v13}, Landroid/util/AtomicFile;-><init>(Ljava/io/File;)V

    invoke-virtual {v12}, Landroid/util/AtomicFile;->getBaseFile()Ljava/io/File;

    move-result-object v13

    .line 40
    new-instance v14, Ljava/io/File;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v15, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v15, ".bak"

    invoke-virtual {v9, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-direct {v14, v9}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v14}, Ljava/io/File;->exists()Z

    move-result v9

    if-nez v9, :cond_bb

    new-instance v9, Ljava/io/File;

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, ".new"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-direct {v9, v14}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v9}, Ljava/io/File;->exists()Z

    move-result v9

    if-nez v9, :cond_bb

    .line 41
    invoke-virtual {v5, v10}, Lorg/json/JSONObject;->optJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v9

    invoke-static {v13, v9, v11}, Lco/kr/eamobile/hl5/runner/TargetAssets;->cacheMatches(Ljava/io/File;Lorg/json/JSONObject;Ljava/lang/String;)Z

    move-result v9
    :try_end_b6
    .catchall {:try_start_42 .. :try_end_b6} :catchall_1b2

    if-eqz v9, :cond_bb

    const/4 v1, 0x0

    goto/16 :goto_17b

    .line 42
    :cond_bb
    nop

    .line 43
    :try_start_bc
    invoke-virtual {v12}, Landroid/util/AtomicFile;->openRead()Ljava/io/FileInputStream;

    move-result-object v9
    :try_end_c0
    .catch Ljava/io/FileNotFoundException; {:try_start_bc .. :try_end_c0} :catch_dd
    .catchall {:try_start_bc .. :try_end_c0} :catchall_1b2

    :try_start_c0
    invoke-static {v9}, Lco/kr/eamobile/hl5/runner/TargetAssets;->digest(Ljava/io/InputStream;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v11, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14
    :try_end_c8
    .catchall {:try_start_c0 .. :try_end_c8} :catchall_d0

    if-eqz v9, :cond_df

    :try_start_ca
    invoke-virtual {v9}, Ljava/io/InputStream;->close()V
    :try_end_cd
    .catch Ljava/io/FileNotFoundException; {:try_start_ca .. :try_end_cd} :catch_ce
    .catchall {:try_start_ca .. :try_end_cd} :catchall_1b2

    goto :goto_df

    .line 44
    :catch_ce
    move-exception v0

    goto :goto_df

    .line 43
    :catchall_d0
    move-exception v0

    move-object v14, v0

    if-eqz v9, :cond_dc

    :try_start_d4
    invoke-virtual {v9}, Ljava/io/InputStream;->close()V
    :try_end_d7
    .catchall {:try_start_d4 .. :try_end_d7} :catchall_d8

    goto :goto_dc

    :catchall_d8
    move-exception v0

    :try_start_d9
    invoke-virtual {v14, v0}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    :cond_dc
    :goto_dc
    throw v14
    :try_end_dd
    .catch Ljava/io/FileNotFoundException; {:try_start_d9 .. :try_end_dd} :catch_dd
    .catchall {:try_start_d9 .. :try_end_dd} :catchall_1b2

    .line 44
    :catch_dd
    move-exception v0

    const/4 v14, 0x0

    :cond_df
    :goto_df
    nop

    .line 45
    if-nez v14, :cond_157

    .line 46
    nop

    .line 47
    :try_start_e3
    invoke-virtual/range {p0 .. p0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    invoke-virtual {v0, v10}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v9
    :try_end_eb
    .catchall {:try_start_e3 .. :try_end_eb} :catchall_14f

    .line 48
    :try_start_eb
    invoke-virtual {v12}, Landroid/util/AtomicFile;->startWrite()Ljava/io/FileOutputStream;

    move-result-object v14
    :try_end_ef
    .catchall {:try_start_eb .. :try_end_ef} :catchall_13e

    :try_start_ef
    const-string v0, "SHA-256"

    invoke-static {v0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 49
    const/high16 v15, 0x10000

    new-array v15, v15, [B

    .line 50
    :goto_f9
    invoke-virtual {v9, v15}, Ljava/io/InputStream;->read([B)I

    move-result v7

    const/4 v1, -0x1

    if-eq v7, v1, :cond_10a

    const/4 v1, 0x0

    invoke-virtual {v14, v15, v1, v7}, Ljava/io/FileOutputStream;->write([BII)V

    invoke-virtual {v0, v15, v1, v7}, Ljava/security/MessageDigest;->update([BII)V

    move-object/from16 v1, p1

    goto :goto_f9

    .line 51
    :cond_10a
    const/4 v1, 0x0

    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v0

    invoke-static {v0}, Lco/kr/eamobile/hl5/runner/TargetAssets;->hex([B)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v11, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_122

    .line 52
    invoke-virtual {v12, v14}, Landroid/util/AtomicFile;->finishWrite(Ljava/io/FileOutputStream;)V
    :try_end_11c
    .catchall {:try_start_ef .. :try_end_11c} :catchall_13b

    .line 53
    if-eqz v9, :cond_158

    :try_start_11e
    invoke-virtual {v9}, Ljava/io/InputStream;->close()V
    :try_end_121
    .catchall {:try_start_11e .. :try_end_121} :catchall_14f

    goto :goto_158

    .line 51
    :cond_122
    :try_start_122
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Asset fingerprint mismatch: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_13b
    .catchall {:try_start_122 .. :try_end_13b} :catchall_13b

    .line 47
    :catchall_13b
    move-exception v0

    move-object v1, v0

    goto :goto_141

    :catchall_13e
    move-exception v0

    move-object v1, v0

    const/4 v14, 0x0

    :goto_141
    if-eqz v9, :cond_14b

    :try_start_143
    invoke-virtual {v9}, Ljava/io/InputStream;->close()V
    :try_end_146
    .catchall {:try_start_143 .. :try_end_146} :catchall_147

    goto :goto_14b

    :catchall_147
    move-exception v0

    :try_start_148
    invoke-virtual {v1, v0}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    :cond_14b
    :goto_14b
    throw v1
    :try_end_14c
    .catchall {:try_start_148 .. :try_end_14c} :catchall_14c

    .line 53
    :catchall_14c
    move-exception v0

    move-object v9, v14

    goto :goto_151

    :catchall_14f
    move-exception v0

    const/4 v9, 0x0

    :goto_151
    if-eqz v9, :cond_156

    :try_start_153
    invoke-virtual {v12, v9}, Landroid/util/AtomicFile;->failWrite(Ljava/io/FileOutputStream;)V

    :cond_156
    throw v0

    .line 45
    :cond_157
    const/4 v1, 0x0

    .line 55
    :cond_158
    :goto_158
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    const-string v7, "sha256"

    invoke-virtual {v0, v7, v11}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    move-result-object v0

    const-string v7, "length"

    invoke-virtual {v13}, Ljava/io/File;->length()J

    move-result-wide v11

    invoke-virtual {v0, v7, v11, v12}, Lorg/json/JSONObject;->put(Ljava/lang/String;J)Lorg/json/JSONObject;

    move-result-object v0

    const-string v7, "modified"

    invoke-virtual {v13}, Ljava/io/File;->lastModified()J

    move-result-wide v11

    invoke-virtual {v0, v7, v11, v12}, Lorg/json/JSONObject;->put(Ljava/lang/String;J)Lorg/json/JSONObject;

    move-result-object v0

    invoke-virtual {v5, v10, v0}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    :try_end_17a
    .catchall {:try_start_153 .. :try_end_17a} :catchall_1b2

    .line 56
    const/4 v0, 0x1

    .line 37
    :goto_17b
    add-int/lit8 v8, v8, 0x1

    move-object/from16 v1, p1

    goto/16 :goto_53

    .line 58
    :cond_181
    if-eqz v0, :cond_1a3

    .line 59
    nop

    .line 60
    :try_start_184
    invoke-virtual {v3}, Landroid/util/AtomicFile;->startWrite()Ljava/io/FileOutputStream;

    move-result-object v9
    :try_end_188
    .catchall {:try_start_184 .. :try_end_188} :catchall_19b

    :try_start_188
    invoke-virtual {v5}, Lorg/json/JSONObject;->toString()Ljava/lang/String;

    move-result-object v0

    sget-object v1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v0, v1}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v0

    invoke-virtual {v9, v0}, Ljava/io/FileOutputStream;->write([B)V

    invoke-virtual {v3, v9}, Landroid/util/AtomicFile;->finishWrite(Ljava/io/FileOutputStream;)V
    :try_end_198
    .catchall {:try_start_188 .. :try_end_198} :catchall_199

    .line 61
    goto :goto_1a3

    :catchall_199
    move-exception v0

    goto :goto_19d

    :catchall_19b
    move-exception v0

    const/4 v9, 0x0

    :goto_19d
    if-eqz v9, :cond_1a2

    :try_start_19f
    invoke-virtual {v3, v9}, Landroid/util/AtomicFile;->failWrite(Ljava/io/FileOutputStream;)V

    :cond_1a2
    throw v0
    :try_end_1a3
    .catchall {:try_start_19f .. :try_end_1a3} :catchall_1b2

    .line 63
    :cond_1a3
    :goto_1a3
    monitor-exit v2

    return-void

    .line 31
    :catchall_1a5
    move-exception v0

    move-object v1, v0

    if-eqz v3, :cond_1b1

    :try_start_1a9
    invoke-virtual {v3}, Ljava/io/InputStream;->close()V
    :try_end_1ac
    .catchall {:try_start_1a9 .. :try_end_1ac} :catchall_1ad

    goto :goto_1b1

    :catchall_1ad
    move-exception v0

    :try_start_1ae
    invoke-virtual {v1, v0}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    :cond_1b1
    :goto_1b1
    throw v1

    .line 28
    :catchall_1b2
    move-exception v0

    goto :goto_1bc

    .line 29
    :cond_1b4
    new-instance v0, Ljava/io/IOException;

    const-string v1, "Internal storage unavailable"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 28
    :goto_1bc
    monitor-exit v2
    :try_end_1bd
    .catchall {:try_start_1ae .. :try_end_1bd} :catchall_1b2

    throw v0
.end method

.method private static readJson(Ljava/io/InputStream;)Lorg/json/JSONObject;
    .registers 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 10
    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    const/16 v1, 0x1000

    new-array v1, v1, [B

    .line 11
    :goto_9
    invoke-virtual {p0, v1}, Ljava/io/InputStream;->read([B)I

    move-result v2

    const/4 v3, -0x1

    if-eq v2, v3, :cond_15

    const/4 v3, 0x0

    invoke-virtual {v0, v1, v3, v2}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_9

    .line 12
    :cond_15
    new-instance p0, Lorg/json/JSONObject;

    new-instance v1, Ljava/lang/String;

    invoke-virtual {v0}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v0

    sget-object v2, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {v1, v0, v2}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V

    invoke-direct {p0, v1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    return-object p0
.end method
