# SmartSwipeRefreshLayout [![version](https://jitpack.io/v/BugRui/SmartSwipeRefreshLayout.svg)](https://jitpack.io/#BugRui/SmartSwipeRefreshLayout/v1.0.3)

基于原生自带SwipeRefreshLayout封装，添加自动预加载数据，实现无感加载。

Based on Android built-in SwipeRefreshLayout, add automatic pre-load data, realize non-inductive load.

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
  implementation 'com.github.BugRui:SmartSwipeRefreshLayout:v1.0.3'
 
```
因为SmartSwipeRefreshLayout只在内部使用compileOnly，并且依赖于SwipeRefreshLayout和RecyclerView，所以需要将以下依赖项添加到自己的项目中，否则compileOnly将无法通过打包

Because SmartSwipeRefreshLayout only USES compileOnly internally and relies on SwipeRefreshLayout and RecyclerView, you need to add the following dependencies to your project otherwise compileOnly will not pass

```
 implementation 'androidx.swiperefreshlayout:swiperefreshlayout:latest.integration'
 implementation 'androidx.recyclerview:recyclerview:latest.integration'
```

### 使用(use)

#### 在Xml中配置 Configure in Xml

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
### 自身配置 Its configuration
```

//配置预取距离，当前最后可见Item距离最后一条的Item (默认为5) Configure the prefetch distance The default is 5
setPrefetchDistance(5)

//下拉刷新监听  Pull-down refresh listening
setOnRefreshingListener()

//自动刷新  Automatically refresh
autoRefreshing()

//加载刷新完成  Load flush is complete
finishRefreshing()

//加载更多完成 Load more complete
finishLoadingMore()

//加载完成全部，不会继续加载,下拉刷新会重置  The load is complete, the load will not continue, the dropdown refresh will reset
finishLoadingMoreAll()

//重置加载完成全部   The reset load is complete
setNoLoadingMoreAll()

//智能加载监听   Intelligent load monitoring
setOnLoadMoreListener()

//下拉刷新和智能加载监听   Pull-down refresh and smart load listening
setOnRefreshLoadMoreListener()
```

