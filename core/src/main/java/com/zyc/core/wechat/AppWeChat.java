package com.zyc.core.wechat;

import android.app.Activity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyc.core.app.App;
import com.zyc.core.app.ConfigKeys;
import com.zyc.core.wechat.callbacks.IWeChatSignInCallback;

/**
 * @Author: zyc
 * @CreateDate: 2019/8/6 14:08
 * @Description: 懒汉模式的微信API信息
 */
public class AppWeChat {
    static final String APP_ID = App.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    static final String APP_SECRET = App.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback = null;

    private static final class Holder {
        private static final AppWeChat INSTANCE = new AppWeChat();
    }

    public static AppWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private AppWeChat() {
        final Activity activity = App.getConfiguration(ConfigKeys.ACTIVITY);
        this.WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public AppWeChat onSignSuccess(IWeChatSignInCallback callback) {
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public final void SigIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
