package com.example.novelmanager.optimizacion
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class NetworkJobService : JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        // Implementar la lógica de la tarea en segundo plano
        Log.d("NetworkJobService", "Job started")
        // Simular trabajo en segundo plano
        Thread {
            // Realizar trabajo en segundo plano aquí
            Log.d("NetworkJobService", "Job finished")
            jobFinished(params, false)
        }.start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("NetworkJobService", "Job stopped")
        return false
    }
}