//package com.test.viewpagedemo.mqttGetTui;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.igexin.sdk.GTIntentService;
//import com.igexin.sdk.message.GTCmdMessage;
//import com.igexin.sdk.message.GTNotificationMessage;
//import com.igexin.sdk.message.GTTransmitMessage;
//import com.test.viewpagedemo.LoggerUtils;
//
///**
// * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
// * onReceiveMessageData 处理透传消息<br>
// * onReceiveClientId 接收 cid <br>
// * onReceiveOnlineState cid 离线上线通知 <br>
// * onReceiveCommandResult 各种事件处理回执 <br>
// */
//public class DemoIntentService extends GTIntentService {
//
//    public DemoIntentService() {
//
//    }
//
//    @Override
//    public void onReceiveServicePid(Context context, int pid) {
//        LoggerUtils.LOGE("onReceiveServicePid");
//    }
//
//    @Override
//    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
//        LoggerUtils.LOGE("msg = " + msg.getMessageId() + msg.getPayload());
//    }
//
//    @Override
//    public void onReceiveClientId(Context context, String clientid) {
//        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
//    }
//
//    @Override
//    public void onReceiveOnlineState(Context context, boolean online) {
//        LoggerUtils.LOGE("onReceiveOnlineState");
//    }
//
//    @Override
//    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
//        LoggerUtils.LOGE("onReceiveCommandResult");
//    }
//
//    @Override
//    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
//        LoggerUtils.LOGE("onNotificationMessageArrived " + msg.getContent() + "-" + msg.getTitle());
//    }
//
//    @Override
//    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
//        LoggerUtils.LOGE("onNotificationMessageClicked");
//    }
//}