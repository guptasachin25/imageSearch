package com.codepath.imagesearch;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		Intent intent = getIntent();
		ImageResult image = (ImageResult) intent.getSerializableExtra("image");
		
		SmartImageView smartView = (SmartImageView) findViewById(R.id.smartImageView);
		smartView.setImageUrl(image.getImageUrl());
	}
}
