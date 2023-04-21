package org.android.go.sopt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.android.go.sopt.databinding.ActivityMyInfoBinding

class MyInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getInfo()
    }

    private fun getInfo() {
        with(binding) {
            val name = intent.getStringExtra("name").toString()
            val hobby = intent.getStringExtra("hobby").toString()
            tvName.text = "이름 : $name"
            tvHobby.text = "특기 : $hobby"
        }
    }
}