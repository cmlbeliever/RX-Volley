package com.cml.lib.rxvolley.framework;

import android.graphics.Bitmap.CompressFormat;

public class ImageCaches {

	// Default memory cache size as a percent of device memory class
	public static final float DEFAULT_MEM_CACHE_PERCENT = 0.15f;

	public interface Disk {
		int Size = 1024 * 1024 * 100;
		CompressFormat Format = CompressFormat.PNG;
		// PNG is lossless so quality is ignored,but must be provided
		int Quality = 100;
	}

	public interface Memory {
		int Size = 1024 * 1024 * 10;
		CompressFormat Format = CompressFormat.PNG;
		// PNG is lossless so quality is ignored,but must be provided
		int Quality = 100;
	}

	/**
	 * Sets the memory cache size based on a percentage of the max available VM
	 * memory. Eg. setting percent to 0.2 would set the memory cache to one
	 * fifth of the available memory. Throws {@link IllegalArgumentException} if
	 * percent is < 0.05 or > .8. memCacheSize is stored in kilobytes instead of
	 * bytes as this will eventually be passed to construct a LruCache which
	 * takes an int in its constructor.
	 * 
	 * This value should be chosen carefully based on a number of factors Refer
	 * to the corresponding Android Training class for more discussion:
	 * http://developer.android.com/training/displaying-bitmaps/
	 * 
	 * @param percent
	 *            Percent of memory class to use to size memory cache
	 * @return Memory cache size in KB
	 */
	public static int calculateMemCacheSize(float percent) {
		if (percent < 0.05f || percent > 0.8f) {
			throw new IllegalArgumentException(
					"setMemCacheSizePercent - percent must be "
							+ "between 0.05 and 0.8 (inclusive)");
		}
		return Math.round(percent * Runtime.getRuntime().maxMemory() / 1024);
	}
}
