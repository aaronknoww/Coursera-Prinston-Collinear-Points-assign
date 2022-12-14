import java.util.Arrays;

public class BruteCollinearPoints
{ 

    private Point[] _points;
    private LineSegment[] segmentos;
    private Double[] pendientes;
    private Linea[] lineaAux;
    private int counterCtr;//-----------> To have control of the positions of the two arrays lineaAux and Pendientes.
    private int segmentCounter;
     

    public BruteCollinearPoints(Point[] points)//--->finds all line segments containing 4 points
    {
        if (points == null)
            throw new IllegalArgumentException("An Array with a null value is not permitted");
        int i = 1;
        for (Point p : points)
        {
            if (p == null)
                throw new IllegalArgumentException("A point with a null value is not permitted"); 
            for (int j = i; j < points.length; j++)
            {
                if (p==points[i])
                    throw new IllegalArgumentException("A repeated point is not permitted");   
            }
            i++;
        }     
        counterCtr = 0;
        this._points = points;
        pendientes = new Double[_points.length*3];
        lineaAux = new Linea[_points.length*3];
        segmentCounter = 0;
    }
    public int numberOfSegments()//----------------->the number of line segments
    {
        
        return segmentCounter;
    }
    public LineSegment[] segments()//--------------->the line segments
    {
        Arrays.sort(_points);
        if (_points.length==1)
            return new LineSegment[0]; 
      
        for (int i = 0; i < _points.length-1; i++)
        {
            for (int j = i+1; j < _points.length; j++)
            {
                conectSegment(_points[i], _points[j]);                    
                
            }
            
        }

        segmentos = new LineSegment[counterCtr];
        fourPoints();//--------------------------->Find segments with 4 points.
        
        return segmentos;
    }

    // ||||||||||| PRIVATE AREA ||||||||||||||

    private class Linea
    {
        public Point p1;
        public Point p2;
        public int cantidadP;//-----> To store how many poits belong to the current line.
        Linea(Point p, Point q)
        {
            this.p1 = p;
            this.p2 = q;
            cantidadP=2; 
        }
    }
    /*
     * If the slope is found, it returns an integer that represents the index where it was found,
     * otherwise it returns -1
     * @return a integer
     */
    private int findSlope(Double pen)
    {
                       
        for (int i = 0; i < pendientes.length; i++)
        {
            if(pendientes[i]==null)
                break;
            if (Double.compare(pendientes[i], pen) == 0)
                return i;
         
        }
        return -1;

    }
    private void conectSegment(final Point point1, final Point point2)
    {
        Double slope = point1.slopeTo(point2);
        int index = findSlope(slope);
        if( index == -1)
        {
            lineaAux[counterCtr]= new Linea(point1, point2);
            pendientes[counterCtr] = slope;
            counterCtr++;
            return;
        }        
        if(lineaAux[index].p2.compareTo(point2) == -1 )
        {
            lineaAux[index].p2 = point2;
            lineaAux[index].cantidadP++;  
        }

        //lineaAux[index].p2 = (lineaAux[index].p2.compareTo(point2) == -1 ) ? point2 : lineaAux[index].p2;
        
    }
    private void fourPoints()
    {
        
        int i=0;
        
        while (lineaAux[i]!= null)
        {
            if(lineaAux[i].cantidadP==4)
            {
                segmentos[i] = new LineSegment(lineaAux[i].p1, lineaAux[i].p2);
                segmentCounter++;
            }
            
            i++;
        }        
    }
}
