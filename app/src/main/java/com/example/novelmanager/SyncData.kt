package com.example.novelmanager

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.example.novelmanager.network.Data
import com.example.novelmanager.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncData(private val context: Context) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {
        val apiService = RetrofitClient.instance

        // Ejemplo de obtener datos del servidor
        apiService.getData().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    // Procesar los datos obtenidos
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                // Manejar error
            }
        })

        // Ejemplo de enviar datos al servidor
        val dataToSend = Data(  id = 1, name = "Example Name",value = "Example Value" )
        apiService.postData(dataToSend).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Datos enviados exitosamente
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Manejar error
            }
        })

        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        val intent = Intent("com.example.novelmanager.SYNC_COMPLETE")
        context.sendBroadcast(intent)
    }
}