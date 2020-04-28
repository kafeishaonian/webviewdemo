package com.http.client.action.core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.http.client.MainActivity;
import com.http.client.utils.LogUtils;
import com.http.client.utils.PreferenceTools;
import com.http.client.utils.StringUtils;

/**
 * action管理类:支持H5、其他app、app內部页面间跳转
 */
public class ActionManager {

    private static final String TAG = ActivityManager.class.getSimpleName();
    /**
     * params
     */
    private String mActionUrl;// 私有协议：qla://www.http.com?action=1&url=&id=&ext1=&ext2=&ext3=
    private Context mContext;
    private UrlParser mURLParser;
    private boolean mIsValidAction;// Action协议是否有效
    private boolean isUrl;//是否是url链接
    private boolean checkIsContainAction = false;

    public ActionManager(Context context, String actionUrl) {
        super();
        LogUtils.p("ActionUrl : " + actionUrl);
        mActionUrl = actionUrl;
        mContext = context;
        //设置action协议防误触，两次触发时间间隔不可小于1s
        if ((System.currentTimeMillis()- PreferenceTools.getActionTime(context))>=1000){
            PreferenceTools.updateActionTime(context, System.currentTimeMillis());
            mIsValidAction = checkIfActionCorrect();
            isUrl = checkIsHttpUrl();
        }
    }

    private boolean checkIfActionCorrect() {
        if (StringUtils.isBlank(mActionUrl)) {
            return false;
        }

        if (mActionUrl.startsWith(ActionDefineUtils.HEADER_PROTOCOL_ACTION)) {
            return true;
        }

        return false;
    }

    private boolean checkIsHttpUrl() {
        if (!TextUtils.isEmpty(mActionUrl)) {
            if (mActionUrl.startsWith(ActionDefineUtils.HEADER_PROTOCOL_SYSTEM_HTTP)
                    || mActionUrl.startsWith(ActionDefineUtils.HEADER_PROTOCOL_SYSTEM_HTTPS)) {

                checkIsContainAction = checkIsContainAction();
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean checkIsContainAction() {
        if (StringUtils.isBlank(mActionUrl)) {
            return false;
        }

        if (mActionUrl.contains(ActionDefineUtils.HEADER_PROTOCOL_ACTION)) {
            return true;
        }

        return false;
    }

    public void resetActionURL(String actionUrl) {
        this.mActionUrl = actionUrl;
        mIsValidAction = checkIfActionCorrect();
    }

    public boolean isValidAction() {
        return mIsValidAction;
    }

    public boolean isUrl() {
        return isUrl;
    }

    public boolean isCheckContainAction() {
        return checkIsContainAction;
    }

    /**
     * 获取参数的数值
     *
     * @param key
     * @return
     */
    public String getParameter(String key) {
        if (!isValidAction()) {
            return null;
        }
        if (mURLParser == null) {
            mURLParser = new UrlParser(mActionUrl);
        }

        return mURLParser.getParamValue(key);
    }

    /**
     * 处理当前Action跳转
     *
     * @return
     */
    public boolean processAction() {
        if (!isValidAction()) {
            return false;
        }

        String actionParam = getParameter("action");
        if (StringUtils.isNotBlank(actionParam)) {
            return processCurrentAction(actionParam);
        }

        return false;
    }

    /**
     * 处理逻辑
     * qla://www.http.com?action=101&url=&id=&ext1=&ext2=&ext3=
     */
    private boolean processCurrentAction(String actionId) {
        int action = StringUtils.stringToInt(actionId);
        long id = StringUtils.stringToLong(getParameter("id"));
        String url = getParameter("url");
        String title = getParameter("title");
        String activity = getParameter("activity");
        String ext3 = getParameter("ext3");
        switch (action) {
            case 101:
                Log.e(TAG, "processCurrentAction: ==========啦啦啦啦========");
                Toast.makeText(mContext, "啦啦啦啦啦啦啦", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, MainActivity.class));
                break;
        }

        return false;
    }

    /**
     * 处理WebView中每次跳转的URL
     *
     * @return
     */
    public boolean processH5Action() {
        if (!isValidAction()) {
            return false;
        }

        if (mActionUrl.startsWith(ActionDefineUtils.HEADER_PROTOCOL_ACTION)) {
            return processAction();
        }
        return false;
    }

}

