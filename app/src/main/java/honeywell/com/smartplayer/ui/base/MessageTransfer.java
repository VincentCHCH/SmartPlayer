package honeywell.com.smartplayer.ui.base;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author Vincent
 * @version
 *          <p>
 *          实现了此类的接口，就可以拥有一个该接口中定义的handler，同时必须实现方法handleMessage,
 *          在此方法中处理handler中的消息队列。通常用于替代activity中的默认handler(缺点：容易内存溢出)，也可
 *          有于fragment等。
 *          </p>
 * */
public interface MessageTransfer {

	public Handler getHandler();

	public void handleMessage(Message msg);

	static class MyHandler extends Handler {
		private WeakReference<MessageTransfer> mOuter;

		public MyHandler(MessageTransfer transfer) {
			mOuter = new WeakReference<MessageTransfer>(transfer);
		}

		@Override
		public void handleMessage(Message msg) {
			MessageTransfer transfer = mOuter.get();
			if (mOuter != null) {
				transfer.handleMessage(msg);
			}
		}
	}
}
