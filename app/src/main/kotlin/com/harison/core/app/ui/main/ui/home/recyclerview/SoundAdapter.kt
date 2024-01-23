package com.harison.core.app.ui.main.ui.home.recyclerview

import androidx.core.view.isVisible
import com.harison.core.app.R
import com.harison.core.app.databinding.ItemSoundBinding
import com.harison.core.app.domain.ItemSound
import com.harison.core.app.platform.BaseRecyclerViewAdapter
import com.harison.core.app.utils.setOnSingleClickListener

class SoundAdapter : BaseRecyclerViewAdapter<ItemSound, ItemSoundBinding>() {

    var onClickItem: (ItemSound) -> Unit = {}
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_sound
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemSoundBinding>, position: Int) {
        val item = items.getOrNull(position)
        holder.binding.apply {
            button.setOnSingleClickListener {
                items.getOrNull(position)?.let { itemSound ->
                    onClickItem(itemSound)
                }
            }
            name.text = item?.name
            lottieAnimationView.isVisible = item?.isHot == true
        }
    }
}