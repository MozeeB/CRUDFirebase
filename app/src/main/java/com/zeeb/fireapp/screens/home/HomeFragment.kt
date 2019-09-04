package com.zeeb.fireapp.screens.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.NullPointerException

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {


    var navController : NavController? = null

    lateinit var ref: DatabaseReference



    companion object {
        const val CHANNEL_ID = "simplified_coding"
        private const val CHANNEL_NAME= "Simplified Coding"
        private const val CHANNEL_DESC = "Android Push Notification Tutorial"
    }

    lateinit var token: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("users")

        btn_save.setOnClickListener {
            saveData()
//            startActivity(Intent(context, UsersActivity::class.java))
        }

        btn_read.setOnClickListener {
//            startActivity(Intent(context, UsersActivity::class.java))
            findNavController().navigate(R.id.action_homeFragment_to_saveFragment)
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener {
                    task ->

//                progressbar.visibility = View.INVISIBLE
                if(!task.isSuccessful){
//                    textViewToken.text = task.exception?.message
                    return@OnCompleteListener
                }

                token = task.result?.token!!

//                textViewMessage.text = "Your FCM Token is:"
//                textViewToken.text = token

//                edtToken.setText(token)
                val snack = Snackbar.make(view,"Your FCM Token is: " + token,Snackbar.LENGTH_LONG)
                snack.show()

            })


        navController = Navigation.findNavController(view)

    }
    private fun saveData() {
        val nama = input_nama.text.toString()
        val status = input_status.text.toString()

        val userId = ref.push().key.toString()
        val user = ItemUser(userId, nama, status)

        if (nama.isEmpty() || status.isEmpty()){
            Toast.makeText(context, "please fill", Toast.LENGTH_SHORT).show()

        }else{
            ref.child(userId).setValue(user).addOnCompleteListener {
                if (it.isSuccessful){
                    input_nama.setText("")
                    input_status.setText("")
                    findNavController().navigate(R.id.action_homeFragment_to_saveFragment)
                }else{
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()

                }

            }

        }
    }


}
