package honeywell.com.smartplayer.network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import honeywell.com.smartplayer.config.Constants;
import honeywell.com.smartplayer.entity.ControlModel;
import honeywell.com.smartplayer.utils.StringUtil;

/**
 * Created by Vincent on 12/11/15.
 */
public class TCPServer implements Runnable {
    public static final String TAG = "TCPServer";

    private byte[] mMessage = new byte[1024];
    private boolean mLife = true;
    private Context mContext;
    private Socket socket = null;
    private ControlModel mControlModel;

    public TCPServer(Context context) {
        mContext = context;
    }

    public TCPServer(Context context, ControlModel controlModel) {
        mControlModel = controlModel;
        mContext = context;
    }

    /**
     * @return the mLife
     */
    public boolean isLife() {
        return mLife;
    }

    private String sendData(ControlModel controlModel) {
//        String head = StringUtil.stringToAscii(controlModel.getHon() + controlModel.getSender() + controlModel.getType());
        String head = StringUtil.stringToAscii(controlModel.HEAD);

        String rear = controlModel.getData();
        int length = rear.length();
        rear = StringUtil.stringToAscii(rear);
        return head + length + rear;
    }

    /**
     * @param life the mLife to set
     */
    public void setLife(boolean life) {
        this.mLife = life;
    }

    @Override
    public void run() {
        try {
            //创建Socket
            //192.168.0.102;
            //创建Socket，设定访问服务端的地址：192.168.1.112，端口号：12345.
            socket = new Socket(Constants.URL, Constants.PORT_CTR);

            //向器发送消息
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println("honmobctr000");
            out.print(1);
            out.print("f");
//            Log.i(TAG, "sendData(mControlModel): " + sendData(mControlModel));
            //接收来自服务器的消息
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String msg = br.readLine();
            System.out.println("TCP Client recevied data for Server");
//            if (msg != null) {
//                mTextView.setText(msg);
//            } else {
//                mTextView.setText("数据错误!");
//            }
            //关闭流
            out.close();
//            br.close();
            //关闭Socket
            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, e.toString());
        }
    }

}
