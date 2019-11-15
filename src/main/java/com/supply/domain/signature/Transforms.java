package com.supply.domain.signature;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="Transform")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"Transform",
})
public class Transforms implements Serializable{

	private static final long serialVersionUID = 8960563108366074327L;
	
	private Transform Transform;

	public Transform getTransform() {
		return Transform;
	}

	public void setTransform(Transform transform) {
		Transform = transform;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
