package mx.tec.atomictracker

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class ReminderBroadcaster : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {

        //Build notification
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(titleExtra)
            .setContentText(messageExtra)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)

    }


}