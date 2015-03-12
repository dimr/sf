import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import toxi.geom.Vec2D;

public class BBox {

	private final float tolerance = 0.00001f;
	private float left, top, right, bottom;
	
	public BBox(ArrayList<Vec2D> v) {
		/*
		 * COPY FIRST
		 */
		ArrayList<Vec2D> vecs = new ArrayList<Vec2D>(v.size());
		for (int i = 0; i < v.size(); i++) {
			vecs.add(new Vec2D(v.get(i)));
		}
		Collections.sort(vecs, new Comparator<Vec2D>() {

			@Override
			public int compare(Vec2D o1, Vec2D o2) {
				return o1.x < o2.x ? -1 : o1.x > o2.x ? 1 : 0;
			}

		});

		this.left = vecs.get(0).x;
		this.right = vecs.get(vecs.size() - 1).x;

		Collections.sort(vecs, new Comparator<Vec2D>() {

			@Override
			public int compare(Vec2D o1, Vec2D o2) {
				return o1.y < o2.y ? -1 : o1.y > o2.y ? 1 : 0;
			}

		});
		this.top = vecs.get(vecs.size() - 1).y;
		this.bottom = vecs.get(0).y;
		
		
		if (this.left > this.right)
			throw new java.lang.RuntimeException("Left- right mistake");

		if (this.top < this.bottom)
			throw new java.lang.RuntimeException("Top - Bottom mistake");
		vecs = null;
	}

	public float getLeft() {
		return left;
	}

	
	public float getTop() {
		return top;
	}

	

	public float getRight() {
		return right;
	}

	public float getBottom() {
		return bottom;
	}
	
	public Vec2D geoToPixel(Vec2D v, int AppletWidth, int AppletHeight){
		return new Vec2D(AppletWidth * (v.x - this.left)
				/ (this.right - this.left), AppletHeight - AppletHeight
				* (v.y - this.bottom) / (this.top- this.bottom));
	}


	@Override
	public String toString() {
		return "BBox [left=" + left + ", top=" + top + ", right=" + right
				+ ", bottom=" + bottom + "]";
	}

}
