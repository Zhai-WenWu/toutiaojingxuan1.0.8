package mvp.cn.common;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


public class QuickCompatActivity extends AppCompatActivity {
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
