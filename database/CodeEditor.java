    //待会需要处理的
    Drawable()
    
    //setNonPrintablePaintingFlags(int)
    public static final int FLAG_DRAW_WHITESPACE_LEADING = 1;
    public static final int FLAG_DRAW_WHITESPACE_INNER = 1 << 1;
    public static final int FLAG_DRAW_WHITESPACE_TRAILING = 1 << 2;
    public static final int FLAG_DRAW_WHITESPACE_FOR_EMPTY_LINE = 1 << 3;
    public static final int FLAG_DRAW_LINE_SEPARATOR = 1 << 4;
    public static final int FLAG_DRAW_WHITESPACE_IN_SELECTION = 1 << 6;
    //WINDOW_POS
    public static final int WINDOW_POS_MODE_AUTO = 0;
    public static final int WINDOW_POS_MODE_FOLLOW_CURSOR_ALWAYS = 1;
    public static final int WINDOW_POS_MODE_FULL_WIDTH_ALWAYS = 2;
    static final int ACTION_MODE_NONE = 0;
    static final int ACTION_MODE_SEARCH_TEXT = 1;
    static final int ACTION_MODE_SELECT_TEXT = 2;

    public float measureTextRegionOffset() //测量文本区域偏移
    public float getOffset(int line, int column) //获取偏移量
    public float getCharOffsetX(int line, int column) //获取字符偏移量X
    public float getCharOffsetY(int line, int column) //获取字符偏移量Y
    //设置如何控制完成窗口的位置和大小
    public int getCompletionWndPositionMode()
    public void setCompletionWndPositionMode(int mode)
    //格式提示
    public String getFormatTip()
    public void setFormatTip(@NonNull String formatTip)
    //设置行号区域是否与代码区域一起滚动
    public void setPinLineNumber(boolean pinLineNumber) 
    public boolean isLineNumberPinned() 
    //在自动换行模式下在屏幕上显示第一个行号
    public boolean isFirstLineNumberAlwaysVisible() 
    public void setFirstLineNumberAlwaysVisible(boolean enabled) 
    //插入文本
    public void insertText(String text, int selectionOffset)
    //为自动完成窗口设置适配器
    public void setAutoCompletionItemAdapter(@Nullable EditorCompletionAdapter adapter) 
    //设置光标闪烁周期，为0时始终显示
    public void setCursorBlinkPeriod(int period)
    //设置连体字已启用
    public boolean isLigatureEnabled()
    public void setLigatureEnabled(boolean enabled)
    //为编辑器使用的所有绘画设置字体功能设置
    public void setFontFeatureSettings(String features)
    //是否突出显示当前代码块
    public boolean isHighlightCurrentBlock() 
    public void setHighlightCurrentBlock(boolean highlightCurrentBlock) 
    //选择文本时，光标是否应停留在文本行上
    public boolean isStickyTextSelection()
    public void setStickyTextSelection(boolean stickySelection)
    //指定编辑器是否应使用不同的颜色进行绘制，当前行的背景
    public boolean isHighlightCurrentLine() {
    public void setHighlightCurrentLine(boolean highlightCurrentLine) 
    //编辑器语言
    public Language getEditorLanguage() 
    public void setEditorLanguage(@Nullable Language lang) 
    //设置代码块行的宽度 
    public float getBlockLineWidth() 
    public void setBlockLineWidth(float dp) 
    //自动换行
    public boolean isWordwrap() 
    public void setWordwrap(boolean wordwrap) 
    //设置光标动画启用
    public boolean isCursorAnimationEnabled() 
    public void setCursorAnimationEnabled(boolean enabled) 
    //设置滚动条已启用
    public void setScrollBarEnabled(boolean enabled) 
    //是否在垂直滚动条旁边显示行号面板
    public boolean isDisplayLnPanel() 
    public void setDisplayLnPanel(boolean displayLnPanel) 
    //在垂直滚动条旁边的行号面板中设置显示位置模式 style.LineInfoPanelPositionMode
    public int getLnPanelPositionMode() 
    public void setLnPanelPositionMode(int mode) 
    //设置垂直滚动条旁边的行号面板的显示位置 style.LineInfoPanelPosition
    public int getLnPanelPosition() 
    public void setLnPanelPosition(int position) 
    //水平滚动条已启用
    public boolean isHorizontalScrollBarEnabled() 
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
    //垂直滚动已启用
    public boolean isVerticalScrollBarEnabled() 
    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
    //字体大小PX
    public float getTextSizePx() 
    public void setTextSizePx(@Px float size) 
    //设置是否允许编辑器使用RenderNode绘制其文本，可能会使内存过高
    public boolean isHardwareAcceleratedDrawAllowed() 
    public void setHardwareAcceleratedDrawAllowed(boolean acceleratedDraw) 
    //边缘效应的颜色
    public int getEdgeEffectColor() 
    public void setEdgeEffectColor(int color) 
    //获取当前光标块
    public int getCurrentCursorBlock() 
    //获取行号区域的宽度（包括行号边距）
    public float measureLineNumber() {
    //删除光标前的文本或选定的文本（如果有）
    public void deleteText() {
    //从IME提交当前状态的文本
    public void commitText(CharSequence text) 
    public void commitText(CharSequence text, boolean applyAutoIndent) 
    //行信息面板的文本大小
    public float getLineInfoTextSize() 
    public void setLineInfoTextSize(float size) 
    //不可打印的符号
    public int getNonPrintablePaintingFlags() 
    public void setNonPrintablePaintingFlags(int flags) 
    //使所选内容可见
    public void ensureSelectionVisible() 
    //使给定的字符位置可见
    public void ensurePositionVisible(int line, int column) 
    //是否有卡子
    public boolean hasClip() 
    //检查位置是否超过最大Y位置
    public boolean isOverMaxY(float posOnScreen) 
    //使用滚动坐标中的位置确定字符位置
    public long getPointPosition(float xOffset, float yOffset) {
    //使用“视图上的位置”确定字符位置
    public long getPointPositionOnScreen(float x, float y) {
    //获取最大滚动
    public int getScrollMaxY() 
    public int getScrollMaxX() 
    //设置编辑器是否使用基本显示模式来呈现和测量文本。
    public void setBasicDisplayMode(boolean enabled) 
    public boolean isBasicDisplayMode() 
    //格式化文本异步。
    public synchronized boolean formatCodeAsync() 
    //设置给定区域中的文本格式。
    public synchronized boolean formatCodeAsync(CharPosition start, CharPosition end) 
    //Tab 宽度
    public int getTabWidth() 
    public void setTabWidth(int width) 
    //用户缩放可使用的最大和最小文本大小
    public void setScaleTextSizes(float minSize, float maxSize) 
    //已启用截取父级水平滚动
    public boolean isInterceptParentHorizontalScrollEnabled() 
    public void setInterceptParentHorizontalScrollIfNeeded(boolean forceHorizontalScrollable)  
    //是否突出显示括号对
    public boolean isHighlightBracketPair() 
    public void setHighlightBracketPair(boolean highlightBracketPair) 
    //指定编辑器的输入类型
    public int getInputType() {
    public void setInputType(int inputType) {
    //撤销和重做
    public void undo() 
    public void redo() 
    //检查我们是否可以撤消与重做
    public boolean canUndo() 
    public boolean canRedo() 
    //启用/禁用撤消管理器
    public boolean isUndoEnabled() 
    public void setUndoEnabled(boolean enabled) 
    //启动搜索操作模式
    public void beginSearchMode() 
    //分隔线左右边距
    public float getDividerMarginLeft() 
    public float getDividerMarginRight() 
    //设置分隔线的左右边距
    public void setDividerMargin(@Px float marginLeft, @Px float marginRight) 
    public void setDividerMargin(@Px float margin) 
    //设置行号左边距
    public void setLineNumberMarginLeft(@Px float lineNumberMarginLeft) 
    public float getLineNumberMarginLeft() 
    //设置分隔线的宽度
    public float getDividerWidth() 
    public void setDividerWidth(float dividerWidth) 
    //设置行号字体
    public Typeface getTypefaceLineNumber() 
    public void setTypefaceLineNumber(Typeface typefaceLineNumber) 
    //设置文本的字体
    public Typeface getTypefaceText() 
    public void setTypefaceText(Typeface typefaceText) 
    //设置绘制的文本比例X
    public float getTextScaleX() 
    public void setTextScaleX(float textScaleX) 
    //设置绘制的字母间距
    public float getTextLetterSpacing() 
    public void setTextLetterSpacing(float textLetterSpacing) 
    //宽度光标宽度
    public void setCursorWidth(float width) 
    //获取插入选择宽度
    public float getInsertSelectionWidth() 
    //获取行计数
    public int getLineCount() 
    //获取屏幕上的第一个可见行
    public int getFirstVisibleLine() 
    //检查此行在屏幕上是否可见
    public boolean isRowVisible(int row) 
    //设置此TextView的行距。除最后一行外的每一行都有其高度。
    public void setLineSpacing(float add, float mult) 
    public float getLineSpacingExtra() //获取行距和额外空间
    //应添加到除最后一行以外的每一行的值（以像素为单位）
    public void setLineSpacingExtra(float lineSpacingExtra) 
    public float getLineSpacingMultiplier() 
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) 
    public int getLineSpacingPixels() 
    //直接获取基线
    public int getRowBaseline(int row) 
    //获取行高
    public int getRowHeight() 
    //获取行顶部Y偏移
    public int getRowTop(int row) 
    //获取行底Y偏移
    public int getRowBottom(int row) 
    //获取目标行中文本的顶部
    public int getRowTopOfText(int row) 
    //获取目标行中文本的底部
    public int getRowBottomOfText(int row) 
    //获取行中文本的高度
    public int getRowHeightOfText() 
    //获取滚动X、Y
    public int getOffsetX() 
    public int getOffsetY() 
    //指示布局是否正常工作
    public void setLayoutBusy(boolean busy) 
    //是否可以编辑文本
    public boolean isEditable() 
    public boolean getEditable() 
    public void setEditable(boolean editable) 
    //允许通过缩略图缩放文本大小
    public boolean isScalable() 
    public void setScalable(boolean scale) 
    //设置代码线是否可用
    public boolean isBlockLineEnabled() 
    public void setBlockLineEnabled(boolean enabled) 
    //向下移动所选内容
    public void moveSelectionDown() 
    //上移所选内容
    public void moveSelectionUp() 
    //将所选内容左移
    public void moveSelectionLeft() 
    //将所选内容右移
    public void moveSelectionRight() 
    //将所选内容移动到行尾
    public void moveSelectionEnd() 
    //将所选内容移至行首
    public void moveSelectionHome() 
    //将所选内容移动到给定位置
    public void setSelection(int line, int column) 
    //选择所有文本
    public void selectAll() 
    //通过调用设置选择区域
    public void setSelectionRegion(int lineLeft, int columnLeft, int lineRight,int columnRight, int cause)
    //移至下一页
    public void movePageDown() 
    //移至上一页
    public void movePageUp() 
    //从剪贴板粘贴文本
    public void pasteText() 
    //将文本复制到剪贴板
    public void copyText() 
    //将当前行复制到剪贴板
    private void copyLine() 
    //将文本复制到剪贴板并删除 剪切
    public void cutText() 
    //将当前行复制到剪贴板并将其删除。剪切当前行
    public void cutLine() 
    //复制当前行
    public void duplicateLine() 
    //复制当前选择并将其粘贴到
    public void duplicateSelection() 
    //选择左侧选择手柄上的单词。
    public void selectCurrentWord() 
    //选择给定字符位置的单词
    public void selectWord(int line, int column) 
    //获取文本
    public Content getText() 
    //设置文本
    public void setText(@Nullable CharSequence text) 
    //设置字体大小
    public void setTextSize(float textSize) 
    //呈现ASCII函数字符
    public void setRenderFunctionCharacters(boolean renderFunctionCharacters) 
    public boolean isRenderFunctionCharacters() 
    //检查编辑器当前是否正在执行格式化操作
    public boolean isFormatting() 
    //是否显示行号
    public boolean isLineNumberEnabled() 
    public void setLineNumberEnabled(boolean lineNumberEnabled) 
    //设置编辑器主题
    public EditorColorScheme getColorScheme() 
    public void setColorScheme(@NonNull EditorColorScheme colors) 
    //将所选内容移动到行开始滚动 跳转行
    public void jumpToLine(int line) 
    //将当前选择位置标记为光标范围的一个点。
    public void beginLongSelect() 
    public boolean isInLongSelect() 
    //标记长选择模式结束
    public void endLongSelect() 

    //-------------------------------------------------------------------------------
    //-------------------------IME Interaction---------------------------------------
    //-------------------------------------------------------------------------------
    //强制重新运行分析
    public void rerunAnalysis() 
    //隐藏自动完成窗口（如果显示）
    public void hideAutoCompleteWindow() 
    //获取游标代码块索引
    public int getBlockIndex() 
    //显示软输入法
    public void showSoftInput() 
    //隐藏软输入法
    public void hideSoftInput() 
    //通知输入法由于外部原因已更改文本
    public void notifyIMEExternalCursorChange() 
    //重新启动输入连接
    public void restartInput() 
    //将文本中和屏幕上的光标位置发送到输入法
    public void updateCursor() 
    //释放编辑持有的一些资源
    public void release() 
    public boolean isReleased() 
    //隐藏编辑器的所有内置窗口
    public void hideEditorWindows() 
    //由ColorScheme调用以通知失效
    public void onColorUpdated(int type) 
    //由配色方案调用以初始化颜色
    public void onColorFullUpdate() 