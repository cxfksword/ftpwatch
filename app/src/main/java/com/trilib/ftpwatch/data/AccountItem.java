package com.trilib.ftpwatch.data;

import androidx.annotation.NonNull;

import com.trilib.ftpwatch.utils.StorageUtil;

import java.io.Serializable;

public class AccountItem implements Serializable{
    public long id=-1;
    public String account="";
    public String password="";
    public String path= StorageUtil.getMainStoragePath();
    public boolean writable=false;

    @Override
    @NonNull
    public String toString() {
        return "AccountItem{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", path='" + path + '\'' +
                ", writable=" + writable +
                '}';
    }
}