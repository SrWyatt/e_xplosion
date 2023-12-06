import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.radio.repradio.R


class dialogNotification(context: Context) {

    private val dialog: AlertDialog


    init {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.notification_dialog, null)

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)

        dialog = builder.create()
        val entendido = dialogView.findViewById<Button>(R.id.entendido)

        entendido.setOnClickListener{destroy()}


    }



    fun show() {
        dialog.show()
    }

    fun destroy() {
        dialog.dismiss()
    }


}