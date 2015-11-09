package honeywell.com.smartplayer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import honeywell.com.smartplayer.R;
import honeywell.com.smartplayer.ui.base.BaseActivity;


public class DetailsActivity extends BaseActivity implements OnClickListener {

    static final String mimeType = "text/html";
    static final String encoding = "utf-8";

    private RelativeLayout good;
    private RelativeLayout bed;
    private RelativeLayout collect;
    private RelativeLayout share;
    private RelativeLayout discuss;
    private ImageView imgGood;
    private ImageView imgBed;
    private ImageView imgCollect;
    private ImageView imgShare;
    private ImageView imgDiscuss;
    private boolean IsGood = false;
    private boolean IsBed = false;
    private boolean IsCollect = false;
    private TextView detailTitle;

    private Button bn_refresh;
    private ImageView imgGoHome;
    private WebView mWebView;
    private String mUrl;
    private String mTitle;
    private String shareUrl;
    private String shareTitle;

    private int screen_width;

    SharedPreferences sharePre;
    private String mKey;
    private int mDBID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Intent i = getIntent();
        mUrl = i.getStringExtra("url");
        mTitle = i.getStringExtra("title");
        shareTitle = i.getStringExtra("sharetitle");
        initData();
        initControl();
    }

    private void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screen_width = dm.widthPixels;
    }

    private void initControl() {
        detailTitle = (TextView) findViewById(R.id.details_textview_title);
        detailTitle.setText(mTitle);
        good = (RelativeLayout) findViewById(R.id.rlGood);
        bed = (RelativeLayout) findViewById(R.id.rlBed);
        collect = (RelativeLayout) findViewById(R.id.rlCollect);
        share = (RelativeLayout) findViewById(R.id.rlShare);
        discuss = (RelativeLayout) findViewById(R.id.rlDiscuss);
        mWebView = (WebView) findViewById(R.id.detail_webView);
        this.mWebView.setBackgroundColor(0);
        this.mWebView.setBackgroundResource(R.color.detail_bgColor);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        good.setOnClickListener(this);
        bed.setOnClickListener(this);
        collect.setOnClickListener(this);
        share.setOnClickListener(this);
        discuss.setOnClickListener(this);
        bn_refresh.setOnClickListener(this);
        imgGood = (ImageView) findViewById(R.id.imageview_details_good);
        imgBed = (ImageView) findViewById(R.id.imageview_details_bed);
        imgCollect = (ImageView) findViewById(R.id.imageview_details_collect);
        imgShare = (ImageView) findViewById(R.id.imageview_details_share);
        imgDiscuss = (ImageView) findViewById(R.id.imageview_details_discuss);
        imgGoHome = (ImageView) findViewById(R.id.details_imageview_gohome);
        imgGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    // [start] 初始化评价icon

    public void setAppraise(int good, int bad, int collect) {
        initGood(IsGood = (good == 1));
        initBad(IsBed = (bad == 1));
        initCollect(IsCollect = (collect == 1));
    }

    private void initGood(boolean b) {
        if (b) {
            imgGood.setImageResource(R.drawable.button_details_good_selected);
        } else {
            imgGood.setImageResource(R.drawable.button_details_good_default);
        }
    }

    private void initBad(boolean b) {
        if (b) {
            imgBed.setImageResource(R.drawable.button_details_bed_selected);
        } else {
            imgBed.setImageResource(R.drawable.button_details_bed_default);
        }
    }

    private void initCollect(boolean b) {
        if (b) {
            imgCollect
                    .setImageResource(R.drawable.button_details_collect_selected);
        } else {
            imgCollect
                    .setImageResource(R.drawable.button_details_collect_default);
        }
    }

    // [end]

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // 保存状态
        if (mKey.equals(null) && mKey.equals("")) {
            return;
        }
    }


    @Override
    public void onClick(View v) {
        if (mKey.equals(null) || mKey.equals("")) {
            showLongToast(getResources().getString(R.string.user_login_prompt));
            return;
        }
        String url = null;
    }

    private String updateUrl(boolean isAdd, String url) {
        String result = url;
        if (isAdd) {
            result = result.replaceAll("add", "del");
        }
        return result;
    }

}
