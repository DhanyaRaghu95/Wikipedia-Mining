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
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;


/**
 * @author Dhanya Raghu
 *
 */
public class tryForCategoryExtraction  {
	
	int totalPath = 0;
	int relativenessIndex=0;
	URL graphroot = null;
	DirectedGraph<URL, DefaultEdge> hrefGraph= new DefaultDirectedGraph<URL, DefaultEdge>(DefaultEdge.class);
//	DirectedGraph<URL, DefaultEdge> g =          
	public static void main(String[]args) throws ClientProtocolException, IOException
{
		tryForCategoryExtraction tfce=new tryForCategoryExtraction();
		String article="Lists_of_occupations";
		tfce.getFromHttpClient(article);
		
}
	
	public void getFromHttpClient(String article) throws ClientProtocolException, IOException
	{
		String xmlArticle1;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// to get the xml format of the wiki articles : 
        HttpGet httpGet1 = new HttpGet("http://en.wikipedia.org/wiki/Special:Export/"+article);
      //  HttpGet httpGet2=new HttpGet("http://en.wikipedia.org/wiki/Special:Export/" + article2);
        CloseableHttpResponse response1 = httpclient.execute(httpGet1);
      //  CloseableHttpResponse response2 = httpclient.execute(httpGet2);
        try {
        //    System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
          //  HttpEntity entity = response.getEntity();
            xmlArticle1= EntityUtils.toString(entity1);
       //     System.out.println(xmlArticle1);
            EntityUtils.consume(entity1);
        	} finally {
            response1.close();
            }
        getSubCategoryLinks(xmlArticle1,article);
	}
	
	
	public void getSubCategoryLinks(String xmlArticle1,String article) throws ClientProtocolException, IOException
	{
		int i=0;
		 int count=0;
		String wikitext=xmlArticle1;
		 
		WikiTextParser wtp=new WikiTextParser(xmlArticle1);
		Vector <String> vs = wtp.getLinks();
		String [] listOfSubCategories = new String[vs.size()];
        Enumeration <String> es = vs.elements();
   //     System.out.println("the links are");
        while(es.hasMoreElements())
        {
        	listOfSubCategories[i++]=es.nextElement();   
        	count++;
        }
   //     for(int j=0;j<count;j++)
   //     	System.out.println(listOfSubCategories[j]);
         hrefGraph = createHrefGraph(article,count,listOfSubCategories);
      //  System.out.println(hrefGraph.toString());
        
     findOneMoreLevel(listOfSubCategories,count);
       System.out.println("start of bfs traversal");
       dfsOfGraph(hrefGraph);
       System.out.println("end of bfs traversal");
  /*    // chuck this later
       Scanner in=new Scanner(System.in);
       System.out.println("graphroot "+graphroot);
       String articleOned=in.next();
       URL articleOne=new URL("http://en.wikipedia.org/wiki/"+articleOned);
       Set<DefaultEdge> setOfEdges=hrefGraph.getAllEdges(graphroot,articleOne);
       Iterator printEdges=setOfEdges.iterator();
    //   System.out.println("empty or not "+printEdges.hasNext());
       while(printEdges.hasNext())
    	   System.out.println("1 " + printEdges.next());
       
 //      */
      getTwoNamesForWeight(); 
	}
	
	public DirectedGraph<URL, DefaultEdge> createHrefGraph(String article,int count,String [] listOfSubCategories)
	{
		List<URL> childrenOfRoot = new ArrayList<URL>();
	String tempList[]=new String[count];
        
      //  System.out.println("root is "+article);
    //     System.out.println("size is "+count);
      //   for(int i=0;i<count;i++)
        //	 System.out.println(listOfSubCategories[i]);
      try {
    	  // create the node root
            URL root = new URL("http://en.wikipedia.org/wiki/"+article);
            if (graphroot == null) graphroot = root;
            //the root has a list of sub-categories which are all its children, implemented as a list
            //create a new node for each sub-category
            for(int y=0;y<count;y++)
            {
            	tempList[y]=listOfSubCategories[y].replaceAll(" ", "_");
            }	
            for(int i = 0; i < count; i++) {
                childrenOfRoot.add(new URL("http://en.wikipedia.org/wiki/"+tempList[i]));
            }
            
            // add the root
            hrefGraph.addVertex(root);
            System.out.println("adding graph root "+root);
            // add the other vertices
            for(int w=0;w<count;w++)
            {
            	hrefGraph.addVertex(childrenOfRoot.get(w));	
            	//System.out.println("adding vertex "+childrenOfRoot.get(w));
            }	
         // add edges to create linking structure
            for(int e=0;e<count;e++)
            {
            	hrefGraph.addEdge(root, childrenOfRoot.get(e));
            	//System.out.println("adding edge "+root+" "+childrenOfRoot.get(e));
            }
    /*        Set<DefaultEdge> setOfEdges=hrefGraph.getAllEdges(graphroot,childrenOfRoot.get(2));
            Iterator printEdges=setOfEdges.iterator();
         //   System.out.println("empty or not "+printEdges.hasNext());
            while(printEdges.hasNext())
         	   System.out.println("1 " + printEdges.next()); */
            	
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
      	return hrefGraph;
    }
	
	
	public void findOneMoreLevel(String listOfSubCategories[],int count) throws ClientProtocolException, IOException
	{
		for(int i=0;i<count;i++)
		{
			String currentArticleWithUrl=listOfSubCategories[i];
			String currentArticle=currentArticleWithUrl.replace("http://en.wikipedia.org/wiki/", "null");
			String currentArticleProper=currentArticle.replaceAll(" ", "_");
		//	System.out.println(currentArticleProper);
			getFromHttpClientTwo(currentArticleProper);
		}
		
	}
	public void getFromHttpClientTwo(String article) throws ClientProtocolException, IOException
	{
		String xmlArticle1;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// to get the xml format of the wiki articles : 
        HttpGet httpGet1 = new HttpGet("http://en.wikipedia.org/wiki/Special:Export/"+article);
      //  HttpGet httpGet2=new HttpGet("http://en.wikipedia.org/wiki/Special:Export/" + article2);
        CloseableHttpResponse response1 = httpclient.execute(httpGet1);
      //  CloseableHttpResponse response2 = httpclient.execute(httpGet2);
        try {
       //     System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
          //  HttpEntity entity = response.getEntity();
            xmlArticle1= EntityUtils.toString(entity1);
       //     System.out.println(xmlArticle1);
            EntityUtils.consume(entity1);
        	} finally {
            response1.close();
            }
        getSubCategoryLinksTwo(xmlArticle1,article);
	}
	
	public void getSubCategoryLinksTwo(String xmlArticle1,String article) throws ClientProtocolException, IOException
	{
		int i=0;
		 int count=0;
		 String tempListTwo[];
		String wikitext=xmlArticle1;
		List<URL> childrenOfRoot = new ArrayList<URL>();
		WikiTextParser wtp=new WikiTextParser(xmlArticle1);
		Vector <String> vs = wtp.getLinks();
		String [] listOfSubCategories = new String[vs.size()];
        Enumeration <String> es = vs.elements();
     //   System.out.println("the links are");
        while(es.hasMoreElements())
        {
        	listOfSubCategories[i++]=es.nextElement();   //the array gets overwritten each time for each article calling it
        	count++;
        }
        tempListTwo=new String[count];
        for(int y=0;y<count;y++)
        	tempListTwo[y]=listOfSubCategories[y].replaceAll(" ", "_");
        URL currentNode = new URL("http://en.wikipedia.org/wiki/"+article);
        hrefGraph.addVertex(currentNode);
     //   System.out.println("next level adding vertices and edges");
     //   System.out.println("adding vertex "+currentNode);
        for(int p=0;p<count;p++)
        {
        	childrenOfRoot.add(new URL("http://en.wikipedia.org/wiki/"+tempListTwo[p]));
        }
        
        for(int p=0;p<count;p++)
        {
        	hrefGraph.addVertex(childrenOfRoot.get(p));
        	//System.out.println("adding vertex "+childrenOfRoot.get(p));
        }
        	
     // add edges to create linking structure
        for(int q=0;q<count;q++)
        {
        	hrefGraph.addEdge(currentNode, childrenOfRoot.get(q));
        //	System.out.println("adding edges "+currentNode + " "+childrenOfRoot.get(q));
        }
        	
   
	}
	
	public void dfsOfGraph(DirectedGraph<URL,DefaultEdge> hrefGraph)
	{
		GraphIterator<URL, DefaultEdge> iterator = 
                new DepthFirstIterator<URL, DefaultEdge>(hrefGraph);
        while (iterator.hasNext()) {
            System.out.println( iterator.next() );
            
	}
}
	public void getTwoNamesForWeight() throws MalformedURLException
	{
		Scanner in=new Scanner(System.in);
		String article1,article2;
		System.out.println("enter two vertices");
		
		article1=in.next();
	//	System.out.println("enter another");
		article2=in.next();
		convertToUrl(article1,article2);
	}
	public void convertToUrl(String article1,String article2) throws MalformedURLException
	{
		int size1=0,size2=0;
		String articleFull1=("http://en.wikipedia.org/wiki/"+article1);
		String articleFull2=("http://en.wikipedia.org/wiki/"+article2);
		String currentArticle1=articleFull1.replaceAll(" ", "_");
		String currentArticle2=articleFull2.replaceAll(" ", "_");
		URL articleOne=new URL(currentArticle1);
		URL articleTwo=new URL(currentArticle2);
		size1=getShortestPath(graphroot,articleOne);
		size2=getShortestPath(graphroot,articleTwo);
		totalPath=size1+size2;
		getRelativeIndex(totalPath);
		
	}
	public int getShortestPath(URL article1,URL article2)
	{
		System.out.println("Shortest path from"+article1+"  to "+article2+" ");
        List path = DijkstraShortestPath.findPathBetween(hrefGraph,article1,article2);
        System.out.println("size of the path list is "+path.size());
        System.out.println(path + "\n");
        return path.size();
	}
	
	public void getRelativeIndex(int size)
	{
		double relativenessIndex=0;
		for(int i=1;i<=size;i++)
		{
			relativenessIndex= relativenessIndex+ (1.0/(Math.pow(2,i)));
		}
	
	System.out.println("Relativeness Index is "+relativenessIndex);
	}
}
	

