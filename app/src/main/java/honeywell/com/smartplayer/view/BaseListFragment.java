package honeywell.com.smartplayer.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import honeywell.com.smartplayer.R;
import honeywell.com.smartplayer.ui.DetailsActivity;
import honeywell.com.smartplayer.utils.IntentUtil;
import honeywell.com.smartplayer.widget.XListView;


public abstract class BaseListFragment extends Fragment implements
		XListView.IXListViewListener {

	protected XListView listview;
	protected View view;
	LayoutInflater mInflater;
	protected boolean mIsScroll = false;
	ObjectMapper mMapper = new ObjectMapper();
	protected BaseAdapter mAdapter;

	public ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		view = inflater.inflate(R.layout.main, null);
		listview = (XListView) view.findViewById(R.id.list_view);
		initListView();
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void initListView() {
	}

	public void startDetailActivity(Activity mContext, String url,
			String title, String shareTitle) {
		IntentUtil.start_activity(mContext, DetailsActivity.class,
				new BasicNameValuePair("url", url), new BasicNameValuePair(
						"title", title), new BasicNameValuePair("sharetitle",
						shareTitle));
	}

	protected void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}


}
