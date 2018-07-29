package com.geekandroidframework.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.geekandroidframework.ui.IScreen;
import com.geekandroidframework.ui.activity.BaseActivity;

public abstract class BaseFragment extends Fragment implements IScreen {

    /**
     * @return
     */
    protected BaseActivity getBaseActivity() {
        FragmentActivity activity = getActivity();
        if (!(activity instanceof BaseActivity) || activity.isFinishing()) {
            return null;
        }
        return (BaseActivity) activity;
    }
}
