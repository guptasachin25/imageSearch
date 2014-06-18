package com.codepath.imagesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsMenuActivity extends Activity {

	Spinner spImageColor;
	Spinner spImageSize;
	Spinner spImageType;
	EditText etWebUrl;

	List<String> imageColors = new ArrayList<>();
	List<String> imageSizes = new ArrayList<>();
	List<String> imageTypes = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_menu);
		setViews();
		addResources();

		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, imageColors);
		colorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageColor.setAdapter(colorAdapter);

		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, imageTypes);
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageType.setAdapter(typeAdapter);

		ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, imageSizes);
		sizeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageSize.setAdapter(sizeAdapter);
	}

	public void onSubmitMenuOptions(View v) {
		String imageColor = spImageColor.getSelectedItem().toString();
		String imageType = spImageType.getSelectedItem().toString();
		String imageSize = spImageSize.getSelectedItem().toString();
		String website = etWebUrl.getText().toString();

		UserSettings settings = new UserSettings(imageSize, imageColor,
				imageType, website);
		Intent intent = new Intent(this, SearchActivity.class);
		intent.putExtra("settings", settings);
		startActivity(intent);
	}

	private void setViews() {
		spImageColor = (Spinner) findViewById(R.id.spImageColor);
		spImageType = (Spinner) findViewById(R.id.spImageType);
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		etWebUrl = (EditText) findViewById(R.id.etWebUrl);
	}

	private void addResources() {
		String[] colors = new String[]{"all", "red", "blue", "green", "black",
				"brown", "gray", "green", "oragne", "pink", "purple", "teal",
				"white", "yellow"};
		String[] sizes = new String[]{"all", "icon", "small", "medium",
				"large", "xlarge", "xxlarge", "huge"};
		String[] types = new String[]{"all", "face", "photo", "clipart",
				"lineart"};
		Collections.addAll(imageColors, colors);
		Collections.addAll(imageSizes, sizes);
		Collections.addAll(imageTypes, types);
	}
}