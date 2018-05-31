package myCode;

public class Node {
	
	
		private Node next;
		private String artName;
		
		public Node()
		{
			
		}
		
		public Node(String val)
		{
			artName=val;
		}

		public void setStr(String v)
		{
			artName = v;
		}

		public void setNext(Node n)
		{
			next = n;
		}

		public String getStr()
		{
			return artName;
		}

		public Node getNext()
		{
			return next;
		}
}




