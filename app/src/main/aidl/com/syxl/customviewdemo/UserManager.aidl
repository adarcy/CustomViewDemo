// UserManager.aidl
package com.syxl.customviewdemo;

import com.syxl.customviewdemo.User;
// Declare any non-default types here with import statements

interface UserManager {
    void addUser(in User user);

        List<User> getUserList();
}
