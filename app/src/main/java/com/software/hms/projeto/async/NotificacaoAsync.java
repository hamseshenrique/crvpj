package com.software.hms.projeto.async;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.software.hms.projeto.MensagensActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.dto.MensagemDTO;

/**
 * Created by hms on 22/10/16.
 */

public class NotificacaoAsync extends AsyncTask<MensagemDTO,Void,Void>{

    private Context context;

    public NotificacaoAsync(final Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(MensagemDTO... mensagemDTOs) {

        final MensagemDTO mensagemDTO = mensagemDTOs[0];

        if(mensagemDTO != null){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(mensagemDTO.getCabecalho())
                            .setContentText(mensagemDTO.getDescricao())
                            .setAutoCancel(Boolean.TRUE);
            Intent resultIntent = new Intent(context, MensagensActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MensagensActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            1,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }

        return null;
    }
}
