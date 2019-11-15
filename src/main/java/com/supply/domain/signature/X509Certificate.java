package com.supply.domain.signature;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="X509Certificate")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = { 
		"X509Certificate"
})
public class X509Certificate {
	
	private String X509Certificate;

	public String getX509Certificate() {
		return X509Certificate;
	}

	public void setX509Certificate(String x509Certificate) {
		X509Certificate = x509Certificate;
	}
	
}
