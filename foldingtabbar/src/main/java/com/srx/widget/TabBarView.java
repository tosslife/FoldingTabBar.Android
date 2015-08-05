package com.srx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;


public class TabBarView extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {

	private TabMainView tabBackView;
	private View secondlyFirstIV;
	private View secondlySecondIV;
	private View views[] = new View[5];
	private boolean clickable = true;
	private boolean btnsClickable = false;
	private int page = 0;
	private HashMap<String, Integer> btnsMap = new HashMap<String, Integer>();
	private OnTabBarClickListener onTabBarClickListener;
	private TabMainView.OnAnimationEndListener onAnimationEndListener = new TabMainView.OnAnimationEndListener() {

		@Override
		public void onAnimationEnd() {
			clickable = true;
		}
	};

	public TabBarView(Context context) {
		this(context, null);
	}

	public TabBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.widget_tabbar, this, true);
		initView();
		initListener();
		initClickable(btnsClickable = false);
	}

	private void initView() {
		tabBackView = (TabMainView) findViewById(R.id.tabBackView);
		views[0] = findViewById(R.id.firstIV);
		views[1] = findViewById(R.id.secondIV);
		views[2] = findViewById(R.id.centerIV);
		views[3] = findViewById(R.id.fourIV);
		views[4] = findViewById(R.id.fiveIV);
		secondlyFirstIV = findViewById(R.id.secondlyFirstIV);
		secondlySecondIV = findViewById(R.id.secondlySecondIV);
		for (int i = 0; i < views.length; i++) {
			views[i].setTag(i);
		}
		secondlyFirstIV.setTag(10);
		secondlySecondIV.setTag(11);

	}

	private void initListener() {
		for (View view : views) {
			view.setOnClickListener(this);
			view.setOnTouchListener(this);
		}
		secondlyFirstIV.setOnTouchListener(this);
		secondlySecondIV.setOnTouchListener(this);
		secondlyFirstIV.setOnClickListener(this);
		secondlySecondIV.setOnClickListener(this);
		tabBackView.setOnAnimationEndListener(onAnimationEndListener);
	}

	private void initClickable(boolean clickable) {
		views[0].setClickable(clickable);
		views[1].setClickable(clickable);
		views[3].setClickable(clickable);
		views[4].setClickable(clickable);
		secondlyFirstIV.setClickable(!clickable);
		secondlySecondIV.setClickable(!clickable);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int position = (Integer) v.getTag();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			tabBackView.onDown(position);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			tabBackView.onUp(position);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if (!clickable)
			return;
		clickable = false;
		int clickLocation[] = new int[2];
		v.getLocationOnScreen(clickLocation);
		clickLocation[0] = clickLocation[0] + v.getWidth() / 2;
		clickLocation[1] = clickLocation[1] + v.getHeight() / 2;
		int position = (Integer) v.getTag();
		if (position < 10) {
			if (position == 2) {
				initClickable(btnsClickable = !btnsClickable);
			} else {
                initClickable(btnsClickable = false);
				page = position > 1 ? position - 1 : position;
				if (onTabBarClickListener != null) {
					onTabBarClickListener.onMainBtnsClick(position, clickLocation);
				}
                changeLeftRightIcon(page);
			}
			tabBackView.onClick(position);
		} else {
			clickable = true;
			if (onTabBarClickListener != null) {
				if (position == 10)
					onTabBarClickListener.onLeftBtnClick(page);
				else
					onTabBarClickListener.onRightBtnClick(page);
			}
		}
	}

	public void initializePage(int page) {
		this.page = page;
		tabBackView.initPosition(page);
		changeLeftRightIcon(page);
	}

	private void changeLeftRightIcon(int position) {
		setSecondlyFirstBitmap(btnsMap.get(position + "left"));
		setSecondlySecondBitmap(btnsMap.get(position + "right"));
	}

	public void bindBtnsForPage(int page, int menuBitmapId, int leftBitmapId, int rightBitmapId) {
		tabBackView.setMenuBitmaps(page, menuBitmapId);
		btnsMap.put(page + "left", leftBitmapId);
		btnsMap.put(page + "right", rightBitmapId);
		if (this.page != page) {
			initializePage(page);
		}
	}

	public void setMainBitmap(int mainBitmap) {
		tabBackView.setMainBtnBitmap(mainBitmap);
	}

	public void setSecondlyFirstBitmap(int secondlyFirstBitmap) {
		tabBackView.setLeftBtnBitmap(secondlyFirstBitmap);
		secondlyFirstIV.setVisibility(secondlyFirstBitmap != 0 ? View.VISIBLE : View.GONE);
	}

	public void setSecondlySecondBitmap(int secondlySecondBitmap) {
		tabBackView.setRightBtnBitmap(secondlySecondBitmap);
		secondlySecondIV.setVisibility(secondlySecondBitmap != 0 ? View.VISIBLE : View.GONE);
	}

	public void setOnTabBarClickListener(OnTabBarClickListener onTabBarClickListener) {
		this.onTabBarClickListener = onTabBarClickListener;
	}

	public interface OnTabBarClickListener {

		void onMainBtnsClick(int position, int clickLocation[]);

		void onMainBtnsClick(int position);

		void onLeftBtnClick(int page);

		void onRightBtnClick(int page);

	}
}