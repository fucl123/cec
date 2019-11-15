package com.supply.custom;

import com.sun.jna.ptr.IntByReference;
import com.supply.core.ClientException;
import com.supply.utils.UKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PKI {
	private static final Logger LOGGER = LoggerFactory.getLogger(PKI.class);

	private static WT_Key keyInstance = null;

	String password = null;

	/**
	 * 构造函数
	 * @param password
	 */
	public PKI(String password) {
		this.password = password;
		if ((keyInstance == null)) {
			System.setProperty("jna.encoding", "GBK");
			int errCode = -1;
			try {
				keyInstance = WT_Key.instanseKey;

				errCode = keyInstance.SpcInitEnvEx();
				if (errCode != 0) {
					throw new Exception(keyInstance.SpcGetErrMsg(errCode));
				}

				errCode = keyInstance.SpcVerifyPIN(password.getBytes(), password.getBytes().length);
				if (errCode != 0) {
					throw new Exception(keyInstance.SpcGetErrMsg(errCode));
				}
			} catch (Exception e) {
				LOGGER.error("ukey connect failed！");
				System.exit(0);
			}
		}
	}

	/**
	 * 读取key证书
	 * @param flag
	 * @return
	 * @throws ClientException
	 */
	public byte[] getKeyCert(int flag) throws ClientException {
		byte[] szCert = new byte[4096];
		IntByReference nCertLen = new IntByReference(4096);
		int errCode = -1;
		try {
			switch (flag) {
				case 0:
					errCode = keyInstance.SpcGetSignCert(szCert, nCertLen);
					break;
				case 1:
					errCode = keyInstance.SpcGetEnvCert(szCert, nCertLen);
					break;
				default:
					return null;
			}
			if (errCode != 0) {
				throw new Exception(keyInstance.SpcGetErrMsg(errCode));
			}
		} catch (Exception e) {
			keyInstance.SpcClearEnv();
			reInitKey();
			throw new ClientException("Ukey 获取Key的证书时报错. Error=" + e.getMessage());
		}

		byte[] cert = UKeyUtil.subBytes(szCert, 0, nCertLen.getValue());

		return cert;
	}

	/**
	 * 重新初始化ukey
	 * 
	 * @throws ClientException
	 */
	public void reInitKey() throws ClientException {
		int errCode = -1;
		try {
			errCode = keyInstance.SpcInitEnvEx();
			if (errCode != 0) {
				throw new Exception(keyInstance.SpcGetErrMsg(errCode));
			}

			errCode = keyInstance.SpcVerifyPIN(this.password.getBytes(), this.password.getBytes().length);
			if (errCode != 0) {
				throw new Exception(keyInstance.SpcGetErrMsg(errCode));
			}
		} catch (Exception e) {
			throw new ClientException("Ukey 重新初始化报错. Error=" + e.getMessage());
		}
	}

	/**
	 * 读取签名证书号
	 * @return
	 * @throws ClientException
	 */
	public byte[] getSignCertNo() throws ClientException {
		IntByReference nCertNoLen = new IntByReference(100);
		byte[] out = new byte[100];
		try {
			int errCode = keyInstance.SpcGetCertNo(out, nCertNoLen);

			if (errCode != 0) {
				throw new Exception(keyInstance.SpcGetErrMsg(errCode));
			}
		} catch (Exception e) {
			keyInstance.SpcClearEnv();
			reInitKey();
			throw new ClientException("Ukey 取签名证书号时报错. Error=" + e.getMessage());
		}
		return UKeyUtil.subBytes(out, 0, nCertNoLen.getValue());

	}

	/**
	 * 计算摘要
	 * 
	 * @param plainData
	 * @return
	 * @throws ClientException
	 */
	public byte[] sha1Digest(byte[] plainData) throws ClientException 
	{
		IntByReference nSIgnDataLen = new IntByReference(20);
		byte[] out = new byte[20];
		try {
			int errCode = keyInstance.SpcSHA1Digest(plainData, plainData.length, out, nSIgnDataLen);
			if
					(errCode != 0) {
				throw new Exception(keyInstance.SpcGetErrMsg(errCode));
			}
		} catch (Exception e) {
			keyInstance.SpcClearEnv();
			reInitKey();
			throw new ClientException("Ukey SHA1 摘要计算时报错. Error=" + e.getMessage());
		}
		return out;
	}

	/**
	 * 加密数据
	 * 
	 * @param plainData
	 * @return
	 * @throws ClientException
	 */
	public byte[] rsaSignData(byte[] plainData) throws ClientException 
	{
		IntByReference nSIgnDataLen = new IntByReference(128);
		byte[] out = new byte[''];
		try {
			int errCode = keyInstance.SpcSignData(plainData, plainData.length, out, nSIgnDataLen);
			if (errCode != 0) {
				throw new Exception(keyInstance.SpcGetErrMsg(errCode));
			}
		} catch (Exception e) {
			keyInstance.SpcClearEnv();
			reInitKey();
			throw new ClientException("Ukey RSA签名时报错. Error=" + e.getMessage());
		}
		return out;
	}

}
