package com.codepath.imagesearch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchActivity extends Activity {
	EditText etQuery;
	ImageButton btnSearch;
	GridView gvResults;
	ArrayAdapter<ImageResult> arrayAdapter;
	UserSettings userSettings = null;

	String query = null;

	List<ImageResult> imageResults = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setViews();

		// Editing user settings
		setUpUserSettings();
		if (userSettings != null) {
			Toast.makeText(this, userSettings.toString(), Toast.LENGTH_LONG)
					.show();
		}

		arrayAdapter = new ImageViewArrayAdapter(this, imageResults);
		gvResults.setAdapter(arrayAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				intent.putExtra("image", imageResults.get(position));
				startActivity(intent);
			}
		});

		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi(page, totalItemsCount);
			}
		});
	}

	protected void customLoadMoreDataFromApi(int page, int totalItemsCount) {
		Log.d("DEBUG", String.valueOf(totalItemsCount));
		runQuery(query, totalItemsCount);
	}

	private void setUpUserSettings() {
		Intent intent = getIntent();
		if (intent != null) {
			userSettings = (UserSettings) intent
					.getSerializableExtra("settings");
		}
	}

	/**
	 * Search and sets all views.
	 */
	private void setViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (ImageButton) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}

	public void onSearch(View v) {
		String query = etQuery.getText().toString();
		if (query.trim().equals("")) {
			return;
		}

		String toastString = String.format("Searching for %s", query);
		Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
		imageResults.clear();
		arrayAdapter.clear();
		this.query = query;
		runQuery(query, 0);
	}

	private RequestParams getQueryParams(String query, Integer startIndex) {
		RequestParams params = new RequestParams();
		params.put("rsz", "8");
		params.put("start", startIndex.toString());
		params.put("v", "1.0");
		params.put("q", Uri.encode(query));

		if (userSettings != null) {
			params.put("imgtype", userSettings.getImageType());
			params.put("imgsz", userSettings.getImageSize());
			params.put("imgcolor", userSettings.getImageColor());
			params.put("as_sitesearch", userSettings.getWebsite());
		}
		return params;
	}

	private Boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}

	private void runQuery(String query, int startIndex) {
		if (query == null) {
			return;
		}

		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = "https://ajax.googleapis.com/ajax/services/search/images";

		RequestParams params = getQueryParams(query, startIndex);
		Log.d("DEBUG", params.toString());

		if (!isNetworkAvailable()) {
			Toast.makeText(this,
					"Network not available.. Check Network connection",
					Toast.LENGTH_LONG).show();
			return;
		}
		Log.d("DEBUG", "I am not here");

		httpClient.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					arrayAdapter.addAll(ImageResult
							.fromJsonArray(imageJsonResults));
					// Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_bar, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(new QueryTextListener());
		return super.onCreateOptionsMenu(menu);
	}

	public class QueryTextListener implements OnQueryTextListener {
		@Override
		public boolean onQueryTextSubmit(String newQuery) {
			// perform query here
			if (newQuery.trim().equals("")) {
				return true;
			}

			String toastString = String.format("Searching for %s", newQuery);
			Toast.makeText(getApplicationContext(), toastString,
					Toast.LENGTH_LONG).show();
			imageResults.clear();
			arrayAdapter.clear();
			query = newQuery;
			etQuery.setText(query);
			runQuery(query, 0);
			return true;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			return false;
		}
	}

	public void onClickSettings(MenuItem mi) {
		Intent intent = new Intent(this, SettingsMenuActivity.class);
		startActivity(intent);
	}
}
