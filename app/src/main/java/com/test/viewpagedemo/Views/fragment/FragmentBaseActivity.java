package com.test.viewpagedemo.Views.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;


/**
 * fragment生命周期详细情况可以查看FragmentManagerImpl.moveToState(Fragment f, int newState, int transit, int transitionStyle,
 * boolean keepActive)
 * <p>
 * 1.fragment内存重启机制（如屏幕翻转，会调用onSaveInstanceState）
 * 1)保存机制
 * onSaveInstanceState()会调用fragmentmanagerimpl.saveAllState()保存数据
 * fms.mActive = active;保存了mActive里面的fragment
 * fms.mAdded = added; 记录了mAdd里面的fragment
 * 2）重启机制
 * 在onCreate的时候，如果savedInstanceState ！=null，这调用FragmentController.restoreAllState恢复fragment。
 * 然后调用dispatchCreate重新初始化fragment在界面显示。恢复mActive里面fragment的instantiate方法。并确保mAdd表示的fragment可见
 * 然后onCreate接着调用mFragments.dispatchCreate();方法，借由moveToState开始fragment的生命周期
 * <p>
 * <p>
 * 2.show hide
 * 1)如果你有一个很高的概率会再次使用当前的Fragment，建议使用show()，hide()，可以提高性能。
 * 如果你的app有大量图片，这时更好的方式可能是replace，配合你的图片框架在Fragment视图销毁时，回收其图片所占的内存。
 * 2)调用show  hide仅仅会调用fragment的onHiddenChanged方法
 * <p>
 */
public class FragmentBaseActivity extends AppCompatActivity {

    Fragment1 f1;
    Fragment2 f2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //如果savedInstanceState ！=null，这调用FragmentController.restoreAllState恢复fragment。然后调用dispatchCreate
        //重新初始化fragment在界面显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        //当activity被系统回收以后恢复，通过这种方式拿到缓存的f1，避免出现多余的f1
        if (savedInstanceState != null) {
            f1 = (Fragment1) getSupportFragmentManager().findFragmentByTag(Fragment1.getFragmentTag());
        }
        if (f1 == null) {
            System.out.println("new f1");
            f1 = new Fragment1();
        }
        f2 = new Fragment2();

        findViewById(R.id.addf1).setOnClickListener(listener);
        findViewById(R.id.addf2).setOnClickListener(listener);
        findViewById(R.id.removef1).setOnClickListener(listener);
        findViewById(R.id.removef2).setOnClickListener(listener);
        findViewById(R.id.detachf1).setOnClickListener(listener);
        findViewById(R.id.detachf2).setOnClickListener(listener);
        findViewById(R.id.repl1).setOnClickListener(listener);
        findViewById(R.id.repl2).setOnClickListener(listener);
        findViewById(R.id.addf2hidef1).setOnClickListener(listener);
        findViewById(R.id.show1).setOnClickListener(listener);
        findViewById(R.id.hide1).setOnClickListener(listener);
        findViewById(R.id.attachf1).setOnClickListener(listener);

    }

    public void setDataFromFragment(String data) {
        LoggerUtils.LOGD("data = " + data);
    }

    public void setBtn(String text) {
        ((Button) findViewById(R.id.addf1)).setText(text);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("activity onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("activity onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    //detach remove都会触发mAdded删除fragment，只有remove会触发mActive删除fragment;
    @NonNull
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addf1:
                    getSupportFragmentManager().
                            beginTransaction()
                            //这里传入的vp是一个ViewGroup，使用的时候container.addView(f.mView);
                            .add(R.id.vp, f1, Fragment1.getFragmentTag())
                            //调用fragmentmanger.addFragment(),然后backstackRecord.moveToState
                            .commit();
                    break;
                case R.id.addf2:
                    getSupportFragmentManager().beginTransaction().add(R.id.vp, f2)
                            .commit();
                    break;
                case R.id.removef1:

                    getSupportFragmentManager().beginTransaction().remove(f1)
                            .commit();
                    break;
                case R.id.removef2:
                    getSupportFragmentManager().beginTransaction().remove(f2)
                            .commit();
                    break;
                case R.id.detachf1:
                    getSupportFragmentManager().beginTransaction().
                            detach(f1)
                            .commit();
                    break;
                case R.id.detachf2:
                    getSupportFragmentManager().beginTransaction().detach(f2)
                            .commit();
                    break;
                case R.id.repl1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.vp, f1, Fragment1.getFragmentTag())
                            .commit();
                    break;
                case R.id.repl2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.vp, f2)
                            .commit();
                    break;
                case R.id.addf2hidef1:
                    getSupportFragmentManager().beginTransaction().add(R.id.vp, f2)
                            .hide(f1)
                            .commit();
                    break;
                case R.id.hide1:
                    getSupportFragmentManager().beginTransaction()
                            .hide(f1)
                            .commit();
                    break;
                case R.id.show1:
                    getSupportFragmentManager().beginTransaction()
                            .show(f1)
                            .commit();
                    break;
                case R.id.attachf1:
                    getSupportFragmentManager().beginTransaction()
                            .attach(f1)
                            .commit();
                    break;
                /**
                 * 参数string name是transaction.addToBackStack(String tag)中的tag值；
                 至于int flags有两个取值：0或FragmentManager.POP_BACK_STACK_INCLUSIVE；
                 当取值0时，表示除了参数一指定这一层之上的所有层都退出栈，指定的这一层为栈顶层；
                 当取值POP_BACK_STACK_INCLUSIVE时，表示连着参数一指定的这一层一起退出栈；
                 //默认将最上层的操作弹出回退栈
                 popBackStack()
                 */
//                getSupportFragmentManager().popBackStack(Fragment3.getFragmentTag(), 1);

                default:
                    break;
            }
        }
    };


}
