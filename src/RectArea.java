import java.lang.*;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.charset.*;
import java.lang.Runtime;

public class RectArea {
	public static final int MAX_COORD_VAL = 10000;
	public static final int MAX_RECTS = 100;
	public static final int MIN_RECTS = 1;

	public RectArea() {
		eventsQueue_ = new ArrayList<RectangleEdge>();
		rects_ = new ArrayList<Rectangle>();
		activeRects_ = new ArrayList<Rectangle>();
		activeIntervals_ = new ArrayList<Interval>();
		area_ = 0;
        isInWork_ = false;
        lastX_ = 0;
	}

	private class Interval {
        public Interval() {
            lo_ = 0;
            hi_ = 0;
        }

        public Interval(Interval other) {
            lo_ = other.lo();
            hi_ = other.hi();
        }

		public Interval(int lo, int hi) {
        	lo_ = Math.min(lo, hi);
        	hi_ = Math.max(lo,hi);
        }

        private int lo_;
        private int hi_;

        public int lo() {
        	return lo_;
        }

        public int hi() {
        	return hi_;
        }

        public void setLo(int lo) {
            if (lo > hi_) {
                lo_ = hi_;
                hi_ = lo;
            } else {
                lo_ = lo;
            }
        }

        public void setHi(int hi) {
            if (hi < lo_) {
                hi_ = lo_;
                lo_ = hi;
            } else {
                hi_ = hi;
            }
        }

        public int len() {
        	return hi_ - lo_;
        }

        public boolean intersects(Interval other) {
            if ((this.contains(other.hi()) && this.contains(other.lo())) ||
                (this.contains(other.hi()) && !this.contains(other.lo())) ||
                (!this.contains(other.hi()) && this.contains(other.lo())) ||
                (other.contains(this.hi()) && other.contains(this.lo()))) {
                return true;
            } else {
                return false;
            }
        }

        public Interval intersection(Interval other) {
            if(this.contains(other.hi()) && this.contains(other.lo())) {
                    return new Interval(other.lo(),other.hi());
            } else if (this.contains(other.hi()) && !this.contains(other.lo())) {
                    return new Interval(this.lo(),other.hi());
            } else if(!this.contains(other.hi()) && this.contains(other.lo())){
                return new Interval(other.lo(),this.hi());
            } else if(other.contains(this.hi()) && other.contains(this.lo())) {
                    return new Interval(this.lo(),this.hi());
            } else {
        		return new Interval();
        	}
        }

        public boolean contains(int point) {
        	if (point >= lo_ && point <= hi_) {
        		return true;
        	}
        	return false;
        }

	}

    private class Rectangle {
        @Override
        public boolean equals(Object obj) {

        	if (obj instanceof Rectangle) {
        		Rectangle other = (Rectangle)obj;
        		return this.intervalX().lo() == other.intervalX().lo() &&
        		       this.intervalX().hi() == other.intervalX().hi() &&
				       this.intervalY().lo() == other.intervalY().lo() &&
        		       this.intervalY().hi() == other.intervalY().hi();
        	} else {
        		return false;
        	}
        }

    	public Rectangle(int xLo,int yLo, int xHi, int yHi) {
            intervalX_ = new Interval(Math.min(xLo, xHi),Math.max(xLo, xHi));
            intervalY_ = new Interval(Math.min(yLo, yHi),Math.max(yLo, yHi));
        }

        public Rectangle(Interval intervalX, Interval intervalY) {
        	intervalX_ = intervalX;
        	intervalY_ = intervalY;
        }

        public Rectangle() {
        	intervalX_ = new Interval();
        	intervalY_ = new Interval();
        }

        public Interval intervalX() {
        	return intervalX_;
        }

        public Interval intervalY() {
        	return intervalY_;
        }

        public int area() {
        	return intervalX_.len() * intervalY_.len();
        }

        public Rectangle intersection (Rectangle other) {
            Interval intX = this.intervalX().intersection(other.intervalX());
            Interval intY = this.intervalY().intersection(other.intervalY());
            if (intX.len() == 0 || intY.len() == 0) {
            	return new Rectangle();
            }
            return new Rectangle(intX, intY);
        }

        private Interval intervalX_;
        private Interval intervalY_;
    }

    private class RectangleXComparator implements Comparator<Rectangle> {
    	@Override
    	public int compare(Rectangle rLeft, Rectangle rRight) {
    		if(rLeft.intervalX().lo() > rRight.intervalX().lo()) {
    			return 1;
    		} else if (rLeft.intervalX().lo() < rRight.intervalX().lo()){
    			return -1;
    		} else {
    			return 0;
    		}
    	}
    }

    private class RectangleYComparator implements Comparator<Rectangle> {
    	@Override
    	public int compare(Rectangle rLeft, Rectangle rRight) {
    		if(rLeft.intervalY().lo() > rRight.intervalY().lo()) {
    			return 1;
    		} else if (rLeft.intervalY().lo() < rRight.intervalY().lo()){
    			return -1;
    		} else {
    			return 0;
    		}
    	}
    }

    private class RectangleEdge {
    	private Integer x_;
    	private Rectangle r_;
    	RectangleEdge(Integer x, Rectangle r) {
    		x_ = x;
    		r_ = r;
    	}
    	public Integer x() {
    		return x_;
    	}
    	public Rectangle rect() {
    		return r_;
    	}
    }

    private class RectangleEdgeComparator implements Comparator<RectangleEdge> {
    	@Override
    	public int compare(RectangleEdge left, RectangleEdge right) {
    		if(left.x() > right.x()) {
    			return 1;
    		} else if (left.x() < right.x()){
    			return -1;
    		} else {
    			return 0;
    		}
    	}
    }

    private ArrayList<RectangleEdge> eventsQueue_;
    private ArrayList<Rectangle> rects_;
    private ArrayList<Rectangle> activeRects_;
    private ArrayList<Interval> activeIntervals_;
    private Interval currentInterval_;
    private int area_;
    private int lastX_;
    private boolean isInWork_;
    
    public void add(int xLo,int yLo, int xHi, int yHi) {
    	if (Math.abs(xLo) > MAX_COORD_VAL ||
    			Math.abs(yLo) > MAX_COORD_VAL ||
    			Math.abs(xHi) > MAX_COORD_VAL ||
    			Math.abs(yHi) > MAX_COORD_VAL) {
    		throw new IllegalArgumentException("Max coordinate value exceeded");
    	}

    	Rectangle r = new Rectangle(xLo,yLo,xHi,yHi);
        if(r.area() > 0) {
        	Iterator<Rectangle> it = rects_.iterator();
        	while(it.hasNext()) {
        		if (it.next().equals(r)) {
        			it.remove();
        		}
        	}
        	rects_.add(r);
    	}
    }

    public int calc() {
        Collections.sort(rects_, new RectangleXComparator());
        Collections.sort(rects_, new RectangleYComparator());
        for(Rectangle rect : rects_) {
        	eventsQueue_.add(new RectangleEdge(rect.intervalX().lo(), rect));
        	eventsQueue_.add(new RectangleEdge(rect.intervalX().hi(), rect));
        }
        Collections.sort(eventsQueue_, new RectangleEdgeComparator());

        while(!eventsQueue_.isEmpty()) {
        	RectangleEdge e = eventsQueue_.remove(0);
        	Integer x = e.x();
            if(!isInWork_) {
                isInWork_ = true;
                lastX_ = x;
            } else {
                if(x != lastX_) {
                    Collections.sort(activeRects_, new RectangleYComparator());
                    currentInterval_ = null;
                    activeIntervals_.clear();
                    for (Rectangle item : activeRects_) {
                        if (currentInterval_ == null) {
                            currentInterval_ = new Interval(item.intervalY().lo(), item.intervalY().hi());
                        } else {
                            if(currentInterval_.intersects(item.intervalY())) {
                                currentInterval_.setLo(Math.min(currentInterval_.lo(),item.intervalY().lo()));
                                currentInterval_.setHi(Math.max(currentInterval_.hi(),item.intervalY().hi()));
                            } else {
                                activeIntervals_.add(currentInterval_);
                                currentInterval_ = item.intervalY();
                            }
                        }
                    }
                    activeIntervals_.add(currentInterval_);
                    for (Interval item : activeIntervals_) {
                        area_ += item.len() * (x - lastX_);
                    }
                    lastX_ = x;
                }
            }
            
        	Rectangle r = e.rect();
        	if (x == r.intervalX().lo()) {
        		activeRects_.add(r);
        	} else {
        		activeRects_.remove(r);
        	}
        }
    	return area_;
    }

    public static void main(String[] args) {
    	if(args.length != 2) {
    		throw new IllegalArgumentException("Invalid number of arguments : 2 needed");
    	}
    	String inputFile = args[0];
    	String outputFile = args[1];
    	try {
	        InputStream fis = new FileInputStream(inputFile);
	        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
	        BufferedReader br = new BufferedReader(isr);
	        String line;

	        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");

	        RectArea ra = new RectArea();

	        while ((line = br.readLine()) != null) {
	            String [] coords = line.split("\\s+");
	            if (coords.length != 4) {
	            	continue;
	            }
	            ra.add(Integer.parseInt(coords[0]),
	            	   Integer.parseInt(coords[1]),
	            	   Integer.parseInt(coords[2]),
	            	   Integer.parseInt(coords[3]));
	        }

	        Runtime rt = Runtime.getRuntime();

	        System.out.printf("Memory usage [bytes] : used %d free %d total %d\n",
	        		          rt.totalMemory() - rt.freeMemory(),rt.freeMemory(),rt.totalMemory());

	        writer.print(String.valueOf(ra.calc()) + "\n");
	        writer.close();
    	} catch(Exception e) {
    		System.out.printf("%s\n",e.toString());
    	}
    }
}
