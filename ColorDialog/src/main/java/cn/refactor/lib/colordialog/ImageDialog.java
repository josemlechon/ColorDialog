package cn.refactor.lib.colordialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

/**
 * 作者 : andy
 * 日期 : 15/11/7 17:26
 * 邮箱 : andyxialm@gmail.com
 * 描述 : Dialog
 */
public class ImageDialog extends Dialog implements View.OnClickListener {

    private ImageView mImageContent;

    private Bitmap mContentBitmap;

    private View mDialogView;

    private Drawable mDrawable;

    private AnimationSet mAnimIn, mAnimOut;

    private int mResId;

    private boolean mIsShowAnim;


    public ImageDialog(Context context) {
        super(context, R.style.colorDialogFullScreen);
        init();
    }

    private void callDismiss() {
        super.dismiss();
    }

    private void init() {
        mAnimIn = AnimationLoader.getInAnimation(getContext());
        mAnimOut = AnimationLoader.getOutAnimation(getContext());
        initAnimListener();
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getText(titleId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.layout_image_dialog, null);
        setContentView(contentView);

        //getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mImageContent = (ImageView) contentView.findViewById(R.id.imageview_content);

        mImageContent.setOnClickListener(this);


        if (null != mDrawable) {
            mImageContent.setBackgroundDrawable(mDrawable);
        }

        if (null != mContentBitmap) {
            mImageContent.setImageBitmap(mContentBitmap);
        }

        if (0 != mResId) {
            mImageContent.setBackgroundResource(mResId);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        startWithAnimation(mIsShowAnim);
    }

    @Override
    public void dismiss() {
        dismissWithAnimation(mIsShowAnim);
    }

    private void startWithAnimation(boolean showInAnimation) {
        if (showInAnimation) {
            mDialogView.startAnimation(mAnimIn);
        }
    }

    private void dismissWithAnimation(boolean showOutAnimation) {
        if (showOutAnimation) {
            mDialogView.startAnimation(mAnimOut);
        } else {
            super.dismiss();
        }
    }

    private void initAnimListener() {
        mAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        callDismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        dismiss();
    }

    public ImageDialog setAnimationEnable(boolean enable) {
        mIsShowAnim = enable;
        return this;
    }

    public ImageDialog setAnimationIn(AnimationSet animIn) {
        mAnimIn = animIn;
        return this;
    }

    public ImageDialog setAnimationOut(AnimationSet animOut) {
        mAnimOut = animOut;
        initAnimListener();
        return this;
    }

    public ImageDialog setContentImage(Drawable drawable) {
        mDrawable = drawable;
        return this;
    }

    public ImageDialog setContentImage(Bitmap bitmap) {
        mContentBitmap = bitmap;
        return this;
    }

    public ImageDialog setContentImage(int resId) {
        mResId = resId;
        return this;
    }

}
