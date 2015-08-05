# FoldingTabBar.Android

folding tabbar menu for Android. This is a menu library.You can easily add a nice animated tab menu to your app.

Inspired by [this project on Dribbble](https://dribbble.com/shots/2003376-Tab-Bar-Animation)

# Simple Apk 

[Apk Download](https://github.com/tosslife/FoldingTabBar.Android/releases/download/1.0.0/app-simple.apk)

# Screenshot

![screenshot](https://github.com/tosslife/FoldingTabBar.Android/blob/master/simple.gif)

# How to use (参考MainActivity)

### Maven
````
        <dependency>
            <groupId>com.github.tosslife</groupId>
            <artifactId>foldingtabbar</artifactId>
            <version>1.0.0</version>
            <type>aar</type>
        </dependency>
````
### Gradle

add in build.gradle:

````
        //Wait bintray audit ...
        compile 'com.github.tosslife:foldingtabbar:1.0.0'
````
### Xml

After adding the gradle dependencies from above you can go to your xml layout and add the following code for a TabBarView:

````
         <com.srx.widget.TabBarView
            android:id="@+id/tabBarView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true" />
````

### Java

To set some basic settings use the following java-code:

````
       //获取TabBarView
       TabBarView tabBarView = (TabBarView) findViewById(R.id.tabBarView);

       //设置主按钮图标
       tabBarView.setMainBitmap(R.drawable.icon_main);

       //设置菜单对应位置按钮图标及两侧图标
       tabBarView.bindBtnsForPage(0, R.drawable.icon_menu, R.drawable.icon_left, R.drawable.icon_right);

       //设置初始默认选中
       tabBarView.initializePage(0);

       //添加监听
        tabBarView.setOnTabBarClickListener(onTabBarClickListener);

       //监听回调
       private OnTabBarClickListener onTabBarClickListener = new OnTabBarClickListener() {

               @Override
               public void onMainBtnsClick(int position, int[] clickLocation) {
                    //点击菜单
               }

               @Override
               public void onMainBtnsClick(int position) {
                    //点击菜单
               }

               @Override
               public void onLeftBtnClick(int page) {
                    //点击对应菜单的左侧按钮
               }

               @Override
               public void onRightBtnClick(int page) {
                    //点击对应菜单的右侧按钮
               }

           };


       //
````



