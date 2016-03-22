package com.greentower;

/**
 * Contains static helper methods for mathematical operations.
 * This class cannot be instantiated.
 */
public final class MathUtils {

	/**
	 * Private constructor to avoid this class from getting
	 * instantiated.
	 */
	private MathUtils() { }
	
	
	
	/**
	 * Assures that a float value is inside a closed interval.
	 * 
	 * @param value a float value.
	 * @param min the left end of the closed interval.
	 * @param max the right end of the closed interval.
	 * @return the value of the {@code max} parameter if the {@code value}
	 * parameter is outside the right border, the {@code min} if it is outside
	 * the left border. If the value is inside the interval, its value will be
	 * returned.
	 */
	public static float span(float value, float min, float max)
	{
		if(value > max)
			return max;
		else if(value < min)
			return min;
		else
			return value;
	}
}
