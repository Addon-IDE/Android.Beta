package com.mcyizy.editor.editor

//主
import io.github.rosemoe.sora.widget.CodeEditor

//Textmate
/*
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.dsl.languages
import io.github.rosemoe.sora.langs.textmate.registry.model.DefaultGrammarDefinition
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
*/

public class TextmateLanguage(private val mCodeEditor: CodeEditor) {

    /*
    init {
        loadLanguages()
    }
    
    //加载默认语言
    private fun loadLanguages() {
        GrammarRegistry.getInstance().loadGrammars("textmate/languages.json")
    }
    
    //设置语言
    public fun SetLang(lang : Int) {
        
    }
    
    
    //设置语言
    public fun SetLanguages(value : String) {
        try {
            ensureTextmateTheme()
            val editorLanguage = mCodeEditor.editorLanguage
            val language = if (editorLanguage is TextMateLanguage) {
            editorLanguage.updateLanguage(
            "source.java"
            )
            editorLanguage
            } else {
            TextMateLanguage.create(
            "source.java",
            true
            )
            }
            mCodeEditor.setEditorLanguage(
           language
            )
            } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    //设置语言
    public fun SetLanguages2(value : String) {
        
    }
    */

}