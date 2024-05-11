package com.mcyizy.editor.list

//基础库
import android.content.Context
import android.widget.PopupMenu
//widget
import com.google.android.material.tabs.TabLayout
//App
import com.mcyizy.android.tool2.FileOperation

public class FileShortcutList(private val mContext: Context,private val mTabLayout: TabLayout) {

    //监听器声明
    private var mListeners: OnListener? = null
    //App
    private val mFileOperation : FileOperation = FileOperation()
    //基础配置
    var LatchListener : Boolean = false
    //配置
    companion object {
        private const val CLOSE = "关闭"
        private const val CLOSE_OTHER = "关闭其它"
        private const val CLOSE_ALL = "关闭全部"
    }
    
    //初始化
    init {
        //锁住监听器
        LatchListener = true
        //加载监听器
        Listener()
    }
    
    //添加
    public fun addItem(path : String) {
        val newElement = path
        if (isTagExists(mTabLayout,newElement)) {
            LatchListener = true //锁住监听器
            mTabLayout.getTabAt(isTagindex(mTabLayout,newElement))?.select()
            LatchListener = false //解锁监听器
        } else {
            //添加
            val tab = mTabLayout.newTab()
            tab.text = mFileOperation.getFileNmae(path)
            tab.tag = path
            mTabLayout.addTab(tab)
            //选中末尾
            LatchListener = true //锁住监听器
            val lastIndex = mTabLayout.tabCount - 1 // 获取最后一个标签的索引
            mTabLayout.getTabAt(lastIndex)?.select() // 选中最后一个标签
            LatchListener = false //解锁监听器
        }
    }
    
    //判断
    private fun isTagExists(tabLayout: TabLayout, tag: String): Boolean {
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            if (tab?.tag == tag) {
                return true
            }
        }
        return false
    }
    //根据名称获取索引
    private fun isTagindex(tabLayout: TabLayout, tag: String): Int {
        var ints = 0
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            if (tab?.tag == tag) {
                ints = i
            }
        }
        return ints
    }
    
    //监听器
    private fun Listener() {
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (!LatchListener) {
                    //获取
                    val index = tab.position
                    val view = tab.customView
                    // 处理索引和视图
                    val path_text = tab.tag as? String ?: return
                    mListeners?.onSelect(index)
                    mListeners?.onSelect2(path_text.toString())
                    //防止打开文件时多次触发打开
                    false
                }
            }
        
            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 处理标签取消选定事件
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // 处理重新选定事件
                val position = tab.position
                //弹出菜单
                val popupMenu = PopupMenu(mContext, tab.view)
                popupMenu.menu.add(CLOSE)
                popupMenu.menu.add(CLOSE_OTHER)
                popupMenu.menu.add(CLOSE_ALL)
                popupMenu.setOnMenuItemClickListener {
                    when (it.title) {
                        CLOSE -> {
                            mTabLayout.removeTabAt(position)
                            Function()
                        }
                        CLOSE_OTHER -> {
                            if (mTabLayout.tabCount > 1) {
                                mTabLayout.removeOnTabSelectedListener(this)
                                while (mTabLayout.tabCount - 1 > position) {
                                    val index = mTabLayout.tabCount - 1
                                    mTabLayout.removeTabAt(index)
                                }
                                while (tab.position > 0) {
                                    mTabLayout.removeTabAt(0)
                                }
                                mTabLayout.selectTab(tab)
                                mTabLayout.addOnTabSelectedListener(this)
                            }
                        }
                        CLOSE_ALL -> {
                            mTabLayout.removeOnTabSelectedListener(this)
                            for (i in mTabLayout.tabCount - 1 downTo 0) {
                                mTabLayout.removeTabAt(i)
                            }
                            mTabLayout.addOnTabSelectedListener(this)
                            Function()
                        }
                    }
                    true
                }  
                val isPopupShowing = popupMenu.menu.hasVisibleItems()
                popupMenu.show()
            }
        })
    }
    
    //加载功能
    private fun Function() {
        //列表判断是否为空
        val tabCount = mTabLayout.tabCount
        if (tabCount <= 0) {
            mListeners?.onEmpty()
        }
        /*
        //防止空指针
        if (selectedIndex >= 0) {
            //获取位置
            mListeners?.onSelect(selectedIndex)
            
        }
        */
    }
    
    fun setOnListener(listener: OnListener) {
        mListeners = listener
    }    
    
    interface OnListener {
        fun onEmpty()
        fun onSelect(index: Int)
        fun onSelect2(Text: String)
    }
    
    
}
