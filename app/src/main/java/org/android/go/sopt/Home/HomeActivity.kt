package org.android.go.sopt.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.Home.Model.DataModel
import org.android.go.sopt.Home.Model.ViewType
import org.android.go.sopt.Home.RecyclerView.MultiViewAdapter
import org.android.go.sopt.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.rv.layoutManager = LinearLayoutManager(this@HomeActivity)
        binding.rv.adapter = MultiViewAdapter(getList())
    }

    private fun getList(): ArrayList<DataModel> {
        return arrayListOf(
            DataModel(
                ViewType.HEADER.ordinal, ViewObject.Header("I love Sopt!")
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Judy", "I don't like coding.."
                )
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Nick", "I like coding.."
                )
            ),
            DataModel(
                ViewType.HEADER.ordinal, ViewObject.Header("Have a good day!")
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Brendy", "I am tired but not tired.."
                )
            ),
            DataModel(
                ViewType.HEADER.ordinal, ViewObject.Header("I am Korean!")
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Jimin", "I don't like exams!"
                )
            ),
            DataModel(
                ViewType.HEADER.ordinal, ViewObject.Header("I love Korea!")
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Somi", "I like cotton, it is soft!"
                )
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Apple", "I like cotton, it is soft!"
                )
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Banana", "I like cotton, it is soft!"
                )
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Candy", "I like cotton, it is soft!"
                )
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Doremi", "I like cotton, it is soft!"
                )
            ),
            DataModel(
                ViewType.PROFILE.ordinal, ViewObject.Profile(
                    "Eagle", "I like cotton, it is soft!"
                )
            )
        )
    }
}