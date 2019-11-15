package com.supply.domain;

import com.supply.domain.signature.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="Signature",namespace="http://www.w3.org/2000/09/xmldsig#")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {  
		"SignedInfo",
		"SignatureValue",
		"KeyInfo",
})
public class Signature implements Serializable{

	private static final long serialVersionUID = -8883369613523580783L;
	
	private SignedInfo SignedInfo;
	
	private String SignatureValue;
	
	private KeyInfo KeyInfo;
	
	public Signature()
	{
		SignedInfo signedInfo = new SignedInfo();
		
		Reference reference = new Reference();
		reference.setDigestMethod(new DigestMethod());
		Transforms transforms = new Transforms();
		transforms.setTransform(new Transform());
		reference.setTransforms(transforms);
		
		signedInfo.setCanonicalizationMethod(new CanonicalizationMethod());
		signedInfo.setSignatureMethod(new SignatureMethod());
		signedInfo.setReference(reference);
		this.SignedInfo = signedInfo;
		
		KeyInfo keyInfo = new KeyInfo();
		X509Data x509Data = new X509Data();
		x509Data.setX509Certificate("");
		keyInfo.setX509Data(x509Data);
		this.KeyInfo = keyInfo;
		
	}

	public SignedInfo getSignedInfo() {
		return SignedInfo;
	}

	public void setSignedInfo(SignedInfo signedInfo) {
		SignedInfo = signedInfo;
	}

	public String getSignatureValue() {
		return SignatureValue;
	}

	public void setSignatureValue(String signatureValue) {
		SignatureValue = signatureValue;
	}

	public KeyInfo getKeyInfo() {
		return KeyInfo;
	}

	public void setKeyInfo(KeyInfo keyInfo) {
		KeyInfo = keyInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
