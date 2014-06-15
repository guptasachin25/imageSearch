package com.codepath.imagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageViewArrayAdapter extends ArrayAdapter<ImageResult> {
	ImageViewArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.thumbnail_view, images);
	}

	private static class ViewHolder {
		SmartImageView ivImage;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = (SmartImageView) inflater.inflate(
					R.layout.thumbnail_view, parent, false);
			viewHolder.ivImage = (SmartImageView) convertView
					.findViewById(R.id.ivThumbImage);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.ivImage.setImageResource(android.R.color.transparent);
		}
		viewHolder.ivImage.setImageUrl(imageInfo.getThumbnailUrl());
		return convertView;
	}
}
