package com.supply.domain.signature;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="KeyInfo")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = { 
		"KeyName",
		"X509Data"
})
public class KeyInfo implements Serializable{

	private static final long serialVersionUID = -6429111428170797062L;
	
	private String KeyName;
	
	private X509Data X509Data;

	public String getKeyName() {
		return KeyName;
	}

	public void setKeyName(String keyName) {
		KeyName = keyName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public X509Data getX509Data() {
		return X509Data;
	}

	public void setX509Data(X509Data x509Data) {
		X509Data = x509Data;
	}
	
}
