package honeywell.com.smartplayer.ui.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @author Vincent
 */
public class BaseFragment extends Fragment implements MessageTransfer {
    protected Handler mHandler = new MessageTransfer.MyHandler(this);
    protected Context mContext;
    public AlertDialog.Builder builder;
    protected Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mActivity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}

    public void initBuilder(String message) {
        builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("ok", null);
        builder.setMessage(message);
        builder.create().show();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void handleMessage(Message msg) {
    }

    public void doClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当界面不可见时，关闭对话框
     */
    public void closeDialog(Dialog... dialogs) {
        for (Dialog dialog : dialogs) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    protected void stopLoading() {
    }

    protected void printToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
