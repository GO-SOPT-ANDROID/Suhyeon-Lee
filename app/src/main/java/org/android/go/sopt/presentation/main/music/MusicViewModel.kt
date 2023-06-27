package org.android.go.sopt.presentation.main.music

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ResGetMusicListDto
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.showToast
import org.android.go.sopt.util.toPlainRequestBody

class MusicViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.soptSrvc

    private val _musicList: MutableLiveData<MutableList<ResGetMusicListDto.Data.Music>> = MutableLiveData()
    val musicList: LiveData<MutableList<ResGetMusicListDto.Data.Music>> = _musicList

    private val _selImg: MutableLiveData<Uri?> = MutableLiveData(null)
    val selImg: LiveData<Uri?> = _selImg

    val title: MutableLiveData<String> = MutableLiveData()
    val singer: MutableLiveData<String> = MutableLiveData()

    fun setSelImg(uri: Uri) {
        _selImg.value = uri
    }

    private fun addOneData() {
        _musicList.value?.add(ResGetMusicListDto.Data.Music(
            title.value!!, singer.value!!, _selImg.value!!.toString()
        ))
        _musicList.value = _musicList.value?.toMutableList()
    }

    fun uploadMusic(context: Context, mainVm: MainViewModel) {
        if (_selImg.value != null) {
            viewModelScope.launch {
                kotlin.runCatching {
                    soptSrvc.uploadMusic(
                        mainVm.id,
                        ContentUriRequestBody(context, _selImg.value!!).toFormData(),
                        hashMapOf(
                            "title" to title.value.toPlainRequestBody(),
                            "singer" to singer.value.toPlainRequestBody()
                        )
                    )
                }.fold(
                    onSuccess = { response ->
                        addOneData()
                        initializeUi()
                        mainVm.setDialogFlag(false)
                        context.showToast(response.message)
                    }, onFailure = {
                        context.showToast("You've failed to upload music.")
                    }
                )
            }
        }
    }

    fun getMusicList(context: Context, mainVm: MainViewModel) {
        viewModelScope.launch {
            kotlin.runCatching {
                soptSrvc.getMusic(mainVm.id)
            }.fold(
                onSuccess = {
                    _musicList.value = it.data.musicList.toMutableList()
                    mainVm.setDialogFlag(false)
                }, onFailure = {
                    context.showToast("Something happened while getting your music list.")
                }
            )
        }
    }

    private fun initializeUi() {
        title.value = ""
        singer.value = ""
        _selImg.value = null
    }
}