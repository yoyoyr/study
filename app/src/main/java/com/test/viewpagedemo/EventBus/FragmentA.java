package com.test.viewpagedemo.EventBus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentA extends Fragment {

    TextView textView;
    @NonNull
    static FragmentB fragmentB = new FragmentB();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmenta, container, false);

        textView = view.findViewById(R.id.fa);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = FragmentA.this;

                if (fragment.getActivity() != null) {
                    fragment.getFragmentManager().beginTransaction()
                            .add(R.id.content, fragmentB)
                            .addToBackStack(FragmentB.getName()).commit();
                }
            }
        });
        EventBus.getDefault().register(this);
        return view;
    }

    /**
     * 方法名可以随便取
     * <p>
     * •POSTING (默认) 表示事件处理函数的线程跟发布事件的线程在同一个线程。
     * •MAIN 表示事件处理函数的线程在主线程(UI)线程，因此在这里不能进行耗时操作。
     * •BACKGROUND 表示事件处理函数的线程在后台线程，因此不能进行UI操作。如果发布事件的线程是主线程(UI线程)，那么事件处理函数将会开启一个后台线程，如果果发布事件的线程是在后台线程，那么事件处理函数就使用该线程。
     * •ASYNC 表示无论事件发布的线程是哪一个，事件处理函数始终会新建一个子线程运行，同样不能进行UI操作。
     *
     * @param data 参数可以自定义
     *             <p>
     *             note EventBus还支持发送黏性事件，就是在发送事件之后再订阅该事件也能收到该事件
     *             EventBus.getDefault().postSticky(new MessageEvent("粘性事件"));
     *             @Subscribe(sticky = true ,threadMode = ThreadMode.MAIN)
     */
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1,sticky = true)
    public void onEvent(@NonNull Message data) {
        textView.setText(data.content);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getEvent(@NonNull Event event){
        LoggerUtils.LOGD("get event "+event.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static String getName() {
        return "FragmentA";
    }

    public static class Message {

        String content;

        public Message(String content) {
            this.content = content;
        }
    }
}
