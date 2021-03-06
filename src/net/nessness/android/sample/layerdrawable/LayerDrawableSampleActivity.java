package net.nessness.android.sample.layerdrawable;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class LayerDrawableSampleActivity extends Activity implements OnCheckedChangeListener {

    private LayerDrawable mLayer;
    private LayerDrawable mLayerEx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLayer = (LayerDrawable)((ImageView)findViewById(R.id.image)).getDrawable();
        mLayerEx = (LayerDrawable)((ImageView)findViewById(R.id.image_ex)).getDrawable();
        ((ToggleButton)findViewById(R.id.button)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        Drawable drawable = getNewDrawable(isChecked);
        Log.i("new drawable", drawable.getBounds().toString());

        /**
         * GBまで期待通りにならない
         * Rectが0 0 0 0
         */
        mLayer.setDrawableByLayerId(R.id.number, drawable);

        /**
         * ICSに習って現在のRectを
         * 新しいDrawableに設定してやる
         */
        Rect bounds = mLayerEx.findDrawableByLayerId(R.id.number).getBounds();
        // 同じDrawableを使わない
        drawable = getNewDrawable(isChecked);
        drawable.setBounds(bounds);
        mLayerEx.setDrawableByLayerId(R.id.number, drawable);


        // 再描画
        mLayer.invalidateSelf();
        mLayerEx.invalidateSelf();
        Log.i("mLayer drawable set", mLayer.findDrawableByLayerId(R.id.number).getBounds().toString());
        Log.i("mLayerEx drawable set", mLayerEx.findDrawableByLayerId(R.id.number).getBounds().toString());

    }

    private Drawable getNewDrawable(boolean isChecked){
        int res = isChecked? R.drawable.n2: R.drawable.n1;
        return getResources().getDrawable(res);
    }
}