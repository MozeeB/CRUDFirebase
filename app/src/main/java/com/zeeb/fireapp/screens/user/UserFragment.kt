package com.zeeb.fireapp.screens.user


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.zeeb.fireapp.MainActivity
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser
import com.zeeb.fireapp.screens.home.UsersAdapter
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : Fragment() {


    lateinit var ref: DatabaseReference
    lateinit var users: MutableList<ItemUser>
    lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("users")
        users = mutableListOf()

        btn_create_user.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    users.clear()
                    for (usr in dataSnapshot.children) {
                        val user = usr.getValue(ItemUser::class.java)
                        users.add(user!!)
                    }

                    usersAdapter = UsersAdapter(context!!, users)

                    rv_users.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = usersAdapter
                    }
                }
            }
        })
    }
}
