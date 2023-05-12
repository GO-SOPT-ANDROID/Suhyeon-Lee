package org.android.go.sopt.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.android.go.sopt.Data.Model.ResUsersDto
import org.android.go.sopt.Data.SrvcPool
import org.android.go.sopt.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val listUsersSrvc = SrvcPool.reqresSrvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getList()
    }

    private fun getList() {
        listUsersSrvc.listUsers()
            .enqueue(object: retrofit2.Callback<ResUsersDto> {
            override fun onResponse(call: Call<ResUsersDto>, response: Response<ResUsersDto>) {
                if (response.isSuccessful) {
                    //여기부터 해야댐 리스트 만들고 adapter에 뿌려줘야 돼, 근데 어댑터도 바꿔야겠지
                    Log.d("TWOSOME2", response.body()?.data.toString())
                    binding.rv.adapter = HomeAdapter(response.body()?.data!!)
                }
                else { // 서버통신 실패(40X)
                    Toast.makeText(applicationContext, "서버통신 실패(40X)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResUsersDto>, t: Throwable) { // 서버통신 실패(응답값 X)
                Toast.makeText(applicationContext, "서버통신 실패(응답값 X)", Toast.LENGTH_SHORT).show()
            }
        })
    }
}