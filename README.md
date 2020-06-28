# SmartSwipeRefreshLayout [![version](https://jitpack.io/v/BugRui/SmartSwipeRefreshLayout.svg)](https://jitpack.io/#BugRui/SmartSwipeRefreshLayout/1.0.2)

基于原生自带SwipeRefreshLayout封装，添加自动预加载数据，实现无感加载。


### 集成
#### Step 1. Add the JitPack repository to your build file
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

```
####  Step 2. Add the dependency
```
  implementation 'com.github.BugRui:SmartSwipeRefreshLayout:1.0.2'
```

### 使用

#### 在Xml中配置

```
<?xml version="1.0" encoding="utf-8"?>
<com.bugrui.refresh.SmartSwipeRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/refreshLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:showIn="@layout/activity_main">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</com.bugrui.refresh.SmartSwipeRefreshLayout>

```

#### 配置预取距离，当前最后可见Item距离最后一条的Item (不配置默认为5)
```
setPrefetchDistance(5)
```
#### 自动刷新
```
autoRefreshing()
```
