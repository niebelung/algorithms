import java.lang.*;
import java.util.TreeMap;

public class RectArea {
	public RectArea() {
		eventsQueue_ = new TreeMap<Integer, Rectangle>();
	}

	private class Interval {
        public Interval(int lo, int hi) {
        	lo_ = lo;
        	hi_ = hi;
        }

        private int lo_;
        private int hi_;

        public int lo() {
        	return lo_;
        }

        public int hi() {
        	return hi_;
        }
	}

    private class Rectangle {
        public Rectangle(int xLo,int yLo, int xHi, int yHi) {
        	if (xHi - xLo == 0) {
        		throw new IllegalArgumentException("Zero length X interval");
        	}
        	if (yHi - yLo == 0) {
        		throw new IllegalArgumentException("Zero length Y interval");
        	}
            intervalX_ = new Interval(Math.min(xLo, xHi),Math.max(xLo, xHi));
            intervalY_ = new Interval(Math.min(yLo, yHi),Math.max(yLo, yHi));
        }

        public Interval intervalX() {
        	return intervalX_;
        }

        public Interval intervalY() {
        	return intervalY_;
        }

        private Interval intervalX_;
        private Interval intervalY_;
    }

    private TreeMap eventsQueue_;

}
