package azhengye.com.hodgepodge.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import azhengye.com.hodgepodge.R
import azhengye.com.hodgepodge.Utils.getScreenHeight
import azhengye.com.hodgepodge.Utils.getScreenWidth

/**
 * Created by azhengye on 2019-08-16.
 */

class PinballAnimationActivity : BaseActivity() {
    val TAG = "azhengye"

    var screenW: Int = 0
    var rootView: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinball_layout)
        screenW = getScreenWidth(this)
        rootView = findViewById(R.id.root_view)

        findViewById<View>(R.id.testBtn).setOnClickListener(View.OnClickListener {

            val movingView = addAnimationView()

            //弹球动画
            startBallAnimation(movingView)

            //窗口抖动动画
            startWindowAnimation()
        })
    }

    private fun addAnimationView(): View {
        val movingView = ImageView(this)
        movingView.setImageResource(R.drawable.test_icon)

        rootView?.addView(movingView)

        val layoutparam = movingView.layoutParams as FrameLayout.LayoutParams
        layoutparam.gravity = Gravity.BOTTOM
        layoutparam.marginStart = (Math.random() * (getScreenWidth(this) - 300)).toInt()
        layoutparam.height = FrameLayout.LayoutParams.WRAP_CONTENT
        layoutparam.width = FrameLayout.LayoutParams.WRAP_CONTENT
        return movingView
    }

    private fun startWindowAnimation() {
        val springAnimation = createSpringAnimation(window.decorView, SpringAnimation.TRANSLATION_X);
        springAnimation.start();
    }

    private fun startBallAnimation(movingView: View) {
        //X轴方向的动画
        //参考 https://mp.weixin.qq.com/s/z-eH1o9Bc0_Cijp2T-qcSg 优化动画
        movingView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        val flingXAnimation = createFlingAnimation(movingView, DynamicAnimation.TRANSLATION_X);
        flingXAnimation.addUpdateListener { animation, value, velocity ->
            if (velocity > 0 && movingView.x > screenW - movingView.width) {
                flingXAnimation.setStartVelocity(-Math.abs(velocity))
            } else if (velocity < 0 && movingView.x < 0) {
                flingXAnimation.setStartVelocity(Math.abs(velocity))
            }
            animation.start()
        }
        flingXAnimation.setFriction(0.2f)
        flingXAnimation.start();

        //Y轴方向的动画
        val flingYAnimation = createFlingAnimation(movingView, DynamicAnimation.TRANSLATION_Y);
        flingYAnimation.addUpdateListener { animation, value, velocity ->
            if (velocity < 0 && movingView.y < 0) {
                flingYAnimation.setStartVelocity(Math.abs(velocity)).setFriction(0.3f)
            } else if (velocity > 0) {
                flingYAnimation.cancel();
                val holder1 = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 1f, 0f)
                val holder2 = PropertyValuesHolder.ofFloat(
                    View.Y,
                    movingView.getY(),
                    (getScreenHeight(this) - 200 - movingView.height).toFloat()
                )

                val animator = ObjectAnimator.ofPropertyValuesHolder(movingView, holder1, holder2)
                animator.setDuration(3000);

                val bounceInterpolator = BounceInterpolator()

                animator.setInterpolator(bounceInterpolator);
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        movingView.setLayerType(View.LAYER_TYPE_NONE, null);
                        rootView?.removeView(movingView)
                    }
                })
                animator.start()
            }
        }
        flingYAnimation.start();
    }

    fun createSpringAnimation(view: View, property: DynamicAnimation.ViewProperty): SpringAnimation {
        val animation = SpringAnimation(view, property)
        animation.setStartVelocity(4000f);

        val spring = SpringForce(0f)
        spring.stiffness = SpringForce.STIFFNESS_MEDIUM
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        animation.spring = spring
        return animation
    }

    fun createFlingAnimation(view: View, property: DynamicAnimation.ViewProperty): FlingAnimation {
        val animation = FlingAnimation(view, property)
        animation.setStartVelocity(-3000f).setFriction(0.01f)
        animation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS)
        return animation
    }
}