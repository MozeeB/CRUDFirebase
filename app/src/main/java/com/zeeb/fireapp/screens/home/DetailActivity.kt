package com.zeeb.fireapp.screens.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.zeeb.fireapp.MainActivity
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        edtName.setText(intent.getStringExtra("nama"))
        edtStatus.setText(intent.getStringExtra("status"))

        btnUpdate.setOnClickListener {
            updateData()
        }
        btnDelete.setOnClickListener{
            deleteData()
        }
    }

    private fun deleteData() {
        val id  = intent.getStringExtra("id")
        val mydatabase = FirebaseDatabase.getInstance().getReference("users")
        mydatabase.child(id).removeValue()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"Deleted!!",Toast.LENGTH_SHORT).show()
    }

    private fun updateData() {
        val id  = intent.getStringExtra("id")
        val nama = intent.getStringExtra("nama")
        val status = intent.getStringExtra("status")

        val dbUsers = FirebaseDatabase.getInstance().getReference("users")
        val user = ItemUser(id,nama,status)
        dbUsers.child(user.id)
            .setValue(user)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Updated $id", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }else{
                    Toast.makeText(this,"FAILED", Toast.LENGTH_SHORT).show()

                }

            }
    }
}
