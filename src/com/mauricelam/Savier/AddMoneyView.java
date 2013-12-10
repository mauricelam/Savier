package com.mauricelam.Savier;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.text.Html;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    private int amount;
    private TextView amountLabel;
    private boolean centIncrement = false;
    private boolean dragging = false;

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
        amountLabel.setOnTouchListener(new OnTouchListener() {

            private float startY = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent == null)
                    return false;

                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = motionEvent.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (!dragging && startY - motionEvent.getY() > 30) {
                            // Start dragging
                            ClipData dragData = ClipData.newPlainText("money", String.valueOf(amount));
                            DragShadowBuilder shadow = new DragShadow(amountLabel);
                            AddMoneyView.this.startDrag(dragData, shadow, null, 0);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Layout layout = amountLabel.getLayout();
                        if (layout != null) {
                            float eventX = motionEvent.getX();
                            int offset = layout.getOffsetForHorizontal(0, eventX);
                            if (layout.getPrimaryHorizontal(offset) < eventX) {
                                // Fix the offset since offset is looking for the nearest space between characters
                                // instead of actual characters
                                offset += 1;
                            }
                            offset = Math.max(0, Math.min(offset, amountLabel.getText().length()));
                            int increment = amountLabel.getText().length() - offset;
                            centIncrement = increment <= 1;
                            setTextWithIncrement();
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        amountLabel.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        dragging = true;
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        dragging = false;
                        break;
                }
                return false;
            }
        });

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
        int factor = centIncrement ? 1 : 100;
        return delta / 2 * factor;
    }

    public void addAmount(int change) {
        this.setAmount(this.amount + change);
    }

    public void setAmount(int amount) {
        if (amount < 0) amount = 0;
        this.amount = amount;
        setTextWithIncrement();
    }

    private void setTextWithIncrement() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String text = formatter.format(amount / 100.0);

        String[] strs = text.split("\\.");
        if (centIncrement) {
            text = strs[0] + ".<font color=#33b5e5>" + strs[1] + "</font>";
        } else {
            text = "<font color=#33b5e5>" + strs[0] + "</font>." + strs[1];
        }

        amountLabel.setText(Html.fromHtml(text));
    }

    public int getAmount() {
        return amount;
    }

    private static class DragShadow extends DragShadowBuilder {

        public DragShadow(View view) {
            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            shadowTouchPoint.y += 50;
        }

    }

}
