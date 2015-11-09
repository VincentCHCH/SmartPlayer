package honeywell.com.smartplayer.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BasePageAdapter extends FragmentStatePagerAdapter {

	public ArrayList<Fragment> mFragments = new ArrayList<Fragment>();;

	private Activity mActivity;

	public BasePageAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		this.mActivity = activity;
	}

	public void addFragment(List mList, List<Object> listObject) {
//		tabs.addAll(mList);
//		for (int i = 0; i < listObject.size(); i++) {
//			Object object = listObject.get(i);
//			if (object instanceof NewsCategoryListEntity) {
//				addTab(new NewsFragment(mActivity,
//						((NewsCategoryListEntity) listObject.get(i))));
//			} else if (object instanceof BlogsCategoryListEntity) {
//				addTab(new BlogFragment(mActivity,
//						((BlogsCategoryListEntity) listObject.get(i))));
//			} else if (object instanceof WikiCategoryListEntity) {
//				addTab(new WikiFragment(mActivity,
//						((WikiCategoryListEntity) listObject.get(i))));
//			}
//		}
	}
	/**
	 * 只加载listview不包含 tabs
	 * @param listObject
	 */
	public void addFragment( List<Object> listObject) {

//		for (int i = 0; i < listObject.size(); i++) {
//			Object object = listObject.get(i);
//			if (object instanceof NewsCategoryListEntity) {
//				addTab(new NewsFragment(mActivity,
//						((NewsCategoryListEntity) listObject.get(i))));
//			} else if (object instanceof BlogsCategoryListEntity) {
//				addTab(new BlogFragment(mActivity,
//						((BlogsCategoryListEntity) listObject.get(i))));
//			} else if (object instanceof WikiCategoryListEntity) {
//				addTab(new WikiFragment(mActivity,
//						((WikiCategoryListEntity) listObject.get(i))));
//			}
//		}
	}


	public void Clear() {
		mFragments.clear();
	}

	public void addTab(Fragment fragment) {
		mFragments.add(fragment);
		notifyDataSetChanged();
	}

//	@Override
//	public CharSequence getPageTitle(int position) {
//		return tabs.get(position).getName();
//	}

	@Override
	public Fragment getItem(int arg0) {
		return mFragments.get(arg0);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	//

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}
}
