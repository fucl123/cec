package com.supply.custom;

import com.supply.core.ClientException;
import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.utils.Base64;
import org.apache.xml.security.utils.IgnoreAllErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class CustomSign 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomSign.class);
	
	@SuppressWarnings("restriction")
	private static final IgnoreAllErrorHandler IGNORE_ALL_ERROR_HANDLER = new IgnoreAllErrorHandler();

	/**
	 * 加密加签工具
	 */
	public PKI pki;
	
	/**
	 * xml解析工具
	 */
	public DocumentBuilderFactory documentBuilderFactory;

	/**
	 * xml转换工具
	 */
	public TransformerFactory transformerFactory;

	/**
	 * xml转换工具
	 */
	public Transformer transFormer;

	/**
	 * usb keyName
	 */
	public String keyName;

	/**
	 * usb 证书
	 */
	public String x509Certificate;
	
	public CustomSign(String password) throws ClientException 
	{
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
		this.documentBuilderFactory.setNamespaceAware(true);
		this.documentBuilderFactory.setValidating(true);

		this.transformerFactory = TransformerFactory.newInstance();
		try 
		{
			this.pki = new PKI(password);

			this.transFormer = transformerFactory.newTransformer();
			
			this.keyName = new String(pki.getSignCertNo());

			this.x509Certificate = Base64.encode(pki.getKeyCert(0));

		} 
		catch (ClientException e) 
		{
			throw new ClientException("PKI init false. Error=" + e.getMessage());
		} 
		catch (TransformerConfigurationException e) 
		{
			throw new ClientException("transFormer init false. Error=" + e.getMessage());
		} 

	}

	/**
	 * 加签
	 * @param xml
	 * @return
	 * @throws ClientException
	 */
	public String doSign(String xml) throws ClientException
	{
		String signedXml = "";
		
		DOMSource ds;
		StringWriter sw;
		StreamResult sr;
		
		try 
		{
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			documentBuilder.setErrorHandler(IGNORE_ALL_ERROR_HANDLER);
			Document doc = documentBuilder.parse(new InputSource(new StringReader(xml)));

			// 原始xml摘要
			ds = new DOMSource(doc);
			sw = new StringWriter();
			sr = new StreamResult(sw);
			transFormer.transform(ds, sr);
			
			Init.init();
			Canonicalizer canon = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
			
			String canonicalizeStr = new String(canon.canonicalize(sw.toString().getBytes()));

			String digestValue  = "";
			synchronized(this)
			{
				digestValue = Base64.encode(pki.sha1Digest(canonicalizeStr.getBytes()));
			}
			
			// document 增加signature节点
			doc = this.createSignatureElement(doc, digestValue);

			// 读取签名段的报文
			NodeList nodeList = doc.getElementsByTagName("ds:SignedInfo");
			Node node = nodeList.item(0);
			canonicalizeStr = new String(canon.canonicalizeSubtree(node));
			
			
			String signatureValue = "";
			synchronized(this)
			{
				long start = System.currentTimeMillis();
				signatureValue = Base64.encode(pki.rsaSignData(canonicalizeStr.getBytes()));
				LOGGER.info("total sign time：{}ms,contentLength:{}",System.currentTimeMillis() - start, canonicalizeStr.length());
			}
			
			this.setTagValue(doc, "ds:SignatureValue", signatureValue);
			this.setTagValue(doc, "ds:KeyName", keyName);
			this.setTagValue(doc, "ds:X509Certificate", x509Certificate);
			
			ds = new DOMSource(doc);
			sw = new StringWriter();
			sr = new StreamResult(sw);

			transFormer.transform(ds, sr);
			signedXml = new String(canon.canonicalize(sw.toString().getBytes()));
		} 
		catch (SAXException e) 
		{
			throw new ClientException("对原始XML做解析时报错. Error=" + e.getMessage());
		} 
		catch (IOException e) 
		{
			throw new ClientException("对原始XML做解析时报错. Error=" + e.getMessage());
		}
		catch (CanonicalizationException e) 
		{
			throw new ClientException("对结果XML做校验时报错. Error=" + e.getMessage());
		} 
		catch (ParserConfigurationException e) 
		{
			throw new ClientException("对结果XML做校验时报错. Error=" + e.getMessage());
		} catch (TransformerException e) 
		{
			throw new ClientException("对结果XML做校验时报错. Error=" + e.getMessage());
		} catch (ClientException e) 
		{
			throw new ClientException("对结果XML做校验时报错. Error=" + e.getMessage());
		} catch (InvalidCanonicalizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		return signedXml;
	}
	
	/**
	 * 创建签名节点，初始化摘要信息
	 * @param doc
	 * @param digestValue
	 * @return
	 * @throws ClientException
	 */
	public Document createSignatureElement(Document doc, String digestValue) throws ClientException 
	{
		Element canonicalizationMethodElement = doc.createElement("ds:CanonicalizationMethod");
		canonicalizationMethodElement.setAttribute("Algorithm", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
		canonicalizationMethodElement.setTextContent("");
		Element signatureMethodElement = doc.createElement("ds:SignatureMethod");
		signatureMethodElement.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
		signatureMethodElement.setTextContent("");

		Element transformElement = doc.createElement("ds:Transform");
		transformElement.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#enveloped-signature");
		transformElement.setTextContent("");

		Element transformsElement = doc.createElement("ds:Transforms");
		transformsElement.appendChild(transformElement);
		
		//初始化摘要信息
		Element digestValueElement = doc.createElement("ds:DigestValue");
		digestValueElement.setTextContent(digestValue);
		
		Element digestMethodElement = doc.createElement("ds:DigestMethod");
		digestMethodElement.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
		Element referenceElement = doc.createElement("ds:Reference");
		referenceElement.setAttribute("URI", "");
		referenceElement.appendChild(transformsElement);
		referenceElement.appendChild(digestMethodElement);
		referenceElement.appendChild(digestValueElement);

		Element signedInfoElement = doc.createElement("ds:SignedInfo");
		signedInfoElement.setAttribute("xmlns:ceb", "http://www.chinaport.gov.cn/ceb");
		signedInfoElement.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
		signedInfoElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		
		signedInfoElement.appendChild(canonicalizationMethodElement);
		signedInfoElement.appendChild(signatureMethodElement);
		signedInfoElement.appendChild(referenceElement);

		Element signatureValueElement = doc.createElement("ds:SignatureValue");
		signatureValueElement.setTextContent("signaturValue");

		Element x509Certificate = doc.createElement("ds:X509Certificate");
		x509Certificate.setTextContent("");

		Element x509DataElement = doc.createElement("ds:X509Data");
		x509DataElement.appendChild(x509Certificate);
		
		Element keyNameElement = doc.createElement("ds:KeyName");
		keyNameElement.setTextContent("");

		Element keyInfoElement = doc.createElement("ds:KeyInfo");
		keyInfoElement.appendChild(keyNameElement);
		keyInfoElement.appendChild(x509DataElement);

		Element signatureElement = doc.createElement("ds:Signature");
		signatureElement.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
		signatureElement.appendChild(signedInfoElement);
		signatureElement.appendChild(signatureValueElement);
		signatureElement.appendChild(keyInfoElement);
		
		Element rootElement = doc.getDocumentElement();
		rootElement.appendChild(signatureElement);

		return doc;

	}
	
	/**
	 * 设置节点值
	 * @param doc
	 * @param tagName
	 * @param tagValue
	 */
	private void setTagValue(Document doc, String tagName, String tagValue) throws ClientException 
	{
		NodeList nodeList = doc.getElementsByTagName(tagName);
		if(nodeList != null && nodeList.getLength() > 0)
		{
			Node node = nodeList.item(0);
			node.setTextContent(tagValue);
		}
		else
		{
			throw new ClientException("设置节点值的时报错. 节点名称：" + tagName)  ;
		}
	}
	
//	public static void main(String[] args) throws Exception {
//		// byte[] rootData = FileUtils.readFileToByteArray(new
//		// File("C:\\Users\\xujianchao\\Desktop\\SANSEC-Cert.der"));
//		// String rootCert = new
//		// String(com.sansec.util.encoders.Base64.encode(rootData));
//
//		String plainStr = FileUtils.readFileToString(new File("d:\\aa.xml"));
//
//		// Custom customKey = new Custom("USBKey", "88888888", rootCert, 0, null);
//		// System.out.println(customKey.verfySign(plainStr));
//
//		CustomSign customKey = new CustomSign("USBKey", "88888888", null, 0, null);
//		System.out.println(customKey.doSign(plainStr));
//
//	}
}
