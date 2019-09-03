package com.zeeb.fireapp.screens.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser

open class AdapterUser(c: Context?, data: List<ItemUser>?) : RecyclerView.Adapter<AdapterUser.ViewHolder>() {


    var dataItem: List<ItemUser>? = data
    var mContext: Context? = c

    override fun getItemCount(): Int {
        return dataItem?.size!!
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(mContext).inflate(R.layout.item_list, p0, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        p0.nama?.text = dataItem?.get(p1)?.nama
        p0.status?.text = dataItem?.get(p1)?.status


        p0.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("nama", dataItem?.get(p1)?.nama)
            intent.putExtra("status", dataItem?.get(p1)?.status)
            mContext?.startActivity(intent)
        }

        p0.update?.setOnClickListener{
            val id = dataItem?.get(p1)?.id.toString()
            val nama = dataItem?.get(p1)?.nama.toString()
            val status = dataItem?.get(p1)?.status.toString()
            showUpdateDialog(id,nama,status)

        }
        p0.delete?.setOnClickListener{
            val id = dataItem?.get(p1)?.id.toString()
            showDeleteDialog(id)
        }

    }

    private fun showDeleteDialog(id: String) {

        val builder = AlertDialog.Builder(mContext!!)

        builder.setTitle("Delete sure?")
        builder.setPositiveButton("Delete") { dialog, which ->
            val progressDialog = ProgressDialog(mContext, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Deleting...")
            progressDialog.show()
            val mydatabase = FirebaseDatabase.getInstance().getReference("users")
            mydatabase.child(id).removeValue()
            if (mydatabase.child(id).removeValue().isSuccessful){
                progressDialog.dismiss()
            }else{
                progressDialog.dismiss()
            }
            Toast.makeText(mContext,"Deleted!!",Toast.LENGTH_SHORT).show()

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()


    }

    @SuppressLint("InflateParams")
    private fun showUpdateDialog(id: String, nama: String, email: String) {

        val builder = AlertDialog.Builder(mContext!!)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.update, null)

        val textNama = view.findViewById<EditText>(R.id.edtUpdateName)
        val textStatus = view.findViewById<EditText>(R.id.edtUpdateStatus)

        textNama.setText(nama)
        textStatus.setText(email)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("users")
            val user = ItemUser(id,nama,email)

            dbUsers.child(id).setValue(user).addOnCompleteListener {
                Toast.makeText(mContext,"Updated",Toast.LENGTH_SHORT).show()
                textNama.setText("")
                textStatus.setText("")
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()


    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var nama = itemView?.findViewById<TextView>(R.id.tvNama)
        var status = itemView?.findViewById<TextView>(R.id.tvStatus)
        var update = itemView?.findViewById<Button>(R.id.btnUpdate)
        var delete = itemView?.findViewById<Button>(R.id.btnDelete)



    }

}