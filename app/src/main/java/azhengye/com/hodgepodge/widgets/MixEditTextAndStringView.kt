package azhengye.com.hodgepodge.widgets

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import azhengye.com.hodgepodge.Utils.getPxFromDp




/**
 * Created by XiangKang on 2019-08-23.
 */
class MixEditTextAndStringView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), TextWatcher, View.OnKeyListener {

    private val mContext: Context = context
    private val boxCount = 8;
    private val boxWidth = getPxFromDp(context, 40)
    private val boxHeight = getPxFromDp(context, 40)
    private val padding = 14
    private var beforeStr: String? = null
    private var afterStr: String? = null

    init {
        val a = context.obtainStyledAttributes(attrs, azhengye.com.hodgepodge.R.styleable.MixEditTextAndString)
        beforeStr = a.getString(azhengye.com.hodgepodge.R.styleable.MixEditTextAndString_beforeText)
        afterStr = a.getString(azhengye.com.hodgepodge.R.styleable.MixEditTextAndString_afterText)
        a.recycle()

        initViews()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs);
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = View.MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = View.MeasureSpec.getMode(heightMeasureSpec)

        var resultWidth = 0
        var resultHeight = 0

        var lineWidth = 0
        var lineHeight = 0

        var textViewLastLineWidth = 0F

        for (index in 0 until childCount) {
            var childView = getChildAt(index)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            if (childView is TextView) {
                resultWidth += childView.measuredWidth
                resultHeight = childView.measuredHeight;
                textViewLastLineWidth = childView.layout.getLineWidth(childView.layout.lineCount - 1)

            } else if (childView is EditText) {
                val mlp = childView.getLayoutParams() as ViewGroup.MarginLayoutParams
                val editChildWidth = childView.measuredWidth + mlp.marginStart + mlp.marginEnd
                val editChildHeight = childView.measuredHeight + mlp.topMargin + mlp.bottomMargin

                //换行
                if (editChildWidth + textViewLastLineWidth > sizeWidth) {
                    lineHeight = editChildHeight;
                    lineWidth = editChildWidth;
                    resultWidth = Math.max(resultWidth, lineWidth);
                    resultHeight += lineHeight;
                } else {
                    lineWidth = (editChildWidth + textViewLastLineWidth).toInt();
                    resultWidth = Math.max(resultWidth, (textViewLastLineWidth + lineWidth).toInt());
                    resultHeight = resultHeight
                }
            }
        }

        setMeasuredDimension(
            if (modeWidth === View.MeasureSpec.EXACTLY) sizeWidth else resultWidth,
            if (modeHeight === View.MeasureSpec.EXACTLY) sizeHeight else resultHeight
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var flowWidth = getWidth();

        var childLeft = 0;
        var childTop = 0;

        //遍历子控件，记录每个子view的位置
        for (index in 0 until childCount) {
            var childView = getChildAt(index);

            //获取到测量的宽和高
            var childWidth = childView.getMeasuredWidth();
            var childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离

            if (childLeft + childWidth > flowWidth) {
                //换行处理
                childTop += (childHeight)
                childLeft = 0;
            }else{
                childWidth += childWidth
            }
            //布局
            var left = childLeft
            var top = childTop
            var right = childWidth
            var bottom = childHeight
            childView.layout(left, top, right, bottom)

            childLeft += (childWidth)
        }
    }

    private fun initViews() {
        addTextView(beforeStr)

        for (i in 0 until boxCount) {
            var editText = EditText(context);
            var layoutParams = LinearLayout.LayoutParams(boxWidth, boxHeight);
            layoutParams.bottomMargin = padding;
            layoutParams.topMargin = padding;
            layoutParams.leftMargin = padding;
            layoutParams.rightMargin = padding;
            layoutParams.gravity = Gravity.CENTER;


            editText.setOnKeyListener(this);
            editText.setTextColor(Color.BLACK);
            editText.setLayoutParams(layoutParams);
            editText.setGravity(Gravity.CENTER);
            setBackgroundRes(editText, false);

            editText.setInputType(InputType.TYPE_CLASS_TEXT);

            editText.setId(i);
            editText.setEms(1);
            editText.addTextChangedListener(this);

            addView(editText)
        }

        addTextView(afterStr)
    }

    private fun addTextView(str: String?) {
        var beforeText = TextView(context)
        beforeText.setText(str)
        beforeText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F);
        beforeText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        addView(beforeText)
    }

    private fun setBackgroundRes(editText: EditText, focus: Boolean) {
        if (focus) {
            editText.background = resources.getDrawable(azhengye.com.hodgepodge.R.drawable.focus_bg)
        } else {
            editText.background = resources.getDrawable(azhengye.com.hodgepodge.R.drawable.normal_bg)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}