package mvp.cn.common;


import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 *
 */
public class QuickActivity extends AppCompatActivity {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QuickApplication.getInstance().getRefWatcher().watch(this);
    }
}
