import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import com.radio.repradio.R

class version(context: Context) {
    private val dialog: AlertDialog

    init {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)

        dialog = builder.create()

        val cerrar = dialogView.findViewById<Button>(R.id.cerrar)
        cerrar.setOnClickListener{destroy()}

    }

    fun show() {
        dialog.show()
    }

    fun destroy(){
        dialog.dismiss()
    }

}
