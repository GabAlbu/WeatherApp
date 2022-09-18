package com.example.weatherapp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.weatherapp2.databinding.ActivityMainBinding
import com.example.weatherapp2.model.CurrentService
import com.example.weatherapp2.utils.ToastManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var remote: CurrentService
    private var toastManager: ToastManager = ToastManager()

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding? = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        var mTestArray: Array<String> = resources.getStringArray(R.array.spinner_states)


        //injeção de dependencia ()
        toastManager.toast(this, "asd",Toast.LENGTH_SHORT)
    }

    fun setupBinding() {
        binding?.run {
            remote = RetrofitClient.createService(CurrentService::class.java)
            requestTemperature("manaus") //colocar para reconhecer a cidade por GPS
            spinCity.onItemSelectedListener = this@MainActivity
            bttnRefresh.setOnClickListener {
                requestTemperature(spinCity.toString())
            }
        }
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
                binding?.txtTempNum?.text = body?.current?.temperature.toString()
                binding?.bttnRefresh?.setOnClickListener {
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
            R.id.spin_city -> {
                val text = parent.getItemAtPosition(position)
                binding?.txtCityName?.text = text.toString()
                requestTemperature(text.toString())
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "Nothing Item Select", Toast.LENGTH_SHORT).show()
    }



}