package com.ldt.vrview.transform;

import android.view.View;

import com.ldt.vrview.gesture.ViewAttacher;

public class BaseTransformer implements Transformer, ViewAttacher {
    private final int mId;

    public TransformListener getTransformListener() {
        return mTransformListener;
    }

    public void setTransformListener(TransformListener transformListener) {
        mTransformListener = transformListener;
    }

    protected void notifyTransformChanged() {
        if(mTransformListener != null) mTransformListener.onTransformChanged(mId,mValues);
    }

    private TransformListener mTransformListener;
    public float[] mValues = new float[] {0,0,0}; // left-right, up-down, and orientation
    public float mScale = 1;
    protected float mViewWidth = 1;
    protected float mViewHeight = 1;

    protected float mTextureWidth = 1;
    protected float mTextureHeight = 1;

    private boolean[] mEnableValues = new boolean[] {true,true,true,true};

    public BaseTransformer(final int id) {
        mId = id;
    }

    @Override
    public void setViewSize(int width, int height) {
        mViewWidth = width;
        mViewHeight = height;
        updateSize();
    }

    @Override
    public void setTextureSize(float width, float height) {
        mTextureWidth = width;
        mTextureHeight = height;
        updateSize();
    }


    @Override
    public void updateSize() {
    }

    @Override
    public void updateTransform() {
    }

    @Override
    public void setValue(int type, float value) {
        mValues[type] = value;
    }

    @Override
    public void scrollBy(float x, float y, float z) {
        mValues[0] = x;
        mValues[1] = y;
        mValues[2] = z;
    }

    @Override
    public void scrollBy(float[] values) {
        System.arraycopy(values,0,mValues,0,3);
    }

    @Override
    public void scrollXBy(float x) {
        mValues[0] = x;
    }

    @Override
    public void scrollYBy(float y) {
        mValues[1] = y;
    }

    @Override
    public void scrollZBy(float z) {
        mValues[1] = z;
    }

    @Override
    public float[] getCurrentScroll() {
        return mValues;
    }

    @Override
    public void reset() {
        mValues[0] = 0;
        mValues[1] = 0;
        mValues[2] = 0;
    }

    @Override
    public void setEnable(int type, boolean enable) {
        mEnableValues[type] = enable;
    }

    @Override
    public boolean isEnabled(int type) {
        return mEnableValues[type];
    }


    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    @Override
    public void attach(View view) {

    }

    @Override
    public void detach() {
    }
}