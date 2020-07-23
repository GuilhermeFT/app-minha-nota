package com.guiftapps.calculadora.Model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

import com.guiftapps.calculadora.R
import com.guiftapps.calculadora.View.MainActivity

import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class BroadCastReceiverClass : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val date = Date()
        val c = GregorianCalendar()
        c.time = date
        c.add(Calendar.DAY_OF_WEEK, 1)
        val day = DateFormatSymbols().weekdays[c.get(Calendar.DAY_OF_WEEK)]
        var sem = ""
        val daymodify = day.split("\\-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val dsc = DisciplinaSQL(context).Selecionar()


        if (dsc != null) {
            while (dsc.moveToNext()) {
                val dias: Array<String>
                if (dsc.getString(2) != null) {
                    dias = dsc.getString(2).split("\\ ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    for (i in dias.indices) {
                        if (dias[i].equals(daymodify[0], ignoreCase = true)) {
                            sem += dsc.getString(1) + ";\n"
                        }
                    }
                }
            }
        }

        if (sem != "") {
            val intentN = Intent(context, MainActivity::class.java)
            intentN.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intentN, 0)
            val builder = NotificationCompat.Builder(context, "MinhaN")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Aula amanhã")
                    .setContentText("Clique e veja as disciplinas de amanhã")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1, builder.build())
        }

    }
}
