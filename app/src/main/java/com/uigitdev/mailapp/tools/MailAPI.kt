package com.uigitdev.mailapp.tools

import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.GmailAccount
import com.uigitdev.mailapp.model.SentModel
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailAPI(private val sentModel: SentModel, private val gmailAccount: GmailAccount) {
    private var session: Session? = null
    private var emailModel: EmailModel? = null

    fun sendToData(emailModel: EmailModel?) {
        this.emailModel = emailModel
    }

    private fun setProperties() {
        val props = Properties()
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        setSession(props)
    }

    fun send(mailListener: MailListener) {
        try {
            val mm = MimeMessage(session)
            mm.setFrom(InternetAddress(gmailAccount.getEmail()))
            mm.addRecipient(
                Message.RecipientType.TO,
                InternetAddress(emailModel!!.getEmail())
            )
            mm.subject = sentModel.getTitle()
            mm.setText(sentModel.getText())
            Transport.send(mm)
            mailListener.sendEmail(true)
        } catch (e: MessagingException) {
            e.printStackTrace()
            mailListener.sendEmail(false)
        }
    }

    private fun setSession(props: Properties) {
        session = Session.getDefaultInstance(props,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(
                        gmailAccount.getEmail(),
                        gmailAccount.getPassword()
                    )
                }
            })
    }

    interface MailListener {
        fun sendEmail(isSuccess: Boolean)
    }

    init {
        setProperties()
    }
}
