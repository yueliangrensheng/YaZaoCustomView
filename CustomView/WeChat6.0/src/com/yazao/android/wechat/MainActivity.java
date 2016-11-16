package com.yazao.android.wechat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.yazao.android.wechat.fragment.TagFragment;
import com.yazao.android.wechat.widget.ChangeColorIconWithText;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private String[] mTitles = new String[] { "First Fragment !", "Second Fragment !", "Third Fragment !",
			"Fourth Fragment !" };
	private FragmentPagerAdapter mAdapter;
	private ChangeColorIconWithText one, two, three, four;
	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setOverflowButtonAlways();
		// 去除actionbar中的应用图标
		if (getActionBar() != null) {
			getActionBar().setDisplayShowHomeEnabled(false);
		}

		initView();
		initData();
		registerEvent();
	}

	@SuppressWarnings("deprecation")
	private void registerEvent() {
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
		two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
		three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
		four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
		mTabIndicators.add(one);
		mTabIndicators.add(two);
		mTabIndicators.add(three);
		mTabIndicators.add(four);
		one.setIconAlpha(1.0f);
	}

	private void initData() {
		for (String title : mTitles) {
			TagFragment tabFragment = new TagFragment();
			Bundle args = new Bundle();
			args.putString(TagFragment.TITLE, title);
			tabFragment.setArguments(args);
			mTabs.add(tabFragment);
		}

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};

		mViewPager.setAdapter(mAdapter);

	}

	/**
	 * 
	 * @description 利用反射技术，使Actionbar中的OverflowButton一直显示，并且 改变 点击它时菜单出现的位置。
	 *              void
	 * @author yueliangrensheng create at 2015年10月21日下午3:26:58
	 */
	private void setOverflowButtonAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 设置menu显示icon。默认情况下，不显示icon。通过反射技术来显示。
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {

			Class clazz;
			try {
				clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
				Field field = clazz.getDeclaredField("mOptionalIconsVisible");
				if (field != null) {
					field.setAccessible(true);
					field.set(menu, true);
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			// if("MenuBuilder".equals(menu.getClass().getSimpleName())){
			// try {
			// Method method =
			// menu.getClass().getDeclaredMethod("setOptionallconsVisible",
			// Boolean.class);
			// method.setAccessible(true);
			// method.invoke(menu, true);
			// } catch (NoSuchMethodException e) {
			// e.printStackTrace();
			// } catch (IllegalArgumentException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (InvocationTargetException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View v) {
		resetTabs();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicators.get(3).setIconAlpha(1);
			mViewPager.setCurrentItem(3, false);
			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * @description 重置TabIndicator的颜色 void
	 * @author yueliangrensheng create at 2015年10月23日上午11:31:32
	 */
	private void resetTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		Log.i("YaZao", "position= " + position + " , positionOffset= " + positionOffset);
		if (positionOffset > 0) {
			ChangeColorIconWithText left = mTabIndicators.get(position);
			ChangeColorIconWithText right = mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);

		}
	}

	@Override
	public void onPageSelected(int arg0) {

	}
}
