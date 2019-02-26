package astroidsPaket.klasser;

import java.awt.Graphics;

class Point {
	double x;
	double y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Kollar om två punkter är lika
	boolean equals(Point p) {
		if ((int)this.x == (int)p.x && (int) this.y == (int) p.y) {
			return true;
		}
		return false;
	}
}

public class Rectangle {
	Point[] points;
	double currentOrientation;
	private int recWidth;
	private int recHeight;
	private int recX;
	private int recY;

	Rectangle(int playerX, int playerY, int playerWidth, int playerHeight) {
		this.recWidth = playerWidth;
		this.recHeight = playerHeight;
		this.recX = playerX;
		this.recY = playerY;

		points = new Point[playerWidth * 2 + playerHeight * 2];
		// Skapar en ram för rektangeln utifrån dess fyra hörn punkter
		int arrayPos = 0;

		for (int i = playerX; i < playerX + playerWidth; i++) {
			points[arrayPos] = new Point(i, playerY);
			points[arrayPos+1] = new Point(i, playerY + playerHeight);
			arrayPos += 2;
		}
		for (int i = playerY; i < playerY + playerHeight; i++) {
			points[arrayPos] = new Point(playerX, i);
			points[arrayPos+1] = new Point(playerX + playerWidth, i);
			arrayPos +=2;
		}
		// En punkt (övre vänstra hörnet skrivs två gånger utav for-looparna medans nedre högra hörnet aldrig skrivs ut därför
		// skriver vi över en av de två platser i array:en som innehåller övre vänstra hörnet med punkten för undre högra.
		points[0] = new Point(playerX + playerWidth, playerY + playerHeight);
        
    }
   
    // Roterar rektangeln genom att rotera alla punkter i rektangelns ram runt origo
    // vinkeln (direction) är i radianer!
    void rotate(double direction) {
        double toRotate = currentOrientation - direction;
		currentOrientation = direction;

		for (Point p : points) {
			// flyttar rektangelns centrum till origo
			double tempX = p.x - recX - recWidth / 2;
			double tempY = p.y - recY - recHeight / 2;

			// Roterar hörnen
			double rotatedX = (tempX * Math.cos(toRotate)) - (tempY * Math.sin(toRotate));
			double rotatedY = (tempX * Math.sin(toRotate)) + (tempY * Math.cos(toRotate));

			// Flyttar tillbaks rektangeln till dess ursprungliga position
			p.x = rotatedX + recX + recWidth / 2;
			p.y = rotatedY + recY + recHeight / 2;

		}

	}
    
    // Returnerar längden för rektangelns diagonal
    double diagonal() {
    	double diag = Math.sqrt(recWidth*recWidth+recHeight*recHeight);
    	
		return diag;	
    }
    
    //Returnerar minsta avståndet från centerpunkten till en punkt på ramen (omkretsen) för rektangeln
    double minDistaceToBorder() {
    	
    	if(recWidth<recHeight) {
    		return recWidth/2;
    	}
        	
    	return recHeight/2;
	
    }
    
    // returnerar rektangelns mittpunkt
    Point center() {
    	double x = recX+ recWidth/2;
    	double y = recY + recHeight/2;
    	return new Point(x,y);
    }
    
    // Kollar om rektangeln överlappar en annan rektangel (R), returnerar true om de överlappar och annars false
    boolean intersect(Rectangle R) {
    	double xDiffrence = this.center().x-R.center().x;
    	double yDifference = this.center().y-R.center().y;
    	double distCenterToCenter= Math.sqrt(xDiffrence*xDiffrence+yDifference*yDifference);
    	
    	// Om avståndet mellan mittpunkterna är större än sammanlagda längden för diagonalerna delat på två kan rektanglarna
    	// int överlappa.
    	if(distCenterToCenter > this.diagonal()+R.diagonal()) {
    		return false;
    	}
    	
    	// Om avståndet mellan mittpunkterna är mindre än kortaste sträckan från en av rektanglarnas centerpunkt till en punkt på ramen
    	// Då överlappar alltid rektanglarna
    	if(R.minDistaceToBorder() < this.minDistaceToBorder()*2) {
    		if(distCenterToCenter < R.minDistaceToBorder()*2) {
    			return true;
    		}
    	} else {
    		if(distCenterToCenter < this.minDistaceToBorder()*2) {
    			return true;
    		} else if(distCenterToCenter < R.minDistaceToBorder()) {
    			return true;
    		}
    	}
    	
    	// I de fall som inte täcks av if-satserna för avståndet mellan centerpunkterna och diagonalerna.  Då jämför vi alla punkter 
    	//för de två rektanglarna och om två punkter i dess ramar överlappar överlappar, överlappar även rektanglarna.
    	for(Point p1: points) {
    		for(Point p2: R.points) {
    			if(p1.equals(p2)) {
    				return true;
    			}
    		}
    	}
    	
		return false;
   	
    }
    
    // Målar rektangeln genom att måla alla punkter i dess ram.
    void draw(Graphics g) {
    	for(Point p: points) {
    		g.drawLine((int) p.x,(int) p.y,(int) p.x,(int) p.y);
    	}
    }
}
