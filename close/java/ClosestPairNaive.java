public class ClosestPairNaive {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    
    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "NAIVE (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static void findClosestPair(XYPoint points[], boolean print)
    {
	 int nPoints = points.length;
	 double dmin = INF;
	 XYPoint naiveResult1 = new XYPoint();
	 XYPoint naiveResult2 = new XYPoint();
	 

		 for(int i = 0; i < nPoints-1; i ++){  
             for(int j = i + 1; j < nPoints; j ++){  
                 double tempD = points[i].dist(points[j]);  
                 if(tempD < dmin){  
                     dmin = tempD;  
                     naiveResult1 = points[i];  
                     naiveResult2 = points[j];  
                 }  
             }
          }
		 
	
	 if (print)
	   System.out.println("NAIVE " + naiveResult1 + " " + naiveResult2 + " " + dmin);
}
}
