package org.android.go.sopt.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

/* Multi-part 서버 통신: 서버에 사진 전송
 * 1. URI를 가져온다
 * 2. 이걸 Bitmap으로 변환
 * 3. 이걸 File로 저장
 * 4. 이걸 RequestBody로 변환
 * 5. 드디어 서버에 전송!
 *
 * 1) (SrvcInterface) api 작성
 * @Part annotation으로 보내지는 데이터 타입들은 MultipartBody.Part여야 한다.
 * (참고로: 살펴보면 MultipartBody는 RequestBody를 상속받고 있음)
 * 그럼.. 보내려는 사진 URI를 RequestBody 타입으로 바꿔줘야 겠지?
 * 2) Util 코드 사용해 URI -> RequestBody로 변환
 * 3) 서버에 쏘기
*/

class ContentUriRequestBody(
    context: Context,
    private val uri: Uri
) : RequestBody() {
    private val contentResolver = context.contentResolver

    private var fileName = ""
    private var size = -1L

    init {
        contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                fileName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            }
        }
    }

    fun getFileName() = fileName

    override fun contentLength(): Long = size

    override fun contentType(): MediaType? =
        contentResolver.getType(uri)?.toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        contentResolver.openInputStream(uri)?.source()?.use { source ->
            sink.writeAll(source)
        }
    }

    /*
     * SrvcInterface.kt에서 uploadMusic() API 보면,
     * (@Part annotation이 붙은) MultipartBody.Part 타입의 변수에는 SerialName을 명시해줄 수 없었다.
     * @Part("image") image: MultipartBody.Part <- 이렇게 쓸 수 없다는 뜻.
     * 그래서 아래 함수를 통해 SerialName(아래에선 'image')을 명시해준다!
     * 해당 API의 form-data 타입의 Body보면, 한 property의 key가 image, value가 ~.png 등이겠지.
     */
    fun toFormData() = MultipartBody.Part.createFormData("image", getFileName(), this)
}