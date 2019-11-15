package com.supply.domain.signature;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="SignedInfo")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = { 
		"CanonicalizationMethod",
		"SignatureMethod",
		"Reference",
})
public class SignedInfo implements Serializable{

	private static final long serialVersionUID = 2486219709329314074L;
	
	private CanonicalizationMethod CanonicalizationMethod;
	
	private SignatureMethod SignatureMethod;
	
	private Reference Reference;

	public SignedInfo()
	{
		this.CanonicalizationMethod = new CanonicalizationMethod();
		this.SignatureMethod = new SignatureMethod();
		
		Reference reference = new Reference();
		
		Transforms transforms = new Transforms();
		transforms.setTransform(new Transform());
		reference.setTransforms(transforms);
		reference.setDigestMethod(new DigestMethod());
		reference.setDigestValue("");
		
		this.Reference = reference;
	}
	
	public CanonicalizationMethod getCanonicalizationMethod() {
		return CanonicalizationMethod;
	}


	public void setCanonicalizationMethod(CanonicalizationMethod canonicalizationMethod) {
		CanonicalizationMethod = canonicalizationMethod;
	}

	public SignatureMethod getSignatureMethod() {
		return SignatureMethod;
	}

	public void setSignatureMethod(SignatureMethod signatureMethod) {
		SignatureMethod = signatureMethod;
	}

	public Reference getReference() {
		return Reference;
	}

	public void setReference(Reference reference) {
		Reference = reference;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
