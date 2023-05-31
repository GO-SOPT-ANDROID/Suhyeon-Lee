package org.android.go.sopt.presentation.main.gallery

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.BindingFragment

class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val mainVm: MainViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display()
        registerClickEvents()
    }

    private fun display() {
        with(binding) {
            tvId.text = "아이디 ${mainVm.id}님, 안녕하세요!"
            tvName.text = "이름 : ${mainVm.name}"
            tvSkill.text = "특기 : ${mainVm.skill}"
        }
    }

    private fun registerClickEvents() {
        binding.btnLogout.setOnClickListener {
            sharedPreferences =
                requireActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
            val id = sharedPreferences.getString("id", null)
            val pw = sharedPreferences.getString("pw", null)
            if (id != null && pw != null) { // 자동 로그인이 되어 있다 -> 풀어
                val spEditor = sharedPreferences.edit()
                spEditor.putString("id", null)
                spEditor.putString("pw", null)
                spEditor.commit()
            }
            requireActivity().finish()
        }
    }
}