package com.example.novelmanager

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.example.novelmanager.database.entidades.Novel
import com.example.novelmanager.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncDataTask(private val context: Context, private val novelToSend: Novel) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {
        val apiService = RetrofitClient.instance

        // Enviar novela al servidor
        apiService.postNovel(novelToSend).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Novela enviada exitosamente
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