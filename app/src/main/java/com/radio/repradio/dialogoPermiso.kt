package com.radio.repradio

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class dialogoPermiso : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogStyle)

        builder.setTitle("Permiso requerido")
            .setMessage("Es importante que las notificaciones estén activadas para estar al tanto de nuestras actividades y comunicados.\n\nSi deseas activarlo más tarde puedes hacerlo en la opción 'Acerca de...'")
            .setPositiveButton("Entendido") { dialog, which ->
            }

        return builder.create()
    }
}
