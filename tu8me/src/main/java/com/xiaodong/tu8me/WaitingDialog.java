package com.xiaodong.tu8me;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by gqy on 2016/3/8.
 * 等待进度条；
 * <p/>
 * DialogFragment 持有 the  Dialog.setOnCancelListener and  Dialog.setOnDismissListener callbacks.
 */
public class WaitingDialog extends DialogFragment {

    private String title = "提示";
    private String msg = "正在获取银行卡信息...";
    private static WaitingDialog waitingDialog;
    private MyEventCallBack myEventCallBack;

    public static WaitingDialog getInstance() {
        if (waitingDialog == null) {
            synchronized (WaitingDialog.class) {
                if(waitingDialog == null) {
                    waitingDialog = new WaitingDialog();
                }
            }
        }
        return waitingDialog;
    }

    public WaitingDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public WaitingDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public void setOnCancleCallback(MyEventCallBack eventCallBack){
        myEventCallBack = eventCallBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        ProgressDialog pDialog1 = new ProgressDialog(getActivity(), R.style.progressdialog_style);
        Dialog pDialog1 = new Dialog(getActivity(),R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_wait, null);
        pDialog1.setContentView(view);
        Window window = pDialog1.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.95f;
        window.setAttributes(lp);
        TextView tv_msg = (TextView) view.findViewById(R.id.message);
        tv_msg.setText(msg);
//        pDialog1.setMessage(msg);
//        ProgressDialog pDialog = ProgressDialog.show(getActivity(), null, msg, true);
//        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.setCancelable(true);

        pDialog1.setCanceledOnTouchOutside(false);
        pDialog1.setCancelable(true);
        return pDialog1;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Fragment fragment = getTargetFragment();
        myEventCallBack.adapterEventCallBack();
//        if (fragment instanceof TxFragment) {
//            if (getTargetRequestCode() == AppConstants.TX_GETYHK_REQ_CODE) {
//                ((TxFragment) fragment).dialogCanceled(AppConstants.DIALOG_TX_GETYHK_RESULT_CODE);
//            } else if (getTargetRequestCode() == AppConstants.TX_LKTX_REQ_CODE) {
//                ((TxFragment) fragment).dialogCanceled(AppConstants.DIALOG_TX_LKTX_RESULT_CODE);
//            }
//        }
    }
}
