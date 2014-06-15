package com.codepath.imagesearch;

import java.io.Serializable;

/**
 * Class to keep all user settings for image search.
 * 
 * @author sachingupta
 *
 */
public class UserSettings implements Serializable {
	private static final long serialVersionUID = 6617146626094878012L;
	private static String EMPTY_STRING = "";
	
	public String getImageSize() {
		return imageSize.equals("all") ? EMPTY_STRING : imageSize;
	}

	public String getImageColor() {
		return imageColor.equals("all") ? EMPTY_STRING : imageColor;
	}

	public String getImageType() {
		return imageType.equals("all") ? EMPTY_STRING : imageType;
	}

	public String getWebsite() {
		return website == null ? EMPTY_STRING : website;
	}

	public String imageSize;
	public String imageColor; 
	public String imageType;
	public String website;
	
	UserSettings(String imageSize, String imageColor, String imageType, String website) {
		this.imageSize = imageSize;
		this.imageColor = imageColor;
		this.imageType = imageType;
		this.website = website;
	}
	
	public String toString() {
		return String.format("%s.%s.%s.%s", imageSize, imageColor, imageType, website);
	}
}
