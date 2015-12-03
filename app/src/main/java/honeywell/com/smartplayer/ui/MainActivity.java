package honeywell.com.smartplayer.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import honeywell.com.smartplayer.R;
import honeywell.com.smartplayer.adapter.BasePageAdapter;
import honeywell.com.smartplayer.config.Constants;
import honeywell.com.smartplayer.entity.NavigationModel;
import honeywell.com.smartplayer.indicator.PageIndicator;
import honeywell.com.smartplayer.slidingmenu.SlidingMenu;
import honeywell.com.smartplayer.ui.base.BaseFragment;
import honeywell.com.smartplayer.ui.base.BaseSlidingFragmentActivity;
import honeywell.com.smartplayer.ui.base.MessageTransfer;
import honeywell.com.smartplayer.view.VideoPlayerFragment;
import io.vov.vitamio.LibsChecker;


public class MainActivity extends BaseSlidingFragmentActivity implements MessageTransfer{
    private final String LIST_TEXT = "text";
    private final String LIST_IMAGEVIEW = "img";

    // [start]变量
    /**
     * 数字代表列表顺序
     */
    private int mTag = 0;

    private View title;
    private LinearLayout mlinear_listview;

    // title标题
    private ImageView imgQuery;
    private ImageView imgMore;
    private ImageView imgLeft;
    private ImageView imgRight;
    private FrameLayout mFrameTv;
    private ImageView mImgTv;

    // views
    private ViewPager mViewPager;
    private BasePageAdapter mBasePageAdapter;
    private PageIndicator mIndicator;

    private List<Object> categoryList;

    private ListView lvTitle;
    private SimpleAdapter lvAdapter;
    private LinearLayout llGoHome;
    private ImageButton imgLogin;

    private TextView mAboveTitle;
    private SlidingMenu sm;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;

    // load responseData
    private String current_page;

    private InputMethodManager imm;

    private boolean isShowPopupWindows = false;
    private List<NavigationModel> navs;

    private FragmentManager frmManager;
    int targetId = 0;
    private TextView titleText;//tv_title
    private long exitTime = 0;
    private static final int BACK_EXIT_DURATION = 2000;
    private int mCurrentPageIndex = 2;
    protected Handler mHandler = new MessageTransfer.MyHandler(this);
    private final String TAG = "MainActivity";
    private VideoPlayerFragment mVideoPlayerFragment;
//    private TranningFragment2 mTranningFragment;
//    private SettingFragment mSettingFragment;
    private final BaseFragment FRAM_ALWAYS_ARRAY[] = {mVideoPlayerFragment};

    private RelativeLayout mSettingLayout;
    private FragmentTransaction mTransaction;

    /**
     * Tab选项卡集合
     */

//    private static final int[] TAB_ITEM_ARRAY = {R.id.tab1,//Monitor
//            R.id.tab2,//Tranning
//            R.id.tab3,//Setting
//    };
//    private static final int[] TAB_ITEM_ARRAY = {R.id.server_back,R.id.setting_trust_list, R.id.setting_setbtn};

    // [end]

    // [start]生命周期
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        setContentView(R.layout.above_slidingmenu);
        initControl();
        initListView();
        initNav();
        setDefaultFragment();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    // [end]

    // [start]初始化函数
    private void initSlidingMenu() {
        setBehindContentView(R.layout.behind_slidingmenu);
        // customize the SlidingMenu
        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
        //sm.setShadowWidth(20);
        sm.setBehindScrollScale(0);
    }

    private void initControl() {
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        frmManager = this.getSupportFragmentManager();

        imm = (InputMethodManager) getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        mAboveTitle = (TextView) findViewById(R.id.tv_above_title);
        mAboveTitle.setText(getString(R.string.menuGood));
        imgQuery = (ImageView) findViewById(R.id.imageview_above_query);
        imgQuery.setVisibility(View.GONE);
        imgMore = (ImageView) findViewById(R.id.imageview_above_more);
        imgLeft = (ImageView) findViewById(R.id.imageview_above_left);
        imgRight = (ImageView) findViewById(R.id.imageview_above_right);
        lvTitle = (ListView) findViewById(R.id.behind_list_show);
        llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
        imgLogin = (ImageButton) findViewById(R.id.login_login);

        title = findViewById(R.id.main_title);
        mlinear_listview = (LinearLayout) findViewById(R.id.main_linear_listview);
        mFrameTv = (FrameLayout) findViewById(R.id.fl_off);
        mImgTv = (ImageView) findViewById(R.id.iv_off);

    }


    private void initListView() {
        lvAdapter = new SimpleAdapter(this, getData(),
                R.layout.behind_list_show, new String[]{LIST_TEXT,
                LIST_IMAGEVIEW},
                new int[]{R.id.textview_behind_title,
                        R.id.imageview_behind_icon}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub.
                View view = super.getView(position, convertView, parent);
                if (position == mTag) {
                    view.setBackgroundResource(R.drawable.back_behind_list);
                    lvTitle.setTag(view);
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                return view;
            }
        };
        Log.i(TAG,"livTitile: "+lvTitle);
        lvTitle.setAdapter(lvAdapter);
        lvTitle.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FragmentTransaction transaction = frmManager.beginTransaction();

                NavigationModel navModel = navs.get(position);
                mAboveTitle.setText(navModel.getName());
                current_page = navModel.getTags();
                if (lvTitle.getTag() != null) {
                    if (lvTitle.getTag() == view) {
                        MainActivity.this.showContent();
                        return;
                    }
                    ((View) lvTitle.getTag())
                            .setBackgroundColor(Color.TRANSPARENT);
                }
                lvTitle.setTag(view);
                view.setBackgroundResource(R.drawable.back_behind_list);
                imgQuery.setVisibility(View.VISIBLE);
                Log.i(TAG, "posititon: " + position);
                switch (position) {
                    case 0:
                        imgQuery.setVisibility(View.GONE);
                        break;
                    case 1:
                        break;
                    case 2:
                        if (mVideoPlayerFragment == null) {
                            mVideoPlayerFragment = new VideoPlayerFragment();
                        }
                        transaction.replace(R.id.id_content, mVideoPlayerFragment);
                        break;
                    case 3:
                        break;
                }
                transaction.commit();

            }
        });

    }

    private void initNav() {
        navs = new ArrayList<NavigationModel>();
        NavigationModel nav1 = new NavigationModel(getResources().getString(
                R.string.menuGood), "");
        NavigationModel nav2 = new NavigationModel(getResources().getString(
                R.string.menuNews), Constants.TAGS.NEWS_TAG);
        NavigationModel nav3 = new NavigationModel(getResources().getString(
                R.string.menuStudio), Constants.TAGS.WIKI_TAG);
        NavigationModel nav4 = new NavigationModel(getResources().getString(
                R.string.menuBlog), Constants.TAGS.BLOG_TAG);
        Collections.addAll(navs, nav1, nav2, nav3, nav4);
    }


    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuGood));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_handpick);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuNews));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuStudio));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_studio);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuBlog));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_blog);
//        list.add(map);
        return list;
    }

    // [end]

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        keyBackClickCount = 0;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    Toast.makeText(this,
                            getResources().getString(R.string.press_again_exit),
                            Toast.LENGTH_SHORT).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    mFrameTv.setVisibility(View.VISIBLE);
                    mImgTv.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.tv_off);
                    mImgTv.startAnimation(anim);
                    break;
                default:
                    break;
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (sm.isMenuShowing()) {
                toggle();
            } else {
                showMenu();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置Fragment显示初始化
     */
    private void setDefaultFragment() {
        mTransaction = frmManager.beginTransaction();
        mVideoPlayerFragment = new VideoPlayerFragment();
        FRAM_ALWAYS_ARRAY[targetId] = mVideoPlayerFragment;
//        mTranningFragment = new TranningFragment();
        mTransaction.replace(R.id.id_content, mVideoPlayerFragment);
        mTransaction.commit();
    }

    /**
     * 显示指定界面
     * <p/>
     * id是fragment在xml里的id
     */
//    private void setCurrentFragment(int id) {
//        for (int i = 0, len = TAB_ITEM_ARRAY.length; i < len; i++) {
//            Fragment currentFram = frmManager.findFragmentById(TAB_FRAGMENT_ARR[i]);
//            Button btn = (Button) findViewById(TAB_ITEM_ARRAY[i]);
//            if (TAB_FRAGMENT_ARR[i] == id) {
//                frmManager.beginTransaction().show(currentFram).commit();
//                FRAM_ALWAYS_ARRAY[i] = (BaseFragment) currentFram;
//                targetId = i;
//                btn.setSelected(true);
//            } else {
//                frmManager.beginTransaction().hide(currentFram).commit();
//                btn.setSelected(false);
//            }
//        }
//    }

//    /**
//     * 重载bindClickListener(int,int);绑定所有的选项卡
//     */
//    private void bindClickListener() {
//        for (int i = 0, len = TAB_ITEM_ARRAY.length; i < len; i++) {
//            bindClickListener(TAB_ITEM_ARRAY[i], TAB_FRAGMENT_ARR[i]);
//        }
//    }

    /**
     * 绑定选项卡与显示界面，即当点击某个选项卡时，显示某个界面
     */
//    private void bindClickListener(final int tabId, final int frmId) {
////        final RadioButton btn = (RadioButton) findViewById(tabId);
//        final ImageView btn = (ImageView) findViewById(tabId);
//        btn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                setCurrentFragment(frmId);
//                btn.setSelected(true);
//                FragmentTransaction transaction = frmManager.beginTransaction();
//                switch (tabId) {
//                    case R.id.tab1:
//                        targetId = 0;
//                        FRAM_ALWAYS_ARRAY[targetId] = mCameraFragment;
//                        titleText.setText(getString(R.string.monitor));
//                        BaseFragment f1 = FRAM_ALWAYS_ARRAY[0];
//                        if (mCameraFragment == null) {
//                            mCameraFragment = new CameraFragment();
//                        }
//                        transaction.replace(R.id.id_content, mCameraFragment);
//                        break;
//                    case R.id.tab2:
//                        targetId = 1;
//                        titleText.setText(getString(R.string.tranning));
//
//                        if (mTranningFragment == null) {
//                            mTranningFragment = new TranningFragment2();
//                        }
//                        FRAM_ALWAYS_ARRAY[targetId] = mTranningFragment;
//
//                        transaction.replace(R.id.id_content, mTranningFragment);
//                        break;
//                    case R.id.tab3:
//                        targetId = 2;
//                        BaseFragment f3 = FRAM_ALWAYS_ARRAY[2];
//                        titleText.setText(getString(R.string.setting));
//                        if (mSettingFragment == null) {
//                            mSettingFragment = new SettingFragment();
//                        }
//                        FRAM_ALWAYS_ARRAY[targetId] = mSettingFragment;
//                        transaction.replace(R.id.id_content, mSettingFragment);
//                        break;
//                    case R.id.setting_trust_list:
//                        targetId = 0;
//                        titleText.setText(getString(R.string.tranning));
//
//                        if (mTranningFragment == null) {
//                            mTranningFragment = new TranningFragment2();
//                        }
//                        FRAM_ALWAYS_ARRAY[targetId] = mTranningFragment;
//
//                        transaction.replace(R.id.id_content, mTranningFragment);
//                        transaction.addToBackStack(null);
//                        setUiVisible(false);
//                        break;
//                    case R.id.setting_setbtn:
//                        targetId = 1;
//                        titleText.setText(getString(R.string.setting));
//                        if (mSettingFragment == null) {
//                            mSettingFragment = new SettingFragment();
//                        }
//                        FRAM_ALWAYS_ARRAY[targetId] = mSettingFragment;
//                        transaction.replace(R.id.id_content, mSettingFragment);
//                        transaction.addToBackStack(null);
//                        setUiVisible(false);
//                        break;
//                    default:
//                        break;
//                }
//                transaction.commit();
//
//            }
//        });
//    }
    public void doClick(View v) {
        mVideoPlayerFragment.doClick(v);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void handleMessage(Message msg) {

    }


}
