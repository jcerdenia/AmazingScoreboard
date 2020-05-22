package com.joshuacerdenia.amazingscoreboard

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import me.priyesh.chroma.ChromaDialog
import me.priyesh.chroma.ColorMode
import me.priyesh.chroma.ColorSelectListener

private const val REQUEST_CONTACT = 0
private const val PLAYER_NAME_ARG = "player_name"
private const val PLAYER_KEY_ARG = "player_key"
private const val PLAYER_COLOR_ARG = "player_color"

class EditNameFragment : DialogFragment() {

    private lateinit var nameField: EditText
    private lateinit var cancelButton: Button
    private lateinit var doneButton: Button
    private lateinit var searchContactsButton: ImageButton
    private lateinit var clearButton: ImageButton
    private lateinit var colorButton: Button

    companion object {
        fun newInstance(key: String, name: String, color: Int): EditNameFragment {
            val args = Bundle().apply {
                putString(PLAYER_NAME_ARG, name)
                putString(PLAYER_KEY_ARG, key)
                putInt(PLAYER_COLOR_ARG, color)
            }

            return EditNameFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val view: View =
            inflater.inflate(R.layout.fragment_editname, container, false)

        var name = arguments?.getString(PLAYER_NAME_ARG)
        val key = arguments?.getString(PLAYER_KEY_ARG)
        val color = arguments?.getInt(PLAYER_COLOR_ARG)
        val defName = key?.substringBefore("key")?.trim()

        var currentColor = color

        nameField = view.findViewById(R.id.player_name_input)
        cancelButton = view.findViewById(R.id.cancel_button)
        doneButton = view.findViewById(R.id.done_button)
        clearButton = view.findViewById(R.id.clear_button)
        searchContactsButton = view.findViewById(R.id.search_contacts)
        colorButton = view.findViewById(R.id.color_button)

        val nameWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?, start: Int, count: Int, after: Int) {
                // This space intentionally left blank
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                //This one too
            }

            override fun afterTextChanged(sequence: Editable?) {
                // If there's anything at all in the name field, set that as the name
                if (sequence != null) {
                    if (sequence.count() > 0) {
                        name = nameField.text.toString()
                        clearButton.visibility = View.VISIBLE
                    } else {
                        name = defName
                        clearButton.visibility = View.GONE
                    }
                }
            }
        }

        nameField.apply {
            text = Editable.Factory.getInstance().newEditable(name)
            hint = defName
            addTextChangedListener(nameWatcher)
        }

        clearButton.setOnClickListener {
            nameField.text = null
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        doneButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onNameSubmitted(key!!, name!!)
                (fragment as Callbacks).onColorSet(key, currentColor!!)
            }
            dismiss()
        }

        searchContactsButton.apply {
            val pickContactIntent =
                Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            setOnClickListener {
                startActivityForResult(pickContactIntent, REQUEST_CONTACT)
            }
            // GUARD AGAINST NO CONTACT APP
            val packageManager: PackageManager = requireActivity().packageManager
            val resolvedActivity: ResolveInfo? = packageManager.resolveActivity(
                pickContactIntent, PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null) {
                isEnabled = false
            }
        }

        colorButton.apply {
            setBackgroundColor(currentColor!!)
            setOnClickListener {
                ChromaDialog
                    .Builder()
                    .initialColor(currentColor!!)
                    .colorMode(ColorMode.RGB)
                    .onColorSelected(object : ColorSelectListener {
                        override fun onColorSelected(color: Int) {
                            currentColor = color
                            colorButton.setBackgroundColor(currentColor!!)
                        }
                    })
                    .create()
                    .show(requireFragmentManager(), "ChromaDialog")
            }
            setOnLongClickListener {
                // restore default color
                var defColor = when (key) {
                    PLAYER1_KEY -> R.color.colorPlayer1
                    PLAYER2_KEY -> R.color.colorPlayer2
                    PLAYER3_KEY -> R.color.colorPlayer3
                    PLAYER4_KEY -> R.color.colorPlayer4
                    PLAYER5_KEY -> R.color.colorPlayer5
                    PLAYER6_KEY -> R.color.colorPlayer6
                    PLAYER7_KEY -> R.color.colorPlayer7
                    PLAYER8_KEY -> R.color.colorPlayer8
                    else -> currentColor
                }
                currentColor = ContextCompat.getColor(context, defColor!!)
                colorButton.setBackgroundColor(currentColor!!)
                true
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_CONTACT && data != null -> {
                val contactUri: Uri? = data.data
                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                val cursor = requireActivity()
                    .contentResolver
                    .query(contactUri!!, queryFields, null, null, null)
                cursor?.use {
                    if (it.count == 0) {
                        return
                    }
                    it.moveToFirst()
                    val contact = it.getString(0)
                    nameField.text = Editable.Factory.getInstance().newEditable(contact)
                }
            }
        }
    }

    interface Callbacks {
        fun onNameSubmitted(key: String, name: String)
        fun onColorSet(key: String, color: Int)
    }
}