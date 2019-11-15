package com.supply.utils;
public class UKeyUtil
{
  public static byte[] subBytes(byte[] src, int begin, int count)
  {
    byte[] bs = new byte[count];
    for (int i = begin; i < begin + count; i++) bs[(i - begin)] = src[i];
    return bs;
  }

  public static byte[] pkcs5Padding4Key(byte[] plainData, int keyBlodLen)
  {
    int padLen = 0;
    padLen = (plainData.length / keyBlodLen + 1) * keyBlodLen - plainData.length;

    byte[] newPlainData = new byte[plainData.length + padLen];
    System.arraycopy(plainData, 0, newPlainData, 0, plainData.length);

    for (int i = plainData.length; i < newPlainData.length; i++) {
      newPlainData[i] = (byte)padLen;
    }

    return newPlainData;
  }
}
