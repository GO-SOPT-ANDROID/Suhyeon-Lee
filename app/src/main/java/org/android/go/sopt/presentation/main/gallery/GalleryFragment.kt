package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.BindingFragment

class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val mainVm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        consistUi()
    }

    private fun consistUi() {
        with(binding) {
            tvId.text = "아이디 ${mainVm.id}님, 안녕하세요!"
            tvName.text = "이름 : ${mainVm.name}"
            tvSkill.text = "특기 : ${mainVm.skill}"
        }
    }
}