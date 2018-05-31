package myCode;

public class GeometricProgression {
	public static void main(String[]args)
	{
		int size=4;
		double relativenessIndex=0;
		for(int i=1;i<=size;i++)
		{
			relativenessIndex= relativenessIndex+ (1.0/(Math.pow(2,i)));
		}
	
	System.out.println(""+relativenessIndex);
	}
}
