package org.android.go.sopt.presentation.main.gallery

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.util.BindingFragment
import org.android.go.sopt.util.UserConstants

class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display()
        registerClickEvents()
    }

    private fun display() {
        with(binding) {
            tvId.text = "아이디 ${UserConstants.id}님, 안녕하세요!"
            tvName.text = "이름 : ${UserConstants.name}"
            tvSkill.text = "특기 : ${UserConstants.skill}"
        }
    }

    private fun registerClickEvents() {
        binding.btnLogout.setOnClickListener {
            with(UserConstants) {
                if (savedId != null) setAutoLogin(null, null)
            }
            requireActivity().finish()
        }
    }
}