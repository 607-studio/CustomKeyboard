package kq.cn.customkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_input_price)
    EditText etInputPrice;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.layout_opt_container)
    LinearLayout optContainer;
    @BindView(R.id.view_under)
    View underView;

    Unbinder mUnBinder;
    CustomKeyboardManager customKeyboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnBinder) mUnBinder.unbind();
    }

    private void initView() {
        customKeyboardManager = new CustomKeyboardManager(this);

        CustomKeyboardManager.BaseKeyboard priceKeyboard = new CustomKeyboardManager.BaseKeyboard(this, R.xml.stock_price_num_keyboard) {
            @Override
            public boolean handleSpecialKey(EditText etCurrent, int primaryCode) {
                if (primaryCode == getKeyCode( R.integer.keycode_cur_price)) {
                    etCurrent.setText("9.99");
                    return true;
                }
                return false;
            }
        };

        customKeyboardManager.attachTo((EditText) $(R.id.et_input_price1), priceKeyboard);
        customKeyboardManager.attachTo((EditText) $(R.id.et_input_price2), priceKeyboard);
        //customKeyboardManager.attachTo((EditText) $(R.id.et_input_price3), priceKeyboard);
        //customKeyboardManager.attachTo((EditText) $(R.id.et_input_price4), priceKeyboard);
        //customKeyboardManager.attachTo((EditText) $(R.id.et_input_price5), priceKeyboard);
        customKeyboardManager.attachTo((EditText) $(R.id.et_input_price6), priceKeyboard);

        customKeyboardManager.attachTo(etInputPrice, priceKeyboard);

        customKeyboardManager.attachTo(etInputNum, new CustomKeyboardManager.BaseKeyboard(this, R.xml.stock_trade_num_keyboard) {
            @Override
            public boolean handleSpecialKey(EditText etCurrent, int primaryCode) {
                Editable editable = etCurrent.getText();
                int start = etCurrent.getSelectionEnd();
                if (primaryCode == getKeyCode( R.integer.keycode_stocknum_000)) {
                    editable.insert(start, "000");
                    return true;
                } else if (primaryCode == getKeyCode(R.integer.keycode_stocknum_half)){
                    etCurrent.setText(1000 /2+"");
                    return true;
                } else if (primaryCode == getKeyCode(R.integer.keycode_stocknum_all)){
                    setStockNumAll(etCurrent);
                    return true;
                }
                return false;
            }
        });
        customKeyboardManager.setShowUnderView(underView);
    }

    @OnClick(R.id.tv_stock_all)
    public void setStockNumAll(View view){
        etInputNum.setText("10000");
    }

    @Deprecated
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }
}
