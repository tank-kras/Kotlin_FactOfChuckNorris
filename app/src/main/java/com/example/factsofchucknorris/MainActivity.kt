//https://devcolibri.com/develop-a-chuck-norris-facts-android-app-with-kotlin/

package com.example.factsofchucknorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val URL = "https://api.icndb.com/jokes/random"
    var okHttpClient: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // при открытии активити вызывает загрузку факта
        loacRandomFacts()

        // обработчик нажатия кнопки
        buttonNext.setOnClickListener{
            loacRandomFacts()
        }

    }
    // метод загрузки факта
    private fun loacRandomFacts(){
        // progressBar.visibility запускает крутящееся колесо, эмитация загрузки
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }

        // строим объект тип Request (http-запрос к определенному адресу URL)
        val request:Request = Request.Builder().url(URL).build()
        // делаем вызов с http-запросом, ответ вернется в интерфейс с типом Callback. enqueue - поставить в очередь на выполнение
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            // реализация метода onResponse интерфейса Call, в который возвращается результат response
            override fun onResponse(call: Call, response: Response?) {
                // получение из http-ответа текста json
                val json = response?.body()?.string()

                // разбор json
                val txt = (JSONObject(json).getJSONObject("value").get("joke")).toString()

                runOnUiThread {
                    progressBar.visibility = View.GONE
                    factTv.text = Html.fromHtml(txt)
                }
            }
        })


    }
}
