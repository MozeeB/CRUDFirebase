package com.zeeb.fireapp.screens.save


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser
import kotlinx.android.synthetic.main.fragment_save.*


class SaveFragment : Fragment() {


    lateinit var ref : DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("users")

        btnSave.setOnClickListener{
            saveUser()
        }
    }

    private fun saveUser() {
        val name = edtName.text.toString()
        val status = edtStatus.text.toString()

        if (name.isEmpty() || status.isEmpty()) {
            edtName.error = "Please enter a name"
            return
        }

        val userId = ref.push().key.toString()
        val user = ItemUser(userId,name, status)

        ref.child(userId).setValue(user).addOnCompleteListener {

            Toast.makeText(context, "Successs", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_saveFragment_to_homeFragment)

            edtName.setText("")
            edtStatus.setText("")


        }.addOnFailureListener {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

        }


    }
}
