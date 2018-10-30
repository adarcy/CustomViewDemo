package com.syxl.customviewdemo.fragmentAnimation;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.syxl.customviewdemo.R;

public class FragmentTransactionActivity extends AppCompatActivity {
    private static final String TAG = "FragmentTransactionActi";

    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private Fragment fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_transaction);
        fragment = SlidingListFragment.instantiate(this, SlidingListFragment.class.getName());

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        Log.e(TAG,"getMetrics.height==" + outMetrics.heightPixels);
        wm.getDefaultDisplay().getRealMetrics(outMetrics);
        Log.e(TAG,"getRealMetrics.height==" + outMetrics.heightPixels);

//        if (OsUtil.isMIUI()) {//小米系统判断虚拟键是否显示方法
//            if(Settings.Global.getInt(mActivity.getContentResolver(), "force_fsg_nav_bar", 0) != 0){
//                //开启手势，不显示虚拟键
//                return 0;
//            }
//        }

        if (Settings.Global.getInt(getContentResolver(), "force_fsg_nav_bar", 0) != 0) {
            //开启手势，不显示虚拟键
            Log.e(TAG, "开启手势，不显示虚拟键");
        } else {
            Log.e(TAG, "关闭手势，显示虚拟键");
        }

        if (vivoNavigationGestureEnabled(this)) {
            Log.e(TAG, "vivo 开启手势，不显示虚拟键");
        } else {
            Log.e(TAG, "vivo  显示虚拟键");
        }

    }

    private static final String NAVIGATION_GESTURE = "navigation_gesture_on";
    private static final int NAVIGATION_GESTURE_OFF = 0;

    /**
     * 获取vivo手机设置中的"navigation_gesture_on"值，判断当前系统是使用导航键还是手势导航操作
     * @param context app Context
     * @return false 表示使用的是虚拟导航键(NavigationBar)， true 表示使用的是手势， 默认是false
     */
    public boolean vivoNavigationGestureEnabled(Context context) {
        int val = Settings.Secure.getInt(context.getContentResolver(), NAVIGATION_GESTURE, NAVIGATION_GESTURE_OFF);
        return val != NAVIGATION_GESTURE_OFF;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            toggleList();
            return true;
        }else if (id == R.id.show) {
            showList();
            return true;
        }else if (id == R.id.hide) {
            hideList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleList() {
        Fragment f = getFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .setCustomAnimations(R.animator.slide_up,
                            R.animator.slide_down,
                            R.animator.slide_up,
                            R.animator.slide_down)
                    .add(R.id.list_fragment_container, fragment, LIST_FRAGMENT_TAG
                    ).addToBackStack(null).commit();
        }
    }

    private void showList() {
        Fragment f = getFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
        getFragmentManager().beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.slide_up,
                        R.animator.slide_down,
                        R.animator.slide_up,
                        R.animator.slide_down)
                .show(f
                ).commit();
    }

    private void hideList() {
        Fragment f = getFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
        getFragmentManager().beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.slide_up,
                        R.animator.slide_down,
                        R.animator.slide_up,
                        R.animator.slide_down)
                .hide(f
                ).commit();
    }
}
