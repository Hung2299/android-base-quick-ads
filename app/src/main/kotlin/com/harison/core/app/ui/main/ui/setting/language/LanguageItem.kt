package com.harison.core.app.ui.main.ui.setting.language

import android.view.View
import com.harison.core.app.R
import com.harison.core.app.databinding.ItemLanguageBinding
import com.harison.core.app.ui.splash.ui.first_language.Language
import com.xwray.groupie.viewbinding.BindableItem

class LanguageItem(
    private val language: Language,
    private val onLanguageListener: OnLanguageListener,
    private val viewModel: LanguageViewModel
) : BindableItem<ItemLanguageBinding>() {
    private var defaultLocate = "en"

    interface OnLanguageListener {
        fun onChooseLanguage(language: Language, position: Int)
    }

    override fun getLayout(): Int = R.layout.item_language

    override fun initializeViewBinding(view: View): ItemLanguageBinding =
        ItemLanguageBinding.bind(view)

    override fun bind(viewBinding: ItemLanguageBinding, position: Int) {

        viewBinding.languageNameTxt.text = language.name
        viewBinding.flagImg.setImageResource(language.image)

        var locale = defaultLocate
        viewModel.chosenLanguage?.code?.let {
            locale = it
        }

        if (locale == language.code) {
            viewBinding.checkbox.setImageResource(R.drawable.ic_ratio_checked)
        } else {
            viewBinding.checkbox.setImageResource(R.drawable.ic_ratio_unchecked)
        }

        viewBinding.root.setOnClickListener {
            onLanguageListener.onChooseLanguage(language, position)
            viewBinding.checkbox.setImageResource(R.drawable.ic_ratio_checked)
        }
    }
}
