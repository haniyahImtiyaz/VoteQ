package com.ceria.pkl.voteq;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by win 8 on 7/20/2016.
 */
public class ExpandGrid extends ListView {

        private android.view.ViewGroup.LayoutParams params;
        private int old_count = 0;

        public ExpandGrid(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (getCount() != old_count) {
                old_count = getCount();
                params = getLayoutParams();

                setLayoutParams(params);

            }

            super.onDraw(canvas);
        }

    }
