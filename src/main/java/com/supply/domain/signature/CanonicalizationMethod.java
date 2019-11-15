package com.supply.domain.signature;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="CanonicalizationMethod")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = { 
		"Algorithm"
})
public class CanonicalizationMethod 
{

	@XmlAttribute(name = "Algorithm")
	private String Algorithm = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

	public String getAlgorithm() {
		return Algorithm;
	}

	public void setAlgorithm(String algorithm) {
		Algorithm = algorithm;
	}

}
