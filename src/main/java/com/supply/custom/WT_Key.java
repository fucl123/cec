package com.supply.custom;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import java.io.File;

public abstract interface WT_Key extends Library
{
  public static final String path = WT_Key.class.getResource("/").getPath() + "SPSecureAPI.dll";
  public static final WT_Key instanseKey = (WT_Key)Native.loadLibrary("." + File.separator + "SPSecureAPI", WT_Key.class);

  public abstract int SpcInitEnvEx();

  public abstract int SpcClearEnv();

  public abstract int SpcVerifyPIN(byte[] paramArrayOfByte, int paramInt);

  public abstract int SpcChangePIN(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2);

  public abstract int SpcGetRandom(byte[] paramArrayOfByte, int paramInt);

  public abstract int SpcSignData(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, IntByReference
          paramIntByReference);

  public abstract int SpcSignDataNoHash(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2,
                                        IntByReference paramIntByReference);

  public abstract int SpcVerifySignData(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3);

  public abstract int SpcVerifySignDataNoHash(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte2, int paramInt4);

  public abstract int SpcSHA1Digest(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, IntByReference paramIntByReference);

  public abstract int SpcEncodePEM(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2);

  public abstract int SpcDecodePEM(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, IntByReference paramIntByReference);

  public abstract String SpcGetErrMsg(int paramInt);

  public abstract int SpcGetModuleVer(byte[] paramArrayOfByte);

  public abstract int SpcVerifyCert(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3);

  public abstract int SpcGetValidTimeFromCert(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);

  public abstract int SpcSealEnvelope(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3);

  public abstract int SpcOpenEnvelope(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, IntByReference paramIntByReference);

  public abstract int SpcGetUName(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetCardID(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetEntName(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetEntID(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetCertNo(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetEntMode(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcVerifySignWithPubKey(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3);

  public abstract int SpcVerifySignNohashWithPubKey(byte[] paramArrayOfByte1, int paramInt1, byte[]
          paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3);

  public abstract int SpcGetCertPubKey(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, IntByReference paramIntByReference);

  public abstract int SpcGetSignCert(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetEnvCert(byte[] paramArrayOfByte, IntByReference paramIntByReference);

  public abstract int SpcGetCardUserInfo(String paramString, IntByReference paramIntByReference);

  public abstract int SpcGetCertInfo(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, IntByReference paramIntByReference);

  public abstract int SpcSymEncrypt(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, IntByReference paramIntByReference, byte[] paramArrayOfByte3);

  public abstract int SpcSymDecrypt(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, IntByReference paramIntByReference, byte[] paramArrayOfByte3);

  public abstract int SpcRSAEncrypt(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, IntByReference paramIntByReference);

  public abstract int SpcRSADecrypt(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, IntByReference
          paramIntByReference);

  public abstract int SpcGetCardState(int paramInt1, int paramInt2, IntByReference paramIntByReference);

  public abstract int SpcGetCardAttachInfo(byte[] paramArrayOfByte, IntByReference paramIntByReference);
}
