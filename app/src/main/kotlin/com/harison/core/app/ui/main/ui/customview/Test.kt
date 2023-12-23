package com.harison.core.app.ui.main.ui.customview

import android.content.Context
import android.util.AttributeSet
import com.harison.core.app.R
import com.harison.core.app.databinding.ItemLanguageBinding
import com.harison.core.app.platform.BaseCustomViewLinearLayout

class Test : BaseCustomViewLinearLayout<ItemLanguageBinding> {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override val layoutId: Int
        get() = R.layout.item_language

    override fun initView() {
        super.initView()

    }
}