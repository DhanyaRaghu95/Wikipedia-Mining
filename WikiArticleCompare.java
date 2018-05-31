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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import edu.jhu.nlp.wikipedia.WikiTextParser;


/**
 * @author Dhanya Raghu
 *
 */
public class WikiArticleCompare {
	

	public static void main(String[]args) throws IOException
	{
		WikiArticleCompare wac=new WikiArticleCompare();
		wac.readArticles();
	}	
	
	public void readArticles() throws IOException
	{
		Scanner in=new Scanner(System.in);
		String article1,article2;
		article1=in.next();
		article2=in.next();
		getXMLFormatFromHttpClient(article1,article2);
	}
	
	public void getXMLFormatFromHttpClient(String article1,String article2) throws IOException
	{
		String xmlArticle1,xmlArticle2;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// to get the xml format of the wiki articles : 
        HttpGet httpGet1 = new HttpGet("http://en.wikipedia.org/wiki/Special:Export/"+article1);
        HttpGet httpGet2=new HttpGet("http://en.wikipedia.org/wiki/Special:Export/" + article2);
        CloseableHttpResponse response1 = httpclient.execute(httpGet1);
        CloseableHttpResponse response2 = httpclient.execute(httpGet2);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
          //  HttpEntity entity = response.getEntity();
            xmlArticle1= EntityUtils.toString(entity1);
                    System.out.println(xmlArticle1);
                
           
            
           // System.out.println("first page done");
            EntityUtils.consume(entity1);
          //  System.out.println("first page done");
            
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            xmlArticle2 = EntityUtils.toString(entity2);
            System.out.println(xmlArticle2);
            EntityUtils.consume(entity2);
           // System.out.println("second page done");
        } finally {
            response1.close();
            response2.close();
        }
        getCategories(xmlArticle1,xmlArticle2);
	}
	
	public void getCategories(String article1,String article2)
	{
		int count1=0,count2=0,i=0,j=0;
		String temp2;
		String temp2Array[]=new String[100];
		String temp1Array[]=new String[100];
		String tempForCategory1,tempForCategory2;
		boolean flag=false;
		String wikiText = article1;
		String wikiText2 = article2;
        WikiTextParser wtp = new WikiTextParser(wikiText);
        WikiTextParser wtp2 = new WikiTextParser(wikiText2);
        Vector <String> vs = wtp.getCategories();
        Enumeration <String> es = vs.elements();
        Enumeration <String> esTemp=vs.elements();
        Vector <String> vs2 = wtp2.getCategories();
        Enumeration <String> es2 = vs2.elements();
        Enumeration <String> esTemp2=vs2.elements();
        
        while(esTemp.hasMoreElements())
        {
        	temp1Array[j++]=esTemp.nextElement();
        	count1++;
        }
        System.out.println("count1 is "+count1);
       
        while(esTemp2.hasMoreElements())
        {
        	temp2Array[i++]=esTemp2.nextElement();
        	count2++;
        }
        System.out.println("count2 is "+count2);
    //    System.out.println("Categories of second page: end");	
        //if first wiki article has more fields in categories :
        System.out.println("Categories of first page are:");
        for(i=0;i<count1;i++)
        	System.out.println(temp1Array[i]);
        System.out.println("Categories of second page are:");
        for(i=0;i<count2;i++)
        	System.out.println(temp2Array[i]);
        
    /*    if(count1>=count2)
        {
        	while(es.hasMoreElements())
            {
        		tempForCategory1=es.nextElement();
            	while(es2.hasMoreElements())
            	{
            		
            		tempForCategory2=es2.nextElement();
            		System.out.println("first category is "+tempForCategory1);
            		System.out.println("second category is "+tempForCategory2);
            	if(tempForCategory1.equals(tempForCategory2))
            	{
            		flag=true;
                	System.out.println("Common category is : "+tempForCategory2);
                	break;
            	}
            		
            		
            }
    	}
        }
        
        if(flag==false)
        	System.out.println("not a common category");   */
        for(i=0;i<count1;i++)
        	for(j=0;j<count2;j++)
        	{
        		if(temp1Array[i].equalsIgnoreCase(temp2Array[j]))
        		{
        			flag=true;
        			System.out.println("common category is "+temp1Array[i]);
        		}
        	}
        if(flag==false)
        	System.out.println("no common category found");
	}
}
