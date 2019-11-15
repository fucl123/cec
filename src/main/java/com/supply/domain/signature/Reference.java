package com.supply.domain.signature;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="Reference")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = { 
		"Transforms",
		"DigestMethod",
		"DigestValue",
		"URI",
})
public class Reference implements Serializable
{
	private static final long serialVersionUID = -4016382412352855026L;
	
	@XmlAttribute(name = "URI")
	private String URI = "";
	
	private Transforms Transforms;
	
	private DigestMethod DigestMethod;
	
	private String DigestValue;

	public Transforms getTransforms() {
		return Transforms;
	}

	public void setTransforms(Transforms transforms) {
		Transforms = transforms;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DigestMethod getDigestMethod() {
		return DigestMethod;
	}

	public void setDigestMethod(DigestMethod digestMethod) {
		DigestMethod = digestMethod;
	}

	public String getDigestValue() {
		return DigestValue;
	}

	public void setDigestValue(String digestValue) {
		DigestValue = digestValue;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}
}
