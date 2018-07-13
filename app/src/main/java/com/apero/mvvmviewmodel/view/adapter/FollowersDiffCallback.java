package com.apero.mvvmviewmodel.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.apero.mvvmviewmodel.model.Followers;

import java.util.List;

public class FollowersDiffCallback extends DiffUtil.Callback {

    private final List<Followers> mOldEmployeeList;
    private final List<Followers> mNewEmployeeList;

    public FollowersDiffCallback(List<Followers> oldEmployeeList, List<Followers> newEmployeeList) {
        this.mOldEmployeeList = oldEmployeeList;
        this.mNewEmployeeList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return mOldEmployeeList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewEmployeeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldEmployeeList.get(oldItemPosition).getFullname() == mNewEmployeeList.get(
                newItemPosition).getFullname();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Followers oldEmployee = mOldEmployeeList.get(oldItemPosition);
        final Followers newEmployee = mNewEmployeeList.get(newItemPosition);

        return oldEmployee.getFullname().equals(newEmployee.getFullname());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}