package com.mauricelam.Savier;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 1:37 PM
 */
public class AddMoneyView extends RelativeLayout {
    // This view should compose the CoinView, MoneySliderView and the +/- buttons

    private Context context;

    private int amount;
    private TextView amountLabel;

    public AddMoneyView(Context context) {
        super(context);
        init(context);
    }

    public AddMoneyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddMoneyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_money_component, this, true);

        Button plusButton = (Button) view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount(100);
            }
        });

        Button minusButton = (Button) view.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount(-100);
            }
        });

        amountLabel = (TextView) view.findViewById(R.id.money_label);

        SeekBar slider = (SeekBar) view.findViewById(R.id.money_slider);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int startAmount;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                AddMoneyView.this.setAmount(startAmount + getChangeAmount(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                this.startAmount = amount;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                this.startAmount = amount;
                seekBar.setProgress(40);
            }
        });

        this.setAmount(100);
    }

    private int getChangeAmount(int sliderValue) {
        int delta = sliderValue - 40;
        int sign = Integer.signum(delta);
        int deltaMagnitude = Math.abs(delta);
        if (deltaMagnitude <= 10) {
            return deltaMagnitude * sign;
        } else if (deltaMagnitude <= 20) {
            return (deltaMagnitude - 10) * 10 * sign;
        } else if (deltaMagnitude <= 30) {
            return (deltaMagnitude - 20) * 100 * sign;
        } else {
            return (deltaMagnitude - 30) * 1000 * sign;
        }
    }

    public void addAmount(int change) {
        this.setAmount(this.amount + change);
    }

    public void setAmount(int amount) {
        if (amount < 0)
            amount = 0;
        this.amount = amount;
        // FIXME use something like printf. The formatting is wrong
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        amountLabel.setText(formatter.format(amount / 100.0));
    }

    public int getAmount() {
        return amount;
    }
}
