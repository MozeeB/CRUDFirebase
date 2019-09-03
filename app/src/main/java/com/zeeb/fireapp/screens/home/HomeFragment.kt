package com.zeeb.fireapp.screens.home


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(), View.OnClickListener {


    var navController : NavController? = null

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<ItemUser>


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
                Toast.makeText(context, "Your FCM Token is: " + token, Toast.LENGTH_LONG ).show()

            })


        navController = Navigation.findNavController(view)

        ref = FirebaseDatabase.getInstance().getReference("users")
        list = mutableListOf()

        floatingActionButton.setOnClickListener(this)


        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(ItemUser::class.java)
                        list.add(user!!)
                    }
                    val adapterUser = AdapterUser(context, list)
                    rvMain?.layoutManager = LinearLayoutManager(activity)
                    rvMain?.adapter = adapterUser
                }
            }
        })
    }

    @SuppressLint("InvalidAnalyticsName")
    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.floatingActionButton -> navController!!.navigate(R.id.action_homeFragment_to_saveFragment)


        }
    }



}
