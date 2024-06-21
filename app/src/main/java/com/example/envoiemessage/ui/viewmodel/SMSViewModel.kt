package com.example.envoiemessage.ui.viewmodel

import android.app.Application
import android.telephony.SmsManager
import androidx.lifecycle.AndroidViewModel

class SMSViewModel(apllication: Application):AndroidViewModel(apllication) {
    fun sendSMS(phoneNumber : String,message:String){
        SmsManager.getDefault().sendTextMessage(phoneNumber,null,message,null,null)
    }

}