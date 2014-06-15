package com.codepath.imagesearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = 778604065567186426L;
	
	public String imageUrl = null;
	public String thumbnailUrl = null;

	public ImageResult(JSONObject object) {
		try {
			this.imageUrl = object.getString("url");
			this.thumbnailUrl = object.getString("tbUrl");
		} catch (JSONException e) {
			this.imageUrl = null;
			this.thumbnailUrl = null;
		}
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	@Override
	public String toString() {
		return String.format("url:%s\nthumbnailUrl:%s", imageUrl, thumbnailUrl);
	}
	
	public static List<ImageResult> fromJsonArray(
			JSONArray imageJsonResults) {
		List<ImageResult> results = new ArrayList<>();
		for(int i = 0; i< imageJsonResults.length(); i++) {
			try {
				results.add(new ImageResult(imageJsonResults.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		return results;
	}
}
