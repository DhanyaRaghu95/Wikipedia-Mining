package myCode;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import edu.jhu.nlp.wikipedia.WikiTextParser;
import java.net.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;

/**
 * @author Dhanya Raghu
 *
 */

public class XmlStrArticleName {
	public String xmlArticle;
	public String article;
	
	public XmlStrArticleName(String xmlArticle,String article)
	{
		this.xmlArticle=xmlArticle;
		this.article=article;
	}
	
	public void addXmlString(String xmlArticle)
	{
		this.xmlArticle=xmlArticle;
	}
	public void addArticleName(String article)
	{
		this.article=article;
	}
	public String getXmlArticle()
	{
		return xmlArticle;
	}
	public String getArticleName()
	{
		return article;
	}
}
