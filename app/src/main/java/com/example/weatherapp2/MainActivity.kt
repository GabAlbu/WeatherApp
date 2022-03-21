package com.example.weatherapp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.weatherapp2.model.CurrentService
import com.example.weatherapp2.utils.ToastManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var remote: CurrentService
    private lateinit var spinner: Spinner
    private lateinit var temperatureTxtView: TextView
    private lateinit var cityTxtView: TextView
    private lateinit var buttonRefresh: Button
    private var toastManager: ToastManager = ToastManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temperatureTxtView = findViewById(R.id.tempShow)
        buttonRefresh = findViewById(R.id.buttonRefresh)
        cityTxtView = findViewById(R.id.stateShow)
        spinner = findViewById(R.id.spinner)

        spinner.onItemSelectedListener = this

        var mTestArray: Array<String> = resources.getStringArray(R.array.spinner_states)
        remote = RetrofitClient.createService(CurrentService::class.java)
        requestTemperature("manaus")
        //injeção de dependencia ()
        toastManager.toast(this, "asd",Toast.LENGTH_SHORT)
    }

    fun requestTemperature(city: String) {
        val callTemperature = remote.list(city)
        callTemperature.enqueue(object : Callback<GetTemperatureResponse> {
            override fun onResponse(
                call: Call<GetTemperatureResponse>,
                response: Response<GetTemperatureResponse>
            ) {
                val body = response.body()
                body?.current?.temperature
                temperatureTxtView.text = body?.current?.temperature.toString()
                buttonRefresh.setOnClickListener {
                    //       this.onResponse()
                }
            }

            override fun onFailure(call: Call<GetTemperatureResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Não foi possivel solicitar...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    @SuppressLint("ResourceType")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spinner -> {
                val text = parent.getItemAtPosition(position)
                cityTxtView.text = text.toString()
                requestTemperature(text.toString())

            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "Nothing Item Select", Toast.LENGTH_SHORT).show()
    }



}