package com.comrax.mouseappandroid.favorites;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.helpers.AmazingAdapter;
import com.comrax.mouseappandroid.helpers.AmazingListView;

import java.util.List;

public class PaginationDemoActivity extends Activity {
	AmazingListView lsComposer;
	PaginationComposerAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorites_activity_pagination_demo);
		
		lsComposer = (AmazingListView) findViewById(R.id.lsComposer);
		lsComposer.setLoadingView(getLayoutInflater().inflate(R.layout.favorites_loading_view, null));
		lsComposer.setAdapter(adapter = new PaginationComposerAdapter());
		
		adapter.notifyMayHaveMorePages();
	}
	
	public void bRefresh_click(View v) {
		adapter.reset();
		adapter.resetPage();
		adapter.notifyMayHaveMorePages();
	}
	
	class PaginationComposerAdapter extends AmazingAdapter {
		List<FavoritesModel> list = Data.getRows(1).second;
		private AsyncTask<Integer, Void, Pair<Boolean, List<FavoritesModel>>> backgroundTask;

		public void reset() {
			if (backgroundTask != null) backgroundTask.cancel(false);
			
			list = Data.getRows(1).second;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public FavoritesModel getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		protected void onNextPageRequested(int page) {
			Log.d(TAG, "Got onNextPageRequested page=" + page);
			
			if (backgroundTask != null) {
				backgroundTask.cancel(false);
			}
			
			backgroundTask = new AsyncTask<Integer, Void, Pair<Boolean, List<FavoritesModel>>>() {
				@Override
				protected Pair<Boolean, List<FavoritesModel>> doInBackground(Integer... params) {
					int page = params[0];
					
					return Data.getRows(page);
				}
				
				@Override
				protected void onPostExecute(Pair<Boolean, List<FavoritesModel>> result) {
					if (isCancelled()) return; 
					
					list.addAll(result.second);
					nextPage();
					notifyDataSetChanged();
					
					if (result.first) {
						// still have more pages
						notifyMayHaveMorePages();
					} else {
						notifyNoMorePages();
					}
				};
			}.execute(page);
		}

		@Override
		protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
		}

		@Override
		public View getAmazingView(int position, View convertView, ViewGroup parent) {
			View res = convertView;
			if (res == null) res = getLayoutInflater().inflate(R.layout.favorites_item_composer, null);
			
			// we don't have headers, so hide it
			res.findViewById(R.id.header).setVisibility(View.GONE);
			
			TextView lName = (TextView) res.findViewById(R.id.lName);
			TextView lYear = (TextView) res.findViewById(R.id.lYear);
			
			FavoritesModel favoritesModel = getItem(position);
			lName.setText(favoritesModel.name);
			lYear.setText(favoritesModel.year);
			
			return res;
		}

		@Override
		public void configurePinnedHeader(View header, int position, int alpha) {
		}

		@Override
		public int getPositionForSection(int section) {
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			return null;
		}
		
	}
}