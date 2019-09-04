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
import com.zeeb.fireapp.MainActivity
import com.zeeb.fireapp.R
import com.zeeb.fireapp.model.ItemUser
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.update.view.*

class UsersAdapter(val ctx: Context, val users: List<ItemUser>) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UsersViewHolder {
        return UsersViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindItem(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tv_nama
        val tvStatus = itemView.tv_status
        val btnUpdate = itemView.btn_update
        val btnDelete = itemView.btn_delete

        fun bindItem(user: ItemUser) {
            tvName.text = user.nama
            tvStatus.text = user.status

            btnUpdate.setOnClickListener { showUpdateDialog(user) }
            btnDelete.setOnClickListener { deleteInfo(user) }
        }

        fun showUpdateDialog(user: ItemUser) {
            val builder = AlertDialog.Builder(ctx)
            builder.setTitle("Update")

            val inflater = LayoutInflater.from(ctx)
            val view = inflater.inflate(R.layout.update, null)

            view.edt_nama.setText(user.nama)
            view.edt_status.setText(user.status)

            builder.setView(view)
            builder.setPositiveButton("Update") { dialog, which ->
                val dbUsers = FirebaseDatabase.getInstance().getReference("users")

                val nama = view.edt_nama.text.toString().trim()
                val status = view.edt_status.text.toString().trim()

                if (nama.isEmpty()) {
                    view.edt_nama.error = "Mohon masukan nama"
                    view.edt_nama.requestFocus()
                    return@setPositiveButton
                }

                if (status.isEmpty()) {
                    view.edt_status.error = "Mohon masukan status"
                    view.edt_status.requestFocus()
                    return@setPositiveButton
                }

                val user = ItemUser(user.id, nama, status)

                dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                    Toast.makeText(ctx, "Berhasil di update.", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Tidak") { dialog, which ->

            }

            val alert = builder.create()
            alert.show()
        }

        fun deleteInfo(user: ItemUser) {
            val progressDialog = ProgressDialog(ctx, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Menghapus...")
            progressDialog.show()

            val mydatabase = FirebaseDatabase.getInstance().getReference("users")
            mydatabase.child(user.id).removeValue()

            Toast.makeText(ctx, "Berhasil dihapus.", Toast.LENGTH_SHORT).show()

            val intent = Intent(ctx, MainActivity::class.java)
            ctx.startActivity(intent)
        }
    }

}